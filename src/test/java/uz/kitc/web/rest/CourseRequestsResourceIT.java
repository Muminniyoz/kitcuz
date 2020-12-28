package uz.kitc.web.rest;

import uz.kitc.KitcuzApp;
import uz.kitc.domain.CourseRequests;
import uz.kitc.domain.Courses;
import uz.kitc.domain.CourseGroup;
import uz.kitc.repository.CourseRequestsRepository;
import uz.kitc.service.CourseRequestsService;
import uz.kitc.service.dto.CourseRequestsDTO;
import uz.kitc.service.mapper.CourseRequestsMapper;
import uz.kitc.service.dto.CourseRequestsCriteria;
import uz.kitc.service.CourseRequestsQueryService;

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

import uz.kitc.domain.enumeration.Gender;
/**
 * Integration tests for the {@link CourseRequestsResource} REST controller.
 */
@SpringBootTest(classes = KitcuzApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CourseRequestsResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_OF_BIRTH = LocalDate.ofEpochDay(-1L);

    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;

    private static final LocalDate DEFAULT_REGISTERATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REGISTERATION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_REGISTERATION_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE = "BBBBBBBBBB";

    @Autowired
    private CourseRequestsRepository courseRequestsRepository;

    @Autowired
    private CourseRequestsMapper courseRequestsMapper;

    @Autowired
    private CourseRequestsService courseRequestsService;

    @Autowired
    private CourseRequestsQueryService courseRequestsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCourseRequestsMockMvc;

    private CourseRequests courseRequests;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseRequests createEntity(EntityManager em) {
        CourseRequests courseRequests = new CourseRequests()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .email(DEFAULT_EMAIL)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .gender(DEFAULT_GENDER)
            .registerationDate(DEFAULT_REGISTERATION_DATE)
            .telephone(DEFAULT_TELEPHONE)
            .mobile(DEFAULT_MOBILE);
        return courseRequests;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseRequests createUpdatedEntity(EntityManager em) {
        CourseRequests courseRequests = new CourseRequests()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .email(UPDATED_EMAIL)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .gender(UPDATED_GENDER)
            .registerationDate(UPDATED_REGISTERATION_DATE)
            .telephone(UPDATED_TELEPHONE)
            .mobile(UPDATED_MOBILE);
        return courseRequests;
    }

    @BeforeEach
    public void initTest() {
        courseRequests = createEntity(em);
    }

    @Test
    @Transactional
    public void createCourseRequests() throws Exception {
        int databaseSizeBeforeCreate = courseRequestsRepository.findAll().size();
        // Create the CourseRequests
        CourseRequestsDTO courseRequestsDTO = courseRequestsMapper.toDto(courseRequests);
        restCourseRequestsMockMvc.perform(post("/api/course-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(courseRequestsDTO)))
            .andExpect(status().isCreated());

        // Validate the CourseRequests in the database
        List<CourseRequests> courseRequestsList = courseRequestsRepository.findAll();
        assertThat(courseRequestsList).hasSize(databaseSizeBeforeCreate + 1);
        CourseRequests testCourseRequests = courseRequestsList.get(courseRequestsList.size() - 1);
        assertThat(testCourseRequests.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testCourseRequests.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testCourseRequests.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testCourseRequests.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCourseRequests.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testCourseRequests.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testCourseRequests.getRegisterationDate()).isEqualTo(DEFAULT_REGISTERATION_DATE);
        assertThat(testCourseRequests.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testCourseRequests.getMobile()).isEqualTo(DEFAULT_MOBILE);
    }

    @Test
    @Transactional
    public void createCourseRequestsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = courseRequestsRepository.findAll().size();

        // Create the CourseRequests with an existing ID
        courseRequests.setId(1L);
        CourseRequestsDTO courseRequestsDTO = courseRequestsMapper.toDto(courseRequests);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourseRequestsMockMvc.perform(post("/api/course-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(courseRequestsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CourseRequests in the database
        List<CourseRequests> courseRequestsList = courseRequestsRepository.findAll();
        assertThat(courseRequestsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseRequestsRepository.findAll().size();
        // set the field null
        courseRequests.setFirstName(null);

        // Create the CourseRequests, which fails.
        CourseRequestsDTO courseRequestsDTO = courseRequestsMapper.toDto(courseRequests);


        restCourseRequestsMockMvc.perform(post("/api/course-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(courseRequestsDTO)))
            .andExpect(status().isBadRequest());

        List<CourseRequests> courseRequestsList = courseRequestsRepository.findAll();
        assertThat(courseRequestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseRequestsRepository.findAll().size();
        // set the field null
        courseRequests.setLastName(null);

        // Create the CourseRequests, which fails.
        CourseRequestsDTO courseRequestsDTO = courseRequestsMapper.toDto(courseRequests);


        restCourseRequestsMockMvc.perform(post("/api/course-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(courseRequestsDTO)))
            .andExpect(status().isBadRequest());

        List<CourseRequests> courseRequestsList = courseRequestsRepository.findAll();
        assertThat(courseRequestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCourseRequests() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList
        restCourseRequestsMockMvc.perform(get("/api/course-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseRequests.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].registerationDate").value(hasItem(DEFAULT_REGISTERATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)));
    }
    
    @Test
    @Transactional
    public void getCourseRequests() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get the courseRequests
        restCourseRequestsMockMvc.perform(get("/api/course-requests/{id}", courseRequests.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(courseRequests.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.registerationDate").value(DEFAULT_REGISTERATION_DATE.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE));
    }


    @Test
    @Transactional
    public void getCourseRequestsByIdFiltering() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        Long id = courseRequests.getId();

        defaultCourseRequestsShouldBeFound("id.equals=" + id);
        defaultCourseRequestsShouldNotBeFound("id.notEquals=" + id);

        defaultCourseRequestsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCourseRequestsShouldNotBeFound("id.greaterThan=" + id);

        defaultCourseRequestsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCourseRequestsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCourseRequestsByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where firstName equals to DEFAULT_FIRST_NAME
        defaultCourseRequestsShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the courseRequestsList where firstName equals to UPDATED_FIRST_NAME
        defaultCourseRequestsShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllCourseRequestsByFirstNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where firstName not equals to DEFAULT_FIRST_NAME
        defaultCourseRequestsShouldNotBeFound("firstName.notEquals=" + DEFAULT_FIRST_NAME);

        // Get all the courseRequestsList where firstName not equals to UPDATED_FIRST_NAME
        defaultCourseRequestsShouldBeFound("firstName.notEquals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllCourseRequestsByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultCourseRequestsShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the courseRequestsList where firstName equals to UPDATED_FIRST_NAME
        defaultCourseRequestsShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllCourseRequestsByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where firstName is not null
        defaultCourseRequestsShouldBeFound("firstName.specified=true");

        // Get all the courseRequestsList where firstName is null
        defaultCourseRequestsShouldNotBeFound("firstName.specified=false");
    }
                @Test
    @Transactional
    public void getAllCourseRequestsByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where firstName contains DEFAULT_FIRST_NAME
        defaultCourseRequestsShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the courseRequestsList where firstName contains UPDATED_FIRST_NAME
        defaultCourseRequestsShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllCourseRequestsByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where firstName does not contain DEFAULT_FIRST_NAME
        defaultCourseRequestsShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the courseRequestsList where firstName does not contain UPDATED_FIRST_NAME
        defaultCourseRequestsShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }


    @Test
    @Transactional
    public void getAllCourseRequestsByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where lastName equals to DEFAULT_LAST_NAME
        defaultCourseRequestsShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the courseRequestsList where lastName equals to UPDATED_LAST_NAME
        defaultCourseRequestsShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllCourseRequestsByLastNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where lastName not equals to DEFAULT_LAST_NAME
        defaultCourseRequestsShouldNotBeFound("lastName.notEquals=" + DEFAULT_LAST_NAME);

        // Get all the courseRequestsList where lastName not equals to UPDATED_LAST_NAME
        defaultCourseRequestsShouldBeFound("lastName.notEquals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllCourseRequestsByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultCourseRequestsShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the courseRequestsList where lastName equals to UPDATED_LAST_NAME
        defaultCourseRequestsShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllCourseRequestsByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where lastName is not null
        defaultCourseRequestsShouldBeFound("lastName.specified=true");

        // Get all the courseRequestsList where lastName is null
        defaultCourseRequestsShouldNotBeFound("lastName.specified=false");
    }
                @Test
    @Transactional
    public void getAllCourseRequestsByLastNameContainsSomething() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where lastName contains DEFAULT_LAST_NAME
        defaultCourseRequestsShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the courseRequestsList where lastName contains UPDATED_LAST_NAME
        defaultCourseRequestsShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllCourseRequestsByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where lastName does not contain DEFAULT_LAST_NAME
        defaultCourseRequestsShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the courseRequestsList where lastName does not contain UPDATED_LAST_NAME
        defaultCourseRequestsShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }


    @Test
    @Transactional
    public void getAllCourseRequestsByMiddleNameIsEqualToSomething() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where middleName equals to DEFAULT_MIDDLE_NAME
        defaultCourseRequestsShouldBeFound("middleName.equals=" + DEFAULT_MIDDLE_NAME);

        // Get all the courseRequestsList where middleName equals to UPDATED_MIDDLE_NAME
        defaultCourseRequestsShouldNotBeFound("middleName.equals=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    public void getAllCourseRequestsByMiddleNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where middleName not equals to DEFAULT_MIDDLE_NAME
        defaultCourseRequestsShouldNotBeFound("middleName.notEquals=" + DEFAULT_MIDDLE_NAME);

        // Get all the courseRequestsList where middleName not equals to UPDATED_MIDDLE_NAME
        defaultCourseRequestsShouldBeFound("middleName.notEquals=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    public void getAllCourseRequestsByMiddleNameIsInShouldWork() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where middleName in DEFAULT_MIDDLE_NAME or UPDATED_MIDDLE_NAME
        defaultCourseRequestsShouldBeFound("middleName.in=" + DEFAULT_MIDDLE_NAME + "," + UPDATED_MIDDLE_NAME);

        // Get all the courseRequestsList where middleName equals to UPDATED_MIDDLE_NAME
        defaultCourseRequestsShouldNotBeFound("middleName.in=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    public void getAllCourseRequestsByMiddleNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where middleName is not null
        defaultCourseRequestsShouldBeFound("middleName.specified=true");

        // Get all the courseRequestsList where middleName is null
        defaultCourseRequestsShouldNotBeFound("middleName.specified=false");
    }
                @Test
    @Transactional
    public void getAllCourseRequestsByMiddleNameContainsSomething() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where middleName contains DEFAULT_MIDDLE_NAME
        defaultCourseRequestsShouldBeFound("middleName.contains=" + DEFAULT_MIDDLE_NAME);

        // Get all the courseRequestsList where middleName contains UPDATED_MIDDLE_NAME
        defaultCourseRequestsShouldNotBeFound("middleName.contains=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    public void getAllCourseRequestsByMiddleNameNotContainsSomething() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where middleName does not contain DEFAULT_MIDDLE_NAME
        defaultCourseRequestsShouldNotBeFound("middleName.doesNotContain=" + DEFAULT_MIDDLE_NAME);

        // Get all the courseRequestsList where middleName does not contain UPDATED_MIDDLE_NAME
        defaultCourseRequestsShouldBeFound("middleName.doesNotContain=" + UPDATED_MIDDLE_NAME);
    }


    @Test
    @Transactional
    public void getAllCourseRequestsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where email equals to DEFAULT_EMAIL
        defaultCourseRequestsShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the courseRequestsList where email equals to UPDATED_EMAIL
        defaultCourseRequestsShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCourseRequestsByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where email not equals to DEFAULT_EMAIL
        defaultCourseRequestsShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the courseRequestsList where email not equals to UPDATED_EMAIL
        defaultCourseRequestsShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCourseRequestsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultCourseRequestsShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the courseRequestsList where email equals to UPDATED_EMAIL
        defaultCourseRequestsShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCourseRequestsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where email is not null
        defaultCourseRequestsShouldBeFound("email.specified=true");

        // Get all the courseRequestsList where email is null
        defaultCourseRequestsShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllCourseRequestsByEmailContainsSomething() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where email contains DEFAULT_EMAIL
        defaultCourseRequestsShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the courseRequestsList where email contains UPDATED_EMAIL
        defaultCourseRequestsShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCourseRequestsByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where email does not contain DEFAULT_EMAIL
        defaultCourseRequestsShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the courseRequestsList where email does not contain UPDATED_EMAIL
        defaultCourseRequestsShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllCourseRequestsByDateOfBirthIsEqualToSomething() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where dateOfBirth equals to DEFAULT_DATE_OF_BIRTH
        defaultCourseRequestsShouldBeFound("dateOfBirth.equals=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the courseRequestsList where dateOfBirth equals to UPDATED_DATE_OF_BIRTH
        defaultCourseRequestsShouldNotBeFound("dateOfBirth.equals=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllCourseRequestsByDateOfBirthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where dateOfBirth not equals to DEFAULT_DATE_OF_BIRTH
        defaultCourseRequestsShouldNotBeFound("dateOfBirth.notEquals=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the courseRequestsList where dateOfBirth not equals to UPDATED_DATE_OF_BIRTH
        defaultCourseRequestsShouldBeFound("dateOfBirth.notEquals=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllCourseRequestsByDateOfBirthIsInShouldWork() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where dateOfBirth in DEFAULT_DATE_OF_BIRTH or UPDATED_DATE_OF_BIRTH
        defaultCourseRequestsShouldBeFound("dateOfBirth.in=" + DEFAULT_DATE_OF_BIRTH + "," + UPDATED_DATE_OF_BIRTH);

        // Get all the courseRequestsList where dateOfBirth equals to UPDATED_DATE_OF_BIRTH
        defaultCourseRequestsShouldNotBeFound("dateOfBirth.in=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllCourseRequestsByDateOfBirthIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where dateOfBirth is not null
        defaultCourseRequestsShouldBeFound("dateOfBirth.specified=true");

        // Get all the courseRequestsList where dateOfBirth is null
        defaultCourseRequestsShouldNotBeFound("dateOfBirth.specified=false");
    }

    @Test
    @Transactional
    public void getAllCourseRequestsByDateOfBirthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where dateOfBirth is greater than or equal to DEFAULT_DATE_OF_BIRTH
        defaultCourseRequestsShouldBeFound("dateOfBirth.greaterThanOrEqual=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the courseRequestsList where dateOfBirth is greater than or equal to UPDATED_DATE_OF_BIRTH
        defaultCourseRequestsShouldNotBeFound("dateOfBirth.greaterThanOrEqual=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllCourseRequestsByDateOfBirthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where dateOfBirth is less than or equal to DEFAULT_DATE_OF_BIRTH
        defaultCourseRequestsShouldBeFound("dateOfBirth.lessThanOrEqual=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the courseRequestsList where dateOfBirth is less than or equal to SMALLER_DATE_OF_BIRTH
        defaultCourseRequestsShouldNotBeFound("dateOfBirth.lessThanOrEqual=" + SMALLER_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllCourseRequestsByDateOfBirthIsLessThanSomething() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where dateOfBirth is less than DEFAULT_DATE_OF_BIRTH
        defaultCourseRequestsShouldNotBeFound("dateOfBirth.lessThan=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the courseRequestsList where dateOfBirth is less than UPDATED_DATE_OF_BIRTH
        defaultCourseRequestsShouldBeFound("dateOfBirth.lessThan=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllCourseRequestsByDateOfBirthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where dateOfBirth is greater than DEFAULT_DATE_OF_BIRTH
        defaultCourseRequestsShouldNotBeFound("dateOfBirth.greaterThan=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the courseRequestsList where dateOfBirth is greater than SMALLER_DATE_OF_BIRTH
        defaultCourseRequestsShouldBeFound("dateOfBirth.greaterThan=" + SMALLER_DATE_OF_BIRTH);
    }


    @Test
    @Transactional
    public void getAllCourseRequestsByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where gender equals to DEFAULT_GENDER
        defaultCourseRequestsShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the courseRequestsList where gender equals to UPDATED_GENDER
        defaultCourseRequestsShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllCourseRequestsByGenderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where gender not equals to DEFAULT_GENDER
        defaultCourseRequestsShouldNotBeFound("gender.notEquals=" + DEFAULT_GENDER);

        // Get all the courseRequestsList where gender not equals to UPDATED_GENDER
        defaultCourseRequestsShouldBeFound("gender.notEquals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllCourseRequestsByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultCourseRequestsShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the courseRequestsList where gender equals to UPDATED_GENDER
        defaultCourseRequestsShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllCourseRequestsByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where gender is not null
        defaultCourseRequestsShouldBeFound("gender.specified=true");

        // Get all the courseRequestsList where gender is null
        defaultCourseRequestsShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    public void getAllCourseRequestsByRegisterationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where registerationDate equals to DEFAULT_REGISTERATION_DATE
        defaultCourseRequestsShouldBeFound("registerationDate.equals=" + DEFAULT_REGISTERATION_DATE);

        // Get all the courseRequestsList where registerationDate equals to UPDATED_REGISTERATION_DATE
        defaultCourseRequestsShouldNotBeFound("registerationDate.equals=" + UPDATED_REGISTERATION_DATE);
    }

    @Test
    @Transactional
    public void getAllCourseRequestsByRegisterationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where registerationDate not equals to DEFAULT_REGISTERATION_DATE
        defaultCourseRequestsShouldNotBeFound("registerationDate.notEquals=" + DEFAULT_REGISTERATION_DATE);

        // Get all the courseRequestsList where registerationDate not equals to UPDATED_REGISTERATION_DATE
        defaultCourseRequestsShouldBeFound("registerationDate.notEquals=" + UPDATED_REGISTERATION_DATE);
    }

    @Test
    @Transactional
    public void getAllCourseRequestsByRegisterationDateIsInShouldWork() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where registerationDate in DEFAULT_REGISTERATION_DATE or UPDATED_REGISTERATION_DATE
        defaultCourseRequestsShouldBeFound("registerationDate.in=" + DEFAULT_REGISTERATION_DATE + "," + UPDATED_REGISTERATION_DATE);

        // Get all the courseRequestsList where registerationDate equals to UPDATED_REGISTERATION_DATE
        defaultCourseRequestsShouldNotBeFound("registerationDate.in=" + UPDATED_REGISTERATION_DATE);
    }

    @Test
    @Transactional
    public void getAllCourseRequestsByRegisterationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where registerationDate is not null
        defaultCourseRequestsShouldBeFound("registerationDate.specified=true");

        // Get all the courseRequestsList where registerationDate is null
        defaultCourseRequestsShouldNotBeFound("registerationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCourseRequestsByRegisterationDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where registerationDate is greater than or equal to DEFAULT_REGISTERATION_DATE
        defaultCourseRequestsShouldBeFound("registerationDate.greaterThanOrEqual=" + DEFAULT_REGISTERATION_DATE);

        // Get all the courseRequestsList where registerationDate is greater than or equal to UPDATED_REGISTERATION_DATE
        defaultCourseRequestsShouldNotBeFound("registerationDate.greaterThanOrEqual=" + UPDATED_REGISTERATION_DATE);
    }

    @Test
    @Transactional
    public void getAllCourseRequestsByRegisterationDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where registerationDate is less than or equal to DEFAULT_REGISTERATION_DATE
        defaultCourseRequestsShouldBeFound("registerationDate.lessThanOrEqual=" + DEFAULT_REGISTERATION_DATE);

        // Get all the courseRequestsList where registerationDate is less than or equal to SMALLER_REGISTERATION_DATE
        defaultCourseRequestsShouldNotBeFound("registerationDate.lessThanOrEqual=" + SMALLER_REGISTERATION_DATE);
    }

    @Test
    @Transactional
    public void getAllCourseRequestsByRegisterationDateIsLessThanSomething() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where registerationDate is less than DEFAULT_REGISTERATION_DATE
        defaultCourseRequestsShouldNotBeFound("registerationDate.lessThan=" + DEFAULT_REGISTERATION_DATE);

        // Get all the courseRequestsList where registerationDate is less than UPDATED_REGISTERATION_DATE
        defaultCourseRequestsShouldBeFound("registerationDate.lessThan=" + UPDATED_REGISTERATION_DATE);
    }

    @Test
    @Transactional
    public void getAllCourseRequestsByRegisterationDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where registerationDate is greater than DEFAULT_REGISTERATION_DATE
        defaultCourseRequestsShouldNotBeFound("registerationDate.greaterThan=" + DEFAULT_REGISTERATION_DATE);

        // Get all the courseRequestsList where registerationDate is greater than SMALLER_REGISTERATION_DATE
        defaultCourseRequestsShouldBeFound("registerationDate.greaterThan=" + SMALLER_REGISTERATION_DATE);
    }


    @Test
    @Transactional
    public void getAllCourseRequestsByTelephoneIsEqualToSomething() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where telephone equals to DEFAULT_TELEPHONE
        defaultCourseRequestsShouldBeFound("telephone.equals=" + DEFAULT_TELEPHONE);

        // Get all the courseRequestsList where telephone equals to UPDATED_TELEPHONE
        defaultCourseRequestsShouldNotBeFound("telephone.equals=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllCourseRequestsByTelephoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where telephone not equals to DEFAULT_TELEPHONE
        defaultCourseRequestsShouldNotBeFound("telephone.notEquals=" + DEFAULT_TELEPHONE);

        // Get all the courseRequestsList where telephone not equals to UPDATED_TELEPHONE
        defaultCourseRequestsShouldBeFound("telephone.notEquals=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllCourseRequestsByTelephoneIsInShouldWork() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where telephone in DEFAULT_TELEPHONE or UPDATED_TELEPHONE
        defaultCourseRequestsShouldBeFound("telephone.in=" + DEFAULT_TELEPHONE + "," + UPDATED_TELEPHONE);

        // Get all the courseRequestsList where telephone equals to UPDATED_TELEPHONE
        defaultCourseRequestsShouldNotBeFound("telephone.in=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllCourseRequestsByTelephoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where telephone is not null
        defaultCourseRequestsShouldBeFound("telephone.specified=true");

        // Get all the courseRequestsList where telephone is null
        defaultCourseRequestsShouldNotBeFound("telephone.specified=false");
    }
                @Test
    @Transactional
    public void getAllCourseRequestsByTelephoneContainsSomething() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where telephone contains DEFAULT_TELEPHONE
        defaultCourseRequestsShouldBeFound("telephone.contains=" + DEFAULT_TELEPHONE);

        // Get all the courseRequestsList where telephone contains UPDATED_TELEPHONE
        defaultCourseRequestsShouldNotBeFound("telephone.contains=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllCourseRequestsByTelephoneNotContainsSomething() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where telephone does not contain DEFAULT_TELEPHONE
        defaultCourseRequestsShouldNotBeFound("telephone.doesNotContain=" + DEFAULT_TELEPHONE);

        // Get all the courseRequestsList where telephone does not contain UPDATED_TELEPHONE
        defaultCourseRequestsShouldBeFound("telephone.doesNotContain=" + UPDATED_TELEPHONE);
    }


    @Test
    @Transactional
    public void getAllCourseRequestsByMobileIsEqualToSomething() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where mobile equals to DEFAULT_MOBILE
        defaultCourseRequestsShouldBeFound("mobile.equals=" + DEFAULT_MOBILE);

        // Get all the courseRequestsList where mobile equals to UPDATED_MOBILE
        defaultCourseRequestsShouldNotBeFound("mobile.equals=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllCourseRequestsByMobileIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where mobile not equals to DEFAULT_MOBILE
        defaultCourseRequestsShouldNotBeFound("mobile.notEquals=" + DEFAULT_MOBILE);

        // Get all the courseRequestsList where mobile not equals to UPDATED_MOBILE
        defaultCourseRequestsShouldBeFound("mobile.notEquals=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllCourseRequestsByMobileIsInShouldWork() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where mobile in DEFAULT_MOBILE or UPDATED_MOBILE
        defaultCourseRequestsShouldBeFound("mobile.in=" + DEFAULT_MOBILE + "," + UPDATED_MOBILE);

        // Get all the courseRequestsList where mobile equals to UPDATED_MOBILE
        defaultCourseRequestsShouldNotBeFound("mobile.in=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllCourseRequestsByMobileIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where mobile is not null
        defaultCourseRequestsShouldBeFound("mobile.specified=true");

        // Get all the courseRequestsList where mobile is null
        defaultCourseRequestsShouldNotBeFound("mobile.specified=false");
    }
                @Test
    @Transactional
    public void getAllCourseRequestsByMobileContainsSomething() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where mobile contains DEFAULT_MOBILE
        defaultCourseRequestsShouldBeFound("mobile.contains=" + DEFAULT_MOBILE);

        // Get all the courseRequestsList where mobile contains UPDATED_MOBILE
        defaultCourseRequestsShouldNotBeFound("mobile.contains=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllCourseRequestsByMobileNotContainsSomething() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        // Get all the courseRequestsList where mobile does not contain DEFAULT_MOBILE
        defaultCourseRequestsShouldNotBeFound("mobile.doesNotContain=" + DEFAULT_MOBILE);

        // Get all the courseRequestsList where mobile does not contain UPDATED_MOBILE
        defaultCourseRequestsShouldBeFound("mobile.doesNotContain=" + UPDATED_MOBILE);
    }


    @Test
    @Transactional
    public void getAllCourseRequestsByCoursesIsEqualToSomething() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);
        Courses courses = CoursesResourceIT.createEntity(em);
        em.persist(courses);
        em.flush();
        courseRequests.setCourses(courses);
        courseRequestsRepository.saveAndFlush(courseRequests);
        Long coursesId = courses.getId();

        // Get all the courseRequestsList where courses equals to coursesId
        defaultCourseRequestsShouldBeFound("coursesId.equals=" + coursesId);

        // Get all the courseRequestsList where courses equals to coursesId + 1
        defaultCourseRequestsShouldNotBeFound("coursesId.equals=" + (coursesId + 1));
    }


    @Test
    @Transactional
    public void getAllCourseRequestsByCoursesIsEqualToSomething() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);
        CourseGroup courses = CourseGroupResourceIT.createEntity(em);
        em.persist(courses);
        em.flush();
        courseRequests.setCourses(courses);
        courseRequestsRepository.saveAndFlush(courseRequests);
        Long coursesId = courses.getId();

        // Get all the courseRequestsList where courses equals to coursesId
        defaultCourseRequestsShouldBeFound("coursesId.equals=" + coursesId);

        // Get all the courseRequestsList where courses equals to coursesId + 1
        defaultCourseRequestsShouldNotBeFound("coursesId.equals=" + (coursesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCourseRequestsShouldBeFound(String filter) throws Exception {
        restCourseRequestsMockMvc.perform(get("/api/course-requests?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseRequests.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].registerationDate").value(hasItem(DEFAULT_REGISTERATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)));

        // Check, that the count call also returns 1
        restCourseRequestsMockMvc.perform(get("/api/course-requests/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCourseRequestsShouldNotBeFound(String filter) throws Exception {
        restCourseRequestsMockMvc.perform(get("/api/course-requests?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCourseRequestsMockMvc.perform(get("/api/course-requests/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCourseRequests() throws Exception {
        // Get the courseRequests
        restCourseRequestsMockMvc.perform(get("/api/course-requests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCourseRequests() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        int databaseSizeBeforeUpdate = courseRequestsRepository.findAll().size();

        // Update the courseRequests
        CourseRequests updatedCourseRequests = courseRequestsRepository.findById(courseRequests.getId()).get();
        // Disconnect from session so that the updates on updatedCourseRequests are not directly saved in db
        em.detach(updatedCourseRequests);
        updatedCourseRequests
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .email(UPDATED_EMAIL)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .gender(UPDATED_GENDER)
            .registerationDate(UPDATED_REGISTERATION_DATE)
            .telephone(UPDATED_TELEPHONE)
            .mobile(UPDATED_MOBILE);
        CourseRequestsDTO courseRequestsDTO = courseRequestsMapper.toDto(updatedCourseRequests);

        restCourseRequestsMockMvc.perform(put("/api/course-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(courseRequestsDTO)))
            .andExpect(status().isOk());

        // Validate the CourseRequests in the database
        List<CourseRequests> courseRequestsList = courseRequestsRepository.findAll();
        assertThat(courseRequestsList).hasSize(databaseSizeBeforeUpdate);
        CourseRequests testCourseRequests = courseRequestsList.get(courseRequestsList.size() - 1);
        assertThat(testCourseRequests.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testCourseRequests.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCourseRequests.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testCourseRequests.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCourseRequests.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testCourseRequests.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testCourseRequests.getRegisterationDate()).isEqualTo(UPDATED_REGISTERATION_DATE);
        assertThat(testCourseRequests.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testCourseRequests.getMobile()).isEqualTo(UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void updateNonExistingCourseRequests() throws Exception {
        int databaseSizeBeforeUpdate = courseRequestsRepository.findAll().size();

        // Create the CourseRequests
        CourseRequestsDTO courseRequestsDTO = courseRequestsMapper.toDto(courseRequests);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseRequestsMockMvc.perform(put("/api/course-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(courseRequestsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CourseRequests in the database
        List<CourseRequests> courseRequestsList = courseRequestsRepository.findAll();
        assertThat(courseRequestsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCourseRequests() throws Exception {
        // Initialize the database
        courseRequestsRepository.saveAndFlush(courseRequests);

        int databaseSizeBeforeDelete = courseRequestsRepository.findAll().size();

        // Delete the courseRequests
        restCourseRequestsMockMvc.perform(delete("/api/course-requests/{id}", courseRequests.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CourseRequests> courseRequestsList = courseRequestsRepository.findAll();
        assertThat(courseRequestsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
