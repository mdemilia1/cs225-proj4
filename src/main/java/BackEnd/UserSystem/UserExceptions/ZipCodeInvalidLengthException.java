package BackEnd.UserSystem.UserExceptions;

/**
 *  ZipCodeInvalidLengthException Exception
 *  @author Anderson Santana
 */
public class  ZipCodeInvalidLengthException extends RuntimeException{
  
  /**
   * Default Constructor.
   */
  public  ZipCodeInvalidLengthException(){}
  
  /**
   * Constructor.
   * @param reason The reason of the exception.
   */
  public  ZipCodeInvalidLengthException(String reason){
    super(reason);
  }
}
