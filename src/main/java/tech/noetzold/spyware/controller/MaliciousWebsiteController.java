package tech.noetzold.spyware.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.noetzold.spyware.model.MaliciousWebsite;
import tech.noetzold.spyware.repository.MaliciousWebsiteRepository;
import tech.noetzold.spyware.service.MaliciousWebsiteService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

@CrossOrigin
@RestController
@RequestMapping("/website")
public class MaliciousWebsiteController {
    @Autowired
    MaliciousWebsiteService maliciousWebsiteService;

    private static final Logger logger = LogManager.getLogger(MaliciousWebsiteController.class);

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<Collection<MaliciousWebsite>> getAll(HttpServletRequest request, HttpServletResponse response) {
        Collection<MaliciousWebsite> maliciousWebsites = maliciousWebsiteService.findAllMaliciousWebsite();
        if (maliciousWebsites.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(maliciousWebsites, HttpStatus.OK);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<MaliciousWebsite> getMaliciousWebsiteById(@PathVariable("id") long id) {
        if (id <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        MaliciousWebsite maliciousWebsite = maliciousWebsiteService.findMaliciousWebsiteById(id);
        if (maliciousWebsite == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(maliciousWebsite, HttpStatus.OK);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<MaliciousWebsite> save(@RequestBody MaliciousWebsite maliciousWebsite) {
        if (maliciousWebsite == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        MaliciousWebsite existingMaliciousWebsite = maliciousWebsiteService.findMaliciousWebsiteByUrl(maliciousWebsite.getUrl());

        if(existingMaliciousWebsite != null){
            return new ResponseEntity<>(existingMaliciousWebsite, HttpStatus.CREATED);
        }

        try {
            maliciousWebsite = maliciousWebsiteService.saveMaliciousWebsite(maliciousWebsite);
            logger.info("Create MaliciousWebsite: " + maliciousWebsite.getUrl());
            return new ResponseEntity<>(maliciousWebsite, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @DeleteMapping("remove/{id}")
    public String remover(@PathVariable("id") Long id) {
        maliciousWebsiteService.deleteWebsiteById(id);
        logger.info("Remove MaliciousWebsite: " + id);
        return "redirect:/home";
    }
}
