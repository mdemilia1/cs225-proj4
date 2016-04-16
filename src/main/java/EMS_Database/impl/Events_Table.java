package EMS_Database.impl;

import EMS_Database.DoesNotExistException;
import EMS_Database.InitDB;
import EMS_Database.InputEventData;
import EMS_Database.Interface_EventData;
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
 * A class to control the Events Data.
 *
 * @author mike
 */
public class Events_Table extends InitDB implements Interface_EventData {
    private static final String tableName = "EVENTS";

    @Override
    protected String getTableName() {
        return tableName;
    }

    /////////////////////SPECIAL FUNCTIONS///////////////////////////

    /**
     * Input a new event into the events using InputEvent class to create a
     * valid input object
     *
     * @param event of type InputEvent for row insertion.
     * @return the UID of the created event.
     */
    @Override
    public int createEvent(InputEventData event) throws AuthorizationException, UpdateException {
        Permissions.get().checkPermission(tableName, null, Operation.CREATE);
        try {
            //Creating Statement
            PreparedStatement AddAddressStmt = dbConnection.prepareStatement("INSERT INTO EVENTS VALUES(NULL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            int column = 0;
            AddAddressStmt.setString(++column, event.getDescription());
            AddAddressStmt.setString(++column, event.getDetails());
            AddAddressStmt.setString(++column, event.getDetails());
            AddAddressStmt.setString(++column, event.getTitle());
            AddAddressStmt.setTimestamp(++column, event.getStartDate());
            AddAddressStmt.setTimestamp(++column, event.getEndDate());
            AddAddressStmt.setInt(++column, event.getComplete());
            AddAddressStmt.setString(++column, event.getStreet());
            AddAddressStmt.setString(++column, event.getCity());
            AddAddressStmt.setString(++column, event.getState());
            AddAddressStmt.setString(++column, event.getZipcode());
            AddAddressStmt.setString(++column, event.getCountry());
            AddAddressStmt.setString(++column, listToString(event.getOrganizerList())); //inserted as a string
            AddAddressStmt.setString(++column, listToString(event.getSubEventList())); //inserted as a string
            AddAddressStmt.setString(++column, listToString(event.getParticipantList())); //inserted as a string
            AddAddressStmt.setString(++column, listToString(event.getCommittee())); //inserted as a string

            //Execute Statement
            return AddAddressStmt.executeUpdate();
        } catch (SQLException sqle) {
            throw new UpdateException("Error creating event", sqle);
        }
    }

    /**
     * A method to remove an event from the events table.
     *
     * @param uid the UID of the event to be removed.
     * @return a boolean that returns true if removal was successful.
     * @throws DoesNotExistException  if the UID does not exist in the table.
     * @throws AuthorizationException if the accessing user does not have authorization to do so
     */
    @Override
    public void removeEvent(int uid) throws DoesNotExistException, AuthorizationException, UpdateException {
        remove(uid);
    }

    //////////////////////GETTERS////////////////////////////

    /**
     * Returns the specified users description.
     *
     * @param uid the user being searched for
     * @return the description as a String.
     * @throws DoesNotExistException  if the user you are searching for does not
     * @throws AuthorizationException if the accessing user does not have authorization to do so
     */
    @Override
    public String getDescription(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "DESCRIPTION", Operation.VIEW);
        return getDBString("DESCRIPTION", uid);
    }

