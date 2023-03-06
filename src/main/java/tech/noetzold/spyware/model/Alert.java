package tech.noetzold.spyware.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Calendar;

@Entity
public class Alert implements Serializable {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    @NotNull
    private String pcId;

    @NotNull
    private Image image;

    @Type(type="text")
    private String processos;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_cadastro", nullable = false)
    private Calendar data_cadastro;

    public Alert(Long id, String pcId, Image image, String processos, Calendar data_cadastro) {
        this.id = id;
        this.pcId = pcId;
        this.image = image;
        this.processos = processos;
        this.data_cadastro = data_cadastro;
    }

    public Alert() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPcId() {
        return pcId;
    }

    public void setPcId(String pcId) {
        this.pcId = pcId;
    }

    public Image getImagem() {
        return image;
    }

    public void setImagem(Image image) {
        this.image = image;
    }

    public String getProcessos() {
        return processos;
    }

    public void setProcessos(String processos) {
        this.processos = processos;
    }

    public Calendar getData_cadastro() {
        return data_cadastro;
    }

    public void setData_cadastro(Calendar data_cadastro) {
        this.data_cadastro = Calendar.getInstance();
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
        Alert other = (Alert) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Alerta{" +
                "id=" + id +
                ", pcId='" + pcId + '\'' +
                ", imagem=" + image +
                ", processos='" + processos + '\'' +
                ", data_cadastro=" + data_cadastro +
                '}';
    }
}
