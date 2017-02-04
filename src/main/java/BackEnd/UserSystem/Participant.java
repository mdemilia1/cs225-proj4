package BackEnd.UserSystem;

import BackEnd.UserSystem.UserExceptions.ValidationException;
import auth.AuthorizationException;
import auth.Operation;
import auth.Permissions;
import auth.PrivilegeLevel;

/**
 * This class represents a Participant of an event.
 *
 * @author Anderson Santana
 */
public class Participant {
    private int UID;
    private String firstName;
    private String lastName;
    private PhoneNumber phoneNumber;
    // private UserData_Table table;
    private Address address;
    
    // These need to be package-local to avoid redundant authorization checks.
    String emailAddress;
    PrivilegeLevel privilegeLevel = PrivilegeLevel.PARTICIPANT;

    /**
     * Default Constructor.
     */
    public Participant() {
        firstName = "";
        lastName = "";
        emailAddress = "";
        phoneNumber = new PhoneNumber();
        address = new Address();
    }

    /**
     * This constructor initializes the Participant object with a first
     * name, a last name, and an email address.
     *
     * @param firstName The participant's first name
     * @param lastName  The participant's last name
     * @param emailAddress   The participant's email address
     */


    public Participant(int UID, String firstName, String lastName, String emailAddress) {
        this.UID = UID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        phoneNumber = new PhoneNumber();
        address = new Address();
    }


    public Participant(String firstName, String lastName, String emailAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phoneNumber = new PhoneNumber();
        this.address = new Address();
    }


    public Participant(int userID, Participant participant) {
        UID = userID;
        firstName = participant.firstName;
        lastName = participant.lastName;
        emailAddress = participant.emailAddress;
        phoneNumber = participant.phoneNumber;
        address = participant.address;
    }


    public int getUserId() {
        return UID;
    }

    /**
     * It sets the participant's first name.
     *
     * @param firstName The participant's first name
     */
    public void setFirstName(String firstName) throws AuthorizationException {
        Permissions.get().checkPermission("USERS", "FNAME", Operation.MODIFY, getUserId(), getPrivilegeLevel());
        this.firstName = firstName;
    }

    /**
     * It returns the participant's first name.
     *
     * @return The participant's first name
     */
    public String getFirstName() throws AuthorizationException {
        Permissions.get().checkPermission("USERS", "FNAME", Operation.VIEW, getUserId(), getPrivilegeLevel());
        return firstName;
    }

    /**
     * It sets the participant's last name.
     *
     * @param lastName The participant's last name
     */
    public void setLastName(String lastName) throws AuthorizationException {
        Permissions.get().checkPermission("USERS", "LNAME", Operation.MODIFY, getUserId(), getPrivilegeLevel());
        this.lastName = lastName;
    }

    /**
     * It returns the participant's last name.
     *
     * @return The participant's last name
     */
    public String getLastName() throws AuthorizationException {
        Permissions.get().checkPermission("USERS", "LNAME", Operation.VIEW, getUserId(), getPrivilegeLevel());
        return lastName;
    }

    /**
     * It sets the participant's email address.
     *
     * @param emailAddress The participant's email address
     */
    public void setEmailAddress(String emailAddress) throws ValidationException, AuthorizationException {
        Permissions.get().checkPermission("USERS", "EMAIL", Operation.MODIFY, getUserId(), getPrivilegeLevel());

        if (!verifyEmailAddress(emailAddress)) {
            throw new ValidationException("Invalid email address");
        }

        this.emailAddress = emailAddress;
    }

    /**
     * It returns the participant's email address.
     *
     * @return The participant's email address
     */
    public String getEmailAddress() throws AuthorizationException {
        Permissions.get().checkPermission("USERS", "EMAIL", Operation.VIEW, getUserId(), getPrivilegeLevel());
        return emailAddress;
    }

