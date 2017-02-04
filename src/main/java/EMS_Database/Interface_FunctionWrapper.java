package EMS_Database;

import auth.AuthorizationException;
import exception.UpdateException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public interface Interface_FunctionWrapper {

    // DB GETTERS
    String getDBString(String column, int uid) throws DoesNotExistException, AuthorizationException;

    double getDBDouble(String column, int uid) throws DoesNotExistException, AuthorizationException;

    ArrayList<Integer> getDBArrayList(String column, int uid) throws DoesNotExistException, AuthorizationException;

    int getDBInt(String column, int uid) throws DoesNotExistException, AuthorizationException;

    Timestamp getDBTimestamp(String column, int uid) throws DoesNotExistException, AuthorizationException;

    // DB SETTERS
    void setDBString(String column, int uid, String newValue) throws DoesNotExistException, UpdateException, AuthorizationException;

    void setDBDouble(String column, int uid, double newValue) throws DoesNotExistException, UpdateException, AuthorizationException;

    void setDBArrayList(String column, int uid, List<Integer> newValue) throws DoesNotExistException, UpdateException, AuthorizationException;

    void setDBInt(String column, int uid, int newValue) throws DoesNotExistException, UpdateException, AuthorizationException;

    void setDBTimestamp(String column, int uid, Timestamp newValue) throws DoesNotExistException, UpdateException, AuthorizationException;
}
