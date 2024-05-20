package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.JhipsterSettingAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Jhipster;
import com.mycompany.myapp.domain.JhipsterSetting;
import com.mycompany.myapp.repository.JhipsterSettingRepository;
import com.mycompany.myapp.service.dto.JhipsterSettingDTO;
import com.mycompany.myapp.service.mapper.JhipsterSettingMapper;
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
 * Integration tests for the {@link JhipsterSettingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class JhipsterSettingResourceIT {

    private static final String DEFAULT_THEME = "AAAAAAAAAA";
    private static final String UPDATED_THEME = "BBBBBBBBBB";

    private static final Integer DEFAULT_PAGE_SIZE = 1;
    private static final Integer UPDATED_PAGE_SIZE = 2;

    private static final String DEFAULT_OTHERS = "AAAAAAAAAA";
    private static final String UPDATED_OTHERS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/jhipster-settings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private JhipsterSettingRepository jhipsterSettingRepository;

    @Autowired
    private JhipsterSettingMapper jhipsterSettingMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJhipsterSettingMockMvc;

    private JhipsterSetting jhipsterSetting;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JhipsterSetting createEntity(EntityManager em) {
        JhipsterSetting jhipsterSetting = new JhipsterSetting().theme(DEFAULT_THEME).pageSize(DEFAULT_PAGE_SIZE).others(DEFAULT_OTHERS);
        // Add required entity
        Jhipster jhipster;
        if (TestUtil.findAll(em, Jhipster.class).isEmpty()) {
            jhipster = JhipsterResourceIT.createEntity(em);
            em.persist(jhipster);
            em.flush();
        } else {
            jhipster = TestUtil.findAll(em, Jhipster.class).get(0);
        }
        jhipsterSetting.setJhipster(jhipster);
        return jhipsterSetting;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JhipsterSetting createUpdatedEntity(EntityManager em) {
        JhipsterSetting jhipsterSetting = new JhipsterSetting().theme(UPDATED_THEME).pageSize(UPDATED_PAGE_SIZE).others(UPDATED_OTHERS);
        // Add required entity
        Jhipster jhipster;
        if (TestUtil.findAll(em, Jhipster.class).isEmpty()) {
            jhipster = JhipsterResourceIT.createUpdatedEntity(em);
            em.persist(jhipster);
            em.flush();
        } else {
            jhipster = TestUtil.findAll(em, Jhipster.class).get(0);
        }
        jhipsterSetting.setJhipster(jhipster);
        return jhipsterSetting;
    }

    @BeforeEach
    public void initTest() {
        jhipsterSetting = createEntity(em);
    }

    @Test
    @Transactional
    void createJhipsterSetting() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the JhipsterSetting
        JhipsterSettingDTO jhipsterSettingDTO = jhipsterSettingMapper.toDto(jhipsterSetting);
        var returnedJhipsterSettingDTO = om.readValue(
            restJhipsterSettingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jhipsterSettingDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            JhipsterSettingDTO.class
        );

        // Validate the JhipsterSetting in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedJhipsterSetting = jhipsterSettingMapper.toEntity(returnedJhipsterSettingDTO);
        assertJhipsterSettingUpdatableFieldsEquals(returnedJhipsterSetting, getPersistedJhipsterSetting(returnedJhipsterSetting));
    }

    @Test
    @Transactional
    void createJhipsterSettingWithExistingId() throws Exception {
        // Create the JhipsterSetting with an existing ID
        jhipsterSetting.setId(1L);
        JhipsterSettingDTO jhipsterSettingDTO = jhipsterSettingMapper.toDto(jhipsterSetting);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJhipsterSettingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jhipsterSettingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the JhipsterSetting in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllJhipsterSettings() throws Exception {
        // Initialize the database
        jhipsterSettingRepository.saveAndFlush(jhipsterSetting);

        // Get all the jhipsterSettingList
        restJhipsterSettingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jhipsterSetting.getId().intValue())))
            .andExpect(jsonPath("$.[*].theme").value(hasItem(DEFAULT_THEME)))
            .andExpect(jsonPath("$.[*].pageSize").value(hasItem(DEFAULT_PAGE_SIZE)))
            .andExpect(jsonPath("$.[*].others").value(hasItem(DEFAULT_OTHERS)));
    }

    @Test
    @Transactional
    void getJhipsterSetting() throws Exception {
        // Initialize the database
        jhipsterSettingRepository.saveAndFlush(jhipsterSetting);

        // Get the jhipsterSetting
        restJhipsterSettingMockMvc
            .perform(get(ENTITY_API_URL_ID, jhipsterSetting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jhipsterSetting.getId().intValue()))
            .andExpect(jsonPath("$.theme").value(DEFAULT_THEME))
            .andExpect(jsonPath("$.pageSize").value(DEFAULT_PAGE_SIZE))
            .andExpect(jsonPath("$.others").value(DEFAULT_OTHERS));
    }

    @Test
    @Transactional
    void getNonExistingJhipsterSetting() throws Exception {
        // Get the jhipsterSetting
        restJhipsterSettingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingJhipsterSetting() throws Exception {
        // Initialize the database
        jhipsterSettingRepository.saveAndFlush(jhipsterSetting);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the jhipsterSetting
        JhipsterSetting updatedJhipsterSetting = jhipsterSettingRepository.findById(jhipsterSetting.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedJhipsterSetting are not directly saved in db
        em.detach(updatedJhipsterSetting);
        updatedJhipsterSetting.theme(UPDATED_THEME).pageSize(UPDATED_PAGE_SIZE).others(UPDATED_OTHERS);
        JhipsterSettingDTO jhipsterSettingDTO = jhipsterSettingMapper.toDto(updatedJhipsterSetting);

        restJhipsterSettingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jhipsterSettingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(jhipsterSettingDTO))
            )
            .andExpect(status().isOk());

        // Validate the JhipsterSetting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedJhipsterSettingToMatchAllProperties(updatedJhipsterSetting);
    }

    @Test
    @Transactional
    void putNonExistingJhipsterSetting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jhipsterSetting.setId(longCount.incrementAndGet());

        // Create the JhipsterSetting
        JhipsterSettingDTO jhipsterSettingDTO = jhipsterSettingMapper.toDto(jhipsterSetting);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJhipsterSettingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jhipsterSettingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(jhipsterSettingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JhipsterSetting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchJhipsterSetting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jhipsterSetting.setId(longCount.incrementAndGet());

        // Create the JhipsterSetting
        JhipsterSettingDTO jhipsterSettingDTO = jhipsterSettingMapper.toDto(jhipsterSetting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJhipsterSettingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(jhipsterSettingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JhipsterSetting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamJhipsterSetting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jhipsterSetting.setId(longCount.incrementAndGet());

        // Create the JhipsterSetting
        JhipsterSettingDTO jhipsterSettingDTO = jhipsterSettingMapper.toDto(jhipsterSetting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJhipsterSettingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jhipsterSettingDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the JhipsterSetting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateJhipsterSettingWithPatch() throws Exception {
        // Initialize the database
        jhipsterSettingRepository.saveAndFlush(jhipsterSetting);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the jhipsterSetting using partial update
        JhipsterSetting partialUpdatedJhipsterSetting = new JhipsterSetting();
        partialUpdatedJhipsterSetting.setId(jhipsterSetting.getId());

        restJhipsterSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJhipsterSetting.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedJhipsterSetting))
            )
            .andExpect(status().isOk());

        // Validate the JhipsterSetting in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertJhipsterSettingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedJhipsterSetting, jhipsterSetting),
            getPersistedJhipsterSetting(jhipsterSetting)
        );
    }

    @Test
    @Transactional
    void fullUpdateJhipsterSettingWithPatch() throws Exception {
        // Initialize the database
        jhipsterSettingRepository.saveAndFlush(jhipsterSetting);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the jhipsterSetting using partial update
        JhipsterSetting partialUpdatedJhipsterSetting = new JhipsterSetting();
        partialUpdatedJhipsterSetting.setId(jhipsterSetting.getId());

        partialUpdatedJhipsterSetting.theme(UPDATED_THEME).pageSize(UPDATED_PAGE_SIZE).others(UPDATED_OTHERS);

        restJhipsterSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJhipsterSetting.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedJhipsterSetting))
            )
            .andExpect(status().isOk());

        // Validate the JhipsterSetting in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertJhipsterSettingUpdatableFieldsEquals(
            partialUpdatedJhipsterSetting,
            getPersistedJhipsterSetting(partialUpdatedJhipsterSetting)
        );
    }

    @Test
    @Transactional
    void patchNonExistingJhipsterSetting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jhipsterSetting.setId(longCount.incrementAndGet());

        // Create the JhipsterSetting
        JhipsterSettingDTO jhipsterSettingDTO = jhipsterSettingMapper.toDto(jhipsterSetting);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJhipsterSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, jhipsterSettingDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(jhipsterSettingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JhipsterSetting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchJhipsterSetting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jhipsterSetting.setId(longCount.incrementAndGet());

        // Create the JhipsterSetting
        JhipsterSettingDTO jhipsterSettingDTO = jhipsterSettingMapper.toDto(jhipsterSetting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJhipsterSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(jhipsterSettingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JhipsterSetting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamJhipsterSetting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jhipsterSetting.setId(longCount.incrementAndGet());

        // Create the JhipsterSetting
        JhipsterSettingDTO jhipsterSettingDTO = jhipsterSettingMapper.toDto(jhipsterSetting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJhipsterSettingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(jhipsterSettingDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the JhipsterSetting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteJhipsterSetting() throws Exception {
        // Initialize the database
        jhipsterSettingRepository.saveAndFlush(jhipsterSetting);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the jhipsterSetting
        restJhipsterSettingMockMvc
            .perform(delete(ENTITY_API_URL_ID, jhipsterSetting.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return jhipsterSettingRepository.count();
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

    protected JhipsterSetting getPersistedJhipsterSetting(JhipsterSetting jhipsterSetting) {
        return jhipsterSettingRepository.findById(jhipsterSetting.getId()).orElseThrow();
    }

    protected void assertPersistedJhipsterSettingToMatchAllProperties(JhipsterSetting expectedJhipsterSetting) {
        assertJhipsterSettingAllPropertiesEquals(expectedJhipsterSetting, getPersistedJhipsterSetting(expectedJhipsterSetting));
    }

    protected void assertPersistedJhipsterSettingToMatchUpdatableProperties(JhipsterSetting expectedJhipsterSetting) {
        assertJhipsterSettingAllUpdatablePropertiesEquals(expectedJhipsterSetting, getPersistedJhipsterSetting(expectedJhipsterSetting));
    }
}
