package tech.noetzold.spyware.unit;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.noetzold.spyware.controller.MaliciousPortController;
import tech.noetzold.spyware.model.MaliciousPort;
import tech.noetzold.spyware.service.DetalheUsuarioServiceImpl;
import tech.noetzold.spyware.service.MaliciousPortService;
import tech.noetzold.spyware.util.TokenApp;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MaliciousPortController.class)
public class MaliciousPortControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MaliciousPortService maliciousPortService;

    @MockBean
    private DetalheUsuarioServiceImpl detalheUsuarioService;

    public static final int TOKEN_EXPIRACAO = 600_000;
    public static final String TOKEN_SENHA = TokenApp.getTokenPass();

    @Test
    void testGetAll() throws Exception {
        MaliciousPort port1 = new MaliciousPort(1L, "80");
        MaliciousPort port2 = new MaliciousPort(2L, "443");
        List<MaliciousPort> ports = Arrays.asList(port1, port2);
        when(maliciousPortService.findAllMaliciousPort()).thenReturn(ports);

        mockMvc.perform(get("/port/getAll").header("Authorization", "Bearer " + generateToken()))
                .andExpect(status().isOk());
    }

    @Test
    void testGetMaliciousPortById() throws Exception {
        long portId = 1L;
        MaliciousPort maliciousPort = new MaliciousPort(portId, "8080");
        when(maliciousPortService.findMaliciousPortById(portId)).thenReturn(maliciousPort);

        mockMvc.perform(get("/port/get/{id}", portId).header("Authorization", "Bearer " + generateToken()))
                .andExpect(status().isOk());
    }

    @Test
    void testGetMaliciousPortByIdBadRequest() throws Exception {
        mockMvc.perform(get("/port/get/-1").header("Authorization", "Bearer " + generateToken()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSave() throws Exception {
        MaliciousPort maliciousPort = new MaliciousPort(null, "8080");
        MaliciousPort savedMaliciousPort = new MaliciousPort(1L, "8080");
        when(maliciousPortService.saveMaliciousPort(maliciousPort)).thenReturn(savedMaliciousPort);

        mockMvc.perform(post("/port/save").header("Authorization", "Bearer " + generateToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(maliciousPort)))
                .andExpect(status().isCreated());
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
