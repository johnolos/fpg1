package baseClasses;
 
public class Person {
       
        // Private
       
        private String username;
        private String email;
        private String firstName;
        private String lastname;
       
        public Person(String username, String email, String firstName, String lastName) {
                this.username = username;
                this.email = email;
                this.firstName = firstName;
                this.lastname = lastName;
        }
 
        public String getFirstName() {
			return firstName;
        }

		public String getLastname() {
			return lastname;
		}

		@Override
        public String toString() {
                return this.username;
        }
 
        public String getUsername() {
                return username;
        }
 
        public String getEmail() {
                return email;
        }
}