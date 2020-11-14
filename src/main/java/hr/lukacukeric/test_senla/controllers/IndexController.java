package hr.lukacukeric.test_senla.controllers;

import hr.lukacukeric.test_senla.domain.Book;
import hr.lukacukeric.test_senla.service.StorageService;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping("/")
public class IndexController {

    private final StorageService service;
    private final TemplateEngine engine;

    public IndexController(StorageService service, TemplateEngine engine) {
        this.service = service;
        this.engine = engine;
    }

    @GetMapping
    public ModelAndView defaultView() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("book", new Book("", "", ""));
        return modelAndView;
    }

    @PostMapping("upload")
    public ModelAndView downloadFile(@RequestParam("file") MultipartFile xmlDoc) throws ParserConfigurationException, IOException, SAXException {
        ModelAndView modelAndView = new ModelAndView("index");
        service.loadResource(xmlDoc);
        modelAndView.addObject("books", service.getBookList());
        modelAndView.addObject("book", new Book("", "", ""));
        return modelAndView;
    }


    @PostMapping(value = "addBook", params = "enter")
    public ModelAndView addBook(@Valid Book book, Errors errors) {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("books", service.getBookList());
        if (errors.hasErrors()) {
            return modelAndView;
        }
        service.store(book);
        modelAndView.addObject("books", service.getBookList());
        modelAndView.addObject("book", new Book("", "", "" ));
        return modelAndView;
    }

    @PostMapping(value = "addBook", params = "cancel")
    public ModelAndView cancelAddBook(){
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("book", new Book("", "", ""));
        modelAndView.addObject("books", service.getBookList());
        return modelAndView;
    }

    @PostMapping("remove/{isbn}")
    public ModelAndView removing(@PathVariable String isbn) {
        ModelAndView modelAndView = new ModelAndView("index");
        service.remove(isbn);
        modelAndView.addObject("books", service.getBookList());
        modelAndView.addObject("book", new Book("", "", ""));
        return modelAndView;
    }

    @GetMapping("edit/{isbn}")
    public ModelAndView fillEditFields(@PathVariable String isbn){
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("book", new Book("", "", ""));
        modelAndView.addObject("books", service.getBookList());
        modelAndView.addObject("editbook", service.getBookByIsbn(isbn));
        return modelAndView;
    }
    @PostMapping(value = "edit/{isbn}/editing", params = "send")
    public ModelAndView editSelectedBook(@PathVariable String isbn, @Valid Book editbook, Errors errors){
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("book", new Book("", "", ""));
        if (errors.hasErrors()){
            return modelAndView;
        }
        Book editBook = service.getBookByIsbn(isbn);
        editBook.setIsbn(editbook.getIsbn());
        editBook.setTitle(editbook.getTitle());
        editBook.setAuthor(editbook.getAuthor());
        modelAndView.addObject("books", service.getBookList());
        return modelAndView;
    }
    @PostMapping(value = "edit/{isbn}/editing", params = "cancel")
    public ModelAndView cancelEditingOfSelectedBook(@PathVariable String isbn){
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("book", new Book("", "", ""));
        modelAndView.addObject("books", service.getBookList());
        return modelAndView;
    }

    @InitBinder("book")
    void initBinder(DataBinder binder) {
        binder.initDirectFieldAccess();
    }


    @GetMapping("download")
    public void downloadAsXml(HttpServletResponse response) throws IOException {
        final Context context = new Context();
        context.setVariable("books", service.getBookList());
        final String xml = this.engine.process("books.xml", context);
        response.setHeader("Content-Disposition", "attachment; filename=exportBooks.xml");
        response.setContentType("application/xml");
        PrintWriter writer = response.getWriter();
        writer.print(xml);
        writer.close();
    }



}
