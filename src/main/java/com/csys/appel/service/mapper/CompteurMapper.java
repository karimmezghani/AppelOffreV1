package com.csys.appel.service.mapper;

import com.csys.appel.domain.Categorie;
import com.csys.appel.domain.Compteur;
import com.csys.appel.service.dto.CategorieDTO;
import com.csys.appel.service.dto.CompteurDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link Compteur} and its DTO {@link CompteurDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CompteurMapper extends EntityMapper<CompteurDTO, Compteur> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompteurDTO toDtoId(Compteur compteur);
}
