package BackEnd.UserSystem;


import BackEnd.UserSystem.UserExceptions.IllegalCharacterException;
import BackEnd.UserSystem.UserExceptions.PasswordMismatchError;
import EMS_Database.DoesNotExistException;
import auth.AuthorizationException;
import auth.Operation;
import auth.Permissions;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author David Tersoff
 */
public class User extends Participant {
    // private int UID;

    private String password;
    final private char[] ILLEGAL_CHARACTERS = {'@', '/', '\\', ' '};
    

    public User() {
        super();
        password = "";
    }

    /**
     * Constructor, creates a User object
     *
     * @param pword the desired password
     * @param pwordMatch the password entered a second time to verify it
     */
    public User(String firstName, String lastName, String emailAddress, String pword, String pwordMatch)
            throws PasswordMismatchError, IllegalCharacterException {
        super(firstName, lastName, emailAddress);

        try (Permissions.SystemTransaction ignored = Permissions.get().beginSystemTransaction()) {
            setPassword(pword, pwordMatch);
        } catch (DoesNotExistException | AuthorizationException ignored) { }
    }

    /*
     public User(int uid, String firstName, String lastName, String emailAddress, String pword, String pwordMatch) throws PasswordMismatchError, IllegalCharacterException
     {
     super(uid, firstName, lastName, emailAddress);
     setPassword(pword, pwordMatch);
            
     }*/
    public User(int userID, User user) {
        super(userID, user);

        password = user.password;
        privilegeLevel = user.privilegeLevel;
    }

    public User(int uid, String firstName, String lastName, String emailAddress, String pword) {
        super(uid, firstName, lastName, emailAddress);
        password = pword;
    }

    /**
     *
     * @param pword The new password
     * @param pwordMatch repeated password, for verification
     * @throws IllegalCharacterException throws exception if the password
     * contains illegal characters
     * @throws PasswordMismatchError throws exception if the passwords don't
     * match.
     */
    public void setPassword(String pword, String pwordMatch) throws
            IllegalCharacterException, PasswordMismatchError, DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission("USERS", "PWD", Operation.MODIFY, getUserId());

        if (checkCharacters(pword)) {
            if (verifyPassword(pword, pwordMatch)) {
                password = pword;
            } else {
                throw new PasswordMismatchError();
            }
        } else {
            throw new IllegalCharacterException("Password contains illegal characters");
        }
    }

    private boolean verifyPassword(String pword, String pwordMatch) {
        return pword.equals(pwordMatch);
    }

    /**
     *
     * @return username
     */
    /**
     *
     * @return password
     */
    public String getPassword() throws AuthorizationException {
        Permissions.get().checkPermission("USERS", "PWD", Operation.VIEW, getUserId(), getPrivilegeLevel());
        return password;
    }

    /**
     * Checks a String object such as a username or password to see whether or
     * not it contains any illegal characters.
     *
     * @param s The String to be checked
     * @return Returns false if the string contains illegal characters,
     * otherwise returns true
     */
    private boolean checkCharacters(String s) {
        boolean b = true;
        for (char ic : ILLEGAL_CHARACTERS) {
            for (int x = 0; x < s.length(); x++) {
                if (ic == s.charAt(x)) {
                    b = false;
                }
            }
        }
        return b;
    }

    public boolean equals(User user) {
        return user != null && emailAddress != null && emailAddress.equals(user.emailAddress);

    }

    @Override
    public String toString() {
        try {
            return getFirstName() + " " + getLastName();
        } catch (AuthorizationException e) {
            return "Unknown User #" + getUserId();
        }
    }
}
