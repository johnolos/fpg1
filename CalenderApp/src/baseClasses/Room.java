package baseClasses;

import java.io.Serializable;

public class Room implements Serializable{
	
	// Version 2232
	private int capacity;
	private String name;
	
	public Room(int capacity, String name){
		this.capacity=capacity;
		this.name=name;
	}

	public int getCapacity() {
		return capacity;
	}

	public String getName() {
		return name;
	}
	
	
}
