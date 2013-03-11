package baseClasses;

public class Person {
	
	// Private 
	
	private String username;
	private String email;
	
	Person(String username, String email) {
		this.username = username;
		this.email = email;
	}

	@Override
	public String toString() {
		return this.username;
	}
	
	
	
}
