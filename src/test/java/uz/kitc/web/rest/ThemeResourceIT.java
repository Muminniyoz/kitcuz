package uz.kitc.web.rest;

import uz.kitc.KitcuzApp;
import uz.kitc.domain.Theme;
import uz.kitc.domain.Planning;
import uz.kitc.repository.ThemeRepository;
import uz.kitc.service.ThemeService;
import uz.kitc.service.dto.ThemeDTO;
import uz.kitc.service.mapper.ThemeMapper;
import uz.kitc.service.dto.ThemeCriteria;
import uz.kitc.service.ThemeQueryService;

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
 * Integration tests for the {@link ThemeResource} REST controller.
 */
@SpringBootTest(classes = KitcuzApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ThemeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;
    private static final Integer SMALLER_NUMBER = 1 - 1;

    private static final Boolean DEFAULT_IS_SECTION = false;
    private static final Boolean UPDATED_IS_SECTION = true;

    private static final String DEFAULT_ABOUT = "AAAAAAAAAA";
    private static final String UPDATED_ABOUT = "BBBBBBBBBB";

    private static final String DEFAULT_HOME_WORK_ABOUTS = "AAAAAAAAAA";
    private static final String UPDATED_HOME_WORK_ABOUTS = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_URL = "AAAAAAAAAA";
    private static final String UPDATED_FILE_URL = "BBBBBBBBBB";

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private ThemeMapper themeMapper;

    @Autowired
    private ThemeService themeService;

    @Autowired
    private ThemeQueryService themeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restThemeMockMvc;

    private Theme theme;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Theme createEntity(EntityManager em) {
        Theme theme = new Theme()
            .name(DEFAULT_NAME)
            .number(DEFAULT_NUMBER)
            .isSection(DEFAULT_IS_SECTION)
            .about(DEFAULT_ABOUT)
            .homeWorkAbouts(DEFAULT_HOME_WORK_ABOUTS)
            .fileUrl(DEFAULT_FILE_URL);
        return theme;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Theme createUpdatedEntity(EntityManager em) {
        Theme theme = new Theme()
            .name(UPDATED_NAME)
            .number(UPDATED_NUMBER)
            .isSection(UPDATED_IS_SECTION)
            .about(UPDATED_ABOUT)
            .homeWorkAbouts(UPDATED_HOME_WORK_ABOUTS)
            .fileUrl(UPDATED_FILE_URL);
        return theme;
    }

    @BeforeEach
    public void initTest() {
        theme = createEntity(em);
    }

    @Test
    @Transactional
    public void createTheme() throws Exception {
        int databaseSizeBeforeCreate = themeRepository.findAll().size();
        // Create the Theme
        ThemeDTO themeDTO = themeMapper.toDto(theme);
        restThemeMockMvc.perform(post("/api/themes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themeDTO)))
            .andExpect(status().isCreated());

        // Validate the Theme in the database
        List<Theme> themeList = themeRepository.findAll();
        assertThat(themeList).hasSize(databaseSizeBeforeCreate + 1);
        Theme testTheme = themeList.get(themeList.size() - 1);
        assertThat(testTheme.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTheme.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testTheme.isIsSection()).isEqualTo(DEFAULT_IS_SECTION);
        assertThat(testTheme.getAbout()).isEqualTo(DEFAULT_ABOUT);
        assertThat(testTheme.getHomeWorkAbouts()).isEqualTo(DEFAULT_HOME_WORK_ABOUTS);
        assertThat(testTheme.getFileUrl()).isEqualTo(DEFAULT_FILE_URL);
    }

    @Test
    @Transactional
    public void createThemeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = themeRepository.findAll().size();

        // Create the Theme with an existing ID
        theme.setId(1L);
        ThemeDTO themeDTO = themeMapper.toDto(theme);

        // An entity with an existing ID cannot be created, so this API call must fail
        restThemeMockMvc.perform(post("/api/themes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Theme in the database
        List<Theme> themeList = themeRepository.findAll();
        assertThat(themeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllThemes() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList
        restThemeMockMvc.perform(get("/api/themes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(theme.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].isSection").value(hasItem(DEFAULT_IS_SECTION.booleanValue())))
            .andExpect(jsonPath("$.[*].about").value(hasItem(DEFAULT_ABOUT)))
            .andExpect(jsonPath("$.[*].homeWorkAbouts").value(hasItem(DEFAULT_HOME_WORK_ABOUTS)))
            .andExpect(jsonPath("$.[*].fileUrl").value(hasItem(DEFAULT_FILE_URL)));
    }
    
    @Test
    @Transactional
    public void getTheme() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get the theme
        restThemeMockMvc.perform(get("/api/themes/{id}", theme.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(theme.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.isSection").value(DEFAULT_IS_SECTION.booleanValue()))
            .andExpect(jsonPath("$.about").value(DEFAULT_ABOUT))
            .andExpect(jsonPath("$.homeWorkAbouts").value(DEFAULT_HOME_WORK_ABOUTS))
            .andExpect(jsonPath("$.fileUrl").value(DEFAULT_FILE_URL));
    }


    @Test
    @Transactional
    public void getThemesByIdFiltering() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        Long id = theme.getId();

        defaultThemeShouldBeFound("id.equals=" + id);
        defaultThemeShouldNotBeFound("id.notEquals=" + id);

        defaultThemeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultThemeShouldNotBeFound("id.greaterThan=" + id);

        defaultThemeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultThemeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllThemesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where name equals to DEFAULT_NAME
        defaultThemeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the themeList where name equals to UPDATED_NAME
        defaultThemeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllThemesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where name not equals to DEFAULT_NAME
        defaultThemeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the themeList where name not equals to UPDATED_NAME
        defaultThemeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllThemesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultThemeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the themeList where name equals to UPDATED_NAME
        defaultThemeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllThemesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where name is not null
        defaultThemeShouldBeFound("name.specified=true");

        // Get all the themeList where name is null
        defaultThemeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllThemesByNameContainsSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where name contains DEFAULT_NAME
        defaultThemeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the themeList where name contains UPDATED_NAME
        defaultThemeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllThemesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where name does not contain DEFAULT_NAME
        defaultThemeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the themeList where name does not contain UPDATED_NAME
        defaultThemeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllThemesByNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where number equals to DEFAULT_NUMBER
        defaultThemeShouldBeFound("number.equals=" + DEFAULT_NUMBER);

        // Get all the themeList where number equals to UPDATED_NUMBER
        defaultThemeShouldNotBeFound("number.equals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllThemesByNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where number not equals to DEFAULT_NUMBER
        defaultThemeShouldNotBeFound("number.notEquals=" + DEFAULT_NUMBER);

        // Get all the themeList where number not equals to UPDATED_NUMBER
        defaultThemeShouldBeFound("number.notEquals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllThemesByNumberIsInShouldWork() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where number in DEFAULT_NUMBER or UPDATED_NUMBER
        defaultThemeShouldBeFound("number.in=" + DEFAULT_NUMBER + "," + UPDATED_NUMBER);

        // Get all the themeList where number equals to UPDATED_NUMBER
        defaultThemeShouldNotBeFound("number.in=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllThemesByNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where number is not null
        defaultThemeShouldBeFound("number.specified=true");

        // Get all the themeList where number is null
        defaultThemeShouldNotBeFound("number.specified=false");
    }

    @Test
    @Transactional
    public void getAllThemesByNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where number is greater than or equal to DEFAULT_NUMBER
        defaultThemeShouldBeFound("number.greaterThanOrEqual=" + DEFAULT_NUMBER);

        // Get all the themeList where number is greater than or equal to UPDATED_NUMBER
        defaultThemeShouldNotBeFound("number.greaterThanOrEqual=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllThemesByNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where number is less than or equal to DEFAULT_NUMBER
        defaultThemeShouldBeFound("number.lessThanOrEqual=" + DEFAULT_NUMBER);

        // Get all the themeList where number is less than or equal to SMALLER_NUMBER
        defaultThemeShouldNotBeFound("number.lessThanOrEqual=" + SMALLER_NUMBER);
    }

    @Test
    @Transactional
    public void getAllThemesByNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where number is less than DEFAULT_NUMBER
        defaultThemeShouldNotBeFound("number.lessThan=" + DEFAULT_NUMBER);

        // Get all the themeList where number is less than UPDATED_NUMBER
        defaultThemeShouldBeFound("number.lessThan=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllThemesByNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where number is greater than DEFAULT_NUMBER
        defaultThemeShouldNotBeFound("number.greaterThan=" + DEFAULT_NUMBER);

        // Get all the themeList where number is greater than SMALLER_NUMBER
        defaultThemeShouldBeFound("number.greaterThan=" + SMALLER_NUMBER);
    }


    @Test
    @Transactional
    public void getAllThemesByIsSectionIsEqualToSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where isSection equals to DEFAULT_IS_SECTION
        defaultThemeShouldBeFound("isSection.equals=" + DEFAULT_IS_SECTION);

        // Get all the themeList where isSection equals to UPDATED_IS_SECTION
        defaultThemeShouldNotBeFound("isSection.equals=" + UPDATED_IS_SECTION);
    }

    @Test
    @Transactional
    public void getAllThemesByIsSectionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where isSection not equals to DEFAULT_IS_SECTION
        defaultThemeShouldNotBeFound("isSection.notEquals=" + DEFAULT_IS_SECTION);

        // Get all the themeList where isSection not equals to UPDATED_IS_SECTION
        defaultThemeShouldBeFound("isSection.notEquals=" + UPDATED_IS_SECTION);
    }

    @Test
    @Transactional
    public void getAllThemesByIsSectionIsInShouldWork() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where isSection in DEFAULT_IS_SECTION or UPDATED_IS_SECTION
        defaultThemeShouldBeFound("isSection.in=" + DEFAULT_IS_SECTION + "," + UPDATED_IS_SECTION);

        // Get all the themeList where isSection equals to UPDATED_IS_SECTION
        defaultThemeShouldNotBeFound("isSection.in=" + UPDATED_IS_SECTION);
    }

    @Test
    @Transactional
    public void getAllThemesByIsSectionIsNullOrNotNull() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where isSection is not null
        defaultThemeShouldBeFound("isSection.specified=true");

        // Get all the themeList where isSection is null
        defaultThemeShouldNotBeFound("isSection.specified=false");
    }

    @Test
    @Transactional
    public void getAllThemesByAboutIsEqualToSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where about equals to DEFAULT_ABOUT
        defaultThemeShouldBeFound("about.equals=" + DEFAULT_ABOUT);

        // Get all the themeList where about equals to UPDATED_ABOUT
        defaultThemeShouldNotBeFound("about.equals=" + UPDATED_ABOUT);
    }

    @Test
    @Transactional
    public void getAllThemesByAboutIsNotEqualToSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where about not equals to DEFAULT_ABOUT
        defaultThemeShouldNotBeFound("about.notEquals=" + DEFAULT_ABOUT);

        // Get all the themeList where about not equals to UPDATED_ABOUT
        defaultThemeShouldBeFound("about.notEquals=" + UPDATED_ABOUT);
    }

    @Test
    @Transactional
    public void getAllThemesByAboutIsInShouldWork() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where about in DEFAULT_ABOUT or UPDATED_ABOUT
        defaultThemeShouldBeFound("about.in=" + DEFAULT_ABOUT + "," + UPDATED_ABOUT);

        // Get all the themeList where about equals to UPDATED_ABOUT
        defaultThemeShouldNotBeFound("about.in=" + UPDATED_ABOUT);
    }

    @Test
    @Transactional
    public void getAllThemesByAboutIsNullOrNotNull() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where about is not null
        defaultThemeShouldBeFound("about.specified=true");

        // Get all the themeList where about is null
        defaultThemeShouldNotBeFound("about.specified=false");
    }
                @Test
    @Transactional
    public void getAllThemesByAboutContainsSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where about contains DEFAULT_ABOUT
        defaultThemeShouldBeFound("about.contains=" + DEFAULT_ABOUT);

        // Get all the themeList where about contains UPDATED_ABOUT
        defaultThemeShouldNotBeFound("about.contains=" + UPDATED_ABOUT);
    }

    @Test
    @Transactional
    public void getAllThemesByAboutNotContainsSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where about does not contain DEFAULT_ABOUT
        defaultThemeShouldNotBeFound("about.doesNotContain=" + DEFAULT_ABOUT);

        // Get all the themeList where about does not contain UPDATED_ABOUT
        defaultThemeShouldBeFound("about.doesNotContain=" + UPDATED_ABOUT);
    }


    @Test
    @Transactional
    public void getAllThemesByHomeWorkAboutsIsEqualToSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where homeWorkAbouts equals to DEFAULT_HOME_WORK_ABOUTS
        defaultThemeShouldBeFound("homeWorkAbouts.equals=" + DEFAULT_HOME_WORK_ABOUTS);

        // Get all the themeList where homeWorkAbouts equals to UPDATED_HOME_WORK_ABOUTS
        defaultThemeShouldNotBeFound("homeWorkAbouts.equals=" + UPDATED_HOME_WORK_ABOUTS);
    }

    @Test
    @Transactional
    public void getAllThemesByHomeWorkAboutsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where homeWorkAbouts not equals to DEFAULT_HOME_WORK_ABOUTS
        defaultThemeShouldNotBeFound("homeWorkAbouts.notEquals=" + DEFAULT_HOME_WORK_ABOUTS);

        // Get all the themeList where homeWorkAbouts not equals to UPDATED_HOME_WORK_ABOUTS
        defaultThemeShouldBeFound("homeWorkAbouts.notEquals=" + UPDATED_HOME_WORK_ABOUTS);
    }

    @Test
    @Transactional
    public void getAllThemesByHomeWorkAboutsIsInShouldWork() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where homeWorkAbouts in DEFAULT_HOME_WORK_ABOUTS or UPDATED_HOME_WORK_ABOUTS
        defaultThemeShouldBeFound("homeWorkAbouts.in=" + DEFAULT_HOME_WORK_ABOUTS + "," + UPDATED_HOME_WORK_ABOUTS);

        // Get all the themeList where homeWorkAbouts equals to UPDATED_HOME_WORK_ABOUTS
        defaultThemeShouldNotBeFound("homeWorkAbouts.in=" + UPDATED_HOME_WORK_ABOUTS);
    }

    @Test
    @Transactional
    public void getAllThemesByHomeWorkAboutsIsNullOrNotNull() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where homeWorkAbouts is not null
        defaultThemeShouldBeFound("homeWorkAbouts.specified=true");

        // Get all the themeList where homeWorkAbouts is null
        defaultThemeShouldNotBeFound("homeWorkAbouts.specified=false");
    }
                @Test
    @Transactional
    public void getAllThemesByHomeWorkAboutsContainsSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where homeWorkAbouts contains DEFAULT_HOME_WORK_ABOUTS
        defaultThemeShouldBeFound("homeWorkAbouts.contains=" + DEFAULT_HOME_WORK_ABOUTS);

        // Get all the themeList where homeWorkAbouts contains UPDATED_HOME_WORK_ABOUTS
        defaultThemeShouldNotBeFound("homeWorkAbouts.contains=" + UPDATED_HOME_WORK_ABOUTS);
    }

    @Test
    @Transactional
    public void getAllThemesByHomeWorkAboutsNotContainsSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where homeWorkAbouts does not contain DEFAULT_HOME_WORK_ABOUTS
        defaultThemeShouldNotBeFound("homeWorkAbouts.doesNotContain=" + DEFAULT_HOME_WORK_ABOUTS);

        // Get all the themeList where homeWorkAbouts does not contain UPDATED_HOME_WORK_ABOUTS
        defaultThemeShouldBeFound("homeWorkAbouts.doesNotContain=" + UPDATED_HOME_WORK_ABOUTS);
    }


    @Test
    @Transactional
    public void getAllThemesByFileUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where fileUrl equals to DEFAULT_FILE_URL
        defaultThemeShouldBeFound("fileUrl.equals=" + DEFAULT_FILE_URL);

        // Get all the themeList where fileUrl equals to UPDATED_FILE_URL
        defaultThemeShouldNotBeFound("fileUrl.equals=" + UPDATED_FILE_URL);
    }

    @Test
    @Transactional
    public void getAllThemesByFileUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where fileUrl not equals to DEFAULT_FILE_URL
        defaultThemeShouldNotBeFound("fileUrl.notEquals=" + DEFAULT_FILE_URL);

        // Get all the themeList where fileUrl not equals to UPDATED_FILE_URL
        defaultThemeShouldBeFound("fileUrl.notEquals=" + UPDATED_FILE_URL);
    }

    @Test
    @Transactional
    public void getAllThemesByFileUrlIsInShouldWork() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where fileUrl in DEFAULT_FILE_URL or UPDATED_FILE_URL
        defaultThemeShouldBeFound("fileUrl.in=" + DEFAULT_FILE_URL + "," + UPDATED_FILE_URL);

        // Get all the themeList where fileUrl equals to UPDATED_FILE_URL
        defaultThemeShouldNotBeFound("fileUrl.in=" + UPDATED_FILE_URL);
    }

    @Test
    @Transactional
    public void getAllThemesByFileUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where fileUrl is not null
        defaultThemeShouldBeFound("fileUrl.specified=true");

        // Get all the themeList where fileUrl is null
        defaultThemeShouldNotBeFound("fileUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllThemesByFileUrlContainsSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where fileUrl contains DEFAULT_FILE_URL
        defaultThemeShouldBeFound("fileUrl.contains=" + DEFAULT_FILE_URL);

        // Get all the themeList where fileUrl contains UPDATED_FILE_URL
        defaultThemeShouldNotBeFound("fileUrl.contains=" + UPDATED_FILE_URL);
    }

    @Test
    @Transactional
    public void getAllThemesByFileUrlNotContainsSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where fileUrl does not contain DEFAULT_FILE_URL
        defaultThemeShouldNotBeFound("fileUrl.doesNotContain=" + DEFAULT_FILE_URL);

        // Get all the themeList where fileUrl does not contain UPDATED_FILE_URL
        defaultThemeShouldBeFound("fileUrl.doesNotContain=" + UPDATED_FILE_URL);
    }


    @Test
    @Transactional
    public void getAllThemesByPlanningIsEqualToSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);
        Planning planning = PlanningResourceIT.createEntity(em);
        em.persist(planning);
        em.flush();
        theme.setPlanning(planning);
        themeRepository.saveAndFlush(theme);
        Long planningId = planning.getId();

        // Get all the themeList where planning equals to planningId
        defaultThemeShouldBeFound("planningId.equals=" + planningId);

        // Get all the themeList where planning equals to planningId + 1
        defaultThemeShouldNotBeFound("planningId.equals=" + (planningId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultThemeShouldBeFound(String filter) throws Exception {
        restThemeMockMvc.perform(get("/api/themes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(theme.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].isSection").value(hasItem(DEFAULT_IS_SECTION.booleanValue())))
            .andExpect(jsonPath("$.[*].about").value(hasItem(DEFAULT_ABOUT)))
            .andExpect(jsonPath("$.[*].homeWorkAbouts").value(hasItem(DEFAULT_HOME_WORK_ABOUTS)))
            .andExpect(jsonPath("$.[*].fileUrl").value(hasItem(DEFAULT_FILE_URL)));

        // Check, that the count call also returns 1
        restThemeMockMvc.perform(get("/api/themes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultThemeShouldNotBeFound(String filter) throws Exception {
        restThemeMockMvc.perform(get("/api/themes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restThemeMockMvc.perform(get("/api/themes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTheme() throws Exception {
        // Get the theme
        restThemeMockMvc.perform(get("/api/themes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTheme() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        int databaseSizeBeforeUpdate = themeRepository.findAll().size();

        // Update the theme
        Theme updatedTheme = themeRepository.findById(theme.getId()).get();
        // Disconnect from session so that the updates on updatedTheme are not directly saved in db
        em.detach(updatedTheme);
        updatedTheme
            .name(UPDATED_NAME)
            .number(UPDATED_NUMBER)
            .isSection(UPDATED_IS_SECTION)
            .about(UPDATED_ABOUT)
            .homeWorkAbouts(UPDATED_HOME_WORK_ABOUTS)
            .fileUrl(UPDATED_FILE_URL);
        ThemeDTO themeDTO = themeMapper.toDto(updatedTheme);

        restThemeMockMvc.perform(put("/api/themes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themeDTO)))
            .andExpect(status().isOk());

        // Validate the Theme in the database
        List<Theme> themeList = themeRepository.findAll();
        assertThat(themeList).hasSize(databaseSizeBeforeUpdate);
        Theme testTheme = themeList.get(themeList.size() - 1);
        assertThat(testTheme.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTheme.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testTheme.isIsSection()).isEqualTo(UPDATED_IS_SECTION);
        assertThat(testTheme.getAbout()).isEqualTo(UPDATED_ABOUT);
        assertThat(testTheme.getHomeWorkAbouts()).isEqualTo(UPDATED_HOME_WORK_ABOUTS);
        assertThat(testTheme.getFileUrl()).isEqualTo(UPDATED_FILE_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingTheme() throws Exception {
        int databaseSizeBeforeUpdate = themeRepository.findAll().size();

        // Create the Theme
        ThemeDTO themeDTO = themeMapper.toDto(theme);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThemeMockMvc.perform(put("/api/themes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Theme in the database
        List<Theme> themeList = themeRepository.findAll();
        assertThat(themeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTheme() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        int databaseSizeBeforeDelete = themeRepository.findAll().size();

        // Delete the theme
        restThemeMockMvc.perform(delete("/api/themes/{id}", theme.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Theme> themeList = themeRepository.findAll();
        assertThat(themeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
