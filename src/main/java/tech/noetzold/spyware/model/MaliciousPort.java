package tech.noetzold.spyware.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class MaliciousPort {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;
    @NotNull
    private String vulnarableBanners;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVulnarableBanners() {
        return vulnarableBanners;
    }

    public void setVulnarableBanners(String vulnarableBanners) {
        this.vulnarableBanners = vulnarableBanners;
    }

    public MaliciousPort(Long id, String vulnarableBanners) {
        this.id = id;
        this.vulnarableBanners = vulnarableBanners;
    }

    public MaliciousPort() {
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
        MaliciousPort other = (MaliciousPort) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
