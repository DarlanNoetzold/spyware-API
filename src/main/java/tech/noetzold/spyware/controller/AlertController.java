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
import tech.noetzold.spyware.repository.ImageRepository;
import tech.noetzold.spyware.service.AlertService;
import tech.noetzold.spyware.service.RabbitmqService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/alert")
public class AlertController {

    @Autowired
    AlertService alertaSevice;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    private RabbitmqService rabbitmqService;

    @Transactional
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<Alert>> getAll(HttpServletRequest request, HttpServletResponse response, Pageable pageable) {
        return new ResponseEntity<>(alertaSevice.findAll(pageable), HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Alert> getAlertaId(@PathVariable("id") long id) {
        try {
            if(id <= 0)
                return new ResponseEntity<Alert>(HttpStatus.BAD_REQUEST);

            Alert alert = alertaSevice.findAlertaById(id);

            return new ResponseEntity<Alert>(alert, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<Alert>(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @RequestMapping(value = "/pcId/{pcId}", method = RequestMethod.GET)
    public ResponseEntity<Collection<Alert>> getAlertaPcId(@PathVariable("pcId") String pcId) {

        List<Alert> alerts = alertaSevice.findAlertaByPcId(pcId);

        return new ResponseEntity<Collection<Alert>>(alerts, HttpStatus.OK);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<Alert> save(@RequestBody Alert alert, HttpServletRequest request, HttpServletResponse response) {
        try {
            alert.setImagem(imageRepository.findById(alert.getImagem().getId()).get());
            alert.setData_cadastro(Calendar.getInstance());
            this.rabbitmqService.enviaMensagem(RabbitmqConstantes.FILA_ALERTA, alert);
            return new ResponseEntity<Alert>(alert, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Alert>(alert, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    @DeleteMapping("remove/{id}")
    public String remover(@PathVariable("id") Long id) {
        alertaSevice.deleteAlertaById(id);
        return "redirect:/home";
    }

}
