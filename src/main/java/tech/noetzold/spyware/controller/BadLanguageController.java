package tech.noetzold.spyware.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.noetzold.spyware.model.BadLanguage;
import tech.noetzold.spyware.service.BadLanguageService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

@CrossOrigin
@RestController
@RequestMapping("/badLanguage")
public class BadLanguageController {

    @Autowired
    BadLanguageService badLanguageService;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<Collection<BadLanguage>> getAll(HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<Collection<BadLanguage>>(badLanguageService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/get/{nome}", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<BadLanguage> getBadLanguageById(@PathVariable("nome") long id) {

        BadLanguage badLanguage = badLanguageService.findById(id).get();

        return new ResponseEntity<BadLanguage>(badLanguage, HttpStatus.OK);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<BadLanguage> save(@RequestBody BadLanguage badLanguage) {
        try {
            badLanguage = badLanguageService.save(badLanguage);
            return new ResponseEntity<BadLanguage>(badLanguage, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<BadLanguage>(badLanguage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @GetMapping("remover/{id}")
    public String remover(@PathVariable("id") Long id) {
        badLanguageService.deleteById(id);
        return "redirect:/home";
    }
}
