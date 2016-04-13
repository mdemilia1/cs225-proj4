package EMS_Database.impl;

import EMS_Database.DoesNotExistException;
import auth.AuthorizationException;
import EMS_Database.InitDB;
import static EMS_Database.InitDB.debugLog;
import EMS_Database.InputTask;
import EMS_Database.Interface_TaskData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 *
 * @author mike
 */
public class Tasks_Table extends InitDB implements Interface_TaskData {

    private String tableName = "TASKS";

    ///////////////////////SPECIAL FUNCTIONS///////////////////////////    
    @Override
    public String queryEntireTable() {
	StringBuilder returnQuery = new StringBuilder();
	try {
	    PreparedStatement idQueryStmt = dbConnection.prepareStatement("SELECT * FROM TASKS");
	    ResultSet rs = idQueryStmt.executeQuery();

	    while (rs.next()) {
		returnQuery.append(rs.getString("UID"));
		returnQuery.append(",");
		returnQuery.append(rs.getString("DESCRIPTION"));
		returnQuery.append(",");
		returnQuery.append(rs.getString("DETAILS"));
		returnQuery.append(",");
		returnQuery.append(rs.getString("TITLE"));
		returnQuery.append(",");
		returnQuery.append(rs.getString("STREET"));
		returnQuery.append(",");
		returnQuery.append(rs.getString("CITY"));
		returnQuery.append(",");
		returnQuery.append(rs.getString("STATE"));
		returnQuery.append(",");
		returnQuery.append(rs.getString("ZIPCODE"));
		returnQuery.append(",");
		returnQuery.append(rs.getString("COUNTRY"));
		returnQuery.append(",");
		returnQuery.append(rs.getTimestamp("STARTDATE"));
		returnQuery.append(",");
		returnQuery.append(rs.getTimestamp("ENDDATE"));
		returnQuery.append(",");
		returnQuery.append(rs.getString("COMPLETE"));
		returnQuery.append(",");
		returnQuery.append(stringToList(rs.getString("MANAGER")));
		returnQuery.append("\n");
	    }

	} catch (SQLException sqle) {
	    sqle.printStackTrace();
	    System.exit(1);
	}

	return returnQuery.toString();
    }

    /**
     * Inserts a new task into the Task table based on the parameters specified
     * by InputTask.
     *
     * @param task The collected data to be inserted into the Task table
     * @return an int of the UID of the created task.
     */
    @Override
    public int createTask(InputTask task) {
	int newUID = nextValidUID();

	try {
	    //Creating Statement
	    PreparedStatement AddAddressStmt = dbConnection.prepareStatement("INSERT INTO TASKS VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");
	    AddAddressStmt.setInt(1, newUID);
	    AddAddressStmt.setString(2, task.getDescripton());
	    AddAddressStmt.setString(3, task.getDetails());
	    AddAddressStmt.setString(4, task.getTitle());
	    AddAddressStmt.setString(5, task.getStreet());
	    AddAddressStmt.setString(6, task.getCity());
	    AddAddressStmt.setString(7, task.getState());
	    AddAddressStmt.setString(8, task.getZipcode());
	    AddAddressStmt.setString(9, task.getCountry());
	    AddAddressStmt.setTimestamp(10, task.getStartDate());
	    AddAddressStmt.setTimestamp(11, task.getEndDate());
	    AddAddressStmt.setInt(12, task.getComplete());
	    AddAddressStmt.setString(13, listToString(task.getManager()));

	    //Execute Statement
	    AddAddressStmt.executeUpdate();


	} catch (SQLException sqle) {
	    System.err.println(sqle.getMessage()); //seriously bad...
	} finally {
	    return newUID;
	}
    }

    /**
     * Gets the next valid UID in the Events table
     *
     * @return the next valid UID that should be used.
     */
    @Override
    public int nextValidUID() {
	int newUID = 0;
	try {

	    PreparedStatement idQueryStmt = dbConnection.prepareStatement("SELECT * FROM TASKS");
	    ResultSet rs = idQueryStmt.executeQuery();

	    while (rs.next()) {
		newUID = rs.getInt("UID");
		//System.out.println(newUID);
	    }
	    return (newUID + 1);

	} catch (SQLException sqle) {
	    System.err.println(sqle.getMessage());
	    System.exit(1);
	}
	return newUID; // should not be zero
    }

