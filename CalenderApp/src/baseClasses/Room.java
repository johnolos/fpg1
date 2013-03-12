package baseClasses;

public class Room {
	
	// Version 2232
	private int capacity;
	private String name;
	
	Room(int capacity, String name){
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
