package auth.levels;

import auth.AuthorizationException;
import auth.PrivilegeLevel;

public class ParticipantLevel extends Level {
    @Override
    public PrivilegeLevel getPrivilegeLevel() {
        return PrivilegeLevel.PARTICIPANT;
    }

    @Override
    protected void checkPermissionUsers(Context context) throws AuthorizationException {
        switch (context.getFieldForSwitch()) {
            case "level":
                context.cannotCreate();
                context.cannotDeleteOther();
                context.cannotModify();
                context.cannotViewOther();
//                        .cannotViewOther()    // "Other" means other users; they can still view info about themselves
//                        .cannotModify()       // No "Other" or "Self"; doesn't matter who the user is that's being modified
//                        .cannotCreate()       // User can't add other users.
//                        .cannotDeleteOther(); // User can delete self.  Opposite is cannotDeleteSelf().

                break;

            case "fName":
                context.cannotCreate();
                context.cannotDeleteOther();
                context.cannotModifyOther();
                context.cannotViewOther(); // needs to be changed for people who agree to share information

                break;

            case "lName":
                context.cannotCreate();
                context.cannotDeleteOther();
                context.cannotModifyOther();
                context.cannotViewOther(); // needs to be changed for people who agree to share information

                break;

            case "PWD":
                context.cannotCreate();
                context.cannotDeleteOther();
                context.cannotModifyOther();
                context.cannotViewOther();

                break;

            case "email":
                context.cannotCreate();
                context.cannotDeleteOther();
                context.cannotModifyOther();
                context.cannotViewOther(); // needs to be changed for enabling to email organizers

                break;

            case "phone":
                context.cannotCreate();
                context.cannotDeleteOther();
                context.cannotModifyOther();
                context.cannotViewOther(); // needs to be changed for people who agree to share information

                break;


            case "street":
                context.cannotCreate();
                context.cannotDeleteOther();
                context.cannotModifyOther();
                context.cannotViewOther(); // needs to be changed for people who agree to share information

                break;

            case "city":
                context.cannotCreate();
                context.cannotDeleteOther();
                context.cannotModifyOther();
                context.cannotViewOther(); // needs to be changed for people who agree to share information

                break;


            case "state":
                context.cannotCreate();
                context.cannotDeleteOther();
                context.cannotModifyOther();
                context.cannotViewOther(); // needs to be changed for people who agree to share information

                break;

            case "zipcode":
                context.cannotCreate();
                context.cannotDeleteOther();
                context.cannotModifyOther();
                context.cannotViewOther(); // needs to be changed for people who agree to share information

                break;


            case "country":
                context.cannotCreate();
                context.cannotDeleteOther();
                context.cannotModifyOther();
                context.cannotViewOther(); // needs to be changed for people who agree to share information

                break;

            case "participant":// only guessing the usage of this field
                context.noAccess();

                break;

            case "eventLevel":// no idea what this is used for
                context.cannotCreate();

                break;

            default:
                throw new UnknownFieldException(context);
        }
    }

    @Override
    protected void checkPermissionEvents(Context context) throws AuthorizationException {
        switch (context.getFieldForSwitch()) {
            case "tableName":
                context.cannotCreate();
                context.cannotDelete();
                context.cannotModify();
                // is there a need to deny viewing this field for events not participating in?

                break;

            case "description":
                context.cannotCreate();
                context.cannotDelete();
                context.cannotModify();
                // is there a need to deny viewing this field for events not participating in?

                break;

            case "details":
                context.cannotCreate();
                context.cannotDelete();
                context.cannotModify();
                // is there a need to deny viewing this field for events not participating in?

                break;

            case "title":
                context.cannotCreate();
                context.cannotDelete();
                context.cannotModify();
                // is there a need to deny viewing this field for events not participating in?

                break;

            case "startDate":
                context.cannotCreate();
                context.cannotDelete();
                context.cannotModify();
                // is there a need to deny viewing this field for events not participating in?

                break;

            case "endDate":
                context.cannotCreate();
                context.cannotDelete();
                context.cannotModify();
                // is there a need to deny viewing this field for events not participating in?

                break;

            case "complete":
                context.cannotCreate();
                context.cannotDelete();
                context.cannotModify();
                // is there a need to deny viewing this field for events not participating in?

                break;

            case "street":
                context.cannotCreate();
                context.cannotDelete();
                context.cannotModify();
                // is there a need to deny viewing this field for events not participating in?

                break;

            case "city":
                context.cannotCreate();
                context.cannotDelete();
                context.cannotModify();
                // is there a need to deny viewing this field for events not participating in?

                break;


            case "state":
                context.cannotCreate();
                context.cannotDelete();
                context.cannotModify();
                // is there a need to deny viewing this field for events not participating in?

                break;

            case "zipcode":
                context.cannotCreate();
                context.cannotDelete();
                context.cannotModify();
                // is there a need to deny viewing this field for events not participating in?

                break;


            case "country":
                context.cannotCreate();
                context.cannotDelete();
                context.cannotModify();
                // is there a need to deny viewing this field for events not participating in?

                break;

            case "organizerList":
                context.cannotCreate();
                context.cannotDelete();
                context.cannotModify();
                // is there a need to deny viewing this field for events not participating in?

                break;

            case "subEventList":
                context.cannotCreate();
                context.cannotDelete();
                context.cannotModify();
                // is there a need to deny viewing this field for events not participating in?

                break;

            case "participantList":
                context.cannotCreate();
                context.cannotDelete();
                context.cannotModify();
                // is there a need to deny viewing this field for events not participating in?

                break;

            case "committee":
                context.cannotCreate();
                context.cannotDelete();
                context.cannotModify();
                // is there a need to deny viewing this field for events not participating in?

                break;

            // TODO

            default:
                throw new UnknownFieldException(context);
        }
    }

