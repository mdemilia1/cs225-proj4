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
        switch (context.getFieldForSwitch()) {
            case "":
                context
                        .cannotDelete();
                break;

            case "DESCRIPTION":
                context
                        .cannotDelete();
                break;

            case "DETAILS":
                context
                        .cannotDelete();

                break;

            case "TITLE":
                context
                         .cannotDelete();
                break;

            case "STARTDATE":
                context
                        .cannotDelete();
                break;

            case "ENDDATE":
                context
                        .cannotDelete();
                break;

            case "COMPLETE":
                context
                        .cannotDelete();
                break;

            case "STREET":
                context
                        .cannotDelete();
                break;

            case "CITY":
                context
                        .cannotDelete();
                break;

            case "STATE":
                context
                        .cannotDelete();
                break;

            case "ZIPCODE":
                context
                        .cannotDelete();
                break;

            case "COUNTRY":
                context
                        .cannotDelete();
                break;

            case "ORGANIZERLIST":
                context
                        .cannotCreate()
                        .cannotModify()
                        .cannotDelete();
                break;

            case "SUBEVENTLIST":
                context
                        .cannotCreate()
                        .cannotDelete()
                        .cannotModify();
                break;

            case "PARTICIPANTLIST":
                context
                        .cannotCreate()
                        .cannotDelete()
                        .cannotModify();
                break;

            case "COMMITTEE":
                context
                        .cannotCreate()
                        .cannotDelete()
                        .cannotModify();
                break;

            default:
                throw new UnknownFieldException(context);
        }
    }
    @Override
    protected void checkPermissionIncome(Context context) throws AuthorizationException {
        context.cannotModify();
        context.cannotCreate();
        context.cannotDelete();
    }
    @Override
    protected void checkPermissionUsers(Context context) throws AuthorizationException {
        switch (context.getFieldForSwitch()) {
            case "":
                context
                        .cannotDelete();
                break;

            case "LEVEL":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDeleteOther();
                break;

            case "FNAME":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();

                break;

            case "LNAME":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;

            case "PWD":
                context.noAccess();
                break;

            case "EMAIL":
                context
                        .cannotCreate()
                        .cannotDelete()
                        .cannotModify();
                break;

            case "PHONE":
                context
                        .cannotCreate()
                        .cannotDelete()
                        .cannotModify();
                break;

            case "STREET":
                context
                        .cannotCreate()
                        .cannotDelete()
                        .cannotModify();
                break;

            case "CITY":
                context
                        .cannotCreate()
                        .cannotDelete()
                        .cannotModify();
                break;

            case "STATE":
                context
                        .cannotCreate()
                        .cannotDelete()
                        .cannotModify();
                break;

            case "ZIPCODE":
                context
                        .cannotCreate()
                        .cannotDelete()
                        .cannotModify();
                break;

            case "COUNTRY":
                context
                        .cannotCreate()
                        .cannotDelete()
                        .cannotModify();
                break;

            case "PARTICIPANT":
                context
                        .cannotCreate()
                        .cannotDelete()
                        .cannotModify();
                break;

            case "EVENTLEVEL":
                context
                        .cannotCreate()
                        .cannotDelete()
                        .cannotModify();
                break;

            default:
                throw new UnknownFieldException(context);
        }
    }
    @Override
    protected void checkPermissionCommittee(Context context) throws AuthorizationException {
        switch (context.getFieldForSwitch()) {
            case "TITLE":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDeleteOther();
                break;

            case "CHAIRMAN":
                context
                        .cannotModify()
                        .cannotDeleteOther()
                        .cannotCreate();
                break;

            case "BUDGETACCESS":
                context
                        .cannotModify()
                        .cannotDelete()
                        .cannotModifyOtherWithGreaterLevel();
                break;

            case "MEMBERS":
                context
                        .cannotModify()
                        .cannotDelete()
                        .cannotDeleteSelf();
                break;

            case "TASKS":
                context
                        .cannotCreateWithSameOrGreaterLevel()
                        .cannotDeleteOther()
                        .cannotModifyOther();
                break;

            case "INCOME":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;

            case "EXPENSE":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;

            case "BUDGET":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;

            default:
                throw new UnknownFieldException(context);
        }
    }
    @Override
    public void checkPermission(String table, String field, Operation operation, Who who, PrivilegeLevel otherLevel) throws AuthorizationException {
        super.checkPermission(table, field, operation, who, otherLevel);
    }
}
