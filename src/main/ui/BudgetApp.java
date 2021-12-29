package ui;

import model.Expenses;
import model.Receipt;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// persistence related methods adapted from JsonSerializationDemo (https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git)

// Receipt and Budget Application
public class BudgetApp {
    private Expenses expenses;
    private Scanner input;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private static final String JSON_STORE = "data/my expenses";

    // EFFECTS: runs the budget application
    public BudgetApp() {
        input = new Scanner(System.in);
        expenses = new Expenses();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runBudget();
    }

    // MODIFIES: this
    // EFFECTS: processes user inputs
    private void runBudget() {
        boolean running = true;
        String task = null;

        initialize();

        promptLoad();

        while (running) {
            homeScreen();
            task = input.next();
            if (task.equals("exit")) {
                promptSave();
                running = false;
            } else {
                commandOptions(task);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes expenses list
    private void initialize() {
        expenses = new Expenses();
        input = new Scanner(System.in);
    }

    // MODIFIES: this
    // EFFECTS: processes commands from home menu
    private void commandOptions(String command) {
        if (command.equals("r")) {
            addReceipt();
        } else if (command.equals("e")) {
            checkExpenses();
        } else {
            System.out.println("invalid entry");
        }
    }

    // EFFECTS: creates display of home menu options
    private void homeScreen() {
        System.out.println("enter 'r' to enter a new receipt into the system");
        System.out.println("enter 'e' to check past purchases and expense totals");
        System.out.println("enter 'exit' to exit system");
    }

    // MODIFIES: this
    // EFFECTS: adds receipt to expense list
    private void addReceipt() {
        System.out.println("Where was this purchase made?");
        System.out.println("Please enter store name:");
        String location = input.next();

        System.out.println("Please enter transaction amount");
        int amount = input.nextInt();
        if (amount <= 0) {
            System.out.println("Transaction amount cannot be negative, please re-enter the amount");
            amount = input.nextInt();
        }

        System.out.println("Please categorize this purchase:");
        categoryOptions();
        String category = returnCategory(input.next());

        Receipt rec1 = new Receipt(amount, location, category);
        expenses.addReceipt(rec1);
    }

    // EFFECTS: prints out category options
    private void categoryOptions() {
        System.out.println("enter 'f' for food/grocery");
        System.out.println("enter 'e' for entertainment");
        System.out.println("enter 'l' for living expenses");
        System.out.println("enter 'c' for clothing");
        System.out.println("enter 'w' for education/work");
        System.out.println("enter 'o' for other");
    }

    // EFFECTS: gives options to analyze expenses
    private void checkExpenses() {
        System.out.println("How would you like to view your expenses?");
        System.out.println("enter 't' to check total overall expenses");
        System.out.println("enter 'c' to check total expenses by spending category");
        System.out.println("enter 'l' to see all purchases at a given location");

        String entry = input.next();

        if (entry.equals("t")) {
            System.out.println("Your total expenses are $" + expenses.totalExpenses());
        } else if (entry.equals("c")) {
            chooseCategory();
        } else if (entry.equals("l")) {
            selectLocation();
        } else {
            System.out.println("Invalid Entry. Please try again.");
            checkExpenses();
        }
    }

    // EFFECTS: returns category based on given input
    private String returnCategory(String category) {
        if (category.equals("f")) {
            return "food/grocery";
        } else if (category.equals("e")) {
            return "entertainment";
        } else if (category.equals("l")) {
            return "living expenses";
        } else if (category.equals("c")) {
            return "clothing";
        } else if (category.equals("w")) {
            return "education/work";
        } else {
            return "other";
        }
    }

    // EFFECTS: processes category selection input and produces category total
    private void chooseCategory() {
        System.out.println("Please select a category to view total expenses.");
        categoryOptions();

        String category = returnCategory(input.next());
        int catTotal = expenses.categoryTotal(category);
        System.out.println("Your total " + category + " spending is: $" + catTotal);
    }

    // EFFECTS: processes location selection input and returns list of purchases at given location
    //          prints 'no purchases at this location' if there are no purchases at given location
    private void selectLocation() {
        System.out.println("Please enter the store/location of the purchases you would like to view.");
        String loc = input.next();

        ArrayList<Receipt> transactions = expenses.locationPurchases(loc);

        int i = 1;
        if (expenses.locationPurchases(loc).size() == 0) {
            System.out.println("You have no past purchases at this location");
        } else {
            System.out.println("here are you past purchases at " + loc + ":");
            for (Receipt r : transactions) {
                int amount = r.getAmount();
                String category = r.getCategory();
                System.out.println("Transaction " + i + ": Amount: $" + amount + ", Category: " + category);
                i++;
            }
        }
    }

    // EFFECTS: prompts user to save expenses before exiting and processes entry
    private void promptSave() {
        System.out.println("enter 's' to save expenses");
        System.out.println("enter 'q' to quit application without saving");

        String entry = input.next();
        if (entry.equals("s")) {
            saveExpenses();
        }
    }

    // EFFECTS: prompts user to load expenses when starting the application
    private void promptLoad() {
        System.out.println("Would you like to load your saved expenses?");
        System.out.println("enter 'yes' or 'no'");

        String entry = input.next();
        if (entry.equals("yes")) {
            loadExpenses();
        }
    }

    // EFFECTS: saves expenses to my expenses JSON file
    private void saveExpenses() {
        try {
            jsonWriter.open();
            jsonWriter.write(expenses);
            jsonWriter.close();
            System.out.println("Your expense list has been saved");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save to your expense list");
        }
    }

    // EFFECTS: loads expense list from my expenses JSOn file
    private void loadExpenses() {
        try {
            expenses = jsonReader.readExpenses();
            System.out.println("Loaded your expenses");
        } catch (IOException e) {
            System.out.println("Unable to load your expenses");
        }
    }

}
