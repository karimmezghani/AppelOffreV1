package com.csys.appel.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.csys.appel.domain.Offre} entity.
 */
public class OffreDTO implements Serializable {

    private Long id;

    @NotNull
    private String uniteMesure;

    @NotNull
    private String marque;

    @NotNull
    private Double prixUnitaire;

    @NotNull
    private String origine;

    @NotNull
    private Integer delaiLivraison;

    private String observation;

    private Boolean amc;

    private Boolean echantillon;

    private Boolean fodec;

    private Boolean avecCodeBarre;

    private String conditionnement;

    private Double prixConditionnement;

    private TvaDTO tva;

    private FournisseurDTO fournisseur;

    private DemandeOffreDTO demandeOffre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniteMesure() {
        return uniteMesure;
    }

    public void setUniteMesure(String uniteMesure) {
        this.uniteMesure = uniteMesure;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public Double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public String getOrigine() {
        return origine;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public Integer getDelaiLivraison() {
        return delaiLivraison;
    }

    public void setDelaiLivraison(Integer delaiLivraison) {
        this.delaiLivraison = delaiLivraison;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Boolean getAmc() {
        return amc;
    }

    public void setAmc(Boolean amc) {
        this.amc = amc;
    }

    public Boolean getEchantillon() {
        return echantillon;
    }

    public void setEchantillon(Boolean echantillon) {
        this.echantillon = echantillon;
    }

    public Boolean getFodec() {
        return fodec;
    }

    public void setFodec(Boolean fodec) {
        this.fodec = fodec;
    }

    public Boolean getAvecCodeBarre() {
        return avecCodeBarre;
    }

    public void setAvecCodeBarre(Boolean avecCodeBarre) {
        this.avecCodeBarre = avecCodeBarre;
    }

    public String getConditionnement() {
        return conditionnement;
    }

    public void setConditionnement(String conditionnement) {
        this.conditionnement = conditionnement;
    }

    public Double getPrixConditionnement() {
        return prixConditionnement;
    }

    public void setPrixConditionnement(Double prixConditionnement) {
        this.prixConditionnement = prixConditionnement;
    }

    public TvaDTO getTva() {
        return tva;
    }

    public void setTva(TvaDTO tva) {
        this.tva = tva;
    }

    public FournisseurDTO getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(FournisseurDTO fournisseur) {
        this.fournisseur = fournisseur;
    }

    public DemandeOffreDTO getDemandeOffre() {
        return demandeOffre;
    }

    public void setDemandeOffre(DemandeOffreDTO demandeOffre) {
        this.demandeOffre = demandeOffre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OffreDTO)) {
            return false;
        }

        OffreDTO offreDTO = (OffreDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, offreDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OffreDTO{" +
            "id=" + getId() +
            ", uniteMesure='" + getUniteMesure() + "'" +
            ", marque='" + getMarque() + "'" +
            ", prixUnitaire=" + getPrixUnitaire() +
            ", origine='" + getOrigine() + "'" +
            ", delaiLivraison=" + getDelaiLivraison() +
            ", observation='" + getObservation() + "'" +
            ", amc='" + getAmc() + "'" +
            ", echantillon='" + getEchantillon() + "'" +
            ", fodec='" + getFodec() + "'" +
            ", avecCodeBarre='" + getAvecCodeBarre() + "'" +
            ", conditionnement='" + getConditionnement() + "'" +
            ", prixConditionnement=" + getPrixConditionnement() +
            ", tva=" + getTva() +
            ", fournisseur=" + getFournisseur() +
            ", demandeOffre=" + getDemandeOffre() +
            "}";
    }
}
