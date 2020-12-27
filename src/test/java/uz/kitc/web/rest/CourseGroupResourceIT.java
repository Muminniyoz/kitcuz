package uz.kitc.web.rest;

import uz.kitc.KitcuzApp;
import uz.kitc.domain.CourseGroup;
import uz.kitc.domain.Teacher;
import uz.kitc.domain.Planning;
import uz.kitc.repository.CourseGroupRepository;
import uz.kitc.service.CourseGroupService;
import uz.kitc.service.dto.CourseGroupDTO;
import uz.kitc.service.mapper.CourseGroupMapper;
import uz.kitc.service.dto.CourseGroupCriteria;
import uz.kitc.service.CourseGroupQueryService;

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

import uz.kitc.domain.enumeration.GroupStatus;
/**
 * Integration tests for the {@link CourseGroupResource} REST controller.
 */
@SpringBootTest(classes = KitcuzApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CourseGroupResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_START_DATE = LocalDate.ofEpochDay(-1L);

    private static final GroupStatus DEFAULT_STATUS = GroupStatus.STARTING;
    private static final GroupStatus UPDATED_STATUS = GroupStatus.PLANNING;

    @Autowired
    private CourseGroupRepository courseGroupRepository;

    @Autowired
    private CourseGroupMapper courseGroupMapper;

    @Autowired
    private CourseGroupService courseGroupService;

    @Autowired
    private CourseGroupQueryService courseGroupQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCourseGroupMockMvc;

    private CourseGroup courseGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseGroup createEntity(EntityManager em) {
        CourseGroup courseGroup = new CourseGroup()
            .name(DEFAULT_NAME)
            .startDate(DEFAULT_START_DATE)
            .status(DEFAULT_STATUS);
        return courseGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseGroup createUpdatedEntity(EntityManager em) {
        CourseGroup courseGroup = new CourseGroup()
            .name(UPDATED_NAME)
            .startDate(UPDATED_START_DATE)
            .status(UPDATED_STATUS);
        return courseGroup;
    }

    @BeforeEach
    public void initTest() {
        courseGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createCourseGroup() throws Exception {
        int databaseSizeBeforeCreate = courseGroupRepository.findAll().size();
        // Create the CourseGroup
        CourseGroupDTO courseGroupDTO = courseGroupMapper.toDto(courseGroup);
        restCourseGroupMockMvc.perform(post("/api/course-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(courseGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the CourseGroup in the database
        List<CourseGroup> courseGroupList = courseGroupRepository.findAll();
        assertThat(courseGroupList).hasSize(databaseSizeBeforeCreate + 1);
        CourseGroup testCourseGroup = courseGroupList.get(courseGroupList.size() - 1);
        assertThat(testCourseGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCourseGroup.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testCourseGroup.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createCourseGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = courseGroupRepository.findAll().size();

        // Create the CourseGroup with an existing ID
        courseGroup.setId(1L);
        CourseGroupDTO courseGroupDTO = courseGroupMapper.toDto(courseGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourseGroupMockMvc.perform(post("/api/course-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(courseGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CourseGroup in the database
        List<CourseGroup> courseGroupList = courseGroupRepository.findAll();
        assertThat(courseGroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCourseGroups() throws Exception {
        // Initialize the database
        courseGroupRepository.saveAndFlush(courseGroup);

        // Get all the courseGroupList
        restCourseGroupMockMvc.perform(get("/api/course-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getCourseGroup() throws Exception {
        // Initialize the database
        courseGroupRepository.saveAndFlush(courseGroup);

        // Get the courseGroup
        restCourseGroupMockMvc.perform(get("/api/course-groups/{id}", courseGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(courseGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }


    @Test
    @Transactional
    public void getCourseGroupsByIdFiltering() throws Exception {
        // Initialize the database
        courseGroupRepository.saveAndFlush(courseGroup);

        Long id = courseGroup.getId();

        defaultCourseGroupShouldBeFound("id.equals=" + id);
        defaultCourseGroupShouldNotBeFound("id.notEquals=" + id);

        defaultCourseGroupShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCourseGroupShouldNotBeFound("id.greaterThan=" + id);

        defaultCourseGroupShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCourseGroupShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCourseGroupsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        courseGroupRepository.saveAndFlush(courseGroup);

        // Get all the courseGroupList where name equals to DEFAULT_NAME
        defaultCourseGroupShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the courseGroupList where name equals to UPDATED_NAME
        defaultCourseGroupShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCourseGroupsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseGroupRepository.saveAndFlush(courseGroup);

        // Get all the courseGroupList where name not equals to DEFAULT_NAME
        defaultCourseGroupShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the courseGroupList where name not equals to UPDATED_NAME
        defaultCourseGroupShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCourseGroupsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        courseGroupRepository.saveAndFlush(courseGroup);

        // Get all the courseGroupList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCourseGroupShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the courseGroupList where name equals to UPDATED_NAME
        defaultCourseGroupShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCourseGroupsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseGroupRepository.saveAndFlush(courseGroup);

        // Get all the courseGroupList where name is not null
        defaultCourseGroupShouldBeFound("name.specified=true");

        // Get all the courseGroupList where name is null
        defaultCourseGroupShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCourseGroupsByNameContainsSomething() throws Exception {
        // Initialize the database
        courseGroupRepository.saveAndFlush(courseGroup);

        // Get all the courseGroupList where name contains DEFAULT_NAME
        defaultCourseGroupShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the courseGroupList where name contains UPDATED_NAME
        defaultCourseGroupShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCourseGroupsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        courseGroupRepository.saveAndFlush(courseGroup);

        // Get all the courseGroupList where name does not contain DEFAULT_NAME
        defaultCourseGroupShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the courseGroupList where name does not contain UPDATED_NAME
        defaultCourseGroupShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCourseGroupsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        courseGroupRepository.saveAndFlush(courseGroup);

        // Get all the courseGroupList where startDate equals to DEFAULT_START_DATE
        defaultCourseGroupShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the courseGroupList where startDate equals to UPDATED_START_DATE
        defaultCourseGroupShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllCourseGroupsByStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseGroupRepository.saveAndFlush(courseGroup);

        // Get all the courseGroupList where startDate not equals to DEFAULT_START_DATE
        defaultCourseGroupShouldNotBeFound("startDate.notEquals=" + DEFAULT_START_DATE);

        // Get all the courseGroupList where startDate not equals to UPDATED_START_DATE
        defaultCourseGroupShouldBeFound("startDate.notEquals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllCourseGroupsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        courseGroupRepository.saveAndFlush(courseGroup);

        // Get all the courseGroupList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultCourseGroupShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the courseGroupList where startDate equals to UPDATED_START_DATE
        defaultCourseGroupShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllCourseGroupsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseGroupRepository.saveAndFlush(courseGroup);

        // Get all the courseGroupList where startDate is not null
        defaultCourseGroupShouldBeFound("startDate.specified=true");

        // Get all the courseGroupList where startDate is null
        defaultCourseGroupShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCourseGroupsByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courseGroupRepository.saveAndFlush(courseGroup);

        // Get all the courseGroupList where startDate is greater than or equal to DEFAULT_START_DATE
        defaultCourseGroupShouldBeFound("startDate.greaterThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the courseGroupList where startDate is greater than or equal to UPDATED_START_DATE
        defaultCourseGroupShouldNotBeFound("startDate.greaterThanOrEqual=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllCourseGroupsByStartDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courseGroupRepository.saveAndFlush(courseGroup);

        // Get all the courseGroupList where startDate is less than or equal to DEFAULT_START_DATE
        defaultCourseGroupShouldBeFound("startDate.lessThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the courseGroupList where startDate is less than or equal to SMALLER_START_DATE
        defaultCourseGroupShouldNotBeFound("startDate.lessThanOrEqual=" + SMALLER_START_DATE);
    }

    @Test
    @Transactional
    public void getAllCourseGroupsByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        courseGroupRepository.saveAndFlush(courseGroup);

        // Get all the courseGroupList where startDate is less than DEFAULT_START_DATE
        defaultCourseGroupShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the courseGroupList where startDate is less than UPDATED_START_DATE
        defaultCourseGroupShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllCourseGroupsByStartDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courseGroupRepository.saveAndFlush(courseGroup);

        // Get all the courseGroupList where startDate is greater than DEFAULT_START_DATE
        defaultCourseGroupShouldNotBeFound("startDate.greaterThan=" + DEFAULT_START_DATE);

        // Get all the courseGroupList where startDate is greater than SMALLER_START_DATE
        defaultCourseGroupShouldBeFound("startDate.greaterThan=" + SMALLER_START_DATE);
    }


    @Test
    @Transactional
    public void getAllCourseGroupsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        courseGroupRepository.saveAndFlush(courseGroup);

        // Get all the courseGroupList where status equals to DEFAULT_STATUS
        defaultCourseGroupShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the courseGroupList where status equals to UPDATED_STATUS
        defaultCourseGroupShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllCourseGroupsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseGroupRepository.saveAndFlush(courseGroup);

        // Get all the courseGroupList where status not equals to DEFAULT_STATUS
        defaultCourseGroupShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the courseGroupList where status not equals to UPDATED_STATUS
        defaultCourseGroupShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllCourseGroupsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        courseGroupRepository.saveAndFlush(courseGroup);

        // Get all the courseGroupList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultCourseGroupShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the courseGroupList where status equals to UPDATED_STATUS
        defaultCourseGroupShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllCourseGroupsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseGroupRepository.saveAndFlush(courseGroup);

        // Get all the courseGroupList where status is not null
        defaultCourseGroupShouldBeFound("status.specified=true");

        // Get all the courseGroupList where status is null
        defaultCourseGroupShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllCourseGroupsByTeacherIsEqualToSomething() throws Exception {
        // Initialize the database
        courseGroupRepository.saveAndFlush(courseGroup);
        Teacher teacher = TeacherResourceIT.createEntity(em);
        em.persist(teacher);
        em.flush();
        courseGroup.setTeacher(teacher);
        courseGroupRepository.saveAndFlush(courseGroup);
        Long teacherId = teacher.getId();

        // Get all the courseGroupList where teacher equals to teacherId
        defaultCourseGroupShouldBeFound("teacherId.equals=" + teacherId);

        // Get all the courseGroupList where teacher equals to teacherId + 1
        defaultCourseGroupShouldNotBeFound("teacherId.equals=" + (teacherId + 1));
    }


    @Test
    @Transactional
    public void getAllCourseGroupsByPlanningIsEqualToSomething() throws Exception {
        // Initialize the database
        courseGroupRepository.saveAndFlush(courseGroup);
        Planning planning = PlanningResourceIT.createEntity(em);
        em.persist(planning);
        em.flush();
        courseGroup.setPlanning(planning);
        courseGroupRepository.saveAndFlush(courseGroup);
        Long planningId = planning.getId();

        // Get all the courseGroupList where planning equals to planningId
        defaultCourseGroupShouldBeFound("planningId.equals=" + planningId);

        // Get all the courseGroupList where planning equals to planningId + 1
        defaultCourseGroupShouldNotBeFound("planningId.equals=" + (planningId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCourseGroupShouldBeFound(String filter) throws Exception {
        restCourseGroupMockMvc.perform(get("/api/course-groups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restCourseGroupMockMvc.perform(get("/api/course-groups/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCourseGroupShouldNotBeFound(String filter) throws Exception {
        restCourseGroupMockMvc.perform(get("/api/course-groups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCourseGroupMockMvc.perform(get("/api/course-groups/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCourseGroup() throws Exception {
        // Get the courseGroup
        restCourseGroupMockMvc.perform(get("/api/course-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCourseGroup() throws Exception {
        // Initialize the database
        courseGroupRepository.saveAndFlush(courseGroup);

        int databaseSizeBeforeUpdate = courseGroupRepository.findAll().size();

        // Update the courseGroup
        CourseGroup updatedCourseGroup = courseGroupRepository.findById(courseGroup.getId()).get();
        // Disconnect from session so that the updates on updatedCourseGroup are not directly saved in db
        em.detach(updatedCourseGroup);
        updatedCourseGroup
            .name(UPDATED_NAME)
            .startDate(UPDATED_START_DATE)
            .status(UPDATED_STATUS);
        CourseGroupDTO courseGroupDTO = courseGroupMapper.toDto(updatedCourseGroup);

        restCourseGroupMockMvc.perform(put("/api/course-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(courseGroupDTO)))
            .andExpect(status().isOk());

        // Validate the CourseGroup in the database
        List<CourseGroup> courseGroupList = courseGroupRepository.findAll();
        assertThat(courseGroupList).hasSize(databaseSizeBeforeUpdate);
        CourseGroup testCourseGroup = courseGroupList.get(courseGroupList.size() - 1);
        assertThat(testCourseGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCourseGroup.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testCourseGroup.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingCourseGroup() throws Exception {
        int databaseSizeBeforeUpdate = courseGroupRepository.findAll().size();

        // Create the CourseGroup
        CourseGroupDTO courseGroupDTO = courseGroupMapper.toDto(courseGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseGroupMockMvc.perform(put("/api/course-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(courseGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CourseGroup in the database
        List<CourseGroup> courseGroupList = courseGroupRepository.findAll();
        assertThat(courseGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCourseGroup() throws Exception {
        // Initialize the database
        courseGroupRepository.saveAndFlush(courseGroup);

        int databaseSizeBeforeDelete = courseGroupRepository.findAll().size();

        // Delete the courseGroup
        restCourseGroupMockMvc.perform(delete("/api/course-groups/{id}", courseGroup.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CourseGroup> courseGroupList = courseGroupRepository.findAll();
        assertThat(courseGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
