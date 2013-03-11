package baseClasses;

import org.joda.time.DateTime;

public class Alarm {
	DateTime alarm;
	
	
	// Comment her 2q83y42
	//andreaaaaa
	//henriiiiiik
	
	
	Alarm(DateTime time) {
		this.alarm = time;
	}
	
	public void alarmUpdate(DateTime time){
		this.alarm=time;
		// Denne metoden er digg
		this.alarm = null;
	}
	
	public void fireAlarmProperty() {
		System.out.println("Hell");
	}
	
}
