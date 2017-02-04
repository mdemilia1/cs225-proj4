/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd.ManagerSystem;

import EMS_Database.DoesNotExistException;

/**
 * This class holds all the other Manager classes, to facilitate passing the
 * managers around the GUI.
 *
 * @author Julian Kuk
 */
public class MainManager {

    private BudgetItemManager budgetItemManager;
    private BudgetManager budgetManager;
    private CommitteeManager committeeManager;
    private EventManager eventManager;
    private SubEventManager subEventManager;
    private TaskManager taskManager;
    private UserManager userManager;
    private LoginManager logInManager;

    /**
     * initializes the main manager, which holds all the managers that the GUI
     * interacts with
     */
    public MainManager() {

        try {
            userManager = new UserManager();
            logInManager = new LoginManager(userManager.getUserList());
            taskManager = new TaskManager();
            subEventManager = new SubEventManager();
            committeeManager = new CommitteeManager(taskManager.getTasksTable());
            budgetItemManager = new BudgetItemManager();
            budgetManager = new BudgetManager(committeeManager.getCommitteesTable(), budgetItemManager.getIncomeTable(), budgetItemManager.getExpenseTable());

            eventManager = new EventManager(
                    userManager.getUserList(), userManager.getUsersTable(), taskManager.getTasksTable(),
                    subEventManager.getSubEventsTable(), committeeManager.getCommitteesTable(),
                    budgetItemManager.getIncomeTable(), budgetItemManager.getExpenseTable());
            userManager.connectManagers(logInManager);
            taskManager.connectManagers(logInManager, eventManager, committeeManager);
            subEventManager.connectManagers(logInManager, eventManager);
            committeeManager.connectManagers(logInManager, eventManager);
            budgetItemManager.connectManagers(logInManager, eventManager, committeeManager);
            budgetManager.connectManagers(logInManager, eventManager, committeeManager);
            eventManager.connectManagers(logInManager, userManager);

        } catch (DoesNotExistException e) {
            e.printStackTrace();
        }
    }

    public static class Main {

        public static MainManager instance = new MainManager();
    }

    /**
     * returns an instance of the main manager
     *
     * @return an instance of the main manager
     */
    public static MainManager getInstance() {
        return Main.instance;
    }

    /**
     * returns the budget item manager
     *
     * @return the budget item manager
     */
    public BudgetItemManager getBudgetItemManager() {
        return budgetItemManager;
    }

    /**
     * returns the budget manager
     *
     * @return the budget manager
     */
    public BudgetManager getBudgetManager() {
        return budgetManager;
    }

    /**
     * returns the committee manager
     *
     * @return the committee manager
     */
    public CommitteeManager getCommitteeManager() {
        return committeeManager;
    }

    /**
     * returns the event manager
     *
     * @return the event manager
     */
    public EventManager getEventManager() {
        return eventManager;
    }

    /**
     * returns the sub event manager
     *
     * @return the sub event manager
     */
    public SubEventManager getSubEventManager() {
        return subEventManager;
    }

    /**
     * returns the task manager
     *
     * @return the task manager
     */
    public TaskManager getTaskManager() {
        return taskManager;
    }

    /**
     * returns the user manager
     *
     * @return the user manager
     */
    public UserManager getUserManager() {
        return userManager;
    }

    /**
     * returns the log in manager
     *
     * @return the log in manager
     */
    public LoginManager getLogInManager() {
        return logInManager;
    }
}