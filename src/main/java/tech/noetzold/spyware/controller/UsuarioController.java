package tech.noetzold.spyware.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tech.noetzold.spyware.model.Usuario;
import tech.noetzold.spyware.repository.UsuarioRepository;
import tech.noetzold.spyware.service.UsuarioService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UsuarioController {

    private final UsuarioService userService;
    private final PasswordEncoder encoder;

    public UsuarioController(UsuarioService userService, PasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }

    @GetMapping("/listAll")
    public ResponseEntity<List<Usuario>> listarTodos() {
        return ResponseEntity.ok(userService.findAllUsuarios());
    }

    @PostMapping("/save")
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) {
        usuario.setPassword(encoder.encode(usuario.getPassword()));
        return ResponseEntity.ok(userService.saveUsuario(usuario));
    }

    @GetMapping("/validatePass")
    public ResponseEntity<Boolean> validarSenha(@RequestParam String login,
                                                @RequestParam String password) {

        Optional<Usuario> optUsuario = userService.validateLogin(login);
        if (!optUsuario.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }

        Usuario usuario = optUsuario.get();
        boolean valid = encoder.matches(password, usuario.getPassword());

        HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(valid);
    }
}