package EMS_Database.impl;

import EMS_Database.DoesNotExistException;
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

	try {
	    //Creating Statement
	    PreparedStatement AddAddressStmt = dbConnection.prepareStatement("INSERT INTO TASKS VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
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
	    System.err.println(sqle.getMessage()); //seriously bad...
		//  debugLog.log(Level.SEVERE, "TASKS table insertion failed. UID={0}", uid);
		//
		//  throw new UpdateException("Error creating Tasks", sqle);
    }



    /**
     * This function removes a committee specified by the UID.
     *
     * @param uid the UID of the committee to be removed.
     * @return a boolean. true if removal was successful.
     * @throws DoesNotExistException if the uid does not exist in the table.
     */
    @Override
    public void removeTask(int uid) throws DoesNotExistException {
	String table = "TASKS";
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
    public String getDescription(int uid) throws DoesNotExistException {
	return getDBString("DESCRIPTION", tableName, uid);
    }

    @Override
    public String getDetails(int uid) throws DoesNotExistException {
	return getDBString("DETAILS", tableName, uid);
    }

    @Override
    public String getTitle(int uid) throws DoesNotExistException {
	return getDBString("TITLE", tableName, uid);
    }

    @Override
    public String getStreet(int uid) throws DoesNotExistException {
	return getDBString("STREET", tableName, uid);
    }

    @Override
    public String getCity(int uid) throws DoesNotExistException {
	return getDBString("CITY", tableName, uid);
    }

    @Override
    public String getState(int uid) throws DoesNotExistException {
	return getDBString("STATE", tableName, uid);
    }

    @Override
    public String getZipcode(int uid) throws DoesNotExistException {
	return getDBString("ZIPCODE", tableName, uid);
    }

    @Override
    public String getCountry(int uid) throws DoesNotExistException {
	return getDBString("COUNTRY", tableName, uid);
    }

    @Override
    public Timestamp getStartDate(int uid) throws DoesNotExistException {
	return getDBTimestamp("STARTDATE", tableName, uid);
    }

    @Override
    public Timestamp getEndDate(int uid) throws DoesNotExistException {
	return getDBTimestamp("ENDDATE", tableName, uid);
    }

    @Override
    public int getComplete(int uid) throws DoesNotExistException {
	return getDBInt("COMPLETE", tableName, uid);
    }

    @Override
    public ArrayList<Integer> getAuthority(int uid) throws DoesNotExistException {
	return getDBArrayList("MANAGER", tableName, uid);
    }

    /////////////////////////////SETTERS//////////////////////////////
    @Override
    public void setDescription(int uid, String description) throws DoesNotExistException {
	setDBString("DESCRIPTION", tableName, uid, description);
    }

    @Override
    public void setDetails(int uid, String details) throws DoesNotExistException {
	setDBString("DETAILS", tableName, uid, details);
    }

    @Override
    public void setTitle(int uid, String title) throws DoesNotExistException {
	setDBString("TITLE", tableName, uid, title);
    }

    @Override
    public void setStreet(int uid, String street) throws DoesNotExistException {
	setDBString("STREET", tableName, uid, street);
    }

    @Override
    public void setCity(int uid, String city) throws DoesNotExistException {
	setDBString("CITY", tableName, uid, city);
    }

    @Override
    public void setState(int uid, String state) throws DoesNotExistException {
	setDBString("STATE", tableName, uid, state);
    }

    @Override
    public void setZipcode(int uid, String zipcode) throws DoesNotExistException {
	setDBString("ZIPCODE", tableName, uid, zipcode);
    }

    @Override
    public void setCountry(int uid, String country) throws DoesNotExistException {
	setDBString("COUNTRY", tableName, uid, country);
    }

    @Override
    public void setStartDate(int uid, Timestamp time) throws DoesNotExistException {
	setDBTimestamp("STARTDATE", tableName, uid, time);
    }

    @Override
    public void setEndDate(int uid, Timestamp time) throws DoesNotExistException {
	setDBTimestamp("ENDDATE", tableName, uid, time);
    }

    @Override
    public void setComplete(int uid, int complete) throws DoesNotExistException {
	setDBInt("COMPLETE", tableName, uid, complete);
    }

    @Override
    public void setAuthority(int uid, ArrayList<Integer> committeeList) throws DoesNotExistException {
	setDBArrayList("MANAGER", tableName, uid, committeeList);
    }
}