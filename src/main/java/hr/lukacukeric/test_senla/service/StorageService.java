package hr.lukacukeric.test_senla.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {

    void store(MultipartFile file);
    Path load(String filename);
    Resource loadResource(String filename);

}
