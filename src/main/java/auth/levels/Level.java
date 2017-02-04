package auth.levels;

import auth.AuthorizationException;
import auth.Operation;
import auth.PrivilegeLevel;
import auth.Who;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class Level {
    public abstract PrivilegeLevel getPrivilegeLevel();

    /**
     * Check permissions for a database.
     *
     * @param table      name of the affected table, exactly as it appears in the database (e.g. <code>TASKS</code>)
     * @param field      name of the affected field, exactly as it appears in the database; <code>null</code> for CREATE and
     *                   DELETE operations
     * @param operation  if dealing with a single field, VIEW or MODIFY; if dealing with a whole row, CREATE or DELETE
     * @param who        relative identity of the other user if table is <code>USER</code>; otherwise, <code>null</code>
     * @param otherLevel privilege level of the affected user if table is <code>USER</code>; otherwise, <code>null</code>
     * @throws AuthorizationException if the current user lacks permission
     */
    public void checkPermission(String table, String field, Operation operation, Who who, PrivilegeLevel otherLevel) throws AuthorizationException {
        table = table.toUpperCase();
        if (field != null) {
            field = field.toUpperCase();
        }

        Context context = getContext(table, field, operation, who, otherLevel);

        switch (table) {
            case "USERS":       checkPermissionUsers    (context); break;
            case "EVENTS":      checkPermissionEvents   (context); break;
            case "SUBEVENTS":   checkPermissionSubEvents(context); break;
            case "COMMITTEE":   checkPermissionCommittee(context); break;
            case "TASKS":       checkPermissionTasks    (context); break;
            case "INCOME":      checkPermissionIncome   (context); break;
            case "EXPENSE":     checkPermissionExpense  (context); break;

            default:
                throw new IllegalArgumentException("Bad table: " + table);
        }
    }

    protected abstract void checkPermissionUsers    (Context context) throws AuthorizationException;
    protected abstract void checkPermissionEvents   (Context context) throws AuthorizationException;
    protected abstract void checkPermissionSubEvents(Context context) throws AuthorizationException;
    protected abstract void checkPermissionCommittee(Context context) throws AuthorizationException;
    protected abstract void checkPermissionTasks    (Context context) throws AuthorizationException;
    protected abstract void checkPermissionIncome   (Context context) throws AuthorizationException;
    protected abstract void checkPermissionExpense  (Context context) throws AuthorizationException;

    protected Context getContext(String table, String field, Operation operation, Who who, PrivilegeLevel otherLevel) {
        return new Context(table, field, operation, who, otherLevel);
    }

    protected class Context {
        private final String table;
        private final String field;
        private final Operation operation;
        private final Who who;
        private final PrivilegeLevel otherLevel;

        private Context(String table, String field, Operation operation, Who who, PrivilegeLevel otherLevel) {
            this.table = table;
            this.field = field;
            this.operation = operation;
            this.who = who;
            this.otherLevel = otherLevel;
        }

        public String getTable() {
            return table;
        }

        public String getField() {
            return field;
        }

        public String getFieldForSwitch() {
            return field == null ? "" : field;
        }

        public Operation getOperation() {
            return operation;
        }

        public Who getWho() {
            return who;
        }

        public PrivilegeLevel getOtherLevel() {
            return otherLevel;
        }

        protected Context cannotView() throws AuthorizationException {
            if (operation == Operation.VIEW) {
                throw new AuthorizationException(table, field, operation);
            }
            return this;
        }

        protected Context cannotViewSelf() throws AuthorizationException {
            if (operation == Operation.VIEW && who == Who.SELF) {
                throw new AuthorizationException(table, field, operation, who);
            }
            return this;
        }

        protected Context cannotViewOther() throws AuthorizationException {
            if (operation == Operation.VIEW && who == Who.OTHER) {
                throw new AuthorizationException(table, field, operation, who);
            }
            return this;
        }

        protected Context cannotViewOtherWithGreaterLevel() throws AuthorizationException {
            if (operation == Operation.VIEW && who == Who.OTHER && otherLevel.compareTo(getPrivilegeLevel()) > 0) {
                throw new AuthorizationException(table, field, operation, who, otherLevel);
            }
            return this;
        }

        protected Context cannotViewOtherWithSameOrGreaterLevel() throws AuthorizationException {
            if (operation == Operation.VIEW && who == Who.OTHER && otherLevel.compareTo(getPrivilegeLevel()) >= 0) {
                throw new AuthorizationException(table, field, operation, who, otherLevel);
            }
            return this;
        }

        protected Context cannotModify() throws AuthorizationException {
            if (operation == Operation.MODIFY) {
                throw new AuthorizationException(table, field, operation);
            }
            return this;
        }

        protected Context cannotModifySelf() throws AuthorizationException {
            if (operation == Operation.MODIFY && who == Who.SELF) {
                throw new AuthorizationException(table, field, operation, who);
            }
            return this;
        }

        protected Context cannotModifyOther() throws AuthorizationException {
            if (operation == Operation.MODIFY && who == Who.OTHER) {
                throw new AuthorizationException(table, field, operation, who);
            }
            return this;
        }

        protected Context cannotModifyOtherWithGreaterLevel() throws AuthorizationException {
            if (operation == Operation.MODIFY && who == Who.OTHER && otherLevel.compareTo(getPrivilegeLevel()) > 0) {
                throw new AuthorizationException(table, field, operation, who, otherLevel);
            }
            return this;
        }

        protected Context cannotModifyOtherWithSameOrGreaterLevel() throws AuthorizationException {
            if (operation == Operation.MODIFY && who == Who.OTHER && otherLevel.compareTo(getPrivilegeLevel()) >= 0) {
                throw new AuthorizationException(table, field, operation, who, otherLevel);
            }
            return this;
        }

        protected Context cannotCreate() throws AuthorizationException {
            if (operation == Operation.CREATE) {
                throw new AuthorizationException(table, field, operation);
            }
            return this;
        }

        protected Context cannotCreateWithGreaterLevel() throws AuthorizationException {
            if (operation == Operation.CREATE && otherLevel.compareTo(getPrivilegeLevel()) > 0) {
                throw new AuthorizationException(table, field, operation, who, otherLevel);
            }
            return this;
        }

        protected Context cannotCreateWithSameOrGreaterLevel() throws AuthorizationException {
            if (operation == Operation.CREATE && otherLevel.compareTo(getPrivilegeLevel()) >= 0) {
                throw new AuthorizationException(table, field, operation, who, otherLevel);
            }
            return this;
        }

        protected Context cannotDelete() throws AuthorizationException {
            if (operation == Operation.DELETE) {
                throw new AuthorizationException(table, field, operation);
            }
            return this;
        }

        protected Context cannotDeleteSelf() throws AuthorizationException {
            if (operation == Operation.DELETE && who == Who.SELF) {
                throw new AuthorizationException(table, field, operation, who);
            }
            return this;
        }

        protected Context cannotDeleteOther() throws AuthorizationException {
            if (operation == Operation.DELETE && who == Who.OTHER) {
                throw new AuthorizationException(table, field, operation, who);
            }
            return this;
        }

        protected Context cannotDeleteOtherWithGreaterLevel() throws AuthorizationException {
            if (operation == Operation.DELETE && who == Who.OTHER && otherLevel.compareTo(getPrivilegeLevel()) > 0) {
                throw new AuthorizationException(table, field, operation, who, otherLevel);
            }
            return this;
        }

        protected Context cannotDeleteOtherWithSameOrGreaterLevel() throws AuthorizationException {
            if (operation == Operation.DELETE && who == Who.OTHER && otherLevel.compareTo(getPrivilegeLevel()) >= 0) {
                throw new AuthorizationException(table, field, operation, who, otherLevel);
            }
            return this;
        }

        protected Context noAccess() throws AuthorizationException {
            throw new AuthorizationException(table, field, operation);
        }
    }

    public class UnknownFieldException extends IllegalArgumentException {
        protected UnknownFieldException(Context context) {
            this(context.getTable(), context.getField());
        }

        protected UnknownFieldException(String table, String field) {
            super(String.format("No permissions exist in %s for field %s of table %s", getPrivilegeLevel().name(), field == null ? "(null)" : field, table));
        }
    }
}
