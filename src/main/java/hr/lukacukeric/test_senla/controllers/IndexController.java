package hr.lukacukeric.test_senla.controllers;

import hr.lukacukeric.test_senla.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class IndexController {

    private final StorageService service;

    public IndexController(StorageService service) {
        this.service = service;
    }

    @PostMapping("download")
    public ModelAndView downloadFile(@RequestParam MultipartFile xmlDoc) {
        return null;
    }

    @GetMapping
    public ModelAndView showContent(){
        return new ModelAndView("index", "file", service.load(null));

    }

}
