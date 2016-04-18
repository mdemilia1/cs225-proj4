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
        try(Permissions.SystemTransaction ignored = Permissions.get().beginSystemTransaction()) {
            title = scheduleItem.getTitle();
            description = scheduleItem.getDescription();
            location = scheduleItem.getLocation();
            timeSchedule = scheduleItem.getTimeSchedule();
        }
        catch(AuthorizationException ignored){

        }
    }
    
    public void setTitle(String title) throws AuthorizationException {
        this.title = title;
    }
    
    public String getTitle() throws AuthorizationException {
        return title;
    }
    
    public void setDescription(String description) throws AuthorizationException {
        this.description = description;
    }
    
    public String getDescription() throws AuthorizationException {
        return description;
    }
    
    public void setLocation(Location location) throws AuthorizationException {
        this.location = location;
    }
    
    public Location getLocation() throws AuthorizationException {
        return location;
    }
    
    public void setTimeSchedule(TimeSchedule timeSchedule) throws AuthorizationException {
        this.timeSchedule = timeSchedule;
    }
    
    public TimeSchedule getTimeSchedule() throws AuthorizationException {
        return timeSchedule;
    }
    
    public boolean equals(ScheduleItem scheduleItem) throws AuthorizationException {
        if (this.getTitle().equalsIgnoreCase(scheduleItem.getTitle()) &&
                this.getDescription().equalsIgnoreCase(scheduleItem.getDescription()) 
                && this.getLocation().equals(scheduleItem.getLocation())
                && this.getTimeSchedule().equals(scheduleItem.getTimeSchedule()))
            return true;
        else
            return false;
    }
    
    public String toString() {
        return "Description: \n" + description + "\nLocation: \n" + location.toString() + 
                "\nTime Schedule: \n" + timeSchedule.toString();
    }
}
