package tech.noetzold.spyware.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.noetzold.spyware.model.MaliciousWebsite;
import tech.noetzold.spyware.model.Usuario;
import tech.noetzold.spyware.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository userRepository;

    public List<Usuario> findAllUsuarios(){
        return userRepository.findAll();
    }

    public Usuario saveUsuario(Usuario usuario){
        return userRepository.save(usuario);
    }

    public Optional<Usuario> validateLogin(String login){
        return userRepository.findByLogin(login);
    }
}
