package tech.noetzold.spyware.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.noetzold.spyware.model.Alert;

import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {
    @Query("select p from Alert p where p.pcId = ?1")
    List<Alert> findAllByPcId(String pcId);
}
