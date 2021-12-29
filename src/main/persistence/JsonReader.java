package persistence;

import jdk.nashorn.internal.parser.JSONParser;
import model.Expenses;
import model.Receipt;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Class adapted from JsonSerializationDemo (https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git)

// represents a reader to read the saved receipts as data from the 'my expenses' file
public class JsonReader {
    private String file;

    // EFFECTS: constructs reader to read data from JSON saved file
    public JsonReader(String file) {
        this.file = file;
    }

    // EFFECTS: read and returns receipts from 'my expenses' file
    // throws IOException if an error occurs reading data from file
    public Expenses readExpenses() throws IOException {
        String data = readFile(file);
        JSONObject jsonObject = new JSONObject(data);
        return parseReceipts(jsonObject);
    }

    // EFFECTS: reads expenses file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses saved expenses file for expense list and returns it
    private Expenses parseReceipts(JSONObject jsonObject) {
        Expenses expenses = new Expenses();
        addReceipts(expenses, jsonObject);
        return expenses;
    }

    // MODIFIES: expenses
    // EFFECTS: finds receipts in saved expenses list and adds them to expense list
    private void addReceipts(Expenses expenses, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("receipts");
        for (Object json : jsonArray) {
            JSONObject nextReceipt = (JSONObject) json;
            addReceipt(expenses, nextReceipt);
        }
    }

    // MODIFIES: expenses
    // EFFECTS: finds receipt from JSON object and adds it to workroom
    private void addReceipt(Expenses expenses, JSONObject jsonObject) {
        int amount = jsonObject.getInt("amount");
        String location = jsonObject.getString("location");
        String category = jsonObject.getString("category");
        Receipt receipt = new Receipt(amount, location, category);
        expenses.loadReceipt(receipt);
    }

}
