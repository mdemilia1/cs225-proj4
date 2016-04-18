package BackEnd.EventSystem;

import BackEnd.UserSystem.User;
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
        super((ScheduleItem)task);
        TASK_ID = task_id;
        responsibleList = task.getResponsibleList();
        completed = task.getCompleted();
    }
    
    private void setTASK_ID(int task_id) {
        TASK_ID = task_id;
    }
    
    public int getTASK_ID() {
        return TASK_ID;
    }
    
    public void setResponsibleList(ArrayList<User> responsibleList) {
        this.responsibleList = responsibleList;
    }
    
    public ArrayList<User> getResponsibleList() {
        return responsibleList;
    }
    
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    
    public boolean getCompleted() {
        return completed;
    }
    
    public boolean equals(Task task) {
        if (this.getTASK_ID() == task.getTASK_ID() 
                && this.getResponsibleList().equals(task.getResponsibleList()) 
                && this.getCompleted() == task.getCompleted())
            return true;
        else
            return false;
    }

     public String toString() {
	 //return "Task Description: \n" + super.getDescription() + "\nTask Complete: " + completed;
	 return super.getTitle();
     }

    
     @Override
    public ArrayList<Object> getReport() {
        ArrayList<Object> report = new ArrayList<Object>();
        ArrayList<String> responsible = new ArrayList<String>();
        
        for(int i = 0; i < responsibleList.size(); i++) {
            responsible.add("" + responsibleList.get(i).getFirstName());
            responsible.add("" + responsibleList.get(i).getLastName());
            responsible.add("" + responsibleList.get(i).getEmailAddress());
            responsible.add("" + responsibleList.get(i).getAddress().getCity());
            responsible.add("" + responsibleList.get(i).getAddress().getCountry());
            responsible.add("" + responsibleList.get(i).getAddress().getState());
            responsible.add("" + responsibleList.get(i).getAddress().getStreet());
            responsible.add("" + responsibleList.get(i).getAddress().getZipCode());
            responsible.add("" + responsibleList.get(i).getPrivilegeLevel().name());
            responsible.add("" + responsibleList.get(i).getPhoneNumber());
            responsible.add("" + responsibleList.get(i).getUserId());
            

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