    /**
     * A method to get the Street of the user with UID specified.
     *
     * @param uid the UID of the user in question
     * @return the Details as a string
     * @throws DoesNotExistException  if the user does not exist.
     * @throws AuthorizationException if the accessing user does not have authorization to do so
     */
    @Override
    public String getDetails(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "DETAILS", Operation.VIEW);
        return getDBString("DETAILS", uid);
    }

    /**
     * A method to get the Street of the user with UID specified.
     *
     * @param uid the UID of the user in question
     * @return The title as a string
     * @throws DoesNotExistException  if the user does not exist.
     * @throws AuthorizationException if the accessing user does not have authorization to do so
     */
    @Override
    public String getTitle(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "TITLE", Operation.VIEW);
        return getDBString("TITLE", uid);
    }

    /**
     * A method to get the start date of the user with UID specified.
     *
     * @param uid the UID of the user in question.
     * @return A timestamp of the start date
     * @throws DoesNotExistException  if the user does not exist.
     * @throws AuthorizationException if the accessing user does not have authorization to do so
     */
    @Override
    public Timestamp getStartDate(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "STARTDATE", Operation.VIEW);
        return getDBTimestamp("STARTDATE", uid);
    }

    /**
     * A method to get the end date of the user with UID specified.
     *
     * @param uid the UID of the user in question
     * @return A timestamp of the end date
     * @throws DoesNotExistException  if the user does not exist.
     * @throws AuthorizationException if the accessing user does not have authorization to do so
     */
    @Override
    public Timestamp getEndDate(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "ENDDATE", Operation.VIEW);
        return getDBTimestamp("ENDDATE", uid);
    }

    /**
     * A method to get
     *
     * @param uid the UID of the user in question
     * @return
     * @throws DoesNotExistException  if the user does not exist.
     * @throws AuthorizationException if the accessing user does not have authorization to do so
     */
    @Override
    public int getComplete(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "COMPLETE", Operation.VIEW);
        return getDBInt("COMPLETE", uid);
    }

    /**
     * A method to get the Street of the user with UID specified.
     *
     * @param uid the UID of the user in question
     * @return A string of the Street name
     * @throws DoesNotExistException  if the user does not exist.
     * @throws AuthorizationException if the accessing user does not have authorization to do so
     */
    @Override
    public String getStreet(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "STREET", Operation.VIEW);
        return getDBString("STREET", uid);
    }

    /**
     * A method to get the City of the user with UID specified.
     *
     * @param uid the UID of the user in question
     * @return A string of the City name
     * @throws DoesNotExistException  if the user does not exist.
     * @throws AuthorizationException if the accessing user does not have authorization to do so
     */
    @Override
    public String getCity(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "CITY", Operation.VIEW);
        return getDBString("CITY", uid);
    }

    /**
     * A method to get the State of the user with UID specified.
     *
     * @param uid the UID of the user in question
     * @return A string of the State name
     * @throws DoesNotExistException  if the user does not exist.
     * @throws AuthorizationException if the accessing user does not have authorization to do so
     */
    @Override
    public String getState(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "STATE", Operation.VIEW);
        return getDBString("STATE", uid);
    }

    /**
     * A method to get the Zip Code of the user with UID specified.
     *
     * @param uid the UID of the user in question
     * @return A string of the Zip Code
     * @throws DoesNotExistException  if the user does not exist.
     * @throws AuthorizationException if the accessing user does not have authorization to do so
     */
    @Override
    public String getZipcode(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "ZIPCODE", Operation.VIEW);
        return getDBString("ZIPCODE", uid);
    }

    /**
     * A method to get the Country of the user with UID specified.
     *
     * @param uid the UID of the user in question
     * @return A string of the Country name
     * @throws DoesNotExistException  if the user does not exist.
     * @throws AuthorizationException if the accessing user does not have authorization to do so
     */
    @Override
    public String getCountry(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "COUNTRY", Operation.VIEW);
        return getDBString("COUNTRY", uid);
    }

    /**
     * A method to get the List of Organizers of the event
     *
     * @param uid
     * @return An arraylist containing the list of Organizers of the event
     * @throws DoesNotExistException  if the list of Organizers does not exist
     * @throws AuthorizationException if the accessing user does not have authorization to do so
     */
    @Override
    public ArrayList<Integer> getOrganizerList(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "ORGANIZER", Operation.VIEW);
        return getDBArrayList("ORGANIZER", uid);
    }

    /**
     * A method to get the list of SubEvents of the event
     *
     * @param uid
     * @return An arraylist containing the list of SubEvents of the event
     * @throws DoesNotExistException  if the list of SubEvents does not exist.
     * @throws AuthorizationException if the accessing user does not have authorization to do so
     */
    @Override
    public ArrayList<Integer> getSubEventList(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "SUBEVENT", Operation.VIEW);
        return getDBArrayList("SUBEVENT", uid);
    }

    /**
     * A method to get the list of Participants
     *
     * @param uid
     * @return An arraylist containing the list of Participants of the event
     * @throws DoesNotExistException  if the list of Participants does not exist
     * @throws AuthorizationException if the accessing user does not have authorization to do so
     */
    @Override
    public ArrayList<Integer> getParticipantList(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "PARTICIPANT", Operation.VIEW);
        return getDBArrayList("PARTICIPANT", uid);
    }

    /**
     * A method to get the Committee of the event
     *
     * @param uid
     * @return An arraylist containing the Committee of the event
     * @throws DoesNotExistException  if the user does not exist.
     * @throws AuthorizationException if the accessing user does not have authorization to do so
     */
    @Override
    public ArrayList<Integer> getCommittee(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "COMMITTEE", Operation.VIEW);
        return getDBArrayList("COMMITTEE", uid);
    }

    ////////////////////////SETTERS///////////////////////////////
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
    public void setOrganizerList(int uid, ArrayList<Integer> organizerList) throws DoesNotExistException, AuthorizationException, UpdateException {
        Permissions.get().checkPermission(tableName, "ORGANIZER", Operation.MODIFY);
        setDBArrayList("ORGANIZER", uid, organizerList);
    }

    @Override
    public void setSubEventList(int uid, ArrayList<Integer> subEventList) throws DoesNotExistException, AuthorizationException, UpdateException {
        Permissions.get().checkPermission(tableName, "SUBEVENT", Operation.MODIFY);
        setDBArrayList("SUBEVENT", uid, subEventList);
    }

    @Override
    public void setParticipantList(int uid, ArrayList<Integer> participantList) throws DoesNotExistException, AuthorizationException, UpdateException {
        Permissions.get().checkPermission(tableName, "PARTICIPANT", Operation.MODIFY);
        setDBArrayList("PARTICIPANT", uid, participantList);
    }

    @Override
    public void setCommittee(int uid, ArrayList<Integer> committeeList) throws DoesNotExistException, AuthorizationException, UpdateException {
        Permissions.get().checkPermission(tableName, "COMMITTEE", Operation.MODIFY);
        setDBArrayList("COMMITTEE", uid, committeeList);
    }
}
