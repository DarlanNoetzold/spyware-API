package tech.noetzold.spyware.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.noetzold.spyware.model.Imagem;
import tech.noetzold.spyware.repository.ImagemRepository;
import tech.noetzold.spyware.service.ImagemService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

@CrossOrigin
@RestController
@RequestMapping("/image")
public class ImagemController {
    @Autowired
    ImagemService imagemService;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<Collection<Imagem>> getAll(HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<Collection<Imagem>>(imagemService.findAllImages(), HttpStatus.OK);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<Imagem> getImagemById(@PathVariable("id") long id) {
        try {
            if(id <= 0)
                return new ResponseEntity<Imagem>(HttpStatus.BAD_REQUEST);
            Imagem imagem = imagemService.findImagemById(id);

            return new ResponseEntity<Imagem>(imagem, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<Imagem>(HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<Imagem> save(@RequestBody Imagem imagem) {
        try {
            imagem = imagemService.saveImagem(imagem);
            return new ResponseEntity<Imagem>(imagem, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Imagem>(imagem, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
