package EMS_Database.impl;

import EMS_Database.DoesNotExistException;
import EMS_Database.InitDB;
import static EMS_Database.InitDB.debugLog;
import EMS_Database.Interface_EventData;
import EMS_Database.InputEventData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * A class to control the Events Data.
 *
 * @author mike
 */
public class Events_Table extends InitDB implements Interface_EventData {

    private static String tableName = "EVENTS";

    /////////////////////SPECIAL FUNCTIONS///////////////////////////    
    /**
     * Input a new event into the events using InputEvent class to create a
     * valid input object
     *
     * @param event of type InputEvent for row insertion.
     * @return the UID of the created event.
     */
    @Override
    public int createEvent(InputEventData event) {
        int newUID = nextValidUID();

        try {
            //Creating Statement
            PreparedStatement AddAddressStmt = dbConnection.prepareStatement("INSERT INTO EVENTS VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            AddAddressStmt.setInt(1, newUID);
            AddAddressStmt.setString(2, event.getDescription());
            AddAddressStmt.setString(3, event.getDetails());
            AddAddressStmt.setString(4, event.getTitle());
            AddAddressStmt.setTimestamp(5, event.getStartDate());
            AddAddressStmt.setTimestamp(6, event.getEndDate());
            AddAddressStmt.setInt(7, event.getComplete());
            AddAddressStmt.setString(8, event.getStreet());
            AddAddressStmt.setString(9, event.getCity());
            AddAddressStmt.setString(10, event.getState());
            AddAddressStmt.setString(11, event.getZipcode());
            AddAddressStmt.setString(12, event.getCountry());
            AddAddressStmt.setString(13, listToString(event.getOrganizerList())); //inserted as a string
            AddAddressStmt.setString(14, listToString(event.getSubEventList())); //inserted as a string
            AddAddressStmt.setString(15, listToString(event.getParticipantList())); //inserted as a string
            AddAddressStmt.setString(16, listToString(event.getCommittee())); //inserted as a string

            //Execute Statement
            AddAddressStmt.executeUpdate();

        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        } finally {
            return newUID;
        }
    }

    /**
     * Gets the next vaild UID in the Events table
     *
     * @return the next valid UID that should be used.
     */
    @Override
    public int nextValidUID() {
        int newUID = 0;
        try {

            PreparedStatement idQueryStmt = dbConnection.prepareStatement("SELECT * FROM EVENTS");
            ResultSet rs = idQueryStmt.executeQuery();

            while (rs.next()) {
                newUID = rs.getInt("UID");
                //System.out.println(newUID);
            }
            return (newUID + 1);

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            System.exit(1);
        }
        return newUID; // should not be zero
    }

    /**
     * Debug function to return a string to view the entire table contents.
     *
     * @return the entire table as a formatted string.
     */
    @Override
    public String queryEntireTable() {
        StringBuilder returnQuery = new StringBuilder();
        try {
            PreparedStatement idQueryStmt = dbConnection.prepareStatement("SELECT * FROM EVENTS");
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
                returnQuery.append(rs.getTimestamp("STARTDATE"));
                returnQuery.append(",");
                returnQuery.append(rs.getTimestamp("ENDDATE"));
                returnQuery.append(",");
                returnQuery.append(rs.getString("COMPLETE"));
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
                returnQuery.append(rs.getString("ORGANIZER"));
                returnQuery.append(",");
                returnQuery.append(rs.getString("SUBEVENT"));
                returnQuery.append(",");
                returnQuery.append(rs.getString("PARTICIPANT"));
                returnQuery.append(",");
                returnQuery.append(rs.getString("COMMITTEE"));
                returnQuery.append("\n");
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            System.exit(1);
        }

        return returnQuery.toString();
    }

    /**
     * A method to remove an event from the events table.
     *
     * @param uid the UID of the event to be removed.
     * @return a boolean that returns true if removal was successful.
     * @throws DoesNotExistException if the UID does not exist in the table.
     */
    @Override
    public void removeEvent(int uid) throws DoesNotExistException {
        String table = "EVENTS";
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

    //////////////////////GETTERS////////////////////////////
    /**
     * Returns the specified users description.
     *
     * @param uid the user being searched for
     * @return the description as a String.
     * @throws DoesNotExistException if the user you are searching for does not
     * exist.
     */
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

    /**
     * A method to get the start date of the user with UID specified.
     *
     * @param uid the UID of the user in question.
     * @return A timestamp of the start date
     * @throws DoesNotExistException if the user does not exist.
     */
    @Override
    public Timestamp getStartDate(int uid) throws DoesNotExistException {
        return getDBTimestamp("STARTDATE", tableName, uid);
    }

    /**
     * I think you can figure out what the rest of these functions do...
     *
     * @param uid
     * @return
     * @throws DoesNotExistException
     */
    @Override
    public Timestamp getEndDate(int uid) throws DoesNotExistException {
        return getDBTimestamp("ENDDATE", tableName, uid);
    }

    /**
     * If you cant figure out what these methods do by now then just ask me.
     *
     * @param uid
     * @return
     * @throws DoesNotExistException
     */
    @Override
    public int getComplete(int uid) throws DoesNotExistException {
        return getDBInt("COMPLETE", tableName, uid);
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
    public ArrayList<Integer> getOrganizerList(int uid) throws DoesNotExistException {
        return getDBArrayList("ORGANIZER", tableName, uid);
    }

    @Override
    public ArrayList<Integer> getSubEventList(int uid) throws DoesNotExistException {
        return getDBArrayList("SUBEVENT", tableName, uid);
    }

    @Override
    public ArrayList<Integer> getParticipantList(int uid) throws DoesNotExistException {
        return getDBArrayList("PARTICIPANT", tableName, uid);
    }

    @Override
    public ArrayList<Integer> getCommittee(int uid) throws DoesNotExistException {
        return getDBArrayList("COMMITTEE", tableName, uid);
    }

    ////////////////////////SETTERS///////////////////////////////
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
    public void setOrganizerList(int uid, ArrayList<Integer> organizerList) throws DoesNotExistException {
        setDBArrayList("ORGANIZER", tableName, uid, organizerList);
    }

    @Override
    public void setSubEventList(int uid, ArrayList<Integer> subEventList) throws DoesNotExistException {
        setDBArrayList("SUBEVENT", tableName, uid, subEventList);
    }

    @Override
    public void setParticipantList(int uid, ArrayList<Integer> participantList) throws DoesNotExistException {
        setDBArrayList("PARTICIPANT", tableName, uid, participantList);
    }

    @Override
    public void setCommittee(int uid, ArrayList<Integer> committeeList) throws DoesNotExistException {
        setDBArrayList("COMMITTEE", tableName, uid, committeeList);
    }
}
