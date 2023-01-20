package tech.noetzold.spyware.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.noetzold.spyware.model.Alerta;
import tech.noetzold.spyware.repository.AlertaRepository;
import tech.noetzold.spyware.repository.ImagemRepository;
import tech.noetzold.spyware.service.AlertaService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/alert")
public class AlertaController {

    @Autowired
    AlertaService alertaSevice;

    @Autowired
    ImagemRepository imagemRepository;

    @Transactional
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<Alerta>> getAll(HttpServletRequest request, HttpServletResponse response, Pageable pageable) {
        return new ResponseEntity<>(alertaSevice.findAll(pageable), HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Alerta> getAlertaId(@PathVariable("id") long id) {
        try {
            if(id <= 0)
                return new ResponseEntity<Alerta>(HttpStatus.BAD_REQUEST);

            Alerta alerta = alertaSevice.findAlertaById(id);

            return new ResponseEntity<Alerta>(alerta, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<Alerta>(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @RequestMapping(value = "/pcId/{pcId}", method = RequestMethod.GET)
    public ResponseEntity<Collection<Alerta>> getAlertaPcId(@PathVariable("pcId") String pcId) {

        List<Alerta> alertas = alertaSevice.findAlertaByPcId(pcId);

        return new ResponseEntity<Collection<Alerta>>(alertas, HttpStatus.OK);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<Alerta> save(@RequestBody Alerta alerta, HttpServletRequest request, HttpServletResponse response) {
        try {
            alerta.setImagem(imagemRepository.findById(alerta.getImagem().getId()).get());
            alerta.setData_cadastro(Calendar.getInstance());
            alerta = alertaSevice.saveAlerta(alerta);
            return new ResponseEntity<Alerta>(alerta, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Alerta>(alerta, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    @DeleteMapping("remove/{id}")
    public String remover(@PathVariable("id") Long id) {
        alertaSevice.deleteAlertaById(id);
        return "redirect:/home";
    }

}
