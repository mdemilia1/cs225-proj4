package GUI;


import BackEnd.ManagerSystem.UserManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import BackEnd.ManagerSystem.MainManager;
import BackEnd.UserSystem.Participant;
import EMS_Database.DoesNotExistException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by michael on 4/19/2016.
 */

public class AdministrationManagementPanel extends javax.swing.JPanel implements ActionListener {

    private Object[][] data = new Object[10][5];
    private String[] columnNames = {"First Name", "Last Name", "User Id", "Make Admin"};
    private MainManager manage;
    private UserManager user;
    private AdminPanelTable ut;
    private JTable userTable;
    private ArrayList<Participant> userList;
    private JScrollPane scrollBar;
    private JButton submit;


    public AdministrationManagementPanel() {
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        manage = new MainManager().getInstance();
        user = manage.getUserManager();
        userList = user.getUserList();
        try {
            setTable();
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        }
        scrollBar = new JScrollPane(userTable);
        //scrollBar.setPreferredSize(new Dimension(50, 50));
        scrollBar.setVisible(true);
        submit = new JButton("Submit");
        submit.setPreferredSize(new Dimension(30, 30));
        submit.addActionListener(this);
        this.add(scrollBar);
        this.add(submit);
    }

    public void actionPerformed(ActionEvent e)
    {
        for(int i =0; i < ut.getRowCount(); i++)
        {
            if((boolean) ut.getValueAt(i, 2) == true)
            {
                System.out.println("row: " + i + " is checked");
                //update the database on click
            }
        }
    }

    public void setTable() throws DoesNotExistException {
        ut = new AdminPanelTable();
        for (int j = 0; j < userList.size(); j++)
        {
                ut.addRow(new Object[]{userList.get(j).getFirstName(), userList.get(j).getLastName(), false});

        }
        userTable = new JTable(ut);
        userTable.setPreferredScrollableViewportSize(new Dimension(50, 50));
        userTable.setFillsViewportHeight(true);
    }


    public class AdminPanelTable extends DefaultTableModel
    {
        public AdminPanelTable() {
            super(new String[]{"First Name", "Last Name", "Make Admin"}, 0);
        }

        public Class<?> getColumnClass(int columnIndex) {
            Class clazz = String.class;
            switch (columnIndex) {
                case 2:
                    clazz = Boolean.class;
            }
            return clazz;
        }

        public boolean isCellEditable(int row, int column) {
            return column == 2;
        }

        public void setValueAt(Object aValue, int row, int column) {
            if (aValue instanceof Boolean && column == 2) {
                Vector rowData = (Vector) getDataVector().get(row);
                rowData.set(2, (boolean) aValue);
                fireTableCellUpdated(row, column);
                if((boolean) aValue)
                    System.out.println("checked");
            }
        }
    }
}