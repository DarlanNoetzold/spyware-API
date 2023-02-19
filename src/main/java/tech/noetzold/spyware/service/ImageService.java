package tech.noetzold.spyware.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.noetzold.spyware.model.Image;
import tech.noetzold.spyware.repository.ImageRepository;

import java.util.List;

@Service
public class ImageService {
    @Autowired
    ImageRepository imageRepository;

    public List<Image> findAllImages(){
        return imageRepository.findAll();
    }

    public Image findImagemById(Long id){
        return imageRepository.findById(id).get();
    }

    public Image saveImagem(Image image){
        return imageRepository.save(image);
    }

    public void deleteImagem(Image image){
        imageRepository.delete(image);
    }
}
