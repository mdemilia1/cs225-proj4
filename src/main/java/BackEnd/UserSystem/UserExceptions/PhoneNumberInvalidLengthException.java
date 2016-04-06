package BackEnd.UserSystem.UserExceptions;

/**
 * PhoneNumberInvalidLengthException Exception
 * @author Anderson Santana
 */
public class PhoneNumberInvalidLengthException extends RuntimeException{
  
  /**
   * Default Constructor.
   */
  public PhoneNumberInvalidLengthException(){}
  
  /**
   * Constructor.
   * @param reason The reason of the exception.
   */
  public PhoneNumberInvalidLengthException(String reason){
    super(reason);
  }
}
