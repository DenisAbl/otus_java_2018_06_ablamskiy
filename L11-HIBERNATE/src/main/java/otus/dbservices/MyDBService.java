package otus.dbservices;

import otus.DBHelper;
import otus.datasets.DataSet;
import otus.executrors.Executor;
import otus.executrors.OrmExecutor;
import otus.visitor.FieldsVisitor;
import otus.visitor.Visitor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MyDBService implements DBService {

    private Class userClass;
    private final Connection connection;
    private Map<Field,String> columns;

    public <T extends DataSet> MyDBService(Class<T> clazz) {
        this.connection = DBHelper.getConnection();
        userClass = clazz;
        Visitor visitor = new FieldsVisitor();
        columns = visitor.dispatch(userClass);
    }

    @Override
    public String getMetaData() {
        try {
            return "Connected to: " + getConnection().getMetaData().getURL() + "\n" +
                    "DB name: " + getConnection().getMetaData().getDatabaseProductName() + "\n" +
                    "DB version: " + getConnection().getMetaData().getDatabaseProductVersion() + "\n" +
                    "Driver: " + getConnection().getMetaData().getDriverName();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @Override
    public void prepareTable() throws SQLException {
        final String[] tempQuery = {"create table if not exists %s (id bigint(20) not null auto_increment, PRIMARY KEY (id), "};
        columns.forEach((field,columnLine)-> {
            if (!field.getName().equals("id")){tempQuery[0] += columnLine;}
        });
        String query = String.format(tempQuery[0].substring(0,tempQuery[0].length()-2) + ")", userClass.getSimpleName());
        Executor executor = new OrmExecutor(getConnection());
        executor.execQuery(query, resultSet -> null);

    }

    @Override
    public void addUsers(String... names) throws SQLException {
        Executor executor = new OrmExecutor(getConnection());
        String query = String.format("insert into %s (name) values(?)",userClass.getSimpleName());
        executor.execUpdate(query, statement -> {
            for(String name : names) {
                statement.setString(1,name);
                statement.execute();
            }
        });
    }

    @Override
    public <T extends DataSet> List<T> getAllUsers() throws SQLException {
        Executor executor = new OrmExecutor(getConnection());
        String query = String.format("select * from %s", userClass.getSimpleName());
        List<T> usersList = new ArrayList<>();
        return executor.execQuery(query,resultSet -> {

            while(!resultSet.isLast()){
                resultSet.next();
                usersList.add(getUser(resultSet, (Class<T>) userClass));
            }
            return usersList;
        });
    }

    @Override
    public String getUserName(int id) throws SQLException {
        Executor executor = new OrmExecutor(getConnection());
        String query = String.format("select name from %s where id=%s",userClass.getSimpleName(),id);
        return executor.execQuery(query, resultSet -> {
            resultSet.next();
            return resultSet.getString("name");
        });
    }

    @Override
    public List<String> getAllNames() throws SQLException {
        Executor executor = new OrmExecutor(getConnection());
        String query = String.format("select name from %s",userClass.getSimpleName());
        return executor.execQuery(query, resultSet -> {
            List<String> nameList = new ArrayList<>();
            while(!resultSet.isLast()){
                resultSet.next();
                nameList.add(resultSet.getString("name"));
            }
            return nameList;
        });
    }

    @Override
    public void deleteTables() throws SQLException {
        Executor executor = new OrmExecutor(getConnection());
        String query = "drop table if exists " + userClass.getSimpleName();
        executor.execQuery(query, result -> null);
     }

    @Override
    public <T extends DataSet> void save(T user) {
        Executor executor = new OrmExecutor(getConnection());
        String query = "insert into %s (%s) values (%s)";

        String columnNames = columns.keySet().stream()
                .map(Field::getName)
                .filter(f->!f.equals("id"))
                .collect(Collectors.joining( ","));

        String columnValues = columns.keySet().stream()
                .filter(field -> !field.getName().equals("id"))
                .map(field -> {
                    Object value = "";
                    try {
                        field.setAccessible(true);
                        value = field.get(user);
                        if (value instanceof String) value = "'" + value+ "'";
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    finally {
                        return value.toString();
                    }
                })
                .collect(Collectors.joining( ","));

        query = String.format(query,userClass.getSimpleName(),columnNames,columnValues);

        try {
            executor.execUpdate(query, PreparedStatement::execute);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) {
        T dataSet = null;
        try {
            Executor executor = new OrmExecutor(getConnection());
            String query = String.format("select * from %s where id=%s", userClass.getSimpleName(), id);
            dataSet = executor.execQuery(query, resultSet -> {
                resultSet.next();
                return getUser(resultSet, clazz);
            });
        } catch (SQLException e){
            e.printStackTrace();
        }
        return dataSet;
    }

    private <T extends DataSet> T getUser(ResultSet resultSet, Class<T> clazz)  {
        var ref = new Object() {
            T dataSet = null;
        };
        try {
            ref.dataSet = clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        columns.keySet().forEach(field -> {
                try {
                    Object fieldValue = resultSet.getObject(field.getName());
                    if (fieldValue != null){
                        field.set(ref.dataSet,fieldValue);}
                } catch (IllegalAccessException | SQLException e) {
                    e.printStackTrace();
                }
        });
        return ref.dataSet;
    }

    private Connection getConnection(){
        return connection;
    }


}
