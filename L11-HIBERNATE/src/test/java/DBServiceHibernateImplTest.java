import org.junit.Before;
import org.junit.Test;
import otus.datasets.AddressDataSet;
import otus.datasets.PhoneDataSet;
import otus.datasets.UserDataSet;
import otus.dbservices.DBService;
import otus.dbservices.DBServiceHibernateImpl;

import java.util.ArrayList;

public class DBServiceHibernateImplTest {

    DBService service;

    @Before
    public void setUp() {
        service = new DBServiceHibernateImpl();
    }

    @Test
    public void saveTest(){
        service.save(new UserDataSet("John Doe",34,new AddressDataSet("Leninskiy,100"),new ArrayList<PhoneDataSet>()));
    }

}
