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

    @Column(name = "login", nullable=false, unique=true)
    private String login;

    @OneToOne(cascade = CascadeType.ALL)
    private AddressDataSet address;

    @OneToMany(cascade = CascadeType.ALL)
    private List<PhoneDataSet> phoneNumbers;

    public  UserDataSet(){}

    public UserDataSet(String login, String name, int age, AddressDataSet address, List<PhoneDataSet> phoneNumbers) {
        this.login = login;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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
                ", login='" + login + '\'' +
                ", address=" + address +
                ", phoneNumbers=" + phoneNumbers +
                '}';
    }
}
