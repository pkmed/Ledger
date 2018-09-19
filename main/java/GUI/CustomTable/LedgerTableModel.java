package GUI.CustomTable;

import Logic.InfoModels.DebtEntry;
import Logic.InfoModels.Entry;
import Logic.InfoModels.IncomeEntry;

import javax.swing.event.TableModelEvent;
import javax.swing.table.*;
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
        int i;
        for (i = 0; i < row.length; i++) {
            setValueAt(row[i], currentRowNumber, i);
        }
        if(row.length<colCount){
            while(i<colCount) {
                setValueAt("", currentRowNumber, i);
                i++;
            }
        }
        data.add(currentRowNumber, singleRow);
        newRowsAdded(new TableModelEvent(this,currentRowNumber,currentRowNumber,-1,TableModelEvent.INSERT));
        currentRowNumber++;
    }
    public void addRow(Entry entry){
        singleRow = new ArrayList<>();
        if(entry instanceof IncomeEntry) {
            setValueAt(entry.getLabel(), currentRowNumber, 0);
            setValueAt(entry.getAmount(), currentRowNumber, 1);
            setValueAt(entry.getDate(), currentRowNumber, 2);
        } else if(entry instanceof DebtEntry){
            setValueAt(entry.getLabel(),currentRowNumber,0);
            setValueAt(entry.getAmount(),currentRowNumber,1);
            setValueAt(entry.getDate(),currentRowNumber,2);
            setValueAt(((DebtEntry)entry).isRepaid(),currentRowNumber,3);
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