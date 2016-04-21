package BackEnd.EventSystem;

import BackEnd.UserSystem.User;
import auth.AuthorizationException;
import auth.Operation;
import auth.Permissions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ketty Lezama
 */

public class Committee implements Reportable {
    private int COMMITTEE_ID;
    private String title;
    private ArrayList<User> memberList, budgetAccessList;
    private User chair;
    private ArrayList<Task> taskList;
    private Budget budget;

    public Committee() {
        title = "";
        memberList = new ArrayList<>();
        budgetAccessList = new ArrayList<>();
        chair = new User();
        taskList = new ArrayList<>();
        budget = new Budget();
    }

    public Committee(String title) {
        this.title = title;
        memberList = new ArrayList<>();
        budgetAccessList = new ArrayList<>();
        chair = new User();
        taskList = new ArrayList<>();
        budget = new Budget();
    }

    public Committee(int committee_id, String title) {
        COMMITTEE_ID = committee_id;
        this.title = title;
        memberList = new ArrayList<>();
        budgetAccessList = new ArrayList<>();
        chair = new User();
        taskList = new ArrayList<>();
        budget = new Budget();
    }

    public Committee(int committee_id, Committee committee) {
        try(Permissions.SystemTransaction ignored = Permissions.get().beginSystemTransaction()) {
            this.COMMITTEE_ID = committee_id;
            title = committee.getTitle();
            memberList = committee.getMemberList();
            budgetAccessList = committee.getBudgetAccessList();
            chair = committee.getChair();
            taskList = committee.getTaskList();
            budget = committee.getBudget();
        }
        catch(AuthorizationException ignored){

        }
    }

    /**
     * Confirms if the committee has completed all of the tasks in taskList or not.
     *
     * @return True if the committee has finished all tasks, and false if it has not.
     */
    boolean isFinished() {
        boolean completed = true;

        try {
            for (Task task : getTaskList())
                if (!task.getCompleted())
                    completed = false;
        }catch (AuthorizationException authEx){}
        return completed;
    }

    int[] getTaskCompletion() {
        int completed = 0;
        int total = 0;
        try{
            total = getTaskList().size();
        }catch (AuthorizationException authEx){}

        try{
            for (Task aTaskList : getTaskList()) {
                if (aTaskList.getCompleted()) {
                    completed++;
                }
            }
        }catch (AuthorizationException authEx){}

        return new int[]{completed, total};
    }

    /**
     * Returns the committee ID.
     *
     * @return COMMITTEE_ID The committee ID.
     */
    public int getCOMMITTEE_ID() {
        Permissions.get().checkPermission("COMMITTEE","UID", Operation.VIEW);
        return COMMITTEE_ID;
    }

    /**
     * Sets the committee title to the given title.
     *
     * @param title The committee title.
     */
    public void setTitle(String title) {
        Permissions.get().checkPermission("COMMITTEE","TITLE", Operation.MODIFY);
        this.title = title;
    }

    /**
     * Returns the committee title.
     *
     * @return title The committee's title.
     */
    public String getTitle(){
        Permissions.get().checkPermission("COMMITTEE","TITLE", Operation.VIEW);
        return title;
    }

    /**
     * Stores a list of committee members.
     *
     * @param memberList The array list of UIDs for members
     */
    public void setMemberList(ArrayList<User> memberList) {
        Permissions.get().checkPermission("COMMITTEE","MEMBERS", Operation.MODIFY);
        this.memberList = memberList;
    }

    /**
     * Returns the committee member list.
     *
     * @return memberList
     */
    public ArrayList<User> getMemberList() {
        Permissions.get().checkPermission("COMMITTEE","MEMBERS", Operation.VIEW);
        return memberList;
    }

    public ArrayList<User> getMemberListWithChair(){
        Permissions.get().checkPermission("COMMITTEE","MEMBERS", Operation.VIEW);
        Permissions.get().checkPermission("COMMITTEE","CHAIR", Operation.VIEW);
        for (User member : memberList)
            if (member.equals(chair))
                return memberList;

        if (chair != null)
            memberList.add(chair);
        else
            throw new NullPointerException("Object chair is currently null. Unable to add to member list.");

        return memberList;
    }

