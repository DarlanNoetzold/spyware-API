package tech.noetzold.spyware.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.noetzold.spyware.model.MaliciousProcess;

public interface MaliciousProcessRepository extends JpaRepository<MaliciousProcess, Long> {
}
