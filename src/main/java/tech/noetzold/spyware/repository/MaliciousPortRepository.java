package tech.noetzold.spyware.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.noetzold.spyware.model.MaliciousPort;

public interface MaliciousPortRepository extends JpaRepository<MaliciousPort, Long> {
}
