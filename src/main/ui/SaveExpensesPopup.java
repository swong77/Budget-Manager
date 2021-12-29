package ui;

import model.Event;
import model.EventLog;
import model.Expenses;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

// represents a popup window to prompt to save expenses
public class SaveExpensesPopup implements ActionListener {
    private static final String JSON_STORE = "data/my expenses";
    private JsonWriter jsonWriter;
    private JButton yesSaveButton;
    private JButton noSaveButton;
    private JFrame frame;
    private JPanel panel;
    private JLabel saveExpensesLabel;
    private Expenses expenses;
    private BufferedImage warningImage;


    // constructs new popup display given expenses
    public SaveExpensesPopup(Expenses expenses) {
        popupDisplay();
        this.expenses = expenses;
        jsonWriter = new JsonWriter(JSON_STORE);
    }

    // EFFECTS: constructs new display with frame and panel
    public void popupDisplay() {
        frame = new JFrame("My Expenses");

        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));

        warningImage();
        loadOptions();

        frame.add(panel, BorderLayout.CENTER);
        frame.setTitle("Budget App");
        frame.pack();
        frame.setVisible(true);
    }

    // EFFECTS: adds warning image to panel
    public void warningImage() {
        try {
            warningImage = ImageIO.read(new File("data/warning.png"));
            warningImage = resizeImage(warningImage,50,50);
            JLabel picLabel = new JLabel(new ImageIcon(warningImage));
            panel.add(picLabel);
        } catch (IOException e) {
            ;
        }
    }

    // EFFECTS: adds buttons and label to panel
    public void loadOptions() {
        panel.add(new JLabel("WARNING: Unsaved Receipts!"));
        saveExpensesLabel = new JLabel("Would you like to save your recently inputted receipts?");
        panel.add(saveExpensesLabel);

        yesSaveButton = new JButton("Yes");
        panel.add(yesSaveButton);
        yesSaveButton.addActionListener(this);

        noSaveButton = new JButton("No");
        panel.add(noSaveButton);
        noSaveButton.addActionListener(this);
    }

    // EFFECTS: saves expenses to my expenses JSON file
    private void saveExpenses() {
        try {
            jsonWriter.open();
            jsonWriter.write(expenses);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            ;
        }
    }

    // EFFECTS: resizes given image to given width and height
    public static BufferedImage resizeImage(BufferedImage img, int setWidth, int setHeight) {
        Image tmp = img.getScaledInstance(setWidth, setHeight, Image.SCALE_SMOOTH);
        BufferedImage scaledImg = new BufferedImage(setWidth, setHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics2D graphic2D = scaledImg.createGraphics();
        graphic2D.drawImage(tmp, 0, 0, null);
        graphic2D.dispose();

        return scaledImg;
    }

    // EFFECTS: saved expenses if yes Save Button is pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(yesSaveButton)) {
            frame.dispose();
            saveExpenses();
            printLog(EventLog.getInstance());
            EventLog.getInstance().clear();

        } else if (e.getSource().equals(noSaveButton)) {
            frame.dispose();
            printLog(EventLog.getInstance());
            EventLog.getInstance().clear();
        }
    }

    // EFFECTS: prints event log to the console
    public void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString());
            System.out.println("\n\n");
        }
    }

}
