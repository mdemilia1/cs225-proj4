package auth;

import BackEnd.ManagerSystem.MainManager;
import BackEnd.UserSystem.User;

/**
 * @author Dave, Paul
 */
public class Permissions {
    private static Permissions instance;

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

        return user.getPrivilegeLevel();
    }

    private int getCurrentUserID() {
        User user = getCurrentUser();

        return user == null ? 0 : user.getUserId();
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

        // TODO: Check permission
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
     */
    public void checkPermission(String table, String field, Operation operation, int userID) throws AuthorizationException {
        if (isInSystemTransaction()) {
            return;
        }

        // TODO: Check permission
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

        // TODO: Check permission
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
            } catch (Throwable ex) {
                // Probably already finalized
            }
        }
    }
}
