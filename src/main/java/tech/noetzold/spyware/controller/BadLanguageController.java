package tech.noetzold.spyware.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/language")
public class BadLanguageController {

    @Autowired
    BadLanguageService badLanguageService;

    private static final Logger logger = LoggerFactory.getLogger(BadLanguageController.class);

    @Transactional
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Collection<BadLanguage>> getAll(HttpServletRequest request, HttpServletResponse response) {
        Collection<BadLanguage> badLanguages = badLanguageService.findAllBadLanguage();
        if (badLanguages.isEmpty()) {
            return new ResponseEntity<Collection<BadLanguage>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Collection<BadLanguage>>(badLanguages, HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<BadLanguage> getBadLanguageById(@PathVariable("id") long id) {
        if (id <= 0) {
            return new ResponseEntity<BadLanguage>(HttpStatus.BAD_REQUEST);
        }
        BadLanguage badLanguage = badLanguageService.findBadLanguageById(id);
        if (badLanguage == null) {
            return new ResponseEntity<BadLanguage>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<BadLanguage>(badLanguage, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<BadLanguage> save(@RequestBody BadLanguage badLanguage) {
        if (badLanguage == null) {
            return new ResponseEntity<BadLanguage>(HttpStatus.BAD_REQUEST);
        }

        BadLanguage existingBadLanguage = badLanguageService.findBadLanguageByWord(badLanguage.getWord());

        if(existingBadLanguage != null){
            return new ResponseEntity<BadLanguage>(existingBadLanguage, HttpStatus.CREATED);
        }

        badLanguage = badLanguageService.saveBadLanguage(badLanguage);
        logger.info("Create badLanguage: " +badLanguage.getWord());
        return new ResponseEntity<BadLanguage>(badLanguage, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public String remover(@PathVariable("id") Long id) {
        badLanguageService.deleteBadLanguage(id);
        logger.info("Remove badLanguage: " + id);
        return "redirect:/home";
    }
}
