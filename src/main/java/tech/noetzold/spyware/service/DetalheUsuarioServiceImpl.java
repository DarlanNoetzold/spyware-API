package tech.noetzold.spyware.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import tech.noetzold.spyware.data.DetalheUsuarioData;
import tech.noetzold.spyware.model.User;
import tech.noetzold.spyware.repository.UserRepository;

import java.util.Optional;

@Component
public class DetalheUsuarioServiceImpl implements UserDetailsService {

    private final UserRepository service;

    public DetalheUsuarioServiceImpl(UserRepository service) {
        this.service = service;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> usuario = service.findByLogin(username);

        if (!usuario.isPresent()){
            throw new UsernameNotFoundException("Usuario [" + username + "] não encontrado");
        }

        return new DetalheUsuarioData(usuario);
    }
}
