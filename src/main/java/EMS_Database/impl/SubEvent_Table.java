package EMS_Database.impl;

import EMS_Database.DoesNotExistException;
import EMS_Database.InitDB;
import EMS_Database.InputSubEventData;
import EMS_Database.Interface_SubEventData;
import auth.AuthorizationException;
import auth.Operation;
import auth.Permissions;
import exception.UpdateException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

/**
 * @author mike
 */
public class SubEvent_Table extends InitDB implements Interface_SubEventData {
    private static final String tableName = "SUBEVENTS";

    @Override
    protected String getTableName() {
        return tableName;
    }

    ///////////////////////SPECIAL FUNCTIONS///////////////////////////

    /**
     * Inserts a new row into SubEvent Table based on the parameters of
     * InputSubEventData
     *
     * @param subevent the collected data to be inserted
     * @return an int of the UID upon successful creation.
     */
    @Override
    public int createSubEvent(InputSubEventData subevent) throws UpdateException, AuthorizationException {
        Permissions.get().checkPermission(tableName, null, Operation.CREATE);

        try {
            //Creating Statement
            PreparedStatement AddAddressStmt = dbConnection.prepareStatement("INSERT INTO SUBEVENTS VALUES(NULL,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
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

        } catch (SQLException sqle) {
            throw new UpdateException("Error creating sub-event", sqle);
        }
    }

    /**
     * This function removes a subevent specified by the UID.
     *
     * @param uid the UID of the subevent to be removed.
     * @return a boolean. true if removal was successful.
     * @throws DoesNotExistException if the uid does not exist in the table.
     */
    @Override
    public void removeSubEvent(int uid) throws DoesNotExistException, UpdateException, AuthorizationException {
        remove(uid);
    }

    ////////////////////////GETTERS/////////////////////////
    @Override
    public String getDescription(int uid) throws DoesNotExistException, AuthorizationException {
        return getDBString("DESCRIPTION", uid);
    }

    @Override
    public String getDetails(int uid) throws DoesNotExistException, AuthorizationException {
        return getDBString("DETAILS", uid);
    }

    @Override
    public String getTitle(int uid) throws DoesNotExistException, AuthorizationException {
        return getDBString("TITLE", uid);
    }

    @Override
    public String getStreet(int uid) throws DoesNotExistException, AuthorizationException {
        return getDBString("STREET", uid);
    }

    @Override
    public String getCity(int uid) throws DoesNotExistException, AuthorizationException {
        return getDBString("CITY", uid);
    }

    @Override
    public String getState(int uid) throws DoesNotExistException, AuthorizationException {
        return getDBString("STATE", uid);
    }

    @Override
    public String getZipcode(int uid) throws DoesNotExistException, AuthorizationException {
        return getDBString("ZIPCODE", uid);
    }

    @Override
    public String getCountry(int uid) throws DoesNotExistException, AuthorizationException {
        return getDBString("COUNTRY", uid);
    }

    @Override
    public Timestamp getStartDate(int uid) throws DoesNotExistException, AuthorizationException {
        return getDBTimestamp("STARTDATE", uid);
    }

    @Override
    public Timestamp getEndDate(int uid) throws DoesNotExistException, AuthorizationException {
        return getDBTimestamp("ENDDATE", uid);
    }

    @Override
    public int getComplete(int uid) throws DoesNotExistException, AuthorizationException {
        return getDBInt("COMPLETE", uid);
    }

    /////////////////////SETTERS////////////////////////////
    @Override
    public void setDescription(int uid, String description) throws DoesNotExistException, UpdateException, AuthorizationException {
        setDBString("DESCRIPTION", uid, description);
    }

    @Override
    public void setDetails(int uid, String details) throws DoesNotExistException, UpdateException, AuthorizationException {
        setDBString("DETAILS", uid, details);
    }

    @Override
    public void setTitle(int uid, String title) throws DoesNotExistException, UpdateException, AuthorizationException {
        setDBString("TITLE", uid, title);
    }

    @Override
    public void setStreet(int uid, String street) throws DoesNotExistException, UpdateException, AuthorizationException {
        setDBString("STREET", uid, street);
    }

    @Override
    public void setCity(int uid, String city) throws DoesNotExistException, UpdateException, AuthorizationException {
        setDBString("CITY", uid, city);
    }

    @Override
    public void setState(int uid, String state) throws DoesNotExistException, UpdateException, AuthorizationException {
        setDBString("STATE", uid, state);
    }

    @Override
    public void setZipcode(int uid, String zipcode) throws DoesNotExistException, UpdateException, AuthorizationException {
        setDBString("ZIPCODE", uid, zipcode);
    }

    @Override
    public void setCountry(int uid, String country) throws DoesNotExistException, UpdateException, AuthorizationException {
        setDBString("COUNTRY", uid, country);
    }

    @Override
    public void setStartDate(int uid, Timestamp time) throws DoesNotExistException, UpdateException, AuthorizationException {
        setDBTimestamp("STARTDATE", uid, time);
    }

    @Override
    public void setEndDate(int uid, Timestamp time) throws DoesNotExistException, UpdateException, AuthorizationException {
        setDBTimestamp("ENDDATE", uid, time);
    }

    @Override
    public void setComplete(int uid, int complete) throws DoesNotExistException, UpdateException, AuthorizationException {
        setDBInt("COMPLETE", uid, complete);
    }
}
