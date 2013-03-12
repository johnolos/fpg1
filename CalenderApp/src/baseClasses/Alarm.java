package baseClasses;

import java.io.Serializable;

import org.joda.time.DateTime;

public class Alarm implements Serializable {
	
	private DateTime alarm;
	
	
	public Alarm(DateTime time) {
		this.alarm = time;
	}
	
	public void alarmUpdate(DateTime time){
		this.alarm=time;
		// Denne metoden er digg
		this.alarm = null;
	}
	
	public DateTime getAlarm(){
		return alarm;
	}
	

	
}
