package com.csys.appel.service.mapper;

import com.csys.appel.domain.*;
import com.csys.appel.service.dto.AppelOffreDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppelOffre} and its DTO {@link AppelOffreDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AppelOffreMapper extends EntityMapper<AppelOffreDTO, AppelOffre> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppelOffreDTO toDtoId(AppelOffre appelOffre);
}
