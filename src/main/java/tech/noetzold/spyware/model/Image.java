package tech.noetzold.spyware.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Base64;


@Entity
public class Image implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @SequenceGenerator(name = "sequence", sequenceName = "sequence", allocationSize = 50)
    private Long id;

    @NotNull
    private String productImg;

    @NotNull
    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] base64Img;

    public Image() {
    }

    public Image(Long id, String productImg, @NotNull byte[] base64Img) {
        this.id = id;
        this.productImg = productImg;
        this.base64Img = base64Img;
    }

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
        this.base64Img = Base64.getDecoder().decode(base64Img);
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
        Image other = (Image) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}

