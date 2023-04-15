package tech.noetzold.spyware.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.noetzold.spyware.model.Image;
import tech.noetzold.spyware.service.ImageService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

@CrossOrigin
@RestController
@RequestMapping("/image")
public class ImageController {
    @Autowired
    ImageService imageService;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<Collection<Image>> getAll(HttpServletRequest request, HttpServletResponse response) {
        Collection<Image> images = imageService.findAllImages();
        if(images.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Collection<Image>>(images, HttpStatus.OK);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<Image> getImagemById(@PathVariable("id") long id) {
        try {
            if(id <= 0)
                return new ResponseEntity<Image>(HttpStatus.BAD_REQUEST);
            Image image = imageService.findImagemById(id);
            if(image == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Image>(image, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<Image>(HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<Image> save(@RequestBody Image image) {
        try {
            image = imageService.saveImagem(image);
            if(image.getBase64Img() == null) {
                return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
            }
            image.setBase64Img("");
            return new ResponseEntity<Image>(image, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Image>(image, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
