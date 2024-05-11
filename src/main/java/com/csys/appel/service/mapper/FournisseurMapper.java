package com.csys.appel.service.mapper;

import com.csys.appel.domain.*;
import com.csys.appel.service.dto.FournisseurDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Fournisseur} and its DTO {@link FournisseurDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface FournisseurMapper extends EntityMapper<FournisseurDTO, Fournisseur> {
    @Mapping(target = "user", source = "user", qualifiedByName = "login")
    FournisseurDTO toDto(Fournisseur s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FournisseurDTO toDtoId(Fournisseur fournisseur);
}
