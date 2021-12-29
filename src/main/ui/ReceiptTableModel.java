package ui;

import model.Expenses;
import model.Receipt;

import javax.swing.table.AbstractTableModel;

// represents table model for receipts (expense list)
public class ReceiptTableModel extends AbstractTableModel {
    private String[] columnNames =
            {"Store/Location", "Amount ($)", "Category"};
    private Expenses expenses;

    // constructs table model with given expense list
    public ReceiptTableModel(Expenses expenses) {
        this.expenses = expenses;
    }

    // MODIFIES: expenses
    // EFFECTS: updates entry to add receipt to table
    public void addRowEntry(Receipt receipt) {
        this.fireTableDataChanged();
    }

    // EFFECTS: returns receipt at given row of the table
    public Receipt getExpenses(int row) {
        return expenses.getReceipt(row);
    }

    // EFFECTS: returns the row count
    @Override
    public int getRowCount() {
        return expenses.length();
    }

    // EFFECTS: returns the number of columns
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    // EFFECTS: returns the column name
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    // EFFECTS: returns the classes of each column
    @Override
    public Class getColumnClass(int column) {
        switch (column) {
            case 1: return int.class;
            default: return String.class;
        }
    }

    // EFFECTS: indicated whether each cell is/ is not editable
    @Override
    public boolean isCellEditable(int row, int column) {
        switch (column) {
            default:
                return false;
        }
    }

    // EFFECTS: returns the value for given cell
    @Override
    public Object getValueAt(int row, int column) {
        Receipt receipt = getExpenses(row);

        switch (column) {
            case 0:
                return receipt.getLocation();
            case 1:
                return receipt.getAmount();
            case 2:
                return receipt.getCategory();
            default:
                return null;
        }
    }

    // EFFECTS: sets value to given value for given cell
    @Override
    public void setValueAt(Object value, int row, int column) {
        Receipt receipt = getExpenses(row);

        switch (column) {
            case 0:
                receipt.setLocation((String) value);
                break;
            case 1:
                receipt.setAmount((int) value);
                break;
            case 2:
                receipt.setCategory((String) value);
                break;
        }

        fireTableCellUpdated(row, column);
    }

}
