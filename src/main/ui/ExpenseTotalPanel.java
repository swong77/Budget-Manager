package ui;

import model.Expenses;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// represents panel to calculate total expenses (overall and per category)
public class ExpenseTotalPanel implements ActionListener {
    private static final String[] categoryOptions = {
            "food/grocery",
            "entertainment",
            "living expenses",
            "clothing",
            "education/work",
            "other"};
    private JPanel expensesPanel;
    private JButton expensesButton;
    private JLabel totalExpensesText;
    private JComboBox totalCategorySelector;
    private JLabel categoryTotalText;
    private JPanel panel;
    private Expenses expenses;

    // constructs new expense total calculator panel
    public ExpenseTotalPanel(JPanel panel, Expenses expenses) {
        this.panel = panel;
        this.expenses = expenses;
        expensesPanel();
    }

    // EFFECTS: creates new panel
    public void expensesPanel() {
        expensesPanel = new JPanel();
        expensesPanel.setLayout(new GridLayout(3,2));
        panel.add(expensesPanel);

        totalExpensesField();
        calculateCategoryTotal();
    }

    // EFFECTS: adds button to calculate and display total expenses
    public void totalExpensesField() {
        expensesButton = new JButton("Calculate Total Expenses");
        expensesButton.addActionListener(this);
        expensesPanel.add(expensesButton);

        totalExpensesText = new JLabel("Total Expenses:");
        expensesPanel.add(totalExpensesText);
    }

    // EFFECTS: creates drop down to select and display total expenses for selected category
    public void calculateCategoryTotal() {
        expensesPanel.add(new JLabel("Select category to calculate total:"));
        expensesPanel.add(new JLabel(""));

        totalCategorySelector = new JComboBox(categoryOptions);
        totalCategorySelector.setSelectedIndex(0);
        totalCategorySelector.addActionListener(this);
        expensesPanel.add(totalCategorySelector);

        categoryTotalText = new JLabel("Total _____ Spending:");
        expensesPanel.add(categoryTotalText);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(totalCategorySelector)) {
            String category = totalCategorySelector.getSelectedItem().toString();
            int amount = expenses.categoryTotal(category);
            String categoryTotal = String.valueOf(amount);
            categoryTotalText.setText("Total " + category + " spending: $" + categoryTotal);
        } else if (e.getSource().equals(expensesButton)) {
            totalExpensesText.setText("Total Expenses: $" + expenses.totalExpenses());
        }
    }
}
