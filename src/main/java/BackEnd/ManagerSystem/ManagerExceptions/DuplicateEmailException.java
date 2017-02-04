/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd.ManagerSystem.ManagerExceptions;

/**
 * A custom exception class for when a user tries to create an account,
 * but the email address is already in use.
 *
 * @author Julian Kuk
 */
public class DuplicateEmailException extends Exception {
    public DuplicateEmailException(){
    }
    
    public DuplicateEmailException(String message){
        super(message);
    }    
}
