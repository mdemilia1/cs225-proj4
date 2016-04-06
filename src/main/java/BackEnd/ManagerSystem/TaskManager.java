package BackEnd.ManagerSystem;

import BackEnd.ManagerSystem.ManagerExceptions.PrivilegeInsufficientException;
import BackEnd.EventSystem.Task;
import BackEnd.EventSystem.TimeSchedule;
import BackEnd.UserSystem.Location;
import BackEnd.UserSystem.User;
import EMS_Database.DoesNotExistException;
import EMS_Database.impl.Tasks_Table;
import java.util.ArrayList;

/**
 * This class serves as a liaison between the GUI and the back end and the data.
 * It checks to see whether a user has the proper privileges to change
 * something, and if the user does, then edits the database accordingly. It also
 * provides ready-to-use methods for the GUI to call.
 *
 * @author Julian Kuk
 */
public class TaskManager {

    private Tasks_Table tasksTable;
    private Task selectedTask;
    private LoginManager logInManager;
    private EventManager eventManager;
    private CommitteeManager committeeManager;

    /**
     * initializes the task manager, and connects to the task database
     */
    public TaskManager() {
        tasksTable = new Tasks_Table();
    }

    public void connectManagers(LoginManager logInManager, EventManager eventManager, CommitteeManager committeeManager) {
        this.logInManager = logInManager;
        this.eventManager = eventManager;
        this.committeeManager = committeeManager;
    }

    /**
     * returns the tasks table
     *
     * @return the task table
     */
    public Tasks_Table getTasksTable() {
        return tasksTable;
    }

    /**
     * stores the task selected by the user
     *
     * @param selectedTask the selected task
     */
    public void setSelectedTask(Task selectedTask) {
        this.selectedTask = selectedTask;
    }

    /**
     * returns the selected task
     *
     * @return the selected task
     */
    public Task getSelectedTask() {
        return selectedTask;
    }

    /**
     * adds a user to the responsible list of the selected task, if the logged
     * in user has sufficient privilege
     *
     * @param responsible the user to add to the list
     * @throws PrivilegeInsufficientException
     * @throws DoesNotExistException
     */
    public void addResponsible(User responsible)
            throws PrivilegeInsufficientException, DoesNotExistException {

        if (PrivilegeManager.hasTaskPrivilege(
                logInManager.getLoggedInUser(),
                eventManager.getSelectedEvent(),
                committeeManager.getSelectedCommittee(),
                selectedTask)) {
            selectedTask.getResponsibleList().add(responsible);
            ArrayList<Integer> newResponsibleList = tasksTable.getAuthority(selectedTask.getTASK_ID());
            newResponsibleList.add(responsible.getUserId());
            tasksTable.setAuthority(selectedTask.getTASK_ID(), newResponsibleList);
        }
    }

    /**
     * removes a user from the responsible list of the selected task if the
     * logged in user has sufficient privilege
     *
     * @param responsible the user to remove
     * @throws PrivilegeInsufficientException
     * @throws DoesNotExistException
     */
    public void removeResponsible(User responsible)
            throws PrivilegeInsufficientException, DoesNotExistException {

        if (PrivilegeManager.hasTaskPrivilege(
                logInManager.getLoggedInUser(),
                eventManager.getSelectedEvent(),
                committeeManager.getSelectedCommittee(),
                selectedTask)) {
            selectedTask.getResponsibleList().remove(responsible);
            ArrayList<Integer> newResponsibleList = tasksTable.getAuthority(selectedTask.getTASK_ID());
            newResponsibleList.remove(new Integer(responsible.getUserId())); //!!HERE
            tasksTable.setAuthority(selectedTask.getTASK_ID(), newResponsibleList);
        }
    }

    /**
     * edits the title of the selected task if the user has sufficient privilege
     *
     * @param title the title to change to
     * @throws PrivilegeInsufficientException
     * @throws DoesNotExistException
     */
    public void editTitle(String title)
            throws PrivilegeInsufficientException, DoesNotExistException {

        if (PrivilegeManager.hasTaskPrivilege(
                logInManager.getLoggedInUser(),
                eventManager.getSelectedEvent(),
                committeeManager.getSelectedCommittee(),
                selectedTask)) {
            selectedTask.setTitle(title);
            tasksTable.setTitle(selectedTask.getTASK_ID(), title);
        }
    }

    /**
     * edits the completion status of the selected task if the user has
     * sufficient privilege
     *
     * @param completed the status to change to
     * @throws PrivilegeInsufficientException
     * @throws DoesNotExistException
     */
    public void editCompleted(boolean completed)
            throws PrivilegeInsufficientException, DoesNotExistException {

        if (PrivilegeManager.hasTaskPrivilege(
                logInManager.getLoggedInUser(),
                eventManager.getSelectedEvent(),
                committeeManager.getSelectedCommittee(),
                selectedTask)) {
            selectedTask.setCompleted(completed);
            tasksTable.setComplete(selectedTask.getTASK_ID(), completed == true ? 1 : 0);
        }
    }

