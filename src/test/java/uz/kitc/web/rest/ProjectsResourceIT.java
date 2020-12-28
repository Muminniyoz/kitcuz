package uz.kitc.web.rest;

import uz.kitc.KitcuzApp;
import uz.kitc.domain.Projects;
import uz.kitc.repository.ProjectsRepository;
import uz.kitc.service.ProjectsService;
import uz.kitc.service.dto.ProjectsDTO;
import uz.kitc.service.mapper.ProjectsMapper;
import uz.kitc.service.dto.ProjectsCriteria;
import uz.kitc.service.ProjectsQueryService;

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
 * Integration tests for the {@link ProjectsResource} REST controller.
 */
@SpringBootTest(classes = KitcuzApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProjectsResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_ABOUT = "AAAAAAAAAA";
    private static final String UPDATED_ABOUT = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_URL = "AAAAAAAAAA";
    private static final String UPDATED_FILE_URL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CREATED_DATE = LocalDate.ofEpochDay(-1L);

    private static final Boolean DEFAULT_IS_SHOWING = false;
    private static final Boolean UPDATED_IS_SHOWING = true;

    @Autowired
    private ProjectsRepository projectsRepository;

    @Autowired
    private ProjectsMapper projectsMapper;

    @Autowired
    private ProjectsService projectsService;

    @Autowired
    private ProjectsQueryService projectsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectsMockMvc;

    private Projects projects;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Projects createEntity(EntityManager em) {
        Projects projects = new Projects()
            .title(DEFAULT_TITLE)
            .about(DEFAULT_ABOUT)
            .fileUrl(DEFAULT_FILE_URL)
            .createdDate(DEFAULT_CREATED_DATE)
            .isShowing(DEFAULT_IS_SHOWING);
        return projects;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Projects createUpdatedEntity(EntityManager em) {
        Projects projects = new Projects()
            .title(UPDATED_TITLE)
            .about(UPDATED_ABOUT)
            .fileUrl(UPDATED_FILE_URL)
            .createdDate(UPDATED_CREATED_DATE)
            .isShowing(UPDATED_IS_SHOWING);
        return projects;
    }

    @BeforeEach
    public void initTest() {
        projects = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjects() throws Exception {
        int databaseSizeBeforeCreate = projectsRepository.findAll().size();
        // Create the Projects
        ProjectsDTO projectsDTO = projectsMapper.toDto(projects);
        restProjectsMockMvc.perform(post("/api/projects")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectsDTO)))
            .andExpect(status().isCreated());

        // Validate the Projects in the database
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeCreate + 1);
        Projects testProjects = projectsList.get(projectsList.size() - 1);
        assertThat(testProjects.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testProjects.getAbout()).isEqualTo(DEFAULT_ABOUT);
        assertThat(testProjects.getFileUrl()).isEqualTo(DEFAULT_FILE_URL);
        assertThat(testProjects.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProjects.isIsShowing()).isEqualTo(DEFAULT_IS_SHOWING);
    }

    @Test
    @Transactional
    public void createProjectsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectsRepository.findAll().size();

        // Create the Projects with an existing ID
        projects.setId(1L);
        ProjectsDTO projectsDTO = projectsMapper.toDto(projects);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectsMockMvc.perform(post("/api/projects")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Projects in the database
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProjects() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList
        restProjectsMockMvc.perform(get("/api/projects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projects.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].about").value(hasItem(DEFAULT_ABOUT.toString())))
            .andExpect(jsonPath("$.[*].fileUrl").value(hasItem(DEFAULT_FILE_URL)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].isShowing").value(hasItem(DEFAULT_IS_SHOWING.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getProjects() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get the projects
        restProjectsMockMvc.perform(get("/api/projects/{id}", projects.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projects.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.about").value(DEFAULT_ABOUT.toString()))
            .andExpect(jsonPath("$.fileUrl").value(DEFAULT_FILE_URL))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.isShowing").value(DEFAULT_IS_SHOWING.booleanValue()));
    }


    @Test
    @Transactional
    public void getProjectsByIdFiltering() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        Long id = projects.getId();

        defaultProjectsShouldBeFound("id.equals=" + id);
        defaultProjectsShouldNotBeFound("id.notEquals=" + id);

        defaultProjectsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProjectsShouldNotBeFound("id.greaterThan=" + id);

        defaultProjectsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProjectsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProjectsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where title equals to DEFAULT_TITLE
        defaultProjectsShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the projectsList where title equals to UPDATED_TITLE
        defaultProjectsShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllProjectsByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where title not equals to DEFAULT_TITLE
        defaultProjectsShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the projectsList where title not equals to UPDATED_TITLE
        defaultProjectsShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllProjectsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultProjectsShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the projectsList where title equals to UPDATED_TITLE
        defaultProjectsShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllProjectsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where title is not null
        defaultProjectsShouldBeFound("title.specified=true");

        // Get all the projectsList where title is null
        defaultProjectsShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllProjectsByTitleContainsSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where title contains DEFAULT_TITLE
        defaultProjectsShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the projectsList where title contains UPDATED_TITLE
        defaultProjectsShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllProjectsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where title does not contain DEFAULT_TITLE
        defaultProjectsShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the projectsList where title does not contain UPDATED_TITLE
        defaultProjectsShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllProjectsByFileUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where fileUrl equals to DEFAULT_FILE_URL
        defaultProjectsShouldBeFound("fileUrl.equals=" + DEFAULT_FILE_URL);

        // Get all the projectsList where fileUrl equals to UPDATED_FILE_URL
        defaultProjectsShouldNotBeFound("fileUrl.equals=" + UPDATED_FILE_URL);
    }

    @Test
    @Transactional
    public void getAllProjectsByFileUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where fileUrl not equals to DEFAULT_FILE_URL
        defaultProjectsShouldNotBeFound("fileUrl.notEquals=" + DEFAULT_FILE_URL);

        // Get all the projectsList where fileUrl not equals to UPDATED_FILE_URL
        defaultProjectsShouldBeFound("fileUrl.notEquals=" + UPDATED_FILE_URL);
    }

    @Test
    @Transactional
    public void getAllProjectsByFileUrlIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where fileUrl in DEFAULT_FILE_URL or UPDATED_FILE_URL
        defaultProjectsShouldBeFound("fileUrl.in=" + DEFAULT_FILE_URL + "," + UPDATED_FILE_URL);

        // Get all the projectsList where fileUrl equals to UPDATED_FILE_URL
        defaultProjectsShouldNotBeFound("fileUrl.in=" + UPDATED_FILE_URL);
    }

    @Test
    @Transactional
    public void getAllProjectsByFileUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where fileUrl is not null
        defaultProjectsShouldBeFound("fileUrl.specified=true");

        // Get all the projectsList where fileUrl is null
        defaultProjectsShouldNotBeFound("fileUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllProjectsByFileUrlContainsSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where fileUrl contains DEFAULT_FILE_URL
        defaultProjectsShouldBeFound("fileUrl.contains=" + DEFAULT_FILE_URL);

        // Get all the projectsList where fileUrl contains UPDATED_FILE_URL
        defaultProjectsShouldNotBeFound("fileUrl.contains=" + UPDATED_FILE_URL);
    }

    @Test
    @Transactional
    public void getAllProjectsByFileUrlNotContainsSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where fileUrl does not contain DEFAULT_FILE_URL
        defaultProjectsShouldNotBeFound("fileUrl.doesNotContain=" + DEFAULT_FILE_URL);

        // Get all the projectsList where fileUrl does not contain UPDATED_FILE_URL
        defaultProjectsShouldBeFound("fileUrl.doesNotContain=" + UPDATED_FILE_URL);
    }


    @Test
    @Transactional
    public void getAllProjectsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where createdDate equals to DEFAULT_CREATED_DATE
        defaultProjectsShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the projectsList where createdDate equals to UPDATED_CREATED_DATE
        defaultProjectsShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllProjectsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultProjectsShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the projectsList where createdDate not equals to UPDATED_CREATED_DATE
        defaultProjectsShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllProjectsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultProjectsShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the projectsList where createdDate equals to UPDATED_CREATED_DATE
        defaultProjectsShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllProjectsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where createdDate is not null
        defaultProjectsShouldBeFound("createdDate.specified=true");

        // Get all the projectsList where createdDate is null
        defaultProjectsShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjectsByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultProjectsShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the projectsList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultProjectsShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllProjectsByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultProjectsShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the projectsList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultProjectsShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllProjectsByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where createdDate is less than DEFAULT_CREATED_DATE
        defaultProjectsShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the projectsList where createdDate is less than UPDATED_CREATED_DATE
        defaultProjectsShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllProjectsByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultProjectsShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the projectsList where createdDate is greater than SMALLER_CREATED_DATE
        defaultProjectsShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }


    @Test
    @Transactional
    public void getAllProjectsByIsShowingIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where isShowing equals to DEFAULT_IS_SHOWING
        defaultProjectsShouldBeFound("isShowing.equals=" + DEFAULT_IS_SHOWING);

        // Get all the projectsList where isShowing equals to UPDATED_IS_SHOWING
        defaultProjectsShouldNotBeFound("isShowing.equals=" + UPDATED_IS_SHOWING);
    }

    @Test
    @Transactional
    public void getAllProjectsByIsShowingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where isShowing not equals to DEFAULT_IS_SHOWING
        defaultProjectsShouldNotBeFound("isShowing.notEquals=" + DEFAULT_IS_SHOWING);

        // Get all the projectsList where isShowing not equals to UPDATED_IS_SHOWING
        defaultProjectsShouldBeFound("isShowing.notEquals=" + UPDATED_IS_SHOWING);
    }

    @Test
    @Transactional
    public void getAllProjectsByIsShowingIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where isShowing in DEFAULT_IS_SHOWING or UPDATED_IS_SHOWING
        defaultProjectsShouldBeFound("isShowing.in=" + DEFAULT_IS_SHOWING + "," + UPDATED_IS_SHOWING);

        // Get all the projectsList where isShowing equals to UPDATED_IS_SHOWING
        defaultProjectsShouldNotBeFound("isShowing.in=" + UPDATED_IS_SHOWING);
    }

    @Test
    @Transactional
    public void getAllProjectsByIsShowingIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where isShowing is not null
        defaultProjectsShouldBeFound("isShowing.specified=true");

        // Get all the projectsList where isShowing is null
        defaultProjectsShouldNotBeFound("isShowing.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProjectsShouldBeFound(String filter) throws Exception {
        restProjectsMockMvc.perform(get("/api/projects?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projects.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].about").value(hasItem(DEFAULT_ABOUT.toString())))
            .andExpect(jsonPath("$.[*].fileUrl").value(hasItem(DEFAULT_FILE_URL)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].isShowing").value(hasItem(DEFAULT_IS_SHOWING.booleanValue())));

        // Check, that the count call also returns 1
        restProjectsMockMvc.perform(get("/api/projects/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProjectsShouldNotBeFound(String filter) throws Exception {
        restProjectsMockMvc.perform(get("/api/projects?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProjectsMockMvc.perform(get("/api/projects/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProjects() throws Exception {
        // Get the projects
        restProjectsMockMvc.perform(get("/api/projects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjects() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        int databaseSizeBeforeUpdate = projectsRepository.findAll().size();

        // Update the projects
        Projects updatedProjects = projectsRepository.findById(projects.getId()).get();
        // Disconnect from session so that the updates on updatedProjects are not directly saved in db
        em.detach(updatedProjects);
        updatedProjects
            .title(UPDATED_TITLE)
            .about(UPDATED_ABOUT)
            .fileUrl(UPDATED_FILE_URL)
            .createdDate(UPDATED_CREATED_DATE)
            .isShowing(UPDATED_IS_SHOWING);
        ProjectsDTO projectsDTO = projectsMapper.toDto(updatedProjects);

        restProjectsMockMvc.perform(put("/api/projects")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectsDTO)))
            .andExpect(status().isOk());

        // Validate the Projects in the database
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeUpdate);
        Projects testProjects = projectsList.get(projectsList.size() - 1);
        assertThat(testProjects.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testProjects.getAbout()).isEqualTo(UPDATED_ABOUT);
        assertThat(testProjects.getFileUrl()).isEqualTo(UPDATED_FILE_URL);
        assertThat(testProjects.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProjects.isIsShowing()).isEqualTo(UPDATED_IS_SHOWING);
    }

    @Test
    @Transactional
    public void updateNonExistingProjects() throws Exception {
        int databaseSizeBeforeUpdate = projectsRepository.findAll().size();

        // Create the Projects
        ProjectsDTO projectsDTO = projectsMapper.toDto(projects);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectsMockMvc.perform(put("/api/projects")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Projects in the database
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProjects() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        int databaseSizeBeforeDelete = projectsRepository.findAll().size();

        // Delete the projects
        restProjectsMockMvc.perform(delete("/api/projects/{id}", projects.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
