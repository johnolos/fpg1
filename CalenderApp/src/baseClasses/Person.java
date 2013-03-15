package baseClasses;

import java.io.Serializable;


 
public class Person implements Comparable, Serializable {
       
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
        
		public int compareTo(Person arg0) {
			if(this.lastname.compareTo(arg0.lastname) == 1){
				return 1;
			} else if (this.lastname.compareTo(arg0.lastname) == 0) {
				return this.firstName.compareTo(arg0.firstName);
			}
			return -1;
		}

		@Override
		public int compareTo(Object o) {
			// TODO Auto-generated method stub
			return 0;
		}
}