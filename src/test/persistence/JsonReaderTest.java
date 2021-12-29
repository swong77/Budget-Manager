package persistence;

import model.Expenses;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Tests adapted from JsonSerializationDemo (https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git)

public class JsonReaderTest {

    @Test
    public void testReaderFileNotFound() {
        JsonReader reader = new JsonReader("data/FileNotFound");
        try {
            Expenses expenses = reader.readExpenses();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testReaderNoReceipts() {
        JsonReader reader = new JsonReader("data/testReaderNoReceipts");
        try {
            Expenses expenses = reader.readExpenses();
            assertEquals(0, expenses.length());
        } catch (IOException e) {
            fail("cannot read file");
        }
    }

    @Test
    public void testReaderThreeReceipts() {
        JsonReader reader = new JsonReader("data/testReaderThreeReceipts");
        try {
            Expenses expenses = reader.readExpenses();
            assertEquals(3, expenses.length());

            assertEquals(expenses.totalExpenses(), 10 + 30 + 40);
            assertEquals(expenses.categoryTotal("food/grocery"), 10);
            assertEquals(expenses.categoryTotal("clothing"), 40);
            assertEquals(expenses.categoryTotal("living expenses"), 30);
        } catch (IOException e) {
            fail("cannot read file");
        }
    }

}
