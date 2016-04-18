/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd.EventSystem;

import auth.AuthorizationException;

import java.util.ArrayList;

/**
 * This class is a simple interface for generating reports.
 *
 * @author Julian Kuk
 */
public interface Reportable {
    public ArrayList<Object> getReport() throws AuthorizationException;
}
