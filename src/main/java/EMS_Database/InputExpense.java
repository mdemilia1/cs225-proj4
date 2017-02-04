package EMS_Database;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author mike
 */
public class InputExpense {

    private String description;
    private Timestamp time;
    private double value;

    public InputExpense(){
	Timestamp time = new Timestamp(new Date().getTime());
	this.description = null;
	this.time = time;
	this.value = 0.0;
    }
    
    public InputExpense(String description, Timestamp time, double value) {
	this.description = description;
	this.time = time;
	this.value = value;
    }

    public String getDescription() {
	return description;
    }

    public Timestamp getTime() {
	return time;
    }

    public double getValue() {
	return value;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public void setTime(Timestamp time) {
	this.time = time;
    }

    public void setValue(double value) {
	this.value = value;
    }
                            
}
