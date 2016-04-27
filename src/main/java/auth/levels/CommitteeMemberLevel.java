package auth.levels;

import auth.AuthorizationException;
import auth.PrivilegeLevel;

/**
 * @author Bill
 */

public class CommitteeMemberLevel extends Level {
    @Override
    public PrivilegeLevel getPrivilegeLevel() {
        return PrivilegeLevel.COMMITTEE_MEMBER;
    }

    @Override
    protected void checkPermissionUsers(Context context) throws AuthorizationException {
        switch (context.getFieldForSwitch()){
            case "":
                context.noAccess();
                break;
            case "LEVEL":
                context.noAccess();
                break;
            case "FNAME":
                context
                        .cannotModifyOther()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "LNAME":
                context
                        .cannotModifyOther()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "PWD":
                context
                        .cannotViewOther()
                        .cannotModifyOther()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "EMAIL":
                context
                        .cannotModifyOther()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "PHONE":
                context
                        .cannotViewOther()
                        .cannotModifyOther()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "STREET":
                context
                        .cannotViewOther()
                        .cannotModifyOther()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "CITY":
                context
                        .cannotViewOther()
                        .cannotModifyOther()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "STATE":
                context
                        .cannotViewOther()
                        .cannotModifyOther()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "ZIPCODE":
                context
                        .cannotViewOther()
                        .cannotModifyOther()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "COUNTRY":
                context
                        .cannotViewOther()
                        .cannotModifyOther()
                        .cannotCreate()
                        .cannotDelete();
                break;
            default:
                throw new UnknownFieldException(context);
        }
    }

    @Override
    protected void checkPermissionEvents(Context context) throws AuthorizationException {
        switch (context.getFieldForSwitch()){
            case "":
                context.noAccess();
            case "DESCRIPTION":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "DETAILS":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "TITLE":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "STARTDATE":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "ENDDATE":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "COMPLETE":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "STREET":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "CITY":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "STATE":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "ZIPCODE":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "COUNTRY":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "ORGANIZER":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "SUBEVENT":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "PARTICIPANT":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "COMMITTEE":
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
    protected void checkPermissionSubEvents(Context context) throws AuthorizationException {
        switch (context.getFieldForSwitch()){
            case "":
                context.noAccess();
            case "DESCRIPTION":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "DETAILS":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "TITLE":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "COMPLETE":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "STREET":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "CITY":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "STATE":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "ZIPCODE":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "COUNTRY":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "STARTDATE":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "ENDDATE":
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
    protected void checkPermissionCommittee(Context context) throws AuthorizationException {
        switch (context.getFieldForSwitch()){
            case "":
                context.noAccess();
            case "TITLE":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "CHAIRMAN":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "BUDGETACCESS":
                context.noAccess();
                break;
            case "MEMBERS":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "TASKS":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "INCOME":
                context.noAccess();
                break;
            case "EXPENSE":
                context.noAccess();
                break;
            case "BUDGET":
                context.noAccess();
                break;
            default:
                throw new UnknownFieldException(context);
        }
    }

    @Override
    protected void checkPermissionTasks(Context context) throws AuthorizationException {
        switch (context.getFieldForSwitch()){
            case "":
                context.noAccess();
            case "DESCRIPTION":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "DETAILS":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "TITLE":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "STREET":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "CITY":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "STATE":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "ZIPCODE":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "COUNTRY":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "STARTDATE":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "ENDDATE":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "COMPLETE":
                context
                        .cannotModify()
                        .cannotCreate()
                        .cannotDelete();
                break;
            case "MANAGER":
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
    protected void checkPermissionIncome(Context context) throws AuthorizationException {
        switch (context.getFieldForSwitch()){
            case "":
                context.noAccess();
            case "DESCRIPTION":
                context.noAccess();
                break;
            case "DATE":
                context.noAccess();
                break;
            case "VALUE":
                context.noAccess();
                break;
            default:
                throw new UnknownFieldException(context);
        }
    }

    @Override
    protected void checkPermissionExpense(Context context) throws AuthorizationException {
        switch (context.getFieldForSwitch()){
            case "":
                context.noAccess();
            case "DESCRIPTION":
                context.noAccess();
                break;
            case "DATE":
                context.noAccess();
                break;
            case "VALUE":
                context.noAccess();
            default:
                throw new UnknownFieldException(context);
        }
    }
}
