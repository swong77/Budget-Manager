package persistence;

import model.Expenses;
import model.Receipt;
import org.junit.jupiter.api.Test;


import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


// Tests adapted from JsonSerializationDemo (https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git)

public class JsonWriterTest {

    @Test
    public void testWriterInvalidFile() {
        try {
            Expenses expenses = new Expenses();
            JsonWriter jsonWriter = new JsonWriter("./data/my\0illegal:fileName.json");
            jsonWriter.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testWriterNoReceipts() {
        try {
            Expenses expenses = new Expenses();
            JsonWriter jsonWriter = new JsonWriter("data/testWriterNoReceipts");
            jsonWriter.open();
            jsonWriter.write(expenses);
            jsonWriter.close();

            JsonReader jsonReader = new JsonReader("data/testWriterNoReceipts");
            expenses = jsonReader.readExpenses();
            assertEquals(0, expenses.length());

        } catch (IOException e) {
            fail("exception not expected to be thrown");
        }
    }

    @Test
    public void testWriterThreeReceipts() {
        try {
            Receipt rec1 = new Receipt(10, "Shoppers", "food/grocery");
            Receipt rec2 = new Receipt(50, "Subway", "food/grocery");
            Receipt rec3 = new Receipt(5, "Superstore", "clothing");

            Expenses expenses = new Expenses();
            expenses.addReceipt(rec1);
            expenses.addReceipt(rec2);
            expenses.addReceipt(rec3);

            JsonWriter jsonWriter = new JsonWriter("data/testWriterThreeReceipts");
            jsonWriter.open();
            jsonWriter.write(expenses);
            jsonWriter.close();

            JsonReader jsonReader = new JsonReader("data/testWriterThreeReceipts");
            expenses = jsonReader.readExpenses();
            assertEquals(3, expenses.length());
            assertEquals(expenses.categoryTotal("food/grocery"), 10 + 50);
            assertEquals(expenses.categoryTotal("clothing"), 5);
        } catch (IOException e) {
            fail("exception not expected to be thrown");
        }
    }

}
