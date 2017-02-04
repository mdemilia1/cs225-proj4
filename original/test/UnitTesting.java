/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import EMS_Database.DoesNotExistException;
import EMS_Database.InputCommittee;
import EMS_Database.InputEventData;
import EMS_Database.InputExpense;
import EMS_Database.InputIncome;
import EMS_Database.InputSubEventData;
import EMS_Database.InputTask;
import EMS_Database.InputUser;
import EMS_Database.impl.Committees_Table;
import EMS_Database.impl.Events_Table;
import EMS_Database.impl.Expense_Table;
import EMS_Database.impl.Income_Table;
import EMS_Database.impl.RootKey;
import EMS_Database.impl.SubEvent_Table;
import EMS_Database.impl.Tasks_Table;
import EMS_Database.impl.UserData_Table;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author mike
 */
public class UnitTesting {
    //Make sure that all tables exist throughout testing
    Events_Table et = new Events_Table();
    SubEvent_Table set = new SubEvent_Table();
    Tasks_Table tt = new Tasks_Table();
    Committees_Table ct = new Committees_Table();
    UserData_Table udt = new UserData_Table();
    Expense_Table exTable = new Expense_Table();
    Income_Table inTable = new Income_Table();
    RootKey key = new RootKey();
    
    //Universal insertion data.
    Timestamp startDate = new Timestamp(new Date().getTime());
    Timestamp endDate = new Timestamp(new Date().getTime());
    ArrayList<Integer> list = new ArrayList<Integer>();

    public UnitTesting() {
    }
//    @BeforeClass
//    public static void setUpClass() {
//    }

    @Before
    public void setUpBogusData() {
	//BOGUS Events table data
	InputEventData ied = new InputEventData();
	//BOGUS SubEvents table data
	InputSubEventData ised = new InputSubEventData();
	//BOGUS Committees table data
	InputCommittee ic = new InputCommittee();
	//BOGUS Tasks table data
	InputTask it = new InputTask();
	//BOGUS UserData table data
	InputUser iu = new InputUser(); //can be declared with no data.
	//BOGUS Income data
	//InputIncome in = new InputIncome("some large sum of money", 0.01);
	InputIncome in = new InputIncome();
	//BOGUS Expense data
	//InputExpense ex = new InputExpense("something expensive", 0.02);
	InputExpense ex = new InputExpense();

	//Creates 30 rows in each table 	
	for (int x = 0; x < 30; x++) {
	    et.createEvent(ied);
	    set.createSubEvent(ised);
	    tt.createTask(it);
	    ct.createCommittee(ic);
	    udt.createUser(iu);
	    inTable.insertBudgetItem(in);
	    exTable.insertBudgetItem(ex);
	}
	System.out.println("Data added.");
    }

    @Test
    @Ignore
    public void queryAllTables() {
	System.out.println("EVENTS TABLE");
	System.out.print(et.queryEntireTable());
	System.out.println("SUBEVENTS TABLE");
	System.out.print(set.queryEntireTable());
	System.out.println("TASKS TABLE");
	System.out.print(tt.queryEntireTable());
	System.out.println("COMMITTEE TABLE");
	System.out.print(ct.queryEntireTable());
	System.out.println("USERS TABLE");
	System.out.print(udt.queryEntireTable());
	System.out.println("INCOME TABLE");
	System.out.print(inTable.queryEntireTable());
	System.out.println("EXPENSE TABLE");
	System.out.print(exTable.queryEntireTable());
    }

    @Test    
    @Ignore
    public void testEventsTable() {
	try {
	    int uid = 2;
	    //System.out.println(et.getDescription(uid));
	    //System.out.println(et.getDetails(uid));
            System.out.println(et.getTitle(uid));
//	    System.out.println(et.getStartDate(uid));
//	    System.out.println(et.getEndDate(uid));
//	    System.out.println(et.getComplete(uid));
//	    System.out.println(et.getStreet(uid));
//	    System.out.println(et.getCity(uid));
//	    System.out.println(et.getState(uid));
//	    System.out.println(et.getZipcode(uid));
//	    System.out.println(et.getCountry(uid));
//	    System.out.println(et.getOrganizerList(uid));
//	    System.out.println(et.getSubEventList(uid));
//	    System.out.println(et.getParticipantList(uid));
//	    System.out.println(et.getCommittee(uid));

//	    et.setDescription(uid, "al;skdjf;alskdf");
//	    et.setDetails(uid, "new details");
            et.setTitle(uid, "penguin");
//	    et.setStartDate(uid, startDate);
//	    et.setEndDate(uid, endDate);
//	    et.setComplete(uid, 1);
//	    et.setStreet(uid, "Mulholland Drive");
//	    et.setCity(uid, "Tokyo");
//	    et.setState(uid, "XYZ");
//	    et.setZipcode(uid, "90210");
//	    et.setCountry(uid, "JAPAN!");
//	    et.setOrganizerList(uid, list);
//	    et.setSubEventList(uid, list);
//	    et.setParticipantList(uid, list);
//	    et.setCommittee(uid, list);
            
            System.out.println(et.getTitle(uid));


	} catch (DoesNotExistException dnee) {
	    System.out.println(dnee.getMessage());
	}
    }

