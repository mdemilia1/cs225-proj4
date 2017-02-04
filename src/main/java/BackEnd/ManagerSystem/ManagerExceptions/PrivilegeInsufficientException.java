/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd.ManagerSystem.ManagerExceptions;

/**
 * A custom exception class for when a user does not have the proper privilege level.
 *
 * @author Julian Kuk
 */
public class PrivilegeInsufficientException extends Exception{
    
    public PrivilegeInsufficientException(String message){
        super(message);
    }
    
}
