package EMS_Database;

import auth.AuthorizationException;
import exception.UpdateException;

import java.sql.Timestamp;

public interface Interface_BudgetData {
    void removeBudgetItem(int uid) throws AuthorizationException, UpdateException, DoesNotExistException;

    int insertBudgetItem(InputIncome input) throws AuthorizationException, UpdateException;

    int insertBudgetItem(InputExpense input) throws AuthorizationException, UpdateException;

    double total() throws AuthorizationException;

    // GETTERS
    String getDescription(int uid) throws AuthorizationException, DoesNotExistException;

    double getValue(int uid) throws AuthorizationException, DoesNotExistException;

    Timestamp getDate(int uid) throws AuthorizationException, DoesNotExistException;

    // SETTERS
    void setDescription(int uid, String description) throws AuthorizationException, UpdateException, DoesNotExistException;

    void setValue(int uid, double value) throws AuthorizationException, UpdateException, DoesNotExistException;

    void setDate(int uid, Timestamp date) throws AuthorizationException, UpdateException, DoesNotExistException;
}
