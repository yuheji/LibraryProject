import java.io.Console;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        //Creates table and database if it doesn't exist
        SQLiteCommands.createTable();

        int numBooks = SQLiteCommands.getLastInsertedId();
        ConsoleHelper.printMessage("Loaded " + numBooks + " books into library");

        while(true) {
            showMainMenu();
            Integer option = ConsoleHelper.getInt(false);
            if(option == 5) {
                ConsoleHelper.printMessage("\nLibrary saved.");
                break;
            }
            handleMainMenuOptions(option);
        }
    }

    /**
     * Shows main menu options
     */
    private static void showMainMenu() {
        ConsoleHelper.printTitle("Book Manager");
        ConsoleHelper.printMessage("");
        ConsoleHelper.printOption(1, "View all books");
        ConsoleHelper.printOption(2, "Add a book");
        ConsoleHelper.printOption(3, "Edit a book");
        ConsoleHelper.printOption(4, "Search for a book");
        ConsoleHelper.printOption(5, "Save and exit\n");
        ConsoleHelper.printPrompt("Choose [1-5]");
    }

    /**
     * Handles user options from the main menu
     * @param option Option that user selected
     */
    private static void handleMainMenuOptions(Integer option) {
        switch (option) {
            case 1:
                ConsoleHelper.printTitle("View Books");
                showBooks();
                break;
            case 2:
                ConsoleHelper.printTitle("Add a Book");
                addBook();
                break;
            case 3:
                ConsoleHelper.printTitle("Edit a Book");
                editBook();
                break;
            case 4:
                ConsoleHelper.printTitle("Search");
                searchBook();
                break;
            default:
                break;
        }
    }

    /**
     * Show a list of all books in the database
     */
    private static void showBooks() {
        List<Book> bookList = SQLiteCommands.displayBooks();
        if(bookList.isEmpty()) {
            ConsoleHelper.printMessage("Book list is empty!");
            return;
        }
        for(Book book: bookList) {
            ConsoleHelper.printMessage("[" + book.getId() + "] " + book.getTitle(), 1);
        }
        while(true) {
            ConsoleHelper.printMessage("\nTo view details enter the book ID, to return press <Enter>.\n");
            ConsoleHelper.printPrompt("Book ID");
            Integer id = ConsoleHelper.getInt(true);
            if (id == null) {
                break;
            }
            ConsoleHelper.printMessage("");
            Book book = SQLiteCommands.findBookById(id);
            ConsoleHelper.printMessage("ID: " + book.getId(), 1);
            ConsoleHelper.printMessage("Title: " + book.getTitle(), 1);
            ConsoleHelper.printMessage("Author: " + book.getAuthor(), 1);
            ConsoleHelper.printMessage("Description: " + book.getDescription(), 1);
            ConsoleHelper.printMessage("");
        }
    }

    /**
     * Adds a book to the database
     */
    private static void addBook() {
        ConsoleHelper.printPrompt("Please enter the following information");
        ConsoleHelper.printMessage("\n");
        ConsoleHelper.printPrompt("Title", 1);
        String title = ConsoleHelper.getString();
        ConsoleHelper.printPrompt("Author", 1);
        String author = ConsoleHelper.getString();
        ConsoleHelper.printPrompt("Description", 1);
        String description = ConsoleHelper.getString();
        ConsoleHelper.printMessage("");

        Book book = new Book(title, author, description);
        SQLiteCommands.insertBook(book);
        ConsoleHelper.printMessage("Book [" + SQLiteCommands.getLastInsertedId() + "] Saved");
    }

    /**
     * Edits a book details
     */
    private static void editBook() {
        List<Book> bookList = SQLiteCommands.displayBooks();
        if(bookList.isEmpty()) {
            ConsoleHelper.printMessage("Book list is empty!");
            return;
        }
        for(Book book: bookList) {
            ConsoleHelper.printMessage("[" + book.getId() + "] " + book.getTitle(), 1);
        }
        while(true) {
            ConsoleHelper.printMessage("\nEnter the book ID of the book you want to edit; to return press <Enter>.\n");
            ConsoleHelper.printPrompt("Book ID");
            Integer id = ConsoleHelper.getInt(true);
            if (id == null) {
                break;
            }
            ConsoleHelper.printMessage("");
            Book book = SQLiteCommands.findBookById(id);
            ConsoleHelper.printMessage("Input the following information. To leave a field unchanged, hit <Enter>.\n");
            ConsoleHelper.printPrompt("Title [" + book.getTitle() + "]", 1);
            String title = ConsoleHelper.getString();
            ConsoleHelper.printPrompt("Author [" + book.getAuthor() + "]", 1);
            String author = ConsoleHelper.getString();
            ConsoleHelper.printPrompt("Description [" + book.getDescription() + "]", 1);
            String description = ConsoleHelper.getString();
            ConsoleHelper.printMessage("");

            if(!title.equals("")) {
                book.setTitle(title);
            }
            if(!author.equals("")) {
                book.setAuthor(author);
            }
            if(!description.equals("")) {
                book.setDescription(description);
            }

            SQLiteCommands.editBook(book);
            ConsoleHelper.printMessage("Book [" + SQLiteCommands.getLastInsertedId() + "] Saved");
        }
    }

    /**
     * Grabs and displays a list of books with search term in one of the columns
     */
    private static void searchBook() {
        ConsoleHelper.printMessage("Type in one or more keywords to search for\n");
        ConsoleHelper.printPrompt("Search", 1);
        String search = ConsoleHelper.getString();
        ConsoleHelper.printMessage("");
        ConsoleHelper.printMessage("The following books matched your query. " +
                "Enter the book ID to see more details, or <Enter> to return.\n");
        while(true) {
            List<Book> bookList = SQLiteCommands.searchBooks(search);
            for(Book book: bookList) {
                ConsoleHelper.printMessage("[" + book.getId() + "] " + book.getTitle(), 1);
            }
            ConsoleHelper.printMessage("");
            ConsoleHelper.printPrompt("Book ID");
            Integer bookID = ConsoleHelper.getInt(true);
            if(bookID == null) {
                break;
            }
            Book selectedBook = null;
            for(Book book: bookList) {
                if(book.getId() == bookID); {
                    selectedBook = book;
                }
            }
            if(selectedBook != null) {
                ConsoleHelper.printMessage("ID: " + selectedBook.getId(), 1);
                ConsoleHelper.printMessage("Title: " + selectedBook.getTitle(), 1);
                ConsoleHelper.printMessage("Author: " + selectedBook.getAuthor(), 1);
                ConsoleHelper.printMessage("Description: " + selectedBook.getDescription(), 1);
                ConsoleHelper.printMessage("");
            } else {
                ConsoleHelper.printMessage("Please enter a valid Book ID");
            }
        }
    }
}
