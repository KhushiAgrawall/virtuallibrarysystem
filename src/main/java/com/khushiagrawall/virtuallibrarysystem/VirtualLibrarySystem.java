package com.khushiagrawall.virtuallibrarysystem;

import com.khushiagrawall.virtuallibrarysystem.models.Library;
import com.khushiagrawall.virtuallibrarysystem.utlis.ScannerUtils;
import com.khushiagrawall.virtuallibrarysystem.commands.*;

import java.util.HashMap;
import java.util.Map;

public class VirtualLibrarySystem {
    public  void main(String[] args) {
        Library lib = new Library();
        //BookSearcher searcher = new BookSearcher(lib);

        displayWelcomeMessage();

        Map<Integer, Command> commands = new HashMap<>();
        commands.put(1, new SearchBookCommand());
        commands.put(2, new BorrowBookCommand());
        commands.put(3, new ReturnBookCommand());
        commands.put(4, new BookInventoryCommand());
        commands.put(5, new ViewLogCommand());
        commands.put(6, new UploadBookCommand());
        commands.put(7, new ShowStatisticsCommand());
        commands.put(8, new AnalyzersCommand());

        int choice;

        while (true) {
            showMainMenu();
            choice = ScannerUtils.getIntInput("Choose an option: ");

            if (choice == 9) {
                displayExitMessage();
                break;
            }


            Command command = commands.get(choice);
            if (command != null) {
                command.execute(lib);
            } else {
                System.out.println("Invalid Choice");
            }
        }

    }

    private  void displayWelcomeMessage() {
        System.out.println("📕📗📘📙📔📕📗📘📙📔📕📗📘📙📔📕📗📘📙📔📕📗📘📙📔📕📗📘📙📔");
        System.out.println("📚          Virtual Library Management System (VLS)        📚");
    }

    private  void showMainMenu() {
        System.out.println("📕📗📘📙📔📕📗📘📙📔📕📗📘📙📔📕📗📘📙📔📕📗📘📙📔📕📗📘📙📔📕📗📘");
        displayMainMenuOptions();
        System.out.println("------------------------------------------------------------------");
    }

    private  void displayMainMenuOptions() {
        System.out.println("1. Search for a book            \t2. 🚚 Borrow a book");
        System.out.println("3. Return a book                \t4. 📫 View Library Inventory");
        System.out.println("5. 💾 View Transaction Log      \t6. 👆 Upload Books");
        System.out.println("7. Books Statistics Overview    \t8. Analyzers");
        System.out.println("9. Exit");
    }

    private  void displayExitMessage() {
        System.out.println("📕📗📘📙📔📕📗📘📙📔📕📗📘📙📔📕📗📘📙📔📕📗📘📙📔");
        System.out.println("📔\tThank You for visitng. Come Back Soon  📕");
        System.out.println("📕📗📘📙📔📕📗📘📙📔📕📗📘📙📔📕📗📘📙📔📕📗📘📙📔");
    }
}
