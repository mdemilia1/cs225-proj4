package BackEnd.EventSystem;

import BackEnd.UserSystem.User;
import BackEnd.UserSystem.Participant;
import auth.AuthorizationException;
import auth.Permissions;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 *
 * @author Ketty Lezama
 */

public class Event extends ScheduleItem implements Reportable {
    private int EVENT_ID;
    private ArrayList<User> organizerList;
    private ArrayList<SubEvent> subEventList;
    private ArrayList<Committee> committeeList;
    private ArrayList<Participant> participantList;
    
    public Event(){
        super();
        organizerList = new ArrayList<User>();
        subEventList = new ArrayList<SubEvent>();
        committeeList = new ArrayList<Committee>();
        participantList = new ArrayList<Participant>();
    }
    
    public Event(int event_id, String title) {
        super(title);
        EVENT_ID = event_id;
        organizerList = new ArrayList<User>();
        subEventList = new ArrayList<SubEvent>();
        committeeList = new ArrayList<Committee>();
        participantList = new ArrayList<Participant>();
    }
    
    public Event(int eventID, Event event){
        super((ScheduleItem)event);
        EVENT_ID = eventID;
        try(Permissions.SystemTransaction ignored = Permissions.get().beginSystemTransaction()) {
            organizerList = event.getOrganizerList();
            subEventList = event.getSubEventList();
            committeeList = event.getCommitteeList();
            participantList = event.getParticipantList();
        }
        catch(AuthorizationException ignored){

        }
    }
    
    public boolean isReady() throws AuthorizationException{
        boolean eventReady = true;
        
        for (Committee committee : committeeList)
            if (committee.isFinished() == false)
                eventReady = false;
        
        return eventReady;
    }
    
    private void setEVENT_ID(int event_id) {
        EVENT_ID = event_id;
    }
    
    public int getEVENT_ID() throws AuthorizationException {
        return EVENT_ID;
    }
    
    public void setOrganizerList(ArrayList<User> organizerList) throws AuthorizationException {
        this.organizerList = organizerList;
    }
    
    public ArrayList<User> getOrganizerList() throws AuthorizationException {
        return organizerList;
    }
    
    public void setSubEventList(ArrayList<SubEvent> subEventList) throws AuthorizationException {
        this.subEventList = subEventList;
    }
    
    public ArrayList<SubEvent> getSubEventList() throws AuthorizationException {
        return subEventList;
    }
    
    public void setCommitteeList(ArrayList<Committee> committeeList) throws AuthorizationException {
        this.committeeList = committeeList;
    }
    
    public ArrayList<Committee> getCommitteeList() throws AuthorizationException {
        return committeeList;
    }
    
    public void setParticipantList(ArrayList<Participant> participantList) throws AuthorizationException {
        this.participantList = participantList;
    }
    
    public ArrayList<Participant> getParticipantList() throws AuthorizationException {
        return participantList;
    }
    
    public double getTotalEventBudget() throws AuthorizationException {
        return getTotalEventIncome() - getTotalEventExpense();
    }
    
    public double getTotalEventIncome() throws AuthorizationException{
        double income = 0;
        for (int i = 0; i < committeeList.size(); i++){
            income += committeeList.get(i).getBudget().getTotalIncome();
        }
        return income;
    }
    
    public double getTotalEventExpense() throws AuthorizationException{
        double expense = 0;
        for (int i = 0; i < committeeList.size(); i++){
            expense += committeeList.get(i).getBudget().getTotalExpense();
        }
        return expense;
    }
    
    public int getTotalTaskProgress() throws AuthorizationException{
        double completed = 0;
        double total = 0;
        int[] taskCompletion = new int[2];
        for (int i = 0; i < committeeList.size(); i++){
            taskCompletion = committeeList.get(i).getTaskCompletion();
            completed += taskCompletion[0];
            total += taskCompletion[1];
        }
        return (int)(100 * completed / total);
    }
    
    public boolean equals(Event event) throws AuthorizationException {
        if (this.getEVENT_ID() == event.getEVENT_ID()
                && this.getOrganizerList().equals(event.getOrganizerList())
                && this.getSubEventList().equals(event.getSubEventList())
                && this.getCommitteeList().equals(event.getCommitteeList())
                && this.getParticipantList().equals(event.getParticipantList()))
            return true;
        else
            return false;
    }
    
