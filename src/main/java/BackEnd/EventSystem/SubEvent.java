package BackEnd.EventSystem;

import auth.AuthorizationException;
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
        super((ScheduleItem)subEvent);
        SUB_EVENT_ID = subEventID;
    }
    
    private void setSUB_EVENT_ID(int sub_event_id) throws AuthorizationException {
        SUB_EVENT_ID = sub_event_id;
    }
    
    public int getSUB_EVENT_ID() throws AuthorizationException {
        return SUB_EVENT_ID;
    }
    
    public boolean equals(SubEvent subEvent) throws AuthorizationException{
        if (this.getSUB_EVENT_ID() == subEvent.getSUB_EVENT_ID())
            return true;
        else
            return false;
    }
    
    public String toString() {
        try(Permissions.SystemTransaction ignored = Permissions.get().beginSystemTransaction()) {
            return "Sub-Event Description: \n" + super.getDescription();
        }
        catch(AuthorizationException ignored){}
        return null;
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