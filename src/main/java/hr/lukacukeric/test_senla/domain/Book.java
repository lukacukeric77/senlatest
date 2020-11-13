package hr.lukacukeric.test_senla.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;

        Book book = (Book) o;

        return isbn.equals(book.isbn);
    }

    @Override
    public int hashCode() {
        return isbn.hashCode();
    }
}
