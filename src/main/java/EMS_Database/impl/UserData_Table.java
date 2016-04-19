package EMS_Database.impl;

import BackEnd.UserSystem.Address;
import BackEnd.UserSystem.User;
import BackEnd.UserSystem.UserExceptions.ValidationException;
import EMS_Database.DoesNotExistException;
import EMS_Database.InitDB;
import EMS_Database.InputUser;
import EMS_Database.Interface_UserData;
import auth.*;
import exception.ReadException;
import exception.UpdateException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Database implementation of all user related data.
 * <p>
 * This class implements all methods surrounding the table which holds the data
 * for all the users in the EventManagementSystem project. It functions as
 * follows, there are 2 special case methods which allow you to insert a user
 * into the database based on the parameters specified by User class and there
 * is a special case to look up users by there effective username. Unless
 * otherwise specified all Accessor/Mutator methods will reference the UID of
 * the user to access or mutate data.
 *
 * @author Mike Meding
 */
public class UserData_Table extends InitDB implements Interface_UserData {
    private static final String tableName = "USERS";

    @Override
    protected String getTableName() {
        return tableName;
    }

    ///////////////////// SPECIAL FUNCTIONS //////////////////////////////

    /**
     * Adds a new user to the database.
     *
     * @param user The user to be added to the database.
     *             <p>
     *             This function requires that you use the User class as a way of correctly
     *             inserting data. null is perfectly acceptable to use as a field of user
     *             when it is created. UID is required and is not checked by this function.
     *             It is your responsibility to make sure that you do not end up with
     *             duplicate UIDs.
     * @return true upon successful insertion of user into database
     */
    @Override
    public int createUser(InputUser user) throws AuthorizationException, UpdateException {
        Permissions.get().checkPermission(tableName, null, Operation.CREATE, Who.OTHER, user.getPrivilegeLevel());

        try {
            //Creating Statement
            PreparedStatement AddAddressStmt = dbConnection.prepareStatement("INSERT INTO USERS VALUES(NULL,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            int column = 0;
            AddAddressStmt.setInt(++column, user.getPrivilegeLevel().id());
            AddAddressStmt.setString(++column, user.getFirstName());
            AddAddressStmt.setString(++column, user.getLastName());
            AddAddressStmt.setString(++column, user.getPwd());
            AddAddressStmt.setString(++column, user.getEmail());
            AddAddressStmt.setString(++column, user.getPhone());
            AddAddressStmt.setString(++column, user.getStreet());
            AddAddressStmt.setString(++column, user.getCity());
            AddAddressStmt.setString(++column, user.getState());
            AddAddressStmt.setString(++column, user.getZipcode());
            AddAddressStmt.setString(++column, user.getCountry());

            //Execute Statement
            return AddAddressStmt.executeUpdate();
        } catch (SQLException sqle) {
            throw new UpdateException("Error creating user", sqle);
        }
    }

    /**
     * A method to look up a UID by email address.
     *
     * @param email String of the username to search for
     * @return int of the UID if successful.
     * @throws DoesNotExistException if user does not exist.
     */
    @Override
    public int getUIDByEmail(String email) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "EMAIL", Operation.VIEW);