    /**
     * This function removes a committee specified by the UID.
     *
     * @param uid the UID of the committee to be removed.
     * @return a boolean. true if removal was successful.
     * @throws DoesNotExistException if the uid does not exist in the table.
     */
    @Override
    public void removeTask(int uid) throws DoesNotExistException, AuthorizationException {
	String table = "TASKS";
        Permissions.get().checkPermission(table, null, Operation.DELETE, uid);
	//checking for existance of that uid
	boolean exists = false;
	for (int validID : currentUIDList(table)) {
	    if (validID == uid) {
		exists = true;
		break;
	    }
	}
	//what to do if that uid does not exist
	if (exists == false) {
	    debugLog.log(Level.WARNING, "UID={0} does not exist in {1} table. Error occurred while calling removeEvent", new Object[]{uid, table});
	    throw new DoesNotExistException("check debug log. " + table + " table error.");
	}

	try {
	    PreparedStatement idQueryStmt = dbConnection.prepareStatement("DELETE FROM " + table + " WHERE UID=?");
	    idQueryStmt.setInt(1, uid);
	    idQueryStmt.executeUpdate();

	} catch (SQLException sqle) {
	    System.err.println(sqle.getMessage());
	    System.err.println("Deleting stuff from " + table + " is dangerous...");
	}
    }

    ///////////////////////////GETTERS/////////////////////////////
    @Override
       public String getDescription(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "DESCRIPTION", Operation.VIEW);
        return getDBString("DESCRIPTION", tableName, uid, description);
    }

    @Override
    public String getDetails(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "DETAILS", Operation.VIEW);
	return getDBString("DETAILS", tableName, uid, details);
    }

    @Override
    public String getTitle(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, “TITLE”, Operation.VIEW);
	return getDBString("TITLE", tableName, uid, title);
    }

    @Override
    public String getStreet(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, “STREET”, Operation.VIEW);
	return getDBString("STREET", tableName, uid, street);
    }

    @Override
    public String getCity(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, “CITY”, Operation.VIEW);
	return getDBString("CITY", tableName, uid, city);
    }

    @Override
    public String getState(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, “STATE”, Operation.VIEW);
	return getDBString("STATE", tableName, uid, state);
    }

    @Override
    public String getZipcode(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, “ZIPCODE”, Operation.VIEW);
	return getDBString("ZIPCODE", tableName, uid, zipcode);
    }

    @Override
    public String getCountry(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, “COUNTRY”, Operation.VIEW);
	return getDBString("COUNTRY", tableName, uid, country);
    }

    @Override
    public Timestamp getStartDate(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, “STARTDATE”, Operation.VIEW);
	return getDBTimestamp("STARTDATE", tableName, uid, startdate);
    }

    @Override
    public Timestamp getEndDate(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, “ENDDATE”, Operation.VIEW);
	return getDBTimestamp("ENDDATE", tableName, uid, enddate);
    }

    @Override
    public int getComplete(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, “COMPLETE”, Operation.VIEW);
	return getDBInt("COMPLETE", tableName, uid, complete);
    }

    @Override
    public ArrayList<Integer> getAuthority(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, “MANAGER”, Operation.VIEW);
	return getDBArrayList("MANAGER", tableName, uid, committee list);
    }

    /////////////////////////////SETTERS//////////////////////////////
    @Override
    public void setDescription(int uid, String description) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "DESCRIPTION", Operation.MODIFY);
	setDBString("DESCRIPTION", tableName, uid, description);
    }

    @Override
    public void setDetails(int uid, String details) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, “DETAILS”, Operation.MODIFY);
	setDBString("DETAILS", tableName, uid, details);
    }

    @Override
    public void setTitle(int uid, String title) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, “TITLE”, Operation.MODIFY);
	setDBString("TITLE", tableName, uid, title);
    }

    @Override
    public void setStreet(int uid, String street) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, “STREET”, Operation.MODIFY);
	setDBString("STREET", tableName, uid, street);
    }

    @Override
    public void setCity(int uid, String city) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, “CITY”, Operation.MODIFY);
	setDBString("CITY", tableName, uid, city);
    }

    @Override
    public void setState(int uid, String state) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, “STATE”, Operation.MODIFY);
	setDBString("STATE", tableName, uid, state);
    }

    @Override
    public void setZipcode(int uid, String zipcode) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, “ZIPCODE”, Operation.MODIFY);
	setDBString("ZIPCODE", tableName, uid, zipcode);
    }

    @Override
    public void setCountry(int uid, String country) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, “COUNTRY”, Operation.MODIFY);
	setDBString("COUNTRY", tableName, uid, country);
    }

    @Override
    public void setStartDate(int uid, Timestamp time) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, “STARTDATE”, Operation.MODIFY);
	setDBTimestamp("STARTDATE", tableName, uid, time);
    }

    @Override
    public void setEndDate(int uid, Timestamp time) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, “ENDDATE”, Operation.MODIFY);
	setDBTimestamp("ENDDATE", tableName, uid, time);
    }

    @Override
    public void setComplete(int uid, int complete) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, “COMPLETE”, Operation.MODIFY);
	setDBInt("COMPLETE", tableName, uid, complete);
    }

    @Override
    public void setAuthority(int uid, ArrayList<Integer> committeeList) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, “MANAGER”, Operation.MODIFY);
	setDBArrayList("MANAGER", tableName, uid, committeeList);
    }
}
