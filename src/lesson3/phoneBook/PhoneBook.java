package lesson3.phoneBook;

import java.util.*;

public class PhoneBook extends Person {
    private static Map<String, ArrayList<Person>> personMap = new HashMap<>();

    public PhoneBook(String name, String phone) {
        super(name, phone);
    }

    public PhoneBook() {
        super();
    }

    public String toString() {
        StringBuilder content = new StringBuilder();
        for (String s : personMap.keySet()) {
            ArrayList<Person> person = personMap.get(s);
            for (int i = 0; i < person.size(); i++) {
                if (i < 1 && person.size() > 1) {
                    content.append(person.get(i).name).append(": ").append(person.get(i).phone).append(", ");
                } else if (person.size() == i + 1 && person.size() != 1) {
                    content.append(person.get(i).phone).append(";\n");
                } else {
                    content.append(person.get(i).name).append(": ").append(person.get(i).phone).append(";\n");
                }
            }
        }
        return content.toString();
    }

    public void add(String name, String phone) {
        if (personMap.containsKey(name)) { //Если такой список доступен по ключу name
            ArrayList<Person> person = personMap.get(name); //
            person.add(new Person(name, phone));//добааляется контакт
        } else {
            ArrayList<Person> person = new ArrayList<>(); //Если ключ отсутсвует в списске
            person.add(new Person(name, phone));//Создаем новый список, кладем туда новый контакт
            personMap.put(name, person);//новый список помещается в хранилище
        }
    }

    public ArrayList<String> get(String name) {
        if (!personMap.containsKey(name)) return null;
        ArrayList<Person> person = personMap.get(name);
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < person.size(); i++) {
            result.add(person.get(i).phone);
        }
        return result;
    }
}
