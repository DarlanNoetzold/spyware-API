package tech.noetzold.spyware.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.noetzold.spyware.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
