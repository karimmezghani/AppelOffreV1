package com.csys.appel.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Offre.
 */
@Entity
@Table(name = "offre")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Offre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "unite_mesure", nullable = false)
    private String uniteMesure;

    @NotNull
    @Column(name = "marque", nullable = false)
    private String marque;

    @NotNull
    @Column(name = "prix_unitaire", nullable = false)
    private Double prixUnitaire;

    @NotNull
    @Column(name = "origine", nullable = false)
    private String origine;

    @NotNull
    @Column(name = "delai_livraison", nullable = false)
    private Integer delaiLivraison;

    @Column(name = "observation")
    private String observation;

    @Column(name = "amc")
    private Boolean amc;

    @Column(name = "echantillon")
    private Boolean echantillon;

    @Column(name = "fodec")
    private Boolean fodec;

    @Column(name = "avec_code_barre")
    private Boolean avecCodeBarre;

    @Column(name = "conditionnement")
    private String conditionnement;

    @Column(name = "prix_conditionnement")
    private Double prixConditionnement;

    @OneToOne
    @JoinColumn(unique = true)
    private Tva tva;

    @ManyToOne
    @JsonIgnoreProperties(value = { "offres", "user" }, allowSetters = true)
    private Fournisseur fournisseur;

    @ManyToOne
    @JsonIgnoreProperties(value = { "offres", "articles", "appelOffre" }, allowSetters = true)
    private DemandeOffre demandeOffre;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Offre id(Long id) {
        this.id = id;
        return this;
    }

    public String getUniteMesure() {
        return this.uniteMesure;
    }

    public Offre uniteMesure(String uniteMesure) {
        this.uniteMesure = uniteMesure;
        return this;
    }

    public void setUniteMesure(String uniteMesure) {
        this.uniteMesure = uniteMesure;
    }

    public String getMarque() {
        return this.marque;
    }

    public Offre marque(String marque) {
        this.marque = marque;
        return this;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public Double getPrixUnitaire() {
        return this.prixUnitaire;
    }

    public Offre prixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
        return this;
    }

    public void setPrixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public String getOrigine() {
        return this.origine;
    }

    public Offre origine(String origine) {
        this.origine = origine;
        return this;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public Integer getDelaiLivraison() {
        return this.delaiLivraison;
    }

    public Offre delaiLivraison(Integer delaiLivraison) {
        this.delaiLivraison = delaiLivraison;
        return this;
    }

    public void setDelaiLivraison(Integer delaiLivraison) {
        this.delaiLivraison = delaiLivraison;
    }

    public String getObservation() {
        return this.observation;
    }

    public Offre observation(String observation) {
        this.observation = observation;
        return this;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Boolean getAmc() {
        return this.amc;
    }

    public Offre amc(Boolean amc) {
        this.amc = amc;
        return this;
    }

    public void setAmc(Boolean amc) {
        this.amc = amc;
    }

    public Boolean getEchantillon() {
        return this.echantillon;
    }

    public Offre echantillon(Boolean echantillon) {
        this.echantillon = echantillon;
        return this;
    }

    public void setEchantillon(Boolean echantillon) {
        this.echantillon = echantillon;
    }

    public Boolean getFodec() {
        return this.fodec;
    }

    public Offre fodec(Boolean fodec) {
        this.fodec = fodec;
        return this;
    }

    public void setFodec(Boolean fodec) {
        this.fodec = fodec;
    }

    public Boolean getAvecCodeBarre() {
        return this.avecCodeBarre;
    }

    public Offre avecCodeBarre(Boolean avecCodeBarre) {
        this.avecCodeBarre = avecCodeBarre;
        return this;
    }

    public void setAvecCodeBarre(Boolean avecCodeBarre) {
        this.avecCodeBarre = avecCodeBarre;
    }

    public String getConditionnement() {
        return this.conditionnement;
    }

    public Offre conditionnement(String conditionnement) {
        this.conditionnement = conditionnement;
        return this;
    }

    public void setConditionnement(String conditionnement) {
        this.conditionnement = conditionnement;
    }

    public Double getPrixConditionnement() {
        return this.prixConditionnement;
    }

    public Offre prixConditionnement(Double prixConditionnement) {
        this.prixConditionnement = prixConditionnement;
        return this;
    }

    public void setPrixConditionnement(Double prixConditionnement) {
        this.prixConditionnement = prixConditionnement;
    }

    public Tva getTva() {
        return this.tva;
    }

    public Offre tva(Tva tva) {
        this.setTva(tva);
        return this;
    }

    public void setTva(Tva tva) {
        this.tva = tva;
    }

    public Fournisseur getFournisseur() {
        return this.fournisseur;
    }

    public Offre fournisseur(Fournisseur fournisseur) {
        this.setFournisseur(fournisseur);
        return this;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public DemandeOffre getDemandeOffre() {
        return this.demandeOffre;
    }

    public Offre demandeOffre(DemandeOffre demandeOffre) {
        this.setDemandeOffre(demandeOffre);
        return this;
    }

    public void setDemandeOffre(DemandeOffre demandeOffre) {
        this.demandeOffre = demandeOffre;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Offre)) {
            return false;
        }
        return id != null && id.equals(((Offre) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Offre{" +
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
            "}";
    }
}
