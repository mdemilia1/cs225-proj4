    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;
import BackEnd.ManagerSystem.ManagerExceptions.PrivilegeInsufficientException;
import EMS_Database.DoesNotExistException;
import EMS_Database.DuplicateInsertionException;
import GUI.Dialog.NewEventDialog;
import BackEnd.EventSystem.Event;
import BackEnd.EventSystem.Committee;
import BackEnd.UserSystem.User;
import BackEnd.EventSystem.TimeSchedule;
import BackEnd.ManagerSystem.MainManager;
import GUI.Dialog.LoginDialog;
import GUI.Dialog.SignupDialog;
import auth.AuthorizationException;
import exception.UpdateException;
import java.awt.Component;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultEditorKit;
import javax.swing.undo.UndoManager;
import java.util.ArrayList;

/**
 *
 * @author Sid
 */
public class Home extends javax.swing.JFrame {

    /**
     * Creates new form Home
     */
    private UndoManager undo = new UndoManager();
    private MainManager manager;
    private Main main;
    private Component activePanel;
    public static boolean programReady = true;
    /**
     *
     */
    public Home() throws AuthorizationException, PrivilegeInsufficientException,
            DoesNotExistException, DuplicateInsertionException{
        initComponents();
        manager = MainManager.getInstance();
        /* Added following line to center dialog. -Ketty */
        setLocationRelativeTo(null);
        while (checkLogIn()) {//CHECK FOR LOGIN
            logIn();
        }
        while (checkForEvents()) {//CHECK IF WE HAVE EVENTS
            createFirstEvent();
        }
        loadEvent();
        /* Changed this try block. -Ketty */
        addPanel();
        setVisible(true);
    }
    public void setEvent(Event e){
        manager.getEventManager().setSelectedEvent(e);
    }
    
    public boolean checkForUsers()
    {
        return manager.getUserManager().getUserList().isEmpty();
    }
    
    public boolean checkForEvents()
    {
        return manager.getEventManager().getEventList().isEmpty();
    }
    public boolean checkLogIn()
    {
        return manager.getLogInManager().getLoggedInUser() == null;
    }
    public void createFirstUser()
    {
            JOptionPane.showMessageDialog(this, "There are no users in the user list.  Please create an administrator account first.");
            SignupDialog sd = new SignupDialog(this, true);
            sd.setVisible(true);
    }
    public void createFirstEvent() throws AuthorizationException, PrivilegeInsufficientException,
            DoesNotExistException, DuplicateInsertionException{
         try
            {
                JOptionPane.showMessageDialog(this, "An event has not been created yet.  Please create one first.");
                NewEventDialog ned = new NewEventDialog(this, true);
                ned.setVisible(true);
                if(ned.getConfirm())
                {
                    Event event = ned.createEvent();
                
                        manager.getEventManager().setSelectedEvent(manager.getEventManager().createEvent(
                                event));
                        manager.getEventManager().editDescription(
                                event.getDescription());
                        TimeSchedule ts = event.getTimeSchedule();
                        int hour = ts.getStartDateTimeCalendar().get(Calendar.HOUR);
                        int minute = ts.getStartDateTimeCalendar().get(Calendar.MINUTE);
                        int year = ts.getStartDateTimeCalendar().get(Calendar.YEAR);
                        int month = ts.getStartDateTimeCalendar().get(Calendar.MONTH);
                        int day = ts.getStartDateTimeCalendar().get(Calendar.DAY_OF_MONTH);
                        manager.getEventManager().editStartDateTime(
                                year, month, day, hour, minute);
                        hour = ts.getEndDateTimeCalendar().get(Calendar.HOUR);
                        minute = ts.getEndDateTimeCalendar().get(Calendar.MINUTE);
                        year = ts.getEndDateTimeCalendar().get(Calendar.YEAR);
                        month = ts.getEndDateTimeCalendar().get(Calendar.MONTH);
                        day = ts.getEndDateTimeCalendar().get(Calendar.DAY_OF_MONTH);  
                        manager.getEventManager().editEndDateTime(
                                year, month, day, hour, minute);
                }
            }
            catch (UpdateException error)
            {
                JOptionPane.showMessageDialog(this, "Unable to create event.");
                System.out.println("Update main event error in Home: " + error.getMessage());
            }
    }
    public void loadEvent()
    {
         manager.getEventManager().setSelectedEvent(
                 manager.getEventManager().getEventList().get(0));
    }
    
