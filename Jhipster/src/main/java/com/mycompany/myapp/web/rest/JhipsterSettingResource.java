package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.JhipsterSettingRepository;
import com.mycompany.myapp.service.JhipsterSettingService;
import com.mycompany.myapp.service.dto.JhipsterSettingDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.JhipsterSetting}.
 */
@RestController
@RequestMapping("/api/jhipster-settings")
public class JhipsterSettingResource {

    private final Logger log = LoggerFactory.getLogger(JhipsterSettingResource.class);

    private static final String ENTITY_NAME = "jhipsterSetting";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JhipsterSettingService jhipsterSettingService;

    private final JhipsterSettingRepository jhipsterSettingRepository;

    public JhipsterSettingResource(JhipsterSettingService jhipsterSettingService, JhipsterSettingRepository jhipsterSettingRepository) {
        this.jhipsterSettingService = jhipsterSettingService;
        this.jhipsterSettingRepository = jhipsterSettingRepository;
    }

    /**
     * {@code POST  /jhipster-settings} : Create a new jhipsterSetting.
     *
     * @param jhipsterSettingDTO the jhipsterSettingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jhipsterSettingDTO, or with status {@code 400 (Bad Request)} if the jhipsterSetting has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<JhipsterSettingDTO> createJhipsterSetting(@Valid @RequestBody JhipsterSettingDTO jhipsterSettingDTO)
        throws URISyntaxException {
        log.debug("REST request to save JhipsterSetting : {}", jhipsterSettingDTO);
        if (jhipsterSettingDTO.getId() != null) {
            throw new BadRequestAlertException("A new jhipsterSetting cannot already have an ID", ENTITY_NAME, "idexists");
        }
        jhipsterSettingDTO = jhipsterSettingService.save(jhipsterSettingDTO);
        return ResponseEntity.created(new URI("/api/jhipster-settings/" + jhipsterSettingDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, jhipsterSettingDTO.getId().toString()))
            .body(jhipsterSettingDTO);
    }

    /**
     * {@code PUT  /jhipster-settings/:id} : Updates an existing jhipsterSetting.
     *
     * @param id the id of the jhipsterSettingDTO to save.
     * @param jhipsterSettingDTO the jhipsterSettingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jhipsterSettingDTO,
     * or with status {@code 400 (Bad Request)} if the jhipsterSettingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jhipsterSettingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<JhipsterSettingDTO> updateJhipsterSetting(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody JhipsterSettingDTO jhipsterSettingDTO
    ) throws URISyntaxException {
        log.debug("REST request to update JhipsterSetting : {}, {}", id, jhipsterSettingDTO);
        if (jhipsterSettingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jhipsterSettingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jhipsterSettingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        jhipsterSettingDTO = jhipsterSettingService.update(jhipsterSettingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jhipsterSettingDTO.getId().toString()))
            .body(jhipsterSettingDTO);
    }

    /**
     * {@code PATCH  /jhipster-settings/:id} : Partial updates given fields of an existing jhipsterSetting, field will ignore if it is null
     *
     * @param id the id of the jhipsterSettingDTO to save.
     * @param jhipsterSettingDTO the jhipsterSettingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jhipsterSettingDTO,
     * or with status {@code 400 (Bad Request)} if the jhipsterSettingDTO is not valid,
     * or with status {@code 404 (Not Found)} if the jhipsterSettingDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the jhipsterSettingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<JhipsterSettingDTO> partialUpdateJhipsterSetting(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody JhipsterSettingDTO jhipsterSettingDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update JhipsterSetting partially : {}, {}", id, jhipsterSettingDTO);
        if (jhipsterSettingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jhipsterSettingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jhipsterSettingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<JhipsterSettingDTO> result = jhipsterSettingService.partialUpdate(jhipsterSettingDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jhipsterSettingDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /jhipster-settings} : get all the jhipsterSettings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jhipsterSettings in body.
     */
    @GetMapping("")
    public List<JhipsterSettingDTO> getAllJhipsterSettings() {
        log.debug("REST request to get all JhipsterSettings");
        return jhipsterSettingService.findAll();
    }

    /**
     * {@code GET  /jhipster-settings/:id} : get the "id" jhipsterSetting.
     *
     * @param id the id of the jhipsterSettingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jhipsterSettingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<JhipsterSettingDTO> getJhipsterSetting(@PathVariable("id") Long id) {
        log.debug("REST request to get JhipsterSetting : {}", id);
        Optional<JhipsterSettingDTO> jhipsterSettingDTO = jhipsterSettingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jhipsterSettingDTO);
    }

    /**
     * {@code DELETE  /jhipster-settings/:id} : delete the "id" jhipsterSetting.
     *
     * @param id the id of the jhipsterSettingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJhipsterSetting(@PathVariable("id") Long id) {
        log.debug("REST request to delete JhipsterSetting : {}", id);
        jhipsterSettingService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
