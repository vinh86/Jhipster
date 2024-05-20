package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.JhipsterSettingTestSamples.*;
import static com.mycompany.myapp.domain.JhipsterTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JhipsterSettingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JhipsterSetting.class);
        JhipsterSetting jhipsterSetting1 = getJhipsterSettingSample1();
        JhipsterSetting jhipsterSetting2 = new JhipsterSetting();
        assertThat(jhipsterSetting1).isNotEqualTo(jhipsterSetting2);

        jhipsterSetting2.setId(jhipsterSetting1.getId());
        assertThat(jhipsterSetting1).isEqualTo(jhipsterSetting2);

        jhipsterSetting2 = getJhipsterSettingSample2();
        assertThat(jhipsterSetting1).isNotEqualTo(jhipsterSetting2);
    }

    @Test
    void jhipsterTest() throws Exception {
        JhipsterSetting jhipsterSetting = getJhipsterSettingRandomSampleGenerator();
        Jhipster jhipsterBack = getJhipsterRandomSampleGenerator();

        jhipsterSetting.setJhipster(jhipsterBack);
        assertThat(jhipsterSetting.getJhipster()).isEqualTo(jhipsterBack);

        jhipsterSetting.jhipster(null);
        assertThat(jhipsterSetting.getJhipster()).isNull();
    }
}
