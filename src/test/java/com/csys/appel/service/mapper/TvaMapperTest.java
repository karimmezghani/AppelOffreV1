package com.csys.appel.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TvaMapperTest {

    private TvaMapper tvaMapper;

    @BeforeEach
    public void setUp() {
        tvaMapper = new TvaMapperImpl();
    }
}
