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
import tech.noetzold.spyware.controller.MaliciousProcessController;
import tech.noetzold.spyware.model.MaliciousProcess;
import tech.noetzold.spyware.service.DetalheUsuarioServiceImpl;
import tech.noetzold.spyware.service.MaliciousProcessService;
import tech.noetzold.spyware.util.TokenApp;

import java.util.*;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MaliciousProcessController.class)
public class MaliciousProcessControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MaliciousProcessService maliciousProcessService;

    @MockBean
    private DetalheUsuarioServiceImpl detalheUsuarioService;

    public static final int TOKEN_EXPIRACAO = 600_000;
    public static final String TOKEN_SENHA = TokenApp.getTokenPass();

    @Test
    public void testGetAllMaliciousProcesses() throws Exception {
        MaliciousProcess maliciousProcess = new MaliciousProcess();
        maliciousProcess.setId(1L);
        maliciousProcess.setNameExe("evil.exe");

        Collection<MaliciousProcess> maliciousProcesses = new ArrayList<>();
        maliciousProcesses.add(maliciousProcess);

        mockMvc.perform(get("/process/getAll")
                        .header("Authorization", "Bearer " + generateToken()))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testGetMaliciousProcessById() throws Exception {
        MaliciousProcess maliciousProcess = new MaliciousProcess();
        maliciousProcess.setId(1L);
        maliciousProcess.setNameExe("evil.exe");

        given(maliciousProcessService.findMaliciousProcessById(1L)).willReturn(maliciousProcess);

        mockMvc.perform(get("/process/get/{id}", 1L)
                        .header("Authorization", "Bearer " + generateToken()))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetMaliciousProcessByIdWithInvalidId() throws Exception {
        mockMvc.perform(get("/process/get/{id}", 0L)
                        .header("Authorization", "Bearer " + generateToken()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSaveMaliciousProcess() throws Exception {
        MaliciousProcess maliciousProcess = new MaliciousProcess();
        maliciousProcess.setNameExe("evil.exe");

        given(maliciousProcessService.saveMaliciousProcess(any(MaliciousProcess.class))).willReturn(maliciousProcess);

        mockMvc.perform(post("/process/save")
                        .header("Authorization", "Bearer " + generateToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(maliciousProcess)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testRemoveMaliciousProcess() throws Exception {
        mockMvc.perform(delete("/process/remove/{id}", 1L)
                        .header("Authorization", "Bearer " + generateToken()))
                .andExpect(status().isOk());

        verify(maliciousProcessService, times(1)).deleteProcessById(1L);
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
