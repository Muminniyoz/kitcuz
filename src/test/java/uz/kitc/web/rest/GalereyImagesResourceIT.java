package uz.kitc.web.rest;

import uz.kitc.KitcuzApp;
import uz.kitc.domain.GalereyImages;
import uz.kitc.domain.Galereya;
import uz.kitc.repository.GalereyImagesRepository;
import uz.kitc.service.GalereyImagesService;
import uz.kitc.service.dto.GalereyImagesDTO;
import uz.kitc.service.mapper.GalereyImagesMapper;
import uz.kitc.service.dto.GalereyImagesCriteria;
import uz.kitc.service.GalereyImagesQueryService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GalereyImagesResource} REST controller.
 */
@SpringBootTest(classes = KitcuzApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class GalereyImagesResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    @Autowired
    private GalereyImagesRepository galereyImagesRepository;

    @Autowired
    private GalereyImagesMapper galereyImagesMapper;

    @Autowired
    private GalereyImagesService galereyImagesService;

    @Autowired
    private GalereyImagesQueryService galereyImagesQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGalereyImagesMockMvc;

    private GalereyImages galereyImages;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GalereyImages createEntity(EntityManager em) {
        GalereyImages galereyImages = new GalereyImages()
            .title(DEFAULT_TITLE)
            .imageUrl(DEFAULT_IMAGE_URL)
            .number(DEFAULT_NUMBER);
        return galereyImages;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GalereyImages createUpdatedEntity(EntityManager em) {
        GalereyImages galereyImages = new GalereyImages()
            .title(UPDATED_TITLE)
            .imageUrl(UPDATED_IMAGE_URL)
            .number(UPDATED_NUMBER);
        return galereyImages;
    }

    @BeforeEach
    public void initTest() {
        galereyImages = createEntity(em);
    }

    @Test
    @Transactional
    public void createGalereyImages() throws Exception {
        int databaseSizeBeforeCreate = galereyImagesRepository.findAll().size();
        // Create the GalereyImages
        GalereyImagesDTO galereyImagesDTO = galereyImagesMapper.toDto(galereyImages);
        restGalereyImagesMockMvc.perform(post("/api/galerey-images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(galereyImagesDTO)))
            .andExpect(status().isCreated());

        // Validate the GalereyImages in the database
        List<GalereyImages> galereyImagesList = galereyImagesRepository.findAll();
        assertThat(galereyImagesList).hasSize(databaseSizeBeforeCreate + 1);
        GalereyImages testGalereyImages = galereyImagesList.get(galereyImagesList.size() - 1);
        assertThat(testGalereyImages.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testGalereyImages.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testGalereyImages.getNumber()).isEqualTo(DEFAULT_NUMBER);
    }

    @Test
    @Transactional
    public void createGalereyImagesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = galereyImagesRepository.findAll().size();

        // Create the GalereyImages with an existing ID
        galereyImages.setId(1L);
        GalereyImagesDTO galereyImagesDTO = galereyImagesMapper.toDto(galereyImages);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGalereyImagesMockMvc.perform(post("/api/galerey-images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(galereyImagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GalereyImages in the database
        List<GalereyImages> galereyImagesList = galereyImagesRepository.findAll();
        assertThat(galereyImagesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGalereyImages() throws Exception {
        // Initialize the database
        galereyImagesRepository.saveAndFlush(galereyImages);

        // Get all the galereyImagesList
        restGalereyImagesMockMvc.perform(get("/api/galerey-images?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(galereyImages.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)));
    }
    
    @Test
    @Transactional
    public void getGalereyImages() throws Exception {
        // Initialize the database
        galereyImagesRepository.saveAndFlush(galereyImages);

        // Get the galereyImages
        restGalereyImagesMockMvc.perform(get("/api/galerey-images/{id}", galereyImages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(galereyImages.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER));
    }


    @Test
    @Transactional
    public void getGalereyImagesByIdFiltering() throws Exception {
        // Initialize the database
        galereyImagesRepository.saveAndFlush(galereyImages);

        Long id = galereyImages.getId();

        defaultGalereyImagesShouldBeFound("id.equals=" + id);
        defaultGalereyImagesShouldNotBeFound("id.notEquals=" + id);

        defaultGalereyImagesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultGalereyImagesShouldNotBeFound("id.greaterThan=" + id);

        defaultGalereyImagesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultGalereyImagesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllGalereyImagesByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        galereyImagesRepository.saveAndFlush(galereyImages);

        // Get all the galereyImagesList where title equals to DEFAULT_TITLE
        defaultGalereyImagesShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the galereyImagesList where title equals to UPDATED_TITLE
        defaultGalereyImagesShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllGalereyImagesByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        galereyImagesRepository.saveAndFlush(galereyImages);

        // Get all the galereyImagesList where title not equals to DEFAULT_TITLE
        defaultGalereyImagesShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the galereyImagesList where title not equals to UPDATED_TITLE
        defaultGalereyImagesShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllGalereyImagesByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        galereyImagesRepository.saveAndFlush(galereyImages);

        // Get all the galereyImagesList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultGalereyImagesShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the galereyImagesList where title equals to UPDATED_TITLE
        defaultGalereyImagesShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllGalereyImagesByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        galereyImagesRepository.saveAndFlush(galereyImages);

        // Get all the galereyImagesList where title is not null
        defaultGalereyImagesShouldBeFound("title.specified=true");

        // Get all the galereyImagesList where title is null
        defaultGalereyImagesShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllGalereyImagesByTitleContainsSomething() throws Exception {
        // Initialize the database
        galereyImagesRepository.saveAndFlush(galereyImages);

        // Get all the galereyImagesList where title contains DEFAULT_TITLE
        defaultGalereyImagesShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the galereyImagesList where title contains UPDATED_TITLE
        defaultGalereyImagesShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllGalereyImagesByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        galereyImagesRepository.saveAndFlush(galereyImages);

        // Get all the galereyImagesList where title does not contain DEFAULT_TITLE
        defaultGalereyImagesShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the galereyImagesList where title does not contain UPDATED_TITLE
        defaultGalereyImagesShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllGalereyImagesByImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        galereyImagesRepository.saveAndFlush(galereyImages);

        // Get all the galereyImagesList where imageUrl equals to DEFAULT_IMAGE_URL
        defaultGalereyImagesShouldBeFound("imageUrl.equals=" + DEFAULT_IMAGE_URL);

        // Get all the galereyImagesList where imageUrl equals to UPDATED_IMAGE_URL
        defaultGalereyImagesShouldNotBeFound("imageUrl.equals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllGalereyImagesByImageUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        galereyImagesRepository.saveAndFlush(galereyImages);

        // Get all the galereyImagesList where imageUrl not equals to DEFAULT_IMAGE_URL
        defaultGalereyImagesShouldNotBeFound("imageUrl.notEquals=" + DEFAULT_IMAGE_URL);

        // Get all the galereyImagesList where imageUrl not equals to UPDATED_IMAGE_URL
        defaultGalereyImagesShouldBeFound("imageUrl.notEquals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllGalereyImagesByImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        galereyImagesRepository.saveAndFlush(galereyImages);

        // Get all the galereyImagesList where imageUrl in DEFAULT_IMAGE_URL or UPDATED_IMAGE_URL
        defaultGalereyImagesShouldBeFound("imageUrl.in=" + DEFAULT_IMAGE_URL + "," + UPDATED_IMAGE_URL);

        // Get all the galereyImagesList where imageUrl equals to UPDATED_IMAGE_URL
        defaultGalereyImagesShouldNotBeFound("imageUrl.in=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllGalereyImagesByImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        galereyImagesRepository.saveAndFlush(galereyImages);

        // Get all the galereyImagesList where imageUrl is not null
        defaultGalereyImagesShouldBeFound("imageUrl.specified=true");

        // Get all the galereyImagesList where imageUrl is null
        defaultGalereyImagesShouldNotBeFound("imageUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllGalereyImagesByImageUrlContainsSomething() throws Exception {
        // Initialize the database
        galereyImagesRepository.saveAndFlush(galereyImages);

        // Get all the galereyImagesList where imageUrl contains DEFAULT_IMAGE_URL
        defaultGalereyImagesShouldBeFound("imageUrl.contains=" + DEFAULT_IMAGE_URL);

        // Get all the galereyImagesList where imageUrl contains UPDATED_IMAGE_URL
        defaultGalereyImagesShouldNotBeFound("imageUrl.contains=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllGalereyImagesByImageUrlNotContainsSomething() throws Exception {
        // Initialize the database
        galereyImagesRepository.saveAndFlush(galereyImages);

        // Get all the galereyImagesList where imageUrl does not contain DEFAULT_IMAGE_URL
        defaultGalereyImagesShouldNotBeFound("imageUrl.doesNotContain=" + DEFAULT_IMAGE_URL);

        // Get all the galereyImagesList where imageUrl does not contain UPDATED_IMAGE_URL
        defaultGalereyImagesShouldBeFound("imageUrl.doesNotContain=" + UPDATED_IMAGE_URL);
    }


    @Test
    @Transactional
    public void getAllGalereyImagesByNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        galereyImagesRepository.saveAndFlush(galereyImages);

        // Get all the galereyImagesList where number equals to DEFAULT_NUMBER
        defaultGalereyImagesShouldBeFound("number.equals=" + DEFAULT_NUMBER);

        // Get all the galereyImagesList where number equals to UPDATED_NUMBER
        defaultGalereyImagesShouldNotBeFound("number.equals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllGalereyImagesByNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        galereyImagesRepository.saveAndFlush(galereyImages);

        // Get all the galereyImagesList where number not equals to DEFAULT_NUMBER
        defaultGalereyImagesShouldNotBeFound("number.notEquals=" + DEFAULT_NUMBER);

        // Get all the galereyImagesList where number not equals to UPDATED_NUMBER
        defaultGalereyImagesShouldBeFound("number.notEquals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllGalereyImagesByNumberIsInShouldWork() throws Exception {
        // Initialize the database
        galereyImagesRepository.saveAndFlush(galereyImages);

        // Get all the galereyImagesList where number in DEFAULT_NUMBER or UPDATED_NUMBER
        defaultGalereyImagesShouldBeFound("number.in=" + DEFAULT_NUMBER + "," + UPDATED_NUMBER);

        // Get all the galereyImagesList where number equals to UPDATED_NUMBER
        defaultGalereyImagesShouldNotBeFound("number.in=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllGalereyImagesByNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        galereyImagesRepository.saveAndFlush(galereyImages);

        // Get all the galereyImagesList where number is not null
        defaultGalereyImagesShouldBeFound("number.specified=true");

        // Get all the galereyImagesList where number is null
        defaultGalereyImagesShouldNotBeFound("number.specified=false");
    }
                @Test
    @Transactional
    public void getAllGalereyImagesByNumberContainsSomething() throws Exception {
        // Initialize the database
        galereyImagesRepository.saveAndFlush(galereyImages);

        // Get all the galereyImagesList where number contains DEFAULT_NUMBER
        defaultGalereyImagesShouldBeFound("number.contains=" + DEFAULT_NUMBER);

        // Get all the galereyImagesList where number contains UPDATED_NUMBER
        defaultGalereyImagesShouldNotBeFound("number.contains=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllGalereyImagesByNumberNotContainsSomething() throws Exception {
        // Initialize the database
        galereyImagesRepository.saveAndFlush(galereyImages);

        // Get all the galereyImagesList where number does not contain DEFAULT_NUMBER
        defaultGalereyImagesShouldNotBeFound("number.doesNotContain=" + DEFAULT_NUMBER);

        // Get all the galereyImagesList where number does not contain UPDATED_NUMBER
        defaultGalereyImagesShouldBeFound("number.doesNotContain=" + UPDATED_NUMBER);
    }


    @Test
    @Transactional
    public void getAllGalereyImagesByGalereyIsEqualToSomething() throws Exception {
        // Initialize the database
        galereyImagesRepository.saveAndFlush(galereyImages);
        Galereya galerey = GalereyaResourceIT.createEntity(em);
        em.persist(galerey);
        em.flush();
        galereyImages.setGalerey(galerey);
        galereyImagesRepository.saveAndFlush(galereyImages);
        Long galereyId = galerey.getId();

        // Get all the galereyImagesList where galerey equals to galereyId
        defaultGalereyImagesShouldBeFound("galereyId.equals=" + galereyId);

        // Get all the galereyImagesList where galerey equals to galereyId + 1
        defaultGalereyImagesShouldNotBeFound("galereyId.equals=" + (galereyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultGalereyImagesShouldBeFound(String filter) throws Exception {
        restGalereyImagesMockMvc.perform(get("/api/galerey-images?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(galereyImages.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)));

        // Check, that the count call also returns 1
        restGalereyImagesMockMvc.perform(get("/api/galerey-images/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultGalereyImagesShouldNotBeFound(String filter) throws Exception {
        restGalereyImagesMockMvc.perform(get("/api/galerey-images?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restGalereyImagesMockMvc.perform(get("/api/galerey-images/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingGalereyImages() throws Exception {
        // Get the galereyImages
        restGalereyImagesMockMvc.perform(get("/api/galerey-images/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGalereyImages() throws Exception {
        // Initialize the database
        galereyImagesRepository.saveAndFlush(galereyImages);

        int databaseSizeBeforeUpdate = galereyImagesRepository.findAll().size();

        // Update the galereyImages
        GalereyImages updatedGalereyImages = galereyImagesRepository.findById(galereyImages.getId()).get();
        // Disconnect from session so that the updates on updatedGalereyImages are not directly saved in db
        em.detach(updatedGalereyImages);
        updatedGalereyImages
            .title(UPDATED_TITLE)
            .imageUrl(UPDATED_IMAGE_URL)
            .number(UPDATED_NUMBER);
        GalereyImagesDTO galereyImagesDTO = galereyImagesMapper.toDto(updatedGalereyImages);

        restGalereyImagesMockMvc.perform(put("/api/galerey-images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(galereyImagesDTO)))
            .andExpect(status().isOk());

        // Validate the GalereyImages in the database
        List<GalereyImages> galereyImagesList = galereyImagesRepository.findAll();
        assertThat(galereyImagesList).hasSize(databaseSizeBeforeUpdate);
        GalereyImages testGalereyImages = galereyImagesList.get(galereyImagesList.size() - 1);
        assertThat(testGalereyImages.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testGalereyImages.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testGalereyImages.getNumber()).isEqualTo(UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingGalereyImages() throws Exception {
        int databaseSizeBeforeUpdate = galereyImagesRepository.findAll().size();

        // Create the GalereyImages
        GalereyImagesDTO galereyImagesDTO = galereyImagesMapper.toDto(galereyImages);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGalereyImagesMockMvc.perform(put("/api/galerey-images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(galereyImagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GalereyImages in the database
        List<GalereyImages> galereyImagesList = galereyImagesRepository.findAll();
        assertThat(galereyImagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGalereyImages() throws Exception {
        // Initialize the database
        galereyImagesRepository.saveAndFlush(galereyImages);

        int databaseSizeBeforeDelete = galereyImagesRepository.findAll().size();

        // Delete the galereyImages
        restGalereyImagesMockMvc.perform(delete("/api/galerey-images/{id}", galereyImages.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GalereyImages> galereyImagesList = galereyImagesRepository.findAll();
        assertThat(galereyImagesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
