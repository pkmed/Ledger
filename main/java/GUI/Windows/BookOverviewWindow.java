package GUI.Windows;

import GUI.Constants.BookOverviewCommands;
import GUI.Controllers.BookOverviewController;
import GUI.CustomTable.LedgerCellRenderer;
import GUI.CustomTable.LedgerTableModel;

import javax.swing.*;

import java.awt.*;

public class BookOverviewWindow extends JFrame {
    private LedgerTableModel ledgerModel = new LedgerTableModel(new String[]{"Entry","Amount","Date"});
    private JTable entriesTable = new JTable(ledgerModel);

    private JButton addEntry = new JButton(BookOverviewCommands.BTN_FUNC_ADD_ENTRY),
            removeEntry = new JButton(BookOverviewCommands.BTN_FUNC_REMOVE_ENTRY),
            editEntry = new JButton(BookOverviewCommands.BTN_FUNC_EDIT_ENTRY),
            backToList = new JButton(BookOverviewCommands.BTN_FUNC_BACK_TO_LIST);
    private BookOverviewController controller = new BookOverviewController();

    public BookOverviewWindow(String openedBookName){
        entriesTable.getColumnModel().getColumn(0).setCellRenderer(new LedgerCellRenderer());
        entriesTable.getColumnModel().getColumn(1).setCellRenderer(new LedgerCellRenderer());
        entriesTable.getColumnModel().getColumn(2).setCellRenderer(new LedgerCellRenderer());
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
        this.addWindowListener(controller);
        setName(openedBookName);

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
        setResizable(true);
    }

    public void refreshEntries(String[] entries) {
        ledgerModel.clearValues();
        for(String entry:entries){
            ledgerModel.addRow(entry.split(";"));
        }
    }
    public String getSelectedEntry(){
        String row="";
        for(int i = 0; i<ledgerModel.getColumnCount(); i++){
            row+=ledgerModel.getValueAt(entriesTable.getSelectedRow(), i)+";";
        }
        return row;
    }
    public int getSelectedEntryId(){
        return entriesTable.getSelectedRow();
    }
}
