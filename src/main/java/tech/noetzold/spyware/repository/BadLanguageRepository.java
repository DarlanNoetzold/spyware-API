package tech.noetzold.spyware.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.noetzold.spyware.model.BadLanguage;

import java.util.Optional;

public interface BadLanguageRepository extends JpaRepository<BadLanguage, Long> {
    Optional<BadLanguage> findByWord(String word);
}
