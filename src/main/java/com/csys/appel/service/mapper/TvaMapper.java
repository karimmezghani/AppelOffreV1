package com.csys.appel.service.mapper;

import com.csys.appel.domain.*;
import com.csys.appel.service.dto.TvaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tva} and its DTO {@link TvaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TvaMapper extends EntityMapper<TvaDTO, Tva> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TvaDTO toDtoId(Tva tva);
}
