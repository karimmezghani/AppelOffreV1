package com.csys.appel.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.csys.appel.domain.Tva} entity.
 */
public class TvaDTO implements Serializable {

    private Long id;

    @NotNull
    private Double tauxTva;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTauxTva() {
        return tauxTva;
    }

    public void setTauxTva(Double tauxTva) {
        this.tauxTva = tauxTva;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TvaDTO)) {
            return false;
        }

        TvaDTO tvaDTO = (TvaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tvaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TvaDTO{" +
            "id=" + getId() +
            ", tauxTva=" + getTauxTva() +
            "}";
    }
}
