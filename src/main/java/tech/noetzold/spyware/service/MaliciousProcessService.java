package tech.noetzold.spyware.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.noetzold.spyware.model.MaliciousProcess;
import tech.noetzold.spyware.repository.MaliciousProcessRepository;

import java.util.List;

@Service
public class MaliciousProcessService {

    @Autowired
    MaliciousProcessRepository maliciousProcessRepository;

    public List<MaliciousProcess> findAllMaliciousProcess(){
        return maliciousProcessRepository.findAll();
    }

    public MaliciousProcess findMaliciousProcessById(Long id){
        return maliciousProcessRepository.findById(id).get();
    }

    public MaliciousProcess saveMaliciousProcess(MaliciousProcess maliciousPort){
        return maliciousProcessRepository.save(maliciousPort);
    }

    public void deleteProcessById(Long id){
        maliciousProcessRepository.deleteById(id);
    }

}
