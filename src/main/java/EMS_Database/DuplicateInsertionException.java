package EMS_Database;

/**
 *
 * @author mike
 * 
 */
public class DuplicateInsertionException extends Exception {

    public DuplicateInsertionException() {
    }

    public DuplicateInsertionException(String type) {
        super(type);
    }
}
