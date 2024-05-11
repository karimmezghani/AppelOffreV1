package com.csys.appel.service;

import com.csys.appel.service.dto.CompteurDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.csys.appel.domain.Compteur}.
 */
public interface CompteurService {
    /**
     * Save a compteur.
     *
     * @param compteurDTO the entity to save.
     * @return the persisted entity.
     */
    CompteurDTO save(CompteurDTO compteurDTO);

    /**
     * Partially updates a compteur.
     *
     * @param compteurDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CompteurDTO> partialUpdate(CompteurDTO compteurDTO);

    /**
     * Get all the compteurs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CompteurDTO> findAll(Pageable pageable);

    /**
     * Get the "id" compteur.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompteurDTO> findOne(Long id);

    /**
     * Delete the "id" compteur.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
