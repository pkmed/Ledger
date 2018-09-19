package GUI.Windows;

import GUI.Controllers.BooksListController;

import javax.swing.*;
import java.awt.*;

public class BookCreationForm extends JFrame {
    private JLabel bookNameLabel = new JLabel("Book name:");
    private JTextField bookNameField = new JTextField();
    private JRadioButton incomeTypeBtn = new JRadioButton("Income book"), debtTypeBtn = new JRadioButton("Debt book");
    private JButton createBookBtn;

    public BookCreationForm(BooksListController booksListController, String btnFunc){
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(150,150,350,150);
        GridBagLayout gbLay = new GridBagLayout();
        GridBagConstraints gbConstr = new GridBagConstraints();
        setLayout(gbLay);
        createBookBtn = new JButton(btnFunc);
        createBookBtn.addActionListener(booksListController);

        gbConstr.insets.set(4,4,4,4);

        gbConstr.gridx = 0;
        gbConstr.gridy = 0;
        gbConstr.fill = GridBagConstraints.CENTER;
        gbConstr.gridwidth = 1;
        gbConstr.weightx = 0.15;
        gbConstr.weighty = 0.33;
        add(bookNameLabel, gbConstr);

        gbConstr.gridx = 1;
        gbConstr.gridy = 0;
        gbConstr.fill = GridBagConstraints.HORIZONTAL;
        gbConstr.gridwidth = 4;
        gbConstr.weightx = 0.85;
        gbConstr.weighty = 0.33;
        add(bookNameField, gbConstr);

        gbConstr.gridx = 0;
        gbConstr.gridy = 1;
        gbConstr.fill = GridBagConstraints.CENTER;
        gbConstr.gridwidth = 1;
        gbConstr.weightx = 0.15;
        gbConstr.weighty = 0.33;
        add(incomeTypeBtn, gbConstr);

        gbConstr.gridx = 1;
        gbConstr.gridy = 1;
        gbConstr.fill = GridBagConstraints.HORIZONTAL;
        gbConstr.gridwidth = 1;
        gbConstr.weightx = 0.85;
        gbConstr.weighty = 0.33;
        add(debtTypeBtn, gbConstr);

        gbConstr.gridx = 2;
        gbConstr.gridy = 1;
        gbConstr.fill = GridBagConstraints.CENTER;
        gbConstr.gridwidth = 1;
        gbConstr.weightx = 0.15;
        gbConstr.weighty = 0.33;
        add(createBookBtn, gbConstr);

        setVisible(true);
        setResizable(false);
    }

    public String[] getValues() {
        String[] values = new String[2];
        values[0] = bookNameField.getText();
        if(incomeTypeBtn.isSelected())
            values[1] = "income";
        else
            values[1] = "debt";
        return values;
    }
}
