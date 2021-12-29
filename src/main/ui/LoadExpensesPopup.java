package ui;

import model.Expenses;
import persistence.JsonReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

// represents the popup UI to load/not load saved data
public class LoadExpensesPopup implements ActionListener {
    private static final String JSON_STORE = "data/my expenses";

    private JButton yesLoadButton;
    private JButton noLoadButton;
    private JFrame frame;
    private JPanel panel;
    private JLabel loadOptionsLabel;
    private Expenses loadedExpenses;
    private JsonReader jsonReader;

    // constructs new popup display with new expense list
    public LoadExpensesPopup() {
        popupDisplay();
        loadedExpenses = new Expenses();
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: constructs new display with frame and panel
    public void popupDisplay() {
        frame = new JFrame("My Expenses");

        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));

        loadOptions();

        frame.add(panel, BorderLayout.CENTER);
        frame.setTitle("Budget App");
        frame.pack();
        frame.setVisible(true);
    }

    // EFFECTS: adds buttons and label to panel
    public void loadOptions() {
        loadOptionsLabel = new JLabel("Would you like to load your saved expenses?");
        panel.add(loadOptionsLabel);

        yesLoadButton = new JButton("Yes");
        panel.add(yesLoadButton);
        yesLoadButton.addActionListener(this);

        noLoadButton = new JButton("No");
        panel.add(noLoadButton);
        noLoadButton.addActionListener(this);
    }


    // EFFECTS: loads expense list from my expenses JSOn file
    private void loadExpenses() {
        try {
            loadedExpenses = jsonReader.readExpenses();
        } catch (IOException e) {
            ;
        }
    }

    // EFFECTS: loads expense list if the yes Load Button is pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(yesLoadButton)) {
            loadExpenses();
            frame.dispose();
            new MainWindow(loadedExpenses);
        } else if (e.getSource().equals(noLoadButton)) {
            new MainWindow(new Expenses());
            frame.dispose();
        }
    }
}
