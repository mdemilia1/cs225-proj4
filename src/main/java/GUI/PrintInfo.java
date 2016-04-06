/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import BackEnd.ManagerSystem.MainManager;
import EMS_Database.impl.Committees_Table;
import EMS_Database.impl.Events_Table;
import EMS_Database.impl.Expense_Table;
import EMS_Database.impl.Income_Table;
import EMS_Database.impl.SubEvent_Table;
import EMS_Database.impl.Tasks_Table;
import EMS_Database.impl.UserData_Table;

/**
 *
 * @author Sid
 */
public class PrintInfo {
    
    public static void main(String[] args)
    {
        Events_Table edt = new Events_Table();
        //edt.removeAll("EVENTS");
        System.out.println("EVENTS");
        System.out.println(edt.queryEntireTable());
        
        System.out.println("USERS");
        UserData_Table udt = new UserData_Table();
        //udt.removeAll("USERS");
        System.out.println(udt.queryEntireTable());
        
        System.out.println("COMMITTEES");
        Committees_Table ct = new Committees_Table();
        //ct.removeAll("COMMITTEE");
        System.out.println(ct.queryEntireTable());
        
        System.out.println("TASKS");
        Tasks_Table tt = new Tasks_Table();
        System.out.println(tt.queryEntireTable());
        
        System.out.println("INCOMES");
        Income_Table it = new Income_Table();
        System.out.println(it.queryEntireTable());
        
        System.out.println("EXPENSES");
        Expense_Table et = new Expense_Table();
        System.out.println(et.queryEntireTable());
        
        System.out.println("SUBEVENTS");
        SubEvent_Table set = new SubEvent_Table();
        //set.removeAll("SUBEVENTS");
        System.out.println(set.queryEntireTable());
        
        System.out.println("ORGANIZERS");
        MainManager manager = MainManager.getInstance();
        for (int i = 0; i < manager.getEventManager().getEventList().size(); i++){
            manager.getEventManager().setSelectedEvent(manager.getEventManager().getEventList().get(0));
            System.out.println(manager.getEventManager().getSelectedEvent().getOrganizerList());
            System.out.println("PARTICIPANTS");
            System.out.println(manager.getEventManager().getSelectedEvent().getParticipantList());
        }
    }
}
