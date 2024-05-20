package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.JhipsterSetting;
import com.mycompany.myapp.repository.JhipsterSettingRepository;
import com.mycompany.myapp.service.dto.JhipsterSettingDTO;
import com.mycompany.myapp.service.mapper.JhipsterSettingMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.JhipsterSetting}.
 */
@Service
@Transactional
public class JhipsterSettingService {

    private final Logger log = LoggerFactory.getLogger(JhipsterSettingService.class);

    private final JhipsterSettingRepository jhipsterSettingRepository;

    private final JhipsterSettingMapper jhipsterSettingMapper;

    public JhipsterSettingService(JhipsterSettingRepository jhipsterSettingRepository, JhipsterSettingMapper jhipsterSettingMapper) {
        this.jhipsterSettingRepository = jhipsterSettingRepository;
        this.jhipsterSettingMapper = jhipsterSettingMapper;
    }

    /**
     * Save a jhipsterSetting.
     *
     * @param jhipsterSettingDTO the entity to save.
     * @return the persisted entity.
     */
    public JhipsterSettingDTO save(JhipsterSettingDTO jhipsterSettingDTO) {
        log.debug("Request to save JhipsterSetting : {}", jhipsterSettingDTO);
        JhipsterSetting jhipsterSetting = jhipsterSettingMapper.toEntity(jhipsterSettingDTO);
        jhipsterSetting = jhipsterSettingRepository.save(jhipsterSetting);
        return jhipsterSettingMapper.toDto(jhipsterSetting);
    }

    /**
     * Update a jhipsterSetting.
     *
     * @param jhipsterSettingDTO the entity to save.
     * @return the persisted entity.
     */
    public JhipsterSettingDTO update(JhipsterSettingDTO jhipsterSettingDTO) {
        log.debug("Request to update JhipsterSetting : {}", jhipsterSettingDTO);
        JhipsterSetting jhipsterSetting = jhipsterSettingMapper.toEntity(jhipsterSettingDTO);
        jhipsterSetting = jhipsterSettingRepository.save(jhipsterSetting);
        return jhipsterSettingMapper.toDto(jhipsterSetting);
    }

    /**
     * Partially update a jhipsterSetting.
     *
     * @param jhipsterSettingDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<JhipsterSettingDTO> partialUpdate(JhipsterSettingDTO jhipsterSettingDTO) {
        log.debug("Request to partially update JhipsterSetting : {}", jhipsterSettingDTO);

        return jhipsterSettingRepository
            .findById(jhipsterSettingDTO.getId())
            .map(existingJhipsterSetting -> {
                jhipsterSettingMapper.partialUpdate(existingJhipsterSetting, jhipsterSettingDTO);

                return existingJhipsterSetting;
            })
            .map(jhipsterSettingRepository::save)
            .map(jhipsterSettingMapper::toDto);
    }

    /**
     * Get all the jhipsterSettings.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<JhipsterSettingDTO> findAll() {
        log.debug("Request to get all JhipsterSettings");
        return jhipsterSettingRepository
            .findAll()
            .stream()
            .map(jhipsterSettingMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one jhipsterSetting by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<JhipsterSettingDTO> findOne(Long id) {
        log.debug("Request to get JhipsterSetting : {}", id);
        return jhipsterSettingRepository.findById(id).map(jhipsterSettingMapper::toDto);
    }

    /**
     * Delete the jhipsterSetting by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete JhipsterSetting : {}", id);
        jhipsterSettingRepository.deleteById(id);
    }
}
