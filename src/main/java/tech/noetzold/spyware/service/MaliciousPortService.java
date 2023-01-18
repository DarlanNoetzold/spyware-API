package tech.noetzold.spyware.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.noetzold.spyware.model.MaliciousPort;
import tech.noetzold.spyware.repository.MaliciousPortRepository;

import java.util.List;

@Service
public class MaliciousPortService {

    @Autowired
    MaliciousPortRepository maliciousPortRepository;

    public List<MaliciousPort> findAllMaliciousPort(){
        return maliciousPortRepository.findAll();
    }

    public MaliciousPort findMaliciousPortById(Long id){
        return maliciousPortRepository.findById(id).get();
    }

    public MaliciousPort saveMaliciousPort(MaliciousPort maliciousPort){
        return maliciousPortRepository.save(maliciousPort);
    }

    public void deleteMaliciousPortById(Long id){
        maliciousPortRepository.deleteById(id);
    }
}
