import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Methods that all use SQLite
 */
public class SQLiteCommands {
    /**
     * Opens database connection, also creates database called library.db if needed
     */
    public static void openDatabase() {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:database/library.db");
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    /**
     * Creates table books, which matches up with our book object
     */
    public static void createTable() {
        Connection connection;
        Statement stmt;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:database/library.db");

            stmt = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS BOOKS " +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, TITLE TEXT NOT NULL, " +
                    "AUTHOR TEXT NOT NULL, DESCRIPTION TEXT NOT NULL)";
            stmt.executeUpdate(sql);
            stmt.close();
            connection.close();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a list of all books
     *
     * @return List of books;
     */
    public static List<Book> displayBooks() {
        List<Book> bookList = new ArrayList<Book>();
        Connection connection;
        Statement stmt;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:database/library.db");
            connection.setAutoCommit(false);

            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM BOOKS;");
            while (rs.next()) {
                int id = rs.getInt("ID");
                String title = rs.getString("TITLE");
                String author = rs.getString("AUTHOR");
                String description = rs.getString("DESCRIPTION");
                bookList.add(new Book(id, title, author, description));
            }
            System.out.println();
            rs.close();
            stmt.close();
            connection.commit();
            connection.close();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return bookList;
    }

    /**
     * Inserts a book into the database
     * @param book Book to be inserted into database
     */
    public static void insertBook(Book book) {
        Connection connection;
        Statement stmt;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:database/library.db");
            connection.setAutoCommit(false);
            stmt = connection.createStatement();
            String sql = "INSERT INTO BOOKS (TITLE, AUTHOR, DESCRIPTION) " +
                    "VALUES ('" + book.getTitle() + "', '" + book.getAuthor() + "', '" + book.getDescription() + "');";
            stmt.executeUpdate(sql);

            stmt.close();
            connection.commit();
            connection.close();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    /**
     * Updates book info in database
     *
     * @param book Book to be edited
     */
    public static void editBook(Book book) {
        Connection connection;
        Statement stmt;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:database/library.db");
            connection.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = connection.createStatement();
            String sql = "UPDATE BOOKS SET AUTHOR = '" + book.getAuthor() + "', TITLE = '" + book.getTitle() + "', " +
                    "DESCRIPTION = '" + book.getDescription() + "' WHERE ID=" + book.getId() + ";";
            stmt.executeUpdate(sql);
            connection.commit();

            stmt.close();
            connection.close();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public static List<Book> searchBooks(String searchTerm) {
        List<Book> bookList = new ArrayList<Book>();
        String searchTermModified = "'%" + searchTerm + "%'";
        Connection connection;
        Statement stmt;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:database/library.db");
            connection.setAutoCommit(false);

            stmt = connection.createStatement();
//            String sql = "SELECT * FROM BOOKS WHERE" +
//                    "TITLE LIKE " + searchTermModified + " OR " +
//                    "AUTHOR LIKE " + searchTermModified + "OR " +
//                    "DESCRIPTION LIKE " + searchTermModified + ";";

            String sql = "SELECT * FROM BOOKS WHERE" +
                    "TITLE LIKE '%" + searchTerm + "%';";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("ID");
                String title = rs.getString("TITLE");
                String author = rs.getString("AUTHOR");
                String description = rs.getString("DESCRIPTION");
                bookList.add(new Book(id, title, author, description));
            }
            System.out.println();
            rs.close();
            stmt.close();
            connection.commit();
            connection.close();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return bookList;
    }

    /**
     * Finds a book by the id
     *
     * @param id Book id
     */
    public static Book findBookById(int id) {
        Book book = null;
        Connection connection;
        Statement stmt;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:database/library.db");
            connection.setAutoCommit(false);

            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM BOOKS WHERE ID=" + id + ";");
            while ( rs.next() ) {
                book = new Book(id, rs.getString("TITLE"), rs.getString("AUTHOR"), rs.getString("DESCRIPTION"));
            }
            rs.close();
            stmt.close();
            connection.commit();
            connection.close();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return book;
    }

    public static Integer getLastInsertedId() {
        Integer id = null;
        Connection connection;
        Statement stmt;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:database/library.db");
            connection.setAutoCommit(false);

            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT seq FROM SQLITE_SEQUENCE WHERE NAME='BOOKS'");
            while ( rs.next() ) {
                id = rs.getInt(1);
            }
            rs.close();
            stmt.close();
            connection.commit();
            connection.close();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return id;
    }
}
