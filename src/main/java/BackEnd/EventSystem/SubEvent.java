package BackEnd.EventSystem;

import BackEnd.UserSystem.Location;
import auth.AuthorizationException;
import auth.Operation;
import auth.Permissions;

import java.util.ArrayList;

/**
 *
 * @author Ketty Lezama
 */

public class SubEvent extends ScheduleItem implements Reportable {
    private int SUB_EVENT_ID;
    
    public SubEvent(){
        super();
    }
    
    public SubEvent(String title) {
        super(title);
    }
    public SubEvent(int sub_event_id, String description) {
        super(description);
        SUB_EVENT_ID = sub_event_id;
    }
    
    public SubEvent(int subEventID, SubEvent subEvent){
        super(subEvent);
        SUB_EVENT_ID = subEventID;
    }
    

    public int getSUB_EVENT_ID() throws AuthorizationException {
        Permissions.get().checkPermission("SUBEVENTS","UID", Operation.VIEW);
        return SUB_EVENT_ID;
    }

    public void setTitle(String title) throws AuthorizationException {
        Permissions.get().checkPermission("SUBEVENTS","TITLE", Operation.MODIFY);
        super.setTitle(title);
    }

    public String getTitle() throws AuthorizationException {
        Permissions.get().checkPermission("SUBEVENTS","TITLE", Operation.VIEW);
        return super.getTitle();
    }

    public void setDescription(String description) throws AuthorizationException {
        Permissions.get().checkPermission("SUBEVENTS","DESCRIPTION", Operation.MODIFY);
        super.setDescription(description);
    }

    public String getDescription() throws AuthorizationException {
        Permissions.get().checkPermission("SUBEVENTS","DESCRIPTION", Operation.VIEW);
        return super.getDescription();
    }

    public void setLocation(Location location) throws AuthorizationException {
        //Should only have to check one Field type of "Location" right?
        Permissions.get().checkPermission("SUBEVENTS","STREET", Operation.MODIFY);
        super.setLocation(location);
    }

    public Location getLocation() throws AuthorizationException {
        //See setLocation
        Permissions.get().checkPermission("SUBEVENTS","STREET", Operation.VIEW);
        return super.getLocation();
    }

    public void setTimeSchedule(TimeSchedule timeSchedule) throws AuthorizationException {
        super.setTimeSchedule(timeSchedule);
    }

    public TimeSchedule getTimeSchedule() throws AuthorizationException {
        return super.getTimeSchedule();
    }
    
    public boolean equals(SubEvent subEvent) throws AuthorizationException{
        if (this.getSUB_EVENT_ID() == subEvent.getSUB_EVENT_ID())
            return true;
        else
            return false;
    }
    
    public String toString() {
        try{
            return "Sub-Event Description: \n" + getDescription();
        }
        catch(AuthorizationException check){ return "Unauthorized Access";}

    }
    
    @Override
    public ArrayList<Object> getReport() throws AuthorizationException {
        ArrayList<Object> report = new ArrayList<Object>();
            report.add("" + this.getTitle());
            report.add("" + this.getDescription());
            report.add("" + this.getLocation().getCity());
            report.add("" + this.getLocation().getCountry());
            report.add("" + this.getLocation().getState());
            report.add("" + this.getLocation().getStreet());
            report.add("" + this.getLocation().getZipCode());
            report.add("" + this.getTimeSchedule().getStartDateTimeCalendar().getTime().getDay());
            report.add("" + this.getTimeSchedule().getEndDateTimeCalendar().getTime().getDay());
        
        return report;
    }
}