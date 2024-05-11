package com.csys.appel.service.impl;

import com.csys.appel.domain.Tva;
import com.csys.appel.repository.TvaRepository;
import com.csys.appel.service.TvaService;
import com.csys.appel.service.dto.TvaDTO;
import com.csys.appel.service.mapper.TvaMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Tva}.
 */
@Service
@Transactional
public class TvaServiceImpl implements TvaService {

    private final Logger log = LoggerFactory.getLogger(TvaServiceImpl.class);

    private final TvaRepository tvaRepository;

    private final TvaMapper tvaMapper;

    public TvaServiceImpl(TvaRepository tvaRepository, TvaMapper tvaMapper) {
        this.tvaRepository = tvaRepository;
        this.tvaMapper = tvaMapper;
    }

    @Override
    public TvaDTO save(TvaDTO tvaDTO) {
        log.debug("Request to save Tva : {}", tvaDTO);
        Tva tva = tvaMapper.toEntity(tvaDTO);
        tva = tvaRepository.save(tva);
        return tvaMapper.toDto(tva);
    }

    @Override
    public Optional<TvaDTO> partialUpdate(TvaDTO tvaDTO) {
        log.debug("Request to partially update Tva : {}", tvaDTO);

        return tvaRepository
            .findById(tvaDTO.getId())
            .map(
                existingTva -> {
                    tvaMapper.partialUpdate(existingTva, tvaDTO);
                    return existingTva;
                }
            )
            .map(tvaRepository::save)
            .map(tvaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TvaDTO> findAll() {
        log.debug("Request to get all Tvas");
        return tvaRepository.findAll().stream().map(tvaMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TvaDTO> findOne(Long id) {
        log.debug("Request to get Tva : {}", id);
        return tvaRepository.findById(id).map(tvaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tva : {}", id);
        tvaRepository.deleteById(id);
    }
}
