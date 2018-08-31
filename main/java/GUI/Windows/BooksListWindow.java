package GUI.Windows;

import GUI.Constants.BooksListCommands;
import GUI.Controllers.BooksListController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BooksListWindow extends JFrame {
    //TODO: implement own table model
    //TODO: remake getSelectedEntry
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> booksList = new JList<>(listModel);

    private DefaultTableModel tableModel = new DefaultTableModel();
    private JTable booksTable = new JTable(tableModel);

    private JButton addBook = new JButton(BooksListCommands.BTN_FUNC_ADD_BOOK),
            deleteBook = new JButton(BooksListCommands.BTN_FUNC_DELETE_BOOK),
            exportBook = new JButton(BooksListCommands.BTN_FUNC_EXPORT_BOOK),
            editBook = new JButton(BooksListCommands.BTN_FUNC_OPEN_BOOK);
    private BooksListController controller = new BooksListController();

    public BooksListWindow(){
        tableModel.addColumn("Book name");
        tableModel.addColumn("Type");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(100,100,500,600);
        GridBagLayout gbLay = new GridBagLayout();
        GridBagConstraints gbConstr = new GridBagConstraints();
        setLayout(gbLay);
        addBook.addActionListener(controller);
        deleteBook.addActionListener(controller);
        exportBook.addActionListener(controller);
        editBook.addActionListener(controller);
        booksTable.addMouseListener(controller);
        

        gbConstr.insets.set(4,4,4,4);

        gbConstr.gridx = 0;
        gbConstr.gridy = 0;
        gbConstr.fill = GridBagConstraints.BOTH;
        gbConstr.gridwidth = 5;
        gbConstr.gridheight = 10;
        gbConstr.weightx = 0.85;
        gbConstr.weighty = 1;
        add(new JScrollPane(booksTable), gbConstr);

        gbConstr.fill = GridBagConstraints.CENTER;
        gbConstr.gridx = 5;
        gbConstr.gridy = 0;
        gbConstr.gridwidth = 1;
        gbConstr.gridheight = 1;
        gbConstr.weightx = 0.15;
        gbConstr.weighty = 0.05;
        add(addBook, gbConstr);

        gbConstr.gridy = 1;
        add(deleteBook, gbConstr);

        gbConstr.gridy = 2;
        add(exportBook, gbConstr);

        gbConstr.gridy = 3;
        add(editBook, gbConstr);

        gbConstr.gridy = 4;
        gbConstr.gridheight = 6;
        gbConstr.weighty = 0.8;
        add(new JPanel(), gbConstr);

        setVisible(true);
        setResizable(false);
    }

    public void refreshList(String[] books) {
        //listModel.clear();
        while(tableModel.getRowCount()>0){
            tableModel.removeRow(0);
        }
        for(String book:books){
            //listModel.addElement(book);
            tableModel.addRow(book.split(";"));
        }
    }
    public String getSelectedBook(){
        return tableModel.getValueAt(booksTable.getSelectedRow(), 0).toString();
    }
}