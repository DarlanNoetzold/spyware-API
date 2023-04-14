package tech.noetzold.spyware.unit;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import tech.noetzold.spyware.controller.UserController;
import tech.noetzold.spyware.model.User;
import tech.noetzold.spyware.service.DetalheUsuarioServiceImpl;
import tech.noetzold.spyware.service.UserService;
import tech.noetzold.spyware.util.TokenApp;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserController userController;
    @MockBean
    private DetalheUsuarioServiceImpl detalheUsuarioService;

    public static final int TOKEN_EXPIRACAO = 600_000;
    public static final String TOKEN_SENHA = TokenApp.getTokenPass();

    @Test
    public void testListarTodos() throws Exception {
        mockMvc.perform(get("/listAll")
                        .header("Authorization", "Bearer " + generateToken()))
                .andExpect(status().isOk());
    }

    @Test
    public void testSalvar() throws Exception {
        User user = new User(1, "John", "password");
        String userJson = asJsonString(user);

        Mockito.when(userService.saveUsuario(Mockito.any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        mockMvc.perform(post("/save")
                        .header("Authorization", "Bearer " + generateToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk());

        Mockito.verify(userService, Mockito.times(1)).saveUsuario(Mockito.any(User.class));
        Mockito.verifyNoMoreInteractions(userService);
    }

    @Test
    public void testValidarSenha() throws Exception {
        User user = new User(1, "John", "$2a$10$BX./sFd/YgTtTatjZTzoWuzp3q9DilBd.04APLI/mDCeRuzQmz2e");

        Mockito.when(userService.validateLogin(Mockito.eq("john.doe@test.com"))).thenReturn(Optional.of(user));

        mockMvc.perform(get("/validatePass")
                        .header("Authorization", "Bearer " + generateToken())
                        .param("login", "john.doe@test.com")
                        .param("password", "password"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        mockMvc.perform(get("/validatePass")
                        .param("login", "john.doe@test.com")
                        .param("password", "wrongpassword"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("false"));

        Mockito.verify(userService, Mockito.times(1)).validateLogin(Mockito.eq("john.doe@test.com"));
        Mockito.verifyNoMoreInteractions(userService);
    }

    public static String generateToken() {
        String token = JWT.create().
                withSubject("string")
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRACAO))
                .sign(Algorithm.HMAC512(TOKEN_SENHA));

        return token;
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
