package tech.noetzold.spyware.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.noetzold.spyware.model.MaliciousWebsite;
import tech.noetzold.spyware.repository.MaliciousWebsiteRepository;

import java.util.List;

@Service
public class MaliciousWebsiteService {

    @Autowired
    MaliciousWebsiteRepository maliciousWebsiteRepository;

    public List<MaliciousWebsite> findAllMaliciousWebsite(){
        return maliciousWebsiteRepository.findAll();
    }

    public MaliciousWebsite findMaliciousWebsiteById(Long id){
        return maliciousWebsiteRepository.findById(id).get();
    }

    public MaliciousWebsite saveMaliciousWebsite(MaliciousWebsite maliciousPort){
        return maliciousWebsiteRepository.save(maliciousPort);
    }

    public void deleteWebsiteById(Long id){
        maliciousWebsiteRepository.deleteById(id);
    }

    public MaliciousWebsite findMaliciousWebsiteByUrl(String url) {
        return maliciousWebsiteRepository.findByUrl(url).orElse(null);
    }
}
