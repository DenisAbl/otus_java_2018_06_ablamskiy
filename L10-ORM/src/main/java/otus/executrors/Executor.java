package otus.executrors;

import otus.datasets.DataSet;
import otus.handlers.ResultSetHandler;
import otus.handlers.StatementHandler;

import java.sql.SQLException;


public interface Executor {



    <T> T execQuery(String query, ResultSetHandler<T> resultSetHandler) throws SQLException;

    void execUpdate(String query, StatementHandler statement) throws SQLException;
}
