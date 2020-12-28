package uz.kitc.web.rest;

import uz.kitc.KitcuzApp;
import uz.kitc.domain.Galereya;
import uz.kitc.repository.GalereyaRepository;
import uz.kitc.service.GalereyaService;
import uz.kitc.service.dto.GalereyaDTO;
import uz.kitc.service.mapper.GalereyaMapper;
import uz.kitc.service.dto.GalereyaCriteria;
import uz.kitc.service.GalereyaQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GalereyaResource} REST controller.
 */
@SpringBootTest(classes = KitcuzApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class GalereyaResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CREATED_DATE = LocalDate.ofEpochDay(-1L);

    @Autowired
    private GalereyaRepository galereyaRepository;

    @Autowired
    private GalereyaMapper galereyaMapper;

    @Autowired
    private GalereyaService galereyaService;

    @Autowired
    private GalereyaQueryService galereyaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGalereyaMockMvc;

    private Galereya galereya;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Galereya createEntity(EntityManager em) {
        Galereya galereya = new Galereya()
            .title(DEFAULT_TITLE)
            .createdDate(DEFAULT_CREATED_DATE);
        return galereya;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Galereya createUpdatedEntity(EntityManager em) {
        Galereya galereya = new Galereya()
            .title(UPDATED_TITLE)
            .createdDate(UPDATED_CREATED_DATE);
        return galereya;
    }

    @BeforeEach
    public void initTest() {
        galereya = createEntity(em);
    }

    @Test
    @Transactional
    public void createGalereya() throws Exception {
        int databaseSizeBeforeCreate = galereyaRepository.findAll().size();
        // Create the Galereya
        GalereyaDTO galereyaDTO = galereyaMapper.toDto(galereya);
        restGalereyaMockMvc.perform(post("/api/galereyas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(galereyaDTO)))
            .andExpect(status().isCreated());

        // Validate the Galereya in the database
        List<Galereya> galereyaList = galereyaRepository.findAll();
        assertThat(galereyaList).hasSize(databaseSizeBeforeCreate + 1);
        Galereya testGalereya = galereyaList.get(galereyaList.size() - 1);
        assertThat(testGalereya.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testGalereya.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    public void createGalereyaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = galereyaRepository.findAll().size();

        // Create the Galereya with an existing ID
        galereya.setId(1L);
        GalereyaDTO galereyaDTO = galereyaMapper.toDto(galereya);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGalereyaMockMvc.perform(post("/api/galereyas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(galereyaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Galereya in the database
        List<Galereya> galereyaList = galereyaRepository.findAll();
        assertThat(galereyaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGalereyas() throws Exception {
        // Initialize the database
        galereyaRepository.saveAndFlush(galereya);

        // Get all the galereyaList
        restGalereyaMockMvc.perform(get("/api/galereyas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(galereya.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getGalereya() throws Exception {
        // Initialize the database
        galereyaRepository.saveAndFlush(galereya);

        // Get the galereya
        restGalereyaMockMvc.perform(get("/api/galereyas/{id}", galereya.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(galereya.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }


    @Test
    @Transactional
    public void getGalereyasByIdFiltering() throws Exception {
        // Initialize the database
        galereyaRepository.saveAndFlush(galereya);

        Long id = galereya.getId();

        defaultGalereyaShouldBeFound("id.equals=" + id);
        defaultGalereyaShouldNotBeFound("id.notEquals=" + id);

        defaultGalereyaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultGalereyaShouldNotBeFound("id.greaterThan=" + id);

        defaultGalereyaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultGalereyaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllGalereyasByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        galereyaRepository.saveAndFlush(galereya);

        // Get all the galereyaList where title equals to DEFAULT_TITLE
        defaultGalereyaShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the galereyaList where title equals to UPDATED_TITLE
        defaultGalereyaShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllGalereyasByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        galereyaRepository.saveAndFlush(galereya);

        // Get all the galereyaList where title not equals to DEFAULT_TITLE
        defaultGalereyaShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the galereyaList where title not equals to UPDATED_TITLE
        defaultGalereyaShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllGalereyasByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        galereyaRepository.saveAndFlush(galereya);

        // Get all the galereyaList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultGalereyaShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the galereyaList where title equals to UPDATED_TITLE
        defaultGalereyaShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllGalereyasByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        galereyaRepository.saveAndFlush(galereya);

        // Get all the galereyaList where title is not null
        defaultGalereyaShouldBeFound("title.specified=true");

        // Get all the galereyaList where title is null
        defaultGalereyaShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllGalereyasByTitleContainsSomething() throws Exception {
        // Initialize the database
        galereyaRepository.saveAndFlush(galereya);

        // Get all the galereyaList where title contains DEFAULT_TITLE
        defaultGalereyaShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the galereyaList where title contains UPDATED_TITLE
        defaultGalereyaShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllGalereyasByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        galereyaRepository.saveAndFlush(galereya);

        // Get all the galereyaList where title does not contain DEFAULT_TITLE
        defaultGalereyaShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the galereyaList where title does not contain UPDATED_TITLE
        defaultGalereyaShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllGalereyasByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        galereyaRepository.saveAndFlush(galereya);

        // Get all the galereyaList where createdDate equals to DEFAULT_CREATED_DATE
        defaultGalereyaShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the galereyaList where createdDate equals to UPDATED_CREATED_DATE
        defaultGalereyaShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllGalereyasByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        galereyaRepository.saveAndFlush(galereya);

        // Get all the galereyaList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultGalereyaShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the galereyaList where createdDate not equals to UPDATED_CREATED_DATE
        defaultGalereyaShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllGalereyasByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        galereyaRepository.saveAndFlush(galereya);

        // Get all the galereyaList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultGalereyaShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the galereyaList where createdDate equals to UPDATED_CREATED_DATE
        defaultGalereyaShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllGalereyasByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        galereyaRepository.saveAndFlush(galereya);

        // Get all the galereyaList where createdDate is not null
        defaultGalereyaShouldBeFound("createdDate.specified=true");

        // Get all the galereyaList where createdDate is null
        defaultGalereyaShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllGalereyasByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        galereyaRepository.saveAndFlush(galereya);

        // Get all the galereyaList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultGalereyaShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the galereyaList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultGalereyaShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllGalereyasByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        galereyaRepository.saveAndFlush(galereya);

        // Get all the galereyaList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultGalereyaShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the galereyaList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultGalereyaShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllGalereyasByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        galereyaRepository.saveAndFlush(galereya);

        // Get all the galereyaList where createdDate is less than DEFAULT_CREATED_DATE
        defaultGalereyaShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the galereyaList where createdDate is less than UPDATED_CREATED_DATE
        defaultGalereyaShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllGalereyasByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        galereyaRepository.saveAndFlush(galereya);

        // Get all the galereyaList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultGalereyaShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the galereyaList where createdDate is greater than SMALLER_CREATED_DATE
        defaultGalereyaShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultGalereyaShouldBeFound(String filter) throws Exception {
        restGalereyaMockMvc.perform(get("/api/galereyas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(galereya.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));

        // Check, that the count call also returns 1
        restGalereyaMockMvc.perform(get("/api/galereyas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultGalereyaShouldNotBeFound(String filter) throws Exception {
        restGalereyaMockMvc.perform(get("/api/galereyas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restGalereyaMockMvc.perform(get("/api/galereyas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingGalereya() throws Exception {
        // Get the galereya
        restGalereyaMockMvc.perform(get("/api/galereyas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGalereya() throws Exception {
        // Initialize the database
        galereyaRepository.saveAndFlush(galereya);

        int databaseSizeBeforeUpdate = galereyaRepository.findAll().size();

        // Update the galereya
        Galereya updatedGalereya = galereyaRepository.findById(galereya.getId()).get();
        // Disconnect from session so that the updates on updatedGalereya are not directly saved in db
        em.detach(updatedGalereya);
        updatedGalereya
            .title(UPDATED_TITLE)
            .createdDate(UPDATED_CREATED_DATE);
        GalereyaDTO galereyaDTO = galereyaMapper.toDto(updatedGalereya);

        restGalereyaMockMvc.perform(put("/api/galereyas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(galereyaDTO)))
            .andExpect(status().isOk());

        // Validate the Galereya in the database
        List<Galereya> galereyaList = galereyaRepository.findAll();
        assertThat(galereyaList).hasSize(databaseSizeBeforeUpdate);
        Galereya testGalereya = galereyaList.get(galereyaList.size() - 1);
        assertThat(testGalereya.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testGalereya.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingGalereya() throws Exception {
        int databaseSizeBeforeUpdate = galereyaRepository.findAll().size();

        // Create the Galereya
        GalereyaDTO galereyaDTO = galereyaMapper.toDto(galereya);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGalereyaMockMvc.perform(put("/api/galereyas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(galereyaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Galereya in the database
        List<Galereya> galereyaList = galereyaRepository.findAll();
        assertThat(galereyaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGalereya() throws Exception {
        // Initialize the database
        galereyaRepository.saveAndFlush(galereya);

        int databaseSizeBeforeDelete = galereyaRepository.findAll().size();

        // Delete the galereya
        restGalereyaMockMvc.perform(delete("/api/galereyas/{id}", galereya.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Galereya> galereyaList = galereyaRepository.findAll();
        assertThat(galereyaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
