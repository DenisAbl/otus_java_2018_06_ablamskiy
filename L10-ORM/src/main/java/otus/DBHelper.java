package otus;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class DBHelper {

    public static Connection getConnection() {

        try {
            Driver driver = (Driver) Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
            DriverManager.registerDriver(driver);
            String url = "jdbc:mysql://" +      //db type
                    "localhost:" +              //host name
                    "3306/" +                   //port
                    "users_db?" +               //db name
                    "user=root&" +              //login
                    "password=Festo000&" +      //password
                    "useSSL=false&" +           //do not use Secure Sockets Layer
                    "serverTimezone=UTC";

            return DriverManager.getConnection(url);

        }catch (IllegalAccessException |
                InstantiationException |
                InvocationTargetException |
                NoSuchMethodException |
                ClassNotFoundException |
                SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
