package EMS_Database;

/**
 *
 * @author mike
 */
public class DoesNotExistException extends Exception {

    public DoesNotExistException() {
        
    }

    public DoesNotExistException(String type) {
        super(type);
    }
}
