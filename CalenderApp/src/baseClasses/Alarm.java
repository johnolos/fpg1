package baseClasses;

import org.joda.time.DateTime;

public class Alarm {
	DateTime alarm;
	
	
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
	
	public boolean isIdiot(){
		return true;
	}
	
}
