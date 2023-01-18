package tech.noetzold.spyware.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.noetzold.spyware.model.BadLanguage;
import tech.noetzold.spyware.repository.BadLanguageRepository;
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
        return new ResponseEntity<Collection<BadLanguage>>(badLanguageService.findAllBadLanguage(), HttpStatus.OK);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<BadLanguage> getBadLanguageById(@PathVariable("id") long id) {
        try {
            if(id <= 0)
                return new ResponseEntity<BadLanguage>(HttpStatus.BAD_REQUEST);

            BadLanguage badLanguage = badLanguageService.findBadLanguageById(id);

            return new ResponseEntity<BadLanguage>(badLanguage, HttpStatus.OK);
        }catch (NullPointerException e){
            e.printStackTrace();
            return new ResponseEntity<BadLanguage>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<BadLanguage> save(@RequestBody BadLanguage badLanguage) {
        try {
            badLanguage = badLanguageService.saveBadLanguage(badLanguage);
            return new ResponseEntity<BadLanguage>(badLanguage, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<BadLanguage>(badLanguage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @DeleteMapping("remove/{id}")
    public String remover(@PathVariable("id") Long id) {
        badLanguageService.deleteBadLanguage(id);
        return "redirect:/home";
    }
}
