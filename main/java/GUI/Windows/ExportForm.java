package GUI.Windows;

import GUI.Constants.BooksListCommands;
import GUI.Controllers.BooksListController;

import javax.swing.*;
import java.awt.*;

public class ExportForm extends JFrame {
    private JLabel directoryLabel = new JLabel("Directory:"), typeLabel = new JLabel("Type:");
    private JTextField directoryField = new JTextField();
    private JFileChooser directoryChooser = new JFileChooser();
    private JComboBox exportTypesChooser = new JComboBox(new String[]{".xls", ".xlsx", ".csv"});
    private JButton exportBtn, dirChooseBtn = new JButton(BooksListCommands.BTN_FUNC_EXPORT_FORM_DIR_CHOOSE);

    public ExportForm(BooksListController booksListController, String btnFunc){
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(150,150,350,150);
        GridBagLayout gbLay = new GridBagLayout();
        GridBagConstraints gbConstr = new GridBagConstraints();
        setLayout(gbLay);
        exportBtn = new JButton(btnFunc);
        exportBtn.addActionListener(booksListController);
        dirChooseBtn.addActionListener(booksListController);
        directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        directoryChooser.setAcceptAllFileFilterUsed(false);

        gbConstr.insets.set(4,4,4,4);

        gbConstr.gridx = 0;
        gbConstr.gridy = 0;
        gbConstr.fill = GridBagConstraints.CENTER;
        gbConstr.gridwidth = 1;
        gbConstr.weightx = 0.15;
        gbConstr.weighty = 0.33;
        add(directoryLabel, gbConstr);

        gbConstr.gridx = 1;
        gbConstr.gridy = 0;
        gbConstr.fill = GridBagConstraints.HORIZONTAL;
        gbConstr.gridwidth = 3;
        gbConstr.weightx = 0.70;
        gbConstr.weighty = 0.33;
        add(directoryField, gbConstr);

        gbConstr.gridx = 4;
        gbConstr.gridy = 0;
        gbConstr.fill = GridBagConstraints.CENTER;
        gbConstr.gridwidth = 1;
        gbConstr.weightx = 0.15;
        gbConstr.weighty = 0.33;
        add(dirChooseBtn, gbConstr);

        gbConstr.gridx = 0;
        gbConstr.gridy = 1;
        gbConstr.fill = GridBagConstraints.CENTER;
        gbConstr.gridwidth = 1;
        gbConstr.weightx = 0.15;
        gbConstr.weighty = 0.33;
        add(typeLabel, gbConstr);

        gbConstr.gridx = 1;
        gbConstr.gridy = 1;
        gbConstr.fill = GridBagConstraints.HORIZONTAL;
        gbConstr.gridwidth = 3;
        gbConstr.weightx = 0.70;
        gbConstr.weighty = 0.33;
        add(exportTypesChooser, gbConstr);

        gbConstr.gridx = 4;
        gbConstr.gridy = 1;
        gbConstr.fill = GridBagConstraints.CENTER;
        gbConstr.gridwidth = 1;
        gbConstr.weightx = 0.15;
        gbConstr.weighty = 0.33;
        add(exportBtn, gbConstr);

        setVisible(true);
        setResizable(false);
    }

    public void showDirChooser(){
        if(directoryChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
            directoryField.setText(directoryChooser.getSelectedFile().getPath());
    }
    public String getSavePath(){
        return directoryField.getText();
    }
    public String getExportType(){
        return exportTypesChooser.getSelectedItem().toString();
    }
}
