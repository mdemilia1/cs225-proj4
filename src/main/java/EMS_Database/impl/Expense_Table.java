package EMS_Database.impl;

import EMS_Database.DoesNotExistException;
import EMS_Database.InitDB;
import static EMS_Database.InitDB.debugLog;
import EMS_Database.InputExpense;
import EMS_Database.Interface_BudgetData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import exception.UpdateException;

/**
 *
 * @author mike
 */
public class Expense_Table extends InitDB implements Interface_BudgetData {
    private String tableName = "EXPENSE";
    
    //SPECIAL FUNCTIONS

    @Override
    public String queryEntireTable() {
	StringBuilder returnQuery = new StringBuilder();
        try {
            PreparedStatement idQueryStmt = dbConnection.prepareStatement("SELECT * FROM EXPENSE");
            ResultSet rs = idQueryStmt.executeQuery();

            while (rs.next()) {
                returnQuery.append(rs.getInt("UID"));
                returnQuery.append(",");
                returnQuery.append(rs.getString("DESCRIPTION"));
                returnQuery.append(",");
		returnQuery.append(rs.getString("DATE"));
		returnQuery.append(",");
                returnQuery.append(rs.getDouble("VALUE"));                                
                returnQuery.append("\n");
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            System.exit(1);
        }

        return returnQuery.toString();
    }

    @Override
    public void removeBudgetItem(int uid) throws DoesNotExistException {
	String table = "EXPENSE";
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
	    PreparedStatement idQueryStmt = dbConnection.prepareStatement("DELETE FROM "+table+" WHERE UID=?");
	    idQueryStmt.setInt(1, uid);
	    idQueryStmt.executeUpdate();

	} catch (SQLException sqle) {
	    System.err.println(sqle.getMessage());
	    System.err.println("Deleting stuff from "+table+" is dangerous...");
	}	
    }
    
    public int insertBudgetItem(InputExpense input) {
        try {
            //Creating Statement
            PreparedStatement AddAddressStmt = dbConnection.prepareStatement("INSERT INTO EXPENSE VALUES(NULL,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            int column = 0;
            AddAddressStmt.setInt(++column, imput.getDescription());
            AddAddressStmt.setInt(++column, imput.getTime());
            AddAddressStmt.setInt(++column, imput.getValue());
            //Execute Statement
         return    AddAddressStmt.executeUpdate();
            

        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
           // debugLog.log(Level., "EXPENSES table insertion failded. UID={0}", uid);

           // throw new UpdateException("Error creating expense", sqle);
        }
    }

    @Override
    public double total() {
	double returnQuery = 0.0;
	double input = 0.0;
	try {
	    PreparedStatement idQueryStmt = dbConnection.prepareStatement("SELECT * FROM EXPENSE");
	    ResultSet rs = idQueryStmt.executeQuery();
	    while (rs.next()) {
		input = rs.getDouble("VALUE");
		returnQuery = returnQuery + input;		
	    }
	    return returnQuery;

	} catch (SQLException sqle) {
	    sqle.printStackTrace();
	    System.exit(1);
	}
	return returnQuery;
    }
    
    
    
    //GETTERS
    @Override
    public String getDescription(int uid) throws DoesNotExistException {
	return getDBString("DESCRIPTION",tableName,uid);
    }

    @Override
    public double getValue(int uid) throws DoesNotExistException {
	return getDBDouble("VALUE",tableName,uid);
    }

    @Override
    public Timestamp getDate(int uid) throws DoesNotExistException {
	return getDBTimestamp("DATE",tableName,uid);
    }
    
    
    
    
    //SETTERS
    @Override
    public void setDescription(int uid, String description) throws DoesNotExistException {
	setDBString("DESCRIPTION",tableName,uid,description);
    }

    @Override
    public void setValue(int uid, double value) throws DoesNotExistException {
	setDBDouble("VALUE",tableName,uid,value);
    }

    @Override
    public void setDate(int uid, Timestamp date) throws DoesNotExistException {
	setDBTimestamp("DATE",tableName,uid,date);
    }        

}
