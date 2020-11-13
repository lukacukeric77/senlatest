package hr.lukacukeric.test_senla.service;

import hr.lukacukeric.test_senla.domain.Book;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

public interface StorageService {

    void store(Book book);
    Book loadResource(MultipartFile filename);
    MultipartFile createXml(List<Book> bookList);

}
