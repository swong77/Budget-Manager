package ui;

import model.Expenses;
import model.Receipt;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// represents popup window with table to display purchases at specific location
public class LocationTablePopup {
    private ReceiptTable table;
    private ReceiptTableModel model;
    private String location;
    private JFrame frame;
    private JPanel panel;
    private Expenses expenses;
    private Expenses locationExpenses;

    // constructs window for location table at given location
    public LocationTablePopup(String location, Expenses expenses) {
        table = new ReceiptTable();
        this.location = location;
        this.expenses = expenses;
        popupDisplay();
    }

    // EFFECTS: constructs new display with frame and panel
    public void popupDisplay() {
        frame = new JFrame("My Expenses");

        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));

        panel.add(new JLabel("DISPLAYING RECEIPTS FROM LOCATION/STORE: " + location));
        addTable();

        frame.add(panel, BorderLayout.CENTER);
        frame.setTitle(location);
        frame.pack();
        frame.setVisible(true);
    }

    // EFFECTS: adds table to main panel
    public void addTable() {
        table = new ReceiptTable();

        ArrayList<Receipt> temp = expenses.locationPurchases(location);
        locationExpenses = new Expenses();
        for (Receipt r : temp) {
            locationExpenses.loadReceipt(r);
        }

        model = new ReceiptTableModel(locationExpenses);

        table.setupTable(model);
        table.addTable(panel);
    }

}
