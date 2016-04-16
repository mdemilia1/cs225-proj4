package exception;

/**
 * Thrown to frontend when there's an unexpected SQL error while reading from the database.
 * @author Paul Buonopane
 */
public class ReadException extends RuntimeException {
    /**
     * {@inheritDoc}
     * @param message {@inheritDoc}
     * @param cause {@inheritDoc}
     */
    public ReadException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public String getMessage() {
        if (getCause() != null) {
            return String.format("%s: %s", super.getMessage(), getCause().getMessage());
        } else {
            return super.getMessage();
        }
    }

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public String getLocalizedMessage() {
        if (getCause() != null) {
            return String.format("%s: %s", super.getLocalizedMessage(), getCause().getLocalizedMessage());
        } else {
            return super.getLocalizedMessage();
        }
    }
}
