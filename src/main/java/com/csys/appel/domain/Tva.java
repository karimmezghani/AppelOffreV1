package com.csys.appel.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Tva.
 */
@Entity
@Table(name = "tva")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Tva implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "taux_tva", nullable = false)
    private Double tauxTva;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tva id(Long id) {
        this.id = id;
        return this;
    }

    public Double getTauxTva() {
        return this.tauxTva;
    }

    public Tva tauxTva(Double tauxTva) {
        this.tauxTva = tauxTva;
        return this;
    }

    public void setTauxTva(Double tauxTva) {
        this.tauxTva = tauxTva;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tva)) {
            return false;
        }
        return id != null && id.equals(((Tva) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tva{" +
            "id=" + getId() +
            ", tauxTva=" + getTauxTva() +
            "}";
    }
}
