package otus.executrors;

import otus.handlers.ResultSetHandler;
import otus.handlers.StatementHandler;


import java.sql.*;


public class OrmExecutor implements Executor{

    private final Connection connection;

    public OrmExecutor(Connection connection) {
        this.connection = connection;
    }

    @Override
    public <T> T execQuery(String query, ResultSetHandler<T> resultSetHandler) throws SQLException {
        try(Statement statement = connection.createStatement()){
            statement.execute(query);
            ResultSet result = statement.getResultSet();
            return resultSetHandler.handle(result);
        }
    }

    public void execUpdate(String query, StatementHandler statement) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(query))
            { statement.accept(preparedStatement); }
    }
}
