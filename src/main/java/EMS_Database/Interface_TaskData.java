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

    public void removeTask(int uid) throws DoesNotExistException;

    // GETTERS
    public String getDescription(int uid) throws DoesNotExistException;

    public String getDetails(int uid) throws DoesNotExistException;
    
    public String getTitle(int uid) throws DoesNotExistException;

    public Timestamp getStartDate(int uid) throws DoesNotExistException;

    public Timestamp getEndDate(int uid) throws DoesNotExistException;

    public int getComplete(int uid) throws DoesNotExistException;

    public ArrayList<Integer> getAuthority(int uid) throws DoesNotExistException;

    public String getStreet(int uid) throws DoesNotExistException; //location info

    public String getCity(int uid) throws DoesNotExistException; //location info

    public String getState(int uid) throws DoesNotExistException; //location info

    public String getZipcode(int uid) throws DoesNotExistException; //location info

    public String getCountry(int uid) throws DoesNotExistException; //location info

    // SETTERS      
    public void setDescription(int uid, String description) throws DoesNotExistException;

    public void setDetails(int uid, String details) throws DoesNotExistException;
    
    public void setTitle(int uid, String title) throws DoesNotExistException;

    public void setStartDate(int uid, Timestamp time) throws DoesNotExistException;

    public void setEndDate(int uid, Timestamp time) throws DoesNotExistException;

    public void setComplete(int uid, int complete) throws DoesNotExistException;

    public void setAuthority(int uid, ArrayList<Integer> committeeList) throws DoesNotExistException;

    public void setStreet(int uid, String street) throws DoesNotExistException; //location info

    public void setCity(int uid, String city) throws DoesNotExistException; //location info

    public void setState(int uid, String state) throws DoesNotExistException; //location info

    public void setZipcode(int uid, String zipcode) throws DoesNotExistException; //location info

    public void setCountry(int uid, String country) throws DoesNotExistException; //location info
}
