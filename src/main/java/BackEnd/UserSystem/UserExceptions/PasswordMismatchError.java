/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd.UserSystem.UserExceptions;

/**
 *
 * @author David Tersoff
 */
public class PasswordMismatchError extends Exception {

    /**
     * Creates a new instance of
     * <code>PasswordMismatchError</code> without detail message.
     */
    public PasswordMismatchError() {
    }

    /**
     * Constructs an instance of
     * <code>PasswordMismatchError</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public PasswordMismatchError(String msg) {
        super(msg);
    }
}