    /**
     * edits the description of the selected task if the user has sufficient
     * privilege
     *
     * @param description the description to change to
     * @throws PrivilegeInsufficientException
     * @throws DoesNotExistException
     */
    public void editDescription(String description)
            throws PrivilegeInsufficientException, DoesNotExistException {

        if (PrivilegeManager.hasTaskPrivilege(
                logInManager.getLoggedInUser(),
                eventManager.getSelectedEvent(),
                committeeManager.getSelectedCommittee(),
                selectedTask)) {
            selectedTask.setDescription(description);
            tasksTable.setDescription(selectedTask.getTASK_ID(), description);
        }
    }

    /**
     * edits the location of the selected task if the user has sufficient
     * privilege
     *
     * @param location the location to change to
     * @throws PrivilegeInsufficientException
     * @throws DoesNotExistException
     */
    public void editLocation(Location location)
            throws PrivilegeInsufficientException, DoesNotExistException {

        if (PrivilegeManager.hasTaskPrivilege(
                logInManager.getLoggedInUser(),
                eventManager.getSelectedEvent(),
                committeeManager.getSelectedCommittee(),
                selectedTask)) {
            selectedTask.setLocation(location);
            tasksTable.setDetails(selectedTask.getTASK_ID(), location.getDetails());
            tasksTable.setStreet(selectedTask.getTASK_ID(), location.getStreet());
            tasksTable.setCity(selectedTask.getTASK_ID(), location.getCity());
            tasksTable.setState(selectedTask.getTASK_ID(), location.getState());
            tasksTable.setZipcode(selectedTask.getTASK_ID(), location.getZipCode());
            tasksTable.setCountry(selectedTask.getTASK_ID(), location.getCountry());
        }
    }

    /**
     * edits the time schedule of the selected task if the user has sufficient
     * privilege
     *
     * @param timeSchedule the time schedule to change to
     * @throws PrivilegeInsufficientException
     * @throws DoesNotExistException
     */
    public void editTimeSchedule(TimeSchedule timeSchedule)
            throws PrivilegeInsufficientException, DoesNotExistException {

        if (PrivilegeManager.hasTaskPrivilege(
                logInManager.getLoggedInUser(),
                eventManager.getSelectedEvent(),
                committeeManager.getSelectedCommittee(),
                selectedTask)) {
            selectedTask.getTimeSchedule().setStartDateTime(timeSchedule.getStartDateTimeTimestamp());
            tasksTable.setStartDate(selectedTask.getTASK_ID(), selectedTask.getTimeSchedule().getStartDateTimeTimestamp());
            selectedTask.getTimeSchedule().setEndDateTime(timeSchedule.getStartDateTimeTimestamp());
            tasksTable.setEndDate(selectedTask.getTASK_ID(), selectedTask.getTimeSchedule().getStartDateTimeTimestamp());
        }
    }

    /**
     * edits the start date / time of the selected sub event if the user has
     * sufficient privilege
     *
     * @param year the year to change to
     * @param month the month to change to
     * @param day the day to change to
     * @param hour the hour to change to
     * @param minute the minute to change to
     * @throws PrivilegeInsufficientException
     * @throws DoesNotExistException
     */
    public void editStartDateTime(int year, int month, int day, int hour, int minute)
            throws PrivilegeInsufficientException, DoesNotExistException {

        if (PrivilegeManager.hasTaskPrivilege(
                logInManager.getLoggedInUser(),
                eventManager.getSelectedEvent(),
                committeeManager.getSelectedCommittee(),
                selectedTask)) {
            selectedTask.getTimeSchedule().setStartDateTime(year, month, day, hour, minute);
            tasksTable.setStartDate(selectedTask.getTASK_ID(), selectedTask.getTimeSchedule().getStartDateTimeTimestamp());
        }
    }

    /**
     * edits the end date / time of the selected sub event if the user has
     * sufficient privilege
     *
     * @param year the year to change to
     * @param month the month to change to
     * @param day the day to change to
     * @param hour the hour to change to
     * @param minute the minute to change to
     * @throws PrivilegeInsufficientException
     * @throws DoesNotExistException
     */
    public void editEndDateTime(int year, int month, int day, int hour, int minute)
            throws PrivilegeInsufficientException, DoesNotExistException {

        if (PrivilegeManager.hasTaskPrivilege(
                logInManager.getLoggedInUser(),
                eventManager.getSelectedEvent(),
                committeeManager.getSelectedCommittee(),
                selectedTask)) {
            selectedTask.getTimeSchedule().setEndDateTime(year, month, day, hour, minute);
            tasksTable.setEndDate(selectedTask.getTASK_ID(), selectedTask.getTimeSchedule().getEndDateTimeTimestamp());
        }
    }
}