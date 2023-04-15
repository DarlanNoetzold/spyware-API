package tech.noetzold.spyware.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.noetzold.spyware.model.BadLanguage;
import tech.noetzold.spyware.repository.BadLanguageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BadLanguageService {

    @Autowired
    BadLanguageRepository badLanguageRepository;

    public List<BadLanguage> findAllBadLanguage(){
        return badLanguageRepository.findAll();
    }

    public BadLanguage findBadLanguageById(Long id){
        Optional<BadLanguage> optionalBadLanguage = badLanguageRepository.findById(id);
        if(optionalBadLanguage.isPresent()){
            return badLanguageRepository.findById(id).get();
        }else{
            return null;
        }
    }

    public BadLanguage saveBadLanguage(BadLanguage badLanguage){
        return badLanguageRepository.save(badLanguage);
    }

    public void deleteBadLanguage(Long id){
        badLanguageRepository.deleteById(id);
    }

    public BadLanguage findBadLanguageByWord(String word) {
        Optional<BadLanguage> optionalBadLanguage = badLanguageRepository.findByWord(word);
        if(optionalBadLanguage.isPresent()){
            return badLanguageRepository.findByWord(word).get();
        }else{
            return null;
        }
    }
}
