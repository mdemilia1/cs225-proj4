package EMS_Database;

import java.util.ArrayList;

/**
 *
 * @author mike
 */
public interface Interface_CommitteeData {

    //SPECIAL FUNCTIONS       
    public int createCommittee(InputCommittee committee);

    public int nextValidUID();

    public String queryEntireTable();
    
    public void removeCommittee(int uid) throws DoesNotExistException;
    
    public double remainingMonies(int uid) throws DoesNotExistException;
    
    public double totalIncome(int uid) throws DoesNotExistException;
    
    public double totalExpense(int uid) throws DoesNotExistException;

    // GETTERS
    public String getTitle(int uid) throws DoesNotExistException;

    public int getChairman(int uid) throws DoesNotExistException;

    public ArrayList<Integer> getBudgetAccessList(int uid) throws DoesNotExistException;

    public ArrayList<Integer> getCommitteeMembers(int uid) throws DoesNotExistException;

    public ArrayList<Integer> getTaskList(int uid) throws DoesNotExistException;
    
    public ArrayList<Integer> getIncome(int uid) throws DoesNotExistException;
    
    public ArrayList<Integer> getExpense(int uid) throws DoesNotExistException;

    public double getBudget(int uid) throws DoesNotExistException;

    // SETTERS
    public void setTitle(int uid, String title) throws DoesNotExistException;

    public void setChairman(int uid, int nuid) throws DoesNotExistException;

    public void setBudgetAccessList(int uid, ArrayList<Integer> accessList) throws DoesNotExistException;

    public void setCommitteeMembers(int uid, ArrayList<Integer> memberList) throws DoesNotExistException;

    public void setTaskList(int uid, ArrayList<Integer> taskList) throws DoesNotExistException;
    
    public void setIncome(int uid, ArrayList<Integer> income) throws DoesNotExistException;
    
    public void setExpense(int uid, ArrayList<Integer> expense) throws DoesNotExistException;

    public void setBudget(int uid, double budget) throws DoesNotExistException;
}
