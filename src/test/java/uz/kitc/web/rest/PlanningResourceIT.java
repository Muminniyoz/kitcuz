package uz.kitc.web.rest;

import uz.kitc.KitcuzApp;
import uz.kitc.domain.Planning;
import uz.kitc.domain.Courses;
import uz.kitc.domain.Teacher;
import uz.kitc.repository.PlanningRepository;
import uz.kitc.service.PlanningService;
import uz.kitc.service.dto.PlanningDTO;
import uz.kitc.service.mapper.PlanningMapper;
import uz.kitc.service.dto.PlanningCriteria;
import uz.kitc.service.PlanningQueryService;

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
 * Integration tests for the {@link PlanningResource} REST controller.
 */
@SpringBootTest(classes = KitcuzApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PlanningResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ABOUT = "AAAAAAAAAA";
    private static final String UPDATED_ABOUT = "BBBBBBBBBB";

    private static final String DEFAULT_DURATION = "AAAAAAAAAA";
    private static final String UPDATED_DURATION = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_URL = "AAAAAAAAAA";
    private static final String UPDATED_FILE_URL = "BBBBBBBBBB";

    @Autowired
    private PlanningRepository planningRepository;

    @Autowired
    private PlanningMapper planningMapper;

    @Autowired
    private PlanningService planningService;

    @Autowired
    private PlanningQueryService planningQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlanningMockMvc;

    private Planning planning;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Planning createEntity(EntityManager em) {
        Planning planning = new Planning()
            .name(DEFAULT_NAME)
            .about(DEFAULT_ABOUT)
            .duration(DEFAULT_DURATION)
            .fileUrl(DEFAULT_FILE_URL);
        return planning;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Planning createUpdatedEntity(EntityManager em) {
        Planning planning = new Planning()
            .name(UPDATED_NAME)
            .about(UPDATED_ABOUT)
            .duration(UPDATED_DURATION)
            .fileUrl(UPDATED_FILE_URL);
        return planning;
    }

    @BeforeEach
    public void initTest() {
        planning = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlanning() throws Exception {
        int databaseSizeBeforeCreate = planningRepository.findAll().size();
        // Create the Planning
        PlanningDTO planningDTO = planningMapper.toDto(planning);
        restPlanningMockMvc.perform(post("/api/plannings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planningDTO)))
            .andExpect(status().isCreated());

        // Validate the Planning in the database
        List<Planning> planningList = planningRepository.findAll();
        assertThat(planningList).hasSize(databaseSizeBeforeCreate + 1);
        Planning testPlanning = planningList.get(planningList.size() - 1);
        assertThat(testPlanning.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPlanning.getAbout()).isEqualTo(DEFAULT_ABOUT);
        assertThat(testPlanning.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testPlanning.getFileUrl()).isEqualTo(DEFAULT_FILE_URL);
    }

    @Test
    @Transactional
    public void createPlanningWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = planningRepository.findAll().size();

        // Create the Planning with an existing ID
        planning.setId(1L);
        PlanningDTO planningDTO = planningMapper.toDto(planning);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanningMockMvc.perform(post("/api/plannings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planningDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Planning in the database
        List<Planning> planningList = planningRepository.findAll();
        assertThat(planningList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPlannings() throws Exception {
        // Initialize the database
        planningRepository.saveAndFlush(planning);

        // Get all the planningList
        restPlanningMockMvc.perform(get("/api/plannings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planning.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].about").value(hasItem(DEFAULT_ABOUT)))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].fileUrl").value(hasItem(DEFAULT_FILE_URL)));
    }
    
    @Test
    @Transactional
    public void getPlanning() throws Exception {
        // Initialize the database
        planningRepository.saveAndFlush(planning);

        // Get the planning
        restPlanningMockMvc.perform(get("/api/plannings/{id}", planning.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(planning.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.about").value(DEFAULT_ABOUT))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.fileUrl").value(DEFAULT_FILE_URL));
    }


    @Test
    @Transactional
    public void getPlanningsByIdFiltering() throws Exception {
        // Initialize the database
        planningRepository.saveAndFlush(planning);

        Long id = planning.getId();

        defaultPlanningShouldBeFound("id.equals=" + id);
        defaultPlanningShouldNotBeFound("id.notEquals=" + id);

        defaultPlanningShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPlanningShouldNotBeFound("id.greaterThan=" + id);

        defaultPlanningShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPlanningShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPlanningsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        planningRepository.saveAndFlush(planning);

        // Get all the planningList where name equals to DEFAULT_NAME
        defaultPlanningShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the planningList where name equals to UPDATED_NAME
        defaultPlanningShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPlanningsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planningRepository.saveAndFlush(planning);

        // Get all the planningList where name not equals to DEFAULT_NAME
        defaultPlanningShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the planningList where name not equals to UPDATED_NAME
        defaultPlanningShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPlanningsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        planningRepository.saveAndFlush(planning);

        // Get all the planningList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPlanningShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the planningList where name equals to UPDATED_NAME
        defaultPlanningShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPlanningsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        planningRepository.saveAndFlush(planning);

        // Get all the planningList where name is not null
        defaultPlanningShouldBeFound("name.specified=true");

        // Get all the planningList where name is null
        defaultPlanningShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllPlanningsByNameContainsSomething() throws Exception {
        // Initialize the database
        planningRepository.saveAndFlush(planning);

        // Get all the planningList where name contains DEFAULT_NAME
        defaultPlanningShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the planningList where name contains UPDATED_NAME
        defaultPlanningShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPlanningsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        planningRepository.saveAndFlush(planning);

        // Get all the planningList where name does not contain DEFAULT_NAME
        defaultPlanningShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the planningList where name does not contain UPDATED_NAME
        defaultPlanningShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllPlanningsByAboutIsEqualToSomething() throws Exception {
        // Initialize the database
        planningRepository.saveAndFlush(planning);

        // Get all the planningList where about equals to DEFAULT_ABOUT
        defaultPlanningShouldBeFound("about.equals=" + DEFAULT_ABOUT);

        // Get all the planningList where about equals to UPDATED_ABOUT
        defaultPlanningShouldNotBeFound("about.equals=" + UPDATED_ABOUT);
    }

    @Test
    @Transactional
    public void getAllPlanningsByAboutIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planningRepository.saveAndFlush(planning);

        // Get all the planningList where about not equals to DEFAULT_ABOUT
        defaultPlanningShouldNotBeFound("about.notEquals=" + DEFAULT_ABOUT);

        // Get all the planningList where about not equals to UPDATED_ABOUT
        defaultPlanningShouldBeFound("about.notEquals=" + UPDATED_ABOUT);
    }

    @Test
    @Transactional
    public void getAllPlanningsByAboutIsInShouldWork() throws Exception {
        // Initialize the database
        planningRepository.saveAndFlush(planning);

        // Get all the planningList where about in DEFAULT_ABOUT or UPDATED_ABOUT
        defaultPlanningShouldBeFound("about.in=" + DEFAULT_ABOUT + "," + UPDATED_ABOUT);

        // Get all the planningList where about equals to UPDATED_ABOUT
        defaultPlanningShouldNotBeFound("about.in=" + UPDATED_ABOUT);
    }

    @Test
    @Transactional
    public void getAllPlanningsByAboutIsNullOrNotNull() throws Exception {
        // Initialize the database
        planningRepository.saveAndFlush(planning);

        // Get all the planningList where about is not null
        defaultPlanningShouldBeFound("about.specified=true");

        // Get all the planningList where about is null
        defaultPlanningShouldNotBeFound("about.specified=false");
    }
                @Test
    @Transactional
    public void getAllPlanningsByAboutContainsSomething() throws Exception {
        // Initialize the database
        planningRepository.saveAndFlush(planning);

        // Get all the planningList where about contains DEFAULT_ABOUT
        defaultPlanningShouldBeFound("about.contains=" + DEFAULT_ABOUT);

        // Get all the planningList where about contains UPDATED_ABOUT
        defaultPlanningShouldNotBeFound("about.contains=" + UPDATED_ABOUT);
    }

    @Test
    @Transactional
    public void getAllPlanningsByAboutNotContainsSomething() throws Exception {
        // Initialize the database
        planningRepository.saveAndFlush(planning);

        // Get all the planningList where about does not contain DEFAULT_ABOUT
        defaultPlanningShouldNotBeFound("about.doesNotContain=" + DEFAULT_ABOUT);

        // Get all the planningList where about does not contain UPDATED_ABOUT
        defaultPlanningShouldBeFound("about.doesNotContain=" + UPDATED_ABOUT);
    }


    @Test
    @Transactional
    public void getAllPlanningsByDurationIsEqualToSomething() throws Exception {
        // Initialize the database
        planningRepository.saveAndFlush(planning);

        // Get all the planningList where duration equals to DEFAULT_DURATION
        defaultPlanningShouldBeFound("duration.equals=" + DEFAULT_DURATION);

        // Get all the planningList where duration equals to UPDATED_DURATION
        defaultPlanningShouldNotBeFound("duration.equals=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    public void getAllPlanningsByDurationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planningRepository.saveAndFlush(planning);

        // Get all the planningList where duration not equals to DEFAULT_DURATION
        defaultPlanningShouldNotBeFound("duration.notEquals=" + DEFAULT_DURATION);

        // Get all the planningList where duration not equals to UPDATED_DURATION
        defaultPlanningShouldBeFound("duration.notEquals=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    public void getAllPlanningsByDurationIsInShouldWork() throws Exception {
        // Initialize the database
        planningRepository.saveAndFlush(planning);

        // Get all the planningList where duration in DEFAULT_DURATION or UPDATED_DURATION
        defaultPlanningShouldBeFound("duration.in=" + DEFAULT_DURATION + "," + UPDATED_DURATION);

        // Get all the planningList where duration equals to UPDATED_DURATION
        defaultPlanningShouldNotBeFound("duration.in=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    public void getAllPlanningsByDurationIsNullOrNotNull() throws Exception {
        // Initialize the database
        planningRepository.saveAndFlush(planning);

        // Get all the planningList where duration is not null
        defaultPlanningShouldBeFound("duration.specified=true");

        // Get all the planningList where duration is null
        defaultPlanningShouldNotBeFound("duration.specified=false");
    }
                @Test
    @Transactional
    public void getAllPlanningsByDurationContainsSomething() throws Exception {
        // Initialize the database
        planningRepository.saveAndFlush(planning);

        // Get all the planningList where duration contains DEFAULT_DURATION
        defaultPlanningShouldBeFound("duration.contains=" + DEFAULT_DURATION);

        // Get all the planningList where duration contains UPDATED_DURATION
        defaultPlanningShouldNotBeFound("duration.contains=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    public void getAllPlanningsByDurationNotContainsSomething() throws Exception {
        // Initialize the database
        planningRepository.saveAndFlush(planning);

        // Get all the planningList where duration does not contain DEFAULT_DURATION
        defaultPlanningShouldNotBeFound("duration.doesNotContain=" + DEFAULT_DURATION);

        // Get all the planningList where duration does not contain UPDATED_DURATION
        defaultPlanningShouldBeFound("duration.doesNotContain=" + UPDATED_DURATION);
    }


    @Test
    @Transactional
    public void getAllPlanningsByFileUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        planningRepository.saveAndFlush(planning);

        // Get all the planningList where fileUrl equals to DEFAULT_FILE_URL
        defaultPlanningShouldBeFound("fileUrl.equals=" + DEFAULT_FILE_URL);

        // Get all the planningList where fileUrl equals to UPDATED_FILE_URL
        defaultPlanningShouldNotBeFound("fileUrl.equals=" + UPDATED_FILE_URL);
    }

    @Test
    @Transactional
    public void getAllPlanningsByFileUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planningRepository.saveAndFlush(planning);

        // Get all the planningList where fileUrl not equals to DEFAULT_FILE_URL
        defaultPlanningShouldNotBeFound("fileUrl.notEquals=" + DEFAULT_FILE_URL);

        // Get all the planningList where fileUrl not equals to UPDATED_FILE_URL
        defaultPlanningShouldBeFound("fileUrl.notEquals=" + UPDATED_FILE_URL);
    }

    @Test
    @Transactional
    public void getAllPlanningsByFileUrlIsInShouldWork() throws Exception {
        // Initialize the database
        planningRepository.saveAndFlush(planning);

        // Get all the planningList where fileUrl in DEFAULT_FILE_URL or UPDATED_FILE_URL
        defaultPlanningShouldBeFound("fileUrl.in=" + DEFAULT_FILE_URL + "," + UPDATED_FILE_URL);

        // Get all the planningList where fileUrl equals to UPDATED_FILE_URL
        defaultPlanningShouldNotBeFound("fileUrl.in=" + UPDATED_FILE_URL);
    }

    @Test
    @Transactional
    public void getAllPlanningsByFileUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        planningRepository.saveAndFlush(planning);

        // Get all the planningList where fileUrl is not null
        defaultPlanningShouldBeFound("fileUrl.specified=true");

        // Get all the planningList where fileUrl is null
        defaultPlanningShouldNotBeFound("fileUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllPlanningsByFileUrlContainsSomething() throws Exception {
        // Initialize the database
        planningRepository.saveAndFlush(planning);

        // Get all the planningList where fileUrl contains DEFAULT_FILE_URL
        defaultPlanningShouldBeFound("fileUrl.contains=" + DEFAULT_FILE_URL);

        // Get all the planningList where fileUrl contains UPDATED_FILE_URL
        defaultPlanningShouldNotBeFound("fileUrl.contains=" + UPDATED_FILE_URL);
    }

    @Test
    @Transactional
    public void getAllPlanningsByFileUrlNotContainsSomething() throws Exception {
        // Initialize the database
        planningRepository.saveAndFlush(planning);

        // Get all the planningList where fileUrl does not contain DEFAULT_FILE_URL
        defaultPlanningShouldNotBeFound("fileUrl.doesNotContain=" + DEFAULT_FILE_URL);

        // Get all the planningList where fileUrl does not contain UPDATED_FILE_URL
        defaultPlanningShouldBeFound("fileUrl.doesNotContain=" + UPDATED_FILE_URL);
    }


    @Test
    @Transactional
    public void getAllPlanningsByCourseIsEqualToSomething() throws Exception {
        // Initialize the database
        planningRepository.saveAndFlush(planning);
        Courses course = CoursesResourceIT.createEntity(em);
        em.persist(course);
        em.flush();
        planning.setCourse(course);
        planningRepository.saveAndFlush(planning);
        Long courseId = course.getId();

        // Get all the planningList where course equals to courseId
        defaultPlanningShouldBeFound("courseId.equals=" + courseId);

        // Get all the planningList where course equals to courseId + 1
        defaultPlanningShouldNotBeFound("courseId.equals=" + (courseId + 1));
    }


    @Test
    @Transactional
    public void getAllPlanningsByTeacherIsEqualToSomething() throws Exception {
        // Initialize the database
        planningRepository.saveAndFlush(planning);
        Teacher teacher = TeacherResourceIT.createEntity(em);
        em.persist(teacher);
        em.flush();
        planning.setTeacher(teacher);
        planningRepository.saveAndFlush(planning);
        Long teacherId = teacher.getId();

        // Get all the planningList where teacher equals to teacherId
        defaultPlanningShouldBeFound("teacherId.equals=" + teacherId);

        // Get all the planningList where teacher equals to teacherId + 1
        defaultPlanningShouldNotBeFound("teacherId.equals=" + (teacherId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPlanningShouldBeFound(String filter) throws Exception {
        restPlanningMockMvc.perform(get("/api/plannings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planning.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].about").value(hasItem(DEFAULT_ABOUT)))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].fileUrl").value(hasItem(DEFAULT_FILE_URL)));

        // Check, that the count call also returns 1
        restPlanningMockMvc.perform(get("/api/plannings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPlanningShouldNotBeFound(String filter) throws Exception {
        restPlanningMockMvc.perform(get("/api/plannings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPlanningMockMvc.perform(get("/api/plannings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPlanning() throws Exception {
        // Get the planning
        restPlanningMockMvc.perform(get("/api/plannings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlanning() throws Exception {
        // Initialize the database
        planningRepository.saveAndFlush(planning);

        int databaseSizeBeforeUpdate = planningRepository.findAll().size();

        // Update the planning
        Planning updatedPlanning = planningRepository.findById(planning.getId()).get();
        // Disconnect from session so that the updates on updatedPlanning are not directly saved in db
        em.detach(updatedPlanning);
        updatedPlanning
            .name(UPDATED_NAME)
            .about(UPDATED_ABOUT)
            .duration(UPDATED_DURATION)
            .fileUrl(UPDATED_FILE_URL);
        PlanningDTO planningDTO = planningMapper.toDto(updatedPlanning);

        restPlanningMockMvc.perform(put("/api/plannings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planningDTO)))
            .andExpect(status().isOk());

        // Validate the Planning in the database
        List<Planning> planningList = planningRepository.findAll();
        assertThat(planningList).hasSize(databaseSizeBeforeUpdate);
        Planning testPlanning = planningList.get(planningList.size() - 1);
        assertThat(testPlanning.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlanning.getAbout()).isEqualTo(UPDATED_ABOUT);
        assertThat(testPlanning.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testPlanning.getFileUrl()).isEqualTo(UPDATED_FILE_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingPlanning() throws Exception {
        int databaseSizeBeforeUpdate = planningRepository.findAll().size();

        // Create the Planning
        PlanningDTO planningDTO = planningMapper.toDto(planning);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanningMockMvc.perform(put("/api/plannings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planningDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Planning in the database
        List<Planning> planningList = planningRepository.findAll();
        assertThat(planningList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlanning() throws Exception {
        // Initialize the database
        planningRepository.saveAndFlush(planning);

        int databaseSizeBeforeDelete = planningRepository.findAll().size();

        // Delete the planning
        restPlanningMockMvc.perform(delete("/api/plannings/{id}", planning.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Planning> planningList = planningRepository.findAll();
        assertThat(planningList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
