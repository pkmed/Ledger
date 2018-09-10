package GUI.CustomTable;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class LedgerTableModel extends AbstractTableModel {
    //TODO: copy methods from DefaultTableModel
    private String[] columnNames;
    private ArrayList<ArrayList<Object>> data = new ArrayList<>();
    private int currentRowNumber = 0;
    private int colCount;
    private ArrayList<Object> singleRow;
    public LedgerTableModel(String[] columns){
        columnNames = columns;
        colCount = columns.length;
    }
    public void addRow(String[] row){
        singleRow = new ArrayList<>();
        for (int i = 0; i < row.length; i++) {
            setValueAt(row[i],currentRowNumber,i);
        }
        data.add(currentRowNumber, singleRow);
        newRowsAdded(new TableModelEvent(this,currentRowNumber,currentRowNumber,-1,TableModelEvent.INSERT));
        currentRowNumber++;
    }
    private void newRowsAdded(TableModelEvent event){
        fireTableChanged(event);
    }
    public void removeRow(int i) {
        data.remove(i);
        currentRowNumber--;
    }
    public void clearValues(){
        data.clear();
        currentRowNumber=0;
    }
    public int getColumnCount() {
        return colCount;
    }

    public int getRowCount() {
        return data.size();
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data.get(row).get(col);
    }
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
    public boolean isCellEditable(int row, int col) {
        return false;
    }
    public void setValueAt(Object value, int row, int col) {
        singleRow.add(col,value);
        fireTableCellUpdated(row, col);
    }
}