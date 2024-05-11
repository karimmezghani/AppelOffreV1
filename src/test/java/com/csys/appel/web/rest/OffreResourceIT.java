package com.csys.appel.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.csys.appel.IntegrationTest;
import com.csys.appel.domain.Offre;
import com.csys.appel.repository.OffreRepository;
import com.csys.appel.service.dto.OffreDTO;
import com.csys.appel.service.mapper.OffreMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OffreResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OffreResourceIT {

    private static final String DEFAULT_UNITE_MESURE = "AAAAAAAAAA";
    private static final String UPDATED_UNITE_MESURE = "BBBBBBBBBB";

    private static final String DEFAULT_MARQUE = "AAAAAAAAAA";
    private static final String UPDATED_MARQUE = "BBBBBBBBBB";

    private static final Double DEFAULT_PRIX_UNITAIRE = 1D;
    private static final Double UPDATED_PRIX_UNITAIRE = 2D;

    private static final String DEFAULT_ORIGINE = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINE = "BBBBBBBBBB";

    private static final Integer DEFAULT_DELAI_LIVRAISON = 1;
    private static final Integer UPDATED_DELAI_LIVRAISON = 2;

    private static final String DEFAULT_OBSERVATION = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVATION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AMC = false;
    private static final Boolean UPDATED_AMC = true;

    private static final Boolean DEFAULT_ECHANTILLON = false;
    private static final Boolean UPDATED_ECHANTILLON = true;

    private static final Boolean DEFAULT_FODEC = false;
    private static final Boolean UPDATED_FODEC = true;

    private static final Boolean DEFAULT_AVEC_CODE_BARRE = false;
    private static final Boolean UPDATED_AVEC_CODE_BARRE = true;

    private static final String DEFAULT_CONDITIONNEMENT = "AAAAAAAAAA";
    private static final String UPDATED_CONDITIONNEMENT = "BBBBBBBBBB";

    private static final Double DEFAULT_PRIX_CONDITIONNEMENT = 1D;
    private static final Double UPDATED_PRIX_CONDITIONNEMENT = 2D;

    private static final String ENTITY_API_URL = "/api/offres";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OffreRepository offreRepository;

    @Autowired
    private OffreMapper offreMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOffreMockMvc;

    private Offre offre;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Offre createEntity(EntityManager em) {
        Offre offre = new Offre()
            .uniteMesure(DEFAULT_UNITE_MESURE)
            .marque(DEFAULT_MARQUE)
            .prixUnitaire(DEFAULT_PRIX_UNITAIRE)
            .origine(DEFAULT_ORIGINE)
            .delaiLivraison(DEFAULT_DELAI_LIVRAISON)
            .observation(DEFAULT_OBSERVATION)
            .amc(DEFAULT_AMC)
            .echantillon(DEFAULT_ECHANTILLON)
            .fodec(DEFAULT_FODEC)
            .avecCodeBarre(DEFAULT_AVEC_CODE_BARRE)
            .conditionnement(DEFAULT_CONDITIONNEMENT)
            .prixConditionnement(DEFAULT_PRIX_CONDITIONNEMENT);
        return offre;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Offre createUpdatedEntity(EntityManager em) {
        Offre offre = new Offre()
            .uniteMesure(UPDATED_UNITE_MESURE)
            .marque(UPDATED_MARQUE)
            .prixUnitaire(UPDATED_PRIX_UNITAIRE)
            .origine(UPDATED_ORIGINE)
            .delaiLivraison(UPDATED_DELAI_LIVRAISON)
            .observation(UPDATED_OBSERVATION)
            .amc(UPDATED_AMC)
            .echantillon(UPDATED_ECHANTILLON)
            .fodec(UPDATED_FODEC)
            .avecCodeBarre(UPDATED_AVEC_CODE_BARRE)
            .conditionnement(UPDATED_CONDITIONNEMENT)
            .prixConditionnement(UPDATED_PRIX_CONDITIONNEMENT);
        return offre;
    }

    @BeforeEach
    public void initTest() {
        offre = createEntity(em);
    }

    @Test
    @Transactional
    void createOffre() throws Exception {
        int databaseSizeBeforeCreate = offreRepository.findAll().size();
        // Create the Offre
        OffreDTO offreDTO = offreMapper.toDto(offre);
        restOffreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(offreDTO)))
            .andExpect(status().isCreated());

        // Validate the Offre in the database
        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeCreate + 1);
        Offre testOffre = offreList.get(offreList.size() - 1);
        assertThat(testOffre.getUniteMesure()).isEqualTo(DEFAULT_UNITE_MESURE);
        assertThat(testOffre.getMarque()).isEqualTo(DEFAULT_MARQUE);
        assertThat(testOffre.getPrixUnitaire()).isEqualTo(DEFAULT_PRIX_UNITAIRE);
        assertThat(testOffre.getOrigine()).isEqualTo(DEFAULT_ORIGINE);
        assertThat(testOffre.getDelaiLivraison()).isEqualTo(DEFAULT_DELAI_LIVRAISON);
        assertThat(testOffre.getObservation()).isEqualTo(DEFAULT_OBSERVATION);
        assertThat(testOffre.getAmc()).isEqualTo(DEFAULT_AMC);
        assertThat(testOffre.getEchantillon()).isEqualTo(DEFAULT_ECHANTILLON);
        assertThat(testOffre.getFodec()).isEqualTo(DEFAULT_FODEC);
        assertThat(testOffre.getAvecCodeBarre()).isEqualTo(DEFAULT_AVEC_CODE_BARRE);
        assertThat(testOffre.getConditionnement()).isEqualTo(DEFAULT_CONDITIONNEMENT);
        assertThat(testOffre.getPrixConditionnement()).isEqualTo(DEFAULT_PRIX_CONDITIONNEMENT);
    }

    @Test
    @Transactional
    void createOffreWithExistingId() throws Exception {
        // Create the Offre with an existing ID
        offre.setId(1L);
        OffreDTO offreDTO = offreMapper.toDto(offre);

        int databaseSizeBeforeCreate = offreRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOffreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(offreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Offre in the database
        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkUniteMesureIsRequired() throws Exception {
        int databaseSizeBeforeTest = offreRepository.findAll().size();
        // set the field null
        offre.setUniteMesure(null);

        // Create the Offre, which fails.
        OffreDTO offreDTO = offreMapper.toDto(offre);

        restOffreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(offreDTO)))
            .andExpect(status().isBadRequest());

        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMarqueIsRequired() throws Exception {
        int databaseSizeBeforeTest = offreRepository.findAll().size();
        // set the field null
        offre.setMarque(null);

        // Create the Offre, which fails.
        OffreDTO offreDTO = offreMapper.toDto(offre);

        restOffreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(offreDTO)))
            .andExpect(status().isBadRequest());

        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrixUnitaireIsRequired() throws Exception {
        int databaseSizeBeforeTest = offreRepository.findAll().size();
        // set the field null
        offre.setPrixUnitaire(null);

        // Create the Offre, which fails.
        OffreDTO offreDTO = offreMapper.toDto(offre);

        restOffreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(offreDTO)))
            .andExpect(status().isBadRequest());

        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOrigineIsRequired() throws Exception {
        int databaseSizeBeforeTest = offreRepository.findAll().size();
        // set the field null
        offre.setOrigine(null);

        // Create the Offre, which fails.
        OffreDTO offreDTO = offreMapper.toDto(offre);

        restOffreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(offreDTO)))
            .andExpect(status().isBadRequest());

        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDelaiLivraisonIsRequired() throws Exception {
        int databaseSizeBeforeTest = offreRepository.findAll().size();
        // set the field null
        offre.setDelaiLivraison(null);

        // Create the Offre, which fails.
        OffreDTO offreDTO = offreMapper.toDto(offre);

        restOffreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(offreDTO)))
            .andExpect(status().isBadRequest());

        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOffres() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList
        restOffreMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offre.getId().intValue())))
            .andExpect(jsonPath("$.[*].uniteMesure").value(hasItem(DEFAULT_UNITE_MESURE)))
            .andExpect(jsonPath("$.[*].marque").value(hasItem(DEFAULT_MARQUE)))
            .andExpect(jsonPath("$.[*].prixUnitaire").value(hasItem(DEFAULT_PRIX_UNITAIRE.doubleValue())))
            .andExpect(jsonPath("$.[*].origine").value(hasItem(DEFAULT_ORIGINE)))
            .andExpect(jsonPath("$.[*].delaiLivraison").value(hasItem(DEFAULT_DELAI_LIVRAISON)))
            .andExpect(jsonPath("$.[*].observation").value(hasItem(DEFAULT_OBSERVATION)))
            .andExpect(jsonPath("$.[*].amc").value(hasItem(DEFAULT_AMC.booleanValue())))
            .andExpect(jsonPath("$.[*].echantillon").value(hasItem(DEFAULT_ECHANTILLON.booleanValue())))
            .andExpect(jsonPath("$.[*].fodec").value(hasItem(DEFAULT_FODEC.booleanValue())))
            .andExpect(jsonPath("$.[*].avecCodeBarre").value(hasItem(DEFAULT_AVEC_CODE_BARRE.booleanValue())))
            .andExpect(jsonPath("$.[*].conditionnement").value(hasItem(DEFAULT_CONDITIONNEMENT)))
            .andExpect(jsonPath("$.[*].prixConditionnement").value(hasItem(DEFAULT_PRIX_CONDITIONNEMENT.doubleValue())));
    }

    @Test
    @Transactional
    void getOffre() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get the offre
        restOffreMockMvc
            .perform(get(ENTITY_API_URL_ID, offre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(offre.getId().intValue()))
            .andExpect(jsonPath("$.uniteMesure").value(DEFAULT_UNITE_MESURE))
            .andExpect(jsonPath("$.marque").value(DEFAULT_MARQUE))
            .andExpect(jsonPath("$.prixUnitaire").value(DEFAULT_PRIX_UNITAIRE.doubleValue()))
            .andExpect(jsonPath("$.origine").value(DEFAULT_ORIGINE))
            .andExpect(jsonPath("$.delaiLivraison").value(DEFAULT_DELAI_LIVRAISON))
            .andExpect(jsonPath("$.observation").value(DEFAULT_OBSERVATION))
            .andExpect(jsonPath("$.amc").value(DEFAULT_AMC.booleanValue()))
            .andExpect(jsonPath("$.echantillon").value(DEFAULT_ECHANTILLON.booleanValue()))
            .andExpect(jsonPath("$.fodec").value(DEFAULT_FODEC.booleanValue()))
            .andExpect(jsonPath("$.avecCodeBarre").value(DEFAULT_AVEC_CODE_BARRE.booleanValue()))
            .andExpect(jsonPath("$.conditionnement").value(DEFAULT_CONDITIONNEMENT))
            .andExpect(jsonPath("$.prixConditionnement").value(DEFAULT_PRIX_CONDITIONNEMENT.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingOffre() throws Exception {
        // Get the offre
        restOffreMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOffre() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        int databaseSizeBeforeUpdate = offreRepository.findAll().size();

        // Update the offre
        Offre updatedOffre = offreRepository.findById(offre.getId()).get();
        // Disconnect from session so that the updates on updatedOffre are not directly saved in db
        em.detach(updatedOffre);
        updatedOffre
            .uniteMesure(UPDATED_UNITE_MESURE)
            .marque(UPDATED_MARQUE)
            .prixUnitaire(UPDATED_PRIX_UNITAIRE)
            .origine(UPDATED_ORIGINE)
            .delaiLivraison(UPDATED_DELAI_LIVRAISON)
            .observation(UPDATED_OBSERVATION)
            .amc(UPDATED_AMC)
            .echantillon(UPDATED_ECHANTILLON)
            .fodec(UPDATED_FODEC)
            .avecCodeBarre(UPDATED_AVEC_CODE_BARRE)
            .conditionnement(UPDATED_CONDITIONNEMENT)
            .prixConditionnement(UPDATED_PRIX_CONDITIONNEMENT);
        OffreDTO offreDTO = offreMapper.toDto(updatedOffre);

        restOffreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, offreDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(offreDTO))
            )
            .andExpect(status().isOk());

        // Validate the Offre in the database
        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeUpdate);
        Offre testOffre = offreList.get(offreList.size() - 1);
        assertThat(testOffre.getUniteMesure()).isEqualTo(UPDATED_UNITE_MESURE);
        assertThat(testOffre.getMarque()).isEqualTo(UPDATED_MARQUE);
        assertThat(testOffre.getPrixUnitaire()).isEqualTo(UPDATED_PRIX_UNITAIRE);
        assertThat(testOffre.getOrigine()).isEqualTo(UPDATED_ORIGINE);
        assertThat(testOffre.getDelaiLivraison()).isEqualTo(UPDATED_DELAI_LIVRAISON);
        assertThat(testOffre.getObservation()).isEqualTo(UPDATED_OBSERVATION);
        assertThat(testOffre.getAmc()).isEqualTo(UPDATED_AMC);
        assertThat(testOffre.getEchantillon()).isEqualTo(UPDATED_ECHANTILLON);
        assertThat(testOffre.getFodec()).isEqualTo(UPDATED_FODEC);
        assertThat(testOffre.getAvecCodeBarre()).isEqualTo(UPDATED_AVEC_CODE_BARRE);
        assertThat(testOffre.getConditionnement()).isEqualTo(UPDATED_CONDITIONNEMENT);
        assertThat(testOffre.getPrixConditionnement()).isEqualTo(UPDATED_PRIX_CONDITIONNEMENT);
    }

    @Test
    @Transactional
    void putNonExistingOffre() throws Exception {
        int databaseSizeBeforeUpdate = offreRepository.findAll().size();
        offre.setId(count.incrementAndGet());

        // Create the Offre
        OffreDTO offreDTO = offreMapper.toDto(offre);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOffreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, offreDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(offreDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Offre in the database
        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOffre() throws Exception {
        int databaseSizeBeforeUpdate = offreRepository.findAll().size();
        offre.setId(count.incrementAndGet());

        // Create the Offre
        OffreDTO offreDTO = offreMapper.toDto(offre);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOffreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(offreDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Offre in the database
        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOffre() throws Exception {
        int databaseSizeBeforeUpdate = offreRepository.findAll().size();
        offre.setId(count.incrementAndGet());

        // Create the Offre
        OffreDTO offreDTO = offreMapper.toDto(offre);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOffreMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(offreDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Offre in the database
        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOffreWithPatch() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        int databaseSizeBeforeUpdate = offreRepository.findAll().size();

        // Update the offre using partial update
        Offre partialUpdatedOffre = new Offre();
        partialUpdatedOffre.setId(offre.getId());

        partialUpdatedOffre
            .uniteMesure(UPDATED_UNITE_MESURE)
            .marque(UPDATED_MARQUE)
            .prixUnitaire(UPDATED_PRIX_UNITAIRE)
            .origine(UPDATED_ORIGINE)
            .delaiLivraison(UPDATED_DELAI_LIVRAISON)
            .amc(UPDATED_AMC)
            .echantillon(UPDATED_ECHANTILLON);

        restOffreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOffre.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOffre))
            )
            .andExpect(status().isOk());

        // Validate the Offre in the database
        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeUpdate);
        Offre testOffre = offreList.get(offreList.size() - 1);
        assertThat(testOffre.getUniteMesure()).isEqualTo(UPDATED_UNITE_MESURE);
        assertThat(testOffre.getMarque()).isEqualTo(UPDATED_MARQUE);
        assertThat(testOffre.getPrixUnitaire()).isEqualTo(UPDATED_PRIX_UNITAIRE);
        assertThat(testOffre.getOrigine()).isEqualTo(UPDATED_ORIGINE);
        assertThat(testOffre.getDelaiLivraison()).isEqualTo(UPDATED_DELAI_LIVRAISON);
        assertThat(testOffre.getObservation()).isEqualTo(DEFAULT_OBSERVATION);
        assertThat(testOffre.getAmc()).isEqualTo(UPDATED_AMC);
        assertThat(testOffre.getEchantillon()).isEqualTo(UPDATED_ECHANTILLON);
        assertThat(testOffre.getFodec()).isEqualTo(DEFAULT_FODEC);
        assertThat(testOffre.getAvecCodeBarre()).isEqualTo(DEFAULT_AVEC_CODE_BARRE);
        assertThat(testOffre.getConditionnement()).isEqualTo(DEFAULT_CONDITIONNEMENT);
        assertThat(testOffre.getPrixConditionnement()).isEqualTo(DEFAULT_PRIX_CONDITIONNEMENT);
    }

    @Test
    @Transactional
    void fullUpdateOffreWithPatch() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        int databaseSizeBeforeUpdate = offreRepository.findAll().size();

        // Update the offre using partial update
        Offre partialUpdatedOffre = new Offre();
        partialUpdatedOffre.setId(offre.getId());

        partialUpdatedOffre
            .uniteMesure(UPDATED_UNITE_MESURE)
            .marque(UPDATED_MARQUE)
            .prixUnitaire(UPDATED_PRIX_UNITAIRE)
            .origine(UPDATED_ORIGINE)
            .delaiLivraison(UPDATED_DELAI_LIVRAISON)
            .observation(UPDATED_OBSERVATION)
            .amc(UPDATED_AMC)
            .echantillon(UPDATED_ECHANTILLON)
            .fodec(UPDATED_FODEC)
            .avecCodeBarre(UPDATED_AVEC_CODE_BARRE)
            .conditionnement(UPDATED_CONDITIONNEMENT)
            .prixConditionnement(UPDATED_PRIX_CONDITIONNEMENT);

        restOffreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOffre.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOffre))
            )
            .andExpect(status().isOk());

        // Validate the Offre in the database
        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeUpdate);
        Offre testOffre = offreList.get(offreList.size() - 1);
        assertThat(testOffre.getUniteMesure()).isEqualTo(UPDATED_UNITE_MESURE);
        assertThat(testOffre.getMarque()).isEqualTo(UPDATED_MARQUE);
        assertThat(testOffre.getPrixUnitaire()).isEqualTo(UPDATED_PRIX_UNITAIRE);
        assertThat(testOffre.getOrigine()).isEqualTo(UPDATED_ORIGINE);
        assertThat(testOffre.getDelaiLivraison()).isEqualTo(UPDATED_DELAI_LIVRAISON);
        assertThat(testOffre.getObservation()).isEqualTo(UPDATED_OBSERVATION);
        assertThat(testOffre.getAmc()).isEqualTo(UPDATED_AMC);
        assertThat(testOffre.getEchantillon()).isEqualTo(UPDATED_ECHANTILLON);
        assertThat(testOffre.getFodec()).isEqualTo(UPDATED_FODEC);
        assertThat(testOffre.getAvecCodeBarre()).isEqualTo(UPDATED_AVEC_CODE_BARRE);
        assertThat(testOffre.getConditionnement()).isEqualTo(UPDATED_CONDITIONNEMENT);
        assertThat(testOffre.getPrixConditionnement()).isEqualTo(UPDATED_PRIX_CONDITIONNEMENT);
    }

    @Test
    @Transactional
    void patchNonExistingOffre() throws Exception {
        int databaseSizeBeforeUpdate = offreRepository.findAll().size();
        offre.setId(count.incrementAndGet());

        // Create the Offre
        OffreDTO offreDTO = offreMapper.toDto(offre);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOffreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, offreDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(offreDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Offre in the database
        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOffre() throws Exception {
        int databaseSizeBeforeUpdate = offreRepository.findAll().size();
        offre.setId(count.incrementAndGet());

        // Create the Offre
        OffreDTO offreDTO = offreMapper.toDto(offre);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOffreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(offreDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Offre in the database
        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOffre() throws Exception {
        int databaseSizeBeforeUpdate = offreRepository.findAll().size();
        offre.setId(count.incrementAndGet());

        // Create the Offre
        OffreDTO offreDTO = offreMapper.toDto(offre);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOffreMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(offreDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Offre in the database
        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOffre() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        int databaseSizeBeforeDelete = offreRepository.findAll().size();

        // Delete the offre
        restOffreMockMvc
            .perform(delete(ENTITY_API_URL_ID, offre.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
