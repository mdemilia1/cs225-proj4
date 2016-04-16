package EMS_Database;

import BackEnd.UserSystem.Participant;
import BackEnd.UserSystem.User;
import auth.PrivilegeLevel;

/**
 * An object class for use with implementing a new user into the database
 *
 * @author Mike Meding
 */
public class InputUser {

    private int uid;
    private PrivilegeLevel privilegeLevel;
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
    
    /*
     * User function creates a valid new user to create an insertion into the DB
     * 
     * uid The unique user identification number. Does not check for duplicates upon creation.
     * level PrivilegeLevel.id()
     * fname The String of the first name of the user
     * lname The String of the last name of the user
     * pwd The protected password of the user (should be hashed.) as a String
     * email The email of the user as a String.
     * phone The phone number represented as a String
     * street Personal info as a String default is null
     * city Personal info as a String default is null
     * state Personal info as a String default is null
     * zipcode Personal info as a String default is null
     * country Personal info as a String default is null
     * eventLevel No longer used
     * 
     */

    public InputUser() {
        //if nothing is specified.
        this.privilegeLevel = PrivilegeLevel.PARTICIPANT;
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
    }

    public InputUser(User user) {
        //from actual backend user class
        this.privilegeLevel = user.getPrivilegeLevel();
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

    }

    public InputUser(Participant user) {
        this.privilegeLevel = user.getPrivilegeLevel();
        this.fname = user.getFirstName();
        this.lname = user.getLastName();
        this.pwd = "";
        this.email = user.getEmailAddress();
        this.phone = user.getPhoneNumber().toString();
        this.street = user.getAddress().getStreet();
        this.city = user.getAddress().getCity();
        this.state = user.getAddress().getState();
        this.zipcode = user.getAddress().getZipCode();
        this.country = user.getAddress().getCountry();
    }

    public InputUser(int uid, PrivilegeLevel privilegeLevel, String fname, String lname, String pwd, String email, String phone, String street, String city, String state, String zipcode, String country) {
        //manual insertion
        this.uid = uid;
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

        this.privilegeLevel = privilegeLevel;
    }

    @Deprecated
    public InputUser(int uid, int level, String fname, String lname, String pwd, String email, String phone, String street, String city, String state, String zipcode, String country, int eventLevel, int participant) {
        //manual insertion
        this.uid = uid;
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

        this.privilegeLevel = PrivilegeLevel.legacyFromLevels(level, eventLevel, participant);
    }

    //GETTERS
    public int getUid() {
        return uid;
    }

    @Deprecated
    public int getLevel() {
        return getPrivilegeLevel().legacyIsAdmin() ? 1 : 0;
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

    public PrivilegeLevel getPrivilegeLevel() {
        return privilegeLevel;
    }


    //SETTERS
    public void setUid(int uid) {
        this.uid = uid;
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

    public void setPrivilegeLevel(PrivilegeLevel privilegeLevel) {
        this.privilegeLevel = privilegeLevel;
    }
}
