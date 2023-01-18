package tech.noetzold.spyware.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.noetzold.spyware.model.MaliciousWebsite;

public interface MaliciousWebsiteRepository extends JpaRepository<MaliciousWebsite, Long> {
}
