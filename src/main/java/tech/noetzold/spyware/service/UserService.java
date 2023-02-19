package tech.noetzold.spyware.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.noetzold.spyware.model.User;
import tech.noetzold.spyware.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAllUsuarios(){
        return userRepository.findAll();
    }

    public User saveUsuario(User user){
        return userRepository.save(user);
    }

    public Optional<User> validateLogin(String login){
        return userRepository.findByLogin(login);
    }
}
