package tech.noetzold.spyware.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.noetzold.spyware.message.RabbitmqConstantes;
import tech.noetzold.spyware.model.Alert;
import tech.noetzold.spyware.model.Image;
import tech.noetzold.spyware.repository.ImageRepository;
import tech.noetzold.spyware.service.AlertService;
import tech.noetzold.spyware.service.RabbitmqService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@CrossOrigin
@RestController
@RequestMapping("/alert")
public class AlertController {

    @Autowired
    AlertService alertSevice;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    private RabbitmqService rabbitmqService;

    @Transactional
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<Alert>> getAll(HttpServletRequest request, HttpServletResponse response, Pageable pageable) {
        if (pageable == null || pageable.getPageNumber() < 0 || pageable.getPageSize() <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Page<Alert> alerts = alertSevice.findAll(pageable);
        return new ResponseEntity<>(alerts, HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Alert> getAlertId(@PathVariable("id") long id) {
        if (id <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Alert alert = alertSevice.findAlertaById(id);
        if (alert == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(alert, HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value = "/pcId/{pcId}", method = RequestMethod.GET)
    public ResponseEntity<Collection<Alert>> getAlertPcId(@PathVariable("pcId") String pcId) {
        if (pcId == null || pcId.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Alert> alerts = alertSevice.findAlertaByPcId(pcId);
        if (alerts == null || alerts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(alerts, HttpStatus.OK);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<Alert> save(@RequestBody Alert alert, HttpServletRequest request, HttpServletResponse response) {
        if (alert == null || alert.getImagem() == null ||
                alert.getImagem().getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Image> optionalImage = imageRepository.findById(alert.getImagem().getId());
        if (!optionalImage.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        alert.setImagem(optionalImage.get());
        alert.setData_cadastro(Calendar.getInstance());
        this.rabbitmqService.enviaMensagem(RabbitmqConstantes.FILA_ALERT, alert);
        alert.getImagem().setBase64Img("");
        return new ResponseEntity<>(alert, HttpStatus.CREATED);
    }

    @DeleteMapping("remove/{id}")
    public String remover(@PathVariable("id") Long id) {
        alertSevice.deleteAlertaById(id);
        return "redirect:/home";
    }

}
