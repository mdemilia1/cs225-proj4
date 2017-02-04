package EMS_Database.impl;

import EMS_Database.DoesNotExistException;
import EMS_Database.InitDB;
import EMS_Database.InputTask;
import EMS_Database.Interface_TaskData;
import auth.AuthorizationException;
import auth.Operation;
import auth.Permissions;
import exception.UpdateException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * @author mike
 */
public class Tasks_Table extends InitDB implements Interface_TaskData {
    private static final String tableName = "TASKS";

    @Override
    protected String getTableName() {
        return tableName;
    }

    /**
     * Inserts a new task into the Task table based on the parameters specified
     * by InputTask.
     *
     * @param task The collected data to be inserted into the Task table
     * @return an int of the UID of the created task.
     */
    @Override
    public int createTask(InputTask task) throws AuthorizationException, UpdateException {
        String table = tableName;
        Permissions.get().checkPermission(table, null, Operation.CREATE);

        try {
            //Creating Statement
            PreparedStatement AddAddressStmt = dbConnection.prepareStatement("INSERT INTO TASKS VALUES(NULL,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            int column = 0;
            AddAddressStmt.setString(++column, task.getDescripton());
            AddAddressStmt.setString(++column, task.getDetails());
            AddAddressStmt.setString(++column, task.getTitle());
            AddAddressStmt.setString(++column, task.getStreet());
            AddAddressStmt.setString(++column, task.getCity());
            AddAddressStmt.setString(++column, task.getState());
            AddAddressStmt.setString(++column, task.getZipcode());
            AddAddressStmt.setString(++column, task.getCountry());
            AddAddressStmt.setTimestamp(++column, task.getStartDate());
            AddAddressStmt.setTimestamp(++column, task.getEndDate());
            AddAddressStmt.setInt(++column, task.getComplete());
            AddAddressStmt.setString(++column, listToString(task.getManager()));

            //Execute Statement
            return AddAddressStmt.executeUpdate();
        } catch (SQLException sqle) {
            throw new UpdateException("Error creating Tasks", sqle);
        }
    }


    /**
     * This function removes a committee specified by the UID.
     *
     * @param uid the UID of the committee to be removed.
     * @return a boolean. true if removal was successful.
     * @throws DoesNotExistException if the uid does not exist in the table.
     */
    @Override
    public void removeTask(int uid) throws DoesNotExistException, AuthorizationException, UpdateException {
        remove(uid);
    }

    ///////////////////////////GETTERS/////////////////////////////
    @Override
    public String getDescription(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "DESCRIPTION", Operation.VIEW);
        return getDBString("DESCRIPTION", uid);
    }

    @Override
    public String getDetails(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "DETAILS", Operation.VIEW);
        return getDBString("DETAILS", uid);
    }

    @Override
    public String getTitle(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "TITLE", Operation.VIEW);
        return getDBString("TITLE", uid);
    }

    @Override
    public String getStreet(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "STREET", Operation.VIEW);
        return getDBString("STREET", uid);
    }

    @Override
    public String getCity(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "CITY", Operation.VIEW);
        return getDBString("CITY", uid);
    }

    @Override
    public String getState(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "STATE", Operation.VIEW);
        return getDBString("STATE", uid);
    }

    @Override
    public String getZipcode(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "ZIPCODE", Operation.VIEW);
        return getDBString("ZIPCODE", uid);
    }

    @Override
    public String getCountry(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "COUNTRY", Operation.VIEW);
        return getDBString("COUNTRY", uid);
    }

    @Override
    public Timestamp getStartDate(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "STARTDATE", Operation.VIEW);
        return getDBTimestamp("STARTDATE", uid);
    }

    @Override
    public Timestamp getEndDate(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "ENDDATE", Operation.VIEW);
        return getDBTimestamp("ENDDATE", uid);
    }

    @Override
    public int getComplete(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "COMPLETE", Operation.VIEW);
        return getDBInt("COMPLETE", uid);
    }

    @Override
    public ArrayList<Integer> getAuthority(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "MANAGER", Operation.VIEW);
        return getDBArrayList("MANAGER", uid);
    }

    /////////////////////////////SETTERS//////////////////////////////
    @Override
    public void setDescription(int uid, String description) throws DoesNotExistException, AuthorizationException, UpdateException {
        Permissions.get().checkPermission(tableName, "DESCRIPTION", Operation.MODIFY);
        setDBString("DESCRIPTION", uid, description);
    }

    @Override
    public void setDetails(int uid, String details) throws DoesNotExistException, AuthorizationException, UpdateException {
        Permissions.get().checkPermission(tableName, "DETAILS", Operation.MODIFY);
        setDBString("DETAILS", uid, details);
    }

    @Override
    public void setTitle(int uid, String title) throws DoesNotExistException, AuthorizationException, UpdateException {
        Permissions.get().checkPermission(tableName, "TITLE", Operation.MODIFY);
        setDBString("TITLE", uid, title);
    }

    @Override
    public void setStreet(int uid, String street) throws DoesNotExistException, AuthorizationException, UpdateException {
        Permissions.get().checkPermission(tableName, "STREET", Operation.MODIFY);
        setDBString("STREET", uid, street);
    }

    @Override
    public void setCity(int uid, String city) throws DoesNotExistException, AuthorizationException, UpdateException {
        Permissions.get().checkPermission(tableName, "CITY", Operation.MODIFY);
        setDBString("CITY", uid, city);
    }

    @Override
    public void setState(int uid, String state) throws DoesNotExistException, AuthorizationException, UpdateException {
        Permissions.get().checkPermission(tableName, "STATE", Operation.MODIFY);
        setDBString("STATE", uid, state);
    }

    @Override
    public void setZipcode(int uid, String zipcode) throws DoesNotExistException, AuthorizationException, UpdateException {
        Permissions.get().checkPermission(tableName, "ZIPCODE", Operation.MODIFY);
        setDBString("ZIPCODE", uid, zipcode);
    }

    @Override
    public void setCountry(int uid, String country) throws DoesNotExistException, AuthorizationException, UpdateException {
        Permissions.get().checkPermission(tableName, "COUNTRY", Operation.MODIFY);
        setDBString("COUNTRY", uid, country);
    }

    @Override
    public void setStartDate(int uid, Timestamp time) throws DoesNotExistException, AuthorizationException, UpdateException {
        Permissions.get().checkPermission(tableName, "STARTDATE", Operation.MODIFY);
        setDBTimestamp("STARTDATE", uid, time);
    }

    @Override
    public void setEndDate(int uid, Timestamp time) throws DoesNotExistException, AuthorizationException, UpdateException {
        Permissions.get().checkPermission(tableName, "ENDDATE", Operation.MODIFY);
        setDBTimestamp("ENDDATE", uid, time);
    }

    @Override
    public void setComplete(int uid, int complete) throws DoesNotExistException, AuthorizationException, UpdateException {
        Permissions.get().checkPermission(tableName, "COMPLETE", Operation.MODIFY);
        setDBInt("COMPLETE", uid, complete);
    }

    @Override
    public void setAuthority(int uid, ArrayList<Integer> committeeList) throws DoesNotExistException, AuthorizationException, UpdateException {
        Permissions.get().checkPermission(tableName, "MANAGER", Operation.MODIFY);
        setDBArrayList("MANAGER", uid, committeeList);
    }
}
