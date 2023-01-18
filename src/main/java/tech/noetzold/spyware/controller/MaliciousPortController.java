package tech.noetzold.spyware.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.noetzold.spyware.model.MaliciousPort;
import tech.noetzold.spyware.repository.MaliciousPortRepository;
import tech.noetzold.spyware.service.MaliciousPortService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

@CrossOrigin
@RestController
@RequestMapping("/port")
public class MaliciousPortController {
    @Autowired
    MaliciousPortService maliciousPortService;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<Collection<MaliciousPort>> getAll(HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<Collection<MaliciousPort>>(maliciousPortService.findAllMaliciousPort(), HttpStatus.OK);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<MaliciousPort> getMaliciousPortById(@PathVariable("id") long id) {
        try{
            if(id <= 0)
                return new ResponseEntity<MaliciousPort>(HttpStatus.BAD_REQUEST);

            MaliciousPort maliciousPort = maliciousPortService.findMaliciousPortById(id);

            return new ResponseEntity<MaliciousPort>(maliciousPort, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<MaliciousPort>(HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<MaliciousPort> save(@RequestBody MaliciousPort maliciousPort) {
        try {
            maliciousPort = maliciousPortService.saveMaliciousPort(maliciousPort);
            return new ResponseEntity<MaliciousPort>(maliciousPort, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<MaliciousPort>(maliciousPort, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @DeleteMapping("remove/{id}")
    public String remover(@PathVariable("id") Long id) {
        maliciousPortService.deleteMaliciousPortById(id);
        return "redirect:/home";
    }
    
}