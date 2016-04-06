/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI; 

import BackEnd.ManagerSystem.MainManager;
import BackEnd.ManagerSystem.ManagerExceptions.PrivilegeInsufficientException;
import BackEnd.UserSystem.Address;
import BackEnd.UserSystem.PhoneNumber;
import BackEnd.UserSystem.User;
import EMS_Database.DoesNotExistException;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Karina
 */
public class UserManagementPanel extends javax.swing.JPanel {
    private MainManager manager;
    private User selectedUser;
    private Address selectedUserAddress;
    private JList userList;
    private DesignDefault dd;
    private final String PHONE_NUMBER_FIELD = "(XXX) XXX - XXXX";
    private final String STREET_FIELD = "Street";
    private final String CITY_FIELD = "City";
    private final String STATE_FIELD = "State";
    private final String ZIP_CODE_FIELD = "Zip Code";
    private final String COUNTRY_FIELD = "Country";
    /**
     * Creates new form UserManagementPanel
     */
    public UserManagementPanel() {
        dd = DesignDefault.getInstance();
        initComponents();
        ChangeUserPanel.setLayout(new BorderLayout());
        manager = MainManager.getInstance();
        updateLabels();
        initUserList();
        this.setBackground(dd.getPanelBGColor());
    }
    
    public void initUserList()
    {
        Object[] tempUserList = new Object[manager.getUserManager().getUserList().size()];
        for (int i = 0; i < tempUserList.length; i++)
            tempUserList[i] = manager.getUserManager().getUserList().get(i);
        
        userList = new JList(tempUserList);
        
        userList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        userList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        userList.setVisibleRowCount(-1);
        userList.addListSelectionListener(new UserListSelectionListener());
        JScrollPane listScroller = new JScrollPane(userList);
        listScroller.setPreferredSize(new Dimension(250, 80));
        ChangeUserPanel.add(listScroller, BorderLayout.CENTER);
    }
    
    public void updateLabels()
    {   
        selectedUser = manager.getUserManager().getSelectedUser();
        selectedUserAddress = selectedUser.getAddress();
        
        currentUserLabel.setText("Currently Editing User: < " + selectedUser.getFirstName() + " " + selectedUser.getLastName() + " >");
        firstNameField.setText(selectedUser.getFirstName());
        lastNameField.setText(selectedUser.getLastName());
        emailField.setText(selectedUser.getEmailAddress());

        setFieldsToDefaultString();

        if (selectedUser.getAdminPrivilege()) {
            adminBox.setSelected(true);
        } else {
            adminBox.setSelected(false);
        }
        if (selectedUser.getEventCreationPrivilege()) {
            eventBox.setSelected(true);
        } else {
            eventBox.setSelected(false);
        }
    }

    public void setNonAdminView() {
        changeInfoButton.setVisible(false);
        changeUserButton.setVisible(false);
        adminBox.setVisible(false);
        eventBox.setVisible(false);
    }
    
    private PhoneNumber setPhoneNumberToSystemValue() {
        PhoneNumber tempPhoneNumber = new PhoneNumber();
        
        if (phoneNumberField.getText().equals(PHONE_NUMBER_FIELD))
            tempPhoneNumber = new PhoneNumber("");
        else
            tempPhoneNumber = new PhoneNumber(phoneNumberField.getText());            
        
        return tempPhoneNumber;
    }
    
    private Address setAddressToSystemValue() {
        Address tempAddress = new Address();
        
        if (streetField.getText().equals(STREET_FIELD))
            tempAddress.setStreet("");
        else
            tempAddress.setStreet(streetField.getText());
        
        if (stateField.getText().equals(STATE_FIELD))
            tempAddress.setState("");
        else
            tempAddress.setState(stateField.getText());
        
        if (cityField.getText().equals(CITY_FIELD))
            tempAddress.setCity("");
        else
            tempAddress.setCity(cityField.getText());
        
        if (zipcodeField.getText().equals(ZIP_CODE_FIELD))
            tempAddress.setZipCode("");
        else
            tempAddress.setZipCode(zipcodeField.getText());
        
        if (countryField.getText().equals(COUNTRY_FIELD))
            tempAddress.setCountry("");
        else
            tempAddress.setCountry(countryField.getText());
        
        return tempAddress;
    }
    