    public void setBudgetAccessList(ArrayList<User> budgetAccessList) {
        Permissions.get().checkPermission("COMMITTEE","BUDGETACCESS", Operation.MODIFY);
        this.budgetAccessList = budgetAccessList;
    }

    public ArrayList<User> getBudgetAccessList() {
        Permissions.get().checkPermission("COMMITTEE","BUDGETACCESS", Operation.VIEW);
        return budgetAccessList;
    }

    public void setChair(User user) {
        Permissions.get().checkPermission("COMMITTEE","CHAIRMAN", Operation.MODIFY);
        chair = user;
    }

    public User getChair() {
        Permissions.get().checkPermission("COMMITTEE","CHAIRMAN", Operation.VIEW);
        return chair;
    }

    public int getCompletePercent() {
        Permissions.get().checkPermission("COMMITTEE","TASKS", Operation.VIEW);
        int pct;
        float complete = 0.0f;
        float total = taskList.size();
        for (Task t : taskList) {
            if (t.getCompleted()) {
                complete += 1;
            }
        }
        pct = (int) (complete / total * 100);
        return pct;
    }

    public void setTaskList(ArrayList<Task> taskList) throws AuthorizationException {
        Permissions.get().checkPermission("COMMITTEE","TASKS", Operation.MODIFY);
        this.taskList = taskList;
    }

    public ArrayList<Task> getTaskList() throws AuthorizationException {
        Permissions.get().checkPermission("COMMITTEE","TASKS", Operation.VIEW);
        return taskList;
    }

    public void setBudget(Budget budget) throws AuthorizationException {
        Permissions.get().checkPermission("COMMITTEE","BUDGET", Operation.MODIFY);
        this.budget = budget;
    }

    public Budget getBudget() throws AuthorizationException {
        Permissions.get().checkPermission("COMMITTEE","BUDGET", Operation.VIEW);
        return budget;
    }

    public boolean equals(Committee committee) throws AuthorizationException {
        return this.getCOMMITTEE_ID() == committee.getCOMMITTEE_ID()
                && this.getTitle().equalsIgnoreCase(committee.getTitle())
                && this.getMemberList().equals(committee.getMemberList())
                && this.getBudgetAccessList().equals(committee.getBudgetAccessList())
                && this.getChair().equals(committee.getChair())
                && this.getTaskList().equals(committee.getTaskList())
                && this.getBudget().equals(committee.getBudget());
    }

    public String toString() {
//        String taskDescriptions = "";
//        
//        for (Task task : taskList)
//            taskDescriptions += task.toString() + "\n";
//            
//        return "Committee Title: " + title + "\nTotal Budget: $" + budget.getTotalBudget() + "\nTask List: \n" + taskDescriptions;
        try {
            Permissions.get().checkPermission("COMMITTEE","TITLE", Operation.VIEW);
            return getTitle();
        }
        catch (AuthorizationException check){
            return "Access Not Granted";
        }
    }

    private void buildReportComponent(List<String> component, List<User> users) throws AuthorizationException {
        for (User user : users) {
            component.add("" + user.getFirstName());
            component.add("" + user.getLastName());
            component.add("" + user.getEmailAddress());
            component.add("" + user.getAddress().getCity());
            component.add("" + user.getAddress().getCountry());
            component.add("" + user.getAddress().getState());
            component.add("" + user.getAddress().getStreet());
            component.add("" + user.getAddress().getZipCode());
            component.add("" + user.getPrivilegeLevel());
            component.add("" + user.getPhoneNumber());
            component.add("" + user.getUserId());
        }
    }

    @Override
    public ArrayList<Object> getReport() throws AuthorizationException {
        ArrayList<Object> report = new ArrayList<>();
        List<String> member = new ArrayList<>();
        List<String> budgetAccess = new ArrayList<>();

        buildReportComponent(member, memberList);
        buildReportComponent(budgetAccess, budgetAccessList);

        report.add(budget);
        report.add(member);
        report.add("" + taskList.size());
        report.add("" + budget.getTotalBudget());
        report.add("" + budget.getTotalExpense());
        report.add("" + budget.getTotalIncome());
        report.add("" + this.getTitle());
        report.add("" + this.isFinished());


        return report;

    }
}