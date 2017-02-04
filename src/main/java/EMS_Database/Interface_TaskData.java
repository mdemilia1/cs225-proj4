package EMS_Database;

import java.sql.Timestamp;
import java.util.ArrayList;
import auth.AuthorizationException;
import exception.UpdateException;

/**
 *
 * @author mike
 */
public interface Interface_TaskData {
    int createTask(InputTask task) throws AuthorizationException, UpdateException;

    void removeTask(int uid) throws DoesNotExistException, AuthorizationException, UpdateException;

    // GETTERS
    String getDescription(int uid) throws DoesNotExistException, AuthorizationException;

    String getDetails(int uid) throws DoesNotExistException, AuthorizationException;
    
    String getTitle(int uid) throws DoesNotExistException, AuthorizationException;

    Timestamp getStartDate(int uid) throws DoesNotExistException, AuthorizationException;

    Timestamp getEndDate(int uid) throws DoesNotExistException, AuthorizationException;

    int getComplete(int uid) throws DoesNotExistException, AuthorizationException;

    ArrayList<Integer> getAuthority(int uid) throws DoesNotExistException, AuthorizationException;

    String getStreet(int uid) throws DoesNotExistException, AuthorizationException; //location info

    String getCity(int uid) throws DoesNotExistException, AuthorizationException; //location info

    String getState(int uid) throws DoesNotExistException, AuthorizationException; //location info

    String getZipcode(int uid) throws DoesNotExistException, AuthorizationException; //location info

    String getCountry(int uid) throws DoesNotExistException, AuthorizationException; //location info

    // SETTERS      
    void setDescription(int uid, String description) throws DoesNotExistException, AuthorizationException, UpdateException;

    void setDetails(int uid, String details) throws DoesNotExistException, AuthorizationException, UpdateException;
    
    void setTitle(int uid, String title) throws DoesNotExistException, AuthorizationException, UpdateException;

    void setStartDate(int uid, Timestamp time) throws DoesNotExistException, AuthorizationException, UpdateException;

    void setEndDate(int uid, Timestamp time) throws DoesNotExistException, AuthorizationException, UpdateException;

    void setComplete(int uid, int complete) throws DoesNotExistException, AuthorizationException, UpdateException;

    void setAuthority(int uid, ArrayList<Integer> committeeList) throws DoesNotExistException, AuthorizationException, UpdateException;

    void setStreet(int uid, String street) throws DoesNotExistException, AuthorizationException, UpdateException; //location info

    void setCity(int uid, String city) throws DoesNotExistException, AuthorizationException, UpdateException; //location info

    void setState(int uid, String state) throws DoesNotExistException, AuthorizationException, UpdateException; //location info

    void setZipcode(int uid, String zipcode) throws DoesNotExistException, AuthorizationException, UpdateException; //location info

    void setCountry(int uid, String country) throws DoesNotExistException, AuthorizationException, UpdateException; //location info
}
