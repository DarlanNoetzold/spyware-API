package tech.noetzold.spyware.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.noetzold.spyware.model.MaliciousWebsite;
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

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<Collection<MaliciousWebsite>> getAll(HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<Collection<MaliciousWebsite>>(maliciousWebsiteService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<MaliciousWebsite> getMaliciousWebsiteById(@PathVariable("id") long id) {

        MaliciousWebsite maliciousWebsite = maliciousWebsiteService.findById(id).get();

        return new ResponseEntity<MaliciousWebsite>(maliciousWebsite, HttpStatus.OK);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<MaliciousWebsite> save(@RequestBody MaliciousWebsite maliciousWebsite) {
        try {
            maliciousWebsite = maliciousWebsiteService.save(maliciousWebsite);
            return new ResponseEntity<MaliciousWebsite>(maliciousWebsite, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<MaliciousWebsite>(maliciousWebsite, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @DeleteMapping("remove/{id}")
    public String remover(@PathVariable("id") Long id) {
        maliciousWebsiteService.deleteById(id);
        return "redirect:/home";
    }
}
