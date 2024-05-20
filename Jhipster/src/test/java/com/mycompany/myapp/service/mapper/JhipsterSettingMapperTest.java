package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.JhipsterSettingAsserts.*;
import static com.mycompany.myapp.domain.JhipsterSettingTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JhipsterSettingMapperTest {

    private JhipsterSettingMapper jhipsterSettingMapper;

    @BeforeEach
    void setUp() {
        jhipsterSettingMapper = new JhipsterSettingMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getJhipsterSettingSample1();
        var actual = jhipsterSettingMapper.toEntity(jhipsterSettingMapper.toDto(expected));
        assertJhipsterSettingAllPropertiesEquals(expected, actual);
    }
}
