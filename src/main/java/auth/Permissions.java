package auth;

/**
 * @author Dave, Paul
 */
public class Permissions {
    private static Permissions instance;

    private int userID;
    private PrivilegeLevel level;

    private Permissions(int userID, PrivilegeLevel level) {
        this.userID = userID;
        this.level = level;
    }

    public static Permissions get() {
        return instance;
    }

    public static void init(int ownUserID) {
        if (instance != null) {
            throw new IllegalStateException("Permissions as already been initialized");
        }

        // TODO: Look up correct privilege level
        instance = new Permissions(ownUserID, PrivilegeLevel.ADMIN);
    }

    /**
     * Check permissions for an operation against any data that doesn't involve another user.
     * @param table name of the affected table, exactly as it appears in the database (e.g. <code>TASKS</code>)
     * @param field name of the affected field, exactly as it appears in the database; <code>null</code> for CREATE and
     *              DELETE operations
     * @param operation if dealing with a single field, VIEW or MODIFY; if dealing with a whole row, CREATE or DELETE
     * @throws AuthorizationException if the current user lacks permission
     */
    public void checkPermission(String table, String field, Operation operation) throws AuthorizationException {
        // TODO: Check permission
    }

    /**
     * Check permissions for an operation against any data that involves another user (e.g., users table).
     * @param table name of the affected table, exactly as it appears in the database (e.g. <code>TASKS</code>)
     * @param field name of the affected field, exactly as it appears in the database; <code>null</code> for CREATE and
     *              DELETE operations
     * @param operation if dealing with a single field, VIEW or MODIFY; if dealing with a whole row, CREATE or DELETE
     * @param userID ID of the user that is being acted upon (NOT the current user)
     * @throws AuthorizationException if the current user lacks permission
     */
    public void checkPermission(String table, String field, Operation operation, int userID) throws AuthorizationException {
        // TODO: Check permission
    }

    /**
     * Don't use this for now.
     * @param table
     * @param field
     * @param operation
     * @param who
     * @param otherLevel
     * @return
     */
    private void checkPermission(String table, String field, Operation operation, Who who, PrivilegeLevel otherLevel) throws AuthorizationException {
        // TODO: Check permission
    }
}
