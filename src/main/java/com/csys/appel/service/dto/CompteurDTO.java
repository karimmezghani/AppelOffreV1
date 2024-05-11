package com.csys.appel.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.csys.appel.domain.Compteur} entity.
 */
public class CompteurDTO implements Serializable {

    private Long id;

    private String type;

    private String prefix;

    private String suffix;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompteurDTO)) {
            return false;
        }

        CompteurDTO compteurDTO = (CompteurDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, compteurDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompteurDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", prefix='" + getPrefix() + "'" +
            ", suffix='" + getSuffix() + "'" +
            "}";
    }
}
