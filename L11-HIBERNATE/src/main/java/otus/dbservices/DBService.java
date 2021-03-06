package otus.dbservices;

import otus.datasets.DataSet;

import java.sql.SQLException;
import java.util.List;

public interface DBService {

    void prepareTable() throws SQLException;

    String getMetaData();

    void saveUsers(String... names) throws SQLException;

    <T extends DataSet> String getUserName(int id, Class<T> clazz) throws SQLException;

    <T extends DataSet> List<String> getAllNames(Class<T> clazz) throws SQLException;

    void deleteTables() throws SQLException;

    <T extends DataSet> void save(T user);

    <T extends DataSet> T load(long id, Class<T> clazz);

    <T extends DataSet> List<T> getAllUsers(Class<T> clazz) throws SQLException;;
}
