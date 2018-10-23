package otus.dbservices;

import otus.datasets.DataSet;

import java.sql.SQLException;
import java.util.List;

public class DBServiceHibernateImpl implements DBService {

    @Override
    public void prepareTable() throws SQLException {

    }

    @Override
    public String getMetaData() {
        return null;
    }

    @Override
    public void addUsers(String... names) throws SQLException {

    }

    @Override
    public String getUserName(int id) throws SQLException {
        return null;
    }

    @Override
    public List<String> getAllNames() throws SQLException {
        return null;
    }

    @Override
    public void deleteTables() throws SQLException {

    }

    @Override
    public <T extends DataSet> List<T> getAllUsers() throws SQLException {
        return null;
    }

    @Override
    public <T extends DataSet> void save(T user) {

    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) {
        return null;
    }
}
