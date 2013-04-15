/*
 * Created on Oct 27, 2004
 */
package no.ntnu.fp.net.co;

import java.io.EOFException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TimerTask;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import no.ntnu.fp.net.admin.Log;
import no.ntnu.fp.net.cl.ClException;
import no.ntnu.fp.net.cl.ClSocket;
import no.ntnu.fp.net.cl.KtnDatagram;
import no.ntnu.fp.net.cl.KtnDatagram.Flag;

/**
 * Implementation of the Connection-interface. <br>
 * <br>
 * This class implements the behaviour in the methods specified in the interface
 * {@link Connection} over the unreliable, connectionless network realised in
 * {@link ClSocket}. The base class, {@link AbstractConnection} implements some
 * of the functionality, leaving message passing and error handling to this
 * implementation.
 * 
 * @author Sebjørn Birkeland and Stein Jakob Nordbø
 * @see no.ntnu.fp.net.co.Connection
 * @see no.ntnu.fp.net.cl.ClSocket
 */
public class ConnectionImpl2 extends AbstractConnection {

	private final static int MAX_ATTEMPTS = 5;

	/** Keeps track of the used ports for each server port. */
	private static Map<Integer, Boolean> usedPorts = Collections
			.synchronizedMap(new HashMap<Integer, Boolean>());

	/**
	 * Initialise initial sequence number and setup state machine.
	 * 
	 * @param myPort
	 *            - the local port to associate with this connection
	 */
	public ConnectionImpl2(int myPort) {
		super();
		this.myPort = myPort;
		this.myAddress = getIPv4Address();
	}

