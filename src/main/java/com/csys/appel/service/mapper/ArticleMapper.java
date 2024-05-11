package com.csys.appel.service.mapper;

import com.csys.appel.domain.*;
import com.csys.appel.service.dto.ArticleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Article} and its DTO {@link ArticleDTO}.
 */
@Mapper(componentModel = "spring", uses = { DemandeOffreMapper.class, CategorieMapper.class })
public interface ArticleMapper extends EntityMapper<ArticleDTO, Article> {
    @Mapping(target = "demandeOffre", source = "demandeOffre", qualifiedByName = "id")
    @Mapping(target = "categorie", source = "categorie", qualifiedByName = "id")
    ArticleDTO toDto(Article s);
}
