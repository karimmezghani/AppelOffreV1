package com.csys.appel.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.csys.appel.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TvaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tva.class);
        Tva tva1 = new Tva();
        tva1.setId(1L);
        Tva tva2 = new Tva();
        tva2.setId(tva1.getId());
        assertThat(tva1).isEqualTo(tva2);
        tva2.setId(2L);
        assertThat(tva1).isNotEqualTo(tva2);
        tva1.setId(null);
        assertThat(tva1).isNotEqualTo(tva2);
    }
}
