package dbservice.executrors;

import dbservice.handlers.ResultSetHandler;
import dbservice.handlers.StatementHandler;

import java.sql.SQLException;


public interface Executor {



    <T> T execQuery(String query, ResultSetHandler<T> resultSetHandler) throws SQLException;

    void execUpdate(String query, StatementHandler statement) throws SQLException;
}
