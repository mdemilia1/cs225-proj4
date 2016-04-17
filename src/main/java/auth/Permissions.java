package auth;

import BackEnd.ManagerSystem.MainManager;
import BackEnd.UserSystem.User;
import EMS_Database.DoesNotExistException;
import auth.levels.*;

import java.util.EnumMap;
import java.util.Map;

/**
 * @author Dave, Paul
 */
public class Permissions {
    private final static Map<PrivilegeLevel, Level> levels = new EnumMap<>(PrivilegeLevel.class);
    private static Permissions instance;

    static {
        levels.put(PrivilegeLevel.LOGGED_OUT, new LoggedOutLevel());
        levels.put(PrivilegeLevel.PARTICIPANT, new ParticipantLevel());
        levels.put(PrivilegeLevel.COMMITTEE_MEMBER, new CommitteeMemberLevel());
        levels.put(PrivilegeLevel.COMMITTEE_LEADER, new CommitteeLeaderLevel());
        levels.put(PrivilegeLevel.ADMIN, new AdminLevel());
        levels.put(PrivilegeLevel.SYSTEM, new SystemLevel());
    }

    /**
     * Keeps track of system transactions.
     */
    private ThreadLocal<Integer> systemTxnCount = ThreadLocal.withInitial(() -> 0);

    private Permissions() {
    }

    /**
     * Gets a singleton instance of this class.
     *
     * @return a singleton instance
     */
    public static Permissions get() {
        if (instance == null) {
            instance = new Permissions();
        }

        return instance;
    }

    private boolean isInSystemTransaction() {

        return systemTxnCount.get() > 0;
    }

    public SystemTransaction beginSystemTransaction() {
        return new SystemTransaction();
    }

    private User getCurrentUser() {
        return MainManager.getInstance().getLogInManager().getLoggedInUser();
    }

    private PrivilegeLevel getCurrentPrivilegeLevel() {
        if (isInSystemTransaction()) {
            return PrivilegeLevel.SYSTEM;
        }

        User user = getCurrentUser();

        if (user == null) {
            return PrivilegeLevel.LOGGED_OUT;
        }

        try {
            return user.getPrivilegeLevel();
        } catch (AuthorizationException ex) {
            throw new RuntimeException("Bad permission logic: users must be able to read their own privilege levels", ex);
        }
    }

    private int getCurrentUserID() {
        User user = getCurrentUser();

        return user == null ? 0 : user.getUserId();
    }

    private Level getLevel() {
        return levels.get(getCurrentPrivilegeLevel());
    }

    /**
     * Check permissions for an operation against any data that doesn't involve another user.
     *
     * @param table     name of the affected table, exactly as it appears in the database (e.g. <code>TASKS</code>)
     * @param field     name of the affected field, exactly as it appears in the database; <code>null</code> for CREATE and
     *                  DELETE operations
     * @param operation if dealing with a single field, VIEW or MODIFY; if dealing with a whole row, CREATE or DELETE
     * @throws AuthorizationException if the current user lacks permission
     */
    public void checkPermission(String table, String field, Operation operation) throws AuthorizationException {
        if (isInSystemTransaction()) {
            return;
        }

        checkPermission(table, field, operation, null, null);
    }

    /**
     * Check permissions for an operation against any data that involves another user (e.g., users table).
     *
     * @param table     name of the affected table, exactly as it appears in the database (e.g. <code>TASKS</code>)
     * @param field     name of the affected field, exactly as it appears in the database; <code>null</code> for CREATE and
     *                  DELETE operations
     * @param operation if dealing with a single field, VIEW or MODIFY; if dealing with a whole row, CREATE or DELETE
     * @param userID    ID of the user that is being acted upon (NOT the current user)
     * @throws AuthorizationException if the current user lacks permission
     * @throws DoesNotExistException if the other user does not exist
     */
    public void checkPermission(String table, String field, Operation operation, int userID) throws AuthorizationException, DoesNotExistException {
        if (isInSystemTransaction()) {
            return;
        }

        Who who;
        PrivilegeLevel otherLevel;

        if (userID == getCurrentUserID()) {
            who = Who.SELF;
            otherLevel = getCurrentPrivilegeLevel();
        } else {
            who = Who.OTHER;
            try (SystemTransaction ignored = beginSystemTransaction()) {
                otherLevel = MainManager.getInstance().getUserManager().getUsersTable().getPrivilegeLevel(userID);
            }
        }

        checkPermission(table, field, operation, who, otherLevel);
    }

    /**
     * Check permissions for an operation against any data that involves another user (e.g., users table).
     *
     * @param table      name of the affected table, exactly as it appears in the database (e.g. <code>TASKS</code>)
     * @param field      name of the affected field, exactly as it appears in the database; <code>null</code> for CREATE and
     *                   DELETE operations
     * @param operation  if dealing with a single field, VIEW or MODIFY; if dealing with a whole row, CREATE or DELETE
     * @param userID     ID of the user that is being acted upon (NOT the current user)
     * @param otherLevel privilege level of the affected user
     * @throws AuthorizationException if the current user lacks permission
     */
    public void checkPermission(String table, String field, Operation operation, int userID, PrivilegeLevel otherLevel) throws AuthorizationException {
        if (isInSystemTransaction()) {
            return;
        }

        Who who = userID == getCurrentUserID() ? Who.SELF : Who.OTHER;

        checkPermission(table, field, operation, who, otherLevel);
    }

    /**
     * Check permissions for an operation against any data that involves another user.  This is called after the
     * permissions of the affected user have been resolved, or if the affected user doesn't yet exist.
     *
     * @param table      name of the affected table, exactly as it appears in the database (e.g. <code>TASKS</code>)
     * @param field      name of the affected field, exactly as it appears in the database; <code>null</code> for CREATE and
     *                   DELETE operations
     * @param operation  if dealing with a single field, VIEW or MODIFY; if dealing with a whole row, CREATE or DELETE
     * @param who        relative identity of the other user
     * @param otherLevel privilege level of the affected user
     * @throws AuthorizationException if the current user lacks permission
     */
    public void checkPermission(String table, String field, Operation operation, Who who, PrivilegeLevel otherLevel) throws AuthorizationException {
        if (isInSystemTransaction()) {
            return;
        }

        Level level = getLevel();
        assert level.getPrivilegeLevel() == getCurrentPrivilegeLevel();

        level.checkPermission(table, field, operation, who, otherLevel);
    }


    public class SystemTransaction implements AutoCloseable {
        private volatile boolean active = true;

        SystemTransaction() {
            update(1);
        }

        @Override
        protected void finalize() throws Throwable {
            close();
            super.finalize();
        }

        @Override
        public void close() {
            synchronized (this) {
                if (!active) {
                    return;
                }
                active = false;
            }

            update(-1);
        }

        private void update(int delta) {
            try {
                systemTxnCount.set(systemTxnCount.get() + delta);
            } catch (Throwable ignored) {
                // Probably already finalized
            }
        }
    }
}
