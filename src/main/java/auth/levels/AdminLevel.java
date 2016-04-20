package auth.levels;

import auth.AuthorizationException;
import auth.Operation;
import auth.PrivilegeLevel;
import auth.Who;

public class AdminLevel extends Level {
    @Override
    public PrivilegeLevel getPrivilegeLevel() {
        return PrivilegeLevel.ADMIN;
    }
    @Override
    protected void checkPermissionEvents(Context context) throws AuthorizationException {
        switch (context.getFieldForSwitch()) {
            case "":
                context.cannotDelete();
                break;

            case "DESCRIPTION":
                context.cannotDelete();
                break;

            case "DETAILS":
                context.cannotDelete();

                break;

            case "TITLE":
                context.cannotDelete();
                break;

            case "STARTDATE":
                context.cannotDelete();
                break;

            case "ENDDATE":
                context.cannotDelete();
                break;

            case "COMPLETE":
                context.cannotDelete();
                break;

            case "STREET":
                context.cannotDelete();
                break;

            case "CITY":
                context.cannotDelete();
                break;

            case "STATE":
                context.cannotDelete();
                break;

            case "ZIPCODE":
                context.cannotDelete();
                break;

            case "COUNTRY":
                context.cannotDelete();
                break;

            case "ORGANIZERLIST":
                context.cannotDelete();
                break;

            case "SUBEVENTLIST":
                context.cannotModify();
                break;

            case "PARTICIPANTLIST":
                context.cannotModify();
                break;

            case "COMMITTEE":
                context.cannotModify();
                break;

            default:
                throw new UnknownFieldException(context);
        }
    }
    @Override
    protected void checkPermissionUsers(Context context) throws AuthorizationException {
        switch (context.getFieldForSwitch()) {
            case "":
                context.cannotDelete();
                break;

            case "LEVEL":
                context.cannotModify()
                       .cannotCreate()
                       .cannotDelete();
                break;

            case "FNAME":
                context.cannotModify()
                       .cannotCreate()
                       .cannotDelete();

                break;

            case "LNAME":
                context.cannotModify()
                       .cannotCreate()
                       .cannotDelete();
                break;

            case "PWD":
                context.noAccess();
                break;

            case "EMAIL":
                context.cannotCreate()
                       .cannotDelete()
                       .cannotModify();
                break;

            case "PHONE":
                context.cannotCreate()
                       .cannotDelete()
                       .cannotModify();
                break;

            case "STREET":
                context.cannotCreate()
                       .cannotDelete()
                       .cannotModify();
                break;

            case "CITY":
                context.cannotCreate()
                       .cannotDelete()
                       .cannotModify();
                break;

            case "STATE":
                context.cannotCreate()
                       .cannotDelete()
                       .cannotModify();
                break;

            case "ZIPCODE":
                context.cannotCreate()
                       .cannotDelete()
                       .cannotModify();
                break;

            case "COUNTRY":
                context.cannotCreate()
                       .cannotDelete()
                       .cannotModify();
                break;

            case "PARTICIPANT":
                context.cannotCreate()
                       .cannotDelete()
                       .cannotModify();
                break;

            case "EVENTLEVEL":
                context.cannotCreate()
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
                context.cannotDelete();
                break;

            case "CHAIRMAN":
                context.cannotDelete();
                break;

            case "BUDGETACCESS":
                context.cannotDelete();
                break;

            case "MEMBERS":
                context.cannotDelete();
                break;

            case "TASKS":
                context.cannotDelete();
                break;

            case "INCOME":
                context.cannotDelete();
                break;

            case "EXPENSE":
                context.cannotDelete();
                break;

            case "BUDGET":
                context.cannotDelete();
                break;

            default:
                throw new UnknownFieldException(context);
        }
    }
}