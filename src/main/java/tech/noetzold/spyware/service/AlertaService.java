package tech.noetzold.spyware.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tech.noetzold.spyware.model.Alerta;
import tech.noetzold.spyware.repository.AlertaRepository;

import java.util.List;

@Service
@Cacheable("alerta")
public class AlertaService {

    @Autowired
    AlertaRepository alertaRepository;

    public Page<Alerta> findAll(Pageable pageable){
        return alertaRepository.findAll(pageable);
    }

    public Alerta findAlertaById(long id){
        return alertaRepository.findById(id).get();
    }

    public List<Alerta> findAlertaByPcId(String pcId){
        return alertaRepository.findAllByPcId(pcId);
    }

    public Alerta saveAlerta(Alerta alerta){
        return alertaRepository.save(alerta);
    }

    public void deleteAlertaById(Long id){
        alertaRepository.deleteById(id);
    }
}
