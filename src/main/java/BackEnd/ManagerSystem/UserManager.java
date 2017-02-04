package BackEnd.ManagerSystem;

import BackEnd.ManagerSystem.ManagerExceptions.PrivilegeInsufficientException;
import BackEnd.ManagerSystem.ManagerExceptions.DuplicateEmailException;
import BackEnd.UserSystem.Address;
import BackEnd.UserSystem.UserExceptions.IllegalCharacterException;
import BackEnd.UserSystem.Participant;
import BackEnd.UserSystem.UserExceptions.PasswordMismatchError;
import BackEnd.UserSystem.PhoneNumber;
import BackEnd.UserSystem.User;
import BackEnd.UserSystem.UserExceptions.ValidationException;
import EMS_Database.DoesNotExistException;
import EMS_Database.InputUser;
import EMS_Database.impl.UserData_Table;
import auth.AuthorizationException;
import auth.Permissions;
import auth.PrivilegeLevel;
import exception.UpdateException;

import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.swing.*;

/**
 *
 * @author David Tersoff
 */
public class UserManager {

    private ArrayList<Participant> userList;
    private User selectedUser;
    private UserData_Table usersTable;
    private LoginManager logInManager;

    public UserManager()
            throws DoesNotExistException {

        usersTable = new UserData_Table();
        userList = new ArrayList<>();
        rebuildUserList();
    }

    void connectManagers(LoginManager logInManager) {
        this.logInManager = logInManager;
    }

    public UserData_Table getUsersTable() {
        return usersTable;
    }

    public ArrayList<Participant> getUserList() {
        return userList;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public User getUserByUID(int uid) throws AuthorizationException, DoesNotExistException {
        return usersTable.getUser(uid);
    }

    private void rebuildUserList()
            throws DoesNotExistException {

        try (Permissions.SystemTransaction ignored = Permissions.get().beginSystemTransaction()) {
            for (Integer userID : usersTable.getUIDsNeedingRebuild()) {
                try {
                    if (usersTable.getPrivilegeLevel(userID).isParticipant()) {
                        userList.add(rebuildParticipant(userID));
                    } else {
                        userList.add(rebuildUser(userID));
                    }
                } catch (AuthorizationException e) {
                    // Ignore
                }
            }
        }
    }

    private Participant rebuildParticipant(int userID)
            throws DoesNotExistException, AuthorizationException {

        Participant participant = new Participant(
                userID, usersTable.getFirstName(userID), usersTable.getLastName(userID), usersTable.getEmail(userID));
        participant.setPhoneNumber(new PhoneNumber(usersTable.getPhone(userID)));
        participant.setAddress(new Address(usersTable.getStreet(userID), usersTable.getCity(userID),
                usersTable.getState(userID), usersTable.getZipcode(userID), usersTable.getCountry(userID)));

        return participant;
    }

    private User rebuildUser(int userID)
            throws DoesNotExistException, AuthorizationException {

        User user = new User(
                userID, usersTable.getFirstName(userID), usersTable.getLastName(userID),
                usersTable.getEmail(userID), usersTable.getPwd(userID));

        user.setPrivilegeLevel(usersTable.getPrivilegeLevel(userID));
        user.setPhoneNumber(new PhoneNumber(usersTable.getPhone(userID)));
        user.setAddress(new Address(usersTable.getStreet(userID), usersTable.getCity(userID),
                usersTable.getState(userID), usersTable.getZipcode(userID), usersTable.getCountry(userID)));

        return user;
    }

    public User createUser(User user)
            throws DuplicateEmailException, AuthorizationException, UpdateException {

        if (usersTable.checkEmail(user.getEmailAddress())) {
            throw new DuplicateEmailException("Email address already exists in the system");
        } else {
            User newUser = new User(usersTable.createUser(new InputUser(user)), user);
            userList.add(newUser);
            return newUser;
        }
    }

    public void deleteUser(User user)
            throws DoesNotExistException, UpdateException, AuthorizationException {
        userList.remove(selectedUser);
        usersTable.removeUser(selectedUser.getUserId());
    }

    public void editFirstName(String firstName)
            throws PrivilegeInsufficientException, DoesNotExistException, UpdateException, AuthorizationException {

        User loggedInUser = logInManager.getLoggedInUser();
        if (PrivilegeManager.hasUserPrivilege(loggedInUser, selectedUser)) {
            selectedUser.setFirstName(firstName);
            usersTable.setFirstName(selectedUser.getUserId(), firstName);
        }
    }

    public void editLastName(String lastName)
            throws PrivilegeInsufficientException, DoesNotExistException, UpdateException, AuthorizationException {

        User loggedInUser = logInManager.getLoggedInUser();
        if (PrivilegeManager.hasUserPrivilege(loggedInUser, selectedUser)) {
            selectedUser.setLastName(lastName);
            usersTable.setLastName(selectedUser.getUserId(), lastName);
        }
    }

    public void editEmailAddress(String emailAddress)
            throws PrivilegeInsufficientException, DoesNotExistException, UpdateException, AuthorizationException, ValidationException {
        try {
            User loggedInUser = logInManager.getLoggedInUser();
            if (PrivilegeManager.hasUserPrivilege(loggedInUser, selectedUser)) {
                selectedUser.setEmailAddress(emailAddress);
                usersTable.setEmail(selectedUser.getUserId(), emailAddress);
            }
        }
        catch (ValidationException e) {
            JOptionPane.showMessageDialog(new JFrame("Error"),e.getMessage());
            return;
        }
    }

    public void editAddress(Address address)
            throws PrivilegeInsufficientException, DoesNotExistException, UpdateException, AuthorizationException {

        User loggedInUser = logInManager.getLoggedInUser();
        if (PrivilegeManager.hasUserPrivilege(loggedInUser, selectedUser)) {
            selectedUser.setAddress(address);
            usersTable.setAddress(selectedUser.getUserId(), address);
        }
    }

    public void editPhoneNumber(PhoneNumber phoneNumber)
            throws PrivilegeInsufficientException, DoesNotExistException, UpdateException, AuthorizationException {

        User loggedInUser = logInManager.getLoggedInUser();
        if (PrivilegeManager.hasUserPrivilege(loggedInUser, selectedUser)) {
            selectedUser.setPhoneNumber(phoneNumber);
            usersTable.setPhone(selectedUser.getUserId(), phoneNumber.toString());
        }
    }

    public void editPassword(String password, String passwordMatch)
            throws IllegalCharacterException, PasswordMismatchError,
            PrivilegeInsufficientException, DoesNotExistException,
            InvalidKeyException, UnsupportedEncodingException,
            IllegalBlockSizeException, BadPaddingException, UpdateException, AuthorizationException {

        User loggedInUser = logInManager.getLoggedInUser();
        if (PrivilegeManager.hasUserPrivilege(loggedInUser, selectedUser)) {
            selectedUser.setPassword(password, passwordMatch);
            usersTable.setPwd(selectedUser.getUserId(), password);
        }
    }

    public void editPrivilegeLevel(PrivilegeLevel privilegeLevel)
            throws PrivilegeInsufficientException, DoesNotExistException, UpdateException, AuthorizationException{
        User loggedInUser = logInManager.getLoggedInUser();
        if (PrivilegeManager.hasAdminPrivilege(loggedInUser)) {
            selectedUser.setPrivilegeLevel(privilegeLevel);
            usersTable.setPrivilegeLevel(selectedUser.getUserId(), privilegeLevel);
        }
    }
}