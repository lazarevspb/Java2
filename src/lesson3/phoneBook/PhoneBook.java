package lesson3.phoneBook;

import java.util.*;

public class PhoneBook extends Person{
   private static Map<String, Person> personMap = new HashMap<>();

    public PhoneBook(){
    }

    public String toString(String key) {
        return key + " =  " + personMap.get(key) + " ";
    }

    public String toString() {
        String content= "";
        for (String s : personMap.keySet()) {
            content += s + " = " + personMap.get(s) + "\n";
        }
        
        return content;
    }

    public void add(String name, String phone) {
                if (!(personMap.containsKey(name))) {
       Person person = new Person();
        personMap.put(name, person.setPhoneNumberListPerson(phone));
        } else {
                    personMap.get(name).setPhoneNumberList(phone);
                }
    }
    public String get(String name) {
        return toString(name);
    }
}
