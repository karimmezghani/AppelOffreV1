package com.csys.appel.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.csys.appel.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DemandeOffreDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DemandeOffreDTO.class);
        DemandeOffreDTO demandeOffreDTO1 = new DemandeOffreDTO();
        demandeOffreDTO1.setId(1L);
        DemandeOffreDTO demandeOffreDTO2 = new DemandeOffreDTO();
        assertThat(demandeOffreDTO1).isNotEqualTo(demandeOffreDTO2);
        demandeOffreDTO2.setId(demandeOffreDTO1.getId());
        assertThat(demandeOffreDTO1).isEqualTo(demandeOffreDTO2);
        demandeOffreDTO2.setId(2L);
        assertThat(demandeOffreDTO1).isNotEqualTo(demandeOffreDTO2);
        demandeOffreDTO1.setId(null);
        assertThat(demandeOffreDTO1).isNotEqualTo(demandeOffreDTO2);
    }
}
