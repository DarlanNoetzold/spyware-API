package tech.noetzold.spyware.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.noetzold.spyware.model.MaliciousWebsite;

import java.util.Optional;

public interface MaliciousWebsiteRepository extends JpaRepository<MaliciousWebsite, Long> {
    Optional<MaliciousWebsite> findByUrl(String url);
}
