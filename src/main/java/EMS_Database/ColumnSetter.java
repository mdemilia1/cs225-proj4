package EMS_Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface ColumnSetter {
    void set(PreparedStatement stmt, int argumentID) throws SQLException;
}
