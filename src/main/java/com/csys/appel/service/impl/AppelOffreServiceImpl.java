package com.csys.appel.service.impl;

import com.csys.appel.domain.AppelOffre;
import com.csys.appel.repository.AppelOffreRepository;
import com.csys.appel.service.AppelOffreService;
import com.csys.appel.service.dto.AppelOffreDTO;
import com.csys.appel.service.mapper.AppelOffreMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AppelOffre}.
 */
@Service
@Transactional
public class AppelOffreServiceImpl implements AppelOffreService {

    private final Logger log = LoggerFactory.getLogger(AppelOffreServiceImpl.class);

    private final AppelOffreRepository appelOffreRepository;

    private final AppelOffreMapper appelOffreMapper;

    public AppelOffreServiceImpl(AppelOffreRepository appelOffreRepository, AppelOffreMapper appelOffreMapper) {
        this.appelOffreRepository = appelOffreRepository;
        this.appelOffreMapper = appelOffreMapper;
    }

    @Override
    public AppelOffreDTO save(AppelOffreDTO appelOffreDTO) {
        log.debug("Request to save AppelOffre : {}", appelOffreDTO);
        AppelOffre appelOffre = appelOffreMapper.toEntity(appelOffreDTO);
        appelOffre = appelOffreRepository.save(appelOffre);
        return appelOffreMapper.toDto(appelOffre);
    }

    @Override
    public Optional<AppelOffreDTO> partialUpdate(AppelOffreDTO appelOffreDTO) {
        log.debug("Request to partially update AppelOffre : {}", appelOffreDTO);

        return appelOffreRepository
            .findById(appelOffreDTO.getId())
            .map(
                existingAppelOffre -> {
                    appelOffreMapper.partialUpdate(existingAppelOffre, appelOffreDTO);
                    return existingAppelOffre;
                }
            )
            .map(appelOffreRepository::save)
            .map(appelOffreMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AppelOffreDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AppelOffres");
        return appelOffreRepository.findAll(pageable).map(appelOffreMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AppelOffreDTO> findOne(Long id) {
        log.debug("Request to get AppelOffre : {}", id);
        return appelOffreRepository.findById(id).map(appelOffreMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AppelOffre : {}", id);
        appelOffreRepository.deleteById(id);
    }
}
