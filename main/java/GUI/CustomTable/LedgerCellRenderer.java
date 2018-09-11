package GUI.CustomTable;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class LedgerCellRenderer extends JTextArea implements TableCellRenderer {
    public LedgerCellRenderer(){
        setLineWrap(true);
        setWrapStyleWord(true);
    }
    //TODO: cell alignment
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText(value.toString());
        setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height);
        if (table.getRowHeight(row) != getPreferredSize().height) {
            table.setRowHeight(row, getPreferredSize().height);
        }
        if(isSelected){
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        } else {
            setBackground(table.getBackground());
            setForeground(table.getForeground());
        }
        return this;
    }
}