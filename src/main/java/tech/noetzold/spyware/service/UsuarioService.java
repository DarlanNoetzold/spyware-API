package tech.noetzold.spyware.service;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.noetzold.spyware.model.Usuario;

import java.util.Optional;

public interface UsuarioService extends JpaRepository<Usuario, Integer> {

    public Optional<Usuario> findByLogin(String login);

}
