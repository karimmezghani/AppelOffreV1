package com.csys.appel.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DemandeOffre.
 */
@Entity
@Table(name = "demande_offre")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DemandeOffre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "quantite", nullable = false)
    private Integer quantite;

    @OneToMany(mappedBy = "demandeOffre")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tva", "fournisseur", "demandeOffre" }, allowSetters = true)
    private Set<Offre> offres = new HashSet<>();

    @OneToMany(mappedBy = "demandeOffre")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "demandeOffre", "categorie" }, allowSetters = true)
    private Set<Article> articles = new HashSet<>();

    @ManyToOne
    private AppelOffre appelOffre;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DemandeOffre id(Long id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public DemandeOffre description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantite() {
        return this.quantite;
    }

    public DemandeOffre quantite(Integer quantite) {
        this.quantite = quantite;
        return this;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public Set<Offre> getOffres() {
        return this.offres;
    }

    public DemandeOffre offres(Set<Offre> offres) {
        this.setOffres(offres);
        return this;
    }

    public DemandeOffre addOffre(Offre offre) {
        this.offres.add(offre);
        offre.setDemandeOffre(this);
        return this;
    }

    public DemandeOffre removeOffre(Offre offre) {
        this.offres.remove(offre);
        offre.setDemandeOffre(null);
        return this;
    }

    public void setOffres(Set<Offre> offres) {
        if (this.offres != null) {
            this.offres.forEach(i -> i.setDemandeOffre(null));
        }
        if (offres != null) {
            offres.forEach(i -> i.setDemandeOffre(this));
        }
        this.offres = offres;
    }

    public Set<Article> getArticles() {
        return this.articles;
    }

    public DemandeOffre articles(Set<Article> articles) {
        this.setArticles(articles);
        return this;
    }

    public DemandeOffre addArticle(Article article) {
        this.articles.add(article);
        article.setDemandeOffre(this);
        return this;
    }

    public DemandeOffre removeArticle(Article article) {
        this.articles.remove(article);
        article.setDemandeOffre(null);
        return this;
    }

    public void setArticles(Set<Article> articles) {
        if (this.articles != null) {
            this.articles.forEach(i -> i.setDemandeOffre(null));
        }
        if (articles != null) {
            articles.forEach(i -> i.setDemandeOffre(this));
        }
        this.articles = articles;
    }

    public AppelOffre getAppelOffre() {
        return this.appelOffre;
    }

    public DemandeOffre appelOffre(AppelOffre appelOffre) {
        this.setAppelOffre(appelOffre);
        return this;
    }

    public void setAppelOffre(AppelOffre appelOffre) {
        this.appelOffre = appelOffre;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DemandeOffre)) {
            return false;
        }
        return id != null && id.equals(((DemandeOffre) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandeOffre{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", quantite=" + getQuantite() +
            "}";
    }
}
