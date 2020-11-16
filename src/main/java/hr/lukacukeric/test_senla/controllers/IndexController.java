package hr.lukacukeric.test_senla.controllers;

import hr.lukacukeric.test_senla.domain.Book;
import hr.lukacukeric.test_senla.form.WordSearch;
import hr.lukacukeric.test_senla.service.StorageService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

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
        return indexEmptyAdd();
    }

    @PostMapping("upload")
    public ModelAndView downloadFile(@RequestParam("file") MultipartFile xmlDoc) throws ParserConfigurationException, IOException, SAXException {
        ModelAndView modelAndView = indexEmptyAdd();
        service.loadResource(xmlDoc);
        Set<Book> bookSet = service.getBookList();
        if (bookSet.isEmpty()){
            modelAndView.addObject("warning", true);
        }
        modelAndView.addObject("books", bookSet);
        return modelAndView;
    }

    // ADD section

    @PostMapping(value = "addBook", params = "enter")
    public ModelAndView addBook(@Valid Book book, Errors errors) {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("books", service.getBookList());
        modelAndView.addObject(new WordSearch(""));
        if (errors.hasErrors()) {
            return modelAndView;
        } else if (service.searchForPossibleCopyOfISBN(book.getIsbn())){
            modelAndView.addObject("addingWarning", true);
        }
        service.store(book);
        modelAndView.addObject("books", service.getBookList());
        modelAndView.addObject("book", new Book("", "", ""));
        return modelAndView;
    }

    @PostMapping(value = "addBook", params = "cancel")
    public ModelAndView cancelAddBook() {
        return indexEmptyAddListOfBooks();
    }

    // REMOVE section

    @PostMapping("remove/{isbn}")
    public ModelAndView removing(@PathVariable String isbn) {
        ModelAndView modelAndView = indexEmptyAdd();
        service.remove(isbn);
        modelAndView.addObject("books", service.getBookList());
        return modelAndView;
    }

    //EDIT section

    @GetMapping("edit/{isbn}")
    public ModelAndView fillEditFields(@PathVariable String isbn) {
        ModelAndView modelAndView = indexEmptyAddListOfBooks();
        modelAndView.addObject("editbook", service.getBookByIsbn(isbn));
        return modelAndView;
    }

    @PostMapping(value = "edit/{isbn}/editing", params = "send")
    public ModelAndView editSelectedBook(@PathVariable String isbn, @Valid Book editbook, Errors errors) {
        ModelAndView modelAndView = indexEmptyAddListOfBooks();
        if (errors.hasErrors()) {
            modelAndView.addObject("editbook", service.getBookByIsbn(isbn));
            return modelAndView;
        }
        Book bookByIsbn = service.getBookByIsbn(isbn);
        bookByIsbn.setIsbn(editbook.getIsbn());
        bookByIsbn.setTitle(editbook.getTitle());
        bookByIsbn.setAuthor(editbook.getAuthor());
        modelAndView.addObject("book", new Book("", "", ""));
        modelAndView.addObject("books", service.getBookList());
        return modelAndView;
    }

    @PostMapping(value = "edit/{isbn}/editing", params = "cancel")
    public ModelAndView cancelEditingOfSelectedBook(@PathVariable String isbn) {
        return indexEmptyAddListOfBooks();
    }

    //DOWNLOAD section

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

    @GetMapping("search")
    public ModelAndView search(@Valid WordSearch wordSearch, Errors errors){
        ModelAndView modelAndView = indexEmptyAddListOfBooks();
        if (errors.hasErrors()){
            return modelAndView;
        }
        return modelAndView.addObject("books", service.findFromSearch(wordSearch.getSearchedWord()));
    }


    // Private methods for DRY

    private ModelAndView indexEmptyAdd(){
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("book", new Book("", "", ""));
        modelAndView.addObject(new WordSearch(""));
        return modelAndView;
    }

    private ModelAndView indexEmptyAddListOfBooks() {
        ModelAndView modelAndView = indexEmptyAdd();
        modelAndView.addObject("books", service.getBookList());
        return modelAndView;
    }

}
