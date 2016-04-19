package auth.levels;

import auth.AuthorizationException;
import auth.Operation;
import auth.PrivilegeLevel;
import auth.Who;

import static auth.PrivilegeLevel.COMMITTEE_LEADER;

public class CommitteeLeaderLevel extends Level {
    @Override
    protected void checkPermissionSubEvents(Context context) throws AuthorizationException {
        context.cannotModifyOther();
        context.cannotDeleteOther();
    }
    @Override
    protected void checkPermissionExpense(Context context) throws AuthorizationException {
        context.cannotModify();
        context.cannotDelete();
        context.cannotCreate();
    }
    @Override
    public PrivilegeLevel getPrivilegeLevel() {
        return COMMITTEE_LEADER;
    }
    @Override
    protected void checkPermissionTasks(Context context) throws AuthorizationException {
        context.cannotModifyOther();
    }
    @Override
    protected void checkPermissionEvents(Context context) throws AuthorizationException {
        context.cannotModifyOther();
        context.cannotCreate();
        context.cannotDelete();
    }
    @Override
    protected void checkPermissionIncome(Context context) throws AuthorizationException {
        context.cannotModify();
        context.cannotCreate();
        context.cannotDelete();
    }
    @Override
    protected void checkPermissionUsers(Context context) throws AuthorizationException {
        context.cannotModify();
        context.cannotCreate();
        context.cannotDelete();
    }
    @Override
    protected void checkPermissionCommittee(Context context) throws AuthorizationException {
        context.cannotModifyOther();
        context.cannotDelete();
        context.cannotCreate();
    }
    @Override
    public void checkPermission(String table, String field, Operation operation, Who who, PrivilegeLevel otherLevel) throws AuthorizationException {
        super.checkPermission(table, field, operation, who, otherLevel);
    }
}
