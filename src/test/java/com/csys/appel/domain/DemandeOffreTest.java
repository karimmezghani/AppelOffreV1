package com.csys.appel.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.csys.appel.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DemandeOffreTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DemandeOffre.class);
        DemandeOffre demandeOffre1 = new DemandeOffre();
        demandeOffre1.setId(1L);
        DemandeOffre demandeOffre2 = new DemandeOffre();
        demandeOffre2.setId(demandeOffre1.getId());
        assertThat(demandeOffre1).isEqualTo(demandeOffre2);
        demandeOffre2.setId(2L);
        assertThat(demandeOffre1).isNotEqualTo(demandeOffre2);
        demandeOffre1.setId(null);
        assertThat(demandeOffre1).isNotEqualTo(demandeOffre2);
    }
}
