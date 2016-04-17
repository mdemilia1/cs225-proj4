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
        // There are a lot of special methods fo checking user table permissions.
        switch (context.getFieldForSwitch()) {
            case "":
                // Normally, only CREATE and DELETE have empty fields, but VIEW and MODIFY should be accounted
                // for, just in case.  When an entry is created/deleted, all fields are affected, so there's no point
                // in running a check for each one individually.
                context
                        .cannotViewOther()    // "Other" means other users; they can still view info about themselves
                        .cannotModify()       // No "Other" or "Self"; doesn't matter who the user is that's being modified
                        .cannotCreate()       // User can't add other users.
                        .cannotDeleteOther(); // User can delete self.  Opposite is cannotDeleteSelf().
                break;

            case "EXAMPLE1":
                context.noAccess();  // Shortcut; denies everything
                break;

            case "EXAMPLE2":
                // There are also fancy methods like these that prevent users from modifying other users with
                // the same or greater privilege levels:
                context
                        .cannotCreateWithSameOrGreaterLevel()
                        .cannotModifyOtherWithGreaterLevel();
                break;

            // TODO

            default:
                throw new UnknownFieldException(context);
        }
    }

    @Override
    protected void checkPermissionEvents(Context context) throws AuthorizationException {
        /*
         * Here, we would only use the simple methods, because this isn't the user table.  The basics are:
         *   - cannotView
         *   - cannotModify
         *   - cannotCreate
         *   - cannotDelete
         */

        // TODO
    }

    ////// And there are more methods. ///////
    // You'll need to use your IDE to automatically generate stubs for them.
    // WHEN YOU ARE DONE, THERE SHOULDN'T BE ANY ERRORS IN YOUR FILE.
}
