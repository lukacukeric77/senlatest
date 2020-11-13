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

import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class IndexController {

    private final StorageService service;

    public IndexController(StorageService service) {
        this.service = service;
    }

    @PostMapping("upload")
    public String downloadFile(@RequestParam("file") MultipartFile xmlDoc) {
        service.loadResource(xmlDoc);
        return null;
    }

    @GetMapping
    public ModelAndView showContent(){
        return new ModelAndView("index", "file", service.load(null));

    }
    @PostMapping("add")
    public String addBook(@Valid Book book, Errors errors, RedirectAttributes redirectAttributes){
        if (errors.hasErrors()){
            return "index";
            redirectAttributes.addFlashAttribute("Error during upload");
        }
        service.store(book);

    }

}
