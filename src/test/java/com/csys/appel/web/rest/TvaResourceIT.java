package com.csys.appel.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.csys.appel.IntegrationTest;
import com.csys.appel.domain.Tva;
import com.csys.appel.repository.TvaRepository;
import com.csys.appel.service.dto.TvaDTO;
import com.csys.appel.service.mapper.TvaMapper;
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
 * Integration tests for the {@link TvaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TvaResourceIT {

    private static final Double DEFAULT_TAUX_TVA = 1D;
    private static final Double UPDATED_TAUX_TVA = 2D;

    private static final String ENTITY_API_URL = "/api/tvas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TvaRepository tvaRepository;

    @Autowired
    private TvaMapper tvaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTvaMockMvc;

    private Tva tva;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tva createEntity(EntityManager em) {
        Tva tva = new Tva().tauxTva(DEFAULT_TAUX_TVA);
        return tva;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tva createUpdatedEntity(EntityManager em) {
        Tva tva = new Tva().tauxTva(UPDATED_TAUX_TVA);
        return tva;
    }

    @BeforeEach
    public void initTest() {
        tva = createEntity(em);
    }

    @Test
    @Transactional
    void createTva() throws Exception {
        int databaseSizeBeforeCreate = tvaRepository.findAll().size();
        // Create the Tva
        TvaDTO tvaDTO = tvaMapper.toDto(tva);
        restTvaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tvaDTO)))
            .andExpect(status().isCreated());

        // Validate the Tva in the database
        List<Tva> tvaList = tvaRepository.findAll();
        assertThat(tvaList).hasSize(databaseSizeBeforeCreate + 1);
        Tva testTva = tvaList.get(tvaList.size() - 1);
        assertThat(testTva.getTauxTva()).isEqualTo(DEFAULT_TAUX_TVA);
    }

    @Test
    @Transactional
    void createTvaWithExistingId() throws Exception {
        // Create the Tva with an existing ID
        tva.setId(1L);
        TvaDTO tvaDTO = tvaMapper.toDto(tva);

        int databaseSizeBeforeCreate = tvaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTvaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tvaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tva in the database
        List<Tva> tvaList = tvaRepository.findAll();
        assertThat(tvaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTauxTvaIsRequired() throws Exception {
        int databaseSizeBeforeTest = tvaRepository.findAll().size();
        // set the field null
        tva.setTauxTva(null);

        // Create the Tva, which fails.
        TvaDTO tvaDTO = tvaMapper.toDto(tva);

        restTvaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tvaDTO)))
            .andExpect(status().isBadRequest());

        List<Tva> tvaList = tvaRepository.findAll();
        assertThat(tvaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTvas() throws Exception {
        // Initialize the database
        tvaRepository.saveAndFlush(tva);

        // Get all the tvaList
        restTvaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tva.getId().intValue())))
            .andExpect(jsonPath("$.[*].tauxTva").value(hasItem(DEFAULT_TAUX_TVA.doubleValue())));
    }

    @Test
    @Transactional
    void getTva() throws Exception {
        // Initialize the database
        tvaRepository.saveAndFlush(tva);

        // Get the tva
        restTvaMockMvc
            .perform(get(ENTITY_API_URL_ID, tva.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tva.getId().intValue()))
            .andExpect(jsonPath("$.tauxTva").value(DEFAULT_TAUX_TVA.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingTva() throws Exception {
        // Get the tva
        restTvaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTva() throws Exception {
        // Initialize the database
        tvaRepository.saveAndFlush(tva);

        int databaseSizeBeforeUpdate = tvaRepository.findAll().size();

        // Update the tva
        Tva updatedTva = tvaRepository.findById(tva.getId()).get();
        // Disconnect from session so that the updates on updatedTva are not directly saved in db
        em.detach(updatedTva);
        updatedTva.tauxTva(UPDATED_TAUX_TVA);
        TvaDTO tvaDTO = tvaMapper.toDto(updatedTva);

        restTvaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tvaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tvaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Tva in the database
        List<Tva> tvaList = tvaRepository.findAll();
        assertThat(tvaList).hasSize(databaseSizeBeforeUpdate);
        Tva testTva = tvaList.get(tvaList.size() - 1);
        assertThat(testTva.getTauxTva()).isEqualTo(UPDATED_TAUX_TVA);
    }

    @Test
    @Transactional
    void putNonExistingTva() throws Exception {
        int databaseSizeBeforeUpdate = tvaRepository.findAll().size();
        tva.setId(count.incrementAndGet());

        // Create the Tva
        TvaDTO tvaDTO = tvaMapper.toDto(tva);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTvaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tvaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tvaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tva in the database
        List<Tva> tvaList = tvaRepository.findAll();
        assertThat(tvaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTva() throws Exception {
        int databaseSizeBeforeUpdate = tvaRepository.findAll().size();
        tva.setId(count.incrementAndGet());

        // Create the Tva
        TvaDTO tvaDTO = tvaMapper.toDto(tva);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTvaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tvaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tva in the database
        List<Tva> tvaList = tvaRepository.findAll();
        assertThat(tvaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTva() throws Exception {
        int databaseSizeBeforeUpdate = tvaRepository.findAll().size();
        tva.setId(count.incrementAndGet());

        // Create the Tva
        TvaDTO tvaDTO = tvaMapper.toDto(tva);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTvaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tvaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tva in the database
        List<Tva> tvaList = tvaRepository.findAll();
        assertThat(tvaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTvaWithPatch() throws Exception {
        // Initialize the database
        tvaRepository.saveAndFlush(tva);

        int databaseSizeBeforeUpdate = tvaRepository.findAll().size();

        // Update the tva using partial update
        Tva partialUpdatedTva = new Tva();
        partialUpdatedTva.setId(tva.getId());

        restTvaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTva.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTva))
            )
            .andExpect(status().isOk());

        // Validate the Tva in the database
        List<Tva> tvaList = tvaRepository.findAll();
        assertThat(tvaList).hasSize(databaseSizeBeforeUpdate);
        Tva testTva = tvaList.get(tvaList.size() - 1);
        assertThat(testTva.getTauxTva()).isEqualTo(DEFAULT_TAUX_TVA);
    }

    @Test
    @Transactional
    void fullUpdateTvaWithPatch() throws Exception {
        // Initialize the database
        tvaRepository.saveAndFlush(tva);

        int databaseSizeBeforeUpdate = tvaRepository.findAll().size();

        // Update the tva using partial update
        Tva partialUpdatedTva = new Tva();
        partialUpdatedTva.setId(tva.getId());

        partialUpdatedTva.tauxTva(UPDATED_TAUX_TVA);

        restTvaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTva.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTva))
            )
            .andExpect(status().isOk());

        // Validate the Tva in the database
        List<Tva> tvaList = tvaRepository.findAll();
        assertThat(tvaList).hasSize(databaseSizeBeforeUpdate);
        Tva testTva = tvaList.get(tvaList.size() - 1);
        assertThat(testTva.getTauxTva()).isEqualTo(UPDATED_TAUX_TVA);
    }

    @Test
    @Transactional
    void patchNonExistingTva() throws Exception {
        int databaseSizeBeforeUpdate = tvaRepository.findAll().size();
        tva.setId(count.incrementAndGet());

        // Create the Tva
        TvaDTO tvaDTO = tvaMapper.toDto(tva);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTvaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tvaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tvaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tva in the database
        List<Tva> tvaList = tvaRepository.findAll();
        assertThat(tvaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTva() throws Exception {
        int databaseSizeBeforeUpdate = tvaRepository.findAll().size();
        tva.setId(count.incrementAndGet());

        // Create the Tva
        TvaDTO tvaDTO = tvaMapper.toDto(tva);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTvaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tvaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tva in the database
        List<Tva> tvaList = tvaRepository.findAll();
        assertThat(tvaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTva() throws Exception {
        int databaseSizeBeforeUpdate = tvaRepository.findAll().size();
        tva.setId(count.incrementAndGet());

        // Create the Tva
        TvaDTO tvaDTO = tvaMapper.toDto(tva);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTvaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tvaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tva in the database
        List<Tva> tvaList = tvaRepository.findAll();
        assertThat(tvaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTva() throws Exception {
        // Initialize the database
        tvaRepository.saveAndFlush(tva);

        int databaseSizeBeforeDelete = tvaRepository.findAll().size();

        // Delete the tva
        restTvaMockMvc.perform(delete(ENTITY_API_URL_ID, tva.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tva> tvaList = tvaRepository.findAll();
        assertThat(tvaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
