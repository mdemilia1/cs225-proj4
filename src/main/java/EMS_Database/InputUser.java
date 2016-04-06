package EMS_Database;

import BackEnd.UserSystem.Participant;
import BackEnd.UserSystem.User;

/**
 * An object class for use with implementing a new user into the database
 *
 * @author Mike Meding
 */
public class InputUser {

    private int uid;
    private int level;
    private String fname;
    private String lname;
    private String pwd;
    private String email;
    private String phone;
    private String street;
    private String city;
    private String state;
    private String zipcode;
    private String country;
    private int eventLevel;
    private int participant;
    
    /** 
     * User function creates a valid new user to create an insertion into the DB
     * 
     * @param uid The unique user identification number. Does not check
     * for duplicates upon creation.
     * @param level The created users privilege level as an Integer.
     * @param fname The String of the first name of the user
     * @param lname The String of the last name of the user
     * @param pwd The protected password of the user (should be hashed.) as a
     * String
     * @param email The email of the user as a String.
     * @param phone The phone number represented as a String 
     * @param street Personal info as a String default is null
     * @param city Personal info as a String default is null
     * @param state Personal info as a String default is null
     * @param zipcode Personal info as a String default is null
     * @param country Personal info as a String default is null
     * @param eventLevel Event access level must be declared
     * 
     */
    
    public InputUser(){        
        //if nothing is specified.
        this.level = 1;
        this.fname = "default firstname";
        this.lname = "default lastname";
        this.pwd = "password";
        this.email = "user@email.com";        
        this.phone = "8675309";
        this.street = "default streetname";
        this.city = "default city";
        this.state = "default state";
        this.zipcode = "AAAAAA";
        this.country = "default country";
        this.eventLevel = 1;
	this.participant = 0;
    }
    
    public InputUser(User user) {
        //from actual backend user class
        if(user.getAdminPrivilege()){
            this.level = 1;
        } else {
            this.level = 0;
        }        
        //Add firstname field here
        //Add lastname field here
        this.fname = user.getFirstName();
        this.lname = user.getLastName();
        this.pwd = user.getPassword();
                
        this.email = user.getEmailAddress();  
        this.phone = user.getPhoneNumber().toString();
        this.street = user.getAddress().getStreet();
        this.city = user.getAddress().getCity();
        this.state = user.getAddress().getState();
        this.zipcode = user.getAddress().getZipCode();
        this.country = user.getAddress().getCountry();
        if(user.getEventCreationPrivilege()){
            this.eventLevel = 1;
        } else {
            this.eventLevel = 0;
        }
	this.participant = 0;
                                
    }
    
    public InputUser(Participant user) {
        this.participant = 1;
        this.level = 0;
        this.fname = user.getFirstName();
        this.lname = user.getLastName();
        this.pwd = new String();
        this.email = user.getEmailAddress();  
        this.phone = user.getPhoneNumber().toString();
        this.street = user.getAddress().getStreet();
        this.city = user.getAddress().getCity();
        this.state = user.getAddress().getState();
        this.zipcode = user.getAddress().getZipCode();
        this.country = user.getAddress().getCountry();
        this.eventLevel = 0;
    }
    
    public InputUser(int uid, int level, String fname, String lname, String pwd, String email, String phone, String street, String city , String state, String zipcode, String country,
            int eventLevel, int participant) {
        //manual insertion
        this.uid = uid;
        this.level = level;
        this.fname = fname;
        this.lname = lname;
        this.pwd = pwd;
        this.email = email;
        
        this.phone = phone;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.country = country;
        this.eventLevel = eventLevel;
	this.participant = participant;
    }

    //GETTERS
    public int getUid() {
        return uid;
    }

    public int getLevel() {
        return level;
    }

    public String getFirstName() {        
        return fname;
    }

    public String getLastName() {
        return lname;
    }       

    public String getPwd() {
        return pwd;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
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

    public int getEventLevel() {
        return eventLevel;
    } 

    public int getParticipant() {
	return participant;
    }
        

    //SETTERS
    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setFirstName(String fname) {
        this.fname = fname;
    }

    public void setLastName(String lname) {
        this.lname = lname;
    }    
    
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public void setEventLevel(int eventLevel) {
        this.eventLevel = eventLevel;
    }

    public void setParticipant(int participant) {
	this.participant = participant;
    }        
    
    
}
