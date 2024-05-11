package com.csys.appel.service;

import com.csys.appel.service.dto.DemandeOffreDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.csys.appel.domain.DemandeOffre}.
 */
public interface DemandeOffreService {
    /**
     * Save a demandeOffre.
     *
     * @param demandeOffreDTO the entity to save.
     * @return the persisted entity.
     */
    DemandeOffreDTO save(DemandeOffreDTO demandeOffreDTO);

    /**
     * Partially updates a demandeOffre.
     *
     * @param demandeOffreDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DemandeOffreDTO> partialUpdate(DemandeOffreDTO demandeOffreDTO);

    /**
     * Get all the demandeOffres.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DemandeOffreDTO> findAll(Pageable pageable);

    /**
     * Get the "id" demandeOffre.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DemandeOffreDTO> findOne(Long id);

    /**
     * Delete the "id" demandeOffre.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
