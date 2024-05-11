package com.csys.appel.service.mapper;

import com.csys.appel.domain.*;
import com.csys.appel.service.dto.OffreDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Offre} and its DTO {@link OffreDTO}.
 */
@Mapper(componentModel = "spring", uses = { TvaMapper.class, FournisseurMapper.class, DemandeOffreMapper.class })
public interface OffreMapper extends EntityMapper<OffreDTO, Offre> {
    @Mapping(target = "tva", source = "tva", qualifiedByName = "id")
    @Mapping(target = "fournisseur", source = "fournisseur", qualifiedByName = "id")
    @Mapping(target = "demandeOffre", source = "demandeOffre", qualifiedByName = "id")
    OffreDTO toDto(Offre s);
}
