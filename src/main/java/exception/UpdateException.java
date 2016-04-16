package exception;

/**
 * Thrown to frontend when an error occurs while saving information to the database.  This can occur if, for example,
 * an attempt is made to create a duplicate entry.
 * @author Paul Buonopane
 */
public class UpdateException extends Exception {
    /**
     * {@inheritDoc}
     * @param message {@inheritDoc}
     * @param cause {@inheritDoc}
     */
    public UpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public String getMessage() {
        return String.format("%s: %s", super.getMessage(), getCause().getMessage());
    }

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public String getLocalizedMessage() {
        return String.format("%s: %s", super.getLocalizedMessage(), getCause().getLocalizedMessage());
    }
}
