package com.csys.appel.web.rest;

import com.csys.appel.repository.TvaRepository;
import com.csys.appel.service.TvaService;
import com.csys.appel.service.dto.TvaDTO;
import com.csys.appel.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.csys.appel.domain.Tva}.
 */
@RestController
@RequestMapping("/api")
public class TvaResource {

    private final Logger log = LoggerFactory.getLogger(TvaResource.class);

    private static final String ENTITY_NAME = "tva";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TvaService tvaService;

    private final TvaRepository tvaRepository;

    public TvaResource(TvaService tvaService, TvaRepository tvaRepository) {
        this.tvaService = tvaService;
        this.tvaRepository = tvaRepository;
    }

    /**
     * {@code POST  /tvas} : Create a new tva.
     *
     * @param tvaDTO the tvaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tvaDTO, or with status {@code 400 (Bad Request)} if the tva has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tvas")
    public ResponseEntity<TvaDTO> createTva(@Valid @RequestBody TvaDTO tvaDTO) throws URISyntaxException {
        log.debug("REST request to save Tva : {}", tvaDTO);
        if (tvaDTO.getId() != null) {
            throw new BadRequestAlertException("A new tva cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TvaDTO result = tvaService.save(tvaDTO);
        return ResponseEntity
            .created(new URI("/api/tvas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tvas/:id} : Updates an existing tva.
     *
     * @param id the id of the tvaDTO to save.
     * @param tvaDTO the tvaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tvaDTO,
     * or with status {@code 400 (Bad Request)} if the tvaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tvaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tvas/{id}")
    public ResponseEntity<TvaDTO> updateTva(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody TvaDTO tvaDTO)
        throws URISyntaxException {
        log.debug("REST request to update Tva : {}, {}", id, tvaDTO);
        if (tvaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tvaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tvaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TvaDTO result = tvaService.save(tvaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tvaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tvas/:id} : Partial updates given fields of an existing tva, field will ignore if it is null
     *
     * @param id the id of the tvaDTO to save.
     * @param tvaDTO the tvaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tvaDTO,
     * or with status {@code 400 (Bad Request)} if the tvaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tvaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tvaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tvas/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TvaDTO> partialUpdateTva(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TvaDTO tvaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Tva partially : {}, {}", id, tvaDTO);
        if (tvaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tvaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tvaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TvaDTO> result = tvaService.partialUpdate(tvaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tvaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tvas} : get all the tvas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tvas in body.
     */
    @GetMapping("/tvas")
    public List<TvaDTO> getAllTvas() {
        log.debug("REST request to get all Tvas");
        return tvaService.findAll();
    }

    /**
     * {@code GET  /tvas/:id} : get the "id" tva.
     *
     * @param id the id of the tvaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tvaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tvas/{id}")
    public ResponseEntity<TvaDTO> getTva(@PathVariable Long id) {
        log.debug("REST request to get Tva : {}", id);
        Optional<TvaDTO> tvaDTO = tvaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tvaDTO);
    }

    /**
     * {@code DELETE  /tvas/:id} : delete the "id" tva.
     *
     * @param id the id of the tvaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tvas/{id}")
    public ResponseEntity<Void> deleteTva(@PathVariable Long id) {
        log.debug("REST request to delete Tva : {}", id);
        tvaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
