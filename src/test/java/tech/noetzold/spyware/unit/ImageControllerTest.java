package tech.noetzold.spyware.unit;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.noetzold.spyware.controller.ImageController;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;


import tech.noetzold.spyware.model.Image;
import tech.noetzold.spyware.service.DetalheUsuarioServiceImpl;
import tech.noetzold.spyware.service.ImageService;
import tech.noetzold.spyware.util.TokenApp;

@WebMvcTest(ImageController.class)
public class ImageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImageService imageService;

    @MockBean
    private DetalheUsuarioServiceImpl detalheUsuarioService;

    public static final int TOKEN_EXPIRACAO = 600_000;
    public static final String TOKEN_SENHA = TokenApp.getTokenPass();

    @Test
    public void testGetAllImages() throws Exception {
        List<Image> images = Arrays.asList(new Image(), new Image(), new Image());

        given(imageService.findAllImages()).willReturn(images);

        mockMvc.perform(get("/image/getAll").header("Authorization", "Bearer " + generateToken()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetImageById() throws Exception {
        Image image = new Image();
        image.setId(1L);

        given(imageService.findImagemById(1L)).willReturn(image);

        mockMvc.perform(get("/image/get/1").header("Authorization", "Bearer " + generateToken()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testSaveImage() throws Exception {
        Image image = new Image();
        image.setId(1L);
        image.setBase64Img(Base64.encodeBase64String("base64imagestring".getBytes()));

        given(imageService.saveImagem(any(Image.class))).willReturn(image);

        mockMvc.perform(post("/image/save").header("Authorization", "Bearer " + generateToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": 100, \"productImg\": \"teste\", \"base64Img\": \""+image.getBase64Img()+"\" }"))
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
