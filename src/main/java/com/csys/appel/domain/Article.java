package com.csys.appel.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Article.
 */
@Entity
@Table(name = "article")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "designation", nullable = false)
    private String designation;

    @Column(name = "actif")
    private Boolean actif;

    @NotNull
    @Column(name = "code_saisi", nullable = false)
    private String codeSaisi;

    @ManyToOne
    @JsonIgnoreProperties(value = { "offres", "articles", "appelOffre" }, allowSetters = true)
    private DemandeOffre demandeOffre;

    @ManyToOne
    @JsonIgnoreProperties(value = { "articles" }, allowSetters = true)
    private Categorie categorie;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Article id(Long id) {
        this.id = id;
        return this;
    }

    public String getDesignation() {
        return this.designation;
    }

    public Article designation(String designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Boolean getActif() {
        return this.actif;
    }

    public Article actif(Boolean actif) {
        this.actif = actif;
        return this;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public String getCodeSaisi() {
        return this.codeSaisi;
    }

    public Article codeSaisi(String codeSaisi) {
        this.codeSaisi = codeSaisi;
        return this;
    }

    public void setCodeSaisi(String codeSaisi) {
        this.codeSaisi = codeSaisi;
    }

    public DemandeOffre getDemandeOffre() {
        return this.demandeOffre;
    }

    public Article demandeOffre(DemandeOffre demandeOffre) {
        this.setDemandeOffre(demandeOffre);
        return this;
    }

    public void setDemandeOffre(DemandeOffre demandeOffre) {
        this.demandeOffre = demandeOffre;
    }

    public Categorie getCategorie() {
        return this.categorie;
    }

    public Article categorie(Categorie categorie) {
        this.setCategorie(categorie);
        return this;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Article)) {
            return false;
        }
        return id != null && id.equals(((Article) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Article{" +
            "id=" + getId() +
            ", designation='" + getDesignation() + "'" +
            ", actif='" + getActif() + "'" +
            ", codeSaisi='" + getCodeSaisi() + "'" +
            "}";
    }
}
