package uz.kitc.web.rest;

import uz.kitc.KitcuzApp;
import uz.kitc.domain.AbilityStudent;
import uz.kitc.repository.AbilityStudentRepository;
import uz.kitc.service.AbilityStudentService;
import uz.kitc.service.dto.AbilityStudentDTO;
import uz.kitc.service.mapper.AbilityStudentMapper;
import uz.kitc.service.dto.AbilityStudentCriteria;
import uz.kitc.service.AbilityStudentQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AbilityStudentResource} REST controller.
 */
@SpringBootTest(classes = KitcuzApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AbilityStudentResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ABOUT = "AAAAAAAAAA";
    private static final String UPDATED_ABOUT = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_OF_BIRTH = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_REGISTERATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REGISTERATION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_REGISTERATION_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE = "BBBBBBBBBB";

    private static final String DEFAULT_THUMBNAIL_PHOTO_URL = "AAAAAAAAAA";
    private static final String UPDATED_THUMBNAIL_PHOTO_URL = "BBBBBBBBBB";

    private static final String DEFAULT_FULL_PHOTO_URL = "AAAAAAAAAA";
    private static final String UPDATED_FULL_PHOTO_URL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_SHOWING = false;
    private static final Boolean UPDATED_IS_SHOWING = true;

    @Autowired
    private AbilityStudentRepository abilityStudentRepository;

    @Autowired
    private AbilityStudentMapper abilityStudentMapper;

    @Autowired
    private AbilityStudentService abilityStudentService;

    @Autowired
    private AbilityStudentQueryService abilityStudentQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAbilityStudentMockMvc;

    private AbilityStudent abilityStudent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AbilityStudent createEntity(EntityManager em) {
        AbilityStudent abilityStudent = new AbilityStudent()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .about(DEFAULT_ABOUT)
            .email(DEFAULT_EMAIL)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .registerationDate(DEFAULT_REGISTERATION_DATE)
            .telephone(DEFAULT_TELEPHONE)
            .mobile(DEFAULT_MOBILE)
            .thumbnailPhotoUrl(DEFAULT_THUMBNAIL_PHOTO_URL)
            .fullPhotoUrl(DEFAULT_FULL_PHOTO_URL)
            .isShowing(DEFAULT_IS_SHOWING);
        return abilityStudent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AbilityStudent createUpdatedEntity(EntityManager em) {
        AbilityStudent abilityStudent = new AbilityStudent()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .about(UPDATED_ABOUT)
            .email(UPDATED_EMAIL)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .registerationDate(UPDATED_REGISTERATION_DATE)
            .telephone(UPDATED_TELEPHONE)
            .mobile(UPDATED_MOBILE)
            .thumbnailPhotoUrl(UPDATED_THUMBNAIL_PHOTO_URL)
            .fullPhotoUrl(UPDATED_FULL_PHOTO_URL)
            .isShowing(UPDATED_IS_SHOWING);
        return abilityStudent;
    }

    @BeforeEach
    public void initTest() {
        abilityStudent = createEntity(em);
    }

    @Test
    @Transactional
    public void createAbilityStudent() throws Exception {
        int databaseSizeBeforeCreate = abilityStudentRepository.findAll().size();
        // Create the AbilityStudent
        AbilityStudentDTO abilityStudentDTO = abilityStudentMapper.toDto(abilityStudent);
        restAbilityStudentMockMvc.perform(post("/api/ability-students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(abilityStudentDTO)))
            .andExpect(status().isCreated());

        // Validate the AbilityStudent in the database
        List<AbilityStudent> abilityStudentList = abilityStudentRepository.findAll();
        assertThat(abilityStudentList).hasSize(databaseSizeBeforeCreate + 1);
        AbilityStudent testAbilityStudent = abilityStudentList.get(abilityStudentList.size() - 1);
        assertThat(testAbilityStudent.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testAbilityStudent.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testAbilityStudent.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testAbilityStudent.getAbout()).isEqualTo(DEFAULT_ABOUT);
        assertThat(testAbilityStudent.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testAbilityStudent.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testAbilityStudent.getRegisterationDate()).isEqualTo(DEFAULT_REGISTERATION_DATE);
        assertThat(testAbilityStudent.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testAbilityStudent.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testAbilityStudent.getThumbnailPhotoUrl()).isEqualTo(DEFAULT_THUMBNAIL_PHOTO_URL);
        assertThat(testAbilityStudent.getFullPhotoUrl()).isEqualTo(DEFAULT_FULL_PHOTO_URL);
        assertThat(testAbilityStudent.isIsShowing()).isEqualTo(DEFAULT_IS_SHOWING);
    }

    @Test
    @Transactional
    public void createAbilityStudentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = abilityStudentRepository.findAll().size();

        // Create the AbilityStudent with an existing ID
        abilityStudent.setId(1L);
        AbilityStudentDTO abilityStudentDTO = abilityStudentMapper.toDto(abilityStudent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAbilityStudentMockMvc.perform(post("/api/ability-students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(abilityStudentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AbilityStudent in the database
        List<AbilityStudent> abilityStudentList = abilityStudentRepository.findAll();
        assertThat(abilityStudentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = abilityStudentRepository.findAll().size();
        // set the field null
        abilityStudent.setFirstName(null);

        // Create the AbilityStudent, which fails.
        AbilityStudentDTO abilityStudentDTO = abilityStudentMapper.toDto(abilityStudent);


        restAbilityStudentMockMvc.perform(post("/api/ability-students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(abilityStudentDTO)))
            .andExpect(status().isBadRequest());

        List<AbilityStudent> abilityStudentList = abilityStudentRepository.findAll();
        assertThat(abilityStudentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = abilityStudentRepository.findAll().size();
        // set the field null
        abilityStudent.setLastName(null);

        // Create the AbilityStudent, which fails.
        AbilityStudentDTO abilityStudentDTO = abilityStudentMapper.toDto(abilityStudent);


        restAbilityStudentMockMvc.perform(post("/api/ability-students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(abilityStudentDTO)))
            .andExpect(status().isBadRequest());

        List<AbilityStudent> abilityStudentList = abilityStudentRepository.findAll();
        assertThat(abilityStudentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAbilityStudents() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList
        restAbilityStudentMockMvc.perform(get("/api/ability-students?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(abilityStudent.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].about").value(hasItem(DEFAULT_ABOUT.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].registerationDate").value(hasItem(DEFAULT_REGISTERATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].thumbnailPhotoUrl").value(hasItem(DEFAULT_THUMBNAIL_PHOTO_URL)))
            .andExpect(jsonPath("$.[*].fullPhotoUrl").value(hasItem(DEFAULT_FULL_PHOTO_URL)))
            .andExpect(jsonPath("$.[*].isShowing").value(hasItem(DEFAULT_IS_SHOWING.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getAbilityStudent() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get the abilityStudent
        restAbilityStudentMockMvc.perform(get("/api/ability-students/{id}", abilityStudent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(abilityStudent.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME))
            .andExpect(jsonPath("$.about").value(DEFAULT_ABOUT.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.registerationDate").value(DEFAULT_REGISTERATION_DATE.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE))
            .andExpect(jsonPath("$.thumbnailPhotoUrl").value(DEFAULT_THUMBNAIL_PHOTO_URL))
            .andExpect(jsonPath("$.fullPhotoUrl").value(DEFAULT_FULL_PHOTO_URL))
            .andExpect(jsonPath("$.isShowing").value(DEFAULT_IS_SHOWING.booleanValue()));
    }


    @Test
    @Transactional
    public void getAbilityStudentsByIdFiltering() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        Long id = abilityStudent.getId();

        defaultAbilityStudentShouldBeFound("id.equals=" + id);
        defaultAbilityStudentShouldNotBeFound("id.notEquals=" + id);

        defaultAbilityStudentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAbilityStudentShouldNotBeFound("id.greaterThan=" + id);

        defaultAbilityStudentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAbilityStudentShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAbilityStudentsByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where firstName equals to DEFAULT_FIRST_NAME
        defaultAbilityStudentShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the abilityStudentList where firstName equals to UPDATED_FIRST_NAME
        defaultAbilityStudentShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByFirstNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where firstName not equals to DEFAULT_FIRST_NAME
        defaultAbilityStudentShouldNotBeFound("firstName.notEquals=" + DEFAULT_FIRST_NAME);

        // Get all the abilityStudentList where firstName not equals to UPDATED_FIRST_NAME
        defaultAbilityStudentShouldBeFound("firstName.notEquals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultAbilityStudentShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the abilityStudentList where firstName equals to UPDATED_FIRST_NAME
        defaultAbilityStudentShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where firstName is not null
        defaultAbilityStudentShouldBeFound("firstName.specified=true");

        // Get all the abilityStudentList where firstName is null
        defaultAbilityStudentShouldNotBeFound("firstName.specified=false");
    }
                @Test
    @Transactional
    public void getAllAbilityStudentsByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where firstName contains DEFAULT_FIRST_NAME
        defaultAbilityStudentShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the abilityStudentList where firstName contains UPDATED_FIRST_NAME
        defaultAbilityStudentShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where firstName does not contain DEFAULT_FIRST_NAME
        defaultAbilityStudentShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the abilityStudentList where firstName does not contain UPDATED_FIRST_NAME
        defaultAbilityStudentShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }


    @Test
    @Transactional
    public void getAllAbilityStudentsByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where lastName equals to DEFAULT_LAST_NAME
        defaultAbilityStudentShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the abilityStudentList where lastName equals to UPDATED_LAST_NAME
        defaultAbilityStudentShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByLastNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where lastName not equals to DEFAULT_LAST_NAME
        defaultAbilityStudentShouldNotBeFound("lastName.notEquals=" + DEFAULT_LAST_NAME);

        // Get all the abilityStudentList where lastName not equals to UPDATED_LAST_NAME
        defaultAbilityStudentShouldBeFound("lastName.notEquals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultAbilityStudentShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the abilityStudentList where lastName equals to UPDATED_LAST_NAME
        defaultAbilityStudentShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where lastName is not null
        defaultAbilityStudentShouldBeFound("lastName.specified=true");

        // Get all the abilityStudentList where lastName is null
        defaultAbilityStudentShouldNotBeFound("lastName.specified=false");
    }
                @Test
    @Transactional
    public void getAllAbilityStudentsByLastNameContainsSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where lastName contains DEFAULT_LAST_NAME
        defaultAbilityStudentShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the abilityStudentList where lastName contains UPDATED_LAST_NAME
        defaultAbilityStudentShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where lastName does not contain DEFAULT_LAST_NAME
        defaultAbilityStudentShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the abilityStudentList where lastName does not contain UPDATED_LAST_NAME
        defaultAbilityStudentShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }


    @Test
    @Transactional
    public void getAllAbilityStudentsByMiddleNameIsEqualToSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where middleName equals to DEFAULT_MIDDLE_NAME
        defaultAbilityStudentShouldBeFound("middleName.equals=" + DEFAULT_MIDDLE_NAME);

        // Get all the abilityStudentList where middleName equals to UPDATED_MIDDLE_NAME
        defaultAbilityStudentShouldNotBeFound("middleName.equals=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByMiddleNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where middleName not equals to DEFAULT_MIDDLE_NAME
        defaultAbilityStudentShouldNotBeFound("middleName.notEquals=" + DEFAULT_MIDDLE_NAME);

        // Get all the abilityStudentList where middleName not equals to UPDATED_MIDDLE_NAME
        defaultAbilityStudentShouldBeFound("middleName.notEquals=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByMiddleNameIsInShouldWork() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where middleName in DEFAULT_MIDDLE_NAME or UPDATED_MIDDLE_NAME
        defaultAbilityStudentShouldBeFound("middleName.in=" + DEFAULT_MIDDLE_NAME + "," + UPDATED_MIDDLE_NAME);

        // Get all the abilityStudentList where middleName equals to UPDATED_MIDDLE_NAME
        defaultAbilityStudentShouldNotBeFound("middleName.in=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByMiddleNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where middleName is not null
        defaultAbilityStudentShouldBeFound("middleName.specified=true");

        // Get all the abilityStudentList where middleName is null
        defaultAbilityStudentShouldNotBeFound("middleName.specified=false");
    }
                @Test
    @Transactional
    public void getAllAbilityStudentsByMiddleNameContainsSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where middleName contains DEFAULT_MIDDLE_NAME
        defaultAbilityStudentShouldBeFound("middleName.contains=" + DEFAULT_MIDDLE_NAME);

        // Get all the abilityStudentList where middleName contains UPDATED_MIDDLE_NAME
        defaultAbilityStudentShouldNotBeFound("middleName.contains=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByMiddleNameNotContainsSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where middleName does not contain DEFAULT_MIDDLE_NAME
        defaultAbilityStudentShouldNotBeFound("middleName.doesNotContain=" + DEFAULT_MIDDLE_NAME);

        // Get all the abilityStudentList where middleName does not contain UPDATED_MIDDLE_NAME
        defaultAbilityStudentShouldBeFound("middleName.doesNotContain=" + UPDATED_MIDDLE_NAME);
    }


    @Test
    @Transactional
    public void getAllAbilityStudentsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where email equals to DEFAULT_EMAIL
        defaultAbilityStudentShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the abilityStudentList where email equals to UPDATED_EMAIL
        defaultAbilityStudentShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where email not equals to DEFAULT_EMAIL
        defaultAbilityStudentShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the abilityStudentList where email not equals to UPDATED_EMAIL
        defaultAbilityStudentShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultAbilityStudentShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the abilityStudentList where email equals to UPDATED_EMAIL
        defaultAbilityStudentShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where email is not null
        defaultAbilityStudentShouldBeFound("email.specified=true");

        // Get all the abilityStudentList where email is null
        defaultAbilityStudentShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllAbilityStudentsByEmailContainsSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where email contains DEFAULT_EMAIL
        defaultAbilityStudentShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the abilityStudentList where email contains UPDATED_EMAIL
        defaultAbilityStudentShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where email does not contain DEFAULT_EMAIL
        defaultAbilityStudentShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the abilityStudentList where email does not contain UPDATED_EMAIL
        defaultAbilityStudentShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllAbilityStudentsByDateOfBirthIsEqualToSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where dateOfBirth equals to DEFAULT_DATE_OF_BIRTH
        defaultAbilityStudentShouldBeFound("dateOfBirth.equals=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the abilityStudentList where dateOfBirth equals to UPDATED_DATE_OF_BIRTH
        defaultAbilityStudentShouldNotBeFound("dateOfBirth.equals=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByDateOfBirthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where dateOfBirth not equals to DEFAULT_DATE_OF_BIRTH
        defaultAbilityStudentShouldNotBeFound("dateOfBirth.notEquals=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the abilityStudentList where dateOfBirth not equals to UPDATED_DATE_OF_BIRTH
        defaultAbilityStudentShouldBeFound("dateOfBirth.notEquals=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByDateOfBirthIsInShouldWork() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where dateOfBirth in DEFAULT_DATE_OF_BIRTH or UPDATED_DATE_OF_BIRTH
        defaultAbilityStudentShouldBeFound("dateOfBirth.in=" + DEFAULT_DATE_OF_BIRTH + "," + UPDATED_DATE_OF_BIRTH);

        // Get all the abilityStudentList where dateOfBirth equals to UPDATED_DATE_OF_BIRTH
        defaultAbilityStudentShouldNotBeFound("dateOfBirth.in=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByDateOfBirthIsNullOrNotNull() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where dateOfBirth is not null
        defaultAbilityStudentShouldBeFound("dateOfBirth.specified=true");

        // Get all the abilityStudentList where dateOfBirth is null
        defaultAbilityStudentShouldNotBeFound("dateOfBirth.specified=false");
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByDateOfBirthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where dateOfBirth is greater than or equal to DEFAULT_DATE_OF_BIRTH
        defaultAbilityStudentShouldBeFound("dateOfBirth.greaterThanOrEqual=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the abilityStudentList where dateOfBirth is greater than or equal to UPDATED_DATE_OF_BIRTH
        defaultAbilityStudentShouldNotBeFound("dateOfBirth.greaterThanOrEqual=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByDateOfBirthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where dateOfBirth is less than or equal to DEFAULT_DATE_OF_BIRTH
        defaultAbilityStudentShouldBeFound("dateOfBirth.lessThanOrEqual=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the abilityStudentList where dateOfBirth is less than or equal to SMALLER_DATE_OF_BIRTH
        defaultAbilityStudentShouldNotBeFound("dateOfBirth.lessThanOrEqual=" + SMALLER_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByDateOfBirthIsLessThanSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where dateOfBirth is less than DEFAULT_DATE_OF_BIRTH
        defaultAbilityStudentShouldNotBeFound("dateOfBirth.lessThan=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the abilityStudentList where dateOfBirth is less than UPDATED_DATE_OF_BIRTH
        defaultAbilityStudentShouldBeFound("dateOfBirth.lessThan=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByDateOfBirthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where dateOfBirth is greater than DEFAULT_DATE_OF_BIRTH
        defaultAbilityStudentShouldNotBeFound("dateOfBirth.greaterThan=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the abilityStudentList where dateOfBirth is greater than SMALLER_DATE_OF_BIRTH
        defaultAbilityStudentShouldBeFound("dateOfBirth.greaterThan=" + SMALLER_DATE_OF_BIRTH);
    }


    @Test
    @Transactional
    public void getAllAbilityStudentsByRegisterationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where registerationDate equals to DEFAULT_REGISTERATION_DATE
        defaultAbilityStudentShouldBeFound("registerationDate.equals=" + DEFAULT_REGISTERATION_DATE);

        // Get all the abilityStudentList where registerationDate equals to UPDATED_REGISTERATION_DATE
        defaultAbilityStudentShouldNotBeFound("registerationDate.equals=" + UPDATED_REGISTERATION_DATE);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByRegisterationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where registerationDate not equals to DEFAULT_REGISTERATION_DATE
        defaultAbilityStudentShouldNotBeFound("registerationDate.notEquals=" + DEFAULT_REGISTERATION_DATE);

        // Get all the abilityStudentList where registerationDate not equals to UPDATED_REGISTERATION_DATE
        defaultAbilityStudentShouldBeFound("registerationDate.notEquals=" + UPDATED_REGISTERATION_DATE);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByRegisterationDateIsInShouldWork() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where registerationDate in DEFAULT_REGISTERATION_DATE or UPDATED_REGISTERATION_DATE
        defaultAbilityStudentShouldBeFound("registerationDate.in=" + DEFAULT_REGISTERATION_DATE + "," + UPDATED_REGISTERATION_DATE);

        // Get all the abilityStudentList where registerationDate equals to UPDATED_REGISTERATION_DATE
        defaultAbilityStudentShouldNotBeFound("registerationDate.in=" + UPDATED_REGISTERATION_DATE);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByRegisterationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where registerationDate is not null
        defaultAbilityStudentShouldBeFound("registerationDate.specified=true");

        // Get all the abilityStudentList where registerationDate is null
        defaultAbilityStudentShouldNotBeFound("registerationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByRegisterationDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where registerationDate is greater than or equal to DEFAULT_REGISTERATION_DATE
        defaultAbilityStudentShouldBeFound("registerationDate.greaterThanOrEqual=" + DEFAULT_REGISTERATION_DATE);

        // Get all the abilityStudentList where registerationDate is greater than or equal to UPDATED_REGISTERATION_DATE
        defaultAbilityStudentShouldNotBeFound("registerationDate.greaterThanOrEqual=" + UPDATED_REGISTERATION_DATE);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByRegisterationDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where registerationDate is less than or equal to DEFAULT_REGISTERATION_DATE
        defaultAbilityStudentShouldBeFound("registerationDate.lessThanOrEqual=" + DEFAULT_REGISTERATION_DATE);

        // Get all the abilityStudentList where registerationDate is less than or equal to SMALLER_REGISTERATION_DATE
        defaultAbilityStudentShouldNotBeFound("registerationDate.lessThanOrEqual=" + SMALLER_REGISTERATION_DATE);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByRegisterationDateIsLessThanSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where registerationDate is less than DEFAULT_REGISTERATION_DATE
        defaultAbilityStudentShouldNotBeFound("registerationDate.lessThan=" + DEFAULT_REGISTERATION_DATE);

        // Get all the abilityStudentList where registerationDate is less than UPDATED_REGISTERATION_DATE
        defaultAbilityStudentShouldBeFound("registerationDate.lessThan=" + UPDATED_REGISTERATION_DATE);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByRegisterationDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where registerationDate is greater than DEFAULT_REGISTERATION_DATE
        defaultAbilityStudentShouldNotBeFound("registerationDate.greaterThan=" + DEFAULT_REGISTERATION_DATE);

        // Get all the abilityStudentList where registerationDate is greater than SMALLER_REGISTERATION_DATE
        defaultAbilityStudentShouldBeFound("registerationDate.greaterThan=" + SMALLER_REGISTERATION_DATE);
    }


    @Test
    @Transactional
    public void getAllAbilityStudentsByTelephoneIsEqualToSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where telephone equals to DEFAULT_TELEPHONE
        defaultAbilityStudentShouldBeFound("telephone.equals=" + DEFAULT_TELEPHONE);

        // Get all the abilityStudentList where telephone equals to UPDATED_TELEPHONE
        defaultAbilityStudentShouldNotBeFound("telephone.equals=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByTelephoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where telephone not equals to DEFAULT_TELEPHONE
        defaultAbilityStudentShouldNotBeFound("telephone.notEquals=" + DEFAULT_TELEPHONE);

        // Get all the abilityStudentList where telephone not equals to UPDATED_TELEPHONE
        defaultAbilityStudentShouldBeFound("telephone.notEquals=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByTelephoneIsInShouldWork() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where telephone in DEFAULT_TELEPHONE or UPDATED_TELEPHONE
        defaultAbilityStudentShouldBeFound("telephone.in=" + DEFAULT_TELEPHONE + "," + UPDATED_TELEPHONE);

        // Get all the abilityStudentList where telephone equals to UPDATED_TELEPHONE
        defaultAbilityStudentShouldNotBeFound("telephone.in=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByTelephoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where telephone is not null
        defaultAbilityStudentShouldBeFound("telephone.specified=true");

        // Get all the abilityStudentList where telephone is null
        defaultAbilityStudentShouldNotBeFound("telephone.specified=false");
    }
                @Test
    @Transactional
    public void getAllAbilityStudentsByTelephoneContainsSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where telephone contains DEFAULT_TELEPHONE
        defaultAbilityStudentShouldBeFound("telephone.contains=" + DEFAULT_TELEPHONE);

        // Get all the abilityStudentList where telephone contains UPDATED_TELEPHONE
        defaultAbilityStudentShouldNotBeFound("telephone.contains=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByTelephoneNotContainsSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where telephone does not contain DEFAULT_TELEPHONE
        defaultAbilityStudentShouldNotBeFound("telephone.doesNotContain=" + DEFAULT_TELEPHONE);

        // Get all the abilityStudentList where telephone does not contain UPDATED_TELEPHONE
        defaultAbilityStudentShouldBeFound("telephone.doesNotContain=" + UPDATED_TELEPHONE);
    }


    @Test
    @Transactional
    public void getAllAbilityStudentsByMobileIsEqualToSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where mobile equals to DEFAULT_MOBILE
        defaultAbilityStudentShouldBeFound("mobile.equals=" + DEFAULT_MOBILE);

        // Get all the abilityStudentList where mobile equals to UPDATED_MOBILE
        defaultAbilityStudentShouldNotBeFound("mobile.equals=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByMobileIsNotEqualToSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where mobile not equals to DEFAULT_MOBILE
        defaultAbilityStudentShouldNotBeFound("mobile.notEquals=" + DEFAULT_MOBILE);

        // Get all the abilityStudentList where mobile not equals to UPDATED_MOBILE
        defaultAbilityStudentShouldBeFound("mobile.notEquals=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByMobileIsInShouldWork() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where mobile in DEFAULT_MOBILE or UPDATED_MOBILE
        defaultAbilityStudentShouldBeFound("mobile.in=" + DEFAULT_MOBILE + "," + UPDATED_MOBILE);

        // Get all the abilityStudentList where mobile equals to UPDATED_MOBILE
        defaultAbilityStudentShouldNotBeFound("mobile.in=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByMobileIsNullOrNotNull() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where mobile is not null
        defaultAbilityStudentShouldBeFound("mobile.specified=true");

        // Get all the abilityStudentList where mobile is null
        defaultAbilityStudentShouldNotBeFound("mobile.specified=false");
    }
                @Test
    @Transactional
    public void getAllAbilityStudentsByMobileContainsSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where mobile contains DEFAULT_MOBILE
        defaultAbilityStudentShouldBeFound("mobile.contains=" + DEFAULT_MOBILE);

        // Get all the abilityStudentList where mobile contains UPDATED_MOBILE
        defaultAbilityStudentShouldNotBeFound("mobile.contains=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByMobileNotContainsSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where mobile does not contain DEFAULT_MOBILE
        defaultAbilityStudentShouldNotBeFound("mobile.doesNotContain=" + DEFAULT_MOBILE);

        // Get all the abilityStudentList where mobile does not contain UPDATED_MOBILE
        defaultAbilityStudentShouldBeFound("mobile.doesNotContain=" + UPDATED_MOBILE);
    }


    @Test
    @Transactional
    public void getAllAbilityStudentsByThumbnailPhotoUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where thumbnailPhotoUrl equals to DEFAULT_THUMBNAIL_PHOTO_URL
        defaultAbilityStudentShouldBeFound("thumbnailPhotoUrl.equals=" + DEFAULT_THUMBNAIL_PHOTO_URL);

        // Get all the abilityStudentList where thumbnailPhotoUrl equals to UPDATED_THUMBNAIL_PHOTO_URL
        defaultAbilityStudentShouldNotBeFound("thumbnailPhotoUrl.equals=" + UPDATED_THUMBNAIL_PHOTO_URL);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByThumbnailPhotoUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where thumbnailPhotoUrl not equals to DEFAULT_THUMBNAIL_PHOTO_URL
        defaultAbilityStudentShouldNotBeFound("thumbnailPhotoUrl.notEquals=" + DEFAULT_THUMBNAIL_PHOTO_URL);

        // Get all the abilityStudentList where thumbnailPhotoUrl not equals to UPDATED_THUMBNAIL_PHOTO_URL
        defaultAbilityStudentShouldBeFound("thumbnailPhotoUrl.notEquals=" + UPDATED_THUMBNAIL_PHOTO_URL);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByThumbnailPhotoUrlIsInShouldWork() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where thumbnailPhotoUrl in DEFAULT_THUMBNAIL_PHOTO_URL or UPDATED_THUMBNAIL_PHOTO_URL
        defaultAbilityStudentShouldBeFound("thumbnailPhotoUrl.in=" + DEFAULT_THUMBNAIL_PHOTO_URL + "," + UPDATED_THUMBNAIL_PHOTO_URL);

        // Get all the abilityStudentList where thumbnailPhotoUrl equals to UPDATED_THUMBNAIL_PHOTO_URL
        defaultAbilityStudentShouldNotBeFound("thumbnailPhotoUrl.in=" + UPDATED_THUMBNAIL_PHOTO_URL);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByThumbnailPhotoUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where thumbnailPhotoUrl is not null
        defaultAbilityStudentShouldBeFound("thumbnailPhotoUrl.specified=true");

        // Get all the abilityStudentList where thumbnailPhotoUrl is null
        defaultAbilityStudentShouldNotBeFound("thumbnailPhotoUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllAbilityStudentsByThumbnailPhotoUrlContainsSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where thumbnailPhotoUrl contains DEFAULT_THUMBNAIL_PHOTO_URL
        defaultAbilityStudentShouldBeFound("thumbnailPhotoUrl.contains=" + DEFAULT_THUMBNAIL_PHOTO_URL);

        // Get all the abilityStudentList where thumbnailPhotoUrl contains UPDATED_THUMBNAIL_PHOTO_URL
        defaultAbilityStudentShouldNotBeFound("thumbnailPhotoUrl.contains=" + UPDATED_THUMBNAIL_PHOTO_URL);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByThumbnailPhotoUrlNotContainsSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where thumbnailPhotoUrl does not contain DEFAULT_THUMBNAIL_PHOTO_URL
        defaultAbilityStudentShouldNotBeFound("thumbnailPhotoUrl.doesNotContain=" + DEFAULT_THUMBNAIL_PHOTO_URL);

        // Get all the abilityStudentList where thumbnailPhotoUrl does not contain UPDATED_THUMBNAIL_PHOTO_URL
        defaultAbilityStudentShouldBeFound("thumbnailPhotoUrl.doesNotContain=" + UPDATED_THUMBNAIL_PHOTO_URL);
    }


    @Test
    @Transactional
    public void getAllAbilityStudentsByFullPhotoUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where fullPhotoUrl equals to DEFAULT_FULL_PHOTO_URL
        defaultAbilityStudentShouldBeFound("fullPhotoUrl.equals=" + DEFAULT_FULL_PHOTO_URL);

        // Get all the abilityStudentList where fullPhotoUrl equals to UPDATED_FULL_PHOTO_URL
        defaultAbilityStudentShouldNotBeFound("fullPhotoUrl.equals=" + UPDATED_FULL_PHOTO_URL);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByFullPhotoUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where fullPhotoUrl not equals to DEFAULT_FULL_PHOTO_URL
        defaultAbilityStudentShouldNotBeFound("fullPhotoUrl.notEquals=" + DEFAULT_FULL_PHOTO_URL);

        // Get all the abilityStudentList where fullPhotoUrl not equals to UPDATED_FULL_PHOTO_URL
        defaultAbilityStudentShouldBeFound("fullPhotoUrl.notEquals=" + UPDATED_FULL_PHOTO_URL);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByFullPhotoUrlIsInShouldWork() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where fullPhotoUrl in DEFAULT_FULL_PHOTO_URL or UPDATED_FULL_PHOTO_URL
        defaultAbilityStudentShouldBeFound("fullPhotoUrl.in=" + DEFAULT_FULL_PHOTO_URL + "," + UPDATED_FULL_PHOTO_URL);

        // Get all the abilityStudentList where fullPhotoUrl equals to UPDATED_FULL_PHOTO_URL
        defaultAbilityStudentShouldNotBeFound("fullPhotoUrl.in=" + UPDATED_FULL_PHOTO_URL);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByFullPhotoUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where fullPhotoUrl is not null
        defaultAbilityStudentShouldBeFound("fullPhotoUrl.specified=true");

        // Get all the abilityStudentList where fullPhotoUrl is null
        defaultAbilityStudentShouldNotBeFound("fullPhotoUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllAbilityStudentsByFullPhotoUrlContainsSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where fullPhotoUrl contains DEFAULT_FULL_PHOTO_URL
        defaultAbilityStudentShouldBeFound("fullPhotoUrl.contains=" + DEFAULT_FULL_PHOTO_URL);

        // Get all the abilityStudentList where fullPhotoUrl contains UPDATED_FULL_PHOTO_URL
        defaultAbilityStudentShouldNotBeFound("fullPhotoUrl.contains=" + UPDATED_FULL_PHOTO_URL);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByFullPhotoUrlNotContainsSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where fullPhotoUrl does not contain DEFAULT_FULL_PHOTO_URL
        defaultAbilityStudentShouldNotBeFound("fullPhotoUrl.doesNotContain=" + DEFAULT_FULL_PHOTO_URL);

        // Get all the abilityStudentList where fullPhotoUrl does not contain UPDATED_FULL_PHOTO_URL
        defaultAbilityStudentShouldBeFound("fullPhotoUrl.doesNotContain=" + UPDATED_FULL_PHOTO_URL);
    }


    @Test
    @Transactional
    public void getAllAbilityStudentsByIsShowingIsEqualToSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where isShowing equals to DEFAULT_IS_SHOWING
        defaultAbilityStudentShouldBeFound("isShowing.equals=" + DEFAULT_IS_SHOWING);

        // Get all the abilityStudentList where isShowing equals to UPDATED_IS_SHOWING
        defaultAbilityStudentShouldNotBeFound("isShowing.equals=" + UPDATED_IS_SHOWING);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByIsShowingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where isShowing not equals to DEFAULT_IS_SHOWING
        defaultAbilityStudentShouldNotBeFound("isShowing.notEquals=" + DEFAULT_IS_SHOWING);

        // Get all the abilityStudentList where isShowing not equals to UPDATED_IS_SHOWING
        defaultAbilityStudentShouldBeFound("isShowing.notEquals=" + UPDATED_IS_SHOWING);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByIsShowingIsInShouldWork() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where isShowing in DEFAULT_IS_SHOWING or UPDATED_IS_SHOWING
        defaultAbilityStudentShouldBeFound("isShowing.in=" + DEFAULT_IS_SHOWING + "," + UPDATED_IS_SHOWING);

        // Get all the abilityStudentList where isShowing equals to UPDATED_IS_SHOWING
        defaultAbilityStudentShouldNotBeFound("isShowing.in=" + UPDATED_IS_SHOWING);
    }

    @Test
    @Transactional
    public void getAllAbilityStudentsByIsShowingIsNullOrNotNull() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        // Get all the abilityStudentList where isShowing is not null
        defaultAbilityStudentShouldBeFound("isShowing.specified=true");

        // Get all the abilityStudentList where isShowing is null
        defaultAbilityStudentShouldNotBeFound("isShowing.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAbilityStudentShouldBeFound(String filter) throws Exception {
        restAbilityStudentMockMvc.perform(get("/api/ability-students?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(abilityStudent.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].about").value(hasItem(DEFAULT_ABOUT.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].registerationDate").value(hasItem(DEFAULT_REGISTERATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].thumbnailPhotoUrl").value(hasItem(DEFAULT_THUMBNAIL_PHOTO_URL)))
            .andExpect(jsonPath("$.[*].fullPhotoUrl").value(hasItem(DEFAULT_FULL_PHOTO_URL)))
            .andExpect(jsonPath("$.[*].isShowing").value(hasItem(DEFAULT_IS_SHOWING.booleanValue())));

        // Check, that the count call also returns 1
        restAbilityStudentMockMvc.perform(get("/api/ability-students/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAbilityStudentShouldNotBeFound(String filter) throws Exception {
        restAbilityStudentMockMvc.perform(get("/api/ability-students?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAbilityStudentMockMvc.perform(get("/api/ability-students/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAbilityStudent() throws Exception {
        // Get the abilityStudent
        restAbilityStudentMockMvc.perform(get("/api/ability-students/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAbilityStudent() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        int databaseSizeBeforeUpdate = abilityStudentRepository.findAll().size();

        // Update the abilityStudent
        AbilityStudent updatedAbilityStudent = abilityStudentRepository.findById(abilityStudent.getId()).get();
        // Disconnect from session so that the updates on updatedAbilityStudent are not directly saved in db
        em.detach(updatedAbilityStudent);
        updatedAbilityStudent
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .about(UPDATED_ABOUT)
            .email(UPDATED_EMAIL)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .registerationDate(UPDATED_REGISTERATION_DATE)
            .telephone(UPDATED_TELEPHONE)
            .mobile(UPDATED_MOBILE)
            .thumbnailPhotoUrl(UPDATED_THUMBNAIL_PHOTO_URL)
            .fullPhotoUrl(UPDATED_FULL_PHOTO_URL)
            .isShowing(UPDATED_IS_SHOWING);
        AbilityStudentDTO abilityStudentDTO = abilityStudentMapper.toDto(updatedAbilityStudent);

        restAbilityStudentMockMvc.perform(put("/api/ability-students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(abilityStudentDTO)))
            .andExpect(status().isOk());

        // Validate the AbilityStudent in the database
        List<AbilityStudent> abilityStudentList = abilityStudentRepository.findAll();
        assertThat(abilityStudentList).hasSize(databaseSizeBeforeUpdate);
        AbilityStudent testAbilityStudent = abilityStudentList.get(abilityStudentList.size() - 1);
        assertThat(testAbilityStudent.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testAbilityStudent.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testAbilityStudent.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testAbilityStudent.getAbout()).isEqualTo(UPDATED_ABOUT);
        assertThat(testAbilityStudent.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAbilityStudent.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testAbilityStudent.getRegisterationDate()).isEqualTo(UPDATED_REGISTERATION_DATE);
        assertThat(testAbilityStudent.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testAbilityStudent.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testAbilityStudent.getThumbnailPhotoUrl()).isEqualTo(UPDATED_THUMBNAIL_PHOTO_URL);
        assertThat(testAbilityStudent.getFullPhotoUrl()).isEqualTo(UPDATED_FULL_PHOTO_URL);
        assertThat(testAbilityStudent.isIsShowing()).isEqualTo(UPDATED_IS_SHOWING);
    }

    @Test
    @Transactional
    public void updateNonExistingAbilityStudent() throws Exception {
        int databaseSizeBeforeUpdate = abilityStudentRepository.findAll().size();

        // Create the AbilityStudent
        AbilityStudentDTO abilityStudentDTO = abilityStudentMapper.toDto(abilityStudent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAbilityStudentMockMvc.perform(put("/api/ability-students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(abilityStudentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AbilityStudent in the database
        List<AbilityStudent> abilityStudentList = abilityStudentRepository.findAll();
        assertThat(abilityStudentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAbilityStudent() throws Exception {
        // Initialize the database
        abilityStudentRepository.saveAndFlush(abilityStudent);

        int databaseSizeBeforeDelete = abilityStudentRepository.findAll().size();

        // Delete the abilityStudent
        restAbilityStudentMockMvc.perform(delete("/api/ability-students/{id}", abilityStudent.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AbilityStudent> abilityStudentList = abilityStudentRepository.findAll();
        assertThat(abilityStudentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
