package GUI.CustomTable;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.*;

public class LedgerTableModel implements TableModel {

    private Set<TableModelListener> listeners = new HashSet<>();
    private List<String> data;

    public LedgerTableModel(List<String> data){
        this.data = data;
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        listeners.remove(l);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex){
            case 0:
                return "Book name";
            case 1:
                return "Type";
        }
        return "";
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            String row = data.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return row.split(";")[0];
                case 1:
                    return row.split(";")[1];
            }
            return "";
        } catch(ArrayIndexOutOfBoundsException exc){
            return "";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    }
}
