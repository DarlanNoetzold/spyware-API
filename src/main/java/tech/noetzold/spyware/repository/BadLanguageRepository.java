package tech.noetzold.spyware.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.noetzold.spyware.model.BadLanguage;

public interface BadLanguageRepository extends JpaRepository<BadLanguage, Long> {
}
