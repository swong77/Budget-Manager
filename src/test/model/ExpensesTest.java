package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ExpensesTest {
    private Expenses expenses;

    @BeforeEach
    void newEmptyExpenseList() {
        expenses = new Expenses();
    }

    @Test
    void testAddOneReceipt() {
        Receipt rec1 = new Receipt(10,  "Safeway", "food/grocery");
        assertEquals(expenses.length(), 0);

        expenses.addReceipt(rec1);
        assertEquals(expenses.length(), 1);
        assertEquals(expenses.getReceipt(0), rec1);

        rec1.setCategory("other");
        rec1.setAmount(20);
        rec1.setLocation("Costco");
        assertEquals(expenses.getReceipt(0).getAmount(), 20);
        assertEquals(expenses.getReceipt(0).getCategory(), "other");
        assertEquals(expenses.getReceipt(0).getLocation(), "Costco");
    }

    @Test
    void testTotalThreeExpenses() {
        Receipt rec1 = new Receipt(10,  "Safeway", "food/grocery");
        Receipt rec2 = new Receipt(20,  "Zara", "clothing");
        Receipt rec3 = new Receipt(5,  "Costco", "food/grocery");
        expenses.addReceipt(rec1);
        expenses.addReceipt(rec2);
        expenses.addReceipt(rec3);

        assertEquals(expenses.length(), 3);
        assertEquals(expenses.totalExpenses(), 10 + 20 + 5);
        assertEquals(expenses.categoryTotal("clothing"), 20);
        assertEquals(expenses.categoryTotal("food/grocery"), 15);
    }

    @Test
    void testSameStoresReceipts() {
        Receipt rec1 = new Receipt(10,  "Safeway", "food/grocery");
        Receipt rec2 = new Receipt(40, "Zara", "clothing");
        Receipt rec3 = new Receipt(5,  "Safeway", "food/grocery");
        Receipt rec4 = new Receipt(20,  "Zara", "clothing");

        expenses.addReceipt(rec1);
        expenses.addReceipt(rec2);
        expenses.addReceipt(rec3);
        expenses.addReceipt(rec4);

        ArrayList<Receipt> safeway = expenses.locationPurchases("Safeway");
        assertEquals(safeway.size(), 2);
        assertTrue(safeway.contains(rec1));
        assertFalse(safeway.contains(rec2));
        assertTrue(safeway.contains(rec3));
        assertFalse(safeway.contains(rec4));

        ArrayList<Receipt> zara = expenses.locationPurchases("Zara");
        assertEquals(zara.size(), 2);
        assertFalse(zara.contains(rec1));
        assertTrue(zara.contains(rec2));
        assertFalse(zara.contains(rec3));
        assertTrue(zara.contains(rec4));
    }

    @Test
    void testLocationReceiptsNone() {
        Receipt rec1 = new Receipt(10, "Safeway", "food/grocery");
        Receipt rec2 = new Receipt(20, "Zara", "clothing");
        Receipt rec3 = new Receipt(5, "Safeway", "food/grocery");
        Receipt rec4 = new Receipt(20, "Zara", "clothing");

        expenses.addReceipt(rec1);
        expenses.addReceipt(rec2);
        expenses.addReceipt(rec3);
        expenses.addReceipt(rec4);

        ArrayList<Receipt> costco = expenses.locationPurchases("Costco");
        assertEquals(costco.size(), 0);
    }

}