package com.csys.appel.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AppelOffre.
 */
@Entity
@Table(name = "appel_offre")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AppelOffre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "numero", nullable = false)
    private String numero;

    @NotNull
    @Column(name = "date_debut", nullable = false)
    private Instant dateDebut;

    @NotNull
    @Column(name = "date_fin", nullable = false)
    private Instant dateFin;

    @NotNull
    @Column(name = "exercice", nullable = false)
    private String exercice;

    @NotNull
    @Column(name = "designation", nullable = false)
    private String designation;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppelOffre id(Long id) {
        this.id = id;
        return this;
    }

    public String getNumero() {
        return this.numero;
    }

    public AppelOffre numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Instant getDateDebut() {
        return this.dateDebut;
    }

    public AppelOffre dateDebut(Instant dateDebut) {
        this.dateDebut = dateDebut;
        return this;
    }

    public void setDateDebut(Instant dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Instant getDateFin() {
        return this.dateFin;
    }

    public AppelOffre dateFin(Instant dateFin) {
        this.dateFin = dateFin;
        return this;
    }

    public void setDateFin(Instant dateFin) {
        this.dateFin = dateFin;
    }

    public String getExercice() {
        return this.exercice;
    }

    public AppelOffre exercice(String exercice) {
        this.exercice = exercice;
        return this;
    }

    public void setExercice(String exercice) {
        this.exercice = exercice;
    }

    public String getDesignation() {
        return this.designation;
    }

    public AppelOffre designation(String designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppelOffre)) {
            return false;
        }
        return id != null && id.equals(((AppelOffre) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppelOffre{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", exercice='" + getExercice() + "'" +
            ", designation='" + getDesignation() + "'" +
            "}";
    }
}
