package BackEnd.EventSystem;

import BackEnd.UserSystem.Location;
import auth.AuthorizationException;
import auth.Permissions;

/**
 *
 * @author Ketty Lezama
 */

public class ScheduleItem {
    private String title, description;
    private Location location;
    private TimeSchedule timeSchedule;
    
    public ScheduleItem(){
        title = new String();
        description = new String();
        location = new Location();
        timeSchedule = new TimeSchedule();
    }
    
    public ScheduleItem(String title) {
        this.title = title;
        this.description = new String();
        location = new Location();
        timeSchedule = new TimeSchedule();
    }
    
    public ScheduleItem(ScheduleItem scheduleItem){
        Permissions.SystemTransaction ignored = Permissions.get().beginSystemTransaction();
        title = scheduleItem.getTitle();
        description = scheduleItem.getDescription();
        location = scheduleItem.getLocation();
        timeSchedule = scheduleItem.getTimeSchedule();
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setLocation(Location location){
        this.location = location;
    }
    
    public Location getLocation() {
        return location;
    }
    
    public void setTimeSchedule(TimeSchedule timeSchedule) {
        this.timeSchedule = timeSchedule;
    }
    
    public TimeSchedule getTimeSchedule() {
        return timeSchedule;
    }
    
    public boolean equals(ScheduleItem scheduleItem) {

        boolean ts = false;
        try{
            ts = this.getTimeSchedule().equals(scheduleItem.getTimeSchedule());
        }catch (AuthorizationException authEx){}
        if (this.getTitle().equalsIgnoreCase(scheduleItem.getTitle()) &&
                this.getDescription().equalsIgnoreCase(scheduleItem.getDescription())
                && this.getLocation().equals(scheduleItem.getLocation())
                && ts )
            return true;
        else
            return false;
    }
    
    public String toString() {
        return "Description: \n" + description + "\nLocation: \n" + location.toString() + 
                "\nTime Schedule: \n" + timeSchedule.toString();
    }
}
