/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd.EventSystem;

import auth.AuthorizationException;
import auth.Permissions;

import java.util.ArrayList;

/**
 *
 * @author Karina
 */
/**
     * This class is made to cleanly display the items in the Calendar and in the detailsList of the CalendarPanel
     */
public class CalendarEvent {
    
    int day;
    ArrayList<SubEvent> subEventList;
    
    /**
     * The constructor initializes the variables through the parameters. Nothing is default.
     */
    public CalendarEvent(int d, ArrayList<SubEvent> sList)
    {
        try(Permissions.SystemTransaction ignored = Permissions.get().beginSystemTransaction()) {
            setDay(d);
            setSubEventList(sList);
        }
        catch(AuthorizationException ignored){}
    }

    /**
     * Sets the day that this CalendarEvent is on.
     */
    public void setDay(int d) throws AuthorizationException {
        day = d;
    }

    /**
     * Sets the list of subEvents to be on this day.
     */
    public void setSubEventList(ArrayList<SubEvent> sList) throws AuthorizationException {
        subEventList = sList;
    }
    
    /**
     * Returns the day this CalendarEvent represents
     */
    public int getDay() throws AuthorizationException
    {
        return day;
    }
    
    /**
     * Returns the list of subEvents for this day
     */
    public ArrayList<SubEvent> getSubEventList() throws AuthorizationException
    {
        return subEventList;
    }
    
    /**
     * Displays the events on this day, each on their own line.
     * If the day doesn't have any events, it's day is represented as a -1
     * and the text becomes blank. (This is used for the completely unpopulated
     * cells of the Calendar.
     */
    public String toString() {
        String events = "";
        if (day != -1) {
            events += "" + day;
            if (subEventList.size() <= 3) {
                for (int i = 0; i < subEventList.size(); i++) {
                    try(Permissions.SystemTransaction ignored = Permissions.get().beginSystemTransaction()) {
                        events += "\n" + subEventList.get(i).getTitle();
                    }
                    catch(AuthorizationException ignored){}
                }
            } else {
                for (int i = 0; i < 3; i++) {
                    try(Permissions.SystemTransaction ignored = Permissions.get().beginSystemTransaction()) {
                        events += "\n" + subEventList.get(i).getTitle();
                    }
                    catch(AuthorizationException ignored){}
                }
                events += "\n...";
            }
        }
        return events;

    }
    
    
    
}
