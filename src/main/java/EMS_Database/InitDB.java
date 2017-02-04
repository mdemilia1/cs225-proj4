package EMS_Database;

import auth.AuthorizationException;
import auth.Operation;
import auth.Permissions;
import exception.ReadException;
import exception.UpdateException;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Pattern;

/**
 * @author Mike Meding
 *         <p>
 *         Creates a valid connection to the database server. Sets up database framework
 *         if none exists.
 *         <p>
 *         Needs a connection URL, user and password.
 */
public abstract class InitDB implements Interface_FunctionWrapper {

    protected static Connection dbConnection = null;
    private final static Logger debugLog = Logger.getLogger("DebugLog");
    private static final Pattern likeEscapePattern = Pattern.compile("([%_\\\\])");
    private static FileHandler fh = null;

    static { // Setup logging file        
        try {
            fh = new FileHandler("debug.log", false);
            CloseLogger ch = new CloseLogger(fh);
            Thread t = new Thread(ch);
            Runtime.getRuntime().addShutdownHook(t);

        } catch (SecurityException | IOException e) {
            System.err.println(e.getMessage());
        }
        fh.setFormatter(new SimpleFormatter());
        debugLog.addHandler(fh);
        debugLog.setUseParentHandlers(false); //do not use default outputs (console and such)


        // define database properties for connection
        Properties props = new Properties();

        // Driver name
        String driver = "org.sqlite.JDBC";

        // connect to DB driver.
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }

