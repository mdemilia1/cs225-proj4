package auth;

/**
 * @author Paul Buonopane
 */
public class AuthorizationException extends Exception {
    public AuthorizationException(String table, String field, Operation operation) {
        super(String.format(
                operation == Operation.VIEW || operation == Operation.MODIFY
                        ? "Cannot %1$s %3$s"
                        : "Cannot %1$s %2$s field in %3$s",
                operation.toString().toLowerCase(),
                field == null ? "" : field.toLowerCase(),
                table.toLowerCase()
        ));
    }

    public AuthorizationException(String table, String field, Operation operation, Who who) {
        this(table, field, operation, who, null);
    }

    public AuthorizationException(String table, String field, Operation operation, Who who, PrivilegeLevel otherLevel) {
        super(String.format(
                field == null
                        ? "Cannot %1$s %3$s%4$s"
                        : "Cannot %1$s %2$s field in %3$s%4$s",
                operation.name().toLowerCase(),
                field == null ? "" : field.toLowerCase(),
                table.toLowerCase(),
                who == Who.SELF ? "own user" : "other users",
                who == Who.OTHER && otherLevel != null ? " with privilege level " + otherLevel.name().toLowerCase().replace('_', ' ') : ""
        ));
    }
}
