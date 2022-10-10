package tech.noetzold.spyware.service;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.noetzold.spyware.model.BadLanguage;

public interface BadLanguageService extends JpaRepository<BadLanguage, Long> {
}
