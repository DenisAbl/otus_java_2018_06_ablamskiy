package dbservice.handlers;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface StatementHandler {
    void accept(PreparedStatement statement) throws SQLException;
}
