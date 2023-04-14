package tech.noetzold.spyware.unit;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tech.noetzold.spyware.controller.MaliciousWebsiteController;
import tech.noetzold.spyware.model.MaliciousWebsite;
import tech.noetzold.spyware.service.DetalheUsuarioServiceImpl;
import tech.noetzold.spyware.service.MaliciousWebsiteService;
import tech.noetzold.spyware.util.TokenApp;

import java.util.Date;
import java.util.*;


@WebMvcTest(MaliciousWebsiteController.class)
public class MaliciousWebsiteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MaliciousWebsiteService maliciousWebsiteService;

    @MockBean
    private DetalheUsuarioServiceImpl detalheUsuarioService;

    public static final int TOKEN_EXPIRACAO = 600_000;
    public static final String TOKEN_SENHA = TokenApp.getTokenPass();

    @Test
    public void testGetAll() throws Exception {
        MaliciousWebsite website1 = new MaliciousWebsite(1L, "www.example.com");
        MaliciousWebsite website2 = new MaliciousWebsite(1L, "www.test.com");
        List<MaliciousWebsite> websites = Arrays.asList(website1, website2);

        Mockito.when(maliciousWebsiteService.findAllMaliciousWebsite()).thenReturn(websites);

        mockMvc.perform(MockMvcRequestBuilders.get("/website/getAll")
                        .header("Authorization", "Bearer " + generateToken()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetMaliciousWebsiteById() throws Exception {
        MaliciousWebsite website = new MaliciousWebsite(1L, "www.example.com");
        website.setId(1L);

        Mockito.when(maliciousWebsiteService.findMaliciousWebsiteById(1L)).thenReturn(website);

        mockMvc.perform(MockMvcRequestBuilders.get("/website/get/1")
                        .header("Authorization", "Bearer " + generateToken()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.url", Matchers.is("www.example.com")));
    }

    @Test
    public void testGetMaliciousWebsiteByIdInvalidId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/website/get/0")
                        .header("Authorization", "Bearer " + generateToken()))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void testSaveMaliciousWebsite() throws Exception {
        MaliciousWebsite website = new MaliciousWebsite(10L, "www.example.com");

        Mockito.when(maliciousWebsiteService.saveMaliciousWebsite(Mockito.any(MaliciousWebsite.class))).thenReturn(website);

        mockMvc.perform(MockMvcRequestBuilders.post("/website/save")
                        .header("Authorization", "Bearer " + generateToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(website)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testRemoveMaliciousWebsite() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/website/remove/1")
                        .header("Authorization", "Bearer " + generateToken()))
                .andExpect(MockMvcResultMatchers.status().isOk());
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
