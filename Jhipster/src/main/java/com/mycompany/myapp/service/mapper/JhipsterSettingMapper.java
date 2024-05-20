package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Jhipster;
import com.mycompany.myapp.domain.JhipsterSetting;
import com.mycompany.myapp.service.dto.JhipsterDTO;
import com.mycompany.myapp.service.dto.JhipsterSettingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link JhipsterSetting} and its DTO {@link JhipsterSettingDTO}.
 */
@Mapper(componentModel = "spring")
public interface JhipsterSettingMapper extends EntityMapper<JhipsterSettingDTO, JhipsterSetting> {
    @Mapping(target = "jhipster", source = "jhipster", qualifiedByName = "jhipsterId")
    JhipsterSettingDTO toDto(JhipsterSetting s);

    @Named("jhipsterId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    JhipsterDTO toDtoJhipsterId(Jhipster jhipster);
}
