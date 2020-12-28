package uz.kitc.web.rest;

import uz.kitc.KitcuzApp;
import uz.kitc.domain.Faq;
import uz.kitc.repository.FaqRepository;
import uz.kitc.service.FaqService;
import uz.kitc.service.dto.FaqDTO;
import uz.kitc.service.mapper.FaqMapper;
import uz.kitc.service.dto.FaqCriteria;
import uz.kitc.service.FaqQueryService;

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
 * Integration tests for the {@link FaqResource} REST controller.
 */
@SpringBootTest(classes = KitcuzApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FaqResourceIT {

    private static final Integer DEFAULT_ORDER_NUMBER = 1;
    private static final Integer UPDATED_ORDER_NUMBER = 2;
    private static final Integer SMALLER_ORDER_NUMBER = 1 - 1;

    private static final String DEFAULT_QUESTION = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION = "BBBBBBBBBB";

    private static final String DEFAULT_ANSWER = "AAAAAAAAAA";
    private static final String UPDATED_ANSWER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private FaqRepository faqRepository;

    @Autowired
    private FaqMapper faqMapper;

    @Autowired
    private FaqService faqService;

    @Autowired
    private FaqQueryService faqQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFaqMockMvc;

    private Faq faq;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Faq createEntity(EntityManager em) {
        Faq faq = new Faq()
            .orderNumber(DEFAULT_ORDER_NUMBER)
            .question(DEFAULT_QUESTION)
            .answer(DEFAULT_ANSWER)
            .active(DEFAULT_ACTIVE);
        return faq;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Faq createUpdatedEntity(EntityManager em) {
        Faq faq = new Faq()
            .orderNumber(UPDATED_ORDER_NUMBER)
            .question(UPDATED_QUESTION)
            .answer(UPDATED_ANSWER)
            .active(UPDATED_ACTIVE);
        return faq;
    }

    @BeforeEach
    public void initTest() {
        faq = createEntity(em);
    }

    @Test
    @Transactional
    public void createFaq() throws Exception {
        int databaseSizeBeforeCreate = faqRepository.findAll().size();
        // Create the Faq
        FaqDTO faqDTO = faqMapper.toDto(faq);
        restFaqMockMvc.perform(post("/api/faqs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(faqDTO)))
            .andExpect(status().isCreated());

        // Validate the Faq in the database
        List<Faq> faqList = faqRepository.findAll();
        assertThat(faqList).hasSize(databaseSizeBeforeCreate + 1);
        Faq testFaq = faqList.get(faqList.size() - 1);
        assertThat(testFaq.getOrderNumber()).isEqualTo(DEFAULT_ORDER_NUMBER);
        assertThat(testFaq.getQuestion()).isEqualTo(DEFAULT_QUESTION);
        assertThat(testFaq.getAnswer()).isEqualTo(DEFAULT_ANSWER);
        assertThat(testFaq.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createFaqWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = faqRepository.findAll().size();

        // Create the Faq with an existing ID
        faq.setId(1L);
        FaqDTO faqDTO = faqMapper.toDto(faq);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFaqMockMvc.perform(post("/api/faqs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(faqDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Faq in the database
        List<Faq> faqList = faqRepository.findAll();
        assertThat(faqList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFaqs() throws Exception {
        // Initialize the database
        faqRepository.saveAndFlush(faq);

        // Get all the faqList
        restFaqMockMvc.perform(get("/api/faqs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(faq.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderNumber").value(hasItem(DEFAULT_ORDER_NUMBER)))
            .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION)))
            .andExpect(jsonPath("$.[*].answer").value(hasItem(DEFAULT_ANSWER)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getFaq() throws Exception {
        // Initialize the database
        faqRepository.saveAndFlush(faq);

        // Get the faq
        restFaqMockMvc.perform(get("/api/faqs/{id}", faq.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(faq.getId().intValue()))
            .andExpect(jsonPath("$.orderNumber").value(DEFAULT_ORDER_NUMBER))
            .andExpect(jsonPath("$.question").value(DEFAULT_QUESTION))
            .andExpect(jsonPath("$.answer").value(DEFAULT_ANSWER))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getFaqsByIdFiltering() throws Exception {
        // Initialize the database
        faqRepository.saveAndFlush(faq);

        Long id = faq.getId();

        defaultFaqShouldBeFound("id.equals=" + id);
        defaultFaqShouldNotBeFound("id.notEquals=" + id);

        defaultFaqShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFaqShouldNotBeFound("id.greaterThan=" + id);

        defaultFaqShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFaqShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFaqsByOrderNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        faqRepository.saveAndFlush(faq);

        // Get all the faqList where orderNumber equals to DEFAULT_ORDER_NUMBER
        defaultFaqShouldBeFound("orderNumber.equals=" + DEFAULT_ORDER_NUMBER);

        // Get all the faqList where orderNumber equals to UPDATED_ORDER_NUMBER
        defaultFaqShouldNotBeFound("orderNumber.equals=" + UPDATED_ORDER_NUMBER);
    }

    @Test
    @Transactional
    public void getAllFaqsByOrderNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        faqRepository.saveAndFlush(faq);

        // Get all the faqList where orderNumber not equals to DEFAULT_ORDER_NUMBER
        defaultFaqShouldNotBeFound("orderNumber.notEquals=" + DEFAULT_ORDER_NUMBER);

        // Get all the faqList where orderNumber not equals to UPDATED_ORDER_NUMBER
        defaultFaqShouldBeFound("orderNumber.notEquals=" + UPDATED_ORDER_NUMBER);
    }

    @Test
    @Transactional
    public void getAllFaqsByOrderNumberIsInShouldWork() throws Exception {
        // Initialize the database
        faqRepository.saveAndFlush(faq);

        // Get all the faqList where orderNumber in DEFAULT_ORDER_NUMBER or UPDATED_ORDER_NUMBER
        defaultFaqShouldBeFound("orderNumber.in=" + DEFAULT_ORDER_NUMBER + "," + UPDATED_ORDER_NUMBER);

        // Get all the faqList where orderNumber equals to UPDATED_ORDER_NUMBER
        defaultFaqShouldNotBeFound("orderNumber.in=" + UPDATED_ORDER_NUMBER);
    }

    @Test
    @Transactional
    public void getAllFaqsByOrderNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        faqRepository.saveAndFlush(faq);

        // Get all the faqList where orderNumber is not null
        defaultFaqShouldBeFound("orderNumber.specified=true");

        // Get all the faqList where orderNumber is null
        defaultFaqShouldNotBeFound("orderNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllFaqsByOrderNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        faqRepository.saveAndFlush(faq);

        // Get all the faqList where orderNumber is greater than or equal to DEFAULT_ORDER_NUMBER
        defaultFaqShouldBeFound("orderNumber.greaterThanOrEqual=" + DEFAULT_ORDER_NUMBER);

        // Get all the faqList where orderNumber is greater than or equal to UPDATED_ORDER_NUMBER
        defaultFaqShouldNotBeFound("orderNumber.greaterThanOrEqual=" + UPDATED_ORDER_NUMBER);
    }

    @Test
    @Transactional
    public void getAllFaqsByOrderNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        faqRepository.saveAndFlush(faq);

        // Get all the faqList where orderNumber is less than or equal to DEFAULT_ORDER_NUMBER
        defaultFaqShouldBeFound("orderNumber.lessThanOrEqual=" + DEFAULT_ORDER_NUMBER);

        // Get all the faqList where orderNumber is less than or equal to SMALLER_ORDER_NUMBER
        defaultFaqShouldNotBeFound("orderNumber.lessThanOrEqual=" + SMALLER_ORDER_NUMBER);
    }

    @Test
    @Transactional
    public void getAllFaqsByOrderNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        faqRepository.saveAndFlush(faq);

        // Get all the faqList where orderNumber is less than DEFAULT_ORDER_NUMBER
        defaultFaqShouldNotBeFound("orderNumber.lessThan=" + DEFAULT_ORDER_NUMBER);

        // Get all the faqList where orderNumber is less than UPDATED_ORDER_NUMBER
        defaultFaqShouldBeFound("orderNumber.lessThan=" + UPDATED_ORDER_NUMBER);
    }

    @Test
    @Transactional
    public void getAllFaqsByOrderNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        faqRepository.saveAndFlush(faq);

        // Get all the faqList where orderNumber is greater than DEFAULT_ORDER_NUMBER
        defaultFaqShouldNotBeFound("orderNumber.greaterThan=" + DEFAULT_ORDER_NUMBER);

        // Get all the faqList where orderNumber is greater than SMALLER_ORDER_NUMBER
        defaultFaqShouldBeFound("orderNumber.greaterThan=" + SMALLER_ORDER_NUMBER);
    }


    @Test
    @Transactional
    public void getAllFaqsByQuestionIsEqualToSomething() throws Exception {
        // Initialize the database
        faqRepository.saveAndFlush(faq);

        // Get all the faqList where question equals to DEFAULT_QUESTION
        defaultFaqShouldBeFound("question.equals=" + DEFAULT_QUESTION);

        // Get all the faqList where question equals to UPDATED_QUESTION
        defaultFaqShouldNotBeFound("question.equals=" + UPDATED_QUESTION);
    }

    @Test
    @Transactional
    public void getAllFaqsByQuestionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        faqRepository.saveAndFlush(faq);

        // Get all the faqList where question not equals to DEFAULT_QUESTION
        defaultFaqShouldNotBeFound("question.notEquals=" + DEFAULT_QUESTION);

        // Get all the faqList where question not equals to UPDATED_QUESTION
        defaultFaqShouldBeFound("question.notEquals=" + UPDATED_QUESTION);
    }

    @Test
    @Transactional
    public void getAllFaqsByQuestionIsInShouldWork() throws Exception {
        // Initialize the database
        faqRepository.saveAndFlush(faq);

        // Get all the faqList where question in DEFAULT_QUESTION or UPDATED_QUESTION
        defaultFaqShouldBeFound("question.in=" + DEFAULT_QUESTION + "," + UPDATED_QUESTION);

        // Get all the faqList where question equals to UPDATED_QUESTION
        defaultFaqShouldNotBeFound("question.in=" + UPDATED_QUESTION);
    }

    @Test
    @Transactional
    public void getAllFaqsByQuestionIsNullOrNotNull() throws Exception {
        // Initialize the database
        faqRepository.saveAndFlush(faq);

        // Get all the faqList where question is not null
        defaultFaqShouldBeFound("question.specified=true");

        // Get all the faqList where question is null
        defaultFaqShouldNotBeFound("question.specified=false");
    }
                @Test
    @Transactional
    public void getAllFaqsByQuestionContainsSomething() throws Exception {
        // Initialize the database
        faqRepository.saveAndFlush(faq);

        // Get all the faqList where question contains DEFAULT_QUESTION
        defaultFaqShouldBeFound("question.contains=" + DEFAULT_QUESTION);

        // Get all the faqList where question contains UPDATED_QUESTION
        defaultFaqShouldNotBeFound("question.contains=" + UPDATED_QUESTION);
    }

    @Test
    @Transactional
    public void getAllFaqsByQuestionNotContainsSomething() throws Exception {
        // Initialize the database
        faqRepository.saveAndFlush(faq);

        // Get all the faqList where question does not contain DEFAULT_QUESTION
        defaultFaqShouldNotBeFound("question.doesNotContain=" + DEFAULT_QUESTION);

        // Get all the faqList where question does not contain UPDATED_QUESTION
        defaultFaqShouldBeFound("question.doesNotContain=" + UPDATED_QUESTION);
    }


    @Test
    @Transactional
    public void getAllFaqsByAnswerIsEqualToSomething() throws Exception {
        // Initialize the database
        faqRepository.saveAndFlush(faq);

        // Get all the faqList where answer equals to DEFAULT_ANSWER
        defaultFaqShouldBeFound("answer.equals=" + DEFAULT_ANSWER);

        // Get all the faqList where answer equals to UPDATED_ANSWER
        defaultFaqShouldNotBeFound("answer.equals=" + UPDATED_ANSWER);
    }

    @Test
    @Transactional
    public void getAllFaqsByAnswerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        faqRepository.saveAndFlush(faq);

        // Get all the faqList where answer not equals to DEFAULT_ANSWER
        defaultFaqShouldNotBeFound("answer.notEquals=" + DEFAULT_ANSWER);

        // Get all the faqList where answer not equals to UPDATED_ANSWER
        defaultFaqShouldBeFound("answer.notEquals=" + UPDATED_ANSWER);
    }

    @Test
    @Transactional
    public void getAllFaqsByAnswerIsInShouldWork() throws Exception {
        // Initialize the database
        faqRepository.saveAndFlush(faq);

        // Get all the faqList where answer in DEFAULT_ANSWER or UPDATED_ANSWER
        defaultFaqShouldBeFound("answer.in=" + DEFAULT_ANSWER + "," + UPDATED_ANSWER);

        // Get all the faqList where answer equals to UPDATED_ANSWER
        defaultFaqShouldNotBeFound("answer.in=" + UPDATED_ANSWER);
    }

    @Test
    @Transactional
    public void getAllFaqsByAnswerIsNullOrNotNull() throws Exception {
        // Initialize the database
        faqRepository.saveAndFlush(faq);

        // Get all the faqList where answer is not null
        defaultFaqShouldBeFound("answer.specified=true");

        // Get all the faqList where answer is null
        defaultFaqShouldNotBeFound("answer.specified=false");
    }
                @Test
    @Transactional
    public void getAllFaqsByAnswerContainsSomething() throws Exception {
        // Initialize the database
        faqRepository.saveAndFlush(faq);

        // Get all the faqList where answer contains DEFAULT_ANSWER
        defaultFaqShouldBeFound("answer.contains=" + DEFAULT_ANSWER);

        // Get all the faqList where answer contains UPDATED_ANSWER
        defaultFaqShouldNotBeFound("answer.contains=" + UPDATED_ANSWER);
    }

    @Test
    @Transactional
    public void getAllFaqsByAnswerNotContainsSomething() throws Exception {
        // Initialize the database
        faqRepository.saveAndFlush(faq);

        // Get all the faqList where answer does not contain DEFAULT_ANSWER
        defaultFaqShouldNotBeFound("answer.doesNotContain=" + DEFAULT_ANSWER);

        // Get all the faqList where answer does not contain UPDATED_ANSWER
        defaultFaqShouldBeFound("answer.doesNotContain=" + UPDATED_ANSWER);
    }


    @Test
    @Transactional
    public void getAllFaqsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        faqRepository.saveAndFlush(faq);

        // Get all the faqList where active equals to DEFAULT_ACTIVE
        defaultFaqShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the faqList where active equals to UPDATED_ACTIVE
        defaultFaqShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllFaqsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        faqRepository.saveAndFlush(faq);

        // Get all the faqList where active not equals to DEFAULT_ACTIVE
        defaultFaqShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the faqList where active not equals to UPDATED_ACTIVE
        defaultFaqShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllFaqsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        faqRepository.saveAndFlush(faq);

        // Get all the faqList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultFaqShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the faqList where active equals to UPDATED_ACTIVE
        defaultFaqShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllFaqsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        faqRepository.saveAndFlush(faq);

        // Get all the faqList where active is not null
        defaultFaqShouldBeFound("active.specified=true");

        // Get all the faqList where active is null
        defaultFaqShouldNotBeFound("active.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFaqShouldBeFound(String filter) throws Exception {
        restFaqMockMvc.perform(get("/api/faqs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(faq.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderNumber").value(hasItem(DEFAULT_ORDER_NUMBER)))
            .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION)))
            .andExpect(jsonPath("$.[*].answer").value(hasItem(DEFAULT_ANSWER)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restFaqMockMvc.perform(get("/api/faqs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFaqShouldNotBeFound(String filter) throws Exception {
        restFaqMockMvc.perform(get("/api/faqs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFaqMockMvc.perform(get("/api/faqs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingFaq() throws Exception {
        // Get the faq
        restFaqMockMvc.perform(get("/api/faqs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFaq() throws Exception {
        // Initialize the database
        faqRepository.saveAndFlush(faq);

        int databaseSizeBeforeUpdate = faqRepository.findAll().size();

        // Update the faq
        Faq updatedFaq = faqRepository.findById(faq.getId()).get();
        // Disconnect from session so that the updates on updatedFaq are not directly saved in db
        em.detach(updatedFaq);
        updatedFaq
            .orderNumber(UPDATED_ORDER_NUMBER)
            .question(UPDATED_QUESTION)
            .answer(UPDATED_ANSWER)
            .active(UPDATED_ACTIVE);
        FaqDTO faqDTO = faqMapper.toDto(updatedFaq);

        restFaqMockMvc.perform(put("/api/faqs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(faqDTO)))
            .andExpect(status().isOk());

        // Validate the Faq in the database
        List<Faq> faqList = faqRepository.findAll();
        assertThat(faqList).hasSize(databaseSizeBeforeUpdate);
        Faq testFaq = faqList.get(faqList.size() - 1);
        assertThat(testFaq.getOrderNumber()).isEqualTo(UPDATED_ORDER_NUMBER);
        assertThat(testFaq.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testFaq.getAnswer()).isEqualTo(UPDATED_ANSWER);
        assertThat(testFaq.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingFaq() throws Exception {
        int databaseSizeBeforeUpdate = faqRepository.findAll().size();

        // Create the Faq
        FaqDTO faqDTO = faqMapper.toDto(faq);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFaqMockMvc.perform(put("/api/faqs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(faqDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Faq in the database
        List<Faq> faqList = faqRepository.findAll();
        assertThat(faqList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFaq() throws Exception {
        // Initialize the database
        faqRepository.saveAndFlush(faq);

        int databaseSizeBeforeDelete = faqRepository.findAll().size();

        // Delete the faq
        restFaqMockMvc.perform(delete("/api/faqs/{id}", faq.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Faq> faqList = faqRepository.findAll();
        assertThat(faqList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
