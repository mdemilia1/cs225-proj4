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

public class Budget implements Reportable {

    ArrayList<Income> incomeList;
    ArrayList<Expense> expenseList;

    public Budget() {
        this.incomeList = new ArrayList<Income>();
        this.expenseList = new ArrayList<Expense>();
    }

    public Budget(Budget budget) {
        incomeList = budget.getIncomeList();

        Permissions.SystemTransaction ignored = Permissions.get().beginSystemTransaction();
            expenseList = budget.getExpenseList();
    }

    public void setIncomeList(ArrayList<Income> incomeList) {
        this.incomeList = incomeList;
    }

    public void setExpenseList(ArrayList<Expense> expenseList) {
        this.expenseList = expenseList;
    }

    public ArrayList<Income> getIncomeList() {
        return incomeList;
    }

    public ArrayList<Expense> getExpenseList() {
        return expenseList;
    }


    public double getTotalBudget() {
        return getTotalIncome() - getTotalExpense();
    }

    public double getTotalIncome() {
        double total = 0;
        for (int i = 0; i < incomeList.size(); i++) {
            try{
                total += incomeList.get(i).getValue();
            }catch (AuthorizationException authEx){}
        }
        return total;
    }

    public double getTotalExpense() {

        double total = 0;
        for (int i = 0; i < expenseList.size(); i++) {
            try{
                total += expenseList.get(i).getValue();
            }catch(AuthorizationException authEx){}
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
    public ArrayList<Object> getReport() {

        ArrayList<Object> report = new ArrayList<Object>();
        ArrayList<String> income = new ArrayList<String>();
        ArrayList<String> expense = new ArrayList<String>();

        for (int i = 0; i < incomeList.size(); i++) {
            try {
                income.add("" + incomeList.get(i).getValue());
                income.add("" + incomeList.get(i).getDescription());
                income.add("" + incomeList.get(i).getDate().getTime());
            } catch (AuthorizationException authEx) {
            }
        }
        for (int i = 0; i < expenseList.size(); i++) {
            try {
                expense.add("" + expenseList.get(i).getValue());
                expense.add("" + expenseList.get(i).getDescription());
                expense.add("" + expenseList.get(i).getDate().getTime());
            } catch (AuthorizationException authEx) {
            }
        }

        report.add(income);
        report.add(expense);
        report.add("" + this.getTotalBudget());
        report.add("" + this.getTotalExpense());
        report.add("" + this.getTotalIncome());

        return report;
    }
}