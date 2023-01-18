package tech.noetzold.spyware.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.noetzold.spyware.model.Imagem;

public interface ImagemRepository extends JpaRepository<Imagem, Long> {
}
