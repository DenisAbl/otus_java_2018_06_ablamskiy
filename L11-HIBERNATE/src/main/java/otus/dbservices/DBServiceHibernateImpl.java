package otus.dbservices;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import otus.datasets.AddressDataSet;
import otus.datasets.DataSet;
import otus.datasets.PhoneDataSet;
import otus.datasets.UserDataSet;
import otus.dbservices.DAO.DataSetDao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;

//SET GLOBAL time_zone = '+3:00'

public class DBServiceHibernateImpl implements DBService {

    private final SessionFactory sessionFactory;
    private Properties properties;
    private final String CONFIG_PATH = "src/main/resources/hibernate.properties";
    private final Configuration configuration;


    public DBServiceHibernateImpl() {
        configuration = new Configuration();
        properties = new Properties();

        try {
            properties.load(new FileInputStream(new File(CONFIG_PATH)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        configuration.addProperties(properties);
        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.addAnnotatedClass(PhoneDataSet.class);
        configuration.addAnnotatedClass(AddressDataSet.class);

        sessionFactory = createSessionFactory(configuration);
    }

    private static SessionFactory createSessionFactory(Configuration configuration){
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        StandardServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    public void prepareTable() throws SQLException {

    }

    @Override
    public String getMetaData() {
        try {
            return "Connected to: " + configuration.getProperty("hibernate.connection.url") + "\n" +
                    "Dialect: " + configuration.getProperty("hibernate.dialect") + "\n" +
                    "JDBC driver: " + configuration.getProperty("hibernate.connection.driver_class") + "\n" +
                    "hbm2ddl.auto: " + configuration.getProperty("hibernate.hbm2ddl.auto");
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @Override
    public void saveUsers(String... names) throws SQLException {
        runTransaction(session -> {
            var dao = new DataSetDao(session);
            UserDataSet user;
            for (String name: names){
                user = new UserDataSet();
                user.setName(name);
                dao.save(user);
            }
            return true;
        });
    }

    @Override
    public <T extends DataSet> String getUserName(int id, Class<T> clazz) throws SQLException {
        return runTransaction(session -> {
            var dao = new DataSetDao(session);
            return dao.readUserNameById(id,clazz);
        });
    }

    @Override
    public <T extends DataSet> List<String> getAllNames(Class<T> clazz) throws SQLException {
        return runTransaction(session -> {
            var dao = new DataSetDao(session);
            return dao.readAllNames(clazz);
        });
    }

    @Override
    public void deleteTables() throws SQLException {
        runTransaction(session -> {
            session.createSQLQuery("drop database users").executeUpdate();
            session.createSQLQuery("create database users").executeUpdate();
            return true;
        });
    }

    @Override
    public <T extends DataSet> void save(T user) {
        try (Session session = sessionFactory.openSession()){
            var dao = new DataSetDao(session);
            dao.save(user);
        }
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) {
        return runTransaction(session -> {
            var dataSetDao = new DataSetDao(session);
            return dataSetDao.read(id,clazz);
        });
    }

    @Override
    public <T extends DataSet> List<T> getAllUsers(Class<T> clazz) {
        return runTransaction(session -> {
            var dao = new DataSetDao(session);
            return dao.readAllUsers(clazz);
        });
    }

    private <T> T runTransaction(Function<Session,T> function){
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            T result = function.apply(session);
            transaction.commit();
            return result;
        }
    }

    public void shutdown() {
        sessionFactory.close();
    }
}
