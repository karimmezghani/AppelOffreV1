package com.csys.appel.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.csys.appel.domain.Categorie} entity.
 */
public class CategorieDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 2)
    private String designation;

    private Boolean actif;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategorieDTO)) {
            return false;
        }

        CategorieDTO categorieDTO = (CategorieDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, categorieDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategorieDTO{" +
            "id=" + getId() +
            ", designation='" + getDesignation() + "'" +
            ", actif='" + getActif() + "'" +
            "}";
    }
}
