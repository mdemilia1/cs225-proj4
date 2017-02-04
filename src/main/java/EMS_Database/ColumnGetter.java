package EMS_Database;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ColumnGetter<T> {
    T get(ResultSet rs, String column) throws SQLException;
}
