package EMS_Database;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author mike
 */
public interface Interface_FunctionWrapper {

    // DB GETTERS
    public String getDBString(String query, String table, int uid) throws DoesNotExistException;

    public double getDBDouble(String query, String table, int uid) throws DoesNotExistException;

    public ArrayList<Integer> getDBArrayList(String query, String table, int uid) throws DoesNotExistException;

    public int getDBInt(String query, String table, int uid) throws DoesNotExistException;

    public Timestamp getDBTimestamp(String query, String table, int uid) throws DoesNotExistException;

    // DB SETTERS
    public void setDBString(String query, String table, int uid, String newValue) throws DoesNotExistException;

    public void setDBDouble(String query, String table, int uid, double newValue) throws DoesNotExistException;

    public void setDBArrayList(String query, String table, int uid, ArrayList<Integer> newValue) throws DoesNotExistException;

    public void setDBInt(String query, String table, int uid, int newValue) throws DoesNotExistException;

    public void setDBTimestamp(String query, String table, int uid, Timestamp newValue) throws DoesNotExistException;
}
