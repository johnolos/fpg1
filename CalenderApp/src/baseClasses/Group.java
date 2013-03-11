package baseClasses;
 
import java.util.ArrayList;
 
public class Group {
        private String name;
        private ArrayList<Person> members=new ArrayList<Person>();
 
        // Version 2
        // awe9j0q3
        ///kulti
       
        Group(String name){
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