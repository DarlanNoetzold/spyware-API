package tech.noetzold.spyware.unit;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.noetzold.spyware.controller.BadLanguageController;
import tech.noetzold.spyware.model.BadLanguage;
import tech.noetzold.spyware.service.BadLanguageService;
import tech.noetzold.spyware.service.DetalheUsuarioServiceImpl;
import tech.noetzold.spyware.util.TokenApp;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BadLanguageController.class)
public class BadLanguageControllerTest {

    @Mock
    private BadLanguageService badLanguageService;

    @MockBean
    private BadLanguageController badLanguageController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DetalheUsuarioServiceImpl detalheUsuarioService;

    public static final int TOKEN_EXPIRACAO = 600_000;
    public static final String TOKEN_SENHA = TokenApp.getTokenPass();

    @Test
    void getAll() throws Exception {
        BadLanguage badLanguage1 = new BadLanguage(1L, "word1");
        BadLanguage badLanguage2 = new BadLanguage(2L, "word2");

        when(badLanguageService.findAllBadLanguage()).thenReturn(Arrays.asList(badLanguage1, badLanguage2));

        mockMvc.perform(get("/badLanguage/getAll")
                        .contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + generateToken()))
                .andExpect(status().isOk());
    }

    @Test
    void getBadLanguageById() throws Exception {
        BadLanguage badLanguage = new BadLanguage(3L, "word");

        when(badLanguageService.findBadLanguageById(anyLong())).thenReturn(badLanguage);

        mockMvc.perform(get("/badLanguage/get/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + generateToken()))
                .andExpect(status().isOk());
    }

    @Test
    void save() throws Exception {
        BadLanguage badLanguage = new BadLanguage(4L, "word");

        when(badLanguageService.saveBadLanguage(badLanguage)).thenReturn(badLanguage);

        mockMvc.perform(post("/badLanguage/save")
                        .content("{\n" +
                                "  \"word\": \"word\"\n" +
                                "}").header("Authorization", "Bearer " + generateToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
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
