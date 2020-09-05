package lesson3.phoneBook;

import java.util.ArrayList;

public class Person {
    private ArrayList<String> phoneNumberList = new ArrayList<>(5);
    public String name;
    public String phone;

    public Person(String name, String phone, ArrayList<String> phoneNumberList) {
        this.name = name;
        this.phone = phone;
        this.phoneNumberList = phoneNumberList;
    }

    public Person(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public Person() {
    }

    @Override
    public String toString() {
        return phoneNumberList.toString();
    }
}
