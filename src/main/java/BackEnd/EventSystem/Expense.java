/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd.EventSystem;

import auth.AuthorizationException;
import auth.Operation;
import auth.Permissions;

import java.sql.Timestamp;

/**
 *
 * @author Dave
 */
public class Expense extends BudgetItem {
    
    public Expense(){
        super();
    }
    
    public Expense(double value, String description) {
        super(value, description);
    }
    
    public Expense(int expenseID, Expense budgetItem){
        super(expenseID, budgetItem);
    }
    
    public Expense(int expenseID, double value, String description){
        super(expenseID, value, description);
    }

    //GETTERS
    public int getBUDGET_ITEM_ID() {
        Permissions.get().checkPermission("EXPENSE","UID", Operation.VIEW);
        return super.getBUDGET_ITEM_ID();
    }

    public double getValue() {
        Permissions.get().checkPermission("EXPENSE","VALUE", Operation.VIEW);
        return super.getValue();
    }

    public String getDescription() {
        Permissions.get().checkPermission("EXPENSE","DESCRIPTION", Operation.VIEW);
        return super.getDescription();
    }

    public Timestamp getDate() {
        Permissions.get().checkPermission("EXPENSE","DATE", Operation.VIEW);
        return super.getDate();
    }

    //SETTERS
    public void setValue(double value) {
        Permissions.get().checkPermission("EXPENSE","VALUE", Operation.MODIFY);
        super.setValue(value);
    }


    public void setDescription(String description) {
        Permissions.get().checkPermission("EXPENSE","DESCRIPTION", Operation.MODIFY);
        super.setDescription(description);
    }

    public void setDate(Timestamp date) {
        Permissions.get().checkPermission("EXPENSE","DATE", Operation.MODIFY);
        super.setDate(date);
    }

    public void setDate(int year, int month, int day, int hour, int minute) {
        Permissions.get().checkPermission("EXPENSE","DATE", Operation.MODIFY);
        super.setDate(year,month,day,hour,minute);
    }
}
