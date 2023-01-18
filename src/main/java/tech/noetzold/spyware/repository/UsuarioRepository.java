package tech.noetzold.spyware.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.noetzold.spyware.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    public Optional<Usuario> findByLogin(String login);

}
