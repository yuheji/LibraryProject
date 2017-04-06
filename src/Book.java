/**
 * Model for Book object to be saved, edited, and retrieved from database
 */
public class Book {
    private int id;
    private String title;
    private String author;
    private String description;

    public Book(String title, String author, String description) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.id = getLatestId() + 1;
    }

    public Book(int id, String title, String author, String description) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.id = id;
    }

    /**
     * @return Lowest unused int as ID
     */
    private int getLatestId() {
        return SQLiteCommands.getLastInsertedId();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }
}
