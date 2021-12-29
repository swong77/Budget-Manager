package ui;

import model.Expenses;
import model.Receipt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// represents the main budget app UI
public class MainWindow implements ActionListener, WindowListener {
    private static final String[] categoryOptions = {
            "food/grocery",
            "entertainment",
            "living expenses",
            "clothing",
            "education/work",
            "other"};
    private JFrame frame;
    private JPanel panel;
    private JButton okButton;
    private JComboBox categorySelector;
    private JTextField locationText;
    private JTextField amountText;
    private ReceiptTableModel model;
    private Expenses expenses;
    private ReceiptTable table;
    private JPanel textPanel;
    private JTextField searchLocation;
    private JButton searchButton;


    // constructs new main window display
    public MainWindow(Expenses expenses) {
        this.expenses = expenses;
        screenDisplay();
    }

    // EFFECTS: creates main frame and panel
    public void screenDisplay() {
        frame = new JFrame("My Expenses");

        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setLayout(new GridLayout(0, 1));

        addNewReceiptPanel();
        addTable();
        new ExpenseTotalPanel(panel, expenses);
        searchLocationPanel();

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(this);
        frame.setTitle("Budget App");
        frame.pack();
        frame.setVisible(true);
    }

    // EFFECTS: adds panel where user can search for receipts at given location
    public void searchLocationPanel() {
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(3,2));
        searchPanel.add(new JLabel("SEARCH STORE/LOCATION TRANSACTIONS HERE:"));
        searchPanel.add(new JLabel(""));
        searchPanel.add(new JLabel("Enter Location/Store name to view receipts:"));
        searchPanel.add(new JLabel("press 'OK' to search receipts at given store"));

        searchLocation = new JTextField();
        searchPanel.add(searchLocation);

        searchButton = new JButton("OK");
        searchButton.addActionListener(this);
        searchPanel.add(searchButton);

        panel.add(searchPanel);
    }

    // EFFECTS: adds table to display existing and inputted receipts
    public void addTable() {
        table = new ReceiptTable();
        model = new ReceiptTableModel(expenses);

        table.setupTable(model);
        table.addTable(panel);
    }

    // EFFECTS: created new receipt panel and adds it to the main panel
    public void addNewReceiptPanel() {
        textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(3, 4));
        textPanel.add(new JLabel("ENTER NEW RECEIPTS HERE: "));
        textPanel.add(new JLabel(""));
        textPanel.add(new JLabel(""));
        textPanel.add(new JLabel(""));
        textPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));

        addFieldLabels();
        addTextBoxes();
        addCategorySelector();

        okButton = new JButton("OK");
        okButton.addActionListener(this);
        textPanel.add(okButton);

        panel.add(textPanel);
    }

    // EFFECTS: adds labels for receipt panel
    private void addFieldLabels() {
        textPanel.add(new JLabel("Enter Location/Store:"));
        textPanel.add(new JLabel("Enter Amount ($):"));
        textPanel.add(new JLabel("Select Category:"));
        textPanel.add(new JLabel("Press 'OK' to add receipt."));
    }

    // EFFECTS: adds text fields to input location and amount
    private void addTextBoxes() {
        locationText = new JTextField();
        textPanel.add(locationText);

        amountText = new JTextField();
        textPanel.add(amountText);
    }

    // EFFECTS: adds dropdown menu to select from given categories
    private void addCategorySelector() {
        categorySelector = new JComboBox(categoryOptions);
        categorySelector.setSelectedIndex(0);
        categorySelector.addActionListener(this);
        textPanel.add(categorySelector);
    }

    // EFFECTS: handles okButton and search button when pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(okButton)) {
            int amount = Integer.parseInt(amountText.getText());
            String category = categorySelector.getSelectedItem().toString();
            Receipt receipt = new Receipt(amount, locationText.getText(), category);

            expenses.addReceipt(receipt);
            table.addEntry(receipt);
        } else if (e.getSource().equals(searchButton)) {
            String location = searchLocation.getText();

            new LocationTablePopup(location, expenses);
        }
    }

    // EFFECTS: no effect when window is opened
    @Override
    public void windowOpened(WindowEvent e) {

    }

    // EFFECTS: opens save window prompt when closing the main window
    @Override
    public void windowClosing(WindowEvent e) {
        frame.dispose();
        new SaveExpensesPopup(expenses);
    }

    // EFFECTS: no effect when window is closed
    @Override
    public void windowClosed(WindowEvent e) {

    }

    // EFFECTS: no effect when window is iconified
    @Override
    public void windowIconified(WindowEvent e) {

    }

    // EFFECTS: no effect when window is deiconified
    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    // EFFECTS: no effect when window is activated
    @Override
    public void windowActivated(WindowEvent e) {

    }

    // EFFECTS: no effect when window is deacivated
    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
