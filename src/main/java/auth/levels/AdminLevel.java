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
    public void checkPermission(String table, String field, Operation operation, Who who, PrivilegeLevel otherLevel) throws AuthorizationException {
        super.checkPermission(table, field, operation, who, otherLevel);
    }@Override
    protected void checkPermissionTasks(Context context) throws AuthorizationException {
        switch (context.getFieldForSwitch()){
            case "DESCRIPTION":
                context.cannotDelete();
                break;
            case "DETAILS":
                context.cannotDelete();
                break;
            case "TITLE":
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
            case "STARTDATE":
                context.cannotDelete();
                break;
            case "ENDDATE":
                context.cannotDelete();
                break;
            case "COMPLETE":
                context.cannotDelete();
                break;
            case "MANAGER":
                context.cannotDelete();
                break;
            default:
                throw new UnknownFieldException(context);
        }
    }
    @Override
    protected void checkPermissionIncome(Context context) throws AuthorizationException {
        switch (context.getFieldForSwitch()){
            case "DESCRIPTION":
                context.cannotDelete();
                break;
            case "DATE":
                context.cannotDelete();
                break;
            case "VALUE":
                context.cannotDelete();
                break;
            default:
                throw new UnknownFieldException(context);
        }
    }

    @Override
    protected void checkPermissionExpense(Context context) throws AuthorizationException {
        switch (context.getFieldForSwitch()){
            case "DESCRIPTION":
                context.cannotDelete();
                break;
            case "DATE":
                context.cannotDelete();
                break;
            case "VALUE":
                context.cannotDelete();
            default:
                throw new UnknownFieldException(context);
        }
    }
    @Override
    protected void checkPermissionSubEvents(Context context) throws AuthorizationException {
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

            case "STARTTIME":
                context.cannotDelete();
                break;

            case "ENDTIME":
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

            default:
                throw new UnknownFieldException(context);
        }
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
    protected void checkPermissionUsers(Context context) {
        switch (context.getFieldForSwitch()) {
            case "":
                try{
                    context.cannotDelete();
                }catch(AuthorizationException authEx ){}
                break;

            case "LEVEL":
                try{
                    context.cannotModify()
                           .cannotCreate()
                           .cannotDelete();
                }catch(AuthorizationException authEx){}
                break;

            case "FNAME":
                try{
                    context.cannotModify()
                           .cannotCreate()
                           .cannotDelete();
                }catch(AuthorizationException authEx){}
                break;

            case "LNAME":
                try{
                    context.cannotModify()
                           .cannotCreate()
                           .cannotDelete();
                }catch(AuthorizationException authEx){}
                break;

            case "PWD":
                try{
                    context.noAccess();
                }catch(AuthorizationException authEx){}
                break;

            case "EMAIL":
                try{
                    context.cannotCreate()
                           .cannotDelete()
                           .cannotModify();
                }catch(AuthorizationException authEx ){}
                break;

            case "PHONE":
                try{
                    context.cannotCreate()
                           .cannotDelete()
                           .cannotModify();
                }catch(AuthorizationException authEx ){}
                break;

            case "STREET":
                try{
                    context.cannotCreate()
                           .cannotDelete()
                           .cannotModify();
                }catch(AuthorizationException authEx ){}
                break;

            case "CITY":
                try{
                    context.cannotCreate()
                           .cannotDelete()
                           .cannotModify();
                }catch(AuthorizationException authEx ){}
                break;

            case "STATE":
                try{
                    context.cannotCreate()
                           .cannotDelete()
                           .cannotModify();
                }catch(AuthorizationException authEx ){}
                break;

            case "ZIPCODE":
                try{
                    context.cannotCreate()
                           .cannotDelete()
                           .cannotModify();
                }catch(AuthorizationException authEx ){}
                break;

            case "COUNTRY":
                try{
                    context.cannotCreate()
                           .cannotDelete()
                           .cannotModify();
                }catch(AuthorizationException authEx ){}
                break;

            case "PARTICIPANT":
                try{
                    context.cannotCreate()
                           .cannotDelete()
                           .cannotModify();
                }catch(AuthorizationException authEx ){}
                break;

            case "EVENTLEVEL":
                try{
                    context.cannotCreate()
                           .cannotDelete()
                           .cannotModify();
                }catch(AuthorizationException authEx ){}
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