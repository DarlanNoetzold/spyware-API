package tech.noetzold.spyware.service;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.noetzold.spyware.model.Imagem;

public interface ImagemService extends JpaRepository<Imagem, Long> {
}
