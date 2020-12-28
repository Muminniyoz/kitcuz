package uz.kitc.web.rest;

import uz.kitc.KitcuzApp;
import uz.kitc.domain.SystemFiles;
import uz.kitc.repository.SystemFilesRepository;
import uz.kitc.service.SystemFilesService;
import uz.kitc.service.dto.SystemFilesDTO;
import uz.kitc.service.mapper.SystemFilesMapper;
import uz.kitc.service.dto.SystemFilesCriteria;
import uz.kitc.service.SystemFilesQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import uz.kitc.domain.enumeration.FileGroup;
/**
 * Integration tests for the {@link SystemFilesResource} REST controller.
 */
@SpringBootTest(classes = KitcuzApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SystemFilesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_HASH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_HASH_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAA";
    private static final String UPDATED_TYPE = "BBBBB";

    private static final byte[] DEFAULT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final FileGroup DEFAULT_FILE_GROUP = FileGroup.GLOBAL;
    private static final FileGroup UPDATED_FILE_GROUP = FileGroup.USER;

    @Autowired
    private SystemFilesRepository systemFilesRepository;

    @Autowired
    private SystemFilesMapper systemFilesMapper;

    @Autowired
    private SystemFilesService systemFilesService;

    @Autowired
    private SystemFilesQueryService systemFilesQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSystemFilesMockMvc;

    private SystemFiles systemFiles;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SystemFiles createEntity(EntityManager em) {
        SystemFiles systemFiles = new SystemFiles()
            .name(DEFAULT_NAME)
            .hashName(DEFAULT_HASH_NAME)
            .type(DEFAULT_TYPE)
            .file(DEFAULT_FILE)
            .fileContentType(DEFAULT_FILE_CONTENT_TYPE)
            .time(DEFAULT_TIME)
            .fileGroup(DEFAULT_FILE_GROUP);
        return systemFiles;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SystemFiles createUpdatedEntity(EntityManager em) {
        SystemFiles systemFiles = new SystemFiles()
            .name(UPDATED_NAME)
            .hashName(UPDATED_HASH_NAME)
            .type(UPDATED_TYPE)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .time(UPDATED_TIME)
            .fileGroup(UPDATED_FILE_GROUP);
        return systemFiles;
    }

    @BeforeEach
    public void initTest() {
        systemFiles = createEntity(em);
    }

    @Test
    @Transactional
    public void createSystemFiles() throws Exception {
        int databaseSizeBeforeCreate = systemFilesRepository.findAll().size();
        // Create the SystemFiles
        SystemFilesDTO systemFilesDTO = systemFilesMapper.toDto(systemFiles);
        restSystemFilesMockMvc.perform(post("/api/system-files")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(systemFilesDTO)))
            .andExpect(status().isCreated());

        // Validate the SystemFiles in the database
        List<SystemFiles> systemFilesList = systemFilesRepository.findAll();
        assertThat(systemFilesList).hasSize(databaseSizeBeforeCreate + 1);
        SystemFiles testSystemFiles = systemFilesList.get(systemFilesList.size() - 1);
        assertThat(testSystemFiles.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSystemFiles.getHashName()).isEqualTo(DEFAULT_HASH_NAME);
        assertThat(testSystemFiles.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testSystemFiles.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testSystemFiles.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);
        assertThat(testSystemFiles.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testSystemFiles.getFileGroup()).isEqualTo(DEFAULT_FILE_GROUP);
    }

    @Test
    @Transactional
    public void createSystemFilesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = systemFilesRepository.findAll().size();

        // Create the SystemFiles with an existing ID
        systemFiles.setId(1L);
        SystemFilesDTO systemFilesDTO = systemFilesMapper.toDto(systemFiles);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSystemFilesMockMvc.perform(post("/api/system-files")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(systemFilesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SystemFiles in the database
        List<SystemFiles> systemFilesList = systemFilesRepository.findAll();
        assertThat(systemFilesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = systemFilesRepository.findAll().size();
        // set the field null
        systemFiles.setName(null);

        // Create the SystemFiles, which fails.
        SystemFilesDTO systemFilesDTO = systemFilesMapper.toDto(systemFiles);


        restSystemFilesMockMvc.perform(post("/api/system-files")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(systemFilesDTO)))
            .andExpect(status().isBadRequest());

        List<SystemFiles> systemFilesList = systemFilesRepository.findAll();
        assertThat(systemFilesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSystemFiles() throws Exception {
        // Initialize the database
        systemFilesRepository.saveAndFlush(systemFiles);

        // Get all the systemFilesList
        restSystemFilesMockMvc.perform(get("/api/system-files?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(systemFiles.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].hashName").value(hasItem(DEFAULT_HASH_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME.toString())))
            .andExpect(jsonPath("$.[*].fileGroup").value(hasItem(DEFAULT_FILE_GROUP.toString())));
    }
    
    @Test
    @Transactional
    public void getSystemFiles() throws Exception {
        // Initialize the database
        systemFilesRepository.saveAndFlush(systemFiles);

        // Get the systemFiles
        restSystemFilesMockMvc.perform(get("/api/system-files/{id}", systemFiles.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(systemFiles.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.hashName").value(DEFAULT_HASH_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64Utils.encodeToString(DEFAULT_FILE)))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME.toString()))
            .andExpect(jsonPath("$.fileGroup").value(DEFAULT_FILE_GROUP.toString()));
    }


    @Test
    @Transactional
    public void getSystemFilesByIdFiltering() throws Exception {
        // Initialize the database
        systemFilesRepository.saveAndFlush(systemFiles);

        Long id = systemFiles.getId();

        defaultSystemFilesShouldBeFound("id.equals=" + id);
        defaultSystemFilesShouldNotBeFound("id.notEquals=" + id);

        defaultSystemFilesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSystemFilesShouldNotBeFound("id.greaterThan=" + id);

        defaultSystemFilesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSystemFilesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSystemFilesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        systemFilesRepository.saveAndFlush(systemFiles);

        // Get all the systemFilesList where name equals to DEFAULT_NAME
        defaultSystemFilesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the systemFilesList where name equals to UPDATED_NAME
        defaultSystemFilesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSystemFilesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        systemFilesRepository.saveAndFlush(systemFiles);

        // Get all the systemFilesList where name not equals to DEFAULT_NAME
        defaultSystemFilesShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the systemFilesList where name not equals to UPDATED_NAME
        defaultSystemFilesShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSystemFilesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        systemFilesRepository.saveAndFlush(systemFiles);

        // Get all the systemFilesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultSystemFilesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the systemFilesList where name equals to UPDATED_NAME
        defaultSystemFilesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSystemFilesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemFilesRepository.saveAndFlush(systemFiles);

        // Get all the systemFilesList where name is not null
        defaultSystemFilesShouldBeFound("name.specified=true");

        // Get all the systemFilesList where name is null
        defaultSystemFilesShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllSystemFilesByNameContainsSomething() throws Exception {
        // Initialize the database
        systemFilesRepository.saveAndFlush(systemFiles);

        // Get all the systemFilesList where name contains DEFAULT_NAME
        defaultSystemFilesShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the systemFilesList where name contains UPDATED_NAME
        defaultSystemFilesShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSystemFilesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        systemFilesRepository.saveAndFlush(systemFiles);

        // Get all the systemFilesList where name does not contain DEFAULT_NAME
        defaultSystemFilesShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the systemFilesList where name does not contain UPDATED_NAME
        defaultSystemFilesShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllSystemFilesByHashNameIsEqualToSomething() throws Exception {
        // Initialize the database
        systemFilesRepository.saveAndFlush(systemFiles);

        // Get all the systemFilesList where hashName equals to DEFAULT_HASH_NAME
        defaultSystemFilesShouldBeFound("hashName.equals=" + DEFAULT_HASH_NAME);

        // Get all the systemFilesList where hashName equals to UPDATED_HASH_NAME
        defaultSystemFilesShouldNotBeFound("hashName.equals=" + UPDATED_HASH_NAME);
    }

    @Test
    @Transactional
    public void getAllSystemFilesByHashNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        systemFilesRepository.saveAndFlush(systemFiles);

        // Get all the systemFilesList where hashName not equals to DEFAULT_HASH_NAME
        defaultSystemFilesShouldNotBeFound("hashName.notEquals=" + DEFAULT_HASH_NAME);

        // Get all the systemFilesList where hashName not equals to UPDATED_HASH_NAME
        defaultSystemFilesShouldBeFound("hashName.notEquals=" + UPDATED_HASH_NAME);
    }

    @Test
    @Transactional
    public void getAllSystemFilesByHashNameIsInShouldWork() throws Exception {
        // Initialize the database
        systemFilesRepository.saveAndFlush(systemFiles);

        // Get all the systemFilesList where hashName in DEFAULT_HASH_NAME or UPDATED_HASH_NAME
        defaultSystemFilesShouldBeFound("hashName.in=" + DEFAULT_HASH_NAME + "," + UPDATED_HASH_NAME);

        // Get all the systemFilesList where hashName equals to UPDATED_HASH_NAME
        defaultSystemFilesShouldNotBeFound("hashName.in=" + UPDATED_HASH_NAME);
    }

    @Test
    @Transactional
    public void getAllSystemFilesByHashNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemFilesRepository.saveAndFlush(systemFiles);

        // Get all the systemFilesList where hashName is not null
        defaultSystemFilesShouldBeFound("hashName.specified=true");

        // Get all the systemFilesList where hashName is null
        defaultSystemFilesShouldNotBeFound("hashName.specified=false");
    }
                @Test
    @Transactional
    public void getAllSystemFilesByHashNameContainsSomething() throws Exception {
        // Initialize the database
        systemFilesRepository.saveAndFlush(systemFiles);

        // Get all the systemFilesList where hashName contains DEFAULT_HASH_NAME
        defaultSystemFilesShouldBeFound("hashName.contains=" + DEFAULT_HASH_NAME);

        // Get all the systemFilesList where hashName contains UPDATED_HASH_NAME
        defaultSystemFilesShouldNotBeFound("hashName.contains=" + UPDATED_HASH_NAME);
    }

    @Test
    @Transactional
    public void getAllSystemFilesByHashNameNotContainsSomething() throws Exception {
        // Initialize the database
        systemFilesRepository.saveAndFlush(systemFiles);

        // Get all the systemFilesList where hashName does not contain DEFAULT_HASH_NAME
        defaultSystemFilesShouldNotBeFound("hashName.doesNotContain=" + DEFAULT_HASH_NAME);

        // Get all the systemFilesList where hashName does not contain UPDATED_HASH_NAME
        defaultSystemFilesShouldBeFound("hashName.doesNotContain=" + UPDATED_HASH_NAME);
    }


    @Test
    @Transactional
    public void getAllSystemFilesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        systemFilesRepository.saveAndFlush(systemFiles);

        // Get all the systemFilesList where type equals to DEFAULT_TYPE
        defaultSystemFilesShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the systemFilesList where type equals to UPDATED_TYPE
        defaultSystemFilesShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllSystemFilesByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        systemFilesRepository.saveAndFlush(systemFiles);

        // Get all the systemFilesList where type not equals to DEFAULT_TYPE
        defaultSystemFilesShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the systemFilesList where type not equals to UPDATED_TYPE
        defaultSystemFilesShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllSystemFilesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        systemFilesRepository.saveAndFlush(systemFiles);

        // Get all the systemFilesList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultSystemFilesShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the systemFilesList where type equals to UPDATED_TYPE
        defaultSystemFilesShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllSystemFilesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemFilesRepository.saveAndFlush(systemFiles);

        // Get all the systemFilesList where type is not null
        defaultSystemFilesShouldBeFound("type.specified=true");

        // Get all the systemFilesList where type is null
        defaultSystemFilesShouldNotBeFound("type.specified=false");
    }
                @Test
    @Transactional
    public void getAllSystemFilesByTypeContainsSomething() throws Exception {
        // Initialize the database
        systemFilesRepository.saveAndFlush(systemFiles);

        // Get all the systemFilesList where type contains DEFAULT_TYPE
        defaultSystemFilesShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the systemFilesList where type contains UPDATED_TYPE
        defaultSystemFilesShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllSystemFilesByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        systemFilesRepository.saveAndFlush(systemFiles);

        // Get all the systemFilesList where type does not contain DEFAULT_TYPE
        defaultSystemFilesShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the systemFilesList where type does not contain UPDATED_TYPE
        defaultSystemFilesShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }


    @Test
    @Transactional
    public void getAllSystemFilesByTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        systemFilesRepository.saveAndFlush(systemFiles);

        // Get all the systemFilesList where time equals to DEFAULT_TIME
        defaultSystemFilesShouldBeFound("time.equals=" + DEFAULT_TIME);

        // Get all the systemFilesList where time equals to UPDATED_TIME
        defaultSystemFilesShouldNotBeFound("time.equals=" + UPDATED_TIME);
    }

    @Test
    @Transactional
    public void getAllSystemFilesByTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        systemFilesRepository.saveAndFlush(systemFiles);

        // Get all the systemFilesList where time not equals to DEFAULT_TIME
        defaultSystemFilesShouldNotBeFound("time.notEquals=" + DEFAULT_TIME);

        // Get all the systemFilesList where time not equals to UPDATED_TIME
        defaultSystemFilesShouldBeFound("time.notEquals=" + UPDATED_TIME);
    }

    @Test
    @Transactional
    public void getAllSystemFilesByTimeIsInShouldWork() throws Exception {
        // Initialize the database
        systemFilesRepository.saveAndFlush(systemFiles);

        // Get all the systemFilesList where time in DEFAULT_TIME or UPDATED_TIME
        defaultSystemFilesShouldBeFound("time.in=" + DEFAULT_TIME + "," + UPDATED_TIME);

        // Get all the systemFilesList where time equals to UPDATED_TIME
        defaultSystemFilesShouldNotBeFound("time.in=" + UPDATED_TIME);
    }

    @Test
    @Transactional
    public void getAllSystemFilesByTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemFilesRepository.saveAndFlush(systemFiles);

        // Get all the systemFilesList where time is not null
        defaultSystemFilesShouldBeFound("time.specified=true");

        // Get all the systemFilesList where time is null
        defaultSystemFilesShouldNotBeFound("time.specified=false");
    }

    @Test
    @Transactional
    public void getAllSystemFilesByFileGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        systemFilesRepository.saveAndFlush(systemFiles);

        // Get all the systemFilesList where fileGroup equals to DEFAULT_FILE_GROUP
        defaultSystemFilesShouldBeFound("fileGroup.equals=" + DEFAULT_FILE_GROUP);

        // Get all the systemFilesList where fileGroup equals to UPDATED_FILE_GROUP
        defaultSystemFilesShouldNotBeFound("fileGroup.equals=" + UPDATED_FILE_GROUP);
    }

    @Test
    @Transactional
    public void getAllSystemFilesByFileGroupIsNotEqualToSomething() throws Exception {
        // Initialize the database
        systemFilesRepository.saveAndFlush(systemFiles);

        // Get all the systemFilesList where fileGroup not equals to DEFAULT_FILE_GROUP
        defaultSystemFilesShouldNotBeFound("fileGroup.notEquals=" + DEFAULT_FILE_GROUP);

        // Get all the systemFilesList where fileGroup not equals to UPDATED_FILE_GROUP
        defaultSystemFilesShouldBeFound("fileGroup.notEquals=" + UPDATED_FILE_GROUP);
    }

    @Test
    @Transactional
    public void getAllSystemFilesByFileGroupIsInShouldWork() throws Exception {
        // Initialize the database
        systemFilesRepository.saveAndFlush(systemFiles);

        // Get all the systemFilesList where fileGroup in DEFAULT_FILE_GROUP or UPDATED_FILE_GROUP
        defaultSystemFilesShouldBeFound("fileGroup.in=" + DEFAULT_FILE_GROUP + "," + UPDATED_FILE_GROUP);

        // Get all the systemFilesList where fileGroup equals to UPDATED_FILE_GROUP
        defaultSystemFilesShouldNotBeFound("fileGroup.in=" + UPDATED_FILE_GROUP);
    }

    @Test
    @Transactional
    public void getAllSystemFilesByFileGroupIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemFilesRepository.saveAndFlush(systemFiles);

        // Get all the systemFilesList where fileGroup is not null
        defaultSystemFilesShouldBeFound("fileGroup.specified=true");

        // Get all the systemFilesList where fileGroup is null
        defaultSystemFilesShouldNotBeFound("fileGroup.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSystemFilesShouldBeFound(String filter) throws Exception {
        restSystemFilesMockMvc.perform(get("/api/system-files?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(systemFiles.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].hashName").value(hasItem(DEFAULT_HASH_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME.toString())))
            .andExpect(jsonPath("$.[*].fileGroup").value(hasItem(DEFAULT_FILE_GROUP.toString())));

        // Check, that the count call also returns 1
        restSystemFilesMockMvc.perform(get("/api/system-files/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSystemFilesShouldNotBeFound(String filter) throws Exception {
        restSystemFilesMockMvc.perform(get("/api/system-files?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSystemFilesMockMvc.perform(get("/api/system-files/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingSystemFiles() throws Exception {
        // Get the systemFiles
        restSystemFilesMockMvc.perform(get("/api/system-files/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSystemFiles() throws Exception {
        // Initialize the database
        systemFilesRepository.saveAndFlush(systemFiles);

        int databaseSizeBeforeUpdate = systemFilesRepository.findAll().size();

        // Update the systemFiles
        SystemFiles updatedSystemFiles = systemFilesRepository.findById(systemFiles.getId()).get();
        // Disconnect from session so that the updates on updatedSystemFiles are not directly saved in db
        em.detach(updatedSystemFiles);
        updatedSystemFiles
            .name(UPDATED_NAME)
            .hashName(UPDATED_HASH_NAME)
            .type(UPDATED_TYPE)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .time(UPDATED_TIME)
            .fileGroup(UPDATED_FILE_GROUP);
        SystemFilesDTO systemFilesDTO = systemFilesMapper.toDto(updatedSystemFiles);

        restSystemFilesMockMvc.perform(put("/api/system-files")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(systemFilesDTO)))
            .andExpect(status().isOk());

        // Validate the SystemFiles in the database
        List<SystemFiles> systemFilesList = systemFilesRepository.findAll();
        assertThat(systemFilesList).hasSize(databaseSizeBeforeUpdate);
        SystemFiles testSystemFiles = systemFilesList.get(systemFilesList.size() - 1);
        assertThat(testSystemFiles.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSystemFiles.getHashName()).isEqualTo(UPDATED_HASH_NAME);
        assertThat(testSystemFiles.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSystemFiles.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testSystemFiles.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);
        assertThat(testSystemFiles.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testSystemFiles.getFileGroup()).isEqualTo(UPDATED_FILE_GROUP);
    }

    @Test
    @Transactional
    public void updateNonExistingSystemFiles() throws Exception {
        int databaseSizeBeforeUpdate = systemFilesRepository.findAll().size();

        // Create the SystemFiles
        SystemFilesDTO systemFilesDTO = systemFilesMapper.toDto(systemFiles);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSystemFilesMockMvc.perform(put("/api/system-files")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(systemFilesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SystemFiles in the database
        List<SystemFiles> systemFilesList = systemFilesRepository.findAll();
        assertThat(systemFilesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSystemFiles() throws Exception {
        // Initialize the database
        systemFilesRepository.saveAndFlush(systemFiles);

        int databaseSizeBeforeDelete = systemFilesRepository.findAll().size();

        // Delete the systemFiles
        restSystemFilesMockMvc.perform(delete("/api/system-files/{id}", systemFiles.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SystemFiles> systemFilesList = systemFilesRepository.findAll();
        assertThat(systemFilesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
