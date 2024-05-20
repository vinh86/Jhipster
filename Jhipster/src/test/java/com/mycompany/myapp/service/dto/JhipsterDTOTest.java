package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JhipsterDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JhipsterDTO.class);
        JhipsterDTO jhipsterDTO1 = new JhipsterDTO();
        jhipsterDTO1.setId(1L);
        JhipsterDTO jhipsterDTO2 = new JhipsterDTO();
        assertThat(jhipsterDTO1).isNotEqualTo(jhipsterDTO2);
        jhipsterDTO2.setId(jhipsterDTO1.getId());
        assertThat(jhipsterDTO1).isEqualTo(jhipsterDTO2);
        jhipsterDTO2.setId(2L);
        assertThat(jhipsterDTO1).isNotEqualTo(jhipsterDTO2);
        jhipsterDTO1.setId(null);
        assertThat(jhipsterDTO1).isNotEqualTo(jhipsterDTO2);
    }
}
