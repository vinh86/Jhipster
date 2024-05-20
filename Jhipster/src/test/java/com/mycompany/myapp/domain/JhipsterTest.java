package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.JhipsterTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JhipsterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Jhipster.class);
        Jhipster jhipster1 = getJhipsterSample1();
        Jhipster jhipster2 = new Jhipster();
        assertThat(jhipster1).isNotEqualTo(jhipster2);

        jhipster2.setId(jhipster1.getId());
        assertThat(jhipster1).isEqualTo(jhipster2);

        jhipster2 = getJhipsterSample2();
        assertThat(jhipster1).isNotEqualTo(jhipster2);
    }
}
