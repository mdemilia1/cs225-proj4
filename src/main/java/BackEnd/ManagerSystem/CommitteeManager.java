package BackEnd.ManagerSystem;

import BackEnd.ManagerSystem.ManagerExceptions.PrivilegeInsufficientException;
import BackEnd.EventSystem.Committee;
import BackEnd.EventSystem.Task;
import BackEnd.UserSystem.User;
import EMS_Database.DoesNotExistException;
import EMS_Database.DuplicateInsertionException;
import EMS_Database.InputTask;
import EMS_Database.impl.Committees_Table;
import EMS_Database.impl.Tasks_Table;
import auth.AuthorizationException;
import exception.UpdateException;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * This class serves as a liaison between the GUI and the back end and the data.
 * It checks to see whether a user has the proper privileges to change
 * something, and if the user does, then edits the database accordingly. It also
 * provides ready-to-use methods for the GUI to call.
 *
 * @author Julian Kuk
 */
public class CommitteeManager {

    private Committees_Table committeesTable;
    private Tasks_Table tasksTable;
    private Committee selectedCommittee;
    private LoginManager logInManager;
    private EventManager eventManager;

    /**
     * initializes the committee manager, and connects to the committee database
     *
     * @param tasksTable the tasks table
     */
    public CommitteeManager(Tasks_Table tasksTable) {
        committeesTable = new Committees_Table();
        this.tasksTable = tasksTable;
    }

    void connectManagers(LoginManager logInManager, EventManager eventManager) {
        this.logInManager = logInManager;
        this.eventManager = eventManager;
    }

    /**
     * returns the committees table
     *
     * @return the committee table
     */
    Committees_Table getCommitteesTable() {
        return committeesTable;
    }

    /**
     * stores the committee selected by the user
     *
     * @param selectedCommittee the the selected committee
     */
    public void setSelectedCommittee(Committee selectedCommittee) {
        this.selectedCommittee = selectedCommittee;
    }

    /**
     * returns the selected committee
     *
     * @return the selected committee
     */
    public Committee getSelectedCommittee() {
        return selectedCommittee;
    }

    /**
     * edits the title of the selected committee, if the user has sufficient
     * privilege
     *
     * @param title the title
     * @throws PrivilegeInsufficientException
     * @throws DoesNotExistException
     */
    public void editTitle(String title)
            throws PrivilegeInsufficientException, DoesNotExistException, UpdateException, AuthorizationException {

        if (PrivilegeManager.hasCommitteePrivilege(
                logInManager.getLoggedInUser(),
                eventManager.getSelectedEvent(),
                selectedCommittee)) {
            selectedCommittee.setTitle(title);
            committeesTable.setTitle(selectedCommittee.getCOMMITTEE_ID(), title);
        }
    }

    /**
     * edits the chair of the selected committee, if the user has sufficient
     * privilege
     *
     * @param chair the chair to change to
     * @throws PrivilegeInsufficientException
     * @throws DoesNotExistException
     */
    public void editChair(User chair)
            throws PrivilegeInsufficientException, DoesNotExistException, UpdateException, AuthorizationException {

        if (PrivilegeManager.hasCommitteePrivilege(
                logInManager.getLoggedInUser(),
                eventManager.getSelectedEvent(),
                selectedCommittee)) {
            selectedCommittee.setChair(chair);
            committeesTable.setChairman(selectedCommittee.getCOMMITTEE_ID(), chair.getUserId());
        }
    }

    /**
     * add a user to the budget access list, if the logged in user has
     * sufficient privilege
     *
     * @param budgetAccess the user to add
     * @throws PrivilegeInsufficientException
     * @throws DoesNotExistException
     */
    public void addBudgetAccess(User budgetAccess)
            throws PrivilegeInsufficientException, DoesNotExistException, AuthorizationException, UpdateException {

        if (PrivilegeManager.hasCommitteePrivilege(
                logInManager.getLoggedInUser(),
                eventManager.getSelectedEvent(),
                selectedCommittee)) {
            selectedCommittee.getBudgetAccessList().add(budgetAccess);
            ArrayList<Integer> newBudgetAccessList = committeesTable.getBudgetAccessList(selectedCommittee.getCOMMITTEE_ID());
            newBudgetAccessList.add(budgetAccess.getUserId());
            committeesTable.setBudgetAccessList(selectedCommittee.getCOMMITTEE_ID(), newBudgetAccessList);
        }
    }

    /**
     * removes a user from the budget access list, if the user has sufficient
     * privilege
     *
     * @param budgetAccess the user to remove
     * @throws PrivilegeInsufficientException
     * @throws DoesNotExistException
     */
    public void removeBudgetAccess(User budgetAccess)
            throws PrivilegeInsufficientException, DoesNotExistException, AuthorizationException, UpdateException {

        if (PrivilegeManager.hasCommitteePrivilege(
                logInManager.getLoggedInUser(),
                eventManager.getSelectedEvent(),
                selectedCommittee)) {
            selectedCommittee.getBudgetAccessList().remove(budgetAccess);
            ArrayList<Integer> newBudgetAccessList = committeesTable.getBudgetAccessList(selectedCommittee.getCOMMITTEE_ID());
            newBudgetAccessList.remove(new Integer(budgetAccess.getUserId()));
            committeesTable.setBudgetAccessList(selectedCommittee.getCOMMITTEE_ID(), newBudgetAccessList);
        }
    }

