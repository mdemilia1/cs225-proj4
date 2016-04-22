package GUI;


import BackEnd.ManagerSystem.UserManager;
import javax.swing.JTable;
import javax.swing.JCheckBox;
import javax.swing.table.DefaultTableModel;
import BackEnd.ManagerSystem.MainManager;
import BackEnd.UserSystem.Participant;
import EMS_Database.DoesNotExistException;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by michael on 4/19/2016.
 */

public class AdministrationManagementPanel extends javax.swing.JPanel {

    private Object[][] data = new Object[10][5];
    private String[] columnNames = {"First Name", "Last Name", "User Id", "Make Admin"};
    private MainManager manage;
    private UserManager user;
    private AdminPanelTable ut;
    private JTable userTable;
    private ArrayList<Participant> userList;

    private JCheckBox cb;

    //asdfas

    public AdministrationManagementPanel() {
        this.setLayout(new BorderLayout());
        manage = new MainManager().getInstance();
        user = manage.getUserManager();
        userList = user.getUserList();
        try {
            setTable();
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        }
        this.add(userTable, BorderLayout.CENTER);
    }

    public void setTable() throws DoesNotExistException {
        ut = new AdminPanelTable();
        for (int j = 0; j < userList.size(); j++)
        {
                ut.addRow(new Object[]{userList.get(j).getFirstName(), userList.get(j).getLastName(), false});

        }
        userTable = new JTable(ut);
       /* for(int userID : users.getUsersTable().currentUIDList("USERS")) {
            if (users.getUsersTable().getLevel(userID) == 0)
            {
                int i = 0;
                data[j][i] = users.getUsersTable().getFirstName(userID);
                i++;
                data[j][i] = users.getUsersTable().getLastName(userID);
                i++;
                data[j][i] = userID;
                i++;
                data[j][i] = cb = new JCheckBox("Make Admin");
                j++;
            }*/


        //userTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        //userTable.setRowHeight(50);

    }


    public class AdminPanelTable extends DefaultTableModel {
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

            }
        }
    }
}