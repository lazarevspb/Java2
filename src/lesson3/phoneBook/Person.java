package lesson3.phoneBook;

import java.util.ArrayList;

public class Person {
  private ArrayList<String> phoneNumberList = new ArrayList<>(5);

    public void setPhoneNumberList(String phoneNumber) {
        this.phoneNumberList.add(phoneNumber);
    }

    public Person setPhoneNumberListPerson(String phoneNumber) {
       phoneNumberList.add(phoneNumber);
        return this;
    }

    @Override
    public String toString() {
        return phoneNumberList.toString();
    }
}
