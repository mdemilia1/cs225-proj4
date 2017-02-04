package EMS_Database;

import auth.AuthorizationException;
import exception.UpdateException;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author mike
 */
public interface Interface_SubEventData {
    // SPECIAL CASE FUNCTIONS
    int createSubEvent(InputSubEventData subevent) throws AuthorizationException, UpdateException;

    void removeSubEvent(int uid) throws AuthorizationException, UpdateException, DoesNotExistException;

    //GETTERS
    String getDescription(int uid) throws AuthorizationException, DoesNotExistException;

    String getDetails(int uid) throws AuthorizationException, DoesNotExistException;

    String getTitle(int uid) throws AuthorizationException, DoesNotExistException;

    String getStreet(int uid) throws AuthorizationException, DoesNotExistException; //location info

    String getCity(int uid) throws AuthorizationException, DoesNotExistException; //location info

    String getState(int uid) throws AuthorizationException, DoesNotExistException; //location info

    String getZipcode(int uid) throws AuthorizationException, DoesNotExistException; //location info

    String getCountry(int uid) throws AuthorizationException, DoesNotExistException; //location info

    Timestamp getStartDate(int uid) throws AuthorizationException, DoesNotExistException;

    Timestamp getEndDate(int uid) throws AuthorizationException, DoesNotExistException;

    int getComplete(int uid) throws AuthorizationException, DoesNotExistException;

    //SETTERS
    void setDescription(int uid, String description) throws AuthorizationException, UpdateException, DoesNotExistException;

    void setDetails(int uid, String details) throws AuthorizationException, UpdateException, DoesNotExistException;

    void setTitle(int uid, String title) throws AuthorizationException, UpdateException, DoesNotExistException;

    void setStreet(int uid, String street) throws AuthorizationException, UpdateException, DoesNotExistException; //location info

    void setCity(int uid, String city) throws AuthorizationException, UpdateException, DoesNotExistException; //location info

    void setState(int uid, String state) throws AuthorizationException, UpdateException, DoesNotExistException; //location info

    void setZipcode(int uid, String zipcode) throws AuthorizationException, UpdateException, DoesNotExistException; //location info

    void setCountry(int uid, String country) throws AuthorizationException, UpdateException, DoesNotExistException; //location info

    void setStartDate(int uid, Timestamp time) throws AuthorizationException, UpdateException, DoesNotExistException;

    void setEndDate(int uid, Timestamp time) throws AuthorizationException, UpdateException, DoesNotExistException;

    void setComplete(int uid, int complete) throws AuthorizationException, UpdateException, DoesNotExistException;
}
