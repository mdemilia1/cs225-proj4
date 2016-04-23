package auth.levels;

import auth.AuthorizationException;
import auth.PrivilegeLevel;

/**
 * @author Paul Buonopane
 */
public class LoggedOutLevel extends Level {
    @Override
    public PrivilegeLevel getPrivilegeLevel() {
        return PrivilegeLevel.LOGGED_OUT;
    }

    @Override
    protected void checkPermissionUsers(Context context) throws AuthorizationException {
        context.noAccess();
    }

    @Override
    protected void checkPermissionEvents(Context context) throws AuthorizationException {
        context.noAccess();
    }

    @Override
    protected void checkPermissionSubEvents(Context context) throws AuthorizationException {
        context.noAccess();
    }

    @Override
    protected void checkPermissionCommittee(Context context) throws AuthorizationException {
        context.noAccess();
    }

    @Override
    protected void checkPermissionTasks(Context context) throws AuthorizationException {
        context.noAccess();
    }

    @Override
    protected void checkPermissionIncome(Context context) throws AuthorizationException {
        context.noAccess();
    }

    @Override
    protected void checkPermissionExpense(Context context) throws AuthorizationException {
        context.noAccess();
    }
}
