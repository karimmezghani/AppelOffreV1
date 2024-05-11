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
 * A Fournisseur.
 */
@Entity
@Table(name = "fournisseur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Fournisseur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "raison_sociale", nullable = false)
    private String raisonSociale;

    @NotNull
    @Column(name = "matricule_fiscale", nullable = false)
    private String matriculeFiscale;

    @NotNull
    @Column(name = "adresse", nullable = false)
    private String adresse;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "telephone", nullable = false)
    private Integer telephone;

    @OneToMany(mappedBy = "fournisseur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tva", "fournisseur", "demandeOffre" }, allowSetters = true)
    private Set<Offre> offres = new HashSet<>();

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Fournisseur id(Long id) {
        this.id = id;
        return this;
    }

    public String getRaisonSociale() {
        return this.raisonSociale;
    }

    public Fournisseur raisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
        return this;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public String getMatriculeFiscale() {
        return this.matriculeFiscale;
    }

    public Fournisseur matriculeFiscale(String matriculeFiscale) {
        this.matriculeFiscale = matriculeFiscale;
        return this;
    }

    public void setMatriculeFiscale(String matriculeFiscale) {
        this.matriculeFiscale = matriculeFiscale;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public Fournisseur adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return this.email;
    }

    public Fournisseur email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getTelephone() {
        return this.telephone;
    }

    public Fournisseur telephone(Integer telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(Integer telephone) {
        this.telephone = telephone;
    }

    public Set<Offre> getOffres() {
        return this.offres;
    }

    public Fournisseur offres(Set<Offre> offres) {
        this.setOffres(offres);
        return this;
    }

    public Fournisseur addOffre(Offre offre) {
        this.offres.add(offre);
        offre.setFournisseur(this);
        return this;
    }

    public Fournisseur removeOffre(Offre offre) {
        this.offres.remove(offre);
        offre.setFournisseur(null);
        return this;
    }

    public void setOffres(Set<Offre> offres) {
        if (this.offres != null) {
            this.offres.forEach(i -> i.setFournisseur(null));
        }
        if (offres != null) {
            offres.forEach(i -> i.setFournisseur(this));
        }
        this.offres = offres;
    }

    public User getUser() {
        return this.user;
    }

    public Fournisseur user(User user) {
        this.setUser(user);
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fournisseur)) {
            return false;
        }
        return id != null && id.equals(((Fournisseur) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Fournisseur{" +
            "id=" + getId() +
            ", raisonSociale='" + getRaisonSociale() + "'" +
            ", matriculeFiscale='" + getMatriculeFiscale() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", email='" + getEmail() + "'" +
            ", telephone=" + getTelephone() +
            "}";
    }
}
