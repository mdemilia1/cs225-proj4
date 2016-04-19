package BackEnd.EventSystem;

import BackEnd.UserSystem.Location;
import BackEnd.UserSystem.User;
import auth.AuthorizationException;
import auth.Operation;
import auth.Permissions;

import java.util.ArrayList;

/**
 *
 * @author Ketty Lezama 
 */

public class Task extends ScheduleItem implements Reportable {
    private int TASK_ID;
    private ArrayList<User> responsibleList;
    private boolean completed;
    
    public Task(){
        responsibleList = new ArrayList<User>();
    }
    
    public Task(int task_id, String title){
        super(title);
        TASK_ID = task_id;
        responsibleList = new ArrayList<User>();
        completed = false;
    }
    
    public Task(int task_id, Task task){
        super(task);
        TASK_ID = task_id;
        try(Permissions.SystemTransaction ignored = Permissions.get().beginSystemTransaction()) {
            responsibleList = task.getResponsibleList();
            completed = task.getCompleted();
        }
        catch (AuthorizationException ignored){}
    }

    
    public int getTASK_ID() throws AuthorizationException {
        Permissions.get().checkPermission("TASKS","UID", Operation.VIEW);
        return TASK_ID;
    }
    
    public void setResponsibleList(ArrayList<User> responsibleList) throws AuthorizationException {
        Permissions.get().checkPermission("TASKS","MANAGER", Operation.MODIFY);
        this.responsibleList = responsibleList;
    }
    
    public ArrayList<User> getResponsibleList() throws AuthorizationException {
        Permissions.get().checkPermission("TASKS","MANAGER", Operation.VIEW);
        return responsibleList;
    }
    
    public void setCompleted(boolean completed) throws AuthorizationException {
        Permissions.get().checkPermission("TASKS","COMPLETE", Operation.MODIFY);
        this.completed = completed;
    }
    
    public boolean getCompleted() throws AuthorizationException {
        Permissions.get().checkPermission("TASKS","COMPLETE", Operation.VIEW);
        return completed;
    }

    public void setTitle(String title) throws AuthorizationException {
        Permissions.get().checkPermission("TASKS","TITLE", Operation.MODIFY);
        super.setTitle(title);
    }

    public String getTitle() throws AuthorizationException {
        Permissions.get().checkPermission("TASKS","TITLE", Operation.VIEW);
        return super.getTitle();
    }

    public void setDescription(String description) throws AuthorizationException {
        Permissions.get().checkPermission("TASKS","DESCRIPTION", Operation.MODIFY);
        super.setDescription(description);
    }

    public String getDescription() throws AuthorizationException {
        Permissions.get().checkPermission("TASKS","DESCRIPTION", Operation.VIEW);
        return super.getDescription();
    }

    public void setLocation(Location location) throws AuthorizationException {
        //Should only have to check one Field type of "Location" right?
        Permissions.get().checkPermission("TASKS","STREET", Operation.MODIFY);
        super.setLocation(location);
    }

    public Location getLocation() throws AuthorizationException {
        //See setLocation
        Permissions.get().checkPermission("TASKS","STREET", Operation.VIEW);
        return super.getLocation();
    }

    public void setTimeSchedule(TimeSchedule timeSchedule) throws AuthorizationException {
        super.setTimeSchedule(timeSchedule);
    }

    public TimeSchedule getTimeSchedule() throws AuthorizationException {
        return super.getTimeSchedule();
    }


    public boolean equals(Task task) throws AuthorizationException {
        if (this.getTASK_ID() == task.getTASK_ID() 
                && this.getResponsibleList().equals(task.getResponsibleList()) 
                && this.getCompleted() == task.getCompleted())
            return true;
        else
            return false;
    }

     public String toString() {
	 //return "Task Description: \n" + super.getDescription() + "\nTask Complete: " + completed;
     try {
         return getTitle();
     }
         catch(AuthorizationException check){ return "Unauthorized Access"; }

     }

    
     @Override
    public ArrayList<Object> getReport() throws AuthorizationException {
        ArrayList<Object> report = new ArrayList<Object>();
        ArrayList<String> responsible = new ArrayList<String>();
        
        for(int i = 0; i < getResponsibleList().size(); i++) {
            responsible.add("" + getResponsibleList().get(i).getFirstName());
            responsible.add("" + getResponsibleList().get(i).getLastName());
            responsible.add("" + getResponsibleList().get(i).getEmailAddress());
            responsible.add("" + getResponsibleList().get(i).getAddress().getCity());
            responsible.add("" + getResponsibleList().get(i).getAddress().getCountry());
            responsible.add("" + getResponsibleList().get(i).getAddress().getState());
            responsible.add("" + getResponsibleList().get(i).getAddress().getStreet());
            responsible.add("" + getResponsibleList().get(i).getAddress().getZipCode());
            responsible.add("" + getResponsibleList().get(i).getPrivilegeLevel().name());
            responsible.add("" + getResponsibleList().get(i).getPhoneNumber());
            responsible.add("" + getResponsibleList().get(i).getUserId());
            

        }
        
        report.add(responsible);
        report.add("" + this.getCompleted());
        report.add("" + this.getDescription());
        report.add("" + this.getTitle());
        report.add("" + this.getLocation());
        report.add("" + this.getTimeSchedule().getEndDateTimeCalendar());
        report.add("" + this.getTimeSchedule().getStartDateTimeCalendar());
        
        return report;
    }
}
