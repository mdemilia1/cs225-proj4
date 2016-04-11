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

    public boolean hasPermission(String table, String field, Operation operation) {
        // TODO: Check permission
        return true;
    }

    public boolean hasPermission(String table, String field, Operation operation, Who who, PrivilegeLevel otherLevel) {
        // TODO: Check permission
        return true;
    }

    public boolean hasPermission(String table, String field, Operation operation, int userID) {
        // TODO: Check permission
        return true;
    }
}
