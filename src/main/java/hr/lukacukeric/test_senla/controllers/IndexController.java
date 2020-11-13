package hr.lukacukeric.test_senla.controllers;

import hr.lukacukeric.test_senla.domain.Book;
import hr.lukacukeric.test_senla.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping
    public ModelAndView defaultView(){
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("book", new Book("", "", ""));
        return modelAndView;
    }

    @PostMapping("upload")
    public ModelAndView downloadFile(@RequestParam("file") MultipartFile xmlDoc) throws ParserConfigurationException, IOException, SAXException {
        ModelAndView modelAndView = new ModelAndView("index");
        service.loadResource(xmlDoc);
        modelAndView.addObject( "books", service.getBookList());
        modelAndView.addObject("book", new Book("", "", ""));
        return modelAndView;
    }


    @PostMapping("addBook")
    public String addBook(@Valid Book book, Errors errors, RedirectAttributes redirectAttributes){
        if (errors.hasErrors()){
            redirectAttributes.addFlashAttribute("message", "Error during adding");
            return "index";
        }
        service.store(book);
        redirectAttributes.addFlashAttribute("message", "You successfully stored a new book");
        return "index";
    }

    @PostMapping("remove/{isbn}")
    public ModelAndView removing(@PathVariable String isbn){
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("books", service.getBookList());
        modelAndView.addObject("book", new Book("", "", ""));
        service.remove(isbn);
        return modelAndView;

    }

    @InitBinder("book")
    void initBinder (DataBinder binder){
        binder.initDirectFieldAccess();
    }

//    @ModelAttribute("book")
//    public Book createModel(){
//        return new Book("", "", "");
//    }

    @PostMapping("download")
    public String downloadAsXml(){
        service.createXml(null);
        return "index";
    }

}
