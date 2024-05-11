package com.csys.appel.service.impl;

import com.csys.appel.domain.DemandeOffre;
import com.csys.appel.repository.DemandeOffreRepository;
import com.csys.appel.service.DemandeOffreService;
import com.csys.appel.service.dto.DemandeOffreDTO;
import com.csys.appel.service.mapper.DemandeOffreMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DemandeOffre}.
 */
@Service
@Transactional
public class DemandeOffreServiceImpl implements DemandeOffreService {

    private final Logger log = LoggerFactory.getLogger(DemandeOffreServiceImpl.class);

    private final DemandeOffreRepository demandeOffreRepository;

    private final DemandeOffreMapper demandeOffreMapper;

    public DemandeOffreServiceImpl(DemandeOffreRepository demandeOffreRepository, DemandeOffreMapper demandeOffreMapper) {
        this.demandeOffreRepository = demandeOffreRepository;
        this.demandeOffreMapper = demandeOffreMapper;
    }

    @Override
    public DemandeOffreDTO save(DemandeOffreDTO demandeOffreDTO) {
        log.debug("Request to save DemandeOffre : {}", demandeOffreDTO);
        DemandeOffre demandeOffre = demandeOffreMapper.toEntity(demandeOffreDTO);
        demandeOffre = demandeOffreRepository.save(demandeOffre);
        return demandeOffreMapper.toDto(demandeOffre);
    }

    @Override
    public Optional<DemandeOffreDTO> partialUpdate(DemandeOffreDTO demandeOffreDTO) {
        log.debug("Request to partially update DemandeOffre : {}", demandeOffreDTO);

        return demandeOffreRepository
            .findById(demandeOffreDTO.getId())
            .map(
                existingDemandeOffre -> {
                    demandeOffreMapper.partialUpdate(existingDemandeOffre, demandeOffreDTO);
                    return existingDemandeOffre;
                }
            )
            .map(demandeOffreRepository::save)
            .map(demandeOffreMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DemandeOffreDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DemandeOffres");
        return demandeOffreRepository.findAll(pageable).map(demandeOffreMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DemandeOffreDTO> findOne(Long id) {
        log.debug("Request to get DemandeOffre : {}", id);
        return demandeOffreRepository.findById(id).map(demandeOffreMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DemandeOffre : {}", id);
        demandeOffreRepository.deleteById(id);
    }
}
