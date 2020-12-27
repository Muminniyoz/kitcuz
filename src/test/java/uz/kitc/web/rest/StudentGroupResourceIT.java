package uz.kitc.web.rest;

import uz.kitc.KitcuzApp;
import uz.kitc.domain.StudentGroup;
import uz.kitc.domain.Student;
import uz.kitc.domain.CourseGroup;
import uz.kitc.repository.StudentGroupRepository;
import uz.kitc.service.StudentGroupService;
import uz.kitc.service.dto.StudentGroupDTO;
import uz.kitc.service.mapper.StudentGroupMapper;
import uz.kitc.service.dto.StudentGroupCriteria;
import uz.kitc.service.StudentGroupQueryService;

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
 * Integration tests for the {@link StudentGroupResource} REST controller.
 */
@SpringBootTest(classes = KitcuzApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StudentGroupResourceIT {

    private static final LocalDate DEFAULT_STARTING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_STARTING_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_STARTING_DATE = LocalDate.ofEpochDay(-1L);

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_CONTRACT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CONTRACT_NUMBER = "BBBBBBBBBB";

    @Autowired
    private StudentGroupRepository studentGroupRepository;

    @Autowired
    private StudentGroupMapper studentGroupMapper;

    @Autowired
    private StudentGroupService studentGroupService;

    @Autowired
    private StudentGroupQueryService studentGroupQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStudentGroupMockMvc;

    private StudentGroup studentGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudentGroup createEntity(EntityManager em) {
        StudentGroup studentGroup = new StudentGroup()
            .startingDate(DEFAULT_STARTING_DATE)
            .active(DEFAULT_ACTIVE)
            .contractNumber(DEFAULT_CONTRACT_NUMBER);
        return studentGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudentGroup createUpdatedEntity(EntityManager em) {
        StudentGroup studentGroup = new StudentGroup()
            .startingDate(UPDATED_STARTING_DATE)
            .active(UPDATED_ACTIVE)
            .contractNumber(UPDATED_CONTRACT_NUMBER);
        return studentGroup;
    }

    @BeforeEach
    public void initTest() {
        studentGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudentGroup() throws Exception {
        int databaseSizeBeforeCreate = studentGroupRepository.findAll().size();
        // Create the StudentGroup
        StudentGroupDTO studentGroupDTO = studentGroupMapper.toDto(studentGroup);
        restStudentGroupMockMvc.perform(post("/api/student-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(studentGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the StudentGroup in the database
        List<StudentGroup> studentGroupList = studentGroupRepository.findAll();
        assertThat(studentGroupList).hasSize(databaseSizeBeforeCreate + 1);
        StudentGroup testStudentGroup = studentGroupList.get(studentGroupList.size() - 1);
        assertThat(testStudentGroup.getStartingDate()).isEqualTo(DEFAULT_STARTING_DATE);
        assertThat(testStudentGroup.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testStudentGroup.getContractNumber()).isEqualTo(DEFAULT_CONTRACT_NUMBER);
    }

    @Test
    @Transactional
    public void createStudentGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentGroupRepository.findAll().size();

        // Create the StudentGroup with an existing ID
        studentGroup.setId(1L);
        StudentGroupDTO studentGroupDTO = studentGroupMapper.toDto(studentGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentGroupMockMvc.perform(post("/api/student-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(studentGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StudentGroup in the database
        List<StudentGroup> studentGroupList = studentGroupRepository.findAll();
        assertThat(studentGroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllStudentGroups() throws Exception {
        // Initialize the database
        studentGroupRepository.saveAndFlush(studentGroup);

        // Get all the studentGroupList
        restStudentGroupMockMvc.perform(get("/api/student-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].startingDate").value(hasItem(DEFAULT_STARTING_DATE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].contractNumber").value(hasItem(DEFAULT_CONTRACT_NUMBER)));
    }
    
    @Test
    @Transactional
    public void getStudentGroup() throws Exception {
        // Initialize the database
        studentGroupRepository.saveAndFlush(studentGroup);

        // Get the studentGroup
        restStudentGroupMockMvc.perform(get("/api/student-groups/{id}", studentGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(studentGroup.getId().intValue()))
            .andExpect(jsonPath("$.startingDate").value(DEFAULT_STARTING_DATE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.contractNumber").value(DEFAULT_CONTRACT_NUMBER));
    }


    @Test
    @Transactional
    public void getStudentGroupsByIdFiltering() throws Exception {
        // Initialize the database
        studentGroupRepository.saveAndFlush(studentGroup);

        Long id = studentGroup.getId();

        defaultStudentGroupShouldBeFound("id.equals=" + id);
        defaultStudentGroupShouldNotBeFound("id.notEquals=" + id);

        defaultStudentGroupShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStudentGroupShouldNotBeFound("id.greaterThan=" + id);

        defaultStudentGroupShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStudentGroupShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllStudentGroupsByStartingDateIsEqualToSomething() throws Exception {
        // Initialize the database
        studentGroupRepository.saveAndFlush(studentGroup);

        // Get all the studentGroupList where startingDate equals to DEFAULT_STARTING_DATE
        defaultStudentGroupShouldBeFound("startingDate.equals=" + DEFAULT_STARTING_DATE);

        // Get all the studentGroupList where startingDate equals to UPDATED_STARTING_DATE
        defaultStudentGroupShouldNotBeFound("startingDate.equals=" + UPDATED_STARTING_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentGroupsByStartingDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentGroupRepository.saveAndFlush(studentGroup);

        // Get all the studentGroupList where startingDate not equals to DEFAULT_STARTING_DATE
        defaultStudentGroupShouldNotBeFound("startingDate.notEquals=" + DEFAULT_STARTING_DATE);

        // Get all the studentGroupList where startingDate not equals to UPDATED_STARTING_DATE
        defaultStudentGroupShouldBeFound("startingDate.notEquals=" + UPDATED_STARTING_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentGroupsByStartingDateIsInShouldWork() throws Exception {
        // Initialize the database
        studentGroupRepository.saveAndFlush(studentGroup);

        // Get all the studentGroupList where startingDate in DEFAULT_STARTING_DATE or UPDATED_STARTING_DATE
        defaultStudentGroupShouldBeFound("startingDate.in=" + DEFAULT_STARTING_DATE + "," + UPDATED_STARTING_DATE);

        // Get all the studentGroupList where startingDate equals to UPDATED_STARTING_DATE
        defaultStudentGroupShouldNotBeFound("startingDate.in=" + UPDATED_STARTING_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentGroupsByStartingDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentGroupRepository.saveAndFlush(studentGroup);

        // Get all the studentGroupList where startingDate is not null
        defaultStudentGroupShouldBeFound("startingDate.specified=true");

        // Get all the studentGroupList where startingDate is null
        defaultStudentGroupShouldNotBeFound("startingDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentGroupsByStartingDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentGroupRepository.saveAndFlush(studentGroup);

        // Get all the studentGroupList where startingDate is greater than or equal to DEFAULT_STARTING_DATE
        defaultStudentGroupShouldBeFound("startingDate.greaterThanOrEqual=" + DEFAULT_STARTING_DATE);

        // Get all the studentGroupList where startingDate is greater than or equal to UPDATED_STARTING_DATE
        defaultStudentGroupShouldNotBeFound("startingDate.greaterThanOrEqual=" + UPDATED_STARTING_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentGroupsByStartingDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentGroupRepository.saveAndFlush(studentGroup);

        // Get all the studentGroupList where startingDate is less than or equal to DEFAULT_STARTING_DATE
        defaultStudentGroupShouldBeFound("startingDate.lessThanOrEqual=" + DEFAULT_STARTING_DATE);

        // Get all the studentGroupList where startingDate is less than or equal to SMALLER_STARTING_DATE
        defaultStudentGroupShouldNotBeFound("startingDate.lessThanOrEqual=" + SMALLER_STARTING_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentGroupsByStartingDateIsLessThanSomething() throws Exception {
        // Initialize the database
        studentGroupRepository.saveAndFlush(studentGroup);

        // Get all the studentGroupList where startingDate is less than DEFAULT_STARTING_DATE
        defaultStudentGroupShouldNotBeFound("startingDate.lessThan=" + DEFAULT_STARTING_DATE);

        // Get all the studentGroupList where startingDate is less than UPDATED_STARTING_DATE
        defaultStudentGroupShouldBeFound("startingDate.lessThan=" + UPDATED_STARTING_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentGroupsByStartingDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        studentGroupRepository.saveAndFlush(studentGroup);

        // Get all the studentGroupList where startingDate is greater than DEFAULT_STARTING_DATE
        defaultStudentGroupShouldNotBeFound("startingDate.greaterThan=" + DEFAULT_STARTING_DATE);

        // Get all the studentGroupList where startingDate is greater than SMALLER_STARTING_DATE
        defaultStudentGroupShouldBeFound("startingDate.greaterThan=" + SMALLER_STARTING_DATE);
    }


    @Test
    @Transactional
    public void getAllStudentGroupsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        studentGroupRepository.saveAndFlush(studentGroup);

        // Get all the studentGroupList where active equals to DEFAULT_ACTIVE
        defaultStudentGroupShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the studentGroupList where active equals to UPDATED_ACTIVE
        defaultStudentGroupShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllStudentGroupsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentGroupRepository.saveAndFlush(studentGroup);

        // Get all the studentGroupList where active not equals to DEFAULT_ACTIVE
        defaultStudentGroupShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the studentGroupList where active not equals to UPDATED_ACTIVE
        defaultStudentGroupShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllStudentGroupsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        studentGroupRepository.saveAndFlush(studentGroup);

        // Get all the studentGroupList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultStudentGroupShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the studentGroupList where active equals to UPDATED_ACTIVE
        defaultStudentGroupShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllStudentGroupsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentGroupRepository.saveAndFlush(studentGroup);

        // Get all the studentGroupList where active is not null
        defaultStudentGroupShouldBeFound("active.specified=true");

        // Get all the studentGroupList where active is null
        defaultStudentGroupShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentGroupsByContractNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        studentGroupRepository.saveAndFlush(studentGroup);

        // Get all the studentGroupList where contractNumber equals to DEFAULT_CONTRACT_NUMBER
        defaultStudentGroupShouldBeFound("contractNumber.equals=" + DEFAULT_CONTRACT_NUMBER);

        // Get all the studentGroupList where contractNumber equals to UPDATED_CONTRACT_NUMBER
        defaultStudentGroupShouldNotBeFound("contractNumber.equals=" + UPDATED_CONTRACT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllStudentGroupsByContractNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentGroupRepository.saveAndFlush(studentGroup);

        // Get all the studentGroupList where contractNumber not equals to DEFAULT_CONTRACT_NUMBER
        defaultStudentGroupShouldNotBeFound("contractNumber.notEquals=" + DEFAULT_CONTRACT_NUMBER);

        // Get all the studentGroupList where contractNumber not equals to UPDATED_CONTRACT_NUMBER
        defaultStudentGroupShouldBeFound("contractNumber.notEquals=" + UPDATED_CONTRACT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllStudentGroupsByContractNumberIsInShouldWork() throws Exception {
        // Initialize the database
        studentGroupRepository.saveAndFlush(studentGroup);

        // Get all the studentGroupList where contractNumber in DEFAULT_CONTRACT_NUMBER or UPDATED_CONTRACT_NUMBER
        defaultStudentGroupShouldBeFound("contractNumber.in=" + DEFAULT_CONTRACT_NUMBER + "," + UPDATED_CONTRACT_NUMBER);

        // Get all the studentGroupList where contractNumber equals to UPDATED_CONTRACT_NUMBER
        defaultStudentGroupShouldNotBeFound("contractNumber.in=" + UPDATED_CONTRACT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllStudentGroupsByContractNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentGroupRepository.saveAndFlush(studentGroup);

        // Get all the studentGroupList where contractNumber is not null
        defaultStudentGroupShouldBeFound("contractNumber.specified=true");

        // Get all the studentGroupList where contractNumber is null
        defaultStudentGroupShouldNotBeFound("contractNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllStudentGroupsByContractNumberContainsSomething() throws Exception {
        // Initialize the database
        studentGroupRepository.saveAndFlush(studentGroup);

        // Get all the studentGroupList where contractNumber contains DEFAULT_CONTRACT_NUMBER
        defaultStudentGroupShouldBeFound("contractNumber.contains=" + DEFAULT_CONTRACT_NUMBER);

        // Get all the studentGroupList where contractNumber contains UPDATED_CONTRACT_NUMBER
        defaultStudentGroupShouldNotBeFound("contractNumber.contains=" + UPDATED_CONTRACT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllStudentGroupsByContractNumberNotContainsSomething() throws Exception {
        // Initialize the database
        studentGroupRepository.saveAndFlush(studentGroup);

        // Get all the studentGroupList where contractNumber does not contain DEFAULT_CONTRACT_NUMBER
        defaultStudentGroupShouldNotBeFound("contractNumber.doesNotContain=" + DEFAULT_CONTRACT_NUMBER);

        // Get all the studentGroupList where contractNumber does not contain UPDATED_CONTRACT_NUMBER
        defaultStudentGroupShouldBeFound("contractNumber.doesNotContain=" + UPDATED_CONTRACT_NUMBER);
    }


    @Test
    @Transactional
    public void getAllStudentGroupsByStudentIsEqualToSomething() throws Exception {
        // Initialize the database
        studentGroupRepository.saveAndFlush(studentGroup);
        Student student = StudentResourceIT.createEntity(em);
        em.persist(student);
        em.flush();
        studentGroup.setStudent(student);
        studentGroupRepository.saveAndFlush(studentGroup);
        Long studentId = student.getId();

        // Get all the studentGroupList where student equals to studentId
        defaultStudentGroupShouldBeFound("studentId.equals=" + studentId);

        // Get all the studentGroupList where student equals to studentId + 1
        defaultStudentGroupShouldNotBeFound("studentId.equals=" + (studentId + 1));
    }


    @Test
    @Transactional
    public void getAllStudentGroupsByGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        studentGroupRepository.saveAndFlush(studentGroup);
        CourseGroup group = CourseGroupResourceIT.createEntity(em);
        em.persist(group);
        em.flush();
        studentGroup.setGroup(group);
        studentGroupRepository.saveAndFlush(studentGroup);
        Long groupId = group.getId();

        // Get all the studentGroupList where group equals to groupId
        defaultStudentGroupShouldBeFound("groupId.equals=" + groupId);

        // Get all the studentGroupList where group equals to groupId + 1
        defaultStudentGroupShouldNotBeFound("groupId.equals=" + (groupId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStudentGroupShouldBeFound(String filter) throws Exception {
        restStudentGroupMockMvc.perform(get("/api/student-groups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].startingDate").value(hasItem(DEFAULT_STARTING_DATE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].contractNumber").value(hasItem(DEFAULT_CONTRACT_NUMBER)));

        // Check, that the count call also returns 1
        restStudentGroupMockMvc.perform(get("/api/student-groups/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStudentGroupShouldNotBeFound(String filter) throws Exception {
        restStudentGroupMockMvc.perform(get("/api/student-groups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStudentGroupMockMvc.perform(get("/api/student-groups/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingStudentGroup() throws Exception {
        // Get the studentGroup
        restStudentGroupMockMvc.perform(get("/api/student-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudentGroup() throws Exception {
        // Initialize the database
        studentGroupRepository.saveAndFlush(studentGroup);

        int databaseSizeBeforeUpdate = studentGroupRepository.findAll().size();

        // Update the studentGroup
        StudentGroup updatedStudentGroup = studentGroupRepository.findById(studentGroup.getId()).get();
        // Disconnect from session so that the updates on updatedStudentGroup are not directly saved in db
        em.detach(updatedStudentGroup);
        updatedStudentGroup
            .startingDate(UPDATED_STARTING_DATE)
            .active(UPDATED_ACTIVE)
            .contractNumber(UPDATED_CONTRACT_NUMBER);
        StudentGroupDTO studentGroupDTO = studentGroupMapper.toDto(updatedStudentGroup);

        restStudentGroupMockMvc.perform(put("/api/student-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(studentGroupDTO)))
            .andExpect(status().isOk());

        // Validate the StudentGroup in the database
        List<StudentGroup> studentGroupList = studentGroupRepository.findAll();
        assertThat(studentGroupList).hasSize(databaseSizeBeforeUpdate);
        StudentGroup testStudentGroup = studentGroupList.get(studentGroupList.size() - 1);
        assertThat(testStudentGroup.getStartingDate()).isEqualTo(UPDATED_STARTING_DATE);
        assertThat(testStudentGroup.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testStudentGroup.getContractNumber()).isEqualTo(UPDATED_CONTRACT_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingStudentGroup() throws Exception {
        int databaseSizeBeforeUpdate = studentGroupRepository.findAll().size();

        // Create the StudentGroup
        StudentGroupDTO studentGroupDTO = studentGroupMapper.toDto(studentGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentGroupMockMvc.perform(put("/api/student-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(studentGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StudentGroup in the database
        List<StudentGroup> studentGroupList = studentGroupRepository.findAll();
        assertThat(studentGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStudentGroup() throws Exception {
        // Initialize the database
        studentGroupRepository.saveAndFlush(studentGroup);

        int databaseSizeBeforeDelete = studentGroupRepository.findAll().size();

        // Delete the studentGroup
        restStudentGroupMockMvc.perform(delete("/api/student-groups/{id}", studentGroup.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StudentGroup> studentGroupList = studentGroupRepository.findAll();
        assertThat(studentGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
