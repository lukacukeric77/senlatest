package hr.lukacukeric.test_senla.controllers;

import hr.lukacukeric.test_senla.domain.Book;
import hr.lukacukeric.test_senla.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.xml.sax.SAXException;

import javax.validation.Valid;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class IndexController {

    private final StorageService service;

    public IndexController(StorageService service) {
        this.service = service;
    }

    @PostMapping("upload")
    public ModelAndView downloadFile(@RequestParam("file") MultipartFile xmlDoc) throws ParserConfigurationException, IOException, SAXException {
        service.loadResource(xmlDoc);
        return new ModelAndView("index", "file", service.getBookList());
    }


    @PostMapping("addBook")
    public String addBook(@Valid Book book, Errors errors, RedirectAttributes redirectAttributes){
        if (errors.hasErrors()){
            redirectAttributes.addFlashAttribute("Error during adding");
            return "index";
        }
        service.store(book);
        redirectAttributes.addFlashAttribute("You successfully stored a new book");
        return "index";
    }

    @PostMapping("download")
    public String downloadAsXml(){
        service.createXml(null);
        return "index";
    }

}
