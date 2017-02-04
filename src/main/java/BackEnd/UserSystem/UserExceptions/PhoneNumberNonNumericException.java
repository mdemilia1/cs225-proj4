package BackEnd.UserSystem.UserExceptions;

/**
 * PhoneNumberNonNumericException Exception
 * @author Anderson Santana
 */
public class PhoneNumberNonNumericException extends RuntimeException{
  
  /**
   * Default Constructor.
   */
  public PhoneNumberNonNumericException(){}
  
  /**
   * Constructor.
   * @param reason The reason of the exception.
   */
  public PhoneNumberNonNumericException(String reason){
    super(reason);
  }
}
