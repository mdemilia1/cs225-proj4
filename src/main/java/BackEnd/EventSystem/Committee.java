package BackEnd.EventSystem;

import BackEnd.UserSystem.User;

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
        this.COMMITTEE_ID = committee_id;
        title = committee.getTitle();
        memberList = committee.getMemberList();
        budgetAccessList = committee.getBudgetAccessList();
        chair = committee.getChair();
        taskList = committee.getTaskList();
        budget = committee.getBudget();
    }

    /**
     * Confirms if the committee has completed all of the tasks in taskList or not.
     *
     * @return True if the committee has finished all tasks, and false if it has not.
     */
    boolean isFinished() {
        boolean completed = true;

        for (Task task : taskList)
            if (!task.getCompleted())
                completed = false;

        return completed;
    }

    int[] getTaskCompletion() {
        int completed = 0;
        int total = taskList.size();
        for (Task aTaskList : taskList) {
            if (aTaskList.getCompleted()) {
                completed++;
            }
        }
        return new int[]{completed, total};
    }

    /**
     * Returns the committee ID.
     *
     * @return COMMITTEE_ID The committee ID.
     */
    public int getCOMMITTEE_ID() {
        return COMMITTEE_ID;
    }

    /**
     * Sets the committee title to the given title.
     *
     * @param title The committee title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the committee title.
     *
     * @return title The committee's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Stores a list of committee members.
     *
     * @param memberList
     */
    public void setMemberList(ArrayList<User> memberList) {
        this.memberList = memberList;
    }

    /**
     * Returns the committee member list.
     *
     * @return memberList
     */
    public ArrayList<User> getMemberList() {
        return memberList;
    }

    public ArrayList<User> getMemberListWithChair() throws NullPointerException {
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
        this.budgetAccessList = budgetAccessList;
    }

    public ArrayList<User> getBudgetAccessList() {
        return budgetAccessList;
    }

    public void setChair(User user) {
        chair = user;
    }

    public User getChair() {
        return chair;
    }

    public int getCompletePercent() {
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

    public void setTaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public Budget getBudget() {
        return budget;
    }

    public boolean equals(Committee committee) {
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
        return title;
    }

    private void buildReportComponent(List<String> component, List<User> users) {
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
    public ArrayList<Object> getReport() {
        ArrayList<Object> report = new ArrayList<>();
        List<String> member = new ArrayList<>();
        List<String> budgetAccess = new ArrayList<>();

        buildReportComponent(member, memberList);
        buildReportComponent(budgetAccess, budgetAccessList);

        /*
        for(int i = 0; i < taskList.size();i++) {
            task.add("" + taskList.get(i).getDescription());
            task.add("" + taskList.get(i).getTitle());
            task.add("" + taskList.get(i).getCompleted());
            task.add("" + taskList.get(i).getLocation().getCity());
            task.add("" + taskList.get(i).getLocation().getCountry());
            task.add("" + taskList.get(i).getLocation().getState());
            task.add("" + taskList.get(i).getLocation().getStreet());
            task.add("" + taskList.get(i).getLocation().getZipCode());
            task.add("" + taskList.get(i).getLocation().getDetails());
            task.add("" + taskList.get(i).getTimeSchedule().getEndDateTimeCalendar());
            task.add("" + taskList.get(i).getTimeSchedule().getStartDateTimeCalendar());
            for(int j = 0; j < taskList.get(i).getResponsibleList().size();j++) {
                task.add("" + taskList.get(i).getResponsibleList().get(j).getEmailAddress());
                task.add("" + taskList.get(i).getResponsibleList().get(j).getFirstName());
                task.add("" + taskList.get(i).getResponsibleList().get(j).getLastName());
                task.add("" + taskList.get(i).getResponsibleList().get(j).getAddress().getCity());
            }
        }
        */

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