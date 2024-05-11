package com.csys.appel.service;

import com.csys.appel.service.dto.TvaDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.csys.appel.domain.Tva}.
 */
public interface TvaService {
    /**
     * Save a tva.
     *
     * @param tvaDTO the entity to save.
     * @return the persisted entity.
     */
    TvaDTO save(TvaDTO tvaDTO);

    /**
     * Partially updates a tva.
     *
     * @param tvaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TvaDTO> partialUpdate(TvaDTO tvaDTO);

    /**
     * Get all the tvas.
     *
     * @return the list of entities.
     */
    List<TvaDTO> findAll();

    /**
     * Get the "id" tva.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TvaDTO> findOne(Long id);

    /**
     * Delete the "id" tva.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