	private String getIPv4Address() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return "127.0.0.1";
		}
	}

	/**
	 * Establish a connection to a remote location.
	 * 
	 * @param remoteAddress
	 *            - the remote IP-address to connect to
	 * @param remotePort
	 *            - the remote portnumber to connect to
	 * @throws IOException
	 *             If there's an I/O error.
	 * @throws java.net.SocketTimeoutException
	 *             If timeout expires before connection is completed.
	 * @see Connection#connect(InetAddress, int)
	 */
	public void connect(InetAddress remoteAddress, int remotePort)
			throws IOException, SocketTimeoutException {
		if (this.state != state.CLOSED)
			throw new IllegalStateException(
					"State should be closed before making a connection");

		this.remoteAddress = remoteAddress.getHostAddress();
		this.remotePort = remotePort;

		KtnDatagram syn = this.constructInternalPacket(Flag.SYN);

		try {
			// Send SYN, receive SYN_ACK and send ACK
			this.simplySendPacket(syn);
			KtnDatagram synack = this.receiveAck();
			this.lastValidPacketReceived = synack;
			this.remotePort = synack.getSrc_port();
			Thread.sleep(1000);
			this.sendAck(synack, false);
			
			// Expendable code later -- Don't delete --
			//ClSocket socket = new ClSocket();
			//SendTimer sendtimer = new SendTimer(socket, syn);
		} catch (ClException e) {
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.state = State.ESTABLISHED;
	}

	/**
	 * Listen for, and accept, incoming connections.
	 * 
	 * @return A new ConnectionImpl-object representing the new connection.
	 * @see Connection#accept()
	 */
	
	public Connection accept() throws IOException, SocketTimeoutException {
		// throw new NotImplementedException();

		if (this.state != state.CLOSED) {
			throw new IOException(
					"State has to be closed. Cannot accept connection");
		}
		this.state = state.LISTEN;
		KtnDatagram syn = null;
		while (syn == null) {
			try {
				syn = receivePacket(true);
			} catch (Exception e) {
			}
		}
		this.lastValidPacketReceived = syn;

		ConnectionImpl2 connection = new ConnectionImpl2(createPortNumber());
		connection.remoteAddress = syn.getSrc_addr();
		connection.remotePort = syn.getSrc_port();

		this.state = state.SYN_RCVD;

		KtnDatagram ack = null;
		/*try {
			Thread.sleep(200);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}*/
		try {
			int attemptsLeft = MAX_ATTEMPTS;
			while (ack == null && attemptsLeft >= 1) {
				// Send SYN_ACK and receive ACK
				connection.sendAck(syn, true);
				attemptsLeft--;

				ack = connection.receiveAck();

			}
		} catch (IOException e) {
			e.getStackTrace();
		}

		if (isValid(ack)) {
			connection.state = state.ESTABLISHED;
			connection.lastValidPacketReceived = ack;
			return connection;
		} else {
			this.state = state.CLOSED;
			throw new IOException(
					"Unable to connect. No ACK or SYN_ACK received.");
		}

	}

	private int createPortNumber() {
		int base = 49152, max = 65535;
		Random rn = new Random();
		return rn.nextInt(max - base) + base;
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
		if(this.state != State.ESTABLISHED) {
			throw new IllegalStateException("Cannot send unless connection ESTABLISHED");
		}
		KtnDatagram data = this.constructDataPacket(msg), ack = null;
		int attemptsLeft = MAX_ATTEMPTS;
		// Check if right ACK
		while(attemptsLeft > 0 && ack == null) {
			ack = this.sendDataPacketWithRetransmit(data);
			// Perhaps check SEQNR from data with ACK from ACK + 1
			if (ack != null && ack.getFlag() == Flag.ACK && ack.getAck() == data.getSeq_nr()) {
				this.lastValidPacketReceived = ack;
				this.lastDataPacketSent = data;
				break;
			}
			ack = null;
			attemptsLeft--; 
		}
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
		if(this.state!=State.ESTABLISHED)
			throw new IllegalStateException("Cannot receive if not established");
		
		KtnDatagram dataInn;
		try{
			dataInn=receivePacket(false);
			}
    catch (EOFException e){
    		try {
				Thread.sleep(300);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
            this.sendAck(this.disconnectRequest, false);
            this.state=State.CLOSE_WAIT;
            throw e;
    }
		
		if(!(isValid(dataInn)))
			throw new IOException("Packet not valid");
		this.lastValidPacketReceived=dataInn;
		sendAck(dataInn,false);
		return (String) dataInn.getPayload();
	}

	/**
	 * Close the connection.
	 * 
	 * @see Connection#close()
	 */
	public void close() throws IOException {
		// Has received close-request
		if(this.disconnectRequest != null) {
			try {
				//this.sendAck(disconnectRequest, false);
				// Perhaps wait time here
				KtnDatagram fin = this.constructInternalPacket(Flag.FIN);
				Thread.sleep(310);
				this.simplySendPacket(fin);
				this.receiveAck();
			} catch (ClException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else { // Hasn't received close-request
			if(this.state != State.ESTABLISHED) {
				throw new IllegalStateException("Cannot close if not connected");
			}
			KtnDatagram fin = this.constructInternalPacket(Flag.FIN);
			try {
				Thread.sleep(300);
				this.simplySendPacket(fin);
				this.state = State.FIN_WAIT_1;
				this.receiveAck();
				this.state = State.FIN_WAIT_2;
				this.lastValidPacketReceived = fin;
				KtnDatagram fin_2 = this.receivePacket(false);
				Thread.sleep(300);
				this.sendAck(fin_2, false);
			} catch (ClException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.state = State.CLOSED;	
	}

	/**
	 * Test a packet for transmission errors. This function should only called
	 * with data or ACK packets in the ESTABLISHED state.
	 * 
	 * @param packet
	 *            Packet to test.
	 * @return true if packet is free of errors, false otherwise.
	 */
	
	protected boolean isValid(KtnDatagram packet) {
		// Check if packetSent.ACK == packetReceived.SEQ
		if(this.lastValidPacketReceived.getSeq_nr() + 1 != packet.getSeq_nr())
			return false;
//		// Check if packetSent.SEQ + 1 == packetReceived.ACK
//		if(this.lastDataPacketSent.getSeq_nr() + 1 != packet.getAck())
//			return false;
		// Check checksm
		if(packet.getChecksum() != packet.calculateChecksum())
			return false;
		return true;
	}
}

