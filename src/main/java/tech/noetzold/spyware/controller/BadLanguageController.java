package tech.noetzold.spyware.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
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

    @Transactional
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseEntity<Collection<BadLanguage>> getAll(HttpServletRequest request, HttpServletResponse response, Pageable pageable) {
        if (pageable == null) {
            return new ResponseEntity<Collection<BadLanguage>>(HttpStatus.BAD_REQUEST);
        }
        Collection<BadLanguage> badLanguages = badLanguageService.findAllBadLanguage();
        if (badLanguages.isEmpty()) {
            return new ResponseEntity<Collection<BadLanguage>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<BadLanguage>>(badLanguages, HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
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

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<BadLanguage> save(@RequestBody BadLanguage badLanguage) {
        if (badLanguage == null) {
            return new ResponseEntity<BadLanguage>(HttpStatus.BAD_REQUEST);
        }
        if (badLanguageService.findBadLanguageById(badLanguage.getId()) != null) {
            return new ResponseEntity<BadLanguage>(HttpStatus.CONFLICT);
        }

        BadLanguage existingBadLanguage = badLanguageService.findBadLanguageByWord(badLanguage.getWord());

        if(existingBadLanguage != null){
            return new ResponseEntity<BadLanguage>(existingBadLanguage, HttpStatus.CREATED);
        }

        badLanguage = badLanguageService.saveBadLanguage(badLanguage);
        return new ResponseEntity<BadLanguage>(badLanguage, HttpStatus.CREATED);
    }

    @DeleteMapping("remove/{id}")
    public String remover(@PathVariable("id") Long id) {
        badLanguageService.deleteBadLanguage(id);
        return "redirect:/home";
    }
}
