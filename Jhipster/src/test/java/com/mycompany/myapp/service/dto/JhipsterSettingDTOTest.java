package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JhipsterSettingDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JhipsterSettingDTO.class);
        JhipsterSettingDTO jhipsterSettingDTO1 = new JhipsterSettingDTO();
        jhipsterSettingDTO1.setId(1L);
        JhipsterSettingDTO jhipsterSettingDTO2 = new JhipsterSettingDTO();
        assertThat(jhipsterSettingDTO1).isNotEqualTo(jhipsterSettingDTO2);
        jhipsterSettingDTO2.setId(jhipsterSettingDTO1.getId());
        assertThat(jhipsterSettingDTO1).isEqualTo(jhipsterSettingDTO2);
        jhipsterSettingDTO2.setId(2L);
        assertThat(jhipsterSettingDTO1).isNotEqualTo(jhipsterSettingDTO2);
        jhipsterSettingDTO1.setId(null);
        assertThat(jhipsterSettingDTO1).isNotEqualTo(jhipsterSettingDTO2);
    }
}
