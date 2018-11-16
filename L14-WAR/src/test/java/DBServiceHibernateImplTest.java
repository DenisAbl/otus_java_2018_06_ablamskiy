import dbservice.datasets.PhoneDataSet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import dbservice.datasets.AddressDataSet;
import dbservice.datasets.UserDataSet;
import dbservice.dbservices.DBService;
import dbservice.dbservices.DBServiceHibernateImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DBServiceHibernateImplTest {

    DBService service;

    @Before
    public void setUp() throws SQLException {
        service = new DBServiceHibernateImpl();
    }

    @Test
    public void saveTest(){
        List<PhoneDataSet> list = new ArrayList<>();
        list.add(new PhoneDataSet("2-12-85-06"));
        service.save(new UserDataSet("John Doe","John Doe",34,new AddressDataSet("Leninskiy,100"),new ArrayList<>()));
        service.save(new UserDataSet("Sam One","Sam One",25,new AddressDataSet("Aeroport,123"),new ArrayList<>()));
        Assert.assertEquals("save failed","Sam One", service.load(2,UserDataSet.class).getName());
    }

    @Test
    public void loadTest(){
        service.save(new UserDataSet("John Doe","John Doe",34,new AddressDataSet("Leninskiy,100"),new ArrayList<>()));
        service.save(new UserDataSet("Sam One","Sam One",25,new AddressDataSet("Aeroport,123"),new ArrayList<>()));
        UserDataSet loadedUserDataSet = service.load(1,UserDataSet.class);
        Assert.assertEquals("load failre", 34, loadedUserDataSet.getAge());
    }

    @Test
    public void getAllUsersTest() throws SQLException {
        service.save(new UserDataSet("John Doe","John Doe",34,new AddressDataSet("Leninskiy,100"),new ArrayList<>()));
        service.save(new UserDataSet("Sam One","Sam One",25,new AddressDataSet("Aeroport,123"),new ArrayList<>()));
        Assert.assertEquals("getAllUsers failure", 2, service.getAllUsers(UserDataSet.class).size());
        Assert.assertEquals("getAllUsers failure", "Aeroport,123", service.getAllUsers(UserDataSet.class).get(1).getAddress().getStreet());
    }

    @Test
    public void deleteTablesTest() throws SQLException {
        service.deleteTables();
    }

    @Test
    public void getUserNameTest() throws SQLException {
        service.saveUsers("Boris","Jake","Otto");
        Assert.assertEquals("","Jake" ,service.getUserName(2, UserDataSet.class));
    }

    @Test
    public void saveUsersTest() throws SQLException {
        service.saveUsers("Boris","Jake","Otto");
        Assert.assertEquals("saveUsers failure","Jake",service.getUserName(2, UserDataSet.class));
    }

    @Test
    public void getMetaDataTest(){
        System.out.println(service.getMetaData());
    }

    @Test
    public void readAllNamesTest() throws SQLException {
        String[]  names = {"Boris","Jake","Otto"};
        service.saveUsers(names);
        Assert.assertEquals("getAllNames failure", Arrays.asList(names), service.getAllNames(UserDataSet.class));
    }
}
