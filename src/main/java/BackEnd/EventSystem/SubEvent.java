package BackEnd.EventSystem;

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
    
    private void setSUB_EVENT_ID(int sub_event_id) {
        SUB_EVENT_ID = sub_event_id;
    }
    
    public int getSUB_EVENT_ID() {
        return SUB_EVENT_ID;
    }
    
    public boolean equals(SubEvent subEvent) {
        if (this.getSUB_EVENT_ID() == subEvent.getSUB_EVENT_ID())
            return true;
        else
            return false;
    }
    
    public String toString() {
        return "Sub-Event Description: \n" + super.getDescription();
    }
    
    @Override
    public ArrayList<Object> getReport() {
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