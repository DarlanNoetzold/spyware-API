package tech.noetzold.spyware.unit;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tech.noetzold.spyware.controller.AlertController;
import tech.noetzold.spyware.model.Alert;
import tech.noetzold.spyware.model.Image;
import tech.noetzold.spyware.repository.ImageRepository;
import tech.noetzold.spyware.service.AlertService;
import tech.noetzold.spyware.service.DetalheUsuarioServiceImpl;
import tech.noetzold.spyware.service.RabbitmqService;
import tech.noetzold.spyware.util.TokenApp;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AlertController.class)
public class AlertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlertService alertService;

    @MockBean
    private ImageRepository imageRepository;

    @MockBean
    private RabbitmqService rabbitmqService;

    @MockBean
    private DetalheUsuarioServiceImpl detalheUsuarioService;

    public static final int TOKEN_EXPIRACAO = 600_000;
    public static final String TOKEN_SENHA = TokenApp.getTokenPass();

    @Test
    public void testGetAll() throws Exception {
        List<Alert> alerts = new ArrayList<>();
        Page<Alert> page = new PageImpl<>(alerts);
        when(alertService.findAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/alert")
                        .header("Authorization", "Bearer " + generateToken()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetAlertaId() throws Exception {
        Alert alert = new Alert();
        alert.setId(1L);
        when(alertService.findAlertaById(1L)).thenReturn(alert);

        mockMvc.perform(MockMvcRequestBuilders.get("/alert/1")
                        .header("Authorization", "Bearer " + generateToken()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetAlertaPcIdNotFound() throws Exception {
        List<Alert> alerts = new ArrayList<>();
        when(alertService.findAlertaByPcId("teste")).thenReturn(alerts);

        mockMvc.perform(MockMvcRequestBuilders.get("/alert/pcId/pc1")
                        .header("Authorization", "Bearer " + generateToken()))
                .andExpect(status().isNotFound());
    }

    //@Test
    public void testGetAlertaPcId() throws Exception {
        List<Alert> alerts = new ArrayList<>();
        when(alertService.findAlertaByPcId("asdasdasdasd")).thenReturn(alerts);

        mockMvc.perform(MockMvcRequestBuilders.get("/alert/pcId/asdasdasdasd")
                        .header("Authorization", "Bearer " + generateToken()))
                .andExpect(status().isOk());
    }

    @Test
    public void testSave() throws Exception {
        Alert alert = new Alert(1L, "teste", new Image(1L, "aa", new byte[1]), "aa", Calendar.getInstance());
        alert.setId(1L);
        when(alertService.saveAlerta(any(Alert.class))).thenReturn(alert);
        when(imageRepository.findById(anyLong())).thenReturn(Optional.of(new Image()));
        doNothing().when(rabbitmqService).enviaMensagem(anyString(), any());

        mockMvc.perform(MockMvcRequestBuilders.post("/alert/save")
                        .header("Authorization", "Bearer " + generateToken())
                        .content(asJsonString(alert))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
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