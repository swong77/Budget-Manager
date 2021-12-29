package model;

import org.json.JSONObject;
import persistence.Writable;
// persistence related methods adapted from JsonSerializationDemo (https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git)

// represents a receipt with the amount, purchaser, store, and category
public class Receipt implements Writable {
    private int amount;         // purchase amount
    private String location;    // store/location name
    private String category;    // spending category (food/grocery, entertainment, living expenses, clothing, other)

    // EFFECTS: receipt has given amount, person, location, and category
    public Receipt(int amount, String location, String category) {
        this.amount = amount;
        this.location = location;
        this.category = category;
    }

    // EFFECTS: returns receipt amount
    public int getAmount() {
        return amount;
    }

    // EFFECTS: returns receipt location
    public String getLocation() {
        return location;
    }

    // EFFECTS: returns receipt category
    public String getCategory() {
        return category;
    }

    // MODIFIES: this
    // EFFECTS: sets amount as given location
    public void setAmount(int amount) {
        this.amount = amount;
    }

    // MODIFIES: this
    // EFFECTS: sets location as given location
    public void setLocation(String location) {
        this.location = location;
    }

    // MODIFIES: this
    // EFFECTS: sets category as given location
    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public JSONObject saveJson() {
        JSONObject json = new JSONObject();
        json.put("amount", amount);
        json.put("location", location);
        json.put("category", category);
        return json;
    }

}