    /**
     * It verifies the participant's email address by making sure
     * that it does not already exists in the system (or any other
     * reason not yet specified).
     *
     * @param emailAddress The participant's email address
     * @return true
     */
    private boolean verifyEmailAddress(String emailAddress) {
        // Paul Buonopane
        return emailAddress.matches("[\\w_+=-]+@[\\w.-]+\\.[a-zA-Z][\\w-]*\\w");
    }

    /**
     * It sets the participant's phone number.
     *
     * @param phoneNumber The participant's phone number
     */
    public void setPhoneNumber(PhoneNumber phoneNumber) throws AuthorizationException {
        Permissions.get().checkPermission("USERS", "PHONE", Operation.MODIFY, getUserId(), getPrivilegeLevel());
        this.phoneNumber = phoneNumber;
    }

    /**
     * It returns the participant's phone number.
     *
     * @return The participant's phone number
     */
    public PhoneNumber getPhoneNumber() throws AuthorizationException {
        Permissions.get().checkPermission("USERS", "PHONE", Operation.VIEW, getUserId(), getPrivilegeLevel());
        return phoneNumber;
    }

    /**
     * It sets the participant's address.
     *
     * @param address The participant's address
     */
    public void setAddress(Address address) throws AuthorizationException {
        for (String field : new String[] { "STREET", "CITY", "STATE", "ZIPCODE", "COUNTRY" }) {
            Permissions.get().checkPermission("USERS", field, Operation.MODIFY, getUserId(), getPrivilegeLevel());
        }

        this.address = address;
    }

    /**
     * It returns the participant's address.
     *
     * @return The participant's address
     */
    public Address getAddress() throws AuthorizationException {
        for (String field : new String[] { "STREET", "CITY", "STATE", "ZIPCODE", "COUNTRY" }) {
            Permissions.get().checkPermission("USERS", field, Operation.VIEW, getUserId(), getPrivilegeLevel());
        }

        return address;
    }

    /**
     * Determines whether the current object matches its argument.
     *
     * @param obj The object to be compared to the current object
     * @return true if the object has the same details; otherwise,
     * return false
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (this.getClass() == obj.getClass()) {
            Participant other = (Participant) obj;
            return firstName.equals(other.firstName) &&
                    lastName.equals(other.lastName) &&
                    emailAddress.equals(other.emailAddress) &&
                    phoneNumber.equals(other.phoneNumber) &&
                    address.equals(other.address);
        } else {
            return false;
        }
    }

    /**
     * It generates a hash code for the participant object.
     *
     * @return The hash code
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.firstName != null ? this.firstName.hashCode() : 0);
        hash = 53 * hash + (this.lastName != null ? this.lastName.hashCode() : 0);
        hash = 53 * hash + (this.emailAddress != null ? this.emailAddress.hashCode() : 0);
        hash = 53 * hash + (this.phoneNumber != null ? this.phoneNumber.hashCode() : 0);
        hash = 53 * hash + (this.address != null ? this.address.hashCode() : 0);
        return hash;
    }

    /**
     * Creates a string representation of the participant.
     *
     * @return A string representation of the participant
     */
    @Override
    public String toString() {

        return "User ID: " + UID + "\n" + firstName + " " + lastName + "\n" +
                emailAddress + "\n" +
                phoneNumber + "\n" +
                address;
    }

    public PrivilegeLevel getPrivilegeLevel() throws AuthorizationException {
        Permissions.get().checkPermission("USERS", "LEVEL", Operation.VIEW, getUserId(), privilegeLevel);
        return privilegeLevel;
    }

    public void setPrivilegeLevel(PrivilegeLevel privilegeLevel) throws AuthorizationException {
        Permissions.get().checkPermission("USERS", "LEVEL", Operation.MODIFY, getUserId(), this.privilegeLevel);
        Permissions.get().checkPermission("USERS", "LEVEL", Operation.MODIFY, getUserId(), privilegeLevel);
        this.privilegeLevel = privilegeLevel;
    }
}