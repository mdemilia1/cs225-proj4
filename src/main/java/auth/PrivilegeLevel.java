package auth;

import exception.ReadException;

/**
 * @author Dave, Paul
 */
public enum PrivilegeLevel {
    // Order of levels matters here.  Order of IDs does not.
    LOGGED_OUT(0),
    PARTICIPANT(1),
    COMMITTEE_MEMBER(2),
    COMMITTEE_LEADER(3),
    ADMIN(4),
    SYSTEM(5),
    // Cannot skip IDs and must start at 0.
    ;

    private static PrivilegeLevel[] byID;

    private final int id;

    PrivilegeLevel(int id) {
        this.id = id;
    }

    public int id() {
        return id;
    }

    public static PrivilegeLevel fromID(int id) {
        if (byID == null) {
            PrivilegeLevel[] values = values();
            int size = values.length;
            byID = new PrivilegeLevel[size];

            for (PrivilegeLevel level : values) {
                assert level.id < size;
                assert byID[level.id] == null;

                byID[level.id] = level;
            }
        }

        if (id < 0 || id >= byID.length) {
            throw new ReadException(String.format("Invalid privilege level ID: %d", id), null);
        }

        return byID[id];
    }

    public boolean isOrganizer() {
        switch (this) {
            case COMMITTEE_MEMBER:
            case COMMITTEE_LEADER:
            case ADMIN:
                return true;

            default:
                return false;
        }
    }

    public boolean isAdmin() {
        return compareTo(ADMIN) >= 0;
    }

    // This could be changed in the future to also include organizers.
    // Note that getParticipantQuery would also need to be changed.
    public boolean isParticipant() {
        return this == PARTICIPANT;
    }

    public static String getParticipantQuery() {
        return "LEVEL = " + PARTICIPANT.id();
    }


    //
    // Don't use any of this.  [Paul Buonopane]
    //

    @Deprecated
    public boolean legacyIsAdmin() {
        return compareTo(ADMIN) >= 0;
    }

    @Deprecated
    public boolean legacyIsEventCreator() {
        return compareTo(COMMITTEE_MEMBER) >= 0;
    }

    @Deprecated
    public static PrivilegeLevel legacyFromLevels(int admin, int eventCreator, int participant) {
        return legacyFromLevels(admin == 1, eventCreator == 1);
    }

    @Deprecated
    public static PrivilegeLevel legacyFromLevels(boolean admin, boolean eventCreator) {
        if (admin) {
            return ADMIN;
        } else if (eventCreator) {
            return COMMITTEE_LEADER;
        } else {
            return PARTICIPANT;
        }
    }

    @Deprecated
    public boolean legacyIsParticipant() {
        return this == PARTICIPANT;
    }
}
