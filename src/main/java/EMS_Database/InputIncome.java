package EMS_Database;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author mike
 */
public class InputIncome {

    private String description;
    private Timestamp date;
    private double value;

    public InputIncome(){
	Timestamp time = new Timestamp(new Date().getTime());
	this.description = null;
	this.date = time;
	this.value = 0.0;
    }
    public InputIncome(String description, Timestamp date, double value) {
	this.description = description;
	this.date = date;
	this.value = value;
    }

    public String getDescription() {
	return description;
    }

    public Timestamp getDate() {
	return date;
    }

    public double getValue() {
	return value;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public void setDate(Timestamp date) {
	this.date = date;
    }

    public void setValue(double value) {
	this.value = value;
    }

    
    
}
