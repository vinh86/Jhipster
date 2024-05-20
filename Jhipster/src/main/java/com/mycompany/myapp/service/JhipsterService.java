package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Jhipster;
import com.mycompany.myapp.repository.JhipsterRepository;
import com.mycompany.myapp.service.dto.JhipsterDTO;
import com.mycompany.myapp.service.mapper.JhipsterMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Jhipster}.
 */
@Service
@Transactional
public class JhipsterService {

    private final Logger log = LoggerFactory.getLogger(JhipsterService.class);

    private final JhipsterRepository jhipsterRepository;

    private final JhipsterMapper jhipsterMapper;

    public JhipsterService(JhipsterRepository jhipsterRepository, JhipsterMapper jhipsterMapper) {
        this.jhipsterRepository = jhipsterRepository;
        this.jhipsterMapper = jhipsterMapper;
    }

    /**
     * Save a jhipster.
     *
     * @param jhipsterDTO the entity to save.
     * @return the persisted entity.
     */
    public JhipsterDTO save(JhipsterDTO jhipsterDTO) {
        log.debug("Request to save Jhipster : {}", jhipsterDTO);
        Jhipster jhipster = jhipsterMapper.toEntity(jhipsterDTO);
        jhipster = jhipsterRepository.save(jhipster);
        return jhipsterMapper.toDto(jhipster);
    }

    /**
     * Update a jhipster.
     *
     * @param jhipsterDTO the entity to save.
     * @return the persisted entity.
     */
    public JhipsterDTO update(JhipsterDTO jhipsterDTO) {
        log.debug("Request to update Jhipster : {}", jhipsterDTO);
        Jhipster jhipster = jhipsterMapper.toEntity(jhipsterDTO);
        jhipster = jhipsterRepository.save(jhipster);
        return jhipsterMapper.toDto(jhipster);
    }

    /**
     * Partially update a jhipster.
     *
     * @param jhipsterDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<JhipsterDTO> partialUpdate(JhipsterDTO jhipsterDTO) {
        log.debug("Request to partially update Jhipster : {}", jhipsterDTO);

        return jhipsterRepository
            .findById(jhipsterDTO.getId())
            .map(existingJhipster -> {
                jhipsterMapper.partialUpdate(existingJhipster, jhipsterDTO);

                return existingJhipster;
            })
            .map(jhipsterRepository::save)
            .map(jhipsterMapper::toDto);
    }

    /**
     * Get all the jhipsters.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<JhipsterDTO> findAll() {
        log.debug("Request to get all Jhipsters");
        return jhipsterRepository.findAll().stream().map(jhipsterMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one jhipster by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<JhipsterDTO> findOne(Long id) {
        log.debug("Request to get Jhipster : {}", id);
        return jhipsterRepository.findById(id).map(jhipsterMapper::toDto);
    }

    /**
     * Delete the jhipster by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Jhipster : {}", id);
        jhipsterRepository.deleteById(id);
    }
}
