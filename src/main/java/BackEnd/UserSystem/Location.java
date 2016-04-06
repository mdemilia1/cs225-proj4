package BackEnd.UserSystem;

/**
 * This class represents a location.
 * @author Anderson Santana
 */
public class Location extends Address{
    private String details;
    
    public Location(){
        details = new String();
    }
   
    /**
     * This constructor initializes the location object details. 
     * @param details The details of the location
     */
    public Location(String details){
        this.details = details;
    }
    
    /**
     * This constructor initializes the location object with a 
     * street, city, state, zip code, country, and details.
     * @param street
     * @param city
     * @param state
     * @param zipCode
     * @param country
     * @param details 
     */
    public Location(String street, String city, String state, 
            String zipCode, String country, String details){
        super(street, city, state, zipCode, country);
        this.details = details;
    }
    
    /**
     * It sets the details of the location.
     * @param details The details of the location
     */
    public void setDetails(String details){
        this.details = details;
    }
    
    /**
     * It returns the details of the location.
     * @return The details of the location
     */
    public String getDetails(){
        return details;
    }
    
    /**
     * Determines whether the current object matches its argument.
     * @param obj The object to be compared to the current object
     * @return true if the object has the same details; otherwise,
     *          return false
     */
    @Override
    public boolean equals(Object obj){
        if(obj == this) return true;
        if(obj == null) return false;
        if(this.getClass() == obj.getClass()){
            Location other = (Location) obj;
            return super.equals(obj) && details.equals(other.details);
        } else {
            return false;
        }
    }
    
    /** It generates a hash code for the location object.
     *  @return The hash code
     */
    @Override
    public int hashCode(){
        return super.hashCode();
    }
    
    /**
     * Creates a string representation of the location.
     * @return A string representation of the location
     */
    @Override
    public String toString(){
        return super.toString() + "\n" + details;
    }
}