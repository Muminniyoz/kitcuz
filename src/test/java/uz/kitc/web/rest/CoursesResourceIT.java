package uz.kitc.web.rest;

import uz.kitc.KitcuzApp;
import uz.kitc.domain.Courses;
import uz.kitc.domain.Skill;
import uz.kitc.repository.CoursesRepository;
import uz.kitc.service.CoursesService;
import uz.kitc.service.dto.CoursesDTO;
import uz.kitc.service.mapper.CoursesMapper;
import uz.kitc.service.dto.CoursesCriteria;
import uz.kitc.service.CoursesQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CoursesResource} REST controller.
 */
@SpringBootTest(classes = KitcuzApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CoursesResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_ABOUT = "AAAAAAAAAA";
    private static final String UPDATED_ABOUT = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRICE = 0;
    private static final Integer UPDATED_PRICE = 1;
    private static final Integer SMALLER_PRICE = 0 - 1;

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    @Autowired
    private CoursesRepository coursesRepository;

    @Mock
    private CoursesRepository coursesRepositoryMock;

    @Autowired
    private CoursesMapper coursesMapper;

    @Mock
    private CoursesService coursesServiceMock;

    @Autowired
    private CoursesService coursesService;

    @Autowired
    private CoursesQueryService coursesQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCoursesMockMvc;

    private Courses courses;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Courses createEntity(EntityManager em) {
        Courses courses = new Courses()
            .title(DEFAULT_TITLE)
            .about(DEFAULT_ABOUT)
            .price(DEFAULT_PRICE)
            .imageUrl(DEFAULT_IMAGE_URL);
        return courses;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Courses createUpdatedEntity(EntityManager em) {
        Courses courses = new Courses()
            .title(UPDATED_TITLE)
            .about(UPDATED_ABOUT)
            .price(UPDATED_PRICE)
            .imageUrl(UPDATED_IMAGE_URL);
        return courses;
    }

    @BeforeEach
    public void initTest() {
        courses = createEntity(em);
    }

    @Test
    @Transactional
    public void createCourses() throws Exception {
        int databaseSizeBeforeCreate = coursesRepository.findAll().size();
        // Create the Courses
        CoursesDTO coursesDTO = coursesMapper.toDto(courses);
        restCoursesMockMvc.perform(post("/api/courses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(coursesDTO)))
            .andExpect(status().isCreated());

        // Validate the Courses in the database
        List<Courses> coursesList = coursesRepository.findAll();
        assertThat(coursesList).hasSize(databaseSizeBeforeCreate + 1);
        Courses testCourses = coursesList.get(coursesList.size() - 1);
        assertThat(testCourses.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCourses.getAbout()).isEqualTo(DEFAULT_ABOUT);
        assertThat(testCourses.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testCourses.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
    }

    @Test
    @Transactional
    public void createCoursesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = coursesRepository.findAll().size();

        // Create the Courses with an existing ID
        courses.setId(1L);
        CoursesDTO coursesDTO = coursesMapper.toDto(courses);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoursesMockMvc.perform(post("/api/courses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(coursesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Courses in the database
        List<Courses> coursesList = coursesRepository.findAll();
        assertThat(coursesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = coursesRepository.findAll().size();
        // set the field null
        courses.setTitle(null);

        // Create the Courses, which fails.
        CoursesDTO coursesDTO = coursesMapper.toDto(courses);


        restCoursesMockMvc.perform(post("/api/courses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(coursesDTO)))
            .andExpect(status().isBadRequest());

        List<Courses> coursesList = coursesRepository.findAll();
        assertThat(coursesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCourses() throws Exception {
        // Initialize the database
        coursesRepository.saveAndFlush(courses);

        // Get all the coursesList
        restCoursesMockMvc.perform(get("/api/courses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courses.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].about").value(hasItem(DEFAULT_ABOUT.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllCoursesWithEagerRelationshipsIsEnabled() throws Exception {
        when(coursesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCoursesMockMvc.perform(get("/api/courses?eagerload=true"))
            .andExpect(status().isOk());

        verify(coursesServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllCoursesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(coursesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCoursesMockMvc.perform(get("/api/courses?eagerload=true"))
            .andExpect(status().isOk());

        verify(coursesServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getCourses() throws Exception {
        // Initialize the database
        coursesRepository.saveAndFlush(courses);

        // Get the courses
        restCoursesMockMvc.perform(get("/api/courses/{id}", courses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(courses.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.about").value(DEFAULT_ABOUT.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL));
    }


    @Test
    @Transactional
    public void getCoursesByIdFiltering() throws Exception {
        // Initialize the database
        coursesRepository.saveAndFlush(courses);

        Long id = courses.getId();

        defaultCoursesShouldBeFound("id.equals=" + id);
        defaultCoursesShouldNotBeFound("id.notEquals=" + id);

        defaultCoursesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCoursesShouldNotBeFound("id.greaterThan=" + id);

        defaultCoursesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCoursesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCoursesByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        coursesRepository.saveAndFlush(courses);

        // Get all the coursesList where title equals to DEFAULT_TITLE
        defaultCoursesShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the coursesList where title equals to UPDATED_TITLE
        defaultCoursesShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllCoursesByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        coursesRepository.saveAndFlush(courses);

        // Get all the coursesList where title not equals to DEFAULT_TITLE
        defaultCoursesShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the coursesList where title not equals to UPDATED_TITLE
        defaultCoursesShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllCoursesByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        coursesRepository.saveAndFlush(courses);

        // Get all the coursesList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultCoursesShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the coursesList where title equals to UPDATED_TITLE
        defaultCoursesShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllCoursesByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        coursesRepository.saveAndFlush(courses);

        // Get all the coursesList where title is not null
        defaultCoursesShouldBeFound("title.specified=true");

        // Get all the coursesList where title is null
        defaultCoursesShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllCoursesByTitleContainsSomething() throws Exception {
        // Initialize the database
        coursesRepository.saveAndFlush(courses);

        // Get all the coursesList where title contains DEFAULT_TITLE
        defaultCoursesShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the coursesList where title contains UPDATED_TITLE
        defaultCoursesShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllCoursesByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        coursesRepository.saveAndFlush(courses);

        // Get all the coursesList where title does not contain DEFAULT_TITLE
        defaultCoursesShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the coursesList where title does not contain UPDATED_TITLE
        defaultCoursesShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllCoursesByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        coursesRepository.saveAndFlush(courses);

        // Get all the coursesList where price equals to DEFAULT_PRICE
        defaultCoursesShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the coursesList where price equals to UPDATED_PRICE
        defaultCoursesShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllCoursesByPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        coursesRepository.saveAndFlush(courses);

        // Get all the coursesList where price not equals to DEFAULT_PRICE
        defaultCoursesShouldNotBeFound("price.notEquals=" + DEFAULT_PRICE);

        // Get all the coursesList where price not equals to UPDATED_PRICE
        defaultCoursesShouldBeFound("price.notEquals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllCoursesByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        coursesRepository.saveAndFlush(courses);

        // Get all the coursesList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultCoursesShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the coursesList where price equals to UPDATED_PRICE
        defaultCoursesShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllCoursesByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        coursesRepository.saveAndFlush(courses);

        // Get all the coursesList where price is not null
        defaultCoursesShouldBeFound("price.specified=true");

        // Get all the coursesList where price is null
        defaultCoursesShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllCoursesByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        coursesRepository.saveAndFlush(courses);

        // Get all the coursesList where price is greater than or equal to DEFAULT_PRICE
        defaultCoursesShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the coursesList where price is greater than or equal to UPDATED_PRICE
        defaultCoursesShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllCoursesByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        coursesRepository.saveAndFlush(courses);

        // Get all the coursesList where price is less than or equal to DEFAULT_PRICE
        defaultCoursesShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the coursesList where price is less than or equal to SMALLER_PRICE
        defaultCoursesShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    public void getAllCoursesByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        coursesRepository.saveAndFlush(courses);

        // Get all the coursesList where price is less than DEFAULT_PRICE
        defaultCoursesShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the coursesList where price is less than UPDATED_PRICE
        defaultCoursesShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllCoursesByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        coursesRepository.saveAndFlush(courses);

        // Get all the coursesList where price is greater than DEFAULT_PRICE
        defaultCoursesShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the coursesList where price is greater than SMALLER_PRICE
        defaultCoursesShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }


    @Test
    @Transactional
    public void getAllCoursesByImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        coursesRepository.saveAndFlush(courses);

        // Get all the coursesList where imageUrl equals to DEFAULT_IMAGE_URL
        defaultCoursesShouldBeFound("imageUrl.equals=" + DEFAULT_IMAGE_URL);

        // Get all the coursesList where imageUrl equals to UPDATED_IMAGE_URL
        defaultCoursesShouldNotBeFound("imageUrl.equals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllCoursesByImageUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        coursesRepository.saveAndFlush(courses);

        // Get all the coursesList where imageUrl not equals to DEFAULT_IMAGE_URL
        defaultCoursesShouldNotBeFound("imageUrl.notEquals=" + DEFAULT_IMAGE_URL);

        // Get all the coursesList where imageUrl not equals to UPDATED_IMAGE_URL
        defaultCoursesShouldBeFound("imageUrl.notEquals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllCoursesByImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        coursesRepository.saveAndFlush(courses);

        // Get all the coursesList where imageUrl in DEFAULT_IMAGE_URL or UPDATED_IMAGE_URL
        defaultCoursesShouldBeFound("imageUrl.in=" + DEFAULT_IMAGE_URL + "," + UPDATED_IMAGE_URL);

        // Get all the coursesList where imageUrl equals to UPDATED_IMAGE_URL
        defaultCoursesShouldNotBeFound("imageUrl.in=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllCoursesByImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        coursesRepository.saveAndFlush(courses);

        // Get all the coursesList where imageUrl is not null
        defaultCoursesShouldBeFound("imageUrl.specified=true");

        // Get all the coursesList where imageUrl is null
        defaultCoursesShouldNotBeFound("imageUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllCoursesByImageUrlContainsSomething() throws Exception {
        // Initialize the database
        coursesRepository.saveAndFlush(courses);

        // Get all the coursesList where imageUrl contains DEFAULT_IMAGE_URL
        defaultCoursesShouldBeFound("imageUrl.contains=" + DEFAULT_IMAGE_URL);

        // Get all the coursesList where imageUrl contains UPDATED_IMAGE_URL
        defaultCoursesShouldNotBeFound("imageUrl.contains=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllCoursesByImageUrlNotContainsSomething() throws Exception {
        // Initialize the database
        coursesRepository.saveAndFlush(courses);

        // Get all the coursesList where imageUrl does not contain DEFAULT_IMAGE_URL
        defaultCoursesShouldNotBeFound("imageUrl.doesNotContain=" + DEFAULT_IMAGE_URL);

        // Get all the coursesList where imageUrl does not contain UPDATED_IMAGE_URL
        defaultCoursesShouldBeFound("imageUrl.doesNotContain=" + UPDATED_IMAGE_URL);
    }


    @Test
    @Transactional
    public void getAllCoursesBySkillsIsEqualToSomething() throws Exception {
        // Initialize the database
        coursesRepository.saveAndFlush(courses);
        Skill skills = SkillResourceIT.createEntity(em);
        em.persist(skills);
        em.flush();
        courses.addSkills(skills);
        coursesRepository.saveAndFlush(courses);
        Long skillsId = skills.getId();

        // Get all the coursesList where skills equals to skillsId
        defaultCoursesShouldBeFound("skillsId.equals=" + skillsId);

        // Get all the coursesList where skills equals to skillsId + 1
        defaultCoursesShouldNotBeFound("skillsId.equals=" + (skillsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCoursesShouldBeFound(String filter) throws Exception {
        restCoursesMockMvc.perform(get("/api/courses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courses.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].about").value(hasItem(DEFAULT_ABOUT.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)));

        // Check, that the count call also returns 1
        restCoursesMockMvc.perform(get("/api/courses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCoursesShouldNotBeFound(String filter) throws Exception {
        restCoursesMockMvc.perform(get("/api/courses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCoursesMockMvc.perform(get("/api/courses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCourses() throws Exception {
        // Get the courses
        restCoursesMockMvc.perform(get("/api/courses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCourses() throws Exception {
        // Initialize the database
        coursesRepository.saveAndFlush(courses);

        int databaseSizeBeforeUpdate = coursesRepository.findAll().size();

        // Update the courses
        Courses updatedCourses = coursesRepository.findById(courses.getId()).get();
        // Disconnect from session so that the updates on updatedCourses are not directly saved in db
        em.detach(updatedCourses);
        updatedCourses
            .title(UPDATED_TITLE)
            .about(UPDATED_ABOUT)
            .price(UPDATED_PRICE)
            .imageUrl(UPDATED_IMAGE_URL);
        CoursesDTO coursesDTO = coursesMapper.toDto(updatedCourses);

        restCoursesMockMvc.perform(put("/api/courses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(coursesDTO)))
            .andExpect(status().isOk());

        // Validate the Courses in the database
        List<Courses> coursesList = coursesRepository.findAll();
        assertThat(coursesList).hasSize(databaseSizeBeforeUpdate);
        Courses testCourses = coursesList.get(coursesList.size() - 1);
        assertThat(testCourses.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCourses.getAbout()).isEqualTo(UPDATED_ABOUT);
        assertThat(testCourses.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testCourses.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingCourses() throws Exception {
        int databaseSizeBeforeUpdate = coursesRepository.findAll().size();

        // Create the Courses
        CoursesDTO coursesDTO = coursesMapper.toDto(courses);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCoursesMockMvc.perform(put("/api/courses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(coursesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Courses in the database
        List<Courses> coursesList = coursesRepository.findAll();
        assertThat(coursesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCourses() throws Exception {
        // Initialize the database
        coursesRepository.saveAndFlush(courses);

        int databaseSizeBeforeDelete = coursesRepository.findAll().size();

        // Delete the courses
        restCoursesMockMvc.perform(delete("/api/courses/{id}", courses.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Courses> coursesList = coursesRepository.findAll();
        assertThat(coursesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
