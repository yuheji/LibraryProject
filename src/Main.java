import java.sql.*;

public class Main {

    public static void main(String[] args) {
        while(true) {
            showMainMenu();
            Integer option = ConsoleHelper.getInt(false);
            if(option == 5) {
                ConsoleHelper.printMessage("Library saved.");
                break;
            }
            handleMainMenuOptions(option);
        }
    }

    /**
     * Shows main menu options
     */
    public static void showMainMenu() {
        ConsoleHelper.printTitle("Book Manager");
        ConsoleHelper.printMessage("");
        ConsoleHelper.printOption(1, "View all books");
        ConsoleHelper.printOption(2, "Add a book");
        ConsoleHelper.printOption(3, "Edit a book");
        ConsoleHelper.printOption(4, "Search for a book");
        ConsoleHelper.printOption(5, "Save and exit\n");
        ConsoleHelper.printPrompt("Choose [1-5]");
    }

    public static void handleMainMenuOptions(Integer option) {
        switch (option) {
            case 1:
                ConsoleHelper.printTitle("View Books");
                break;
            case 2:
                ConsoleHelper.printTitle("Add a Book");
                break;
            case 3:
                ConsoleHelper.printTitle("Edit a Book");
                break;
            case 4:
                ConsoleHelper.printTitle("Search");
                break;
            default:
                break;
        }
    }
}
