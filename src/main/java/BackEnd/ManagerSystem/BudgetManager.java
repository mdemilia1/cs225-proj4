package BackEnd.ManagerSystem;

import BackEnd.ManagerSystem.ManagerExceptions.PrivilegeInsufficientException;
import BackEnd.EventSystem.Budget;
import BackEnd.EventSystem.Committee;
import BackEnd.EventSystem.Expense;
import BackEnd.EventSystem.Income;
import EMS_Database.DoesNotExistException;
import EMS_Database.DuplicateInsertionException;
import EMS_Database.InputExpense;
import EMS_Database.InputIncome;
import EMS_Database.impl.Committees_Table;
import EMS_Database.impl.Expense_Table;
import EMS_Database.impl.Income_Table;
import auth.AuthorizationException;
import exception.UpdateException;

import java.util.ArrayList;

/**
 * This class serves as a liaison between the GUI and the back end and the data.
 * It checks to see whether a user has the proper privileges to change
 * something, and if the user does, then edits the database accordingly. It also
 * provides ready-to-use methods for the GUI to call.
 *
 * @author Julian Kuk
 */
public class BudgetManager {

    private Committees_Table committeesTable;
    private Income_Table incomeTable;
    private Expense_Table expenseTable;
    private Budget selectedBudget;
    private LoginManager logInManager;
    private EventManager eventManager;
    private CommitteeManager committeeManager;

    /**
     * initializes the budget manager
     *
     * @param committeesTable the committees table
     * @param incomeTable the income table
     * @param expenseTable the expense table
     */
    public BudgetManager(Committees_Table committeesTable, Income_Table incomeTable, Expense_Table expenseTable) {
        this.committeesTable = committeesTable;
        this.incomeTable = incomeTable;
        this.expenseTable = expenseTable;
    }

    void connectManagers(LoginManager logInManager, EventManager eventManager, CommitteeManager committeeManager) {
        this.logInManager = logInManager;
        this.eventManager = eventManager;
        this.committeeManager = committeeManager;
    }

    /**
     * store the budget selected by the user
     *
     * @param selectedBudget the selected budget
     */
    public void setSelectedBudget(Budget selectedBudget) {
        this.selectedBudget = selectedBudget;
    }

    /**
     * return the selected budget
     *
     * @return the selected budget
     */
    public Budget getSelectedBudget() {
        return selectedBudget;
    }

    /**
     * create an income entry in the database, if the user has a sufficient
     * privilege level
     *
     * @param income the income object to create
     * @return the income object created in the database with the proper ID
     * @throws PrivilegeInsufficientException
     * @throws DoesNotExistException
     * @throws DuplicateInsertionException
     */
    public Income createIncome(Income income)
            throws PrivilegeInsufficientException, DoesNotExistException, DuplicateInsertionException, UpdateException, AuthorizationException {

        Income newIncome = null;
        Committee selectedCommittee = committeeManager.getSelectedCommittee();
        if (PrivilegeManager.hasBudgetPrivilege(
                logInManager.getLoggedInUser(),
                eventManager.getSelectedEvent(),
                selectedCommittee)) {

            newIncome = new Income(incomeTable.insertBudgetItem(new InputIncome(
                    income.getDescription(), income.getDate(), income.getValue())), income);
            selectedBudget.getIncomeList().add(newIncome);
            ArrayList<Integer> newIncomeIDList = committeesTable.getIncome(selectedCommittee.getCOMMITTEE_ID());
            newIncomeIDList.add(newIncome.getBUDGET_ITEM_ID());
            committeesTable.setIncome(selectedCommittee.getCOMMITTEE_ID(), newIncomeIDList);

        }
        return newIncome;
    }

    /**
     * delete an income item from the database, if the user has a sufficient
     * privilege level
     *
     * @param income the income item to delete
     * @throws PrivilegeInsufficientException
     * @throws DoesNotExistException
     */
    public void deleteIncome(Income income)
            throws PrivilegeInsufficientException, DoesNotExistException, AuthorizationException, UpdateException {

        Committee selectedCommittee = committeeManager.getSelectedCommittee();
        if (PrivilegeManager.hasBudgetPrivilege(
                logInManager.getLoggedInUser(),
                eventManager.getSelectedEvent(),
                selectedCommittee)) {

            selectedBudget.getIncomeList().remove(income);
            ArrayList<Integer> newIncomeIDList = committeesTable.getIncome(selectedCommittee.getCOMMITTEE_ID());
            newIncomeIDList.remove(new Integer(income.getBUDGET_ITEM_ID()));
            committeesTable.setIncome(selectedCommittee.getCOMMITTEE_ID(), newIncomeIDList);
            incomeTable.removeBudgetItem(income.getBUDGET_ITEM_ID());
        }
    }

    /**
     * create an expense item in the database, if the user has a sufficient
     * privilege
     *
     * @param expense the expense item to add
     * @return the expense object created in the database with proper ID
     * @throws PrivilegeInsufficientException
     * @throws DuplicateInsertionException
     * @throws DoesNotExistException
     */
    public Expense createExpense(Expense expense)
            throws PrivilegeInsufficientException, DoesNotExistException, DuplicateInsertionException, UpdateException, AuthorizationException {

        Expense newExpense = null;
        Committee selectedCommittee = committeeManager.getSelectedCommittee();
        if (PrivilegeManager.hasBudgetPrivilege(
                logInManager.getLoggedInUser(),
                eventManager.getSelectedEvent(),
                selectedCommittee)) {

            newExpense = new Expense(expenseTable.insertBudgetItem(new InputExpense(
                    expense.getDescription(), expense.getDate(), expense.getValue())), expense);
            selectedBudget.getExpenseList().add(newExpense);
            ArrayList<Integer> newExpenseIDList = committeesTable.getExpense(selectedCommittee.getCOMMITTEE_ID());
            newExpenseIDList.add(newExpense.getBUDGET_ITEM_ID());
            committeesTable.setExpense(selectedCommittee.getCOMMITTEE_ID(), newExpenseIDList);
        }
        return newExpense;
    }

    /**
     * delete an expense item from the database, if the user has a sufficient
     * privilege
     *
     * @param expense the expense item to delete
     * @throws PrivilegeInsufficientException
     * @throws DoesNotExistException
     */
    public void deleteExpense(Expense expense)
            throws PrivilegeInsufficientException, DoesNotExistException, AuthorizationException, UpdateException {

        Committee selectedCommittee = committeeManager.getSelectedCommittee();
        if (PrivilegeManager.hasBudgetPrivilege(
                logInManager.getLoggedInUser(),
                eventManager.getSelectedEvent(),
                selectedCommittee)) {

            selectedBudget.getExpenseList().remove(expense);
            ArrayList<Integer> newExpenseIDList = committeesTable.getExpense(selectedCommittee.getCOMMITTEE_ID());
            newExpenseIDList.remove(new Integer(expense.getBUDGET_ITEM_ID()));
            committeesTable.setExpense(selectedCommittee.getCOMMITTEE_ID(), newExpenseIDList);
            expenseTable.removeBudgetItem(expense.getBUDGET_ITEM_ID());
        }
    }
}