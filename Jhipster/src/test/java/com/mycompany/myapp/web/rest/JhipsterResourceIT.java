package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.JhipsterAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Jhipster;
import com.mycompany.myapp.repository.JhipsterRepository;
import com.mycompany.myapp.service.dto.JhipsterDTO;
import com.mycompany.myapp.service.mapper.JhipsterMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link JhipsterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class JhipsterResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CLOSED = false;
    private static final Boolean UPDATED_CLOSED = true;

    private static final String ENTITY_API_URL = "/api/jhipsters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private JhipsterRepository jhipsterRepository;

    @Autowired
    private JhipsterMapper jhipsterMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJhipsterMockMvc;

    private Jhipster jhipster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Jhipster createEntity(EntityManager em) {
        Jhipster jhipster = new Jhipster().name(DEFAULT_NAME).closed(DEFAULT_CLOSED);
        return jhipster;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Jhipster createUpdatedEntity(EntityManager em) {
        Jhipster jhipster = new Jhipster().name(UPDATED_NAME).closed(UPDATED_CLOSED);
        return jhipster;
    }

    @BeforeEach
    public void initTest() {
        jhipster = createEntity(em);
    }

    @Test
    @Transactional
    void createJhipster() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Jhipster
        JhipsterDTO jhipsterDTO = jhipsterMapper.toDto(jhipster);
        var returnedJhipsterDTO = om.readValue(
            restJhipsterMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jhipsterDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            JhipsterDTO.class
        );

        // Validate the Jhipster in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedJhipster = jhipsterMapper.toEntity(returnedJhipsterDTO);
        assertJhipsterUpdatableFieldsEquals(returnedJhipster, getPersistedJhipster(returnedJhipster));
    }

    @Test
    @Transactional
    void createJhipsterWithExistingId() throws Exception {
        // Create the Jhipster with an existing ID
        jhipster.setId(1L);
        JhipsterDTO jhipsterDTO = jhipsterMapper.toDto(jhipster);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJhipsterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jhipsterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Jhipster in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllJhipsters() throws Exception {
        // Initialize the database
        jhipsterRepository.saveAndFlush(jhipster);

        // Get all the jhipsterList
        restJhipsterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jhipster.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].closed").value(hasItem(DEFAULT_CLOSED.booleanValue())));
    }

    @Test
    @Transactional
    void getJhipster() throws Exception {
        // Initialize the database
        jhipsterRepository.saveAndFlush(jhipster);

        // Get the jhipster
        restJhipsterMockMvc
            .perform(get(ENTITY_API_URL_ID, jhipster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jhipster.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.closed").value(DEFAULT_CLOSED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingJhipster() throws Exception {
        // Get the jhipster
        restJhipsterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingJhipster() throws Exception {
        // Initialize the database
        jhipsterRepository.saveAndFlush(jhipster);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the jhipster
        Jhipster updatedJhipster = jhipsterRepository.findById(jhipster.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedJhipster are not directly saved in db
        em.detach(updatedJhipster);
        updatedJhipster.name(UPDATED_NAME).closed(UPDATED_CLOSED);
        JhipsterDTO jhipsterDTO = jhipsterMapper.toDto(updatedJhipster);

        restJhipsterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jhipsterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(jhipsterDTO))
            )
            .andExpect(status().isOk());

        // Validate the Jhipster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedJhipsterToMatchAllProperties(updatedJhipster);
    }

    @Test
    @Transactional
    void putNonExistingJhipster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jhipster.setId(longCount.incrementAndGet());

        // Create the Jhipster
        JhipsterDTO jhipsterDTO = jhipsterMapper.toDto(jhipster);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJhipsterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jhipsterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(jhipsterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Jhipster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchJhipster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jhipster.setId(longCount.incrementAndGet());

        // Create the Jhipster
        JhipsterDTO jhipsterDTO = jhipsterMapper.toDto(jhipster);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJhipsterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(jhipsterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Jhipster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamJhipster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jhipster.setId(longCount.incrementAndGet());

        // Create the Jhipster
        JhipsterDTO jhipsterDTO = jhipsterMapper.toDto(jhipster);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJhipsterMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jhipsterDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Jhipster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateJhipsterWithPatch() throws Exception {
        // Initialize the database
        jhipsterRepository.saveAndFlush(jhipster);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the jhipster using partial update
        Jhipster partialUpdatedJhipster = new Jhipster();
        partialUpdatedJhipster.setId(jhipster.getId());

        partialUpdatedJhipster.name(UPDATED_NAME);

        restJhipsterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJhipster.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedJhipster))
            )
            .andExpect(status().isOk());

        // Validate the Jhipster in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertJhipsterUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedJhipster, jhipster), getPersistedJhipster(jhipster));
    }

    @Test
    @Transactional
    void fullUpdateJhipsterWithPatch() throws Exception {
        // Initialize the database
        jhipsterRepository.saveAndFlush(jhipster);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the jhipster using partial update
        Jhipster partialUpdatedJhipster = new Jhipster();
        partialUpdatedJhipster.setId(jhipster.getId());

        partialUpdatedJhipster.name(UPDATED_NAME).closed(UPDATED_CLOSED);

        restJhipsterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJhipster.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedJhipster))
            )
            .andExpect(status().isOk());

        // Validate the Jhipster in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertJhipsterUpdatableFieldsEquals(partialUpdatedJhipster, getPersistedJhipster(partialUpdatedJhipster));
    }

    @Test
    @Transactional
    void patchNonExistingJhipster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jhipster.setId(longCount.incrementAndGet());

        // Create the Jhipster
        JhipsterDTO jhipsterDTO = jhipsterMapper.toDto(jhipster);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJhipsterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, jhipsterDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(jhipsterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Jhipster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchJhipster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jhipster.setId(longCount.incrementAndGet());

        // Create the Jhipster
        JhipsterDTO jhipsterDTO = jhipsterMapper.toDto(jhipster);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJhipsterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(jhipsterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Jhipster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamJhipster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jhipster.setId(longCount.incrementAndGet());

        // Create the Jhipster
        JhipsterDTO jhipsterDTO = jhipsterMapper.toDto(jhipster);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJhipsterMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(jhipsterDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Jhipster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteJhipster() throws Exception {
        // Initialize the database
        jhipsterRepository.saveAndFlush(jhipster);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the jhipster
        restJhipsterMockMvc
            .perform(delete(ENTITY_API_URL_ID, jhipster.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return jhipsterRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Jhipster getPersistedJhipster(Jhipster jhipster) {
        return jhipsterRepository.findById(jhipster.getId()).orElseThrow();
    }

    protected void assertPersistedJhipsterToMatchAllProperties(Jhipster expectedJhipster) {
        assertJhipsterAllPropertiesEquals(expectedJhipster, getPersistedJhipster(expectedJhipster));
    }

    protected void assertPersistedJhipsterToMatchUpdatableProperties(Jhipster expectedJhipster) {
        assertJhipsterAllUpdatablePropertiesEquals(expectedJhipster, getPersistedJhipster(expectedJhipster));
    }
}
