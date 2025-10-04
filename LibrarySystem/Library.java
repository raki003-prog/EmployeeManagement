import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Library implements Serializable {
    private List<Book> books = new ArrayList<>();
    private static final String FILE_NAME = "library_data.txt";

    public void addBook(String title, String author) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                System.out.println("Book already exists!");
                return;
            }
        }
        books.add(new Book(title, author));
        System.out.println("Book added successfully!");
        saveData();
    }

    public void viewBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in library.");
        } else {
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }

    public void borrowBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                if (book.isBorrowed()) {
                    System.out.println("This book is already borrowed!");
                } else {
                    book.borrowBook();
                    System.out.println("You borrowed: " + book.getTitle());
                }
                saveData();
                return;
            }
        }
        System.out.println("Book not found!");
    }

    public void returnBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                if (book.isBorrowed()) {
                    book.returnBook();
                    System.out.println("You returned: " + book.getTitle());
                } else {
                    System.out.println("This book wasn’t borrowed!");
                }
                saveData();
                return;
            }
        }
        System.out.println("Book not found!");
    }

    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(books);
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }

    @SuppressWarnings("unchecked")
    public void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            books = (List<Book>) ois.readObject();
        } catch (Exception e) {
            books = new ArrayList<>();
        }
    }
}
