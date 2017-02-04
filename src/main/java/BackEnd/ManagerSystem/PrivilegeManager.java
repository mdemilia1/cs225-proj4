package BackEnd.ManagerSystem;

import BackEnd.ManagerSystem.ManagerExceptions.PrivilegeInsufficientException;
import BackEnd.EventSystem.Committee;
import BackEnd.EventSystem.Event;
import BackEnd.EventSystem.Task;
import BackEnd.UserSystem.Participant;
import BackEnd.UserSystem.User;
import auth.AuthorizationException;
import auth.levels.AdminLevel;

/**
 * This class serves as a collection of static methods that help manage whether
 * a given user has the correct privilege level to edit certain parts of an
 * event, or another user, etc.
 *
 * @author Julian Kuk
 */
public class PrivilegeManager {

    private static final String PRIVILEGE_INSUFFICIENT = "Privilege insufficient.";

    private PrivilegeManager() {
    }

    /**
     * checks to see if a user is an administrator
     *
     * @param loggedInUser the currently logged in user
     * @return whether the user has sufficient privilege
     * @throws PrivilegeInsufficientException
     */
    static boolean hasAdminPrivilege(User loggedInUser)
            throws AuthorizationException, PrivilegeInsufficientException {

        if (loggedInUser.getPrivilegeLevel().isAdmin() ) {
            return true;
        } else {
            throw new PrivilegeInsufficientException(PRIVILEGE_INSUFFICIENT);
        }
    }

    /**
     * checks to see if the user has the privilege to edit an account
     *
     * @param loggedInUser the currently logged in user
     * @param selectedUser the currently selected user
     * @return whether the user has sufficient privilege
     * @throws PrivilegeInsufficientException
     */
    static boolean hasUserPrivilege(User loggedInUser, User selectedUser)
            throws AuthorizationException, PrivilegeInsufficientException {

        return loggedInUser.equals(selectedUser) || hasAdminPrivilege(loggedInUser);
    }

    /**
     * checks to see if a user has event creation privileges
     *
     * @param loggedInUser the currently logged in user
     * @return whether the user has event creation privileges
     * @throws PrivilegeInsufficientException
     */
    static boolean hasEventCreationPrivilege(User loggedInUser)
            throws AuthorizationException, PrivilegeInsufficientException {

        return loggedInUser.getPrivilegeLevel().isCommitteeLeader() || hasAdminPrivilege(loggedInUser);
    }

    /**
     * checks to see if a user has event privileges
     *
     * @param loggedInUser the currently logged in user
     * @param selectedEvent the currently selected event
     * @return whether the user has event privileges
     * @throws PrivilegeInsufficientException
     */
    static boolean hasEventPrivilege(User loggedInUser, Event selectedEvent)
            throws AuthorizationException, PrivilegeInsufficientException {

        return selectedEvent.getOrganizerList().contains(loggedInUser) || hasAdminPrivilege(loggedInUser);
    }

    /**
     * checks to see if a user has sub event privileges
     *
     * @param loggedInUser the currently logged in user
     * @param selectedEvent the currently selected event
     * @return whether the user has sub event privileges
     * @throws PrivilegeInsufficientException
     */
    static boolean hasSubEventPrivilege(User loggedInUser, Event selectedEvent)
            throws AuthorizationException, PrivilegeInsufficientException {
        for (Committee committee : selectedEvent.getCommitteeList()) {
            if (committee.getChair().equals(loggedInUser)) {
                return true;
            }
        }

        return hasEventPrivilege(loggedInUser, selectedEvent);
    }

    /**
     * checks to see if a user has committee privileges
     *
     * @param loggedInUser the currently logged in user
     * @param selectedEvent the currently selected event
     * @param selectedCommittee the currently selected committee
     * @return whether the user has committee privileges
     * @throws PrivilegeInsufficientException
     */
    static boolean hasCommitteePrivilege(User loggedInUser, Event selectedEvent, Committee selectedCommittee)
            throws AuthorizationException, PrivilegeInsufficientException {

        return selectedCommittee.getChair().equals(loggedInUser) || hasEventPrivilege(loggedInUser, selectedEvent);
    }

    /**
     * checks to see whether a user has task privileges
     *
     * @param loggedInUser the currently logged in user
     * @param selectedEvent the currently selected event
     * @param selectedCommittee the currently selected committee
     * @param selectedTask the currently selected task
     * @return whether the user has task privileges
     * @throws PrivilegeInsufficientException
     */
    static boolean hasTaskPrivilege(User loggedInUser, Event selectedEvent, Committee selectedCommittee, Task selectedTask)
            throws AuthorizationException, PrivilegeInsufficientException {

        return selectedTask.getResponsibleList().contains(loggedInUser) || hasCommitteePrivilege(loggedInUser, selectedEvent, selectedCommittee);
    }

    /**
     * checks to see whether a user has budget privileges
     *
     * @param loggedInUser the currently logged in user
     * @param selectedEvent the currently selected event
     * @param selectedCommittee the currently selected committee
     * @return whether the user has budget privileges
     * @throws PrivilegeInsufficientException
     */
    static boolean hasBudgetPrivilege(User loggedInUser, Event selectedEvent, Committee selectedCommittee)
            throws AuthorizationException, PrivilegeInsufficientException {

        return selectedCommittee.getBudgetAccessList().contains(loggedInUser) ||
                hasCommitteePrivilege(loggedInUser, selectedEvent, selectedCommittee);
    }
}