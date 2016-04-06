/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import BackEnd.UserSystem.UserExceptions.IllegalCharacterException;
import BackEnd.UserSystem.UserExceptions.PasswordMismatchError;
import BackEnd.ManagerSystem.ManagerExceptions.PrivilegeInsufficientException;
import EMS_Database.DoesNotExistException;
import GUI.Home;

/**
 *
 * @author Karina
 */
public class EMSLauncher 
{
    public static void main(String[] args) throws PasswordMismatchError, IllegalCharacterException, PrivilegeInsufficientException, DoesNotExistException
    {
        // MainManager manager = MainManager.getInstance();
        Home home = new Home();
        home.setVisible(true);
    }
}
