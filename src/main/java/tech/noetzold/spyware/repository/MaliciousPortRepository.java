package tech.noetzold.spyware.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.noetzold.spyware.model.MaliciousPort;

import java.util.Optional;

public interface MaliciousPortRepository extends JpaRepository<MaliciousPort, Long> {
    Optional<MaliciousPort> findByVulnarableBanners(String vulnarableBanners);
}
