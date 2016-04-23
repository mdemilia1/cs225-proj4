package auth.levels;

import auth.AuthorizationException;
import auth.Operation;
import auth.PrivilegeLevel;
import auth.Who;

public class SystemLevel extends Level {
    @Override
    public PrivilegeLevel getPrivilegeLevel() {
        return PrivilegeLevel.SYSTEM;
    }

    @Override
    public void checkPermission(String table, String field, Operation operation, Who who, PrivilegeLevel otherLevel) throws AuthorizationException {
        // Allow everything
    }

    @Override
    protected void checkPermissionUsers(Context context) throws AuthorizationException {
        // Intentionally blank.
    }

    @Override
    protected void checkPermissionEvents(Context context) throws AuthorizationException {
        // Intentionally blank.
    }

    @Override
    protected void checkPermissionSubEvents(Context context) throws AuthorizationException {
        // Intentionally blank.
    }

    @Override
    protected void checkPermissionCommittee(Context context) throws AuthorizationException {
        // Intentionally blank.
    }

    @Override
    protected void checkPermissionTasks(Context context) throws AuthorizationException {
        // Intentionally blank.
    }

    @Override
    protected void checkPermissionIncome(Context context) throws AuthorizationException {
        // Intentionally blank.
    }

    @Override
    protected void checkPermissionExpense(Context context) throws AuthorizationException {
        // Intentionally blank.
    }
}
