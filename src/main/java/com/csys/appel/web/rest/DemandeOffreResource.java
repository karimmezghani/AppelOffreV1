package com.csys.appel.web.rest;

import com.csys.appel.repository.DemandeOffreRepository;
import com.csys.appel.service.DemandeOffreService;
import com.csys.appel.service.dto.DemandeOffreDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.csys.appel.domain.DemandeOffre}.
 */
@RestController
@RequestMapping("/api")
public class DemandeOffreResource {

    private final Logger log = LoggerFactory.getLogger(DemandeOffreResource.class);

    private static final String ENTITY_NAME = "demandeOffre";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DemandeOffreService demandeOffreService;

    private final DemandeOffreRepository demandeOffreRepository;

    public DemandeOffreResource(DemandeOffreService demandeOffreService, DemandeOffreRepository demandeOffreRepository) {
        this.demandeOffreService = demandeOffreService;
        this.demandeOffreRepository = demandeOffreRepository;
    }

    /**
     * {@code POST  /demande-offres} : Create a new demandeOffre.
     *
     * @param demandeOffreDTO the demandeOffreDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new demandeOffreDTO, or with status {@code 400 (Bad Request)} if the demandeOffre has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/demande-offres")
    public ResponseEntity<DemandeOffreDTO> createDemandeOffre(@Valid @RequestBody DemandeOffreDTO demandeOffreDTO)
        throws URISyntaxException {
        log.debug("REST request to save DemandeOffre : {}", demandeOffreDTO);
        if (demandeOffreDTO.getId() != null) {
            throw new BadRequestAlertException("A new demandeOffre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DemandeOffreDTO result = demandeOffreService.save(demandeOffreDTO);
        return ResponseEntity
            .created(new URI("/api/demande-offres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /demande-offres/:id} : Updates an existing demandeOffre.
     *
     * @param id the id of the demandeOffreDTO to save.
     * @param demandeOffreDTO the demandeOffreDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandeOffreDTO,
     * or with status {@code 400 (Bad Request)} if the demandeOffreDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the demandeOffreDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/demande-offres/{id}")
    public ResponseEntity<DemandeOffreDTO> updateDemandeOffre(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DemandeOffreDTO demandeOffreDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DemandeOffre : {}, {}", id, demandeOffreDTO);
        if (demandeOffreDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demandeOffreDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandeOffreRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DemandeOffreDTO result = demandeOffreService.save(demandeOffreDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demandeOffreDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /demande-offres/:id} : Partial updates given fields of an existing demandeOffre, field will ignore if it is null
     *
     * @param id the id of the demandeOffreDTO to save.
     * @param demandeOffreDTO the demandeOffreDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandeOffreDTO,
     * or with status {@code 400 (Bad Request)} if the demandeOffreDTO is not valid,
     * or with status {@code 404 (Not Found)} if the demandeOffreDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the demandeOffreDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/demande-offres/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DemandeOffreDTO> partialUpdateDemandeOffre(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DemandeOffreDTO demandeOffreDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DemandeOffre partially : {}, {}", id, demandeOffreDTO);
        if (demandeOffreDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demandeOffreDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandeOffreRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DemandeOffreDTO> result = demandeOffreService.partialUpdate(demandeOffreDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demandeOffreDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /demande-offres} : get all the demandeOffres.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of demandeOffres in body.
     */
    @GetMapping("/demande-offres")
    public ResponseEntity<List<DemandeOffreDTO>> getAllDemandeOffres(Pageable pageable) {
        log.debug("REST request to get a page of DemandeOffres");
        Page<DemandeOffreDTO> page = demandeOffreService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /demande-offres/:id} : get the "id" demandeOffre.
     *
     * @param id the id of the demandeOffreDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the demandeOffreDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/demande-offres/{id}")
    public ResponseEntity<DemandeOffreDTO> getDemandeOffre(@PathVariable Long id) {
        log.debug("REST request to get DemandeOffre : {}", id);
        Optional<DemandeOffreDTO> demandeOffreDTO = demandeOffreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(demandeOffreDTO);
    }

    /**
     * {@code DELETE  /demande-offres/:id} : delete the "id" demandeOffre.
     *
     * @param id the id of the demandeOffreDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/demande-offres/{id}")
    public ResponseEntity<Void> deleteDemandeOffre(@PathVariable Long id) {
        log.debug("REST request to delete DemandeOffre : {}", id);
        demandeOffreService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
