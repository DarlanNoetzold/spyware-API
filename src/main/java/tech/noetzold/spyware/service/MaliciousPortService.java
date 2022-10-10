package tech.noetzold.spyware.service;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.noetzold.spyware.model.MaliciousPort;

public interface MaliciousPortService extends JpaRepository<MaliciousPort, Long> {
}
