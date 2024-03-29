package tech.noetzold.spyware.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.noetzold.spyware.model.MaliciousPort;
import tech.noetzold.spyware.service.MaliciousPortService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/port")
public class MaliciousPortController {
    @Autowired
    MaliciousPortService maliciousPortService;

    private static final Logger logger = LoggerFactory.getLogger(MaliciousPortController.class);

    @RequestMapping(method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<Collection<MaliciousPort>> getAll(HttpServletRequest request, HttpServletResponse response) {
        Collection<MaliciousPort> maliciousPorts = maliciousPortService.findAllMaliciousPort();
        if (maliciousPorts.isEmpty()) {
            return new ResponseEntity<Collection<MaliciousPort>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Collection<MaliciousPort>>(maliciousPorts, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<MaliciousPort> getMaliciousPortById(@PathVariable("id") long id) {
        if (id <= 0) {
            return new ResponseEntity<MaliciousPort>(HttpStatus.BAD_REQUEST);
        }
        MaliciousPort maliciousPort = maliciousPortService.findMaliciousPortById(id);
        if (maliciousPort == null) {
            return new ResponseEntity<MaliciousPort>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<MaliciousPort>(maliciousPort, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<MaliciousPort> save(@RequestBody MaliciousPort maliciousPort) {
        if (maliciousPort == null || maliciousPort.getVulnarableBanners() == null) {
            return new ResponseEntity<MaliciousPort>(HttpStatus.BAD_REQUEST);
        }

        MaliciousPort existingMaliciousPort = maliciousPortService.findMaliciousPortByVulnarableBanners(maliciousPort.getVulnarableBanners());

        if(existingMaliciousPort != null){
            return new ResponseEntity<MaliciousPort>(existingMaliciousPort, HttpStatus.CREATED);
        }

        try {
            maliciousPort = maliciousPortService.saveMaliciousPort(maliciousPort);
            logger.info("Create MaliciousPort: " + maliciousPort.getVulnarableBanners());
            return new ResponseEntity<MaliciousPort>(maliciousPort, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<MaliciousPort>(maliciousPort, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @DeleteMapping("/{id}")
    public String remover(@PathVariable("id") Long id) {
        logger.info("Remove MaliciousPort: " + id);
        maliciousPortService.deleteMaliciousPortById(id);
        return "redirect:/home";
    }
    
}
