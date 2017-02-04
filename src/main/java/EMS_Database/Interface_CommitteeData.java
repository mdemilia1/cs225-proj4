package EMS_Database;

import auth.AuthorizationException;
import exception.UpdateException;

import java.util.ArrayList;

/**
 * @author mike
 */
public interface Interface_CommitteeData {
    //SPECIAL FUNCTIONS       
    int createCommittee(InputCommittee committee) throws AuthorizationException, UpdateException;

    void removeCommittee(int uid) throws AuthorizationException, UpdateException, DoesNotExistException;

    double remainingMonies(int uid) throws AuthorizationException, DoesNotExistException;

    double totalIncome(int uid) throws AuthorizationException, DoesNotExistException;

    double totalExpense(int uid) throws AuthorizationException, DoesNotExistException;

    // GETTERS
    String getTitle(int uid) throws AuthorizationException, DoesNotExistException;

    int getChairman(int uid) throws AuthorizationException, DoesNotExistException;

    ArrayList<Integer> getBudgetAccessList(int uid) throws AuthorizationException, DoesNotExistException;

    ArrayList<Integer> getCommitteeMembers(int uid) throws AuthorizationException, DoesNotExistException;

    ArrayList<Integer> getTaskList(int uid) throws AuthorizationException, DoesNotExistException;

    ArrayList<Integer> getIncome(int uid) throws AuthorizationException, DoesNotExistException;

    ArrayList<Integer> getExpense(int uid) throws AuthorizationException, DoesNotExistException;

    double getBudget(int uid) throws AuthorizationException, DoesNotExistException;

    // SETTERS
    void setTitle(int uid, String title) throws AuthorizationException, UpdateException, DoesNotExistException;

    void setChairman(int uid, int nuid) throws AuthorizationException, UpdateException, DoesNotExistException;

    void setBudgetAccessList(int uid, ArrayList<Integer> accessList) throws AuthorizationException, UpdateException, DoesNotExistException;

    void setCommitteeMembers(int uid, ArrayList<Integer> memberList) throws AuthorizationException, UpdateException, DoesNotExistException;

    void setTaskList(int uid, ArrayList<Integer> taskList) throws AuthorizationException, UpdateException, DoesNotExistException;

    void setIncome(int uid, ArrayList<Integer> income) throws AuthorizationException, UpdateException, DoesNotExistException;

    void setExpense(int uid, ArrayList<Integer> expense) throws AuthorizationException, UpdateException, DoesNotExistException;

    void setBudget(int uid, double budget) throws AuthorizationException, UpdateException, DoesNotExistException;
}
