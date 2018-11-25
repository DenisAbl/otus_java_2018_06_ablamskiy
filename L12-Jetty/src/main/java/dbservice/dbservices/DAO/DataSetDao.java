package dbservice.dbservices.DAO;

import dbservice.datasets.DataSet;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class DataSetDao {

    private Session session;

    public DataSetDao(Session session) {
        this.session = session;
    }

    public <T extends DataSet> void save(T user){
        session.save(user);
    }

    public <T extends DataSet> T read(long id, Class<T> clazz) {
           return session.load(clazz,id);
    }

    public <T extends DataSet> List<T> readAllUsers(Class<T> clazz) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(clazz);
        query.from(clazz);
        return session.createQuery(query).list();
    }

    public <T extends DataSet> List<String> readAllNames(Class<T> clazz) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<String> query = builder.createQuery(String.class);
        Root<T> root = query.from(clazz);
        query.select(root.get("name"));
        return session.createQuery(query).list();
    }

    public <T extends DataSet> String readUserNameById(long id, Class<T> clazz) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<String> query = builder.createQuery(String.class);
        Root<T> root = query.from(clazz);
        query.select(root.get("name")).where(builder.equal(root.get("id"),id));
        return session.createQuery(query).getSingleResult();
    }

    public <T extends DataSet> Long getUsersNumber(Class<T> clazz) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<T> root = query.from(clazz);
        query.select(builder.count(root));
        return session.createQuery(query).getSingleResult();
    }

    public <T extends DataSet>  List<String> getAllLogins(Class<T> clazz) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<String> query = builder.createQuery(String.class);
        Root<T> root = query.from(clazz);
        query.select(root.get("login"));
        return session.createQuery(query).list();
    }

    public <T extends DataSet> T readUserByLogin(String login, Class<T> clazz) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(clazz);
        Root<T> root = query.from(clazz);
        query.select(root).where(builder.equal(root.get("login"),login));
        return session.createQuery(query).getSingleResult();
    }
}