    public String toString() {
        try(Permissions.SystemTransaction ignored = Permissions.get().beginSystemTransaction()) {
            return "Event Title: " + getTitle() +
                    "Event Description: \n" + super.getDescription() + "\n\n" + super.getLocation().toString() +
                    "\n\n" + super.getTimeSchedule().toString();
        }
        catch(AuthorizationException ignored){}
        return null;
    }
    
    @Override
    public ArrayList<Object> getReport() throws AuthorizationException{
        ArrayList<Object> report = new ArrayList<Object>();
        ArrayList<String> organizer = new ArrayList<String>();
        ArrayList<String> subEvent = new ArrayList<String>();
        ArrayList<String> committee = new ArrayList<String>();
        ArrayList<String> participant = new ArrayList<String>();


        int numOfOrganizers = 0;

        for (int i = 0; i < organizerList.size(); i++) {
            numOfOrganizers++;
            if (i == organizerList.size() - 1) {
                organizer.add("" + numOfOrganizers);
            }

        }
        for (int i = 0; i < subEventList.size(); i++) {
            subEvent.add("" + subEventList.get(i).getTitle());
            subEvent.add("" + subEventList.get(i).getDescription());
            subEvent.add("" + subEventList.get(i).getLocation());
            subEvent.add("" + subEventList.get(i).getTimeSchedule().getStartDateTimeCalendar());
            subEvent.add("" + subEventList.get(i).getTimeSchedule().getEndDateTimeCalendar());
        }
        int numOfMembers = 0;
        int numOfTasks = 0;
        for (int i = 0; i < committeeList.size(); i++) {
            committee.add("" + committeeList.get(i).getTitle());
            committee.add("" + committeeList.get(i).getBudget().getTotalIncome());
            committee.add("" + committeeList.get(i).getBudget().getTotalExpense());
            committee.add("" + committeeList.get(i).getBudget().getTotalIncome());
            committee.add("" + committeeList.get(i).getChair().getFirstName());
            committee.add("" + committeeList.get(i).getChair().getLastName());
            committee.add("" + committeeList.get(i).getChair().getEmailAddress());
            committee.add("" + committeeList.get(i).getChair().getAddress().getCity());
            committee.add("" + committeeList.get(i).getChair().getAddress().getCountry());
            committee.add("" + committeeList.get(i).getChair().getAddress().getState());
            committee.add("" + committeeList.get(i).getChair().getAddress().getStreet());
            committee.add("" + committeeList.get(i).getChair().getAddress().getZipCode());
            //committee.add("" + committeeList.get(i).getChair().getAdminPrivilege());
            //committee.add("" + committeeList.get(i).getChair().getEventCreationPrivilege());
            committee.add("" + committeeList.get(i).getChair().getPhoneNumber());
            committee.add("" + committeeList.get(i).getChair().getUserId());


            for (int j = 0; j < committeeList.get(i).getMemberList().size(); j++) {
                numOfMembers++;
                if (j == committeeList.get(i).getMemberList().size() - 1) {
                    committee.add("" + numOfMembers);
                }
            }
            committee.add("" + committeeList.get(i).isFinished());
            
            for (int j = 0; j < committeeList.get(i).getTaskList().size(); j++) {
                numOfTasks++;
                if (j == committeeList.get(i).getTaskList().size() - 1) {
                    committee.add("" + numOfTasks);
                }
            }
            committee.add("" + committeeList.get(i).isFinished());

        }
        int numOfParticipants = 0;
        for (int i = 0; i < participantList.size(); i++) {
            numOfParticipants++;
            if (i == participantList.size() - 1) {
                participant.add("" + numOfParticipants);
            }
        }


        report.add(organizer);
        report.add(subEvent);
        report.add(committee);
        report.add(participant);
        report.add("" + this.getTitle());
        report.add("" + this.getLocation());
        report.add("" + this.getDescription());
        report.add("" + this.getTimeSchedule().getStartDateTimeCalendar().getTime().getDay());
        report.add("" + this.getTimeSchedule().getEndDateTimeCalendar().getTime().getDay());

        return report;
    }
}
