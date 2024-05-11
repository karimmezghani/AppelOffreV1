package com.csys.appel.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.csys.appel.IntegrationTest;
import com.csys.appel.domain.AppelOffre;
import com.csys.appel.repository.AppelOffreRepository;
import com.csys.appel.service.dto.AppelOffreDTO;
import com.csys.appel.service.mapper.AppelOffreMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link AppelOffreResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AppelOffreResourceIT {

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_DEBUT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_DEBUT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_FIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_FIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_EXERCICE = "AAAAAAAAAA";
    private static final String UPDATED_EXERCICE = "BBBBBBBBBB";

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/appel-offres";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AppelOffreRepository appelOffreRepository;

    @Autowired
    private AppelOffreMapper appelOffreMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppelOffreMockMvc;

    private AppelOffre appelOffre;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppelOffre createEntity(EntityManager em) {
        AppelOffre appelOffre = new AppelOffre()
            .numero(DEFAULT_NUMERO)
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN)
            .exercice(DEFAULT_EXERCICE)
            .designation(DEFAULT_DESIGNATION);
        return appelOffre;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppelOffre createUpdatedEntity(EntityManager em) {
        AppelOffre appelOffre = new AppelOffre()
            .numero(UPDATED_NUMERO)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .exercice(UPDATED_EXERCICE)
            .designation(UPDATED_DESIGNATION);
        return appelOffre;
    }

    @BeforeEach
    public void initTest() {
        appelOffre = createEntity(em);
    }

    @Test
    @Transactional
    void createAppelOffre() throws Exception {
        int databaseSizeBeforeCreate = appelOffreRepository.findAll().size();
        // Create the AppelOffre
        AppelOffreDTO appelOffreDTO = appelOffreMapper.toDto(appelOffre);
        restAppelOffreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appelOffreDTO)))
            .andExpect(status().isCreated());

        // Validate the AppelOffre in the database
        List<AppelOffre> appelOffreList = appelOffreRepository.findAll();
        assertThat(appelOffreList).hasSize(databaseSizeBeforeCreate + 1);
        AppelOffre testAppelOffre = appelOffreList.get(appelOffreList.size() - 1);
        assertThat(testAppelOffre.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testAppelOffre.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testAppelOffre.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testAppelOffre.getExercice()).isEqualTo(DEFAULT_EXERCICE);
        assertThat(testAppelOffre.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
    }

    @Test
    @Transactional
    void createAppelOffreWithExistingId() throws Exception {
        // Create the AppelOffre with an existing ID
        appelOffre.setId(1L);
        AppelOffreDTO appelOffreDTO = appelOffreMapper.toDto(appelOffre);

        int databaseSizeBeforeCreate = appelOffreRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppelOffreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appelOffreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AppelOffre in the database
        List<AppelOffre> appelOffreList = appelOffreRepository.findAll();
        assertThat(appelOffreList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = appelOffreRepository.findAll().size();
        // set the field null
        appelOffre.setNumero(null);

        // Create the AppelOffre, which fails.
        AppelOffreDTO appelOffreDTO = appelOffreMapper.toDto(appelOffre);

        restAppelOffreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appelOffreDTO)))
            .andExpect(status().isBadRequest());

        List<AppelOffre> appelOffreList = appelOffreRepository.findAll();
        assertThat(appelOffreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateDebutIsRequired() throws Exception {
        int databaseSizeBeforeTest = appelOffreRepository.findAll().size();
        // set the field null
        appelOffre.setDateDebut(null);

        // Create the AppelOffre, which fails.
        AppelOffreDTO appelOffreDTO = appelOffreMapper.toDto(appelOffre);

        restAppelOffreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appelOffreDTO)))
            .andExpect(status().isBadRequest());

        List<AppelOffre> appelOffreList = appelOffreRepository.findAll();
        assertThat(appelOffreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateFinIsRequired() throws Exception {
        int databaseSizeBeforeTest = appelOffreRepository.findAll().size();
        // set the field null
        appelOffre.setDateFin(null);

        // Create the AppelOffre, which fails.
        AppelOffreDTO appelOffreDTO = appelOffreMapper.toDto(appelOffre);

        restAppelOffreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appelOffreDTO)))
            .andExpect(status().isBadRequest());

        List<AppelOffre> appelOffreList = appelOffreRepository.findAll();
        assertThat(appelOffreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkExerciceIsRequired() throws Exception {
        int databaseSizeBeforeTest = appelOffreRepository.findAll().size();
        // set the field null
        appelOffre.setExercice(null);

        // Create the AppelOffre, which fails.
        AppelOffreDTO appelOffreDTO = appelOffreMapper.toDto(appelOffre);

        restAppelOffreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appelOffreDTO)))
            .andExpect(status().isBadRequest());

        List<AppelOffre> appelOffreList = appelOffreRepository.findAll();
        assertThat(appelOffreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = appelOffreRepository.findAll().size();
        // set the field null
        appelOffre.setDesignation(null);

        // Create the AppelOffre, which fails.
        AppelOffreDTO appelOffreDTO = appelOffreMapper.toDto(appelOffre);

        restAppelOffreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appelOffreDTO)))
            .andExpect(status().isBadRequest());

        List<AppelOffre> appelOffreList = appelOffreRepository.findAll();
        assertThat(appelOffreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAppelOffres() throws Exception {
        // Initialize the database
        appelOffreRepository.saveAndFlush(appelOffre);

        // Get all the appelOffreList
        restAppelOffreMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appelOffre.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())))
            .andExpect(jsonPath("$.[*].exercice").value(hasItem(DEFAULT_EXERCICE)))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)));
    }

    @Test
    @Transactional
    void getAppelOffre() throws Exception {
        // Initialize the database
        appelOffreRepository.saveAndFlush(appelOffre);

        // Get the appelOffre
        restAppelOffreMockMvc
            .perform(get(ENTITY_API_URL_ID, appelOffre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appelOffre.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()))
            .andExpect(jsonPath("$.exercice").value(DEFAULT_EXERCICE))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION));
    }

    @Test
    @Transactional
    void getNonExistingAppelOffre() throws Exception {
        // Get the appelOffre
        restAppelOffreMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAppelOffre() throws Exception {
        // Initialize the database
        appelOffreRepository.saveAndFlush(appelOffre);

        int databaseSizeBeforeUpdate = appelOffreRepository.findAll().size();

        // Update the appelOffre
        AppelOffre updatedAppelOffre = appelOffreRepository.findById(appelOffre.getId()).get();
        // Disconnect from session so that the updates on updatedAppelOffre are not directly saved in db
        em.detach(updatedAppelOffre);
        updatedAppelOffre
            .numero(UPDATED_NUMERO)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .exercice(UPDATED_EXERCICE)
            .designation(UPDATED_DESIGNATION);
        AppelOffreDTO appelOffreDTO = appelOffreMapper.toDto(updatedAppelOffre);

        restAppelOffreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appelOffreDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appelOffreDTO))
            )
            .andExpect(status().isOk());

        // Validate the AppelOffre in the database
        List<AppelOffre> appelOffreList = appelOffreRepository.findAll();
        assertThat(appelOffreList).hasSize(databaseSizeBeforeUpdate);
        AppelOffre testAppelOffre = appelOffreList.get(appelOffreList.size() - 1);
        assertThat(testAppelOffre.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testAppelOffre.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testAppelOffre.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testAppelOffre.getExercice()).isEqualTo(UPDATED_EXERCICE);
        assertThat(testAppelOffre.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void putNonExistingAppelOffre() throws Exception {
        int databaseSizeBeforeUpdate = appelOffreRepository.findAll().size();
        appelOffre.setId(count.incrementAndGet());

        // Create the AppelOffre
        AppelOffreDTO appelOffreDTO = appelOffreMapper.toDto(appelOffre);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppelOffreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appelOffreDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appelOffreDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppelOffre in the database
        List<AppelOffre> appelOffreList = appelOffreRepository.findAll();
        assertThat(appelOffreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAppelOffre() throws Exception {
        int databaseSizeBeforeUpdate = appelOffreRepository.findAll().size();
        appelOffre.setId(count.incrementAndGet());

        // Create the AppelOffre
        AppelOffreDTO appelOffreDTO = appelOffreMapper.toDto(appelOffre);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppelOffreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appelOffreDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppelOffre in the database
        List<AppelOffre> appelOffreList = appelOffreRepository.findAll();
        assertThat(appelOffreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppelOffre() throws Exception {
        int databaseSizeBeforeUpdate = appelOffreRepository.findAll().size();
        appelOffre.setId(count.incrementAndGet());

        // Create the AppelOffre
        AppelOffreDTO appelOffreDTO = appelOffreMapper.toDto(appelOffre);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppelOffreMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appelOffreDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppelOffre in the database
        List<AppelOffre> appelOffreList = appelOffreRepository.findAll();
        assertThat(appelOffreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppelOffreWithPatch() throws Exception {
        // Initialize the database
        appelOffreRepository.saveAndFlush(appelOffre);

        int databaseSizeBeforeUpdate = appelOffreRepository.findAll().size();

        // Update the appelOffre using partial update
        AppelOffre partialUpdatedAppelOffre = new AppelOffre();
        partialUpdatedAppelOffre.setId(appelOffre.getId());

        partialUpdatedAppelOffre.numero(UPDATED_NUMERO).dateDebut(UPDATED_DATE_DEBUT);

        restAppelOffreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppelOffre.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppelOffre))
            )
            .andExpect(status().isOk());

        // Validate the AppelOffre in the database
        List<AppelOffre> appelOffreList = appelOffreRepository.findAll();
        assertThat(appelOffreList).hasSize(databaseSizeBeforeUpdate);
        AppelOffre testAppelOffre = appelOffreList.get(appelOffreList.size() - 1);
        assertThat(testAppelOffre.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testAppelOffre.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testAppelOffre.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testAppelOffre.getExercice()).isEqualTo(DEFAULT_EXERCICE);
        assertThat(testAppelOffre.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
    }

    @Test
    @Transactional
    void fullUpdateAppelOffreWithPatch() throws Exception {
        // Initialize the database
        appelOffreRepository.saveAndFlush(appelOffre);

        int databaseSizeBeforeUpdate = appelOffreRepository.findAll().size();

        // Update the appelOffre using partial update
        AppelOffre partialUpdatedAppelOffre = new AppelOffre();
        partialUpdatedAppelOffre.setId(appelOffre.getId());

        partialUpdatedAppelOffre
            .numero(UPDATED_NUMERO)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .exercice(UPDATED_EXERCICE)
            .designation(UPDATED_DESIGNATION);

        restAppelOffreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppelOffre.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppelOffre))
            )
            .andExpect(status().isOk());

        // Validate the AppelOffre in the database
        List<AppelOffre> appelOffreList = appelOffreRepository.findAll();
        assertThat(appelOffreList).hasSize(databaseSizeBeforeUpdate);
        AppelOffre testAppelOffre = appelOffreList.get(appelOffreList.size() - 1);
        assertThat(testAppelOffre.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testAppelOffre.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testAppelOffre.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testAppelOffre.getExercice()).isEqualTo(UPDATED_EXERCICE);
        assertThat(testAppelOffre.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void patchNonExistingAppelOffre() throws Exception {
        int databaseSizeBeforeUpdate = appelOffreRepository.findAll().size();
        appelOffre.setId(count.incrementAndGet());

        // Create the AppelOffre
        AppelOffreDTO appelOffreDTO = appelOffreMapper.toDto(appelOffre);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppelOffreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appelOffreDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appelOffreDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppelOffre in the database
        List<AppelOffre> appelOffreList = appelOffreRepository.findAll();
        assertThat(appelOffreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAppelOffre() throws Exception {
        int databaseSizeBeforeUpdate = appelOffreRepository.findAll().size();
        appelOffre.setId(count.incrementAndGet());

        // Create the AppelOffre
        AppelOffreDTO appelOffreDTO = appelOffreMapper.toDto(appelOffre);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppelOffreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appelOffreDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppelOffre in the database
        List<AppelOffre> appelOffreList = appelOffreRepository.findAll();
        assertThat(appelOffreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppelOffre() throws Exception {
        int databaseSizeBeforeUpdate = appelOffreRepository.findAll().size();
        appelOffre.setId(count.incrementAndGet());

        // Create the AppelOffre
        AppelOffreDTO appelOffreDTO = appelOffreMapper.toDto(appelOffre);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppelOffreMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(appelOffreDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppelOffre in the database
        List<AppelOffre> appelOffreList = appelOffreRepository.findAll();
        assertThat(appelOffreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAppelOffre() throws Exception {
        // Initialize the database
        appelOffreRepository.saveAndFlush(appelOffre);

        int databaseSizeBeforeDelete = appelOffreRepository.findAll().size();

        // Delete the appelOffre
        restAppelOffreMockMvc
            .perform(delete(ENTITY_API_URL_ID, appelOffre.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AppelOffre> appelOffreList = appelOffreRepository.findAll();
        assertThat(appelOffreList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
