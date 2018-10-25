package otus.dbservices;

import org.hibernate.Session;
import otus.datasets.DataSet;

public class UserDataSetDAO {

    private Session session;

    UserDataSetDAO(Session session) {
        this.session = session;
    }

    <T extends DataSet> void save(T user){
        session.save(user);
    }
}