        try (PreparedStatement idQueryStmt = dbConnection.prepareStatement("SELECT UID FROM USERS WHERE EMAIL LIKE ? ESCAPE '\\'")) {
            int param = 0;
            idQueryStmt.setString(++param, like(email));

            ResultSet rs = idQueryStmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("UID");
            } else {
                throw new DoesNotExistException("USER");
            }
        } catch (SQLException sqle) {
            throw new ReadException("Error reading user ID", sqle);
        }
    }

    /**
     * This function is used to remove a user from the database with the
     * specified UID
     *
     * @param uid the UID of the user to be removed.
     * @return true, upon successful removal from the database.
     * @throws DoesNotExistException if the user you are trying to delete does
     *                               not exist.
     */
    @Override
    public void removeUser(int uid) throws DoesNotExistException, AuthorizationException, UpdateException {
        remove(uid, uid);
    }

    /**
     * Checks the provided email against those currently in the database.
     * <p>
     * If a hit is found the function returns true indicating that the email exists.
     * Otherwise a false is returned.
     *
     * @param email a string of the email to be checked.
     * @return if that email already exists or not.
     */
    public boolean checkEmail(String email) throws AuthorizationException {
        Permissions.get().checkPermission(tableName, "EMAIL", Operation.VIEW);

        try (PreparedStatement idQueryStmt = dbConnection.prepareStatement("SELECT 1 FROM USERS WHERE EMAIL LIKE ? ESCAPE '\\'")) {
            int param = 0;
            idQueryStmt.setString(++param, like(email));

            ResultSet rs = idQueryStmt.executeQuery();
            return rs.getFetchSize() > 0;
        } catch (SQLException sqle) {
            throw new ReadException("Error checking email", sqle);
        }
    }

    ///////////////////// GETTERS ////////////////////////////
    public User getUser(int uid) throws DoesNotExistException, AuthorizationException {
        try (Permissions.SystemTransaction ignored = Permissions.get().beginSystemTransaction()) {
            PreparedStatement idQueryStmt = dbConnection.prepareStatement("SELECT * FROM USERS WHERE UID=?");
            idQueryStmt.setInt(1, uid);
            ResultSet rs = idQueryStmt.executeQuery();

            User user = new User();
            Address address = new Address();

            if (rs.next()) {
                user.setPrivilegeLevel(PrivilegeLevel.fromID(rs.getInt("LEVEL")));
                user.setFirstName(rs.getString("FNAME"));
                user.setLastName(rs.getString("LNAME"));
                try {
                    user.setEmailAddress(rs.getString("EMAIL"));
                }
                catch (ValidationException e) {
                    e.getMessage();
                }
                user.setPhoneNumber(null);
                address.setStreet(rs.getString("STREET"));
                address.setCity(rs.getString("CITY"));
                address.setState(rs.getString("STATE"));
                address.setZipCode(rs.getString("ZIPCODE"));
                address.setCountry(rs.getString("COUNTRY"));
                user.setAddress(address); //insert address into user.
            } else {
                throw new DoesNotExistException("User doesn't exist");
            }

            return user;
        } catch (SQLException sqle) {
            throw new ReadException("Unable to retrieve user", sqle);
        }
    }

    /**
     * This function serves to return a list of users that may exist in the user
     * table who are only participants of an event, not official users. (no login
     * info.)
     *
     * @return returns an ArrayList<Integer> of the uid values that are
     * considered participants
     */
    @Override
    public ArrayList<Integer> getParticipantList() throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "LEVEL", Operation.VIEW);

        try (PreparedStatement idQueryStmt = dbConnection.prepareStatement("SELECT UID FROM USERS WHERE " + PrivilegeLevel.getParticipantQuery())) {
            ResultSet rs = idQueryStmt.executeQuery();
            ArrayList<Integer> list = new ArrayList<>();

            while (rs.next()) {
                list.add(rs.getInt("UID"));
            }

            return list;
        } catch (SQLException sqle) {
            throw new ReadException("Unable to read list of participants", sqle);
        }
    }

    /**
     * Returns the First Name of the user with the UID specified
     *
     * @param uid The UID of the user in question.
     * @return String of the username that was specified.
     */
    @Override
    public String getFirstName(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "FNAME", Operation.VIEW);
        return getDBString("FNAME", uid);
    }

    /**
     * Returns the Last Name of the user with the specified UID.
     *
     * @param uid
     * @return
     * @throws DoesNotExistException
     */
    @Override
    public String getLastName(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "LNAME", Operation.VIEW);
        return getDBString("LNAME", uid);
    }

    /**
     * A function to return the email of the user specified by UID.
     *
     * @param uid The UID of the user in question.
     * @return String of the email of the user in question.
     * @throws DoesNotExistException if user does not exist.
     */
    @Override
    public String getEmail(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "EMAIL", Operation.VIEW);
        return getDBString("EMAIL", uid);
    }

    /**
     * A method to return the password of the user specified by UID.
     * <p>
     * This method should have passwords hashed into it. No security is
     * implemented with this method. Cyphers and Hashes are your responsibility.
     * This is simply a String method.
     *
     * @param uid The UID of the user in question
     * @return String of the password.
     * @throws DoesNotExistException if the UID does not exist.
     */
    @Override
    public String getPwd(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "PWD", Operation.VIEW);
        return getDBString("PWD", uid);
    }

    /**
     * Returns the user privilege level of the user specified by uid. (LEVEL
     * CANNOT EQUAL 0!)
     *
     * @param uid The unique user id of the user in question.
     * @return user level
     * @throws DoesNotExistException if user does not exist
     */
    @Override
    public PrivilegeLevel getPrivilegeLevel(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "LEVEL", Operation.VIEW);
        return PrivilegeLevel.fromID(getDBInt("LEVEL", uid));
    }

    /**
     * Returns the users phone number as a String.
     *
     * @param uid
     * @return String phone number
     * @throws DoesNotExistException if user does not exist.
     */
    @Override
    public String getPhone(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "PHONE", Operation.VIEW);
        return getDBString("PHONE", uid);
    }

    /**
     * Returns the Street address of the user as a string.
     *
     * @param uid the UID of the user.
     * @return
     * @throws DoesNotExistException
     */
    @Override
    public String getStreet(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "STREET", Operation.VIEW);
        return getDBString("STREET", uid);
    }

    /**
     * Returns the specified city if with the specified UID.
     *
     * @param uid the UID of the user
     * @return a String of the City of the user
     * @throws DoesNotExistException
     */
    @Override
    public String getCity(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "CITY", Operation.VIEW);
        return getDBString("CITY", uid);
    }

    /**
     * Returns the which of the fifty united states the user lives in...
     *
     * @param uid
     * @return
     * @throws DoesNotExistException
     */
    @Override
    public String getState(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "STATE", Operation.VIEW);
        return getDBString("STATE", uid);
    }

    /**
     * I give you props for reading my javadoc this far.
     *
     * @param uid
     * @return seriously? if you haven't figured out the pattern yet then....
     * @throws DoesNotExistException
     */
    @Override
    public String getZipcode(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "ZIPCODE", Operation.VIEW);
        return getDBString("ZIPCODE", uid);
    }

    /**
     * Returns which country the user resides in. MURICA!
     *
     * @param uid
     * @return MURICA!
     * @throws DoesNotExistException if you don't live in MURICA!
     */
    @Override
    public String getCountry(int uid) throws DoesNotExistException, AuthorizationException {
        Permissions.get().checkPermission(tableName, "COUNTRY", Operation.VIEW);
        return getDBString("COUNTRY", uid);
    }

    ////////////////////// SETTERS ///////////////////////////////

    /**
     * A method to update the address that takes a type of address. WRAPPER
     * FUNCTION
     *
     * @param uid     the uid to be updated
     * @param address the address of type Address to use for data to be updated.
     * @throws DoesNotExistException if the UID does not exist in the table.
     */
    @Override
    public void setAddress(int uid, Address address) throws DoesNotExistException, AuthorizationException, UpdateException {
        setStreet(uid, address.getStreet());
        setCity(uid, address.getCity());
        setState(uid, address.getState());
        setZipcode(uid, address.getZipCode());
        setCountry(uid, address.getCountry());
    }

    /**
     * Updates the first name of the user specified by UID
     *
     * @param uid   The UID of the user to have its name updated
     * @param fname Specifies the new username to replace the one specified by
     *              uid
     * @throws DoesNotExistException if the UID specified does not exist.
     */
    @Override
    public void setFirstName(int uid, String fname) throws DoesNotExistException, AuthorizationException, UpdateException {
        Permissions.get().checkPermission(tableName, "FNAME", Operation.MODIFY);
        setDBString("FNAME", uid, fname);
    }

    /**
     * Sets the Last Name of the user specified by the UID
     *
     * @param uid   The UID of the user to change
     * @param lname The new last name.
     * @throws DoesNotExistException if the user searched for does not exist.
     */
    @Override
    public void setLastName(int uid, String lname) throws DoesNotExistException, AuthorizationException, UpdateException {
        Permissions.get().checkPermission(tableName, "LNAME", Operation.MODIFY);
        setDBString("LNAME", uid, lname);
    }

    /**
     * Sets the email or rather USERNAME of the UID in question
     *
     * @param uid   the user being searched for
     * @param email the new email as a string
     * @throws DoesNotExistException if the user UID is not found.
     */
    @Override
    public void setEmail(int uid, String email) throws DoesNotExistException, AuthorizationException, UpdateException {
        Permissions.get().checkPermission(tableName, "EMAIL", Operation.MODIFY);
        setDBString("EMAIL", uid, email);
    }

    /**
     * Sets the password of the user with the specified UID forgetting the old
     * one.
     *
     * @param uid the UID of the user
     * @param pwd the password as a string to be changed.
     * @throws DoesNotExistException if the user searched for does not exist
     */
    @Override
    public void setPwd(int uid, String pwd) throws DoesNotExistException, AuthorizationException, UpdateException {
        Permissions.get().checkPermission(tableName, "PWD", Operation.MODIFY);
        setDBString("PWD", uid, pwd);
    }

    /**
     * Sets the Phone number of the user specified by the UID.
     *
     * @param uid   The UID of the user to have the number changed.
     * @param phone The new number. Represented as a string.
     * @throws DoesNotExistException If the UID does not exist.
     */
    @Override
    public void setPhone(int uid, String phone) throws DoesNotExistException, AuthorizationException, UpdateException {
        Permissions.get().checkPermission(tableName, "PHONE", Operation.MODIFY);
        setDBString("PHONE", uid, phone);
    }

    /**
     * Sets the Street name of the UID.
     *
     * @param uid    The user you are looking for
     * @param street the new street name as a String.
     * @throws DoesNotExistException if the user does not exist.
     */
    @Override
    public void setStreet(int uid, String street) throws DoesNotExistException, AuthorizationException, UpdateException {
        Permissions.get().checkPermission(tableName, "STREET", Operation.MODIFY, uid);
        setDBString("STREET", uid, street);
    }

    /**
     * Sets the city of the UID in question.
     *
     * @param uid  the user being searched for.
     * @param city the new city as a String.
     * @throws DoesNotExistException if the UID does not exist.
     */
    @Override
    public void setCity(int uid, String city) throws DoesNotExistException, AuthorizationException, UpdateException {
        Permissions.get().checkPermission(tableName, "CITY", Operation.MODIFY, uid);
        setDBString("CITY", uid, city);
    }

    /**
     * Sets the current state in the 50 united states that the user lives.
     *
     * @param uid   the UID of the user.
     * @param state the new state in which they live. NERDVANA!
     * @throws DoesNotExistException
     */
    @Override
    public void setState(int uid, String state) throws DoesNotExistException, AuthorizationException, UpdateException {
        Permissions.get().checkPermission(tableName, "STATE", Operation.MODIFY, uid);
        setDBString("STATE", uid, state);
    }

    /**
     * Sets the zipcode of the UID specified.
     *
     * @param uid     the UID of the user.
     * @param zipcode the new zipcode of the user. 90210....
     * @throws DoesNotExistException
     */
    @Override
    public void setZipcode(int uid, String zipcode) throws DoesNotExistException, AuthorizationException, UpdateException {
        Permissions.get().checkPermission(tableName, "ZIPCODE", Operation.MODIFY, uid);
        setDBString("ZIPCODE", uid, zipcode);
    }

    /**
     * Sets the Country in which the UID lives. MURICA!
     *
     * @param uid     the UID of the user.
     * @param country the new country in which he lives.
     * @throws DoesNotExistException if you attempt to set a country besides
     */
    @Override
    public void setCountry(int uid, String country) throws DoesNotExistException, AuthorizationException, UpdateException {
        Permissions.get().checkPermission(tableName, "COUNTRY", Operation.MODIFY, uid);
        setDBString("COUNTRY", uid, country);
    }

    @Override
    public void setPrivilegeLevel(int uid, PrivilegeLevel privilegeLevel) throws DoesNotExistException, AuthorizationException, UpdateException {
        Permissions.get().checkPermission(tableName, "LEVEL", Operation.MODIFY, uid);
        setDBInt("LEVEL", uid, privilegeLevel.id());
    }
}
