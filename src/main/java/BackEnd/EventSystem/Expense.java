/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd.EventSystem;

/**
 *
 * @author Shaunt
 */
public class Expense extends BudgetItem {
    
    public Expense(){
        super();
    }
    
    public Expense(double value, String description) {
        super(value, description);
    }
    
    public Expense(int expenseID, Expense budgetItem){
        super(expenseID, (BudgetItem)budgetItem);
    }
    
    public Expense(int expenseID, double value, String description){
        super(expenseID, value, description);
    }
}
