package GUI.Windows;

import GUI.Constants.BookOverviewCommands;
import GUI.Controllers.BookOverviewController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class BookOverviewWindow extends JFrame {
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> entriesList = new JList<>(listModel);

    //TODO: remake getSelectedEntry
    private DefaultTableModel tableModel = new DefaultTableModel();
    private JTable entriesTable = new JTable(tableModel);

    private JButton addEntry = new JButton(BookOverviewCommands.BTN_FUNC_ADD_ENTRY),
            removeEntry = new JButton(BookOverviewCommands.BTN_FUNC_REMOVE_ENTRY),
            editEntry = new JButton(BookOverviewCommands.BTN_FUNC_EDIT_ENTRY),
            backToList = new JButton(BookOverviewCommands.BTN_FUNC_BACK_TO_LIST);
    private BookOverviewController controller = new BookOverviewController();

    public BookOverviewWindow(String openedBookName){
        tableModel.addColumn("Entry");
        tableModel.addColumn("Amount");
        tableModel.addColumn("Date");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(100,100,500,600);
        GridBagLayout gbLay = new GridBagLayout();
        GridBagConstraints gbConstr = new GridBagConstraints();
        setLayout(gbLay);
        addEntry.addActionListener(controller);
        removeEntry.addActionListener(controller);
        editEntry.addActionListener(controller);
        backToList.addActionListener(controller);
        entriesTable.addMouseListener(controller);

        gbConstr.insets.set(4,4,4,4);

        gbConstr.gridx = 0;
        gbConstr.gridy = 0;
        gbConstr.fill = GridBagConstraints.BOTH;
        gbConstr.gridwidth = 5;
        gbConstr.gridheight = 10;
        gbConstr.weightx = 0.85;
        gbConstr.weighty = 1;
        add(new JScrollPane(entriesTable), gbConstr);

        gbConstr.fill = GridBagConstraints.CENTER;
        gbConstr.gridx = 5;
        gbConstr.gridy = 0;
        gbConstr.gridwidth = 1;
        gbConstr.gridheight = 1;
        gbConstr.weightx = 0.15;
        gbConstr.weighty = 0.05;
        add(addEntry, gbConstr);

        gbConstr.gridy = 1;
        add(removeEntry, gbConstr);

        gbConstr.gridy = 2;
        add(editEntry, gbConstr);

        gbConstr.gridy = 3;
        add(backToList, gbConstr);

        gbConstr.gridy = 4;
        gbConstr.gridheight = 5;
        gbConstr.weighty = 0.8;
        add(new JPanel(), gbConstr);

        setVisible(true);
        setResizable(false);
    }

    public void refreshEntries(String[] entries) {
        //listModel.clear();
        while(tableModel.getRowCount()>0){
            tableModel.removeRow(0);
        }
        for(String entry:entries){
            //listModel.addElement(entry);
            tableModel.addRow(entry.split(";"));
        }
    }
    public String getSelectedEntry(){
        String row="";
        for(int i = 0; i<tableModel.getColumnCount(); i++){
            row+=tableModel.getValueAt(entriesTable.getSelectedRow(), i)+";";
        }
        return row;
    }
}
