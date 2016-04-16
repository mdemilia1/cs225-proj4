package BackEnd.UserSystem;


import BackEnd.UserSystem.UserExceptions.PasswordMismatchError;
import BackEnd.UserSystem.UserExceptions.IllegalCharacterException;
import auth.PrivilegeLevel;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

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
        setPassword(pword, pwordMatch);
        

    }

    /*
     public User(int uid, String firstName, String lastName, String emailAddress, String pword, String pwordMatch) throws PasswordMismatchError, IllegalCharacterException
     {
     super(uid, firstName, lastName, emailAddress);
     setPassword(pword, pwordMatch);
            
     }*/
    public User(int userID, User user) {
        super(userID, user);

        password = user.getPassword();
        setPrivilegeLevel(user.getPrivilegeLevel());
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
            IllegalCharacterException, PasswordMismatchError{
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
    public String getPassword() {
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

    /**
     *
     * @param b boolean value determining if the user has admin privileges
     */
    @Deprecated
    public void setAdminPrivilege(boolean b) {
        setPrivilegeLevel(PrivilegeLevel.legacyFromLevels(b, getEventCreationPrivilege()));
    }

    /**
     *
     * @return the user's admin privileges.
     */
    @Deprecated
    public boolean getAdminPrivilege() {
        return getPrivilegeLevel().legacyIsAdmin();
    }

    /**
     *
     * @param b boolean value determining if the user has event creation
     * privileges
     */
    @Deprecated
    public void setEventCreationPrivilege(boolean b) {
        setPrivilegeLevel(PrivilegeLevel.legacyFromLevels(getAdminPrivilege(), b));
    }

    /**
     *
     * @return the user's event creation privileges
     */
    @Deprecated
    public boolean getEventCreationPrivilege() {
        return getPrivilegeLevel().legacyIsEventCreator();
    }

    public boolean equals(User user) {
        if (user == null) {
            return false;
        }
        String s = this.getEmailAddress();
        return s.equals(user.getEmailAddress());
    }

    public String toString() {
//        String output = "\n" + super.toString() +
//                "\nPassword: " + password +
//                "\nAdmin Privileges: " + adminPrivilege +
//                "\nEvent Creation Privileges: " + eventCreationPrivilege;
//        return output;
        return getFirstName() + " " + getLastName();
    }
}
