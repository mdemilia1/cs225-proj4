/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import BackEnd.EventSystem.Event;
import BackEnd.ManagerSystem.EventManager;
import BackEnd.ManagerSystem.MainManager;
import BackEnd.ManagerSystem.ManagerExceptions.PrivilegeInsufficientException;
import BackEnd.UserSystem.Participant;
import BackEnd.UserSystem.User;
import EMS_Database.DoesNotExistException;
import EMS_Database.DuplicateInsertionException;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Karina
 */
public class MainPanel extends javax.swing.JPanel {

    private JPanel calendarSelectionPanel;
    private JButton selectCalendarButton, selectEventDetailsButton, registerForEventButton;
    //private JButton clearEventButton;
    private JPanel calendarSwitchingPanel;
    private CalendarPanel cp;
    private EventManager eventManager;
    private User loggedInUser;
    private ArrayList<User> organizerList;
    private ArrayList<Participant> participantList;
    
    private final String REGISTER = "Register for Event";
    private final String UNREGISTER = "Unregister";

    /**
     * Creates new form MainPanel and sets up the inner panels
     */
    public MainPanel() {
        eventManager = MainManager.getInstance().getEventManager();
        loggedInUser = MainManager.getInstance().getLogInManager().getLoggedInUser();
        participantList = eventManager.getSelectedEvent().getParticipantList();
        organizerList = eventManager.getSelectedEvent().getOrganizerList();
        
        initComponents();
        setLayout(new BorderLayout());
        calendarSelectionPanel = new JPanel();
        calendarSwitchingPanel = new JPanel();

        selectCalendarButton = new JButton("Calendar");
        selectEventDetailsButton = new JButton("Event Details");

        if (participantList.contains(loggedInUser)) {
            registerForEventButton = new JButton(UNREGISTER);
        } else {
            registerForEventButton = new JButton(REGISTER);
        }

        Dimension dimension = new Dimension(150, 25);
        registerForEventButton.setPreferredSize(dimension);

        selectCalendarButton.addActionListener(new CalendarButtonListener());
        selectEventDetailsButton.addActionListener(new CalendarButtonListener());
        registerForEventButton.addActionListener(new RegisterForEventButtonListener());


        calendarSwitchingPanel.setLayout(new CardLayout());

        calendarSelectionPanel.add(selectCalendarButton);
        calendarSelectionPanel.add(selectEventDetailsButton);
        calendarSelectionPanel.add(registerForEventButton);
  
        /*
        clearEventButton = new JButton("Clear Event");
        clearEventButton.addActionListener(new ClearEventButtonListener());
        calendarSelectionPanel.add(clearEventButton);
        */

        cp = new CalendarPanel();
        EventDetailsPanel edp = new EventDetailsPanel();
        calendarSwitchingPanel.add(cp, "calendar");
        calendarSwitchingPanel.add(edp, "eventDetails");
        add(calendarSwitchingPanel, BorderLayout.NORTH);
        add(calendarSelectionPanel, BorderLayout.SOUTH);
    }

    /**
     * Sets the view to that of a user who is not an admin
     */
    public void setNonAdminOrganizerView() {
        cp.hideEventButtons();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setMaximumSize(new java.awt.Dimension(640, 100));
        setMinimumSize(new java.awt.Dimension(640, 100));
        setPreferredSize(new java.awt.Dimension(640, 100));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    /**
     * Switches the visible panel to the Calendar
     */
    private void selectCalendarButtonActionPerformed(java.awt.event.ActionEvent evt) {
        CardLayout cl = (CardLayout) (calendarSwitchingPanel.getLayout());
        cl.show(calendarSwitchingPanel, "calendar");
    }

    /**
     * Switches the visible panel to the Event Details
     */
    private void selectEventDetailsButtonActionPerformed(java.awt.event.ActionEvent evt) {
        CardLayout cl = (CardLayout) (calendarSwitchingPanel.getLayout());
        cl.show(calendarSwitchingPanel, "eventDetails");
    }

    /**
     * The listener for the buttons in the mainPanel to switch between the Calendar and Event Details
     */
    private class CalendarButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == selectCalendarButton) {
                selectCalendarButtonActionPerformed(e);
            }
            if (e.getSource() == selectEventDetailsButton) {
                selectEventDetailsButtonActionPerformed(e);
            }
        }
    }

    /**
     * Registers/Unregisters a user for the main event based on their current status
     */
    private class RegisterForEventButtonListener
            implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            try {
                if (participantList.contains(loggedInUser)) {
                    eventManager.deleteParticipant(loggedInUser);
                    eventManager.removeOrganizer(loggedInUser);
                    JOptionPane.showMessageDialog(null, "Successfully unregistered.");
                    registerForEventButton.setText(REGISTER);
                } else {
                    eventManager.createParticipant(loggedInUser);
                    eventManager.addOrganizer(loggedInUser);
                    JOptionPane.showMessageDialog(null, "Registration successful!");
                    registerForEventButton.setText(UNREGISTER);
                }
            } catch (DoesNotExistException e) {
                e.printStackTrace();
            }
        }
    }
    /*
    private class ClearEventButtonListener
            implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            try {
                eventManager.deleteEvent(loggedInUser);
                eventManager.setSelectedEvent(eventManager.createEvent(new Event(), loggedInUser));
                JFrame homeFrame = (JFrame)SwingUtilities.windowForComponent((Component)event.getSource());
                homeFrame.validate();
                homeFrame.repaint();
                // event.getSource().getParent().getParent().validate();
            } catch (PrivilegeInsufficientException e) {
                e.printStackTrace();
            } catch (DuplicateInsertionException e) {
                e.printStackTrace();
            } catch (DoesNotExistException e) {
                e.printStackTrace();
            }
        }
    }
    */
}
