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
}
