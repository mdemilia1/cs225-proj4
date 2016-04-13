package EMS_Database;

import BackEnd.UserSystem.Address;
import auth.AuthorizationException;

import java.util.ArrayList;

/**
 *
 * @author mike
 */
public interface Interface_UserData {

    // SPECIAL FUNCTIONS
    public int createUser(InputUser user) throws AuthorizationException;
    
    public int nextValidUID() throws AuthorizationException;
    
    public void removeUser(int uid) throws DoesNotExistException, AuthorizationException;
        

    // GETTERS
    //public User getUser(int uid) throws DoesNotExistException;
    
    public ArrayList<Integer> getParticipantList() throws DoesNotExistException, AuthorizationException;
    
    public int getUIDByEmail(String email) throws DoesNotExistException, AuthorizationException;

    public String getFirstName(int uid) throws DoesNotExistException, AuthorizationException;
    
    public String getLastName(int uid) throws DoesNotExistException, AuthorizationException;

    public String getEmail(int uid) throws DoesNotExistException, AuthorizationException;

    public String getPwd(int uid) throws DoesNotExistException, AuthorizationException;

    public int getLevel(int uid) throws DoesNotExistException, AuthorizationException;
    
    public String getPhone(int uid) throws DoesNotExistException, AuthorizationException;
    
    public String getStreet(int uid) throws DoesNotExistException, AuthorizationException;
    
    public String getCity(int uid) throws DoesNotExistException, AuthorizationException;
    
    public String getState(int uid) throws DoesNotExistException, AuthorizationException;
    
    public String getZipcode(int uid) throws DoesNotExistException, AuthorizationException;
    
    public String getCountry(int uid) throws DoesNotExistException, AuthorizationException;
    
    public int getEventCreationPrivilege(int uid) throws DoesNotExistException, AuthorizationException;
    
    public boolean getParticipant(int uid) throws DoesNotExistException, AuthorizationException;
    
    
    //SETTERS   
    public void setAddress(int uid , Address address) throws DoesNotExistException, AuthorizationException;

    public void setFirstName(int uid, String fname) throws DoesNotExistException, AuthorizationException;
    
    public void setLastName(int uid, String lname) throws DoesNotExistException, AuthorizationException;

    public void setEmail(int uid, String email) throws DoesNotExistException, AuthorizationException;

    public void setPwd(int uid, String pwd) throws DoesNotExistException, AuthorizationException;

    public void setLevel(int uid, int level) throws DoesNotExistException, AuthorizationException;
    
    public void setPhone(int uid, String phone) throws DoesNotExistException, AuthorizationException;
    
    public void setStreet(int uid, String street) throws DoesNotExistException, AuthorizationException;
    
    public void setCity(int uid, String city) throws DoesNotExistException, AuthorizationException;
    
    public void setState(int uid, String state) throws DoesNotExistException, AuthorizationException;
    
    public void setZipcode(int uid, String zipcode) throws DoesNotExistException, AuthorizationException;
    
    public void setCountry(int uid, String country) throws DoesNotExistException, AuthorizationException;
    
    public void setEventCreationPrivilege(int uid, int level) throws DoesNotExistException, AuthorizationException;
    
    public void setParticipant(int uid , boolean status) throws DoesNotExistException, AuthorizationException;
    
}