        try {
            //create connection if no database exists
            dbConnection = DriverManager.getConnection("jdbc:sqlite:ems.db", props);
            System.out.println("Database Connected Successfully.");
            debugLog.info("Database Connected Successfully.");

            //create tables if none exist.
            String createUserTable = "CREATE TABLE IF NOT EXISTS USERS (UID INT PRIMARY KEY AUTOINCREMENT, "
                    + "LEVEL INT, "
                    + "FNAME VARCHAR(50) DEFAULT NULL, "
                    + "LNAME VARCHAR(50) DEFAULT NULL, "
                    + "PWD VARCHAR(256) NOT NULL, "
                    + "EMAIL VARCHAR(256) NOT NULL UNIQUE, "
                    + "PHONE VARCHAR(30) DEFAULT NULL, "
                    + "STREET VARCHAR(100) DEFAULT NULL, "
                    + "CITY VARCHAR(100) DEFAULT NULL, "
                    + "STATE VARCHAR(50) DEFAULT NULL, "
                    + "ZIPCODE VARCHAR(20) DEFAULT NULL, "
                    + "COUNTRY VARCHAR(100) DEFAULT NULL)";

            String createEventsTable = "CREATE TABLE IF NOT EXISTS EVENTS (UID INT PRIMARY KEY AUTOINCREMENT, "
                    + "DESCRIPTION VARCHAR(5000) DEFAULT NULL, "
                    + "DETAILS VARCHAR(500) DEFAULT NULL, "
                    + "TITLE VARCHAR(100) DEFAULT NULL, "
                    + "STARTDATE TIMESTAMP, "
                    + "ENDDATE TIMESTAMP, "
                    + "COMPLETE INT, "
                    + "STREET VARCHAR(100) DEFAULT NULL, "
                    + "CITY VARCHAR(100) DEFAULT NULL, "
                    + "STATE VARCHAR(50) DEFAULT NULL, "
                    + "ZIPCODE VARCHAR(20) DEFAULT NULL, "
                    + "COUNTRY VARCHAR(100) DEFAULT NULL, "
                    + "ORGANIZER VARCHAR(160) DEFAULT NULL, " //organizer list
                    + "SUBEVENT VARCHAR(160) DEFAULT NULL, " //sub-event list
                    + "PARTICIPANT VARCHAR(500) DEFAULT NULL, " //participant list
                    + "COMMITTEE VARCHAR(160) DEFAULT NULL)"; //committee list

            String createSubEventTable = "CREATE TABLE IF NOT EXISTS SUBEVENTS (UID INT PRIMARY KEY AUTOINCREMENT, "
                    + "DESCRIPTION VARCHAR(5000) DEFAULT NULL, "
                    + "DETAILS VARCHAR(500) DEFAULT NULL, "
                    + "TITLE VARCHAR(100) DEFAULT NULL, "
                    + "COMPLETE INT, "
                    + "STREET VARCHAR(100) DEFAULT NULL, "
                    + "CITY VARCHAR(100) DEFAULT NULL, "
                    + "STATE VARCHAR(50) DEFAULT NULL, "
                    + "ZIPCODE VARCHAR(20) DEFAULT NULL, "
                    + "COUNTRY VARCHAR(100) DEFAULT NULL, "
                    + "STARTDATE TIMESTAMP, "
                    + "ENDDATE TIMESTAMP)";

            String createCommitteeTable = "CREATE TABLE IF NOT EXISTS COMMITTEE (UID INT PRIMARY KEY AUTOINCREMENT, "
                    + "TITLE VARCHAR(160) DEFAULT NULL, "
                    + "CHAIRMAN INT, "
                    + "BUDGETACCESS VARCHAR(1000) DEFAULT NULL, " //list of UID #'s
                    + "MEMBERS VARCHAR(1000) DEFAULT NULL, " //list of UID #'s
                    + "TASKS VARCHAR(1000) DEFAULT NULL, " //list of task UID #'s
                    + "INCOME VARCHAR(1000) DEFAULT NULL, " //list of task UID #'s
                    + "EXPENSE VARCHAR(1000) DEFAULT NULL, " //list of task UID #'s
                    + "BUDGET DOUBLE)";

            String createTasksTable = "CREATE TABLE IF NOT EXISTS TASKS (UID INT PRIMARY KEY AUTOINCREMENT, "
                    + "DESCRIPTION VARCHAR(5000) DEFAULT NULL, "
                    + "DETAILS VARCHAR(500) DEFAULT NULL, "
                    + "TITLE VARCHAR(100) DEFAULT NULL, "
                    + "STREET VARCHAR(100) DEFAULT NULL, "
                    + "CITY VARCHAR(100) DEFAULT NULL, "
                    + "STATE VARCHAR(50) DEFAULT NULL, "
                    + "ZIPCODE VARCHAR(20) DEFAULT NULL, "
                    + "COUNTRY VARCHAR(100) DEFAULT NULL, "
                    + "STARTDATE TIMESTAMP, "
                    + "ENDDATE TIMESTAMP, "
                    + "COMPLETE INT, "
                    + "MANAGER VARCHAR(160) DEFAULT NULL)"; //users in charge of task

            String createIncomeTable = "CREATE TABLE IF NOT EXISTS INCOME (UID INT PRIMARY KEY AUTOINCREMENT, "
                    + "DESCRIPTION VARCHAR(1000) DEFAULT NULL, "
                    + "DATE TIMESTAMP, "
                    + "VALUE DOUBLE)";

            String createExpenseTable = "CREATE TABLE IF NOT EXISTS EXPENSE (UID INT PRIMARY KEY AUTOINCREMENT, "
                    + "DESCRIPTION VARCHAR(1000) DEFAULT NULL, "
                    + "DATE TIMESTAMP, "
                    + "VALUE DOUBLE)";

            Statement stmt = dbConnection.createStatement();
            stmt.executeUpdate(createUserTable); //takes table string as argument
            debugLog.info("USER table created successfully");
            stmt.executeUpdate(createEventsTable);
            debugLog.info("EVENT table created successfully");
            stmt.executeUpdate(createSubEventTable);
            debugLog.info("SUBEVENT table created successfully");
            stmt.executeUpdate(createCommitteeTable);
            debugLog.info("COMMITTEE table created successfully");
            stmt.executeUpdate(createTasksTable);
            debugLog.info("TASKS table created successfully");
            stmt.executeUpdate(createIncomeTable);
            debugLog.info("INCOME table created successfully");
            stmt.executeUpdate(createExpenseTable);
            debugLog.info("EXPENSE table created successfully");


        } catch (SQLException sqlee) { //serious errors if this gets thrown
            sqlee.printStackTrace();
            debugLog.severe("TABLE CREATION FAILED!");
        }

    }

    protected abstract String getTableName();

    protected void remove(int uid) throws AuthorizationException, UpdateException, DoesNotExistException {
        Permissions.get().checkPermission(getTableName(), null, Operation.DELETE);
        removeUnsafe(uid);
    }

    protected void remove(int uid, int userID) throws AuthorizationException, UpdateException, DoesNotExistException {
        Permissions.get().checkPermission(getTableName(), null, Operation.DELETE, userID);
        removeUnsafe(uid);
    }

    protected String like(String raw) {
        return likeEscapePattern.matcher(raw).replaceAll("\\\\$1");
    }

    private void removeUnsafe(int uid) throws UpdateException, DoesNotExistException {
        String table = getTableName();

        try (PreparedStatement stmt = dbConnection.prepareStatement("DELETE FROM " + table + " WHERE UID = ?")) {
            stmt.setInt(1, uid);
            stmt.executeUpdate();
        } catch (SQLException sqle) {
            throw new UpdateException("Unable to delete " + table.toLowerCase(), sqle);
        }
    }

    /**
     * This is a separate sub class which is another thread for shutdown
     * sequences.
     */
    private static class CloseLogger implements Runnable {

        private final FileHandler fh;

        CloseLogger(FileHandler fh) {
            this.fh = fh;
        }

        @Override
        public void run() {
            fh.flush();
            fh.close();
            //System.out.println("closed logger");
        }
    }

    public Connection getConnection() {
        return dbConnection;
    }

    /////////////////////////////SPECIAL FUNCTIONS/////////////////////////////////

    /**
     * A function to generate a list of the current UIDs in a table
     *
     * @return ArrayList<Integer> of the current UIDs in the table
     */
    @Deprecated
    public ArrayList<Integer> currentUIDList(String table) {
        ArrayList<Integer> UIDList = new ArrayList<>();
        try (PreparedStatement idQueryStmt = dbConnection.prepareStatement("SELECT UID FROM " + table)) {
            ResultSet rs = idQueryStmt.executeQuery();
            while (rs.next()) {
                UIDList.add(rs.getInt("UID"));
            }
            return UIDList;
        } catch (SQLException sqle) {
            throw new ReadException("Error retrieving list of " + table.toLowerCase() + " IDs", sqle);
        }
    }

    public List<Integer> getUIDsNeedingRebuild() {
        String table = getTableName();
        List<Integer> uids = new ArrayList<>();

        try (PreparedStatement stmt = dbConnection.prepareStatement("SELECT UID FROM " + table)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                uids.add(rs.getInt("UID"));
            }
            return uids;
        } catch (SQLException sqle) {
            throw new ReadException(String.format("Error retrieving list of %s IDs", table.toLowerCase()), sqle);
        }
    }

    /**
     * This function performs the opposite of the stringToList function which is
     * to create a format of ArrayLists that can be stored in a database as a
     * string.
     *
     * @param uidList a string to be converted back into an ArrayList<Integer>
     * @return ArrayList<Integer> of the values contained in the formatted
     * string.
     * @throws NumberFormatException if the values in the string cannot be
     *                               parsed.
     */
    private ArrayList<Integer> stringToList(String uidList) throws NumberFormatException {
        if (uidList == null || uidList.equals("")) {
            return new ArrayList<>();
        } else {
            //Split String
            String[] uidStringList;
            uidStringList = uidList.split(",");

            ArrayList<Integer> uidIntList = new ArrayList<>();

            //parse each item into arraylist
            for (String uid : uidStringList) {
                try {
                    uidIntList.add(Integer.parseInt(uid));
                } catch (NumberFormatException nfe) {
                    throw new NumberFormatException("Parse Error");
                }
            }

            return uidIntList;
        }
    }

    /**
     * Does the opposite of string to list and creates a nicely formatted string
     * for insertion into the database.
     *
     * @param list An ArrayList of Integers representing the UID numbers to be
     *             stored.
     * @return A nicely formated String for insertion into the database.
     */
    protected String listToString(List<Integer> list) {
        StringBuilder returnQuery = new StringBuilder();
        for (int uid : list) {
            returnQuery.append(uid);
            returnQuery.append(",");
        }
        return returnQuery.toString();
    }

    /**
     * A dangerous function that should be used with care. This function removes
     * all the fields contained within the specified table.
     *
     * @param table Added this field as a way of confirming to delete all data
     */
    public void removeAll(String table) throws UpdateException, AuthorizationException {
        try (PreparedStatement idQueryStmt = dbConnection.prepareStatement("DELETE FROM " + table)) {
            idQueryStmt.executeUpdate();
        } catch (SQLException sqle) {
            throw new UpdateException("Error clearing table", sqle);
        }
    }

    // FUNCTION WRAPPERS. USED AS SQL CALL METHODS.
    //GETTERS

    private <T> T get(String column, int uid, ColumnGetter<T> getter) throws DoesNotExistException, AuthorizationException {
        String table = getTableName();

        if ("USER".equals(table)) {
            Permissions.get().checkPermission(table, column, Operation.VIEW, uid);
        } else {
            Permissions.get().checkPermission(table, column, Operation.VIEW);
        }

        try (PreparedStatement stmt = dbConnection.prepareStatement("SELECT " + column + " FROM " + table + " WHERE UID = ?")) {
            stmt.setInt(1, uid);

            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                throw new DoesNotExistException(table);
            }

            return getter.get(rs, column);
        } catch (SQLException sqle) {
            throw new ReadException(String.format("Unable to read %s from %s", column.toLowerCase(), table.toLowerCase()), sqle);
        }
    }

    /**
     * An awesome function that takes a set of parameters and creates an error
     * checked sql query.
     * <p>
     * Each of the following functions corresponds to its data type for its
     * ability to get and set within a table. All the following functions follow
     * the same pattern.
     *
     * @param column essentially the column name that you are looking for in the
     *               table.
     * @param uid    the unique id of the primary key of that table to get the item
     *               of.
     * @return the value stored in the field specified by the table and column.
     * @throws DoesNotExistException if the UID that you are looking for does
     *                               not exist.
     */
    @Override
    public String getDBString(String column, int uid) throws DoesNotExistException, AuthorizationException {
        return get(column, uid, ResultSet::getString);
    }

    @Override
    public double getDBDouble(String column, int uid) throws DoesNotExistException, AuthorizationException {
        return get(column, uid, ResultSet::getDouble);
    }

    @Override
    public ArrayList<Integer> getDBArrayList(String column, int uid) throws DoesNotExistException, AuthorizationException {
        return stringToList(get(column, uid, ResultSet::getString));
    }

    @Override
    public int getDBInt(String column, int uid) throws DoesNotExistException, AuthorizationException {
        return get(column, uid, ResultSet::getInt);
    }

    @Override
    public Timestamp getDBTimestamp(String column, int uid) throws DoesNotExistException, AuthorizationException {
        return get(column, uid, ResultSet::getTimestamp);
    }

    //SETTERS

    private void set(String column, int uid, ColumnSetter setter) throws DoesNotExistException, UpdateException, AuthorizationException {
        String table = getTableName();

        if ("USER".equals(table)) {
            Permissions.get().checkPermission(table, column, Operation.MODIFY, uid);
        } else {
            Permissions.get().checkPermission(table, column, Operation.MODIFY);
        }

        try (PreparedStatement stmt = dbConnection.prepareStatement("UPDATE " + table + " SET " + column + "=? WHERE UID=?")) {
            setter.set(stmt, 1);
            stmt.setInt(2, uid);

            if (stmt.executeUpdate() == 0) {
                throw new DoesNotExistException(table);
            }
        } catch (SQLException sqle) {
            throw new UpdateException(String.format("Unable to set %s for %s", column.toLowerCase(), table.toLowerCase()), sqle);
        }
    }

    @Override
    public void setDBString(String column, int uid, String newValue) throws DoesNotExistException, UpdateException, AuthorizationException {
        set(column, uid, (s, i) -> s.setString(i, newValue));
    }

    @Override
    public void setDBDouble(String column, int uid, double newValue) throws DoesNotExistException, UpdateException, AuthorizationException {
        set(column, uid, (s, i) -> s.setDouble(i, newValue));
    }

    @Override
    public void setDBArrayList(String column, int uid, List<Integer> newValue) throws DoesNotExistException, UpdateException, AuthorizationException {
        set(column, uid, (s, i) -> s.setString(i, listToString(newValue)));
    }

    @Override
    public void setDBInt(String column, int uid, int newValue) throws DoesNotExistException, UpdateException, AuthorizationException {
        set(column, uid, (s, i) -> s.setInt(i, newValue));
    }

    @Override
    public void setDBTimestamp(String column, int uid, Timestamp newValue) throws DoesNotExistException, UpdateException, AuthorizationException {
        set(column, uid, (s, i) -> s.setTimestamp(i, newValue));
    }
}
