package otus.dbservices.DAO;

import org.hibernate.Session;
import otus.datasets.DataSet;

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
        CriteriaQuery<T> criteria = builder.createQuery(clazz);
        criteria.from(clazz);
        return session.createQuery(criteria).list();
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
}
