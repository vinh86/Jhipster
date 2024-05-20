package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.JhipsterAsserts.*;
import static com.mycompany.myapp.domain.JhipsterTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JhipsterMapperTest {

    private JhipsterMapper jhipsterMapper;

    @BeforeEach
    void setUp() {
        jhipsterMapper = new JhipsterMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getJhipsterSample1();
        var actual = jhipsterMapper.toEntity(jhipsterMapper.toDto(expected));
        assertJhipsterAllPropertiesEquals(expected, actual);
    }
}
