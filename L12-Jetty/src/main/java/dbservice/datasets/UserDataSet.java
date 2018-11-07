package dbservice.datasets;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "UserDataSet")
public class UserDataSet extends DataSet {

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @OneToOne(cascade = CascadeType.ALL)
    private AddressDataSet address;

    @OneToMany(cascade = CascadeType.ALL)
    private List<PhoneDataSet> phoneNumbers;

    public  UserDataSet(){}

    public UserDataSet(String name, int age, AddressDataSet address, List<PhoneDataSet> phoneNumbers) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.phoneNumbers = phoneNumbers;
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

    public List<PhoneDataSet> getPhoneNumber() {
        return phoneNumbers;
    }

    public void setPhoneNumber(List<PhoneDataSet> phoneNumber) {
        this.phoneNumbers = phoneNumber;
    }

    @Override
    public String toString() {
        return "UserDataSet{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address=" + address +
                ", phoneNumber=" + phoneNumbers +
                '}';
    }
}
