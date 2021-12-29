package ui;

import model.Expenses;
import model.Receipt;

import javax.swing.*;
import java.awt.*;

// represents table to store receipt transactions
public class ReceiptTable {
    private JLabel transactionsLabel;
    private JTable table;
    private ReceiptTableModel model;

    //constructs new table and transactions label and panel for the table
    public ReceiptTable() {
        transactionsLabel = new JLabel("Here are your past transactions: ");
        table = new JTable();
    }

    // MODIFIES: this
    // EFFECTS: sets up table with column names and default layout
    public void setupTable(ReceiptTableModel model) {
        table = new JTable();
        this.model = model;
        table.setModel(model);

        table.setPreferredScrollableViewportSize(new Dimension(500, 700));
        table.setFillsViewportHeight(true);
    }

    // MODIFIES: panel
    // EFFECTS: adds table and transaction label to given panel
    public void addTable(JPanel panel) {
        panel.add(transactionsLabel);
        panel.add(new JScrollPane(table));
    }

    // MODIFIES: expenses
    // EFFECTS: adds receipt to expense list in table
    public void addEntry(Receipt receipt) {
        model.addRowEntry(receipt);
    }

}
