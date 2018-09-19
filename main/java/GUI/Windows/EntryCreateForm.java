package GUI.Windows;

import GUI.Constants.BookOverviewCommands;
import GUI.Controllers.BookOverviewController;
import Logic.BookOverviewLogic;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EntryCreateForm extends JFrame {
    private JLabel entryLabel = new JLabel("Entry:"), amountLabel = new JLabel("Amount:");
    private JTextField entryField = new JTextField(), amountField = new JTextField();
    private JRadioButton repaidBtn, notRepaidBtn;
    private JButton createEntryBtn;

    public EntryCreateForm(BookOverviewController bookOverviewController, String btnFunc, String bookType){
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(150,150,350,150);
        GridBagLayout gbLay = new GridBagLayout();
        GridBagConstraints gbConstr = new GridBagConstraints();
        setLayout(gbLay);
        createEntryBtn = new JButton(btnFunc);
        createEntryBtn.addActionListener(bookOverviewController);

        gbConstr.insets.set(4,4,4,4);

        gbConstr.gridx = 0;
        gbConstr.gridy = 0;
        gbConstr.fill = GridBagConstraints.CENTER;
        gbConstr.gridwidth = 1;
        gbConstr.weightx = 0.15;
        gbConstr.weighty = 0.33;
        add(entryLabel, gbConstr);

        gbConstr.gridx = 1;
        gbConstr.gridy = 0;
        gbConstr.fill = GridBagConstraints.HORIZONTAL;
        gbConstr.gridwidth = 4;
        gbConstr.weightx = 0.85;
        gbConstr.weighty = 0.33;
        add(entryField, gbConstr);

        gbConstr.gridx = 0;
        gbConstr.gridy = 1;
        gbConstr.fill = GridBagConstraints.CENTER;
        gbConstr.gridwidth = 1;
        gbConstr.weightx = 0.15;
        gbConstr.weighty = 0.33;
        add(amountLabel, gbConstr);

        gbConstr.gridx = 1;
        gbConstr.gridy = 1;
        gbConstr.fill = GridBagConstraints.HORIZONTAL;
        gbConstr.gridwidth = 4;
        gbConstr.weightx = 0.85;
        gbConstr.weighty = 0.33;
        add(amountField, gbConstr);

        if(bookType.equals("income")){
        } else if(bookType.equals("debt") && !btnFunc.equals(BookOverviewCommands.BTN_FUNC_CREATE_ENTRY)){
            repaidBtn = new JRadioButton("Repaid");
            repaidBtn.addActionListener(bookOverviewController);
            notRepaidBtn = new JRadioButton("Not repaid");
            notRepaidBtn.addActionListener(bookOverviewController);
            ButtonGroup btnGroup = new ButtonGroup();
            btnGroup.add(repaidBtn);
            btnGroup.add(notRepaidBtn);

            gbConstr.gridx = 0;
            gbConstr.gridy = 2;
            gbConstr.fill = GridBagConstraints.HORIZONTAL;
            gbConstr.gridwidth = 1;
            gbConstr.weightx = 0.85;
            gbConstr.weighty = 0.33;
            add(repaidBtn, gbConstr);

            gbConstr.gridx = 1;
            gbConstr.gridy = 2;
            gbConstr.fill = GridBagConstraints.HORIZONTAL;
            gbConstr.gridwidth = 1;
            gbConstr.weightx = 0.85;
            gbConstr.weighty = 0.33;
            add(notRepaidBtn, gbConstr);

            if (BookOverviewLogic.getSelectedEntry().split(";")[3].equals("true")) {
                repaidBtn.setSelected(true);
            } else if (BookOverviewLogic.getSelectedEntry().split(";")[3].equals("false")) {
                notRepaidBtn.setSelected(true);
            }
        }

        gbConstr.gridx = 2;
        gbConstr.gridy = 2;
        gbConstr.fill = GridBagConstraints.CENTER;
        gbConstr.gridwidth = 1;
        gbConstr.weightx = 0.15;
        gbConstr.weighty = 0.33;
        add(createEntryBtn, gbConstr);

        setVisible(true);
        setResizable(false);
    }

    public String[] getValues() {
        String[] values;
        if(repaidBtn != null){
            values  = new String[4];
        } else {
            values = new String[3];
        }
        values[0] = entryField.getText();
        values[1] = amountField.getText();
        Date date = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        values[2] = formatForDateNow.format(date);
        if(repaidBtn != null) {
            if (repaidBtn.isSelected()) {
                values[3] = "true";
            } else if (notRepaidBtn.isSelected()) {
                values[3] = "false";
            }
        }
        return values;
    }

    public void setAmount(String amount) {
        amountField.setText(amount);
    }

    public void setEntry(String entry) {
        entryField.setText(entry);
    }
}
