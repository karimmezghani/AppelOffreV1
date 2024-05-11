package com.csys.appel.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.csys.appel.domain.Article} entity.
 */
public class ArticleDTO implements Serializable {

    private Long id;

    @NotNull
    private String designation;

    private Boolean actif;

    @NotNull
    private String codeSaisi;

    private DemandeOffreDTO demandeOffre;

    private CategorieDTO categorie;

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

    public String getCodeSaisi() {
        return codeSaisi;
    }

    public void setCodeSaisi(String codeSaisi) {
        this.codeSaisi = codeSaisi;
    }

    public DemandeOffreDTO getDemandeOffre() {
        return demandeOffre;
    }

    public void setDemandeOffre(DemandeOffreDTO demandeOffre) {
        this.demandeOffre = demandeOffre;
    }

    public CategorieDTO getCategorie() {
        return categorie;
    }

    public void setCategorie(CategorieDTO categorie) {
        this.categorie = categorie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArticleDTO)) {
            return false;
        }

        ArticleDTO articleDTO = (ArticleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, articleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArticleDTO{" +
            "id=" + getId() +
            ", designation='" + getDesignation() + "'" +
            ", actif='" + getActif() + "'" +
            ", codeSaisi='" + getCodeSaisi() + "'" +
            ", demandeOffre=" + getDemandeOffre() +
            ", categorie=" + getCategorie() +
            "}";
    }
}
