package tech.noetzold.spyware.service;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.noetzold.spyware.model.MaliciousWebsite;

public interface MaliciousWebsiteService extends JpaRepository<MaliciousWebsite, Long> {
}