    private void setFieldsToDefaultString() {
        if (selectedUser.getPhoneNumber().toString().equals(""))
            phoneNumberField.setText(PHONE_NUMBER_FIELD);
        else
            phoneNumberField.setText(selectedUser.getPhoneNumber().toString());
        
        if (selectedUserAddress.getStreet().equals(""))
            streetField.setText(STREET_FIELD);
        else
            streetField.setText(selectedUserAddress.getStreet());
        
        if (selectedUserAddress.getState().equals(""))
            stateField.setText(STATE_FIELD);
        else
            stateField.setText(selectedUserAddress.getState());
        
        if (selectedUserAddress.getCity().equals(""))
            cityField.setText(CITY_FIELD);
        else
            cityField.setText(selectedUserAddress.getCity());
        
        if (selectedUserAddress.getZipCode().equals(""))
            zipcodeField.setText(ZIP_CODE_FIELD);
        else
            zipcodeField.setText(selectedUserAddress.getZipCode());
        
        if (selectedUserAddress.getCountry().equals(""))
            countryField.setText(selectedUserAddress.getCountry());
        else
            countryField.setText(selectedUserAddress.getCountry());
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        UserInfoPanelHolder = new javax.swing.JPanel();
        ChangeInfoPanel = new javax.swing.JPanel();
        firstNameLabel = new javax.swing.JLabel();
        lastNameLabel = new javax.swing.JLabel();
        emailLabel = new javax.swing.JLabel();
        phoneNumberLabel = new javax.swing.JLabel();
        addressLabel = new javax.swing.JLabel();
        firstNameField = new javax.swing.JTextField();
        lastNameField = new javax.swing.JTextField();
        emailField = new javax.swing.JTextField();
        phoneNumberField = new javax.swing.JTextField();
        streetField = new javax.swing.JTextField();
        passwordButton = new javax.swing.JButton();
        cityField = new javax.swing.JTextField();
        stateField = new javax.swing.JTextField();
        zipcodeField = new javax.swing.JTextField();
        countryField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        adminBox = new javax.swing.JCheckBox();
        eventBox = new javax.swing.JCheckBox();
        ChangeUserPanel = new javax.swing.JPanel();
        changeInfoButton = new javax.swing.JButton();
        changeUserButton = new javax.swing.JButton();
        currentUserLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(153, 204, 255));
        setPreferredSize(new java.awt.Dimension(640, 480));

        UserInfoPanelHolder.setBackground(new java.awt.Color(255, 255, 255));
        UserInfoPanelHolder.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        UserInfoPanelHolder.setLayout(new java.awt.CardLayout());

        ChangeInfoPanel.setBackground(new java.awt.Color(255, 255, 255));

        firstNameLabel.setFont(dd.getHeaderText());
        firstNameLabel.setText("First name: ");

        lastNameLabel.setFont(dd.getHeaderText());
        lastNameLabel.setText("Last name: ");

        emailLabel.setFont(dd.getHeaderText());
        emailLabel.setText("Email:");

        phoneNumberLabel.setFont(dd.getHeaderText());
        phoneNumberLabel.setText("Phone #:");

        addressLabel.setFont(dd.getHeaderText());
        addressLabel.setText("Address:");

        firstNameField.setText("First Name");

        lastNameField.setText("Last Name");

        emailField.setText("Email");

