package com.csys.appel.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DemandeOffreMapperTest {

    private DemandeOffreMapper demandeOffreMapper;

    @BeforeEach
    public void setUp() {
        demandeOffreMapper = new DemandeOffreMapperImpl();
    }
}
