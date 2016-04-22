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

    public void setTitle(String title)  {
        Permissions.get().checkPermission("SUBEVENTS","TITLE", Operation.MODIFY);
        super.setTitle(title);
    }

    public String getTitle() {
        Permissions.get().checkPermission("SUBEVENTS","TITLE", Operation.VIEW);
        return super.getTitle();
    }

    public void setDescription(String description) {
        Permissions.get().checkPermission("SUBEVENTS","DESCRIPTION", Operation.MODIFY);
        super.setDescription(description);
    }

    public String getDescription() {
        Permissions.get().checkPermission("SUBEVENTS","DESCRIPTION", Operation.VIEW);
        return super.getDescription();
    }

    public void setLocation(Location location) {
        //Should only have to check one Field type of "Location" right?
        Permissions.get().checkPermission("SUBEVENTS","STREET", Operation.MODIFY);
        super.setLocation(location);
    }

    public Location getLocation() {
        //See setLocation
        Permissions.get().checkPermission("SUBEVENTS","STREET", Operation.VIEW);
        return super.getLocation();
    }

    public void setTimeSchedule(TimeSchedule timeSchedule) {
        super.setTimeSchedule(timeSchedule);
    }

    public TimeSchedule getTimeSchedule() {
        return super.getTimeSchedule();
    }
    
    public boolean equals(SubEvent subEvent){
        try {
            if (this.getSUB_EVENT_ID() == subEvent.getSUB_EVENT_ID())
                return true;
            else
                return false;
        }catch (AuthorizationException authEx){ return false;}
    }
    
    public String toString() {
            return "Sub-Event Description: \n" + getDescription();

    }
    
    @Override
    public ArrayList<Object> getReport() {
        ArrayList<Object> report = new ArrayList<Object>();
        try {
            report.add("" + this.getTitle());
            report.add("" + this.getDescription());
            report.add("" + this.getLocation().getCity());
            report.add("" + this.getLocation().getCountry());
            report.add("" + this.getLocation().getState());
            report.add("" + this.getLocation().getStreet());
            report.add("" + this.getLocation().getZipCode());
            report.add("" + this.getTimeSchedule().getStartDateTimeCalendar().getTime().getDay());
            report.add("" + this.getTimeSchedule().getEndDateTimeCalendar().getTime().getDay());
        }catch (AuthorizationException authEx){}

        return report;
    }
}