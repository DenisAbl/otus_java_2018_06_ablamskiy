package otus.dbservices;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import otus.datasets.AddressDataSet;
import otus.datasets.DataSet;
import otus.datasets.PhoneDataSet;
import otus.datasets.UserDataSet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class DBServiceHibernateImpl implements DBService {

    private final SessionFactory sessionFactory;
    private Properties properties;


    public DBServiceHibernateImpl() {
        Configuration configuration = new Configuration();
        properties = new Properties();
//        try {
//            properties.load(new FileInputStream(new File("hibernate.properties")));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        configuration.configure("./Hibernateconfig.cfg.xml").addProperties(properties);

        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.addAnnotatedClass(PhoneDataSet.class);
        configuration.addAnnotatedClass(AddressDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/users_db");
        configuration.setProperty("hibernate.connection.username", "root");
        configuration.setProperty("hibernate.connection.password", "Festo000");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        configuration.setProperty("hibernate.connection.useSSL", "false");
        configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");

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
    public <T extends DataSet> void save(T user) {
        try (Session session = sessionFactory.openSession()){
            UserDataSetDAO dao = new UserDataSetDAO(session);
            dao.save(user);
        }
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) {
        return null;
    }

    @Override
    public <T extends DataSet> List<T> getAllUsers() throws SQLException {
        return null;
    }
}
