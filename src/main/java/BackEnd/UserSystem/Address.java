package BackEnd.UserSystem;

import BackEnd.UserSystem.UserExceptions.ZipCodeInvalidLengthException;
import BackEnd.UserSystem.UserExceptions.ZipCodeInvalidFormatException;

/**
 * This class represents an Address.
 * @author Anderson Santana
 */
public class Address {
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private final int ZIP_CODE_LENGTH = 5;
    
    /**
     * Default Constructor.
     */
    public Address(){
        street = new String();
        city = new String();
        state = new String();
        zipCode = new String();
        country = new String();
    }
    
    /**
     * This constructor initializes the address object with a
     * street, a city, a state, a zip code, and a country.
     * @param street The street of the address
     * @param city  The city of the address
     * @param state The state of the address
     * @param zipCode The zip code of the address
     * @param country The country of the address
     */
    public Address(String street, String city, String state, 
            String zipCode, String country){
        this.street = street;
        this.city = city;
        this.state = state;
        verifyZipCodeFormat(zipCode);
        this.zipCode = zipCode;
        this.country = country;
    }
    
    /**
     * It sets the street of the address.
     * @param street The street of the address
     */
    public void setStreet(String street){
        this.street = street;
    }
    
    /**
     * It returns the street of the address.
     * @return The street of the address
     */
    public String getStreet(){
        return street;
    }
    
    /**
     * It sets the city of the address.
     * @param city The city of the address
     */
    public void setCity(String city){
        this.city = city;
    }
    
    /**
     * It returns the city of the address.
     * @return The city of the address
     */
    public String getCity(){
        return city;
    }
    
    /**
     * It sets the state of the address.
     * @param state The state of the address
     */
    public void setState(String state){
        this.state = state;
    }
    
    /**
     * It returns the state of the address.
     * @return The state of the address
     */
    public String getState(){
        return state;
    }
    
    /**
     * It sets the zip code of the address.
     * @param zipCode The zip code of the address
     */
    public void setZipCode(String zipCode){
        verifyZipCodeFormat(zipCode);
        this.zipCode = zipCode;
    }
    
    /**
     * It returns the zip code of the address.
     * @return The zip code of the address
     */
    public String getZipCode(){
        return zipCode;
    }
    
    /**
     * It verifies if the zip code has five integers.
     * @param zipCode
     * @return true if the zip code format has five digits;
     *   otherwise throws an exception
     */
    private boolean verifyZipCodeFormat(String zipCode){
        if(zipCode.equals("")){
            return true;
        }
        else if(zipCode.length() == ZIP_CODE_LENGTH){
            for(int i = 0; i < zipCode.length(); i++){
                if(Character.isDigit(zipCode.charAt(i)) == false)
                    throw new ZipCodeInvalidFormatException(
                      "Invalid zip code format");
            }
        } else throw new ZipCodeInvalidLengthException(
                      "Invalid zip code length");
        return true;
    }
    
    /**
     * It sets the country of the address.
     * @param country The country of the address
     */
    public void setCountry(String country){
        this.country = country;
    }
    
    /**
     * It returns the country of the address.
     * @return The country of the address
     */
    public String getCountry(){
        return country;
    }
    /** 
     * Determines whether the current object matches its argument.
     * @param obj The object to be compared to the current object
     * @return true if the object has the same street, city, state,
     *      zip code and country; otherwise, return false     
     */    
    @Override    
    public boolean equals(Object obj){
        if(obj == this) return true;
        if(obj == null) return false;
        if(this.getClass() == obj.getClass()){
            Address other = (Address) obj;
            return street.equals(other.street) &&
                     city.equals(other.city) && 
                     state.equals(other.state) && 
                    zipCode.equals(other.zipCode);
        } else {
            return false;
        }
    }
  
    /** It generates a hash code for the address object.
     *  @return The hash code
     */
    @Override
    public int hashCode(){
        int hash = 3;
        hash = 97 * hash + (this.street != null ? this.street.hashCode() : 0);
        hash = 97 * hash + (this.city != null ? this.city.hashCode() : 0);
        hash = 97 * hash + (this.state != null ? this.state.hashCode() : 0);
        hash = 97 * hash + (this.zipCode != null ? this.zipCode.hashCode() : 0);
        return hash;
    }
  
  /**
   * Creates a string representation of the address.
   * @return A string representation of the address.
   */
    @Override
    public String toString() {
        if (city.equals("")
                && state.equals("")) {
            return "";
        } else {

            String info = street + "\n"
                    + city + ", " + state
                    + " " + zipCode + "\n"
                    + country;
            return info;
        }
    }
}
