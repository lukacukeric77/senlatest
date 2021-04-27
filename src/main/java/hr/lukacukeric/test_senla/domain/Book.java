package hr.lukacukeric.test_senla.domain;

import javax.validation.constraints.NotEmpty;
import java.util.Comparator;

public class Book {

    @NotEmpty(message = "field cannot be empty")
    private String isbn;
    @NotEmpty(message = "field cannot be empty")
    private String title;
    @NotEmpty(message = "field cannot be empty")
    private String author;

    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public static Comparator<Book> sortByIsbn() {
        return Comparator.comparing(Book::getIsbn);
    }

    public static Comparator<Book> sortByTitle() {
        return Comparator.comparing(Book::getTitle);
    }

    public static Comparator<Book> sortByAuthor() {
        return Comparator.comparing(Book::getAuthor);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Book)) {
            return false;
        }

        Book book = (Book) o;

        return isbn.equals(book.isbn);
    }

    @Override
    public int hashCode() {
        return isbn.hashCode();
    }
}
