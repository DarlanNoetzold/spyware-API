package tech.noetzold.spyware.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.noetzold.spyware.model.MaliciousProcess;
import tech.noetzold.spyware.service.MaliciousProcessService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

@CrossOrigin
@RestController
@RequestMapping("/process")
public class MaliciousProcessController {
    @Autowired
    MaliciousProcessService maliciousProcessService;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<Collection<MaliciousProcess>> getAll(HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<Collection<MaliciousProcess>>(maliciousProcessService.findAllMaliciousProcess(), HttpStatus.OK);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<MaliciousProcess> getMaliciousProcessById(@PathVariable("id") long id) {
        try {
            if (id <= 0)
                return new ResponseEntity<MaliciousProcess>(HttpStatus.BAD_REQUEST);
            MaliciousProcess maliciousProcess = maliciousProcessService.findMaliciousProcessById(id);

            return new ResponseEntity<MaliciousProcess>(maliciousProcess, HttpStatus.OK);
        }catch (NullPointerException e){
            e.printStackTrace();
            return new ResponseEntity<MaliciousProcess>(HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<MaliciousProcess> save(@RequestBody MaliciousProcess maliciousProcess) {
        try {
            maliciousProcess = maliciousProcessService.saveMaliciousProcess(maliciousProcess);
            return new ResponseEntity<MaliciousProcess>(maliciousProcess, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<MaliciousProcess>(maliciousProcess, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @DeleteMapping("remove/{id}")
    public String remover(@PathVariable("id") Long id) {
        maliciousProcessService.deleteProcessById(id);
        return "redirect:/home";
    }
}
