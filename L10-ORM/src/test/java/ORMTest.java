
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import otus.datasets.UserDataSet;
import otus.dbservices.DBService;
import otus.dbservices.MyDBService;

import java.sql.SQLException;
import java.util.List;

public class ORMTest {

     DBService service;

     @Before
    public void setUp(){
        service = new MyDBService(UserDataSet.class);
    }

    @Test
    public void prepareTable(){
        try {
            service.prepareTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void metaDataTest(){
        System.out.println(service.getMetaData());
    }

    @Test
    public void addUsersTest() throws SQLException {
        service.deleteTables();
        service.prepareTable();
        service.addUsers("Denis","Kate","Viktor");
        Assert.assertEquals("addUsers or getUserName methods failure","Kate", service.getUserName(2));

    }

    @Test
    public void deleteTablesTest(){
        try {
            service.deleteTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void loadTest() throws SQLException {
        service.deleteTables();
        service.prepareTable();
        service.addUsers("Denis","Kate","Viktor");
        UserDataSet retrievedDataSet = service.load(3,UserDataSet.class);
        Assert.assertEquals("load method failure", "Viktor", retrievedDataSet.getName());
    }

    @Test
    public void saveTest() throws SQLException {
        service.deleteTables();
        service.prepareTable();
        service.addUsers("Denis","Kate","Viktor");
        UserDataSet newUserDataSet = new UserDataSet("XZ",34,false ,20,40);
        service.save(newUserDataSet);
        UserDataSet retrievedDataSet = service.load(4,UserDataSet.class);
        Assert.assertEquals("save method failure", newUserDataSet,retrievedDataSet);
    }

    @Test
    public void getAllUserTest() throws SQLException {

        service.deleteTables();
        service.prepareTable();
        service.addUsers("Denis","Kate","Viktor");
        List<UserDataSet> dataSetList = service.getAllUsers();
        Assert.assertEquals("getAllUsers failed", "Viktor", dataSetList.get(2).getName());

    }
}
