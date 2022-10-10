package tech.noetzold.spyware.service;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.noetzold.spyware.model.MaliciousProcess;

public interface MaliciousProcessService extends JpaRepository<MaliciousProcess, Long> {
}
