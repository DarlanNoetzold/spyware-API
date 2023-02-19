package tech.noetzold.spyware.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tech.noetzold.spyware.model.Alert;
import tech.noetzold.spyware.repository.AlertRepository;

import java.util.List;

@Service
@Cacheable("alerta")
public class AlertService {

    @Autowired
    AlertRepository alertaRepository;

    public Page<Alert> findAll(Pageable pageable){
        return alertaRepository.findAll(pageable);
    }

    public Alert findAlertaById(long id){
        return alertaRepository.findById(id).get();
    }

    public List<Alert> findAlertaByPcId(String pcId){
        return alertaRepository.findAllByPcId(pcId);
    }

    public Alert saveAlerta(Alert alert){
        return alertaRepository.save(alert);
    }

    public void deleteAlertaById(Long id){
        alertaRepository.deleteById(id);
    }
}
