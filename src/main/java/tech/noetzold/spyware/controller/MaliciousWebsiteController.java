package tech.noetzold.spyware.controller;

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

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<Collection<MaliciousWebsite>> getAll(HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<Collection<MaliciousWebsite>>(maliciousWebsiteService.findAllMaliciousWebsite(), HttpStatus.OK);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<MaliciousWebsite> getMaliciousWebsiteById(@PathVariable("id") long id) {
        try{
            if(id <= 0)
                return new ResponseEntity<MaliciousWebsite>(HttpStatus.BAD_REQUEST);

            MaliciousWebsite maliciousWebsite = maliciousWebsiteService.findMaliciousWebsiteById(id);

            return new ResponseEntity<MaliciousWebsite>(maliciousWebsite, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<MaliciousWebsite>(HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<MaliciousWebsite> save(@RequestBody MaliciousWebsite maliciousWebsite) {
        try {
            maliciousWebsite = maliciousWebsiteService.saveMaliciousWebsite(maliciousWebsite);
            return new ResponseEntity<MaliciousWebsite>(maliciousWebsite, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<MaliciousWebsite>(maliciousWebsite, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @DeleteMapping("remove/{id}")
    public String remover(@PathVariable("id") Long id) {
        maliciousWebsiteService.deleteWebsiteById(id);
        return "redirect:/home";
    }
}
