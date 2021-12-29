package persistence;

import model.Expenses;
import org.json.JSONObject;

import java.io.*;

// Class adapted from JsonSerializationDemo (https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git)

// represents a writer to write JSON objects to expense list to file
public class JsonWriter {
    private String file;
    private PrintWriter writer;

    // EFFECTS: constructs writer to write to file
    public JsonWriter(String file) {
        this.file = file;
    }

    // MODIFIES: this
    // EFFECTS: opens writer
    //          throws FileNotFoundException if file cannot be opened
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(file));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of expenses to file
    public void write(Expenses expenses) {
        JSONObject json = expenses.saveJson();
        saveToFile(json.toString());
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }


}