        phoneNumberField.setText("Phone Number");
        phoneNumberField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                phoneNumberFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                phoneNumberFieldFocusLost(evt);
            }
        });

        streetField.setText("Street");
        streetField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                streetFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                streetFieldFocusLost(evt);
            }
        });

        passwordButton.setFont(dd.getStandardText());
        passwordButton.setText("Change Password");
        passwordButton.setMinimumSize(dd.getBigButtonDimension());
        passwordButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordButtonActionPerformed(evt);
            }
        });

        cityField.setText("City");
        cityField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cityFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                cityFieldFocusLost(evt);
            }
        });

        stateField.setText("State");
        stateField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                stateFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                stateFieldFocusLost(evt);
            }
        });

        zipcodeField.setText("Zipcode");
        zipcodeField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                zipcodeFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                zipcodeFieldFocusLost(evt);
            }
        });

        countryField.setText("Country");
        countryField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                countryFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                countryFieldFocusLost(evt);
            }
        });

        jButton1.setFont(dd.getStandardText());
        jButton1.setText("Save Changes");
        jButton1.setMinimumSize(dd.getBigButtonDimension());
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveChangesButtonPerformed(evt);
            }
        });

        adminBox.setText("Admin Privileges");
        adminBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminBoxActionPerformed(evt);
            }
        });

        eventBox.setText("Event Creation Privileges");

        javax.swing.GroupLayout ChangeInfoPanelLayout = new javax.swing.GroupLayout(ChangeInfoPanel);
        ChangeInfoPanel.setLayout(ChangeInfoPanelLayout);
        ChangeInfoPanelLayout.setHorizontalGroup(
            ChangeInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ChangeInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ChangeInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lastNameLabel)
                    .addComponent(emailLabel)
                    .addComponent(firstNameLabel)
                    .addComponent(phoneNumberLabel)
                    .addComponent(addressLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ChangeInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(ChangeInfoPanelLayout.createSequentialGroup()
                        .addComponent(passwordButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addGroup(ChangeInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(streetField, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(phoneNumberField, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(firstNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lastNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cityField, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(stateField, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(zipcodeField, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(countryField, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(ChangeInfoPanelLayout.createSequentialGroup()
                            .addComponent(adminBox)
                            .addGap(18, 18, 18)
                            .addComponent(eventBox))))
                .addContainerGap(143, Short.MAX_VALUE))
        );
        ChangeInfoPanelLayout.setVerticalGroup(
            ChangeInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ChangeInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ChangeInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(firstNameLabel)
                    .addComponent(firstNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ChangeInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lastNameLabel)
                    .addComponent(lastNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ChangeInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(emailLabel)
                    .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ChangeInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(phoneNumberLabel)
                    .addComponent(phoneNumberField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ChangeInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addressLabel)
                    .addComponent(streetField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cityField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(zipcodeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(countryField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(ChangeInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(adminBox)
                    .addComponent(eventBox))
                .addGap(18, 18, 18)
                .addGroup(ChangeInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(passwordButton))
                .addGap(33, 33, 33))
        );

        UserInfoPanelHolder.add(ChangeInfoPanel, "changeInfo");

        javax.swing.GroupLayout ChangeUserPanelLayout = new javax.swing.GroupLayout(ChangeUserPanel);
        ChangeUserPanel.setLayout(ChangeUserPanelLayout);
        ChangeUserPanelLayout.setHorizontalGroup(
            ChangeUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 614, Short.MAX_VALUE)
        );
        ChangeUserPanelLayout.setVerticalGroup(
            ChangeUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 344, Short.MAX_VALUE)
        );

        UserInfoPanelHolder.add(ChangeUserPanel, "changeUser");

        changeInfoButton.setFont(dd.getStandardText());
        changeInfoButton.setText("Edit Account Info");
        changeInfoButton.setMinimumSize(dd.getBigButtonDimension());
        changeInfoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeInfoButtonActionPerformed(evt);
            }
        });

        changeUserButton.setFont(dd.getStandardText());
        changeUserButton.setText("Select Another User");
        changeUserButton.setMinimumSize(dd.getBigButtonDimension());
        changeUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeUserButtonActionPerformed(evt);
            }
        });

        currentUserLabel.setFont(dd.getStandardText());
        currentUserLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        currentUserLabel.setText("Current User: <UserName>");

        jLabel3.setFont(dd.getHeaderText());
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("User Management");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
                    .addComponent(UserInfoPanelHolder, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(changeInfoButton)
                        .addGap(18, 18, 18)
                        .addComponent(changeUserButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 224, Short.MAX_VALUE)
                        .addComponent(currentUserLabel)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(changeInfoButton)
                    .addComponent(changeUserButton)
                    .addComponent(currentUserLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(UserInfoPanelHolder, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void passwordButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordButtonActionPerformed
        JOptionPane.showInputDialog("Please enter your new password:");
    }//GEN-LAST:event_passwordButtonActionPerformed

    private void changeInfoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeInfoButtonActionPerformed
        CardLayout cl = (CardLayout)(UserInfoPanelHolder.getLayout());
        cl.show(UserInfoPanelHolder, "changeInfo");
    }//GEN-LAST:event_changeInfoButtonActionPerformed

    private void changeUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeUserButtonActionPerformed
        CardLayout cl = (CardLayout)(UserInfoPanelHolder.getLayout());
        cl.show(UserInfoPanelHolder, "changeUser");
    }//GEN-LAST:event_changeUserButtonActionPerformed

    private void saveChangesButtonPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveChangesButtonPerformed
        // TODO add your handling code here:
        int choice = JOptionPane.showConfirmDialog(null, "Do you want to save these changes?");
        if(choice == JOptionPane.YES_OPTION)
        {
            try 
            {
                manager.getUserManager().editFirstName(firstNameField.getText());
                manager.getUserManager().editLastName(lastNameField.getText());
                manager.getUserManager().editEmailAddress(emailField.getText());
                manager.getUserManager().editPhoneNumber(setPhoneNumberToSystemValue());
                manager.getUserManager().editAddress(setAddressToSystemValue());
                if (manager.getLogInManager().getLoggedInUser().getAdminPrivilege()) {
                    if (adminBox.isSelected()) {
                        manager.getUserManager().editAdminPrivilege(true);
                    } else {
                        manager.getUserManager().editAdminPrivilege(false);
                    }
                    if (eventBox.isSelected()) {
                        manager.getUserManager().editEventCreationPrivilege(true);
                    } else {
                        manager.getUserManager().editEventCreationPrivilege(false);
                    }
                }
            } catch (PrivilegeInsufficientException ex) 
            {
                Logger.getLogger(UserManagementPanel.class.getName()).log(Level.SEVERE, null, ex);
            } 
            catch (DoesNotExistException ex) 
            {
                Logger.getLogger(UserManagementPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            updateLabels();
            
        }
    }//GEN-LAST:event_saveChangesButtonPerformed

    private void adminBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminBoxActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_adminBoxActionPerformed

private void phoneNumberFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_phoneNumberFieldFocusGained
        if (phoneNumberField.getText().equals(PHONE_NUMBER_FIELD)) {
            phoneNumberField.setText("");
        }
}//GEN-LAST:event_phoneNumberFieldFocusGained

private void phoneNumberFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_phoneNumberFieldFocusLost
        if (phoneNumberField.getText().equals("") || phoneNumberField.getText().equals(PHONE_NUMBER_FIELD)) {
            phoneNumberField.setText(PHONE_NUMBER_FIELD);
        }
}//GEN-LAST:event_phoneNumberFieldFocusLost

