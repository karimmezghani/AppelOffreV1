package com.csys.appel.service;

import com.csys.appel.service.dto.AppelOffreDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.csys.appel.domain.AppelOffre}.
 */
public interface AppelOffreService {
    /**
     * Save a appelOffre.
     *
     * @param appelOffreDTO the entity to save.
     * @return the persisted entity.
     */
    AppelOffreDTO save(AppelOffreDTO appelOffreDTO);

    /**
     * Partially updates a appelOffre.
     *
     * @param appelOffreDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AppelOffreDTO> partialUpdate(AppelOffreDTO appelOffreDTO);

    /**
     * Get all the appelOffres.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AppelOffreDTO> findAll(Pageable pageable);

    /**
     * Get the "id" appelOffre.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AppelOffreDTO> findOne(Long id);

    /**
     * Delete the "id" appelOffre.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
