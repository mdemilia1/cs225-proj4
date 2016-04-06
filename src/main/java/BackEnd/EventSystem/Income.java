/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd.EventSystem;

/**
 *
 * @author Shaunt
 */
public class Income extends BudgetItem {

    public Income(){
        super();
    }
    
    public Income(double value, String description) {
        super(value, description);
    }

    public Income(int incomeID, Income budgetItem) {
        super(incomeID, (BudgetItem) budgetItem);
    }

    public Income(int incomeID, double value, String description) {
        super(incomeID, value, description);
    }
}
