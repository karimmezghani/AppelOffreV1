package com.csys.appel.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.csys.appel.domain.AppelOffre} entity.
 */
public class AppelOffreDTO implements Serializable {

    private Long id;

    @NotNull
    private String numero;

    @NotNull
    private Instant dateDebut;

    @NotNull
    private Instant dateFin;

    @NotNull
    private String exercice;

    @NotNull
    private String designation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Instant getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Instant dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Instant getDateFin() {
        return dateFin;
    }

    public void setDateFin(Instant dateFin) {
        this.dateFin = dateFin;
    }

    public String getExercice() {
        return exercice;
    }

    public void setExercice(String exercice) {
        this.exercice = exercice;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppelOffreDTO)) {
            return false;
        }

        AppelOffreDTO appelOffreDTO = (AppelOffreDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, appelOffreDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppelOffreDTO{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", exercice='" + getExercice() + "'" +
            ", designation='" + getDesignation() + "'" +
            "}";
    }
}
