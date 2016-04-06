package BackEnd.ManagerSystem;

import BackEnd.ManagerSystem.ManagerExceptions.PrivilegeInsufficientException;
import BackEnd.EventSystem.Budget;
import BackEnd.EventSystem.Committee;
import BackEnd.EventSystem.Event;
import BackEnd.EventSystem.Expense;
import BackEnd.EventSystem.Income;
import BackEnd.EventSystem.SubEvent;
import BackEnd.EventSystem.Task;
import BackEnd.EventSystem.TimeSchedule;
import BackEnd.UserSystem.Location;
import BackEnd.UserSystem.Participant;
import BackEnd.UserSystem.User;
import EMS_Database.DoesNotExistException;
import EMS_Database.DuplicateInsertionException;
import EMS_Database.InputCommittee;
import EMS_Database.InputEventData;
import EMS_Database.InputSubEventData;
import EMS_Database.InputUser;
import EMS_Database.impl.Committees_Table;
import EMS_Database.impl.Events_Table;
import EMS_Database.impl.Expense_Table;
import EMS_Database.impl.Income_Table;
import EMS_Database.impl.SubEvent_Table;
import EMS_Database.impl.Tasks_Table;
import EMS_Database.impl.UserData_Table;
import java.util.ArrayList;

/**
 * This class serves as a liaison between the GUI and the back end and the data.
 * It checks to see whether a user has the proper privileges to change
 * something, and if the user does, then edits the database accordingly. It also
 * provides ready-to-use methods for the GUI to call.
 *
 * @author Julian Kuk
 */
public class EventManager {

    private UserData_Table usersTable;
    private Events_Table eventsTable;
    private SubEvent_Table subEventsTable;
    private Committees_Table committeesTable;
    private Tasks_Table tasksTable;
    private Income_Table incomeTable;
    private Expense_Table expenseTable;
    private ArrayList<Event> eventList;
    private Event selectedEvent;
    private LoginManager logInManager;
    private UserManager userManager;

    /**
     * initializes the event manager, and connects to the event database
     *
     * @param userList the list of users in the database
     * @param usersTable the users table
     * @param tasksTable the tasks table
     * @param subEventsTable the sub events table
     * @param committeesTable the committees table
     * @param incomeTable the income table
     * @param expenseTable the expense table
     * @throws DoesNotExistException
     */
    public EventManager(
            ArrayList<Participant> userList, UserData_Table usersTable, Tasks_Table tasksTable,
            SubEvent_Table subEventsTable, Committees_Table committeesTable,
            Income_Table incomeTable, Expense_Table expenseTable)
            throws DoesNotExistException {
        this.eventList = new ArrayList<Event>();
        this.usersTable = usersTable;
        this.eventsTable = new Events_Table();
        this.subEventsTable = subEventsTable;
        this.committeesTable = committeesTable;
        this.tasksTable = tasksTable;
        this.incomeTable = incomeTable;
        this.expenseTable = expenseTable;
        rebuildEventList(userList);
    }
    
    public void connectManagers(LoginManager logInManager, UserManager userManager){
        this.logInManager = logInManager;
        this.userManager = userManager;
    }

    /**
     * returns the events table
     *
     * @return the events table
     */
    public Events_Table getEventsTable() {
        return eventsTable;
    }

    /**
     * returns the event list
     *
     * @return the event list
     */
    public ArrayList<Event> getEventList() {
        return eventList;
    }

