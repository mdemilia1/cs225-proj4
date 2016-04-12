package EMS_Database.impl;

import EMS_Database.DoesNotExistException;
import EMS_Database.InitDB;
import static EMS_Database.InitDB.debugLog;
import EMS_Database.InputCommittee;
import EMS_Database.Interface_CommitteeData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 *
 * @author mike
 */
public class Committees_Table extends InitDB implements Interface_CommitteeData {
    
    private String tableName = "COMMITTEE";

    /////////////////////SPECIAL FUNCTIONS/////////////////////////
    /**
     * Adds a new Committee into the committee table based on the the data from
     * InputCommittee object.
     * @param committee the parameters of this object specify what is to be inserted
     * @return the UID of the inserted data.     
     */
    @Override
    public int createCommittee(InputCommittee committee) {

        try {
            //Creating Statement
            PreparedStatement AddAddressStmt = dbConnection.prepareStatement("INSERT INTO COMMITTEE VALUES(?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            int column = 0;
            AddAddressStmt.setString(++column, committee.getTitle());
            AddAddressStmt.setInt(++column, committee.getChairman());
            AddAddressStmt.setString(++column, listToString(committee.getBudgetAcess()));
            AddAddressStmt.setString(++column, listToString(committee.getCommitteeMembers()));
            AddAddressStmt.setString(++column, listToString(committee.getTaskList()));
    	    AddAddressStmt.setString(++column, listToString(committee.getIncome()));
    	    AddAddressStmt.setString(++column, listToString(committee.getExpense()));
            AddAddressStmt.setDouble(++column, committee.getBudget());
            
            //Execute Statement
            return AddAddressStmt.executeUpdate();
            
            //check for duplicates
        //    for(int uid : currentUIDList(tableName)){
        //        if(newUID == uid){
        //            throw new DoesNotExistException("CommitteeTable");
        //        }
        //    }
            // Do we need this? - Tom

        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage()); //seriously bad...
            //  debugLog.log(Level.SEVERE, "COMMITTEE table insertion failed. UID={0}", uid);
            //
            //  throw new UpdateException("Error creating committee", sqle);
    }



    /**
     * Debug function for returning the entire table as a string.
     * @return a string of the entire table
     */
    @Override
    public String queryEntireTable() {
        StringBuilder returnQuery = new StringBuilder();
        try {
            PreparedStatement idQueryStmt = dbConnection.prepareStatement("SELECT * FROM COMMITTEE");
            ResultSet rs = idQueryStmt.executeQuery();

            while (rs.next()) {
                returnQuery.append(rs.getString("UID"));
                returnQuery.append(",");
                returnQuery.append(rs.getString("TITLE"));
                returnQuery.append(",");
                returnQuery.append(rs.getInt("CHAIRMAN"));
                returnQuery.append(",");
                returnQuery.append(rs.getString("BUDGETACCESS"));
                returnQuery.append(",");
                returnQuery.append(rs.getString("MEMBERS"));
                returnQuery.append(",");
                returnQuery.append(rs.getString("TASKS"));
                returnQuery.append(",");
                returnQuery.append(rs.getDouble("BUDGET"));                                
                returnQuery.append("\n");
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            System.exit(1);
        }

        return returnQuery.toString();
    }

    /**
     * This function removes a committee specified by the UID.
     * @param uid the UID of the committee to be removed.
     * @return a boolean. true if removal was successful.
     * @throws DoesNotExistException if the uid does not exist in the table.
     */
    @Override
    public void removeCommittee(int uid) throws DoesNotExistException {
        String table = "COMMITTEE";
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

    @Override
    public double remainingMonies(int uid) throws DoesNotExistException {
	return totalIncome(uid)-totalExpense(uid);
    }

    @Override
    public double totalIncome(int uid) throws DoesNotExistException {
	double income = 0.0;
	Income_Table in = new Income_Table();
	for(int uids : getIncome(uid)){
	    income += in.getValue(uids);
	}
	return income;	
    }

    @Override
    public double totalExpense(int uid) throws DoesNotExistException {
	double expense = 0.0;
	Expense_Table ex = new Expense_Table();
	for(int uids : getExpense(uid)){
	    expense += ex.getValue(uids);
	}
	return expense;
    }
    
    

        
    
    
    ///////////////////GETTERS////////////////////
    @Override
    public String getTitle(int uid) throws DoesNotExistException {
       return getDBString("TITLE",tableName,uid);
    }

    @Override
    public int getChairman(int uid) throws DoesNotExistException {
        return getDBInt("CHAIRMAN",tableName,uid);
    }

     @Override
    public ArrayList<Integer> getBudgetAccessList(int uid) throws DoesNotExistException {
        return getDBArrayList("BUDGETACCESS",tableName,uid);
    }

    @Override
    public ArrayList<Integer> getCommitteeMembers(int uid) throws DoesNotExistException {
        return getDBArrayList("MEMBERS",tableName,uid);
    }

    @Override
    public ArrayList<Integer> getTaskList(int uid) throws DoesNotExistException {
        return getDBArrayList("TASKS",tableName,uid);
    }    

    @Override
    public ArrayList<Integer> getIncome(int uid) throws DoesNotExistException {
	return getDBArrayList("INCOME",tableName,uid);
    }

    @Override
    public ArrayList<Integer> getExpense(int uid) throws DoesNotExistException {
	return getDBArrayList("EXPENSE",tableName,uid);
    }    
    
    @Override
    public double getBudget(int uid) throws DoesNotExistException {
        return getDBDouble("BUDGET",tableName,uid);
    }
    
    
    
    
    /////////////////////////SETTERS///////////////////////////
    @Override
    public void setTitle(int uid, String title) throws DoesNotExistException {
	setDBString("TITLE",tableName,uid,title);
    }

    @Override
    public void setChairman(int uid, int nuid) throws DoesNotExistException {
        setDBInt("CHAIRMAN",tableName,uid,nuid);
    }
    
    @Override
    public void setBudget(int uid, double budget) throws DoesNotExistException {
        setDBDouble("BUDGET",tableName,uid,budget);
    }  

    @Override
    public void setBudgetAccessList(int uid, ArrayList<Integer> accessList) throws DoesNotExistException {
        setDBArrayList("BUDGETACCESS",tableName,uid,accessList);
    }

    @Override
    public void setCommitteeMembers(int uid, ArrayList<Integer> memberList) throws DoesNotExistException {
        setDBArrayList("MEMBERS",tableName,uid,memberList);
    }

    @Override
    public void setIncome(int uid, ArrayList<Integer> income) throws DoesNotExistException {
	setDBArrayList("INCOME",tableName,uid,income);
    }

    @Override
    public void setExpense(int uid, ArrayList<Integer> expense) throws DoesNotExistException {
	setDBArrayList("EXPENSE",tableName,uid,expense);
    }

    @Override
    public void setTaskList(int uid, ArrayList<Integer> taskList) throws DoesNotExistException {
        setDBArrayList("TASKS",tableName,uid,taskList);
    }        
}
