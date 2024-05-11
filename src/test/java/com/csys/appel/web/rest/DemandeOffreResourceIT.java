package com.csys.appel.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.csys.appel.IntegrationTest;
import com.csys.appel.domain.DemandeOffre;
import com.csys.appel.repository.DemandeOffreRepository;
import com.csys.appel.service.dto.DemandeOffreDTO;
import com.csys.appel.service.mapper.DemandeOffreMapper;
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
 * Integration tests for the {@link DemandeOffreResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DemandeOffreResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITE = 1;
    private static final Integer UPDATED_QUANTITE = 2;

    private static final String ENTITY_API_URL = "/api/demande-offres";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DemandeOffreRepository demandeOffreRepository;

    @Autowired
    private DemandeOffreMapper demandeOffreMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDemandeOffreMockMvc;

    private DemandeOffre demandeOffre;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemandeOffre createEntity(EntityManager em) {
        DemandeOffre demandeOffre = new DemandeOffre().description(DEFAULT_DESCRIPTION).quantite(DEFAULT_QUANTITE);
        return demandeOffre;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemandeOffre createUpdatedEntity(EntityManager em) {
        DemandeOffre demandeOffre = new DemandeOffre().description(UPDATED_DESCRIPTION).quantite(UPDATED_QUANTITE);
        return demandeOffre;
    }

    @BeforeEach
    public void initTest() {
        demandeOffre = createEntity(em);
    }

    @Test
    @Transactional
    void createDemandeOffre() throws Exception {
        int databaseSizeBeforeCreate = demandeOffreRepository.findAll().size();
        // Create the DemandeOffre
        DemandeOffreDTO demandeOffreDTO = demandeOffreMapper.toDto(demandeOffre);
        restDemandeOffreMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeOffreDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DemandeOffre in the database
        List<DemandeOffre> demandeOffreList = demandeOffreRepository.findAll();
        assertThat(demandeOffreList).hasSize(databaseSizeBeforeCreate + 1);
        DemandeOffre testDemandeOffre = demandeOffreList.get(demandeOffreList.size() - 1);
        assertThat(testDemandeOffre.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDemandeOffre.getQuantite()).isEqualTo(DEFAULT_QUANTITE);
    }

    @Test
    @Transactional
    void createDemandeOffreWithExistingId() throws Exception {
        // Create the DemandeOffre with an existing ID
        demandeOffre.setId(1L);
        DemandeOffreDTO demandeOffreDTO = demandeOffreMapper.toDto(demandeOffre);

        int databaseSizeBeforeCreate = demandeOffreRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDemandeOffreMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeOffreDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeOffre in the database
        List<DemandeOffre> demandeOffreList = demandeOffreRepository.findAll();
        assertThat(demandeOffreList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeOffreRepository.findAll().size();
        // set the field null
        demandeOffre.setDescription(null);

        // Create the DemandeOffre, which fails.
        DemandeOffreDTO demandeOffreDTO = demandeOffreMapper.toDto(demandeOffre);

        restDemandeOffreMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeOffreDTO))
            )
            .andExpect(status().isBadRequest());

        List<DemandeOffre> demandeOffreList = demandeOffreRepository.findAll();
        assertThat(demandeOffreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkQuantiteIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeOffreRepository.findAll().size();
        // set the field null
        demandeOffre.setQuantite(null);

        // Create the DemandeOffre, which fails.
        DemandeOffreDTO demandeOffreDTO = demandeOffreMapper.toDto(demandeOffre);

        restDemandeOffreMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeOffreDTO))
            )
            .andExpect(status().isBadRequest());

        List<DemandeOffre> demandeOffreList = demandeOffreRepository.findAll();
        assertThat(demandeOffreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDemandeOffres() throws Exception {
        // Initialize the database
        demandeOffreRepository.saveAndFlush(demandeOffre);

        // Get all the demandeOffreList
        restDemandeOffreMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demandeOffre.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE)));
    }

    @Test
    @Transactional
    void getDemandeOffre() throws Exception {
        // Initialize the database
        demandeOffreRepository.saveAndFlush(demandeOffre);

        // Get the demandeOffre
        restDemandeOffreMockMvc
            .perform(get(ENTITY_API_URL_ID, demandeOffre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(demandeOffre.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.quantite").value(DEFAULT_QUANTITE));
    }

    @Test
    @Transactional
    void getNonExistingDemandeOffre() throws Exception {
        // Get the demandeOffre
        restDemandeOffreMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDemandeOffre() throws Exception {
        // Initialize the database
        demandeOffreRepository.saveAndFlush(demandeOffre);

        int databaseSizeBeforeUpdate = demandeOffreRepository.findAll().size();

        // Update the demandeOffre
        DemandeOffre updatedDemandeOffre = demandeOffreRepository.findById(demandeOffre.getId()).get();
        // Disconnect from session so that the updates on updatedDemandeOffre are not directly saved in db
        em.detach(updatedDemandeOffre);
        updatedDemandeOffre.description(UPDATED_DESCRIPTION).quantite(UPDATED_QUANTITE);
        DemandeOffreDTO demandeOffreDTO = demandeOffreMapper.toDto(updatedDemandeOffre);

        restDemandeOffreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, demandeOffreDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeOffreDTO))
            )
            .andExpect(status().isOk());

        // Validate the DemandeOffre in the database
        List<DemandeOffre> demandeOffreList = demandeOffreRepository.findAll();
        assertThat(demandeOffreList).hasSize(databaseSizeBeforeUpdate);
        DemandeOffre testDemandeOffre = demandeOffreList.get(demandeOffreList.size() - 1);
        assertThat(testDemandeOffre.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDemandeOffre.getQuantite()).isEqualTo(UPDATED_QUANTITE);
    }

    @Test
    @Transactional
    void putNonExistingDemandeOffre() throws Exception {
        int databaseSizeBeforeUpdate = demandeOffreRepository.findAll().size();
        demandeOffre.setId(count.incrementAndGet());

        // Create the DemandeOffre
        DemandeOffreDTO demandeOffreDTO = demandeOffreMapper.toDto(demandeOffre);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandeOffreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, demandeOffreDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeOffreDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeOffre in the database
        List<DemandeOffre> demandeOffreList = demandeOffreRepository.findAll();
        assertThat(demandeOffreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDemandeOffre() throws Exception {
        int databaseSizeBeforeUpdate = demandeOffreRepository.findAll().size();
        demandeOffre.setId(count.incrementAndGet());

        // Create the DemandeOffre
        DemandeOffreDTO demandeOffreDTO = demandeOffreMapper.toDto(demandeOffre);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeOffreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeOffreDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeOffre in the database
        List<DemandeOffre> demandeOffreList = demandeOffreRepository.findAll();
        assertThat(demandeOffreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDemandeOffre() throws Exception {
        int databaseSizeBeforeUpdate = demandeOffreRepository.findAll().size();
        demandeOffre.setId(count.incrementAndGet());

        // Create the DemandeOffre
        DemandeOffreDTO demandeOffreDTO = demandeOffreMapper.toDto(demandeOffre);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeOffreMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeOffreDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DemandeOffre in the database
        List<DemandeOffre> demandeOffreList = demandeOffreRepository.findAll();
        assertThat(demandeOffreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDemandeOffreWithPatch() throws Exception {
        // Initialize the database
        demandeOffreRepository.saveAndFlush(demandeOffre);

        int databaseSizeBeforeUpdate = demandeOffreRepository.findAll().size();

        // Update the demandeOffre using partial update
        DemandeOffre partialUpdatedDemandeOffre = new DemandeOffre();
        partialUpdatedDemandeOffre.setId(demandeOffre.getId());

        partialUpdatedDemandeOffre.description(UPDATED_DESCRIPTION);

        restDemandeOffreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemandeOffre.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemandeOffre))
            )
            .andExpect(status().isOk());

        // Validate the DemandeOffre in the database
        List<DemandeOffre> demandeOffreList = demandeOffreRepository.findAll();
        assertThat(demandeOffreList).hasSize(databaseSizeBeforeUpdate);
        DemandeOffre testDemandeOffre = demandeOffreList.get(demandeOffreList.size() - 1);
        assertThat(testDemandeOffre.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDemandeOffre.getQuantite()).isEqualTo(DEFAULT_QUANTITE);
    }

    @Test
    @Transactional
    void fullUpdateDemandeOffreWithPatch() throws Exception {
        // Initialize the database
        demandeOffreRepository.saveAndFlush(demandeOffre);

        int databaseSizeBeforeUpdate = demandeOffreRepository.findAll().size();

        // Update the demandeOffre using partial update
        DemandeOffre partialUpdatedDemandeOffre = new DemandeOffre();
        partialUpdatedDemandeOffre.setId(demandeOffre.getId());

        partialUpdatedDemandeOffre.description(UPDATED_DESCRIPTION).quantite(UPDATED_QUANTITE);

        restDemandeOffreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemandeOffre.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemandeOffre))
            )
            .andExpect(status().isOk());

        // Validate the DemandeOffre in the database
        List<DemandeOffre> demandeOffreList = demandeOffreRepository.findAll();
        assertThat(demandeOffreList).hasSize(databaseSizeBeforeUpdate);
        DemandeOffre testDemandeOffre = demandeOffreList.get(demandeOffreList.size() - 1);
        assertThat(testDemandeOffre.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDemandeOffre.getQuantite()).isEqualTo(UPDATED_QUANTITE);
    }

    @Test
    @Transactional
    void patchNonExistingDemandeOffre() throws Exception {
        int databaseSizeBeforeUpdate = demandeOffreRepository.findAll().size();
        demandeOffre.setId(count.incrementAndGet());

        // Create the DemandeOffre
        DemandeOffreDTO demandeOffreDTO = demandeOffreMapper.toDto(demandeOffre);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandeOffreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, demandeOffreDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandeOffreDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeOffre in the database
        List<DemandeOffre> demandeOffreList = demandeOffreRepository.findAll();
        assertThat(demandeOffreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDemandeOffre() throws Exception {
        int databaseSizeBeforeUpdate = demandeOffreRepository.findAll().size();
        demandeOffre.setId(count.incrementAndGet());

        // Create the DemandeOffre
        DemandeOffreDTO demandeOffreDTO = demandeOffreMapper.toDto(demandeOffre);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeOffreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandeOffreDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeOffre in the database
        List<DemandeOffre> demandeOffreList = demandeOffreRepository.findAll();
        assertThat(demandeOffreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDemandeOffre() throws Exception {
        int databaseSizeBeforeUpdate = demandeOffreRepository.findAll().size();
        demandeOffre.setId(count.incrementAndGet());

        // Create the DemandeOffre
        DemandeOffreDTO demandeOffreDTO = demandeOffreMapper.toDto(demandeOffre);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeOffreMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandeOffreDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DemandeOffre in the database
        List<DemandeOffre> demandeOffreList = demandeOffreRepository.findAll();
        assertThat(demandeOffreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDemandeOffre() throws Exception {
        // Initialize the database
        demandeOffreRepository.saveAndFlush(demandeOffre);

        int databaseSizeBeforeDelete = demandeOffreRepository.findAll().size();

        // Delete the demandeOffre
        restDemandeOffreMockMvc
            .perform(delete(ENTITY_API_URL_ID, demandeOffre.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DemandeOffre> demandeOffreList = demandeOffreRepository.findAll();
        assertThat(demandeOffreList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
