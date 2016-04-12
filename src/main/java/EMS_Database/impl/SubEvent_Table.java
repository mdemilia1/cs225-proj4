package EMS_Database.impl;

import EMS_Database.DoesNotExistException;
import EMS_Database.InitDB;
import static EMS_Database.InitDB.debugLog;
import EMS_Database.InputSubEventData;
import EMS_Database.Interface_SubEventData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;

/**
 *
 * @author mike
 */
public class SubEvent_Table extends InitDB implements Interface_SubEventData {

    private String tableName = "SUBEVENTS";

    ///////////////////////SPECIAL FUNCTIONS///////////////////////////
    /**
     * Inserts a new row into SubEvent Table based on the parameters of
     * InputSubEventData
     *
     * @param subevent the collected data to be inserted
     * @return an int of the UID upon successful creation.
     */
    @Override
    public int createSubEvent(InputSubEventData subevent) {

	try {
	    //Creating Statement
	    PreparedStatement AddAddressStmt = dbConnection.prepareStatement("INSERT INTO SUBEVENTS VALUES(?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
		int column = 0;
	    AddAddressStmt.setString(++column, subevent.getDescription());
	    AddAddressStmt.setString(++column, subevent.getDetails());
	    AddAddressStmt.setString(++column, subevent.getTitle());
	    AddAddressStmt.setInt(++column, subevent.getComplete());
	    AddAddressStmt.setString(++column, subevent.getStreet());
	    AddAddressStmt.setString(++column, subevent.getCity());
	    AddAddressStmt.setString(++column, subevent.getState());
	    AddAddressStmt.setString(++column, subevent.getZipcode());
	    AddAddressStmt.setString(++column, subevent.getCountry());
	    AddAddressStmt.setTimestamp(++column, subevent.getStartTime());
	    AddAddressStmt.setTimestamp(++column, subevent.getEndTime());

	    //Execute Statement
	    return AddAddressStmt.executeUpdate();

	//    for (int uid : currentUIDList(tableName)) {
	//	if (newUID == uid) {
	//	    throw new DoesNotExistException("Problem inserting UID=" + newUID + " into database");
	//	}
	//   }
        // Do we need this? - Tom

	} catch (SQLException sqle) {
	    System.err.println(sqle.getMessage());
		//  debugLog.log(Level.SEVERE, "SUBEVENT table insertion failed. UID={0}", uid);
		//
		//  throw new UpdateException("Error creating sub-event", sqle);
    }



    /**
     * A debug function to display the entire contents of this table
     *
     * @return the entire contents of this table as a string.
     */
    @Override
    public String queryEntireTable() {
	StringBuilder returnQuery = new StringBuilder();
	try {
	    PreparedStatement idQueryStmt = dbConnection.prepareStatement("SELECT * FROM SUBEVENTS");
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
		returnQuery.append(rs.getInt("COMPLETE"));
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
		returnQuery.append("\n");
	    }

	} catch (SQLException sqle) {
	    sqle.printStackTrace();
	    System.exit(1);
	}

	return returnQuery.toString();
    }

    /**
     * This function removes a subevent specified by the UID.
     *
     * @param uid the UID of the subevent to be removed.
     * @return a boolean. true if removal was successful.
     * @throws DoesNotExistException if the uid does not exist in the table.
     */
    @Override
    public void removeSubEvent(int uid) throws DoesNotExistException {
	String table = "SUBEVENTS";
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

    ////////////////////////GETTERS/////////////////////////
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

    /////////////////////SETTERS////////////////////////////
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
}
