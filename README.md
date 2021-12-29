# Receipt and Budget Manager

## Keep track of receipts and manage spending/finances and budgeting.

**About the Application**

This application will help keep track of purchases by allowing you to input receipt totals. For each receipt that you enter into the system, you will be able to keep track of:
- How much was spent on the purchase.
- What store the purchase was made at.
- The spending category (food/grocery, entertainment, living expenses, clothing, education/work, other).

After purchases have been logged into the application, you will be able to view your total overall spending, or your spending by category.  You can also enter a location/store name and see all of your past transactions at that place from the receipts that you have previously inputted.

**The Idea**

This application will be useful for anyone who has to keep track of their receipts, finances/spending, and manage their own budget. I chose this idea because having recently moved out on my own, there are a lot of new expenses and sometimes it is hard to keep track of where my money is going. 

## User Stories

- As a user, I want to be able to be able to add a receipt to my list of expenses
- As a user, I want to be able to view my overall spending total
- As a user, I want to be able to see how much I have spent for a certain spending category
- As a user, I want to be able to search for a specific store and see all my purchases made at that store
- As a user, I want to be prompted to save my inputted receipts/transactions to file when exiting the application
- As a user, I want to be asked whether I want to reload my previously entered receipts when starting the application

##Phase 4: Task 2

- Thu Nov 25 11:55:35 PST 2021
- New Receipt inputted for transaction at UBC Bookstore



- Thu Nov 25 11:55:46 PST 2021
- New Receipt inputted for transaction at Costco



- Thu Nov 25 11:55:47 PST 2021
- Total expenses calculated to be $50



- Thu Nov 25 11:55:49 PST 2021
- food/grocery expenses calculated to be $40



- Thu Nov 25 11:55:53 PST 2021
- Displayed transactions for purchases at Costco



- Thu Nov 25 11:55:57 PST 2021
- Expense List Saved.

##Phase 4: Task 3

- There are many similarities between the LoadExpensesPopup and the SaveExpensesPopup classes. These classes could be refactored to extend an abstract class that would remove a lot of the duplicate code and methods in these classes.
- In the LocationTablePopup class there are methods to setup the main frame and panel display and a table to display expenses, similar to that in the MainWindow class. I could refactor the LocationTablePopup class to be a subclass of MainWindow to use avoid duplication of similar methods by using calls to super.