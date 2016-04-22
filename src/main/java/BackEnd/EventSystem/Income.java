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
public class Income extends BudgetItem {

    public Income(){
        super();
    }
    
    public Income(double value, String description) {
        super(value, description);
    }

    public Income(int incomeID, Income budgetItem) {
        super(incomeID, budgetItem);
    }

    public Income(int incomeID, double value, String description) {
        super(incomeID, value, description);
    }

    //GETTERS
    public int getBUDGET_ITEM_ID() {
        Permissions.get().checkPermission("INCOME","UID", Operation.VIEW);
        return super.getBUDGET_ITEM_ID();
    }

    public double getValue(){
        Permissions.get().checkPermission("INCOME","VALUE", Operation.VIEW);
        return super.getValue();
    }

    public String getDescription() {
        Permissions.get().checkPermission("INCOME","DESCRIPTION", Operation.VIEW);
        return super.getDescription();
    }

    public Timestamp getDate() {
        Permissions.get().checkPermission("INCOME","DATE", Operation.VIEW);
        return super.getDate();
    }

    //SETTERS
    public void setValue(double value) {
        Permissions.get().checkPermission("INCOME","VALUE", Operation.MODIFY);
        super.setValue(value);
    }


    public void setDescription(String description){
        Permissions.get().checkPermission("INCOME","DESCRIPTION", Operation.MODIFY);
        super.setDescription(description);
    }

    public void setDate(Timestamp date){
        Permissions.get().checkPermission("INCOME","DATE", Operation.MODIFY);
        super.setDate(date);
    }

    public void setDate(int year, int month, int day, int hour, int minute){
        Permissions.get().checkPermission("INCOME","DATE", Operation.MODIFY);
        super.setDate(year,month,day,hour,minute);
    }
}
