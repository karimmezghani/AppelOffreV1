package com.csys.appel.service.impl;

import com.csys.appel.domain.Offre;
import com.csys.appel.repository.OffreRepository;
import com.csys.appel.service.OffreService;
import com.csys.appel.service.dto.OffreDTO;
import com.csys.appel.service.mapper.OffreMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Offre}.
 */
@Service
@Transactional
public class OffreServiceImpl implements OffreService {

    private final Logger log = LoggerFactory.getLogger(OffreServiceImpl.class);

    private final OffreRepository offreRepository;

    private final OffreMapper offreMapper;

    public OffreServiceImpl(OffreRepository offreRepository, OffreMapper offreMapper) {
        this.offreRepository = offreRepository;
        this.offreMapper = offreMapper;
    }

    @Override
    public OffreDTO save(OffreDTO offreDTO) {
        log.debug("Request to save Offre : {}", offreDTO);
        Offre offre = offreMapper.toEntity(offreDTO);
        offre = offreRepository.save(offre);
        return offreMapper.toDto(offre);
    }

    @Override
    public Optional<OffreDTO> partialUpdate(OffreDTO offreDTO) {
        log.debug("Request to partially update Offre : {}", offreDTO);

        return offreRepository
            .findById(offreDTO.getId())
            .map(
                existingOffre -> {
                    offreMapper.partialUpdate(existingOffre, offreDTO);
                    return existingOffre;
                }
            )
            .map(offreRepository::save)
            .map(offreMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OffreDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Offres");
        return offreRepository.findAll(pageable).map(offreMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OffreDTO> findOne(Long id) {
        log.debug("Request to get Offre : {}", id);
        return offreRepository.findById(id).map(offreMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Offre : {}", id);
        offreRepository.deleteById(id);
    }
}
