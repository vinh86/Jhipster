package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Jhipster;
import com.mycompany.myapp.service.dto.JhipsterDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Jhipster} and its DTO {@link JhipsterDTO}.
 */
@Mapper(componentModel = "spring")
public interface JhipsterMapper extends EntityMapper<JhipsterDTO, Jhipster> {

    static final JhipsterConverter CONVERTER = new JhipsterConverter();

    @Mapping(target = "setting", expression = "java(CONVERTER.convertToDatabaseColumn(source.getSetting()))")
    JhipsterDTO toDto(Jhipster source);

    @Mapping(target = "setting", expression = "java(CONVERTER.convertToEntityAttribute(destination.getSetting()))")
    Jhipster toEntity(JhipsterDTO destination);

    @Mapping(target = "setting", expression = "java(CONVERTER.convertToEntityAttribute(dto.getSetting()))")
    void partialUpdate(@MappingTarget Jhipster entity, JhipsterDTO dto);
}
