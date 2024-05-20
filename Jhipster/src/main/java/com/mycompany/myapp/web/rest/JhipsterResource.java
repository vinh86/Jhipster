package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.JhipsterRepository;
import com.mycompany.myapp.service.JhipsterService;
import com.mycompany.myapp.service.dto.JhipsterDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Jhipster}.
 */
@RestController
@RequestMapping("/api/jhipsters")
public class JhipsterResource {

    private final Logger log = LoggerFactory.getLogger(JhipsterResource.class);

    private static final String ENTITY_NAME = "jhipster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JhipsterService jhipsterService;

    private final JhipsterRepository jhipsterRepository;

    public JhipsterResource(JhipsterService jhipsterService, JhipsterRepository jhipsterRepository) {
        this.jhipsterService = jhipsterService;
        this.jhipsterRepository = jhipsterRepository;
    }

    /**
     * {@code POST  /jhipsters} : Create a new jhipster.
     *
     * @param jhipsterDTO the jhipsterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jhipsterDTO, or with status {@code 400 (Bad Request)} if the jhipster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<JhipsterDTO> createJhipster(@RequestBody JhipsterDTO jhipsterDTO) throws URISyntaxException {
        log.debug("REST request to save Jhipster : {}", jhipsterDTO);
        if (jhipsterDTO.getId() != null) {
            throw new BadRequestAlertException("A new jhipster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        jhipsterDTO = jhipsterService.save(jhipsterDTO);
        return ResponseEntity.created(new URI("/api/jhipsters/" + jhipsterDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, jhipsterDTO.getId().toString()))
            .body(jhipsterDTO);
    }

    /**
     * {@code PUT  /jhipsters/:id} : Updates an existing jhipster.
     *
     * @param id the id of the jhipsterDTO to save.
     * @param jhipsterDTO the jhipsterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jhipsterDTO,
     * or with status {@code 400 (Bad Request)} if the jhipsterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jhipsterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<JhipsterDTO> updateJhipster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JhipsterDTO jhipsterDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Jhipster : {}, {}", id, jhipsterDTO);
        if (jhipsterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jhipsterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jhipsterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        jhipsterDTO = jhipsterService.update(jhipsterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jhipsterDTO.getId().toString()))
            .body(jhipsterDTO);
    }

    /**
     * {@code PATCH  /jhipsters/:id} : Partial updates given fields of an existing jhipster, field will ignore if it is null
     *
     * @param id the id of the jhipsterDTO to save.
     * @param jhipsterDTO the jhipsterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jhipsterDTO,
     * or with status {@code 400 (Bad Request)} if the jhipsterDTO is not valid,
     * or with status {@code 404 (Not Found)} if the jhipsterDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the jhipsterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<JhipsterDTO> partialUpdateJhipster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JhipsterDTO jhipsterDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Jhipster partially : {}, {}", id, jhipsterDTO);
        if (jhipsterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jhipsterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jhipsterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<JhipsterDTO> result = jhipsterService.partialUpdate(jhipsterDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jhipsterDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /jhipsters} : get all the jhipsters.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jhipsters in body.
     */
    @GetMapping("")
    public List<JhipsterDTO> getAllJhipsters(@RequestParam(name = "filter", required = false) String filter) {
        if ("jhipstersetting-is-null".equals(filter)) {
            log.debug("REST request to get all Jhipsters where jhipsterSetting is null");
            return jhipsterService.findAllWhereJhipsterSettingIsNull();
        }
        log.debug("REST request to get all Jhipsters");
        return jhipsterService.findAll();
    }

    /**
     * {@code GET  /jhipsters/:id} : get the "id" jhipster.
     *
     * @param id the id of the jhipsterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jhipsterDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<JhipsterDTO> getJhipster(@PathVariable("id") Long id) {
        log.debug("REST request to get Jhipster : {}", id);
        Optional<JhipsterDTO> jhipsterDTO = jhipsterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jhipsterDTO);
    }

    /**
     * {@code DELETE  /jhipsters/:id} : delete the "id" jhipster.
     *
     * @param id the id of the jhipsterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJhipster(@PathVariable("id") Long id) {
        log.debug("REST request to delete Jhipster : {}", id);
        jhipsterService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
