package com.csys.appel.service.mapper;

import com.csys.appel.domain.*;
import com.csys.appel.service.dto.DemandeOffreDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DemandeOffre} and its DTO {@link DemandeOffreDTO}.
 */
@Mapper(componentModel = "spring", uses = { AppelOffreMapper.class })
public interface DemandeOffreMapper extends EntityMapper<DemandeOffreDTO, DemandeOffre> {
    @Mapping(target = "appelOffre", source = "appelOffre", qualifiedByName = "id")
    DemandeOffreDTO toDto(DemandeOffre s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DemandeOffreDTO toDtoId(DemandeOffre demandeOffre);
}
