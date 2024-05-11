package com.csys.appel.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.csys.appel.domain.DemandeOffre} entity.
 */
public class DemandeOffreDTO implements Serializable {

    private Long id;

    @NotNull
    private String description;

    @NotNull
    private Integer quantite;

    private AppelOffreDTO appelOffre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public AppelOffreDTO getAppelOffre() {
        return appelOffre;
    }

    public void setAppelOffre(AppelOffreDTO appelOffre) {
        this.appelOffre = appelOffre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DemandeOffreDTO)) {
            return false;
        }

        DemandeOffreDTO demandeOffreDTO = (DemandeOffreDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, demandeOffreDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandeOffreDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", quantite=" + getQuantite() +
            ", appelOffre=" + getAppelOffre() +
            "}";
    }
}
