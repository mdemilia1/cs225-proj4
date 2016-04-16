package BackEnd.ManagerSystem;

import BackEnd.ManagerSystem.ManagerExceptions.PrivilegeInsufficientException;
import BackEnd.EventSystem.SubEvent;
import BackEnd.EventSystem.TimeSchedule;
import BackEnd.UserSystem.Location;
import EMS_Database.DoesNotExistException;
import EMS_Database.impl.SubEvent_Table;
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
public class SubEventManager {

    private SubEvent_Table subEventsTable;
    private SubEvent selectedSubEvent;
    private LoginManager logInManager;
    private EventManager eventManager;

    /**
     * initializes the sub event manager, and connects to the sub event database
     */
    public SubEventManager() {
        subEventsTable = new SubEvent_Table();
    }

    void connectManagers(LoginManager logInManager, EventManager eventManager) {
        this.logInManager = logInManager;
        this.eventManager = eventManager;
    }

    /**
     * returns the sub event table
     *
     * @return the sub event table
     */
    SubEvent_Table getSubEventsTable() {
        return subEventsTable;
    }

    /**
     * stores the sub event selected by the user
     *
     * @param selectedSubEvent the selected sub event
     */
    public void setSelectedSubEvent(SubEvent selectedSubEvent) {
        this.selectedSubEvent = selectedSubEvent;
    }

    /**
     * returns the selected sub event
     *
     * @return the selected sub event
     */
    public SubEvent getSelectedSubEvent() {
        return selectedSubEvent;
    }

    /**
     * edits the title of the selected sub event if the user has sufficient
     * privilege
     *
     * @param title the title to change to
     * @throws PrivilegeInsufficientException
     * @throws DoesNotExistException
     */
    public void editTitle(String title)
            throws PrivilegeInsufficientException, DoesNotExistException, AuthorizationException, UpdateException {

        if (PrivilegeManager.hasSubEventPrivilege(
                logInManager.getLoggedInUser(),
                eventManager.getSelectedEvent())) {
            selectedSubEvent.setTitle(title);
            subEventsTable.setTitle(selectedSubEvent.getSUB_EVENT_ID(), title);
        }
    }

    /**
     * edits the description of the selected sub event if the user has
     * sufficient privilege
     *
     * @param description the description to change to
     * @throws PrivilegeInsufficientException
     * @throws DoesNotExistException
     */
    public void editDescription(String description)
            throws PrivilegeInsufficientException, DoesNotExistException, AuthorizationException, UpdateException {

        if (PrivilegeManager.hasSubEventPrivilege(
                logInManager.getLoggedInUser(),
                eventManager.getSelectedEvent())) {
            selectedSubEvent.setDescription(description);
            subEventsTable.setDescription(selectedSubEvent.getSUB_EVENT_ID(), description);
        }
    }

    /**
     * edits the location of the selected sub event if the user has sufficient
     * privilege
     *
     * @param location the location to change to
     * @throws PrivilegeInsufficientException
     * @throws DoesNotExistException
     */
    public void editLocation(Location location)
            throws PrivilegeInsufficientException, DoesNotExistException, AuthorizationException, UpdateException {

        if (PrivilegeManager.hasSubEventPrivilege(
                logInManager.getLoggedInUser(),
                eventManager.getSelectedEvent())) {
            selectedSubEvent.setLocation(location);
            subEventsTable.setDetails(selectedSubEvent.getSUB_EVENT_ID(), location.getDetails());
            subEventsTable.setStreet(selectedSubEvent.getSUB_EVENT_ID(), location.getStreet());
            subEventsTable.setCity(selectedSubEvent.getSUB_EVENT_ID(), location.getCity());
            subEventsTable.setState(selectedSubEvent.getSUB_EVENT_ID(), location.getState());
            subEventsTable.setZipcode(selectedSubEvent.getSUB_EVENT_ID(), location.getZipCode());
            subEventsTable.setCountry(selectedSubEvent.getSUB_EVENT_ID(), location.getCountry());
        }
    }

    /**
     * edits the time schedule of the selected sub event if the user has
     * sufficient privilege
     *
     * @param timeSchedule the time schedule to change to
     * @throws PrivilegeInsufficientException
     * @throws DoesNotExistException
     */
    public void editTimeSchedule(TimeSchedule timeSchedule)
            throws PrivilegeInsufficientException, DoesNotExistException, AuthorizationException, UpdateException {

        if (PrivilegeManager.hasSubEventPrivilege(
                logInManager.getLoggedInUser(),
                eventManager.getSelectedEvent())) {
            selectedSubEvent.getTimeSchedule().setStartDateTime(timeSchedule.getStartDateTimeTimestamp());
            subEventsTable.setStartDate(selectedSubEvent.getSUB_EVENT_ID(), selectedSubEvent.getTimeSchedule().getStartDateTimeTimestamp());
            selectedSubEvent.getTimeSchedule().setEndDateTime(timeSchedule.getStartDateTimeTimestamp());
            subEventsTable.setEndDate(selectedSubEvent.getSUB_EVENT_ID(), selectedSubEvent.getTimeSchedule().getStartDateTimeTimestamp());
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
            throws PrivilegeInsufficientException, DoesNotExistException, AuthorizationException, UpdateException {

        if (PrivilegeManager.hasSubEventPrivilege(
                logInManager.getLoggedInUser(),
                eventManager.getSelectedEvent())) {
            selectedSubEvent.getTimeSchedule().setStartDateTime(year, month, day, hour, minute);
            subEventsTable.setStartDate(selectedSubEvent.getSUB_EVENT_ID(), selectedSubEvent.getTimeSchedule().getStartDateTimeTimestamp());
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
            throws PrivilegeInsufficientException, DoesNotExistException, AuthorizationException, UpdateException {

        if (PrivilegeManager.hasSubEventPrivilege(
                logInManager.getLoggedInUser(),
                eventManager.getSelectedEvent())) {
            selectedSubEvent.getTimeSchedule().setEndDateTime(year, month, day, hour, minute);
            subEventsTable.setEndDate(selectedSubEvent.getSUB_EVENT_ID(), selectedSubEvent.getTimeSchedule().getEndDateTimeTimestamp());
        }
    }
}