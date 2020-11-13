package hr.lukacukeric.test_senla.service;

import hr.lukacukeric.test_senla.domain.Book;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {

    void store(Book book);
    void loadResource(MultipartFile filename);

}
