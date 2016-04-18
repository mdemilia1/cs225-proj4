package BackEnd.EventSystem;

import java.util.ArrayList;
import auth.AuthorizationException;
import auth.Operation;
import auth.Permissions;
import auth.PrivilegeLevel;

/**
 *
 * @author Shaunt
 */

public class Budget implements Reportable{

    ArrayList<Income> incomeList;
    ArrayList<Expense> expenseList;
    
    public Budget() {
        this.incomeList = new ArrayList<Income>();
        this.expenseList = new ArrayList<Expense>();
    }
    
    public Budget(Budget budget){
        try(Permissions.SystemTransaction ignored = Permissions.get().beginSystemTransaction()) {
            incomeList = budget.getIncomeList();
        }
        catch(AuthorizationException ignored){

        }
        try(Permissions.SystemTransaction ignored = Permissions.get().beginSystemTransaction()) {
            expenseList = budget.getExpenseList();
        }
        catch(AuthorizationException ignored){

        }

    }

    public void setIncomeList(ArrayList<Income> incomeList) throws AuthorizationException {
        this.incomeList = incomeList;
    }

    public void setExpenseList(ArrayList<Expense> expenseList) throws AuthorizationException {
        this.expenseList = expenseList;
    }

    public ArrayList<Income> getIncomeList() throws AuthorizationException {
        return incomeList;
    }

    public ArrayList<Expense> getExpenseList() throws AuthorizationException{
        return expenseList;
    }
    
    
    public double getTotalBudget() throws AuthorizationException {
        return getTotalIncome() - getTotalExpense();
    }
    
    public double getTotalIncome() throws AuthorizationException {
        double total = 0;
        for (int i = 0; i < incomeList.size(); i++) {
            total += incomeList.get(i).getValue();
        }
        return total;
    }

    public double getTotalExpense() throws AuthorizationException {

        double total = 0;
        for (int i = 0; i < expenseList.size(); i++) {
            total += expenseList.get(i).getValue();
        }
        return total;
    }
    
    @Override
    public String toString() {
        return "Budget{" + ", incomeList=" + incomeList + ", expenseList=" + 
                expenseList + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Budget other = (Budget) obj;
        if (this.incomeList != other.incomeList && (this.incomeList == null || !this.incomeList.equals(other.incomeList))) {
            return false;
        }
        if (this.expenseList != other.expenseList && (this.expenseList == null || !this.expenseList.equals(other.expenseList))) {
            return false;
        }
        return true;
    }
    
    @Override
    public ArrayList<Object> getReport() throws AuthorizationException {
        
        ArrayList<Object> report = new ArrayList<Object>();
        ArrayList<String> income = new ArrayList<String>();
        ArrayList<String> expense = new ArrayList<String>();
        
        for(int i = 0; i < incomeList.size(); i ++) {
            income.add("" + incomeList.get(i).getValue());
            income.add("" + incomeList.get(i).getDescription());
            income.add("" + incomeList.get(i).getDate().getTime());
        }
        for(int i = 0; i < expenseList.size(); i ++) {
            expense.add("" + expenseList.get(i).getValue());
            expense.add("" + expenseList.get(i).getDescription());
            expense.add("" + expenseList.get(i).getDate().getTime());
        }

        report.add(income);
        report.add(expense);
        report.add("" + this.getTotalBudget());
        report.add("" + this.getTotalExpense());
        report.add("" + this.getTotalIncome());
        
        return report;
    }
    
    
}
