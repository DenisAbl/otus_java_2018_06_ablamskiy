package otus.datasets;

import java.util.Objects;

public class UserDataSet extends DataSet {

    private String name;
    private int age;
    private AddressDataSet address;
    private PhoneDataSet phoneNumber;

    public  UserDataSet(){}

    public UserDataSet(String name, int age, AddressDataSet address, PhoneDataSet phoneNumber) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public AddressDataSet getAddress() {
        return address;
    }

    public void setAddress(AddressDataSet address) {
        this.address = address;
    }

    public PhoneDataSet getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneDataSet phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "UserDataSet{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address=" + address +
                ", phoneNumber=" + phoneNumber +
                '}';
    }
}