private void streetFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_streetFieldFocusGained
// TODO add your handling code here:
        if (streetField.getText().equals(STREET_FIELD)) {
            streetField.setText("");
        }
}//GEN-LAST:event_streetFieldFocusGained

private void streetFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_streetFieldFocusLost
// TODO add your handling code here:
        if (streetField.getText().equals("") || streetField.getText().equals(STREET_FIELD)) {
            streetField.setText(STREET_FIELD);
        }
}//GEN-LAST:event_streetFieldFocusLost

private void cityFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cityFieldFocusGained
// TODO add your handling code here:
        if (cityField.getText().equals(CITY_FIELD)) {
            cityField.setText("");
        }
}//GEN-LAST:event_cityFieldFocusGained

private void cityFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cityFieldFocusLost
// TODO add your handling code here:
        if (cityField.getText().equals("") || cityField.getText().equals(CITY_FIELD)) {
            cityField.setText(CITY_FIELD);
        }
}//GEN-LAST:event_cityFieldFocusLost

private void stateFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_stateFieldFocusGained
// TODO add your handling code here:
        if (stateField.getText().equals(STATE_FIELD)) {
            stateField.setText("");
        }
}//GEN-LAST:event_stateFieldFocusGained

private void stateFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_stateFieldFocusLost
// TODO add your handling code here:
        if (stateField.getText().equals("") || stateField.getText().equals(STATE_FIELD)) {
            stateField.setText(STATE_FIELD);
        }
}//GEN-LAST:event_stateFieldFocusLost

private void zipcodeFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_zipcodeFieldFocusGained
// TODO add your handling code here:
        if (zipcodeField.getText().equals(ZIP_CODE_FIELD)) {
            zipcodeField.setText("");
        }
}//GEN-LAST:event_zipcodeFieldFocusGained

private void zipcodeFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_zipcodeFieldFocusLost
// TODO add your handling code here:
        if (zipcodeField.getText().equals("") || zipcodeField.getText().equals(ZIP_CODE_FIELD)) {
            zipcodeField.setText(ZIP_CODE_FIELD);
        }
}//GEN-LAST:event_zipcodeFieldFocusLost

private void countryFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_countryFieldFocusGained
// TODO add your handling code here:
        if (countryField.getText().equals(COUNTRY_FIELD)) {
            countryField.setText("");
        }
}//GEN-LAST:event_countryFieldFocusGained

private void countryFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_countryFieldFocusLost
// TODO add your handling code here:
        if (countryField.getText().equals("") || countryField.getText().equals(COUNTRY_FIELD)) {
            countryField.setText(COUNTRY_FIELD);
        }
}//GEN-LAST:event_countryFieldFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ChangeInfoPanel;
    private javax.swing.JPanel ChangeUserPanel;
    private javax.swing.JPanel UserInfoPanelHolder;
    private javax.swing.JLabel addressLabel;
    private javax.swing.JCheckBox adminBox;
    private javax.swing.JButton changeInfoButton;
    private javax.swing.JButton changeUserButton;
    private javax.swing.JTextField cityField;
    private javax.swing.JTextField countryField;
    private javax.swing.JLabel currentUserLabel;
    private javax.swing.JTextField emailField;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JCheckBox eventBox;
    private javax.swing.JTextField firstNameField;
    private javax.swing.JLabel firstNameLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField lastNameField;
    private javax.swing.JLabel lastNameLabel;
    private javax.swing.JButton passwordButton;
    private javax.swing.JTextField phoneNumberField;
    private javax.swing.JLabel phoneNumberLabel;
    private javax.swing.JTextField stateField;
    private javax.swing.JTextField streetField;
    private javax.swing.JTextField zipcodeField;
    // End of variables declaration//GEN-END:variables

    class UserListSelectionListener implements ListSelectionListener {
    public void valueChanged(ListSelectionEvent e) {
        manager.getUserManager().setSelectedUser((User)userList.getSelectedValue());
        updateLabels();
    }
    }
}
