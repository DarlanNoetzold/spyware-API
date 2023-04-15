package tech.noetzold.spyware.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.noetzold.spyware.model.MaliciousProcess;

import java.util.Optional;

public interface MaliciousProcessRepository extends JpaRepository<MaliciousProcess, Long> {
    Optional<MaliciousProcess> findByNameExe(String nameExe);
}
