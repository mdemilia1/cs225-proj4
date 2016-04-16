package BackEnd.ManagerSystem;

import BackEnd.ManagerSystem.ManagerExceptions.LogInIncorrectException;
import BackEnd.UserSystem.Participant;
import BackEnd.UserSystem.User;
import EMS_Database.DoesNotExistException;

import java.util.ArrayList;

/**
 * @author David Tersoff
 */
public class LoginManager {

    private User loggedInUser;
    private ArrayList<Participant> userList;
    private final String INCORRECT_LOG_IN = "Incorrect log in information.";


    LoginManager(ArrayList<Participant> userList) throws
            DoesNotExistException {
        this.userList = userList;

    }

    public void setLoggedInUser(String emailAddress, String password)
            throws LogInIncorrectException {
        User user = checkEmailAddress(emailAddress);
        checkPassword(user, password);
        loggedInUser = user;
    }

    private User checkEmailAddress(String emailAddress) throws LogInIncorrectException {
        for (Participant anUserList : userList) {
            if (anUserList.getEmailAddress().equalsIgnoreCase(emailAddress)) {
                return (User) anUserList;
            }
        }
        throw new LogInIncorrectException(INCORRECT_LOG_IN);
    }

    // TODO: This is an extremely bad way to check a password.  [Paul Buonopane]
    private boolean checkPassword(User user, String password) throws
            LogInIncorrectException {

        if (user.getPassword().equals(password)) {
            return true;
        } else {
            throw new LogInIncorrectException(INCORRECT_LOG_IN);
        }

    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void logOut() {
        loggedInUser = null;
    }
}