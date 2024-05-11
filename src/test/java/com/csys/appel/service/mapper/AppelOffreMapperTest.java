package com.csys.appel.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AppelOffreMapperTest {

    private AppelOffreMapper appelOffreMapper;

    @BeforeEach
    public void setUp() {
        appelOffreMapper = new AppelOffreMapperImpl();
    }
}
