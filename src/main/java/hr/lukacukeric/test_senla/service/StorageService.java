package hr.lukacukeric.test_senla.service;

import hr.lukacukeric.test_senla.domain.Book;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Set;

public interface StorageService {

    void store(Book book);

    void loadResource(MultipartFile file)
        throws ParserConfigurationException, IOException, SAXException;

    Set<Book> getBookList();

    void remove(String isbn);

    Book getBookByIsbn(String isbn);

    Set<Book> findFromSearch(String word);

    Boolean searchForPossibleCopyOfISBN(String isbn);

    Set<Book> sortByISBN();

    Set<Book> sortByTitle();

    Set<Book> sortByAuthor();
}
