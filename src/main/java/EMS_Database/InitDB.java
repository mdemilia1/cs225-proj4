package EMS_Database;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * @author Mike Meding
 *
 * Creates a valid connection to the database server. Sets up database framework
 * if none exists.
 *
 * Needs a connection URL, user and password.
 */
public abstract class InitDB implements Interface_FunctionWrapper {

    protected Connection dbConnection = null;
    public final static Logger debugLog = Logger.getLogger("DebugLog");
    private static FileHandler fh = null;

    static { // Setup logging file        
	try {
	    fh = new FileHandler("debug.log", false);
	    CloseLogger ch = new CloseLogger(fh);
	    Thread t = new Thread(ch);
	    Runtime.getRuntime().addShutdownHook(t);

	} catch (SecurityException e) {
	    System.err.println(e.getMessage());
	} catch (IOException e) {
	    System.err.println(e.getMessage());
	}
	fh.setFormatter(new SimpleFormatter());
	debugLog.addHandler(fh);
	debugLog.setUseParentHandlers(false); //do not use default outputs (console and such)


    }

    public InitDB() {



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
			+ "EMAIL VARCHAR(256) NOT NULL, "
			+ "PHONE VARCHAR(30) DEFAULT NULL, "
			+ "STREET VARCHAR(100) DEFAULT NULL, "
			+ "CITY VARCHAR(100) DEFAULT NULL, "
			+ "STATE VARCHAR(50) DEFAULT NULL, "
			+ "ZIPCODE VARCHAR(20) DEFAULT NULL, "
			+ "COUNTRY VARCHAR(100) DEFAULT NULL, "
			+ "PARTICIPANT VARCHAR(100) DEFAULT NULL, "
			+ "EVENTLEVEL INT NOT NULL)";

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
		
		String createKeyTable = "CREATE TABLE IF NOT EXISTS ROOTKEY (UID INT PRIMARY KEY, "
			+ "MOD BIGINT, "
			+ "EXP BIGINT)";

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
		stmt.executeUpdate(createKeyTable);
		debugLog.info("ROOTKEY table created successfully");


	    } catch (SQLException sqlee) { //serious errors if this gets thrown
		sqlee.printStackTrace();
		debugLog.severe("TABLE CREATION FAILED!");
	    }

    }

    /**
     * This is a separate sub class which is another thread for shutdown
     * sequences.
     */
    private static class CloseLogger implements Runnable {

	private final FileHandler fh;

	public CloseLogger(FileHandler fh) {
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
     * A function to generate a list of the current UID's in a table
     *
     * @return ArrayList<Integer> of the current UID's in the table
     */
    public ArrayList<Integer> currentUIDList(String table) {
	int newUID = 0;
	ArrayList<Integer> UIDList = new ArrayList<Integer>();
	try {

	    PreparedStatement idQueryStmt = dbConnection.prepareStatement("SELECT * FROM " + table);
	    ResultSet rs = idQueryStmt.executeQuery();

	    while (rs.next()) {
		newUID = rs.getInt("UID");
		UIDList.add(newUID);
	    }
	    return UIDList;

	} catch (SQLException sqle) {
	    sqle.printStackTrace();
	    System.exit(1);
	}
	return UIDList; // should not be zero
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
     * parsed.
     */
    public ArrayList<Integer> stringToList(String uidList) throws NumberFormatException {
	if (uidList.equals("") || uidList == null) {
	    return new ArrayList<Integer>();
	} else {
	    //Split String
	    String[] uidStringList;
	    uidStringList = uidList.split(",");

	    ArrayList<Integer> uidIntList = new ArrayList<Integer>();

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
     * stored.
     * @return A nicely formated String for insertion into the database.
     */
    public String listToString(ArrayList<Integer> list) {
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
     * @param table Added this field as a way of confirming to delete all data     
     */
    public void removeAll(String table) {
	for (int uid : currentUIDList(table)) {
	    try {
		PreparedStatement idQueryStmt = dbConnection.prepareStatement("DELETE FROM "+table+" WHERE UID=?");
		idQueryStmt.setInt(1, uid);
		idQueryStmt.executeUpdate();
	    } catch (SQLException sqle) {
		System.err.println(sqle.getMessage());
		debugLog.severe("Remove All FAILED.");
		System.exit(1); //if the shit really hits the fan.
	    }
	}	
    }

    // FUNCTION WRAPPERS. USED AS SQL CALL METHODS.
    //GETTERS
    /**
     * An awesome function that takes a set of parameters and creates an error
     * checked sql query.
     * <p>
     * Each of the following functions corresponds to its data type for its
     * ability to get and set within a table. All the following functions follow
     * the same pattern.
     *
     * @param query essentially the column name that you are looking for in the
     * table.
     * @param table the table to search for that column
     * @param uid the unique id of the primary key of that table to get the item
     * of.
     * @return the value stored in the field specified by the table and column.
     * @throws DoesNotExistException if the UID that you are looking for does
     * not exist.
     */
    @Override
    public String getDBString(String query, String table, int uid) throws DoesNotExistException {
	//checking for existance of that uid
	boolean exists = false;
	for (int validID : currentUIDList(table)) {
	    if (validID == uid) {
		exists = true;
		break;
	    }
	}
	if (exists == false) {
	    debugLog.log(Level.WARNING, "UID={0} does not exist in {1} table. Error occurred while calling get{2}", new Object[]{uid, table, query});
	    throw new DoesNotExistException("check debug log. " + table + " table error.");
	}
	//executing query
	try {
	    PreparedStatement idQueryStmt = dbConnection.prepareStatement("SELECT * FROM " + table + " WHERE UID=?");
	    idQueryStmt.setInt(1, uid);
	    ResultSet rs = idQueryStmt.executeQuery();

	    //Gets the row with uid specified
	    String returnQuery = null;
	    while (rs.next()) {
		returnQuery = rs.getString(query); //Should not have two uids with the same name                            
	    }
	    return returnQuery; //should always return here.

	} catch (SQLException sqle) {
	    debugLog.log(Level.SEVERE, "SERIOUS DATABASE ERROR IN " + table + " TABLE.");
	    sqle.printStackTrace();
	    System.exit(1);
	}
	throw new DoesNotExistException("Should not get here...");
    }

    @Override
    public double getDBDouble(String query, String table, int uid) throws DoesNotExistException {
	//checking for existance of that uid
	boolean exists = false;
	for (int validID : currentUIDList(table)) {
	    if (validID == uid) {
		exists = true;
		break;
	    }
	}
	if (exists == false) {
	    debugLog.log(Level.WARNING, "UID={0} does not exist in {1} table. Error occurred while calling get{2}", new Object[]{uid, table, query});
	    throw new DoesNotExistException("check debug log. " + table + " table error.");
	}
	//executing query
	try {
	    PreparedStatement idQueryStmt = dbConnection.prepareStatement("SELECT * FROM " + table + " WHERE UID=?");
	    idQueryStmt.setInt(1, uid);
	    ResultSet rs = idQueryStmt.executeQuery();

	    //Gets the row with uid specified
	    double returnQuery = 0.0;
	    while (rs.next()) {
		returnQuery = rs.getDouble(query); //Should not have two uids with the same name                            
	    }
	    return returnQuery; //should always return here.

	} catch (SQLException sqle) {
	    debugLog.log(Level.SEVERE, "SERIOUS DATABASE ERROR IN " + table + " TABLE.");
	    sqle.printStackTrace();
	    System.exit(1);
	}
	throw new DoesNotExistException("Should not get here...");
    }

    @Override
    public ArrayList<Integer> getDBArrayList(String query, String table, int uid) throws DoesNotExistException {
	//checking for existance of that uid
	boolean exists = false;
	for (int validID : currentUIDList(table)) {
	    if (validID == uid) {
		exists = true;
		break;
	    }
	}
	if (exists == false) {
	    debugLog.log(Level.WARNING, "UID={0} does not exist in {1} table. Error occurred while calling get{2}", new Object[]{uid, table, query});
	    throw new DoesNotExistException("check debug log. " + table + " table error.");
	}
	//executing query
	try {
	    PreparedStatement idQueryStmt = dbConnection.prepareStatement("SELECT * FROM " + table + " WHERE UID=?");
	    idQueryStmt.setInt(1, uid);
	    ResultSet rs = idQueryStmt.executeQuery();

	    //Gets the row with uid specified
	    String returnQuery = "";
	    while (rs.next()) {
		returnQuery = rs.getString(query); //Should not have two uids with the same name                            
	    }
	    return stringToList(returnQuery); //should always return here.

	} catch (SQLException sqle) {
	    debugLog.log(Level.SEVERE, "SERIOUS DATABASE ERROR IN " + table + " TABLE.");
	    sqle.printStackTrace();
	    System.exit(1);
	}
	throw new DoesNotExistException("Should not get here...");
    }

    @Override
    public int getDBInt(String query, String table, int uid) throws DoesNotExistException {
	//checking for existance of that uid
	boolean exists = false;
	for (int validID : currentUIDList(table)) {
	    if (validID == uid) {
		exists = true;
		break;
	    }
	}
	if (exists == false) {
	    debugLog.log(Level.WARNING, "UID={0} does not exist in {1} table. Error occurred while calling get{2}", new Object[]{uid, table, query});
	    throw new DoesNotExistException("check debug log. " + table + " table error.");
	}
	//executing query
	try {
	    PreparedStatement idQueryStmt = dbConnection.prepareStatement("SELECT * FROM " + table + " WHERE UID=?");
	    idQueryStmt.setInt(1, uid);
	    ResultSet rs = idQueryStmt.executeQuery();

	    //Gets the row with uid specified
	    int returnQuery = 0;
	    while (rs.next()) {
		returnQuery = rs.getInt(query); //Should not have two uids with the same name                            
	    }
	    return returnQuery; //should always return here.

	} catch (SQLException sqle) {
	    debugLog.log(Level.SEVERE, "SERIOUS DATABASE ERROR IN " + table + " TABLE.");
	    sqle.printStackTrace();
	    System.exit(1);
	}
	throw new DoesNotExistException("Should not get here...");
    }
    
    /**
     * This Function is used mostly for accessing items for RSA encryption keys which require a BigInteger
     * @param query The column to be selected.
     * @param table The table to select the column from.
     * @param uid The uid in the table to be used for the value based on the two aformentioned parameters.
     * @return A BigInt that that holds the key value.
     * @throws DoesNotExistException if the uid that you are looking for does not exist.
     */
    public BigInteger getDBBigInt(String query, String table, int uid) throws DoesNotExistException {
	//checking for existance of that uid
	boolean exists = false;
	for (int validID : currentUIDList(table)) {
	    if (validID == uid) {
		exists = true;
		break;
	    }
	}
	//nice error logging if uid does not exist in table.
	if (exists == false) {
	    debugLog.log(Level.WARNING, "UID={0} does not exist in {1} table. Error occurred while calling get {2}", new Object[]{uid, table, query});
	    throw new DoesNotExistException("check debug log. " + table + " table error.");
	}
	//executing query
	try {
	    PreparedStatement idQueryStmt = dbConnection.prepareStatement("SELECT * FROM " + table + " WHERE UID=?");
	    idQueryStmt.setInt(1, uid);
	    ResultSet rs = idQueryStmt.executeQuery();

	    //Gets the row with uid specified
	    long returnQuery = 0; //BIGINT == storing in database as a long.
	    
	    while (rs.next()) {		
		returnQuery = rs.getLong(query); //Should not have two uids with the same name                            
	    }	    
	    //parsing long to BigInteger using BigDecimal.
	    BigDecimal returnVal = new BigDecimal(returnQuery);	    
	    return returnVal.toBigInteger(); //should always return here.

	} catch (SQLException sqle) {
	    debugLog.log(Level.SEVERE, "SERIOUS DATABASE ERROR IN " + table + " TABLE.");
	    sqle.printStackTrace();
	    System.exit(1);
	}
	throw new DoesNotExistException("Should not get here...");
    }

    @Override
    public Timestamp getDBTimestamp(String query, String table, int uid) throws DoesNotExistException {
	//checking for existance of that uid
	boolean exists = false;
	for (int validID : currentUIDList(table)) {
	    if (validID == uid) {
		exists = true;
		break;
	    }
	}
	if (exists == false) {
	    debugLog.log(Level.WARNING, "UID={0} does not exist in {1} table. Error occurred while calling get{2}", new Object[]{uid, table, query});
	    throw new DoesNotExistException("check debug log. " + table + " table error.");
	}
	//executing query
	try {
	    PreparedStatement idQueryStmt = dbConnection.prepareStatement("SELECT * FROM " + table + " WHERE UID=?");
	    idQueryStmt.setInt(1, uid);
	    ResultSet rs = idQueryStmt.executeQuery();

	    //Gets the row with uid specified
	    Timestamp returnQuery = new Timestamp(new Date().getTime()); //using current time as default.
	    while (rs.next()) {
		returnQuery = rs.getTimestamp(query); //Should not have two uids with the same name                            
	    }
	    return returnQuery; //should always return here.

	} catch (SQLException sqle) {
	    debugLog.log(Level.SEVERE, "SERIOUS DATABASE ERROR IN " + table + " TABLE.");
	    sqle.printStackTrace();
	    System.exit(1);
	}
	throw new DoesNotExistException("Should not get here...");
    }

    //SETTERS
    @Override
    public void setDBString(String query, String table, int uid, String newValue) throws DoesNotExistException {
	try {
	    boolean exists = false;
	    for (int validID : currentUIDList(table)) {
		if (validID == uid) {
		    exists = true;
		    break;
		}
	    }
	    if (exists) {
		PreparedStatement idQueryStmt = dbConnection.prepareStatement("UPDATE " + table + " SET " + query + "=? WHERE UID=?");
		idQueryStmt.setString(1, newValue);
		idQueryStmt.setInt(2, uid);
		idQueryStmt.executeUpdate();
	    } else {
		debugLog.log(Level.WARNING, "UID={0} does not exist in {1} table. Error occurred while calling set{2}", new Object[]{uid, table, query});
		throw new DoesNotExistException("check debug log. " + table + " table error.");
	    }
	} catch (SQLException sqle) {
	    debugLog.log(Level.SEVERE, "SERIOUS DATABASE ERROR IN " + table + " TABLE.");
	    sqle.printStackTrace();
	    System.exit(1);
	}
    }

    @Override
    public void setDBDouble(String query, String table, int uid, double newValue) throws DoesNotExistException {
	try {
	    boolean exists = false;
	    for (int validID : currentUIDList(table)) {
		if (validID == uid) {
		    exists = true;
		    break;
		}
	    }
	    if (exists) {
		PreparedStatement idQueryStmt = dbConnection.prepareStatement("UPDATE " + table + " SET " + query + "=? WHERE UID=?");
		idQueryStmt.setDouble(1, newValue);
		idQueryStmt.setInt(2, uid);
		idQueryStmt.executeUpdate();
	    } else {
		debugLog.log(Level.WARNING, "UID={0} does not exist in {1} table. Error occurred while calling set{2}", new Object[]{uid, table, query});
		throw new DoesNotExistException("check debug log. " + table + " table error.");
	    }
	} catch (SQLException sqle) {
	    debugLog.log(Level.SEVERE, "SERIOUS DATABASE ERROR IN " + table + " TABLE.");
	    sqle.printStackTrace();
	    System.exit(1);
	}
    }

    @Override
    public void setDBArrayList(String query, String table, int uid, ArrayList<Integer> newValue) throws DoesNotExistException {
	String newStringValue = listToString(newValue);
	try {
	    boolean exists = false;
	    for (int validID : currentUIDList(table)) {
		if (validID == uid) {
		    exists = true;
		    break;
		}
	    }
	    if (exists) {
		PreparedStatement idQueryStmt = dbConnection.prepareStatement("UPDATE " + table + " SET " + query + "=? WHERE UID=?");
		idQueryStmt.setString(1, newStringValue);
		idQueryStmt.setInt(2, uid);
		idQueryStmt.executeUpdate();
	    } else {
		debugLog.log(Level.WARNING, "UID={0} does not exist in {1} table. Error occurred while calling set{2}", new Object[]{uid, table, query});
		throw new DoesNotExistException("check debug log. " + table + " table error.");
	    }
	} catch (SQLException sqle) {
	    debugLog.log(Level.SEVERE, "SERIOUS DATABASE ERROR IN " + table + " TABLE.");
	    sqle.printStackTrace();
	    System.exit(1);
	}
    }

    @Override
    public void setDBInt(String query, String table, int uid, int newValue) throws DoesNotExistException {
	try {
	    boolean exists = false;
	    for (int validID : currentUIDList(table)) {
		if (validID == uid) {
		    exists = true;
		    break;
		}
	    }
	    if (exists) {
		PreparedStatement idQueryStmt = dbConnection.prepareStatement("UPDATE " + table + " SET " + query + "=? WHERE UID=?");
		idQueryStmt.setInt(1, newValue);
		idQueryStmt.setInt(2, uid);
		idQueryStmt.executeUpdate();
	    } else {
		debugLog.log(Level.WARNING, "UID={0} does not exist in {1} table. Error occurred while calling set{2}", new Object[]{uid, table, query});
		throw new DoesNotExistException("check debug log. " + table + " table error.");
	    }
	} catch (SQLException sqle) {
	    debugLog.log(Level.SEVERE, "SERIOUS DATABASE ERROR IN " + table + " TABLE.");
	    sqle.printStackTrace();
	    System.exit(1);
	}
    }

    @Override
    public void setDBTimestamp(String query, String table, int uid, Timestamp newValue) throws DoesNotExistException {
	try {
	    boolean exists = false;
	    for (int validID : currentUIDList(table)) {
		if (validID == uid) {
		    exists = true;
		    break;
		}
	    }
	    if (exists) {
		PreparedStatement idQueryStmt = dbConnection.prepareStatement("UPDATE " + table + " SET " + query + "=? WHERE UID=?");
		idQueryStmt.setTimestamp(1, newValue);
		idQueryStmt.setInt(2, uid);
		idQueryStmt.executeUpdate();
	    } else {
		debugLog.log(Level.WARNING, "UID={0} does not exist in {1} table. Error occurred while calling set{2}", new Object[]{uid, table, query});
		throw new DoesNotExistException("check debug log. " + table + " table error.");
	    }
	} catch (SQLException sqle) {
	    debugLog.log(Level.SEVERE, "SERIOUS DATABASE ERROR IN " + table + " TABLE.");
	    sqle.printStackTrace();
	    System.exit(1);
	}
    }
}
