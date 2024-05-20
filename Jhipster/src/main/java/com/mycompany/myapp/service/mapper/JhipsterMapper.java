package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Jhipster;
import com.mycompany.myapp.service.dto.JhipsterDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Jhipster} and its DTO {@link JhipsterDTO}.
 */
@Mapper(componentModel = "spring")
public interface JhipsterMapper extends EntityMapper<JhipsterDTO, Jhipster> {}
