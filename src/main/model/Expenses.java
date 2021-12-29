package model;

import com.sun.javafx.css.StyleManager;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
// persistence related methods adapted from JsonSerializationDemo (https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git)

// represents a list of purchases/receipts for a given person
public class Expenses implements Writable {
    private ArrayList<Receipt> expenses;    // expense list (list of purchases)

    // Constructs a new expense list
    // EFFECTS: creates an expense list that is an empty list of expenses
    public Expenses() {
        expenses = new ArrayList<Receipt>();
    }

    // MODIFIES: this
    // EFFECTS: adds receipt to the expenses list
    public void addReceipt(Receipt r) {
        expenses.add(r);
        String location = r.getLocation();
        EventLog.getInstance().logEvent(new Event("New Receipt inputted for transaction at " + location));
    }

    // MODIFIES: this
    // EFFECTS: adds receipt to the loaded expenses list
    public void loadReceipt(Receipt r) {
        expenses.add(r);
    }

    // EFFECTS: returns given receipt
    public Receipt getReceipt(int i) {
        return expenses.get(i);
    }

    // EFFECTS: returns sum of purchase amounts for an expense list
    public int totalExpenses() {
        int total = 0;
        for (Receipt r: expenses) {
            total = total + r.getAmount();
        }
        EventLog.getInstance().logEvent(new Event("Total expenses calculated to be $" + total));
        return total;
    }

    // EFFECTS: return sum of purchase amounts for a given category in an expense list
    public int categoryTotal(String category) {
        int total = 0;
        for (Receipt r: expenses) {
            if (r.getCategory().equals(category)) {
                total = total + r.getAmount();
            }
        }
        EventLog.getInstance().logEvent(new Event(category + " expenses calculated to be $" + total));
        return total;
    }

    // EFFECTS: returns expense list including receipts only for the given store/location
    public ArrayList<Receipt> locationPurchases(String location) {
        ArrayList<Receipt> transactionList = new ArrayList<Receipt>();

        for (Receipt r: expenses) {
            if (r.getLocation().equals(location)) {
                transactionList.add(r);
            }
        }
        EventLog.getInstance().logEvent(new Event("Displayed transactions for purchases at " + location));
        return transactionList;
    }

    // EFFECTS: returns number of purchases in the expense list
    public int length() {
        return expenses.size();
    }


    @Override
    public JSONObject saveJson() {
        JSONObject json = new JSONObject();
        json.put("receipts", receiptsSaveJson());
        EventLog.getInstance().logEvent(new Event("Expense List Saved."));
        return json;
    }

    // EFFECTS: returns receipts as a JSON array
    private JSONArray receiptsSaveJson() {
        JSONArray jsonArray = new JSONArray();

        for (Receipt r : expenses) {
            jsonArray.put(r.saveJson());
        }

        return jsonArray;
    }
}
