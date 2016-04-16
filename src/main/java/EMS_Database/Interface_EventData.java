package EMS_Database;

import auth.AuthorizationException;
import exception.UpdateException;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author mike
 */
public interface Interface_EventData {
    // SPECIAL CASE FUNCTIONS
    int createEvent(InputEventData event) throws AuthorizationException, UpdateException;
    
    void removeEvent(int uid) throws AuthorizationException, UpdateException, DoesNotExistException;

    // GETTERS
    String getTitle(int uid) throws AuthorizationException, DoesNotExistException;
    
    String getDescription(int uid) throws AuthorizationException, DoesNotExistException;
    
    String getDetails(int uid) throws AuthorizationException, DoesNotExistException;

    Timestamp getStartDate(int uid) throws AuthorizationException, DoesNotExistException;

    Timestamp getEndDate(int uid) throws AuthorizationException, DoesNotExistException;

    int getComplete(int uid) throws AuthorizationException, DoesNotExistException;

    ArrayList<Integer> getCommittee(int uid) throws AuthorizationException, DoesNotExistException;
    
    ArrayList<Integer> getOrganizerList(int uid) throws AuthorizationException, DoesNotExistException;

    ArrayList<Integer> getSubEventList(int uid) throws AuthorizationException, DoesNotExistException;

    ArrayList<Integer> getParticipantList(int uid) throws AuthorizationException, DoesNotExistException;

    String getStreet(int uid) throws AuthorizationException, DoesNotExistException; //location info

    String getCity(int uid) throws AuthorizationException, DoesNotExistException; //location info

    String getState(int uid) throws AuthorizationException, DoesNotExistException; //location info

    String getZipcode(int uid) throws AuthorizationException, DoesNotExistException; //location info

    String getCountry(int uid) throws AuthorizationException, DoesNotExistException; //location info

    // SETTERS     
    void setTitle(int uid, String title) throws AuthorizationException, UpdateException, DoesNotExistException;
    
    void setDescription(int uid, String description) throws AuthorizationException, UpdateException, DoesNotExistException;
    
    void setDetails(int uid, String details) throws AuthorizationException, UpdateException, DoesNotExistException;

    void setStartDate(int uid, Timestamp time) throws AuthorizationException, UpdateException, DoesNotExistException;

    void setEndDate(int uid, Timestamp time) throws AuthorizationException, UpdateException, DoesNotExistException;

    void setComplete(int uid, int complete) throws AuthorizationException, UpdateException, DoesNotExistException;

    void setCommittee(int uid, ArrayList<Integer> committeeList) throws AuthorizationException, UpdateException, DoesNotExistException;
    
    void setOrganizerList(int uid, ArrayList<Integer> organizerList) throws AuthorizationException, UpdateException, DoesNotExistException;

    void setSubEventList(int uid, ArrayList<Integer> subEventList) throws AuthorizationException, UpdateException, DoesNotExistException;

    void setParticipantList(int uid, ArrayList<Integer> participantList) throws AuthorizationException, UpdateException, DoesNotExistException;

    void setStreet(int uid, String street) throws AuthorizationException, UpdateException, DoesNotExistException; //location info

    void setCity(int uid, String city) throws AuthorizationException, UpdateException, DoesNotExistException; //location info

    void setState(int uid, String state) throws AuthorizationException, UpdateException, DoesNotExistException; //location info

    void setZipcode(int uid, String zipcode) throws AuthorizationException, UpdateException, DoesNotExistException; //location info

    void setCountry(int uid, String country) throws AuthorizationException, UpdateException, DoesNotExistException; //location info
}