    @Override
    protected void checkPermissionSubEvents(Context context) throws AuthorizationException {
        switch (context.getFieldForSwitch()) {
            case "description":
                context.cannotCreate();
                context.cannotDelete();
                context.cannotModify();
                // is there a need to deny viewing this field for events not participating in?

                break;

            case "details":
                context.cannotCreate();
                context.cannotDelete();
                context.cannotModify();
                // is there a need to deny viewing this field for events not participating in?

                break;

            case "title":
                context.cannotCreate();
                context.cannotDelete();
                context.cannotModify();
                // is there a need to deny viewing this field for events not participating in?

                break;

            case "complete":
                context.cannotCreate();
                context.cannotDelete();
                context.cannotModify();
                // is there a need to deny viewing this field for events not participating in?

                break;

            case "street":
                context.cannotCreate();
                context.cannotDelete();
                context.cannotModify();
                // is there a need to deny viewing this field for events not participating in?

                break;

            case "city":
                context.cannotCreate();
                context.cannotDelete();
                context.cannotModify();
                // is there a need to deny viewing this field for events not participating in?

                break;


            case "state":
                context.cannotCreate();
                context.cannotDelete();
                context.cannotModify();
                // is there a need to deny viewing this field for events not participating in?

                break;

            case "zipcode":
                context.cannotCreate();
                context.cannotDelete();
                context.cannotModify();
                // is there a need to deny viewing this field for events not participating in?

                break;


            case "country":
                context.cannotCreate();
                context.cannotDelete();
                context.cannotModify();
                // is there a need to deny viewing this field for events not participating in?

                break;

            case "startTime":
                context.cannotCreate();
                context.cannotDelete();
                context.cannotModify();
                // is there a need to deny viewing this field for events not participating in?

                break;

            case "endTime":
                context.cannotCreate();
                context.cannotDelete();
                context.cannotModify();
                // is there a need to deny viewing this field for events not participating in?

                break;



            // TODO

            default:
                throw new UnknownFieldException(context);
        }
    }

    @Override
    protected void checkPermissionCommittee(Context context) throws AuthorizationException {
        switch (context.getFieldForSwitch()) {
            case "title":
                context.cannotCreate();
                context.cannotDelete();
                context.cannotModify();

                break;

            case "chairman":
                context.cannotCreate();
                context.cannotDelete();
                context.cannotModify();

                break;

            case "budgetaccess":
                context.noAccess();

                break;

            case "members":
                context.noAccess();

                break;

            case "tasks":
                context.noAccess();

                break;

            case "income":
                context.noAccess();

                break;

            case "expense":
                context.noAccess();

                break;

            case "budget":
                context.noAccess();

                break;



            // TODO

            default:
                throw new UnknownFieldException(context);
        }
    }

    @Override
    protected void checkPermissionTasks(Context context) throws AuthorizationException {
        switch (context.getFieldForSwitch()) {
            case "description":
                context.noAccess();

                break;

            case "details":
                context.noAccess();

                break;

            case "title":
                context.noAccess();

                break;


            case "street":
                context.noAccess();

                break;

            case "city":
                context.noAccess();

                break;


            case "state":
                context.noAccess();

                break;

            case "zipcode":
                context.noAccess();

                break;


            case "country":
                context.noAccess();

                break;

            case "startDate":
                context.noAccess();

                break;

            case "endDate":
                context.noAccess();

                break;

            case "complete":
                context.noAccess();

                break;



            // TODO

            default:
                throw new UnknownFieldException(context);
        }
    }

    @Override
    protected void checkPermissionIncome(Context context) throws AuthorizationException {
        switch (context.getFieldForSwitch()) {
            case "description":
                context.noAccess();

                break;

            case "time":
                context.noAccess();

                break;

            case "value":
                context.noAccess();

                break;



            // TODO

            default:
                throw new UnknownFieldException(context);
        }
    }

    @Override
    protected void checkPermissionExpense(Context context) throws AuthorizationException {
        switch (context.getFieldForSwitch()) {
            case "description":
                context.noAccess();

                break;

            case "date":
                context.noAccess();

                break;

            case "value":
                context.noAccess();

                break;



            // TODO

            default:
                throw new UnknownFieldException(context);
        }
    }
}
