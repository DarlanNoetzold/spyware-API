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
        Collection<MaliciousProcess> maliciousProcesses = maliciousProcessService.findAllMaliciousProcess();
        if (maliciousProcesses.isEmpty()) {
            return new ResponseEntity<Collection<MaliciousProcess>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Collection<MaliciousProcess>>(maliciousProcesses, HttpStatus.OK);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<MaliciousProcess> getMaliciousProcessById(@PathVariable("id") long id) {
        if (id <= 0) {
            return new ResponseEntity<MaliciousProcess>(HttpStatus.BAD_REQUEST);
        }
        MaliciousProcess maliciousProcess = maliciousProcessService.findMaliciousProcessById(id);
        if (maliciousProcess == null) {
            return new ResponseEntity<MaliciousProcess>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<MaliciousProcess>(maliciousProcess, HttpStatus.OK);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<MaliciousProcess> save(@RequestBody MaliciousProcess maliciousProcess) {
        if (maliciousProcess == null) {
            return new ResponseEntity<MaliciousProcess>(HttpStatus.BAD_REQUEST);
        }

        MaliciousProcess existingMaliciousProcess = maliciousProcessService.findMaliciousProcessByNameExe(maliciousProcess.getNameExe());

        if(existingMaliciousProcess != null){
            return new ResponseEntity<MaliciousProcess>(existingMaliciousProcess, HttpStatus.CREATED);
        }

        maliciousProcess = maliciousProcessService.saveMaliciousProcess(maliciousProcess);
        return new ResponseEntity<MaliciousProcess>(maliciousProcess, HttpStatus.CREATED);
    }

    @DeleteMapping("remove/{id}")
    public String remover(@PathVariable("id") Long id) {
        maliciousProcessService.deleteProcessById(id);
        return "redirect:/home";
    }
}
