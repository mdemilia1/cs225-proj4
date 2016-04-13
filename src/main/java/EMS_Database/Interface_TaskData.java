package EMS_Database;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author mike
 */
public interface Interface_TaskData {

    // SPECIAL CASE FUNCTIONS
    public String queryEntireTable();

    public int createTask(InputTask task);

    public int nextValidUID();

    public void removeTask(int uid) throws DoesNotExistException, AuthorizationException;

    // GETTERS
    public String getDescription(int uid) throws DoesNotExistException, AuthorizationException;

    public String getDetails(int uid) throws DoesNotExistException, AuthorizationException;
    
    public String getTitle(int uid) throws DoesNotExistException, AuthorizationException;

    public Timestamp getStartDate(int uid) throws DoesNotExistException, AuthorizationException;

    public Timestamp getEndDate(int uid) throws DoesNotExistException, AuthorizationException;

    public int getComplete(int uid) throws DoesNotExistException, AuthorizationException;

    public ArrayList<Integer> getAuthority(int uid) throws DoesNotExistException, AuthorizationException;

    public String getStreet(int uid) throws DoesNotExistException, AuthorizationException; //location info

    public String getCity(int uid) throws DoesNotExistException, AuthorizationException; //location info

    public String getState(int uid) throws DoesNotExistException, AuthorizationException; //location info

    public String getZipcode(int uid) throws DoesNotExistException, AuthorizationException; //location info

    public String getCountry(int uid) throws DoesNotExistException, AuthorizationException; //location info

    // SETTERS      
    public void setDescription(int uid, String description) throws DoesNotExistException, AuthorizationException;

    public void setDetails(int uid, String details) throws DoesNotExistException, AuthorizationException;
    
    public void setTitle(int uid, String title) throws DoesNotExistException, AuthorizationException;

    public void setStartDate(int uid, Timestamp time) throws DoesNotExistException, AuthorizationException;

    public void setEndDate(int uid, Timestamp time) throws DoesNotExistException, AuthorizationException;

    public void setComplete(int uid, int complete) throws DoesNotExistException, AuthorizationException;

    public void setAuthority(int uid, ArrayList<Integer> committeeList) throws DoesNotExistException, AuthorizationException;

    public void setStreet(int uid, String street) throws DoesNotExistException, AuthorizationException; //location info

    public void setCity(int uid, String city) throws DoesNotExistException, AuthorizationException; //location info

    public void setState(int uid, String state) throws DoesNotExistException, AuthorizationException; //location info

    public void setZipcode(int uid, String zipcode) throws DoesNotExistException, AuthorizationException; //location info

    public void setCountry(int uid, String country) throws DoesNotExistException, AuthorizationException; //location info
}
