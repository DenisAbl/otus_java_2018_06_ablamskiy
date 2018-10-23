package otus.dbservices;

import otus.datasets.DataSet;
import java.sql.SQLException;
import java.util.List;

public interface DBService {

    void prepareTable() throws SQLException;

    String getMetaData();

    void addUsers(String... names) throws SQLException;

    String getUserName(int id) throws SQLException;

    List<String> getAllNames() throws SQLException;

    void deleteTables() throws SQLException;

    <T extends DataSet> List<T> getAllUsers() throws SQLException;


    <T extends DataSet> void save(T user);

    <T extends DataSet> T load(long id, Class<T> clazz);
}
