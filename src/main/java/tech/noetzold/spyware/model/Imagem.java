package tech.noetzold.spyware.model;

import org.apache.tomcat.util.codec.binary.Base64;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Entity
public class Imagem {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    @NotNull
    private String productImg;

    @NotNull
    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] base64Img;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }


    public byte[] getBase64Img() throws UnsupportedEncodingException {
        return this.base64Img;

    }

    public void setBase64Img(String base64Img) {
        this.base64Img = Base64.decodeBase64(base64Img);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Imagem other = (Imagem) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}

