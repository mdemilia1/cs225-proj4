/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd.UserSystem.UserExceptions;

/**
 * 
 * @author David Tersoff
 */
public class IllegalCharacterException extends ValidationException {
    /**
     * Constructs an instance of
     * <code>IllegalCharacterException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public IllegalCharacterException(String msg) {
        super(msg);
    }
}
