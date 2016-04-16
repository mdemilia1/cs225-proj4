package EMS_Database.impl;

import EMS_Database.DoesNotExistException;
import EMS_Database.InitDB;
import EMS_Database.InputCommittee;
import EMS_Database.Interface_CommitteeData;
import auth.AuthorizationException;
import auth.Operation;
import auth.Permissions;
import exception.UpdateException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author mike
 */
public class Committees_Table extends InitDB implements Interface_CommitteeData {
    private static final String tableName = "COMMITTEE";

    @Override
    protected String getTableName() {
        return tableName;
    }

    /////////////////////SPECIAL FUNCTIONS/////////////////////////

    /**
     * Adds a new Committee into the committee table based on the the data from
     * InputCommittee object.
     *
     * @param committee the parameters of this object specify what is to be inserted
     * @return the UID of the inserted data.
     */
    @Override
    public int createCommittee(InputCommittee committee) throws UpdateException {
        try {
            //Creating Statement
            PreparedStatement AddAddressStmt = dbConnection.prepareStatement("INSERT INTO COMMITTEE VALUES(NULL,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
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
        } catch (SQLException sqle) {
            throw new UpdateException("Error creating committee", sqle);
        }
    }

    /**
     * This function removes a committee specified by the UID.
     *
     * @param uid the UID of the committee to be removed.
     * @return a boolean. true if removal was successful.
     * @throws DoesNotExistException  if the uid does not exist in the table.
     * @throws AuthorizationException if the accessing user does not have authorization to do so
     */
    @Override
    public void removeCommittee(int uid) throws DoesNotExistException, AuthorizationException, UpdateException {
        remove(uid);
    }

    @Override
    public double remainingMonies(int uid) throws DoesNotExistException, AuthorizationException {
        return totalIncome(uid) - totalExpense(uid);
    }

    @Override
    public double totalIncome(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "INCOME", Operation.VIEW);
        double income = 0.0;
        Income_Table in = new Income_Table();
        for (int uids : getIncome(uid)) {
            income += in.getValue(uids);
        }
        return income;
    }

    @Override
    public double totalExpense(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "EXPENSE", Operation.VIEW);
        double expense = 0.0;
        Expense_Table ex = new Expense_Table();
        for (int uids : getExpense(uid)) {
            expense += ex.getValue(uids);
        }
        return expense;
    }


    ///////////////////GETTERS////////////////////
    @Override
    public String getTitle(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "TITLE", Operation.VIEW);
        return getDBString("TITLE", uid);
    }

    @Override
    public int getChairman(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "CHAIRMAN", Operation.VIEW);
        return getDBInt("CHAIRMAN", uid);
    }

    @Override
    public ArrayList<Integer> getBudgetAccessList(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "BUDGETACCESS", Operation.VIEW);
        return getDBArrayList("BUDGETACCESS", uid);
    }

    @Override
    public ArrayList<Integer> getCommitteeMembers(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "MEMBERS", Operation.VIEW);
        return getDBArrayList("MEMBERS", uid);
    }

    @Override
    public ArrayList<Integer> getTaskList(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "TASKS", Operation.VIEW);
        return getDBArrayList("TASKS", uid);
    }

    @Override
    public ArrayList<Integer> getIncome(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "INCOME", Operation.VIEW);
        return getDBArrayList("INCOME", uid);
    }

    @Override
    public ArrayList<Integer> getExpense(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "EXPENSE", Operation.VIEW);
        return getDBArrayList("EXPENSE", uid);
    }

    @Override
    public double getBudget(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "BUDGET", Operation.VIEW);
        return getDBDouble("BUDGET", uid);
    }


    /////////////////////////SETTERS///////////////////////////
    @Override
    public void setTitle(int uid, String title) throws DoesNotExistException, AuthorizationException, UpdateException {
        Permissions.get().checkPermission(tableName, "TITLE", Operation.MODIFY);
        setDBString("TITLE", uid, title);
    }

    @Override
    public void setChairman(int uid, int nuid) throws DoesNotExistException, AuthorizationException, UpdateException {
        Permissions.get().checkPermission(tableName, "CHAIRMAN", Operation.MODIFY);
        setDBInt("CHAIRMAN", uid, nuid);
    }

    @Override
    public void setBudget(int uid, double budget) throws DoesNotExistException, AuthorizationException, UpdateException {
        Permissions.get().checkPermission(tableName, "BUDGET", Operation.MODIFY);
        setDBDouble("BUDGET", uid, budget);
    }

    @Override
    public void setBudgetAccessList(int uid, ArrayList<Integer> accessList) throws DoesNotExistException, AuthorizationException, UpdateException {
        Permissions.get().checkPermission(tableName, "BUDGETACCESS", Operation.MODIFY);
        setDBArrayList("BUDGETACCESS", uid, accessList);
    }

    @Override
    public void setCommitteeMembers(int uid, ArrayList<Integer> memberList) throws DoesNotExistException, AuthorizationException, UpdateException {
        Permissions.get().checkPermission(tableName, "MEMBERS", Operation.MODIFY);
        setDBArrayList("MEMBERS", uid, memberList);
    }

    @Override
    public void setIncome(int uid, ArrayList<Integer> income) throws DoesNotExistException, AuthorizationException, UpdateException {
        Permissions.get().checkPermission(tableName, "INCOME", Operation.MODIFY);
        setDBArrayList("INCOME", uid, income);
    }

    @Override
    public void setExpense(int uid, ArrayList<Integer> expense) throws DoesNotExistException, AuthorizationException, UpdateException {
        Permissions.get().checkPermission(tableName, "EXPENSE", Operation.MODIFY);
        setDBArrayList("EXPENSE", uid, expense);
    }

    @Override
    public void setTaskList(int uid, ArrayList<Integer> taskList) throws DoesNotExistException, AuthorizationException, UpdateException {
        Permissions.get().checkPermission(tableName, "TASKS", Operation.MODIFY);
        setDBArrayList("TASKS", uid, taskList);
    }
}
