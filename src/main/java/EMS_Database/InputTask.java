package EMS_Database;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class is designed to gather all the data needed for the creation of a
 * task to be added to the task table.
 *
 * @author mike
 */
public class InputTask {

    private String descripton;
    private String details;
    private String title;
    private String street;
    private String city;
    private String state;
    private String zipcode;
    private String country;    
    private Timestamp startDate;
    private Timestamp endDate;
    private int complete;
    private ArrayList<Integer> manager;
    
    public InputTask(){
	ArrayList<Integer> list = new ArrayList<Integer>(); //list with no values
	Timestamp time = new Timestamp(new Date().getTime()); //gets current time.
	this.descripton = null;
	this.details = null;
	this.title = null;
	this.street = null;
	this.city = null;
	this.state = null;
	this.zipcode = null;
	this.country = null;
	this.startDate = time;
	this.endDate = time;
	this.complete = 0;
	this.manager = list;
    }

    public InputTask(String descripton, String details, String title, String street, String city, String state, String zipcode, String country, Timestamp startDate, Timestamp endDate, int complete, ArrayList<Integer> manager) {
	this.descripton = descripton;
	this.details = details;
	this.title = title;
	this.street = street;
	this.city = city;
	this.state = state;
	this.zipcode = zipcode;
	this.country = country;
	this.startDate = startDate;
	this.endDate = endDate;
	this.complete = complete;
	this.manager = manager;
    }

    public String getDescripton() {
	return descripton;
    }

    public String getDetails() {
	return details;
    }

    public String getTitle() {
	return title;
    }

    public String getStreet() {
	return street;
    }

    public String getCity() {
	return city;
    }

    public String getState() {
	return state;
    }

    public String getZipcode() {
	return zipcode;
    }

    public String getCountry() {
	return country;
    }

    public Timestamp getStartDate() {
	return startDate;
    }

    public Timestamp getEndDate() {
	return endDate;
    }

    public int getComplete() {
	return complete;
    }

    public ArrayList<Integer> getManager() {
	return manager;
    }

    public void setDescripton(String descripton) {
	this.descripton = descripton;
    }

    public void setDetails(String details) {
	this.details = details;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public void setStreet(String street) {
	this.street = street;
    }

    public void setCity(String city) {
	this.city = city;
    }

    public void setState(String state) {
	this.state = state;
    }

    public void setZipcode(String zipcode) {
	this.zipcode = zipcode;
    }

    public void setCountry(String country) {
	this.country = country;
    }

    public void setStartDate(Timestamp startDate) {
	this.startDate = startDate;
    }

    public void setEndDate(Timestamp endDate) {
	this.endDate = endDate;
    }

    public void setComplete(int complete) {
	this.complete = complete;
    }

    public void setManager(ArrayList<Integer> manager) {
	this.manager = manager;
    }

    
                
}
