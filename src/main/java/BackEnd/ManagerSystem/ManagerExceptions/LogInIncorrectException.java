/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd.ManagerSystem.ManagerExceptions;

/**
 * This is a custom exception class for when a user enters incorrect log in information.
 * 
 * @author Julian Kuk
 */
public class LogInIncorrectException extends Exception {
    public LogInIncorrectException(){
    }
    
    public LogInIncorrectException(String message){
        super(message);
    }
}