    public void logIn()
    {
           LoginDialog ld = new LoginDialog(this, true);
           ld.setVisible(true);
           if (ld.getConfirm()) 
           {
               ld.createUser();
               manager.getUserManager().setSelectedUser(
                       manager.getLogInManager().getLoggedInUser());
           }
    }
    
    public void logOut() throws AuthorizationException, PrivilegeInsufficientException,
        DoesNotExistException, DuplicateInsertionException{
        dispose();
        manager.getLogInManager().logOut();
        Home home = new Home();
    }
    
    public void addPanel() {
        try {
            main = new Main(this);

            User loggedInUser = manager.getLogInManager().getLoggedInUser();
            Event selectedEvent = manager.getEventManager().getSelectedEvent();
            ArrayList<User> allCommitteeMembers = new ArrayList<User>();

            for (int i = 0; i < selectedEvent.getCommitteeList().size(); i++) {
                Committee committee = selectedEvent.getCommitteeList().get(i);

                for (int j = 0; j < committee.getMemberListWithChair().size(); j++) {
                    User committeeMember = committee.getMemberListWithChair().get(j);
                    if (!allCommitteeMembers.contains(committeeMember)) {
                        allCommitteeMembers.add(committeeMember);
                    }
                }
            }

            if (loggedInUser.getPrivilegeLevel().isAdmin()) {
                add(main);
                activePanel = (Component) main;
            }
            else {
                ArrayList<User> committeeChairList = new ArrayList<User>();
                ArrayList<Committee> committeeList = selectedEvent.getCommitteeList();
                for (int i = 0; i < committeeList.size(); i++) {
                    committeeChairList.add(committeeList.get(i).getChair());
                }
                if (committeeChairList.contains(loggedInUser)) {
                    main.getUserManagementPanel().setNonAdminView();
                    add(main);
                    activePanel = (Component) main;
                } else if (allCommitteeMembers.contains(loggedInUser)) {
                    main.setCommitteeView();
                    add(main);
                    activePanel = (Component) main;
                } else {
                    main.setParticipantView();
                    add(main);
                    activePanel = (Component) main;
                }
                /*
                 if(true){
                 }
                 else if (selectedEvent.getOrganizerList().contains(loggedInUser)) {
                 main.getUserManagementPanel().setNonAdminView();
                 add(main);
                 activePanel = (Component)main;
                 }
                 else if (allCommitteeMembers.contains(loggedInUser)) {
                 main.setCommitteeView();
                 add(main);
                 activePanel = (Component)main;
                 }
                 else {
                 main.setParticipantView();
                 add(main);
                 activePanel = (Component)main;
                 }*/
            }
        }
         catch (Exception e) {
            System.out.println("CAN'T CREATE HOME\n" + e);
            e.printStackTrace();
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        emsMenuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        printMenuItem = new javax.swing.JMenuItem();
        printPreviewMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        saveMenuItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        logOutMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        cutMenuItem = new javax.swing.JMenuItem(new DefaultEditorKit.CutAction());
        copyMenuItem = new javax.swing.JMenuItem(new DefaultEditorKit.CopyAction());
        pasteMenuItem = new javax.swing.JMenuItem(new DefaultEditorKit.PasteAction());
        toolsMenu = new javax.swing.JMenu();
        reportsMenu = new javax.swing.JMenu();
        committeeReportsMenuItem = new javax.swing.JMenuItem();
        registrationReportsMenuItem = new javax.swing.JMenuItem();
        budgetReportsMenuItem = new javax.swing.JMenuItem();
        databaseMenu = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(960, 700));
        setMinimumSize(new java.awt.Dimension(960, 700));
        setResizable(false);

        fileMenu.setText("File");

        printMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        printMenuItem.setText("Print");
        printMenuItem.setEnabled(false);
        printMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(printMenuItem);

        printPreviewMenuItem.setText("Print Preview");
        printPreviewMenuItem.setEnabled(false);
        printPreviewMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printPreviewMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(printPreviewMenuItem);
        fileMenu.add(jSeparator1);

        saveMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveMenuItem.setText("Save");
        saveMenuItem.setEnabled(false);
        saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveMenuItem);
        fileMenu.add(jSeparator2);

        logOutMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        logOutMenuItem.setText("Logout");
        logOutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                try{
                    logOutMenuItemActionPerformed(evt);
                }catch (AuthorizationException AEx){
                }catch (PrivilegeInsufficientException PEx){
                }catch (DoesNotExistException DNEex){
                }catch (DuplicateInsertionException dupEx){}
            }
        });
        fileMenu.add(logOutMenuItem);

        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        emsMenuBar.add(fileMenu);

        editMenu.setText("Edit");

        cutMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        cutMenuItem.setText("Cut");
        editMenu.add(cutMenuItem);

        copyMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        copyMenuItem.setText("Copy");
        editMenu.add(copyMenuItem);

        pasteMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        pasteMenuItem.setText("Paste");
        editMenu.add(pasteMenuItem);

        emsMenuBar.add(editMenu);

        toolsMenu.setText("Tools");

        reportsMenu.setText("Reports");

        committeeReportsMenuItem.setText("Committee");
        committeeReportsMenuItem.setEnabled(false);
        committeeReportsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                committeeReportsMenuItemActionPerformed(evt);
            }
        });
        reportsMenu.add(committeeReportsMenuItem);

        registrationReportsMenuItem.setText("Registration");
        registrationReportsMenuItem.setEnabled(false);
        registrationReportsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registrationReportsMenuItemActionPerformed(evt);
            }
        });
        reportsMenu.add(registrationReportsMenuItem);

        budgetReportsMenuItem.setText("Budget");
        budgetReportsMenuItem.setEnabled(false);
        budgetReportsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                budgetReportsMenuItemActionPerformed(evt);
            }
        });
        reportsMenu.add(budgetReportsMenuItem);

        toolsMenu.add(reportsMenu);

        databaseMenu.setText("Database");

        jMenuItem1.setText("Import");
        databaseMenu.add(jMenuItem1);

        jMenuItem2.setText("Export");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        databaseMenu.add(jMenuItem2);

        toolsMenu.add(databaseMenu);

        emsMenuBar.add(toolsMenu);

        helpMenu.setText("Help");

        aboutMenuItem.setText("About");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutMenuItem);

        emsMenuBar.add(helpMenu);

        setJMenuBar(emsMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 960, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 679, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void saveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMenuItemActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(this, "Can't save yet!");
    }//GEN-LAST:event_saveMenuItemActionPerformed

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(this, "Ask Julian...");
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    private void registrationReportsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registrationReportsMenuItemActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(this, "Registration reports would be here");
    }//GEN-LAST:event_registrationReportsMenuItemActionPerformed

    private void printPreviewMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printPreviewMenuItemActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(this, "You're still checking these?.");
    }//GEN-LAST:event_printPreviewMenuItemActionPerformed

    private void printMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printMenuItemActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(this, "Not implemented yet");
    }//GEN-LAST:event_printMenuItemActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void committeeReportsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_committeeReportsMenuItemActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(this, "Committee reports would be here if they existed...");
    }//GEN-LAST:event_committeeReportsMenuItemActionPerformed

    private void budgetReportsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_budgetReportsMenuItemActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(this, "Why does saying budget report make me giggle?");
    }//GEN-LAST:event_budgetReportsMenuItemActionPerformed

    private void logOutMenuItemActionPerformed(java.awt.event.ActionEvent evt)
        throws AuthorizationException, PrivilegeInsufficientException,
            DoesNotExistException, DuplicateInsertionException{//GEN-FIRST:event_logOutMenuItemActionPerformed
        logOut();
    }//GEN-LAST:event_logOutMenuItemActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenuItem budgetReportsMenuItem;
    private javax.swing.JMenuItem committeeReportsMenuItem;
    private javax.swing.JMenuItem copyMenuItem;
    private javax.swing.JMenuItem cutMenuItem;
    private javax.swing.JMenu databaseMenu;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuBar emsMenuBar;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JMenuItem logOutMenuItem;
    private javax.swing.JMenuItem pasteMenuItem;
    private javax.swing.JMenuItem printMenuItem;
    private javax.swing.JMenuItem printPreviewMenuItem;
    private javax.swing.JMenuItem registrationReportsMenuItem;
    private javax.swing.JMenu reportsMenu;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JMenu toolsMenu;
    // End of variables declaration//GEN-END:variables
}