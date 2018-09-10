package GUI.CustomTable;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class LedgerCellEditor extends JTextArea implements TableCellRenderer {
    public LedgerCellEditor(){
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
        return this;
    }
}