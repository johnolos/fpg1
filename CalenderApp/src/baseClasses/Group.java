package baseClasses;
 
import java.io.Serializable;
import java.util.ArrayList;
 
public class Group implements Serializable {
        private String name;
        private ArrayList<Person> members=new ArrayList<Person>();
 
        public Group(String name){
                this.name=name;
        }
       
        public String getName(){
                return this.name;
        }
       
        public void addMember(Person member) {
                members.add(member);
        }
       
        public void removeMember(Person member){
                members.remove(member);
        }
 
}