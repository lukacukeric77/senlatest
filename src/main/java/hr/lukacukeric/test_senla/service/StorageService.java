package hr.lukacukeric.test_senla.service;

import hr.lukacukeric.test_senla.domain.Book;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface StorageService {

    void store(Book book);
    void loadResource(MultipartFile filename) throws ParserConfigurationException, IOException, SAXException;
    MultipartFile createXml(List<Book> bookList);
    List<Book> getBookList(List<Book> bookList);
}
