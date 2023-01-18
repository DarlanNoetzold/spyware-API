package tech.noetzold.spyware.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.noetzold.spyware.model.Alerta;

import java.util.List;

public interface AlertaRepository extends JpaRepository<Alerta, Long> {
    @Query("select p from Alerta p where p.pcId = ?1")
    List<Alerta> findAllByPcId(String pcId);
}