    @Test
    @Ignore
    public void testSubEventsTable() {
	try {
	    int uid = 2;
//	    System.out.println(set.getDescription(uid));
//	    System.out.println(set.getDetails(uid));
	    System.out.println(set.getTitle(uid));
//	    System.out.println(set.getComplete(uid));
//	    System.out.println(set.getStreet(uid));
//	    System.out.println(set.getCity(uid));
//	    System.out.println(set.getState(uid));
//	    System.out.println(set.getZipcode(uid));
//	    System.out.println(set.getStartDate(uid));
//	    System.out.println(set.getEndDate(uid));
//
//	    set.setDescription(uid, "Better Description!");
//	    set.setDetails(uid, "new details");
	    set.setTitle(uid, "New Title");
//	    set.setStreet(uid, "28 Mullholand Drive");
//	    set.setCity(uid, "Amherst");
//	    set.setState(uid, "Massachusetts");
//	    set.setZipcode(uid, "12345");
//	    set.setCountry(uid, "USA");
//	    set.setStartDate(uid, startDate);
//	    set.setEndDate(uid, endDate);
//	    set.setComplete(uid, 1);
//
//	    System.out.println(set.getDescription(uid));
//	    System.out.println(set.getDetails(uid));
	    System.out.println(set.getTitle(uid));
//	    System.out.println(set.getComplete(uid));
//	    System.out.println(set.getStreet(uid));
//	    System.out.println(set.getCity(uid));
//	    System.out.println(set.getState(uid));
//	    System.out.println(set.getZipcode(uid));
//	    System.out.println(set.getStartDate(uid));
//	    System.out.println(set.getEndDate(uid));

	} catch (DoesNotExistException dnee) {
	    System.err.println(dnee.getMessage());
	}
    }

    @Test
    @Ignore
    public void testTasksTable() {
	try {
	    int uid = 2;
//	    System.out.println(tt.getDescription(uid));
//	    System.out.println(tt.getDetails(uid));
	    System.out.println(tt.getTitle(uid));
//	    System.out.println(tt.getStreet(uid));
//	    System.out.println(tt.getCity(uid));
//	    System.out.println(tt.getState(uid));
//	    System.out.println(tt.getZipcode(uid));
//	    System.out.println(tt.getCountry(uid));
//	    System.out.println(tt.getStartDate(uid));
//	    System.out.println(tt.getEndDate(uid));
//	    System.out.println(tt.getComplete(uid));
//	    System.out.println(tt.getAuthority(uid));

//	    tt.setDescription(uid, "set description");
//	    tt.setDetails(uid, "set details");
	    tt.setTitle(uid, "NEW TITLE!");
//	    tt.setStreet(uid, "set street");
//	    tt.setCity(uid, "set city");
//	    tt.setState(uid, "set state");
//	    tt.setZipcode(uid, "set zipcode");
//	    tt.setCountry(uid, "se country");
//	    tt.setStartDate(uid, startDate);
//	    tt.setEndDate(uid, endDate);
//	    tt.setComplete(uid, 1);
//	    tt.setAuthority(uid, list);
//
//	    System.out.println(tt.getDescription(uid));
//	    System.out.println(tt.getDetails(uid));
	    System.out.println(tt.getTitle(uid));
//	    System.out.println(tt.getStreet(uid));
//	    System.out.println(tt.getCity(uid));
//	    System.out.println(tt.getState(uid));
//	    System.out.println(tt.getZipcode(uid));
//	    System.out.println(tt.getCountry(uid));
//	    System.out.println(tt.getStartDate(uid));
//	    System.out.println(tt.getEndDate(uid));
//	    System.out.println(tt.getComplete(uid));
//	    System.out.println(tt.getAuthority(uid));

	} catch (DoesNotExistException dnee) {
	    System.err.println(dnee.getMessage());
	}
    }

    @Test    
    @Ignore
    public void testRootKey() {
	key.addPrivKey(BigInteger.ONE, BigInteger.ONE);
	key.addPubKey(BigInteger.TEN, BigInteger.TEN);
	try {
	    System.out.println(key.getPrivExp().toString());
	    System.out.println(key.getPrivMod().toString());
	    System.out.println(key.getPubMod().toString());
	    System.out.println(key.getPubExp().toString());
	} catch (DoesNotExistException dnee) {
	    System.err.println(dnee.getMessage());
	}
	key.removePrivKey();
	key.removePubKey();
    }

    @Test
    @Ignore
    public void testEmailFunction() {
	System.out.println(udt.checkEmail("AB@A.com"));
    }

    @After
    public void tearDownTables() {
	et.removeAll("EVENTS");
	set.removeAll("SUBEVENTS");
	tt.removeAll("TASKS");
	ct.removeAll("COMMITTEE");
	udt.removeAll("USERS");
	inTable.removeAll("INCOME");
	exTable.removeAll("EXPENSE");
	System.out.println("Data removed.");
    }
//    @AfterClass
//    public static void tearDownClass() {
//    }
}