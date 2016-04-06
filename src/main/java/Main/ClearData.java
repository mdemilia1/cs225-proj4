/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

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
public class ClearData {
    
        public static void main(String[] args)
        {
            System.out.println("DELETING EVENTS");
            Events_Table edt = new Events_Table();
            edt.removeAll("EVENTS");
        
            System.out.println("DELETING USERS");
            UserData_Table udt = new UserData_Table();
            udt.removeAll("USERS");
        
            System.out.println("DELETING COMMITTEES");
            Committees_Table ct = new Committees_Table();
            ct.removeAll("COMMITTEE");
        
            System.out.println("DELETINGSUBEVENTS");
            SubEvent_Table set = new SubEvent_Table();
            set.removeAll("SUBEVENTS");
        
            System.out.println("DELETING TASKS");
            Tasks_Table tt = new Tasks_Table();
            tt.removeAll("TASKS");
            
            System.out.println("DELETING INCOMES");
            Income_Table incomeTable = new Income_Table();
            incomeTable.removeAll("INCOME");
            
            System.out.println("DELETING EXPENSES");
            Expense_Table expenseTable = new Expense_Table();
            expenseTable.removeAll("EXPENSE");
//            MainManager manager = MainManager.getInstance();
//            try
//            {
//                User u = new User("Alpha","Bravo","AB@AB.com","ab","ab");
//                Address addr = new Address("Street","City", "STATE","00000","COUNTRY");
//                PhoneNumber num = new PhoneNumber("5555555555");
//                u.setAddress(addr);
//                u.setPhoneNumber(num);
//                u.setAdminPrivilege(true); 
//                u.setEventCreationPrivilege(true);
//                manager.getUserManager().createUser(u);
//            }
//            catch (Exception e)
//            {
//                e.printStackTrace();
//            }
        }
}
