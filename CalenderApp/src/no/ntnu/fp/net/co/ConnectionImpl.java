package no.ntnu.fp.net.co;

import java.io.EOFException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import no.ntnu.fp.net.cl.ClException;
import no.ntnu.fp.net.cl.ClSocket;
import no.ntnu.fp.net.cl.KtnDatagram;
import no.ntnu.fp.net.cl.KtnDatagram.Flag;


public class ConnectionImpl extends AbstractConnection {

    
    private static Map<Integer, Boolean> usedPorts = Collections.synchronizedMap(new HashMap<Integer, Boolean>());

   
    private static final int MAX_SEND_ATTEMPTS = 10;
    private static final int TIME_WAIT_DURATION = 5000;

        
    public ConnectionImpl(int myPort) {
        super();
        usedPorts.put(myPort, true);
        this.myPort = myPort;
        this.myAddress = getIPv4Address();
    }

    private String getIPv4Address() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        }
        catch (UnknownHostException e) {
            return "127.0.0.1";
        }
    }

    /**
     * Establish a connection to a remote location.
     *
     * @param remoteAddress
     *            - the remote IP-address to connect to
     * @param remotePort
     *            - the remote port number to connect to
     * @throws IOException
     *             If there's an I/O error.
     * @throws java.net.SocketTimeoutException
     *             If timeout expires before connection is completed.
     * @see Connection#connect(InetAddress, int)
     */
    public void connect(InetAddress remoteAddress, int remotePort) throws IOException, SocketTimeoutException {
        if (state != State.CLOSED)
            throw new IllegalStateException("State should be closed before making a connection");

        // Set state information
        this.remoteAddress = remoteAddress.getHostAddress();
        this.remotePort = remotePort;

        // Send SYN, receive SYN_ACK and send ACK on SYN_ACK
        KtnDatagram synAck = null, syn = constructInternalPacket(Flag.SYN);

        // Send SYN and receive SYN_ACK
        synAck = safelySendPacket(syn, State.CLOSED, State.SYN_SENT);

        if (isReallyValid(synAck)) {
            lastValidPacketReceived = synAck;
            this.remotePort = synAck.getSrc_port();

            System.out.println("\nSENDING ACK on SYN_ACK");
            safelySendAck(synAck);
        } else {
            throw new IOException("Could not connect, did not receive valid SYN_ACK");
        }

        //System.out.println("\nESTABLISHED\n");
        state = State.ESTABLISHED;
    }

    /**
     * Listen for, and accept, incoming connections.
     *
     * @return A new ConnectionImpl-object representing the new connection.
     * @see Connection#accept()
     */
    public Connection accept() throws IOException, SocketTimeoutException {

        if (state != State.CLOSED)
            throw new IllegalStateException("State should be closed");

        //System.out.println("\nserver: LISTEN\n");
        state = State.LISTEN;

        // Receiving SYN
        KtnDatagram syn = null;
        while (!isReallyValid(syn)) try {
            //System.out.println("\nRECEIVING SYN");
            syn = receivePacket(true);
        } catch (Exception e) { } // Ignore

        // Create new connection
        ConnectionImpl connection = new ConnectionImpl(newPortNumber());
        connection.remoteAddress = syn.getSrc_addr();
        connection.remotePort = syn.getSrc_port();
        //System.out.println("\nSYN_RCVD\n");
        connection.state = State.SYN_RCVD;

        // Send SYN_ACK and receive ACK
        KtnDatagram ack = null;
        try {
            int triesLeft = MAX_SEND_ATTEMPTS;
            while (!connection.isReallyValid(ack) && triesLeft-- > 0) try {
                //System.out.println("\nSENDING SYN_ACK");
                connection.sendAck(syn, true);
                //System.out.println("\nRECEIVING ACK ON SYN_ACK");
                ack = connection.receiveAck();
            } catch (ConnectException e) { // Try again on A2 fail
            } catch (IOException e) { }

        } catch (Exception e) {
            throw new IOException("Unable to connect");
        }

        //System.out.println("\nserver: CLOSED\n");
        state = State.CLOSED;

        // Finalize connection
        if (connection.isReallyValid(ack)) {
            //System.out.println("\nESTABLISHED\n");
            connection.state = State.ESTABLISHED;
            connection.lastValidPacketReceived = ack;
            return connection;
        } else
            throw new IOException("Unable to connect; did not receive valid ACK on SYN_ACK");
    }

    /**
     * Find, reserve and return a new port number to be used in a new connection.
     * @return int
     */
    public static int newPortNumber() {
        int portnum = -1;
           while (portnum == -1 || usedPorts.containsKey(portnum)) {
               portnum = (int)((Math.random()*8999) + 1001);
        }
        usedPorts.put(portnum, true);
        return portnum;
    }

    /**
     * Send a message from the application.
     *
     * @param msg
     *            - the String to be sent.
     * @throws ConnectException
     *             If no connection exists.
     * @throws IOException
     *             If no ACK was received.
     * @see AbstractConnection#sendDataPacketWithRetransmit(KtnDatagram)
     * @see no.ntnu.fp.net.co.Connection#send(String)
     */
    public void send(String msg) throws ConnectException, IOException {
        if (state != State.ESTABLISHED)
            throw new IllegalStateException("Data packets can only be sent in established state");

        // If SYN_ACK is still the last valid packet received, send ACK on SYN_ACK again
        // TODO: Is this the solution to lost ACK on SYN_ACK?
        if (lastValidPacketReceived.getFlag() == Flag.SYN_ACK) {
            boolean sent = false;
            do try {
                sendAck(lastValidPacketReceived, false);
                sent = true;
            } catch (SocketException e) { }
            while (sent == false);
        }

        KtnDatagram datapacket = constructDataPacket(msg), ack = null;
        int triesLeft = MAX_SEND_ATTEMPTS;
        while (!isReallyValid(ack) && triesLeft-- > 0) {
            //System.out.println("\nSENDING DATA + RECEIVE ACK");
            ack = sendDataPacketWithRetransmit(datapacket);
        }

        if (!isReallyValid(ack)) {
            state = State.CLOSED;
            throw new IOException("Failed to send packet");
        }

        // To prevent the fix above from running forever
        lastValidPacketReceived = ack;
    }

    /**
     * Wait for incoming data.
     *
     * @return The received data's payload as a String.
     * @see Connection#receive()
     * @see AbstractConnection#receivePacket(boolean)
     * @see AbstractConnection#sendAck(KtnDatagram, boolean)
     */
    public String receive() throws ConnectException, IOException {
        if (state != State.ESTABLISHED)
            throw new IllegalStateException("State must be established");

        //System.out.println("\nRECEIVING DATA");
        KtnDatagram ktnd = null;
        try {
            ktnd = receivePacket(false);
        } catch (EOFException e) {
            //System.out.println("\nEOFException");
            throw e;
        } catch (IOException e) { } // Ignore

        // Send ACK and deliver content to application
        if (isReallyValid(ktnd) && ktnd.getFlag() == Flag.NONE
                && ktnd.getSeq_nr() <= lastValidPacketReceived.getSeq_nr() + 2) {
            // TODO: Sequence number check merged in validation method. And maybe bugs with + 2?

            lastValidPacketReceived = ktnd;

            //System.out.println("\nSENDING ACK");
            safelySendAck(ktnd);

            return (String)ktnd.getPayload();
        }

        // Received duplicate, try again
        safelySendAck(lastValidPacketReceived);
        return receive();
    }

    /**
     * Sending an ACK and handling errors
     * @param ktnd
     * @throws IOException
     */
    private void safelySendAck(KtnDatagram ktnd) throws IOException {
        if (ktnd.getFlag() != Flag.NONE && ktnd.getFlag() != Flag.SYN && ktnd.getFlag() != null
                && ktnd.getFlag() != Flag.FIN && ktnd.getFlag() != Flag.SYN_ACK)
            throw new IllegalArgumentException("Cannot ACK "+ktnd.getFlag().toString()+" packet");
        int triesLeft = MAX_SEND_ATTEMPTS;
        do {
            try {
                sendAck(ktnd, ktnd.getFlag() == Flag.SYN);
                return;
            } catch (IOException e) { } // Ignore
        } while (triesLeft-- > 0);
        throw new IOException("Could not send ACK");
    }

    /**
     * Send a packet and wait for ACK. If no ACK is received, null is returned
     * @param ktnd
     * @return KtnDatagram
     * @throws EOFException
     */
    private KtnDatagram safelySendPacket(KtnDatagram ktnd, State before, State after) throws EOFException {
        KtnDatagram ack = null;
        int triesLeft = MAX_SEND_ATTEMPTS;
        while (!isReallyValid(ack) && triesLeft-- > 0) try {

            state = before;
            //System.out.println("\n"+state.toString());

            //System.out.println("\nSENDING "+ktnd.getFlag().toString());
            simplySendPacket(ktnd);

            state = after;
            //System.out.println("\n"+state.toString());

            //System.out.println("\nRECEIVING "+ktnd.getFlag()+" ACK");
            ack = receiveAck();

        } catch (ClException e) { // Ignore A2 errors
        } catch (ConnectException e) {
        } catch (SocketException e) {
        } catch (IOException e) { }
        return ack;
    }

    /**
     * Close the connection.
     *
     * @see Connection#close()
     */
    public void close() throws IOException {
        if (state != State.ESTABLISHED)
            throw new IllegalStateException("Cannot close unless connected");

        // Sending FIN_ACK if disconnect request has been received, if not send FIN
        if (disconnectRequest != null) {

            // Send FIN_ACK
            KtnDatagram resend = null;
            int triesLeft = MAX_SEND_ATTEMPTS;
            do {
                //System.out.println("\nSENDING FIN_ACK");
                safelySendAck(disconnectRequest);
                state = State.CLOSE_WAIT;

                // Wait until the ACK should have arrived
                try { Thread.sleep(2000); } catch (InterruptedException e) {  }

                try {
                    resend = receivePacket(true);
                } catch (SocketException e) { }
            } while (isReallyValid(resend) && triesLeft-- > 0);

            // Still got FIN packet to ACK
            if (isReallyValid(resend))
                throw new IOException("Could not close connection; first FIN_ACK never received");

            // Send FIN and receive FIN_ACK
            KtnDatagram fin = constructInternalPacket(Flag.FIN), fin_ack = null;
            fin_ack = safelySendPacket(fin, State.CLOSE_WAIT, State.LAST_ACK);

            if (isReallyValid(fin_ack)) {
                state = State.CLOSED;
            }

        } else {

            // Send FIN and receive FIN_ACK
            KtnDatagram fin = constructInternalPacket(Flag.FIN), fin_ack = null;
            fin_ack = safelySendPacket(fin, State.ESTABLISHED, State.FIN_WAIT_1);

            if (!isReallyValid(fin_ack))
                throw new IOException("Could not close connection; did not receive FIN_ACK");

            state = State.FIN_WAIT_2;

            // Receive FIN
            fin = null;
            int triesLeft = MAX_SEND_ATTEMPTS;
            do {
                System.out.println("\nRECEIVING FIN");
                fin = receivePacket(true);

            } while (!isReallyValid(fin) && triesLeft-- > 0);

            if (!isReallyValid(fin))
                throw new IOException("Failed to close connection; never received final FIN");

            // Send FIN_ACK
            long start = System.currentTimeMillis();
            do {
                if (isReallyValid(fin)) {
                    System.out.println("\nSENDING FIN_ACK");
                    safelySendAck(fin);
                    //sendAck(fin, false);
                }
                fin = receivePacket(true);
            } while (System.currentTimeMillis() - start < TIME_WAIT_DURATION);

            state = State.CLOSED;
        }
    }

    /**
     * Test a packet for transmission errors. This function should only called
     * with data or ACK packets in the ESTABLISHED state.
     *
     * @param packet
     *            Packet to test.
     * @return true if packet is free of errors, false otherwise.
     */
    @Override
    protected boolean isValid(KtnDatagram packet) {
        return (packet != null && packet.getChecksum() == packet.calculateChecksum());
    }

    /**
     * Extra method for thorough validation to avoid rewriting the same code
     * Each state has different requirements for valid packets.
     * This method requires that the {lastValidPacketReceived} and
     * {lastDataPacketSent} variables are used properly.
     * @param packet
     * @return boolean
     */
    private boolean isReallyValid(KtnDatagram packet) {

        if (!isValid(packet)) return false;

        switch (state) {
        case CLOSED:
            return false;
        case LISTEN:
            return (packet.getFlag() == Flag.SYN);
        case SYN_SENT:
            return (packet.getFlag() == Flag.SYN_ACK
                    && packet.getSrc_addr().equals(remoteAddress));
        case SYN_RCVD:
            return (packet.getFlag() == Flag.ACK
                    && packet.getSrc_addr().equals(remoteAddress) && packet.getSrc_port() == remotePort);
        case LAST_ACK:
        case FIN_WAIT_1:
            return (packet.getFlag() == Flag.ACK
                    && packet.getSrc_addr().equals(remoteAddress) && packet.getSrc_port() == remotePort);
        case ESTABLISHED:
            return ((packet.getFlag() == Flag.NONE || packet.getFlag() == Flag.ACK || packet.getFlag() == Flag.FIN)
                    && (packet.getFlag() == Flag.NONE || packet.getAck() == lastDataPacketSent.getSeq_nr())
                    && (packet.getFlag() == Flag.ACK || packet.getSeq_nr() > lastValidPacketReceived.getSeq_nr())
                    && packet.getSrc_addr().equals(remoteAddress) && packet.getSrc_port() == remotePort);
        case FIN_WAIT_2:
            return (packet.getFlag() == Flag.FIN
                    && packet.getSrc_addr().equals(remoteAddress) && packet.getSrc_port() == remotePort);
        case TIME_WAIT:        // Retransmit
        case CLOSE_WAIT:    // Retransmit
            return (packet.getFlag() == Flag.FIN
                    && packet.getSrc_addr().equals(remoteAddress) && packet.getSrc_port() == remotePort);
        }

        return false;
    }
}