    /**
     * add a member to the selected committee, if the user has sufficient
     * privilege
     *
     * @param member the member to add
     * @throws PrivilegeInsufficientException
     * @throws DoesNotExistException
     */
    public void addMember(User member)
            throws PrivilegeInsufficientException, DoesNotExistException, AuthorizationException, UpdateException {

        if (PrivilegeManager.hasCommitteePrivilege(
                logInManager.getLoggedInUser(),
                eventManager.getSelectedEvent(),
                selectedCommittee)) {
            selectedCommittee.getMemberList().add(member);
            ArrayList<Integer> newMemberList = committeesTable.getCommitteeMembers(selectedCommittee.getCOMMITTEE_ID());
            newMemberList.add(member.getUserId());
            committeesTable.setCommitteeMembers(selectedCommittee.getCOMMITTEE_ID(), newMemberList);
        }
    }

    /**
     * remove a member from the selected committee, if the user has sufficient
     * privilege
     *
     * @param member the member to remove
     * @throws PrivilegeInsufficientException
     * @throws DoesNotExistException
     */
    public void removeMember(User member)
            throws PrivilegeInsufficientException, DoesNotExistException, AuthorizationException, UpdateException {

        if (PrivilegeManager.hasCommitteePrivilege(
                logInManager.getLoggedInUser(),
                eventManager.getSelectedEvent(),
                selectedCommittee)) {
            selectedCommittee.getMemberList().remove(member);
            ArrayList<Integer> newMemberList = committeesTable.getCommitteeMembers(selectedCommittee.getCOMMITTEE_ID());
            newMemberList.remove(new Integer(member.getUserId()));
            committeesTable.setCommitteeMembers(selectedCommittee.getCOMMITTEE_ID(), newMemberList);
        }
    }

    /**
     * create a task entry in the database, if the user has sufficient privilege
     *
     * @param task the task to create
     * @return the task object created in the database
     * @throws PrivilegeInsufficientException
     * @throws DoesNotExistException
     * @throws DuplicateInsertionException
     */
    public Task createTask(Task task)
            throws PrivilegeInsufficientException, DoesNotExistException, DuplicateInsertionException, UpdateException, AuthorizationException {

        Task newTask = null;
        if (PrivilegeManager.hasCommitteePrivilege(
                logInManager.getLoggedInUser(),
                eventManager.getSelectedEvent(),
                selectedCommittee)) {
            ArrayList<Integer> responsibleIDList = task.getResponsibleList().stream()
                    .map(User::getUserId)
                    .collect(Collectors.toCollection(ArrayList::new));

            newTask = new Task(tasksTable.createTask(new InputTask(
                    task.getDescription(), task.getTitle(), task.getLocation().getDetails(), task.getLocation().getStreet(), task.getLocation().getCity(), //FIX THIS LINE FOR ACTUAL TITLE!!!!
                    task.getLocation().getState(), task.getLocation().getZipCode(), task.getLocation().getCountry(),
                    task.getTimeSchedule().getStartDateTimeTimestamp(), task.getTimeSchedule().getEndDateTimeTimestamp(),
                    (task.getCompleted() ? 1 : 0), responsibleIDList)), task);

            selectedCommittee.getTaskList().add(newTask);
            ArrayList<Integer> newTaskList = committeesTable.getTaskList(selectedCommittee.getCOMMITTEE_ID());
            newTaskList.add(newTask.getTASK_ID());
            committeesTable.setTaskList(selectedCommittee.getCOMMITTEE_ID(), newTaskList);
        }
        return newTask;
    }

    /**
     * delete the selected task from the database, if the user has sufficient
     * privilege
     *
     * @param task the task to delete
     * @throws PrivilegeInsufficientException
     * @throws DoesNotExistException
     */
    public void deleteTask(Task task)
            throws PrivilegeInsufficientException, DoesNotExistException, AuthorizationException, UpdateException {

        if (PrivilegeManager.hasCommitteePrivilege(
                logInManager.getLoggedInUser(),
                eventManager.getSelectedEvent(),
                selectedCommittee)) {
            selectedCommittee.getTaskList().remove(task);
            ArrayList<Integer> newTaskList = committeesTable.getTaskList(selectedCommittee.getCOMMITTEE_ID());
            newTaskList.remove(new Integer(task.getTASK_ID()));
            committeesTable.setTaskList(selectedCommittee.getCOMMITTEE_ID(), newTaskList);
            tasksTable.removeTask(task.getTASK_ID());
        }
    }
}