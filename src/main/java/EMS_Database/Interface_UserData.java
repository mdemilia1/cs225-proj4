package EMS_Database;

import BackEnd.UserSystem.Address;
import java.util.ArrayList;

/**
 *
 * @author mike
 */
public interface Interface_UserData {

    // SPECIAL FUNCTIONS
    public int createUser(InputUser user);        
    
    public int nextValidUID();
    
    public void removeUser(int uid) throws DoesNotExistException;        
        

    // GETTERS
    //public User getUser(int uid) throws DoesNotExistException;
    
    public ArrayList<Integer> getParticipantList() throws DoesNotExistException;
    
    public int getUIDByEmail(String email) throws DoesNotExistException;

    public String getFirstName(int uid) throws DoesNotExistException;
    
    public String getLastName(int uid) throws DoesNotExistException;

    public String getEmail(int uid) throws DoesNotExistException;

    public String getPwd(int uid) throws DoesNotExistException;

    public int getLevel(int uid) throws DoesNotExistException;
    
    public String getPhone(int uid) throws DoesNotExistException;
    
    public String getStreet(int uid) throws DoesNotExistException;
    
    public String getCity(int uid) throws DoesNotExistException;
    
    public String getState(int uid) throws DoesNotExistException;
    
    public String getZipcode(int uid) throws DoesNotExistException;
    
    public String getCountry(int uid) throws DoesNotExistException;
    
    public int getEventCreationPrivilege(int uid) throws DoesNotExistException;
    
    public boolean getParticipant(int uid) throws DoesNotExistException;
    
    
    //SETTERS   
    public void setAddress(int uid , Address address) throws DoesNotExistException;   

    public void setFirstName(int uid, String fname) throws DoesNotExistException;
    
    public void setLastName(int uid, String lname) throws DoesNotExistException;

    public void setEmail(int uid, String email) throws DoesNotExistException;

    public void setPwd(int uid, String pwd) throws DoesNotExistException;

    public void setLevel(int uid, int level) throws DoesNotExistException;
    
    public void setPhone(int uid, String phone) throws DoesNotExistException;
    
    public void setStreet(int uid, String street) throws DoesNotExistException;
    
    public void setCity(int uid, String city) throws DoesNotExistException;
    
    public void setState(int uid, String state) throws DoesNotExistException;
    
    public void setZipcode(int uid, String zipcode) throws DoesNotExistException;
    
    public void setCountry(int uid, String country) throws DoesNotExistException;
    
    public void setEventCreationPrivilege(int uid, int level) throws DoesNotExistException;
    
    public void setParticipant(int uid , boolean status) throws DoesNotExistException;
    
}
