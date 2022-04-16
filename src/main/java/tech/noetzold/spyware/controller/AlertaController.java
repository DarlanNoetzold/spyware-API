package tech.noetzold.spyware.controller;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.noetzold.spyware.model.Alerta;
import tech.noetzold.spyware.service.AlertaService;
import tech.noetzold.spyware.service.ImagemService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Set;


@CrossOrigin
@RestController
@RequestMapping("/alerta")
public class AlertaController {

    @Autowired
    AlertaService alertaService;

    @Autowired
    ImagemService imagemService;

    @Transactional
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<Alerta>> getAll(HttpServletRequest request, HttpServletResponse response, Pageable pageable) {
        return new ResponseEntity<>(alertaService.findAll(pageable), HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Alerta> getAlertaId(@PathVariable("id") long id) {

        Alerta alerta = alertaService.findById(id).get();

        return new ResponseEntity<Alerta>(alerta, HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value = "/pcId/{pcId}", method = RequestMethod.GET)
    public ResponseEntity<Collection<Alerta>> getAlertaPcId(@PathVariable("pcId") String pcId) {

        List<Alerta> alertas = alertaService.findAllByPcId(pcId);

        return new ResponseEntity<Collection<Alerta>>(alertas, HttpStatus.OK);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<Alerta> save(@RequestBody Alerta alerta, HttpServletRequest request, HttpServletResponse response) {
        try {
            alerta.setImagem(imagemService.findById(alerta.getImagem().getId()).get());
            alerta.setData_cadastro(Calendar.getInstance());
            alerta = alertaService.save(alerta);
            return new ResponseEntity<Alerta>(alerta, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Alerta>(alerta, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    @GetMapping("remover/{id}")
    public String remover(@PathVariable("id") Long id) {
        alertaService.deleteById(id);
        return "redirect:/home";
    }

}
