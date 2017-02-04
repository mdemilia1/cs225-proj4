/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import EMS_Database.impl.Committees_Table;
import EMS_Database.impl.Events_Table;
import EMS_Database.impl.Expense_Table;
import EMS_Database.impl.Income_Table;
import EMS_Database.impl.RootKey;
import EMS_Database.impl.SubEvent_Table;
import EMS_Database.impl.Tasks_Table;
import EMS_Database.impl.UserData_Table;
import org.junit.Test;

/**
 *
 * @author mike
 */
public class ClearTables {

    @Test
    public void Teardown() {
	//makes sure all the tables are created correctly
	Events_Table et = new Events_Table();
	SubEvent_Table set = new SubEvent_Table();
	Tasks_Table tt = new Tasks_Table();
	Committees_Table ct = new Committees_Table();
	UserData_Table udt = new UserData_Table();
	Expense_Table exTable = new Expense_Table();
	Income_Table inTable = new Income_Table();
	RootKey key = new RootKey();
	
	//remove data from each table.
	et.removeAll("EVENTS");
	set.removeAll("SUBEVENTS");
	tt.removeAll("TASKS");
	ct.removeAll("COMMITTEE");
	udt.removeAll("USERS");
	inTable.removeAll("INCOME");
	exTable.removeAll("EXPENSE");	
	key.removePrivKey();
	key.removePubKey();
    }    
}