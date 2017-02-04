package EMS_Database.impl;

import EMS_Database.*;
import auth.AuthorizationException;
import auth.Operation;
import auth.Permissions;
import exception.ReadException;
import exception.UpdateException;

import java.sql.*;

/**
 * @author mike
 */
public class Expense_Table extends InitDB implements Interface_BudgetData {
    private static final String tableName = "EXPENSE";

    @Override
    protected String getTableName() {
        return tableName;
    }

    @Override
    public void removeBudgetItem(int uid) throws AuthorizationException, UpdateException, DoesNotExistException {
        remove(uid);
    }

    @Override
    public int insertBudgetItem(InputExpense input) throws AuthorizationException, UpdateException {
        Permissions.get().checkPermission(tableName, null, Operation.CREATE);

        try {
            //Creating Statement
            PreparedStatement AddAddressStmt = dbConnection.prepareStatement("INSERT INTO " + tableName + " VALUES(NULL, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            int column = 0;
            AddAddressStmt.setString(++column, input.getDescription());
            AddAddressStmt.setTimestamp(++column, input.getTime());
            AddAddressStmt.setDouble(++column, input.getValue());
            //Execute Statement
            return AddAddressStmt.executeUpdate();
        } catch (SQLException sqle) {
            throw new UpdateException("Error creating expense", sqle);
        }
    }

    @Override
    @Deprecated
    public int insertBudgetItem(InputIncome input) throws AuthorizationException, UpdateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public double total() throws AuthorizationException {
        final String field = "VALUE";
        Permissions.get().checkPermission(tableName, field, Operation.VIEW);

        try {
            PreparedStatement idQueryStmt = dbConnection.prepareStatement("SELECT SUM(" + field + ") FROM " + tableName);
            try (ResultSet rs = idQueryStmt.executeQuery()) {
                return rs.next() ? rs.getDouble(1) : 0.0;
            }

        } catch (SQLException sqle) {
            throw new ReadException("Error reading total", sqle);
        }
    }


    //GETTERS
    @Override
    public String getDescription(int uid) throws AuthorizationException, DoesNotExistException {
        final String field = "DESCRIPTION";
        Permissions.get().checkPermission(tableName, field, Operation.VIEW);
        return getDBString(field, uid);
    }

    @Override
    public double getValue(int uid) throws AuthorizationException, DoesNotExistException {
        final String field = "VALUE";
        Permissions.get().checkPermission(tableName, field, Operation.VIEW);
        return getDBDouble(field, uid);
    }

    @Override
    public Timestamp getDate(int uid) throws AuthorizationException, DoesNotExistException {
        final String field = "DATE";
        Permissions.get().checkPermission(tableName, field, Operation.VIEW);
        return getDBTimestamp(field, uid);
    }


    //SETTERS
    @Override
    public void setDescription(int uid, String description) throws AuthorizationException, UpdateException, DoesNotExistException {
        final String field = "DESCRIPTION";
        Permissions.get().checkPermission(tableName, field, Operation.MODIFY);
        setDBString(field, uid, description);
    }

    @Override
    public void setValue(int uid, double value) throws AuthorizationException, UpdateException, DoesNotExistException {
        final String field = "VALUE";
        Permissions.get().checkPermission(tableName, field, Operation.MODIFY);
        setDBDouble(field, uid, value);
    }

    @Override
    public void setDate(int uid, Timestamp date) throws AuthorizationException, UpdateException, DoesNotExistException {
        final String field = "DATE";
        Permissions.get().checkPermission(tableName, field, Operation.MODIFY);
        setDBTimestamp(field, uid, date);
    }

}
