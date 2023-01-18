package tech.noetzold.spyware.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.noetzold.spyware.model.Imagem;
import tech.noetzold.spyware.repository.ImagemRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ImagemService {
    @Autowired
    ImagemRepository imagemRepository;

    public List<Imagem> findAllImages(){
        return imagemRepository.findAll();
    }

    public Imagem findImagemById(Long id){
        return imagemRepository.findById(id).get();
    }

    public Imagem saveImagem(Imagem imagem){
        return imagemRepository.save(imagem);
    }

    public void deleteImagem(Imagem imagem){
        imagemRepository.delete(imagem);
    }
}
