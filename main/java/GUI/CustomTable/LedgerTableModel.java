package GUI.CustomTable;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.util.ArrayList;

public class LedgerTableModel extends AbstractTableModel {
    private TableColumnModel columnModel = new DefaultTableColumnModel();
    private ArrayList<ArrayList<Object>> data = new ArrayList<>();
    private int currentRowNumber = 0;
    private int colCount=0;
    private ArrayList<Object> singleRow;
    public LedgerTableModel(String[] columns){
        for(int i=0;i<columns.length;i++){
            columnModel.addColumn(new TableColumn(i));
            columnModel.getColumn(i).setHeaderValue(columns[i]);
            columnModel.getColumn(i).addPropertyChangeListener(new TableColumnPropertyChangeHandler());
            colCount++;
        }
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
    /*public void addColumn(String colName){
        columnModel.addColumn(new TableColumn(colCount));
        columnModel.getColumn(colCount).setHeaderValue(colName);
        columnModel.getColumn(colCount).addPropertyChangeListener(new TableColumnPropertyChangeHandler());
        fireTableChanged(new TableModelEvent(this,0,currentRowNumber,colCount,TableModelEvent.UPDATE));
        colCount++;
    }*/
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
        return (String)columnModel.getColumn(col).getHeaderValue();
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