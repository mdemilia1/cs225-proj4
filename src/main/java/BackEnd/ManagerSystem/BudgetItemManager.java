package BackEnd.ManagerSystem;

import BackEnd.ManagerSystem.ManagerExceptions.PrivilegeInsufficientException;
import BackEnd.EventSystem.BudgetItem;
import BackEnd.EventSystem.Income;
import EMS_Database.DoesNotExistException;
import EMS_Database.impl.Expense_Table;
import EMS_Database.impl.Income_Table;
import auth.AuthorizationException;
import exception.UpdateException;

/**
 * This class serves as a liaison between the GUI and the back end and the data.
 * It checks to see whether a user has the proper privileges to change
 * something, and if the user does, then edits the database accordingly. It also
 * provides ready-to-use methods for the GUI to call.
 *
 * @author Julian Kuk
 */
public class BudgetItemManager {

    private Income_Table incomeTable;
    private Expense_Table expenseTable;
    private BudgetItem selectedBudgetItem;
    private LoginManager logInManager;
    private EventManager eventManager;
    private CommitteeManager committeeManager;

    /**
     * initializes the budget item manager, and connects to the budget item
     * database
     */
    BudgetItemManager() {
        incomeTable = new Income_Table();
        expenseTable = new Expense_Table();
    }

    void connectManagers(LoginManager logInManager, EventManager eventManager, CommitteeManager committeeManager) {
        this.logInManager = logInManager;
        this.eventManager = eventManager;
        this.committeeManager = committeeManager;
    }

    /**
     * returns the income table
     *
     * @return the income table
     */
    Income_Table getIncomeTable() {
        return incomeTable;
    }

    /**
     * returns the expense table
     *
     * @return the expense table
     */
    Expense_Table getExpenseTable() {
        return expenseTable;
    }

    /**
     * stores the budget item selected by the user
     *
     * @param selectedBudgetItem the selected budget item
     */
    public void setSelectedBudgetItem(BudgetItem selectedBudgetItem) {
        this.selectedBudgetItem = selectedBudgetItem;
    }

    /**
     * returns the selected budget item
     *
     * @return the selected budget item
     */
    public BudgetItem getSelectedBudgetItem() {
        return selectedBudgetItem;
    }

    /**
     * edits the value of the selected budget item
     *
     * @param value the value to change it to
     * @throws PrivilegeInsufficientException
     * @throws DoesNotExistException
     */
    public void editValue(int value)
            throws PrivilegeInsufficientException, DoesNotExistException, UpdateException, AuthorizationException {

        if (PrivilegeManager.hasBudgetPrivilege(
                logInManager.getLoggedInUser(),
                eventManager.getSelectedEvent(),
                committeeManager.getSelectedCommittee())) {
            selectedBudgetItem.setValue(value);
            if (selectedBudgetItem instanceof Income) {
                incomeTable.setValue(selectedBudgetItem.getBUDGET_ITEM_ID(), value);
            } else {
                expenseTable.setValue(selectedBudgetItem.getBUDGET_ITEM_ID(), value);
            }
        }
    }

    /**
     * edits the description of the selected budget item
     *
     * @param description the description to change it to
     * @throws PrivilegeInsufficientException
     * @throws DoesNotExistException
     */
    public void editDescription(String description)
            throws PrivilegeInsufficientException, DoesNotExistException, UpdateException, AuthorizationException {

        if (PrivilegeManager.hasBudgetPrivilege(
                logInManager.getLoggedInUser(),
                eventManager.getSelectedEvent(),
                committeeManager.getSelectedCommittee())) {
            selectedBudgetItem.setDescription(description);
            if (selectedBudgetItem instanceof Income) {
                incomeTable.setDescription(selectedBudgetItem.getBUDGET_ITEM_ID(), description);
            } else {
                expenseTable.setDescription(selectedBudgetItem.getBUDGET_ITEM_ID(), description);
            }
        }
    }

    /**
     * edit the date of the selected budget item
     *
     * @param year the year to change to
     * @param month the month to change to
     * @param day the day to change to
     * @param hour the hour to change to
     * @param minute the minute to change to
     * @throws PrivilegeInsufficientException
     * @throws DoesNotExistException
     */
    public void editDate(int year, int month, int day, int hour, int minute)
            throws PrivilegeInsufficientException, DoesNotExistException, UpdateException, AuthorizationException {

        if (PrivilegeManager.hasBudgetPrivilege(
                logInManager.getLoggedInUser(),
                eventManager.getSelectedEvent(),
                committeeManager.getSelectedCommittee())) {
            selectedBudgetItem.setDate(year, month, day, hour, minute);
            if (selectedBudgetItem instanceof Income) {
                incomeTable.setDate(selectedBudgetItem.getBUDGET_ITEM_ID(), selectedBudgetItem.getDate());
            } else {
                incomeTable.setDate(selectedBudgetItem.getBUDGET_ITEM_ID(), selectedBudgetItem.getDate());
            }
        }
    }
}