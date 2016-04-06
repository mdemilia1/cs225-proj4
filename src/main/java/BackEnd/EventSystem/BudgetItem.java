/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd.EventSystem;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Shaunt
 */
public class BudgetItem {
    private int BUDGET_ITEM_ID;
    private double value;
    private String description;
    private Timestamp date;
    
    public BudgetItem(){
        description = new String();
        date = new Timestamp(0);
    }
    
    public BudgetItem(double value, String description) {
        setValue(value);
        this.description = description;
        date = new Timestamp(0);
    }
    
    public BudgetItem(int budgetItemID, double value, String description) {
        BUDGET_ITEM_ID = budgetItemID;
        setValue(value);
        this.description = description;
        date = new Timestamp(0);
    }
    
    public BudgetItem(int budgetItemID, BudgetItem budgetItem){
        this.BUDGET_ITEM_ID = budgetItemID;
        setValue(budgetItem.getValue());
        this.description = budgetItem.getDescription();
        this.date = budgetItem.getDate();
    }
    

    public void setValue(double value) {
        this.value = Math.abs(value);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
    
    public void setDate(int year, int month, int day, int hour, int minute) throws IllegalArgumentException {
        Calendar calendar = new GregorianCalendar();
        if (year >= 2013 && year <= 9999)
            calendar.set(Calendar.YEAR, year);
        else
            throw new IllegalArgumentException("Invalid year entered.");
        
        if (month >= 0 && month < 12)
            calendar.set(Calendar.MONTH, month);
        else
            throw new IllegalArgumentException("Invalid numerical month entered.");
        
        if (day > 0 && day <= calendar.getMaximum(Calendar.DAY_OF_MONTH))
            calendar.set(Calendar.DAY_OF_MONTH, day);
        else
            throw new IllegalArgumentException("Invalid numerical day entered.");
        
        if (hour >= 0 && hour <= 24)
            calendar.set(Calendar.HOUR_OF_DAY, hour);
        else
            throw new IllegalArgumentException("Invalid hour entered.");
        
        if (minute >= 0 && minute < 60)
            calendar.set(Calendar.MINUTE, minute);
        else
            throw new IllegalArgumentException("Invalid minute entered.");
        date = new Timestamp(calendar.getTimeInMillis());
    }
    
    public int getBUDGET_ITEM_ID(){
        return BUDGET_ITEM_ID;
    }
    
    public double getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getDate() {
        return date;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BudgetItem other = (BudgetItem) obj;
        if (this.value != other.value) {
            return false;
        }
        if ((this.description == null) ? (other.description != null) : !this.description.equals(other.description)) {
            return false;
        }
        if (this.date != other.date && (this.date == null || !this.date.equals(other.date))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BudgetItem{" + "value=" + value + ", description=" + description + ", date=" + date + '}';
    }
}