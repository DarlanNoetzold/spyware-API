package tech.noetzold.spyware.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.noetzold.spyware.model.Imagem;
import tech.noetzold.spyware.service.ImagemService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

@CrossOrigin
@RestController
@RequestMapping("/imagem")
public class ImagemController {
    @Autowired
    ImagemService imagemService;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<Collection<Imagem>> getAll(HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<Collection<Imagem>>(imagemService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/get/{nome}", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<Imagem> getImagemById(@PathVariable("nome") long id) {

        Imagem imagem = imagemService.findById(id).get();

        return new ResponseEntity<Imagem>(imagem, HttpStatus.OK);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<Imagem> save(@RequestBody Imagem imagem) {
        try {
            imagem = imagemService.save(imagem);
            return new ResponseEntity<Imagem>(imagem, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Imagem>(imagem, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
