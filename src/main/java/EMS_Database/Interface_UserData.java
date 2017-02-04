package EMS_Database;

import BackEnd.UserSystem.Address;
import BackEnd.UserSystem.User;
import auth.AuthorizationException;
import auth.PrivilegeLevel;
import exception.UpdateException;

import java.util.ArrayList;

/**
 * @author mike
 */
public interface Interface_UserData {
    // SPECIAL FUNCTIONS
    int createUser(InputUser user) throws AuthorizationException, UpdateException;

    void removeUser(int uid) throws DoesNotExistException, AuthorizationException, UpdateException;


    // GETTERS
    User getUser(int uid) throws DoesNotExistException, AuthorizationException;

    ArrayList<Integer> getParticipantList() throws DoesNotExistException, AuthorizationException;

    int getUIDByEmail(String email) throws DoesNotExistException, AuthorizationException;

    String getFirstName(int uid) throws DoesNotExistException, AuthorizationException;

    String getLastName(int uid) throws DoesNotExistException, AuthorizationException;

    String getEmail(int uid) throws DoesNotExistException, AuthorizationException;

    String getPwd(int uid) throws DoesNotExistException, AuthorizationException;

    PrivilegeLevel getPrivilegeLevel(int uid) throws DoesNotExistException, AuthorizationException;

    String getPhone(int uid) throws DoesNotExistException, AuthorizationException;

    String getStreet(int uid) throws DoesNotExistException, AuthorizationException;

    String getCity(int uid) throws DoesNotExistException, AuthorizationException;

    String getState(int uid) throws DoesNotExistException, AuthorizationException;

    String getZipcode(int uid) throws DoesNotExistException, AuthorizationException;

    String getCountry(int uid) throws DoesNotExistException, AuthorizationException;


    //SETTERS
    void setAddress(int uid, Address address) throws DoesNotExistException, AuthorizationException, UpdateException;

    void setFirstName(int uid, String fname) throws DoesNotExistException, AuthorizationException, UpdateException;

    void setLastName(int uid, String lname) throws DoesNotExistException, AuthorizationException, UpdateException;

    void setEmail(int uid, String email) throws DoesNotExistException, AuthorizationException, UpdateException;

    void setPwd(int uid, String pwd) throws DoesNotExistException, AuthorizationException, UpdateException;

    void setPhone(int uid, String phone) throws DoesNotExistException, AuthorizationException, UpdateException;

    void setStreet(int uid, String street) throws DoesNotExistException, AuthorizationException, UpdateException;

    void setCity(int uid, String city) throws DoesNotExistException, AuthorizationException, UpdateException;

    void setState(int uid, String state) throws DoesNotExistException, AuthorizationException, UpdateException;

    void setZipcode(int uid, String zipcode) throws DoesNotExistException, AuthorizationException, UpdateException;

    void setCountry(int uid, String country) throws DoesNotExistException, AuthorizationException, UpdateException;

    void setPrivilegeLevel(int uid, PrivilegeLevel privilegeLevel) throws DoesNotExistException, AuthorizationException, UpdateException;

}
