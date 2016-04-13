package EMS_Database.impl;

import EMS_Database.DoesNotExistException;
import EMS_Database.InitDB;
import static EMS_Database.InitDB.debugLog;
import EMS_Database.Interface_EventData;
import EMS_Database.InputEventData;
import auth.AuthorizationException;
import auth.Operation;
import auth.Permissions;
import exception.UpdateException;
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
    public int createEvent(InputEventData event) { //throws AuthorizationException{
        //Permissions.get().checkPermission(tableName, null, Operation.CREATE);
        // Not sure about this commented out code. It seems like the proper step to take but not sure how exactly to do so if even possible
        try {
            //Creating Statement
            PreparedStatement AddAddressStmt = dbConnection.prepareStatement("INSERT INTO EVENTS VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
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
            System.err.println(sqle.getMessage());
            //  debugLog.log(Level.SEVERE, "EVENT table insertion failed. UID={0}", uid);
            //
            //  throw new UpdateException("Error creating event", sqle);
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
         * @throws AuthorizationException if the accessing user does not have authorization to do so
         */
        @Override
        public void removeEvent(int uid) throws DoesNotExistException, AuthorizationException {
            String table = "EVENTS";
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

        //////////////////////GETTERS////////////////////////////
        /**
         * Returns the specified users description.
         *
         * @param uid the user being searched for
         * @return the description as a String.
         * @throws DoesNotExistException if the user you are searching for does not
         * @throws AuthorizationException if the accessing user does not have authorization to do so
         */
        @Override
        public String getDescription(int uid) throws DoesNotExistException, AuthorizationException {
            Permissions.get().checkPermission(tableName, "DESCRIPTION", Operation.VIEW);
            return getDBString("DESCRIPTION", tableName, uid);
        }
        /**
         * A method to get the Street of the user with UID specified.
         *
         * @param uid the UID of the user in question
         * @return the Details as a string
         * @throws DoesNotExistException if the user does not exist.
         * @throws AuthorizationException if the accessing user does not have authorization to do so
         */
        @Override
        public String getDetails(int uid) throws DoesNotExistException, AuthorizationException {
            Permissions.get().checkPermission(tableName, "DETAILS", Operation.VIEW);
            return getDBString("DETAILS", tableName, uid);
        }
        /**
         * A method to get the Street of the user with UID specified.
         *
         * @param uid the UID of the user in question
         * @return The title as a string
         * @throws DoesNotExistException if the user does not exist.
         * @throws AuthorizationException if the accessing user does not have authorization to do so
         */
        @Override
        public String getTitle(int uid) throws DoesNotExistException, AuthorizationException {
            Permissions.get().checkPermission(tableName, "TITLE", Operation.VIEW);
            return getDBString("TITLE", tableName, uid);
        }

        /**
         * A method to get the start date of the user with UID specified.
         *
         * @param uid the UID of the user in question.
         * @return A timestamp of the start date
         * @throws DoesNotExistException if the user does not exist.
         * @throws AuthorizationException if the accessing user does not have authorization to do so
         */
        @Override
        public Timestamp getStartDate(int uid) throws DoesNotExistException, AuthorizationException {
            Permissions.get().checkPermission(tableName, "STARTDATE", Operation.VIEW);
            return getDBTimestamp("STARTDATE", tableName, uid);
        }

        /**
         * A method to get the end date of the user with UID specified.
         *
         * @param uid the UID of the user in question
         * @return A timestamp of the end date
         * @throws DoesNotExistException if the user does not exist.
         * @throws AuthorizationException if the accessing user does not have authorization to do so
         */
        @Override
        public Timestamp getEndDate(int uid) throws DoesNotExistException, AuthorizationException {
            Permissions.get().checkPermission(tableName, "ENDDATE", Operation.VIEW);
            return getDBTimestamp("ENDDATE", tableName, uid);
        }

        /**
         * A method to get
         *
         * @param uid the UID of the user in question
         * @return
         * @throws DoesNotExistException if the user does not exist.
         * @throws AuthorizationException if the accessing user does not have authorization to do so
         */
        @Override
        public int getComplete(int uid) throws DoesNotExistException, AuthorizationException {
            Permissions.get().checkPermission(tableName, "COMPLETE", Operation.VIEW);
            return getDBInt("COMPLETE", tableName, uid);
        }
        /**
         * A method to get the Street of the user with UID specified.
         *
         * @param uid the UID of the user in question
         * @return A string of the Street name
         * @throws DoesNotExistException if the user does not exist.
         * @throws AuthorizationException if the accessing user does not have authorization to do so
         */
        @Override
        public String getStreet(int uid) throws DoesNotExistException, AuthorizationException {
            Permissions.get().checkPermission(tableName, "STREET", Operation.VIEW);
            return getDBString("STREET", tableName, uid);
        }
        /**
         * A method to get the City of the user with UID specified.
         *
         * @param uid the UID of the user in question
         * @return A string of the City name
         * @throws DoesNotExistException if the user does not exist.
         * @throws AuthorizationException if the accessing user does not have authorization to do so
         */
        @Override
        public String getCity(int uid) throws DoesNotExistException, AuthorizationException {
            Permissions.get().checkPermission(tableName, "CITY", Operation.VIEW);
            return getDBString("CITY", tableName, uid);
        }
        /**
         * A method to get the State of the user with UID specified.
         *
         * @param uid the UID of the user in question
         * @return A string of the State name
         * @throws DoesNotExistException if the user does not exist.
         * @throws AuthorizationException if the accessing user does not have authorization to do so
         */
        @Override
        public String getState(int uid) throws DoesNotExistException, AuthorizationException {
            Permissions.get().checkPermission(tableName, "STATE", Operation.VIEW);
            return getDBString("STATE", tableName, uid);
        }
        /**
         * A method to get the Zip Code of the user with UID specified.
         *
         * @param uid the UID of the user in question
         * @return A string of the Zip Code
         * @throws DoesNotExistException if the user does not exist.
         * @throws AuthorizationException if the accessing user does not have authorization to do so
         */
        @Override
        public String getZipcode(int uid) throws DoesNotExistException, AuthorizationException {
            Permissions.get().checkPermission(tableName, "ZIPCODE", Operation.VIEW);
            return getDBString("ZIPCODE", tableName, uid);
        }
        /**
         * A method to get the Country of the user with UID specified.
         *
         * @param uid the UID of the user in question
         * @return A string of the Country name
         * @throws DoesNotExistException if the user does not exist.
         * @throws AuthorizationException if the accessing user does not have authorization to do so
         */
        @Override
        public String getCountry(int uid) throws DoesNotExistException, AuthorizationException {
            Permissions.get().checkPermission(tableName, "COUNTRY", Operation.VIEW);
            return getDBString("COUNTRY", tableName, uid);
        }
        /**
         * A method to get the List of Organizers of the event
         *
         * @param uid
         * @return An arraylist containing the list of Organizers of the event
         * @throws DoesNotExistException if the list of Organizers does not exist
         * @throws AuthorizationException if the accessing user does not have authorization to do so
         */
        @Override
        public ArrayList<Integer> getOrganizerList(int uid) throws DoesNotExistException, AuthorizationException {
            Permissions.get().checkPermission(tableName, "ORGANIZER", Operation.VIEW);
            return getDBArrayList("ORGANIZER", tableName, uid);
        }
        /**
         * A method to get the list of SubEvents of the event
         *
         * @param uid
         * @return An arraylist containing the list of SubEvents of the event
         * @throws DoesNotExistException if the list of SubEvents does not exist.
         * @throws AuthorizationException if the accessing user does not have authorization to do so
         */
        @Override
        public ArrayList<Integer> getSubEventList(int uid) throws DoesNotExistException, AuthorizationException {
            Permissions.get().checkPermission(tableName, "SUBEVENT", Operation.VIEW);
            return getDBArrayList("SUBEVENT", tableName, uid);
        }
        /**
         * A method to get the list of Participants
         *
         * @param uid
         * @return An arraylist containing the list of Participants of the event
         * @throws DoesNotExistException if the list of Participants does not exist
         * @throws AuthorizationException if the accessing user does not have authorization to do so
         */
        @Override
        public ArrayList<Integer> getParticipantList(int uid) throws DoesNotExistException, AuthorizationException {
            Permissions.get().checkPermission(tableName, "PARTICIPANT", Operation.VIEW);
            return getDBArrayList("PARTICIPANT", tableName, uid);
        }

        /**
         * A method to get the Committee of the event
         *
         * @param uid
         * @return An arraylist containing the Committee of the event
         * @throws DoesNotExistException if the user does not exist.
         * @throws AuthorizationException if the accessing user does not have authorization to do so
         */
        @Override
        public ArrayList<Integer> getCommittee(int uid) throws DoesNotExistException, AuthorizationException {
            Permissions.get().checkPermission(tableName, "COMMITTEE", Operation.VIEW);
            return getDBArrayList("COMMITTEE", tableName, uid);
        }

        ////////////////////////SETTERS///////////////////////////////
        @Override
        public void setDescription(int uid, String description) throws DoesNotExistException, AuthorizationException {
            Permissions.get().checkPermission(tableName, "DESCRIPTION", Operation.MODIFY);
            setDBString("DESCRIPTION", tableName, uid, description);
        }

        @Override
        public void setDetails(int uid, String details) throws DoesNotExistException AuthorizationException{
            Permissions.get().checkPermission(tableName, "DETAILS", Operation.MODIFY);
            setDBString("DETAILS", tableName, uid, details);
        }

        @Override
        public void setTitle(int uid, String title) throws DoesNotExistException, AuthorizationException {
            Permissions.get().checkPermission(tableName, "TITLE", Operation.MODIFY);
            setDBString("TITLE", tableName, uid, title);
        }

        @Override
        public void setStartDate(int uid, Timestamp time) throws DoesNotExistException, AuthorizationException {
            Permissions.get().checkPermission(tableName, "STARTDATE", Operation.MODIFY);
            setDBTimestamp("STARTDATE", tableName, uid, time);
        }

        @Override
        public void setEndDate(int uid, Timestamp time) throws DoesNotExistException, AuthorizationException {
            Permissions.get().checkPermission(tableName, "ENDDATE", Operation.MODIFY);
            setDBTimestamp("ENDDATE", tableName, uid, time);
        }

        @Override
        public void setComplete(int uid, int complete) throws DoesNotExistException, AuthorizationException {
            Permissions.get().checkPermission(tableName, "COMPLETE", Operation.MODIFY);
            setDBInt("COMPLETE", tableName, uid, complete);
        }

        @Override
        public void setStreet(int uid, String street) throws DoesNotExistException, AuthorizationException {
            Permissions.get().checkPermission(tableName, "STREET", Operation.MODIFY);
            setDBString("STREET", tableName, uid, street);
        }

        @Override
        public void setCity(int uid, String city) throws DoesNotExistException, AuthorizationException {
            Permissions.get().checkPermission(tableName, "CITY", Operation.MODIFY);
            setDBString("CITY", tableName, uid, city);
        }

        @Override
        public void setState(int uid, String state) throws DoesNotExistException, AuthorizationException {
            Permissions.get().checkPermission(tableName, "STATE", Operation.MODIFY);
            setDBString("STATE", tableName, uid, state);
        }

        @Override
        public void setZipcode(int uid, String zipcode) throws DoesNotExistException, AuthorizationException {
            Permissions.get().checkPermission(tableName, "ZIPCODE", Operation.MODIFY);
            setDBString("ZIPCODE", tableName, uid, zipcode);
        }

        @Override
        public void setCountry(int uid, String country) throws DoesNotExistException, AuthorizationException {
            Permissions.get().checkPermission(tableName, "COUNTRY", Operation.MODIFY);
            setDBString("COUNTRY", tableName, uid, country);
        }

        @Override
        public void setOrganizerList(int uid, ArrayList<Integer> organizerList) throws DoesNotExistException, AuthorizationException {
            Permissions.get().checkPermission(tableName, "ORGANIZER", Operation.MODIFY);
            setDBArrayList("ORGANIZER", tableName, uid, organizerList);
        }

        @Override
        public void setSubEventList(int uid, ArrayList<Integer> subEventList) throws DoesNotExistException, AuthorizationException {
            Permissions.get().checkPermission(tableName, "SUBEVENT", Operation.MODIFY);
            setDBArrayList("SUBEVENT", tableName, uid, subEventList);
        }

        @Override
        public void setParticipantList(int uid, ArrayList<Integer> participantList) throws DoesNotExistException, AuthorizationException {
            Permissions.get().checkPermission(tableName, "PARTICIPANT", Operation.MODIFY);
            setDBArrayList("PARTICIPANT", tableName, uid, participantList);
        }

        @Override
        public void setCommittee(int uid, ArrayList<Integer> committeeList) throws DoesNotExistException, AuthorizationException {
            Permissions.get().checkPermission(tableName, "COMMITTEE", Operation.MODIFY);
            setDBArrayList("COMMITTEE", tableName, uid, committeeList);
        }
    }