    /**
     * stores the event selected by the user
     *
     * @param selectedEvent the selected event
     */
    public void setSelectedEvent(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    /**
     * returns the selected event
     *
     * @return the selected event
     */
    public Event getSelectedEvent() {
        return selectedEvent;
    }

    /**
     * create an event entry in the database, if the user has sufficient
     * privilege
     *
     * @param event the event to create
     * @return the event object created in the database proper ID
     * @throws PrivilegeInsufficientException
     * @throws DuplicateInsertionException
     */
    public Event createEvent(Event event)
            throws PrivilegeInsufficientException, DuplicateInsertionException {

        Event newEvent = null;
        User loggedInUser = logInManager.getLoggedInUser();
        if (PrivilegeManager.hasEventCreationPrivilege(loggedInUser)) {
            ArrayList<Integer> organizerIDList, subEventIDList, participantIDList, committeeIDList;
            organizerIDList = new ArrayList<Integer>();
            organizerIDList.add(loggedInUser.getUserId());
            subEventIDList = new ArrayList<Integer>();
            participantIDList = new ArrayList<Integer>();
            participantIDList.add(loggedInUser.getUserId());
            System.out.println(participantIDList);
            committeeIDList = new ArrayList<Integer>();

            newEvent = new Event(eventsTable.createEvent(new InputEventData(
                    event.getDescription(), event.getLocation().getDetails(), event.getTitle() , "IGNORE",
                    event.getTimeSchedule().getStartDateTimeTimestamp(), event.getTimeSchedule().getEndDateTimeTimestamp(),
                    0, committeeIDList, organizerIDList, subEventIDList, participantIDList,
                    event.getLocation().getStreet(), event.getLocation().getCity(),
                    event.getLocation().getState(), event.getLocation().getZipCode(),
                    event.getLocation().getCountry())), event);

            eventList.add(newEvent);
            selectedEvent = newEvent;
        }
        return newEvent;
    }

    /**
     * delete the selected event from the database, if the user has sufficient
     * privilege
     *
     * @throws PrivilegeInsufficientException
     * @throws DoesNotExistException
     */
    public void deleteEvent()
            throws PrivilegeInsufficientException, DoesNotExistException {

        User loggedInUser = logInManager.getLoggedInUser();
        if (PrivilegeManager.hasEventPrivilege(loggedInUser, selectedEvent)) {
            for (int i = selectedEvent.getCommitteeList().size() - 1; i >= 0; i--) {
                deleteCommittee(selectedEvent.getCommitteeList().get(i));
            }
            for (int j = selectedEvent.getSubEventList().size() - 1; j >= 0; j--) {
                deleteSubEvent(selectedEvent.getSubEventList().get(j));
            }
            for (int k = selectedEvent.getParticipantList().size() - 1; k >= 0; k--) {
                deleteParticipant(selectedEvent.getParticipantList().get(k));
            }
            eventsTable.removeEvent(selectedEvent.getEVENT_ID());
            eventList.remove(selectedEvent);
            selectedEvent = null;
        }
    }

    /**
     * add an organizer to the organizer list, if the user has sufficient
     * privilege
     *
     * @param organizer the organizer to add
     * @throws PrivilegeInsufficientException
     * @throws DoesNotExistException
     */
    public void addOrganizer(User organizer)
            throws DoesNotExistException {

        //User loggedInUser = logInManager.getLoggedInUser();
        //if (PrivilegeManager.hasEventPrivilege(loggedInUserr, selectedEvent)) {
            Integer organizerUserID = new Integer(usersTable.getUIDByEmail(organizer.getEmailAddress()));
            ArrayList<Integer> newOrganizerList = eventsTable.getOrganizerList(selectedEvent.getEVENT_ID());
            newOrganizerList.add(organizerUserID);

            eventsTable.setOrganizerList(selectedEvent.getEVENT_ID(), newOrganizerList);
            selectedEvent.getOrganizerList().add(organizer);
        //}
    }
    
    /**
     * remove an organizer from the list, if the user has sufficient privilege
     *
     * @param organizer the organizer to remove
     * @throws PrivilegeInsufficientException
     * @throws DoesNotExistException
     */
    public void removeOrganizer(User organizer)
            throws DoesNotExistException {

        //User loggedInUser = logInManager.getLoggedInUser();
        //User selectedUser = userManager.getSelectedUser();
        //if (PrivilegeManager.hasUserPrivilege(loggedInUser, selectedUser)) {
            Integer organizerUserID = new Integer(usersTable.getUIDByEmail(organizer.getEmailAddress()));
            ArrayList<Integer> newOrganizerList = eventsTable.getOrganizerList(selectedEvent.getEVENT_ID());
            newOrganizerList.remove(organizerUserID);

            eventsTable.setOrganizerList(selectedEvent.getEVENT_ID(), newOrganizerList);
            selectedEvent.getOrganizerList().remove(organizer);
        //}
    }

    /**
     * create a sub event entry in the database, if the user has sufficient
     * privilege
     *
     * @param subEvent the sub event to create
     * @return the sub event object created in the database
     * @throws PrivilegeInsufficientException
     * @throws DoesNotExistException
     */
    public SubEvent createSubEvent(SubEvent subEvent)
            throws PrivilegeInsufficientException, DoesNotExistException {

        SubEvent newSubEvent = null;
        User loggedInUser = logInManager.getLoggedInUser();
        if (PrivilegeManager.hasSubEventPrivilege(loggedInUser, selectedEvent)) {

            newSubEvent = new SubEvent(subEventsTable.createSubEvent(
                    new InputSubEventData(subEvent.getDescription(), subEvent.getLocation().getDetails(), subEvent.getTitle(),
                    0, subEvent.getLocation().getStreet(), subEvent.getLocation().getCity(),
                    subEvent.getLocation().getState(), subEvent.getLocation().getZipCode(),
                    subEvent.getLocation().getCountry(), subEvent.getTimeSchedule().getStartDateTimeTimestamp(),
                    subEvent.getTimeSchedule().getEndDateTimeTimestamp())), subEvent);

            ArrayList<Integer> newSubEventList = eventsTable.getSubEventList(selectedEvent.getEVENT_ID());
            newSubEventList.add(newSubEvent.getSUB_EVENT_ID());

            eventsTable.setSubEventList(selectedEvent.getEVENT_ID(), newSubEventList);
            selectedEvent.getSubEventList().add(newSubEvent);
        }
        return newSubEvent;
    }

    /**
     * delete a sub event entry from the database, if the user has sufficient
     * privilege
     *
     * @param subEvent the sub event to delete
     * @throws PrivilegeInsufficientException
     * @throws DoesNotExistException
     */
    public void deleteSubEvent(SubEvent subEvent)
            throws PrivilegeInsufficientException, DoesNotExistException {

        User loggedInUser = logInManager.getLoggedInUser();
        if (PrivilegeManager.hasSubEventPrivilege(loggedInUser, selectedEvent)) {

            Integer subEventID = new Integer(subEvent.getSUB_EVENT_ID());
            ArrayList<Integer> newSubEventIDList = eventsTable.getSubEventList(selectedEvent.getEVENT_ID());
            newSubEventIDList.remove(subEventID);

            eventsTable.setSubEventList(selectedEvent.getEVENT_ID(), newSubEventIDList);
            subEventsTable.removeSubEvent(subEventID);
            selectedEvent.getSubEventList().remove(subEvent);
        }
    }

    /**
     * create a committee entry in the database, if the user has sufficient
     * privilege
     *
     * @param committee the committee to create
     * @return the committee object created in the database
     * @throws PrivilegeInsufficientException
     * @throws DoesNotExistException
     */
    public Committee createCommittee(Committee committee)
            throws PrivilegeInsufficientException, DoesNotExistException {

        Committee newCommittee = null;
        User loggedInUser = logInManager.getLoggedInUser();
        if (PrivilegeManager.hasEventPrivilege(loggedInUser, selectedEvent)) {
            ArrayList<Integer> budgetAccessIDList = new ArrayList<Integer>();
            for (int i = 0; i < committee.getBudgetAccessList().size(); i++) {
                budgetAccessIDList.add(committee.getBudgetAccessList().get(i).getUserId());
            }
            ArrayList<Integer> memberIDList = new ArrayList<Integer>();
            for (int i = 0; i < committee.getMemberList().size(); i++) {
                memberIDList.add(committee.getMemberList().get(i).getUserId());
            }
            ArrayList<Integer> incomeIDList = new ArrayList<Integer>();
            for (int i = 0; i < committee.getBudget().getIncomeList().size(); i++) {
                incomeIDList.add(committee.getBudget().getIncomeList().get(i).getBUDGET_ITEM_ID());
            }
            ArrayList<Integer> expenseIDList = new ArrayList<Integer>();
            for (int i = 0; i < committee.getBudget().getExpenseList().size(); i++) {
                expenseIDList.add(committee.getBudget().getExpenseList().get(i).getBUDGET_ITEM_ID());
            }
            ArrayList<Integer> taskIDList = new ArrayList<Integer>();
            for (int i = 0; i < committee.getTaskList().size(); i++) {
                taskIDList.add(committee.getTaskList().get(i).getTASK_ID());
            }

            if (committee.getChair().getUserId() == 0) {
                committee.setChair(loggedInUser);
            }
            newCommittee = new Committee(committeesTable.createCommittee(new InputCommittee(
                    committee.getTitle(), committee.getChair().getUserId(), budgetAccessIDList,
                    memberIDList, taskIDList, incomeIDList, expenseIDList, 0)), committee);

            ArrayList<Integer> newCommitteeIDList = eventsTable.getCommittee(selectedEvent.getEVENT_ID());
            newCommitteeIDList.add(newCommittee.getCOMMITTEE_ID());

            eventsTable.setCommittee(selectedEvent.getEVENT_ID(), newCommitteeIDList);
            selectedEvent.getCommitteeList().add(newCommittee);
        }
        return newCommittee;
    }

    /**
     * deletes a committee object from the database, if the user has sufficient
     * privilege
     *
     * @param committee the committee to delete
     * @throws PrivilegeInsufficientException
     * @throws DoesNotExistException
     */
    public void deleteCommittee(Committee committee)
            throws PrivilegeInsufficientException, DoesNotExistException {

        User loggedInUser = logInManager.getLoggedInUser();
        if (PrivilegeManager.hasEventPrivilege(loggedInUser, selectedEvent)) {
            deleteTaskList(committee);
            deleteIncomeList(committee);
            deleteExpenseList(committee);
            ArrayList<Integer> newCommitteeIDList = eventsTable.getCommittee(selectedEvent.getEVENT_ID());
            newCommitteeIDList.remove(new Integer(committee.getCOMMITTEE_ID()));

            eventsTable.setCommittee(selectedEvent.getEVENT_ID(), newCommitteeIDList);
            committeesTable.removeCommittee(committee.getCOMMITTEE_ID());
            selectedEvent.getCommitteeList().remove(committee);
        }
    }

    private void deleteTaskList(Committee committee)
            throws DoesNotExistException {

        ArrayList<Integer> taskIDList = committeesTable.getTaskList(committee.getCOMMITTEE_ID());
        for (Integer taskID : taskIDList) {
            tasksTable.removeTask(taskID);
        }
        for (int i = committee.getTaskList().size() - 1; i >= 0; i--) {
            committee.getTaskList().remove(i);
        }
    }

    private void deleteIncomeList(Committee committee)
            throws DoesNotExistException {

        ArrayList<Integer> incomeIDList = committeesTable.getIncome(committee.getCOMMITTEE_ID());
        for (Integer incomeID : incomeIDList) {
            incomeTable.removeBudgetItem(incomeID);
        }
        for (int i = committee.getBudget().getIncomeList().size() - 1; i >= 0; i--) {
            committee.getBudget().getIncomeList().remove(i);
        }
    }

    private void deleteExpenseList(Committee committee)
            throws DoesNotExistException {

        ArrayList<Integer> expenseIDList = committeesTable.getExpense(committee.getCOMMITTEE_ID());
        for (Integer expenseID : expenseIDList) {
            expenseTable.removeBudgetItem(expenseID);
        }
        for (int i = committee.getBudget().getExpenseList().size() - 1; i >= 0; i--) {
            committee.getBudget().getExpenseList().remove(i);
        }
    }

    /**
     * create a participant in the database, if the user has sufficient
     * privilege
     *
     * @param participant
     * @return the participant created in the database with the proper ID
     * @throws DoesNotExistException
     */
    public Participant createParticipant(Participant participant)
            throws DoesNotExistException {

        Participant newParticipant = null;
        User loggedInUser = logInManager.getLoggedInUser();
        ArrayList<Integer> newParticipantList = eventsTable.getParticipantList(selectedEvent.getEVENT_ID());
        if (loggedInUser == null) {
            newParticipant = new Participant(usersTable.createUser(new InputUser(participant)), participant);
            newParticipantList.add(newParticipant.getUserId());
            selectedEvent.getParticipantList().add(newParticipant);
        } else {
            newParticipantList.add(participant.getUserId());
            selectedEvent.getParticipantList().add(participant);
        }
        eventsTable.setParticipantList(selectedEvent.getEVENT_ID(), newParticipantList);
        return newParticipant;
    }

    /**
     *
     * @param participant
     * @throws DoesNotExistException
     */
    public void deleteParticipant(Participant participant)
            throws DoesNotExistException {

        if (!(participant instanceof User)) {
            usersTable.removeUser(participant.getUserId());
        }
        ArrayList<Integer> newParticipantIDList = eventsTable.getParticipantList(selectedEvent.getEVENT_ID());
        newParticipantIDList.remove(new Integer(participant.getUserId()));

        eventsTable.setParticipantList(selectedEvent.getEVENT_ID(), newParticipantIDList);
        selectedEvent.getParticipantList().remove(participant);
    }
    
    /**
     * edits the title of the selected event, if the user has
     * sufficient privilege
     *
     * @param title the title to change to
     * @throws PrivilegeInsufficientException
     * @throws DoesNotExistException
     */    
    public void editTitle(String title)
            throws PrivilegeInsufficientException, DoesNotExistException {

        User loggedInUser = logInManager.getLoggedInUser();
        if (PrivilegeManager.hasEventPrivilege(loggedInUser, selectedEvent)) {
            eventsTable.setTitle(selectedEvent.getEVENT_ID(), title);
            selectedEvent.setTitle(title);
        }
    }

    /**
     * edits the description of the selected event, if the user has
     * sufficient privilege
     *
     * @param description the description to change to
     * @throws PrivilegeInsufficientException
     * @throws DoesNotExistException
     */
    public void editDescription(String description)
            throws PrivilegeInsufficientException, DoesNotExistException {

        User loggedInUser = logInManager.getLoggedInUser();
        if (PrivilegeManager.hasEventPrivilege(loggedInUser, selectedEvent)) {
            eventsTable.setDescription(selectedEvent.getEVENT_ID(), description);
            selectedEvent.setDescription(description);
        }
    }

    /**
     * edit the location of the selected event, if the user has sufficient
     * privilege
     *
     * @param location
     * @throws PrivilegeInsufficientException
     * @throws DoesNotExistException
     */
    public void editLocation(Location location)
            throws PrivilegeInsufficientException, DoesNotExistException {

        User loggedInUser = logInManager.getLoggedInUser();
        if (PrivilegeManager.hasEventPrivilege(loggedInUser, selectedEvent)) {
            eventsTable.setDetails(selectedEvent.getEVENT_ID(), location.getDetails());
            eventsTable.setStreet(selectedEvent.getEVENT_ID(), location.getStreet());
            eventsTable.setCity(selectedEvent.getEVENT_ID(), location.getCity());
            eventsTable.setState(selectedEvent.getEVENT_ID(), location.getState());
            eventsTable.setZipcode(selectedEvent.getEVENT_ID(), location.getZipCode());
            eventsTable.setCountry(selectedEvent.getEVENT_ID(), location.getCountry());
            selectedEvent.setLocation(location);
        }
    }

    /**
     * edit the start date / time of the selected event, if the user has
     * sufficient privilege
     *
     * @param year the year
     * @param month the month
     * @param day the day
     * @param hour the hour
     * @param minute the minute
     * @throws PrivilegeInsufficientException
     * @throws DoesNotExistException
     */
    public void editStartDateTime(int year, int month, int day, int hour, int minute)
            throws PrivilegeInsufficientException, DoesNotExistException {

        User loggedInUser = logInManager.getLoggedInUser();
        if (PrivilegeManager.hasEventPrivilege(loggedInUser, selectedEvent)) {
            eventsTable.setStartDate(selectedEvent.getEVENT_ID(), selectedEvent.getTimeSchedule().getStartDateTimeTimestamp());
            selectedEvent.getTimeSchedule().setStartDateTime(year, month, day, hour, minute);
        }
    }

    /**
     * edits the time schedule for the selected event, if the user has
     * sufficient privilege
     *
     * @param timeSchedule the new time schedule
     * @throws PrivilegeInsufficientException
     * @throws DoesNotExistException
     */
    public void editTimeSchedule(TimeSchedule timeSchedule)
            throws PrivilegeInsufficientException, DoesNotExistException {

        User loggedInUser = logInManager.getLoggedInUser();
        if (PrivilegeManager.hasEventPrivilege(loggedInUser, selectedEvent)) {
            selectedEvent.getTimeSchedule().setStartDateTime(timeSchedule.getStartDateTimeTimestamp());
            eventsTable.setStartDate(selectedEvent.getEVENT_ID(), selectedEvent.getTimeSchedule().getStartDateTimeTimestamp());
            selectedEvent.getTimeSchedule().setEndDateTime(timeSchedule.getStartDateTimeTimestamp());
            eventsTable.setEndDate(selectedEvent.getEVENT_ID(), selectedEvent.getTimeSchedule().getStartDateTimeTimestamp());
        }
    }

    /**
     * edit the end date / time of the selected event, if the user has
     * sufficient privilege
     *
     * @param year the year
     * @param month the month
     * @param day the day
     * @param hour the hour
     * @param minute the minute
     * @throws PrivilegeInsufficientException
     * @throws DoesNotExistException
     */
    public void editEndDateTime(int year, int month, int day, int hour, int minute)
            throws PrivilegeInsufficientException, DoesNotExistException {

        User loggedInUser = logInManager.getLoggedInUser();
        if (PrivilegeManager.hasEventPrivilege(loggedInUser, selectedEvent)) {
            eventsTable.setEndDate(selectedEvent.getEVENT_ID(), selectedEvent.getTimeSchedule().getEndDateTimeTimestamp());
            selectedEvent.getTimeSchedule().setEndDateTime(year, month, day, hour, minute);
        }
    }

    /**
     * private method for rebuilding the event list
     *
     * @param userList the list of users in the database
     * @throws DoesNotExistException
     */
    private void rebuildEventList(ArrayList<Participant> userList)
            throws DoesNotExistException {

        ArrayList<Integer> eventIDList = eventsTable.currentUIDList("EVENTS");
        for (Integer eventID : eventIDList) {
            eventList.add(rebuildEvent(eventID, userList));
        }
    }

    private Event rebuildEvent(int eventID, ArrayList<Participant> userList)
            throws DoesNotExistException {

        Event event = new Event(eventID, eventsTable.getTitle(eventID));
        event.setDescription(eventsTable.getDescription(eventID));
        event.setSubEventList(rebuildSubEventList(eventID));
        event.setOrganizerList(rebuildOrganizerList(eventID, userList));
        event.setParticipantList(rebuildParticipantList(eventID, userList));
        event.setCommitteeList(rebuildCommitteeList(eventID, userList));
        event.setLocation(
                new Location(eventsTable.getStreet(eventID), eventsTable.getCity(eventID),
                eventsTable.getState(eventID), eventsTable.getZipcode(eventID),
                eventsTable.getCountry(eventID), eventsTable.getDetails(eventID)));
        event.getTimeSchedule().setStartDateTime(eventsTable.getStartDate(eventID));
        event.getTimeSchedule().setEndDateTime(eventsTable.getEndDate(eventID));

        return event;
    }

    private ArrayList<SubEvent> rebuildSubEventList(int eventID)
            throws DoesNotExistException {

        ArrayList<SubEvent> subEventList = new ArrayList<SubEvent>();
        for (Integer subEventID : eventsTable.getSubEventList(eventID)) {
            subEventList.add(rebuildSubEvent(subEventID));
        }
        return subEventList;
    }

    private SubEvent rebuildSubEvent(int subEventID)
            throws DoesNotExistException {

        SubEvent subEvent = new SubEvent(subEventID, subEventsTable.getTitle(subEventID));
        subEvent.setDescription(subEventsTable.getDescription(subEventID));
        subEvent.setLocation(
                new Location(subEventsTable.getStreet(subEventID), subEventsTable.getCity(subEventID),
                subEventsTable.getState(subEventID), subEventsTable.getZipcode(subEventID),
                subEventsTable.getCountry(subEventID), subEventsTable.getDetails(subEventID)));
        subEvent.getTimeSchedule().setStartDateTime(subEventsTable.getStartDate(subEventID));
        subEvent.getTimeSchedule().setEndDateTime(subEventsTable.getEndDate(subEventID));

        return subEvent;
    }

    private ArrayList<User> rebuildOrganizerList(int eventID, ArrayList<Participant> userList)
            throws DoesNotExistException {

        ArrayList<User> organizerList = new ArrayList<User>();
        ArrayList<Integer> organizerIDList = eventsTable.getOrganizerList(eventID);
        for (Participant user : userList) {
            if (organizerIDList.contains(user.getUserId())) {
                organizerList.add((User) user);
            }
        }
        return organizerList;
    }

    private ArrayList<Participant> rebuildParticipantList(int eventID, ArrayList<Participant> userList)
            throws DoesNotExistException {

        ArrayList<Participant> participantList = new ArrayList<Participant>();
        ArrayList<Integer> participantIDList = eventsTable.getParticipantList(eventID);
        for (Participant participant : userList) {
            if (participantIDList.contains(participant.getUserId())) {
                participantList.add(participant);
            }
        }
        return participantList;
    }

    private ArrayList<Committee> rebuildCommitteeList(int eventID, ArrayList<Participant> userList)
            throws DoesNotExistException {

        ArrayList<Committee> committeeList = new ArrayList<Committee>();
        for (Integer committeeID : eventsTable.getCommittee(eventID)) {
            committeeList.add(rebuildCommittee(committeeID, userList));
        }
        return committeeList;
    }

    private Committee rebuildCommittee(int committeeID, ArrayList<Participant> userList)
            throws DoesNotExistException {

        Committee committee = new Committee(committeeID, committeesTable.getTitle(committeeID));
        for (Participant user : userList) {
            if (user.getUserId() == committeesTable.getChairman(committeeID)) {
                committee.setChair((User) user);
                break;
            }
        }
        committee.setMemberList(rebuildCommitteeMemberList(committeeID, userList));
        committee.setBudgetAccessList(rebuildBudgetAccessList(committeeID, userList));
        committee.setTaskList(rebuildTaskList(committeeID, userList));
        Budget newBudget = new Budget();
        newBudget.setIncomeList(rebuildIncomeList(committeeID));
        newBudget.setExpenseList(rebuildExpenseList(committeeID));
        committee.setBudget(newBudget);

        return committee;
    }

    private ArrayList<User> rebuildCommitteeMemberList(int committeeID, ArrayList<Participant> userList)
            throws DoesNotExistException {
        
        ArrayList<User> memberList = new ArrayList<User>();
        ArrayList<Integer> memberIDList = committeesTable.getCommitteeMembers(committeeID);

        for (Participant user : userList) {
            if (memberIDList.contains(user.getUserId())) {
                memberList.add((User) user);
            }
        }
        return memberList;
    }

    private ArrayList<User> rebuildBudgetAccessList(int committeeID, ArrayList<Participant> userList)
            throws DoesNotExistException {
        
        ArrayList<User> budgetAccessList = new ArrayList<User>();
        ArrayList<Integer> budgetAccessIDList = committeesTable.getBudgetAccessList(committeeID);

        for (Participant user : userList) {
            if (budgetAccessIDList.contains(user.getUserId())) {
                budgetAccessList.add((User) user);
            }
        }
        return budgetAccessList;
    }

    private ArrayList<Task> rebuildTaskList(int committeeID, ArrayList<Participant> userList)
            throws DoesNotExistException {
        
        ArrayList<Task> taskList = new ArrayList<Task>();
        for (Integer taskID : committeesTable.getTaskList(committeeID)) {
            taskList.add(rebuildTask(taskID, userList));
        }
        return taskList;

    }

    private Task rebuildTask(int taskID, ArrayList<Participant> userList)
            throws DoesNotExistException {
        
        Task task = new Task(taskID, tasksTable.getTitle(taskID));
        task.setLocation(new Location(tasksTable.getStreet(taskID), tasksTable.getCity(taskID),
                tasksTable.getState(taskID), tasksTable.getZipcode(taskID), tasksTable.getCountry(taskID),
                tasksTable.getDetails(taskID)));
        task.getTimeSchedule().setStartDateTime(tasksTable.getStartDate(taskID));
        task.getTimeSchedule().setEndDateTime(tasksTable.getEndDate(taskID));

        task.setResponsibleList(rebuildResponsibleList(taskID, userList));
        task.setCompleted(tasksTable.getComplete(taskID) == 1 ? true : false);

        return task;
    }

    private ArrayList<User> rebuildResponsibleList(int taskID, ArrayList<Participant> userList)
            throws DoesNotExistException {
        
        ArrayList<User> responsibleList = new ArrayList<User>();
        ArrayList<Integer> responsibleIDList = tasksTable.getAuthority(taskID);

        for (Participant user : userList) {
            if (responsibleIDList.contains(user.getUserId())) {
                responsibleList.add((User) user);
            }
        }
        return responsibleList;
    }

    private ArrayList<Income> rebuildIncomeList(int committeeID)
            throws DoesNotExistException {
        
        ArrayList<Income> incomeList = new ArrayList<Income>();
        ArrayList<Integer> incomeIDList = committeesTable.getIncome(committeeID);
        for (Integer incomeID : incomeIDList) {
            incomeList.add(rebuildIncome(incomeID));
        }
        return incomeList;
    }

    private Income rebuildIncome(int incomeID)
            throws DoesNotExistException {
        return new Income(incomeID, incomeTable.getValue(incomeID), incomeTable.getDescription(incomeID));
    }

    private ArrayList<Expense> rebuildExpenseList(int committeeID)
            throws DoesNotExistException {

        ArrayList<Expense> expenseList = new ArrayList<Expense>();
        ArrayList<Integer> expenseIDList = committeesTable.getExpense(committeeID);
        for (Integer expenseID : expenseIDList) {
            expenseList.add(rebuildExpense(expenseID));
        }
        return expenseList;
    }

    private Expense rebuildExpense(int expenseID)
            throws DoesNotExistException {
        return new Expense(expenseID, expenseTable.getValue(expenseID), expenseTable.getDescription(expenseID));
    }
}