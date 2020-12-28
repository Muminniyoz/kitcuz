package uz.kitc.web.rest;

import uz.kitc.KitcuzApp;
import uz.kitc.domain.News;
import uz.kitc.repository.NewsRepository;
import uz.kitc.service.NewsService;
import uz.kitc.service.dto.NewsDTO;
import uz.kitc.service.mapper.NewsMapper;
import uz.kitc.service.dto.NewsCriteria;
import uz.kitc.service.NewsQueryService;

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
 * Integration tests for the {@link NewsResource} REST controller.
 */
@SpringBootTest(classes = KitcuzApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class NewsResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_FULL_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_FULL_TEXT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CREATED_DATE = LocalDate.ofEpochDay(-1L);

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private NewsService newsService;

    @Autowired
    private NewsQueryService newsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNewsMockMvc;

    private News news;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static News createEntity(EntityManager em) {
        News news = new News()
            .title(DEFAULT_TITLE)
            .shortText(DEFAULT_SHORT_TEXT)
            .fullText(DEFAULT_FULL_TEXT)
            .createdDate(DEFAULT_CREATED_DATE)
            .active(DEFAULT_ACTIVE)
            .imageUrl(DEFAULT_IMAGE_URL);
        return news;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static News createUpdatedEntity(EntityManager em) {
        News news = new News()
            .title(UPDATED_TITLE)
            .shortText(UPDATED_SHORT_TEXT)
            .fullText(UPDATED_FULL_TEXT)
            .createdDate(UPDATED_CREATED_DATE)
            .active(UPDATED_ACTIVE)
            .imageUrl(UPDATED_IMAGE_URL);
        return news;
    }

    @BeforeEach
    public void initTest() {
        news = createEntity(em);
    }

    @Test
    @Transactional
    public void createNews() throws Exception {
        int databaseSizeBeforeCreate = newsRepository.findAll().size();
        // Create the News
        NewsDTO newsDTO = newsMapper.toDto(news);
        restNewsMockMvc.perform(post("/api/news")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(newsDTO)))
            .andExpect(status().isCreated());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeCreate + 1);
        News testNews = newsList.get(newsList.size() - 1);
        assertThat(testNews.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testNews.getShortText()).isEqualTo(DEFAULT_SHORT_TEXT);
        assertThat(testNews.getFullText()).isEqualTo(DEFAULT_FULL_TEXT);
        assertThat(testNews.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testNews.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testNews.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
    }

    @Test
    @Transactional
    public void createNewsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = newsRepository.findAll().size();

        // Create the News with an existing ID
        news.setId(1L);
        NewsDTO newsDTO = newsMapper.toDto(news);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNewsMockMvc.perform(post("/api/news")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(newsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNews() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList
        restNewsMockMvc.perform(get("/api/news?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(news.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].shortText").value(hasItem(DEFAULT_SHORT_TEXT)))
            .andExpect(jsonPath("$.[*].fullText").value(hasItem(DEFAULT_FULL_TEXT.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)));
    }
    
    @Test
    @Transactional
    public void getNews() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get the news
        restNewsMockMvc.perform(get("/api/news/{id}", news.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(news.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.shortText").value(DEFAULT_SHORT_TEXT))
            .andExpect(jsonPath("$.fullText").value(DEFAULT_FULL_TEXT.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL));
    }


    @Test
    @Transactional
    public void getNewsByIdFiltering() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        Long id = news.getId();

        defaultNewsShouldBeFound("id.equals=" + id);
        defaultNewsShouldNotBeFound("id.notEquals=" + id);

        defaultNewsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNewsShouldNotBeFound("id.greaterThan=" + id);

        defaultNewsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNewsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllNewsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where title equals to DEFAULT_TITLE
        defaultNewsShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the newsList where title equals to UPDATED_TITLE
        defaultNewsShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllNewsByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where title not equals to DEFAULT_TITLE
        defaultNewsShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the newsList where title not equals to UPDATED_TITLE
        defaultNewsShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllNewsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultNewsShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the newsList where title equals to UPDATED_TITLE
        defaultNewsShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllNewsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where title is not null
        defaultNewsShouldBeFound("title.specified=true");

        // Get all the newsList where title is null
        defaultNewsShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllNewsByTitleContainsSomething() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where title contains DEFAULT_TITLE
        defaultNewsShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the newsList where title contains UPDATED_TITLE
        defaultNewsShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllNewsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where title does not contain DEFAULT_TITLE
        defaultNewsShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the newsList where title does not contain UPDATED_TITLE
        defaultNewsShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllNewsByShortTextIsEqualToSomething() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where shortText equals to DEFAULT_SHORT_TEXT
        defaultNewsShouldBeFound("shortText.equals=" + DEFAULT_SHORT_TEXT);

        // Get all the newsList where shortText equals to UPDATED_SHORT_TEXT
        defaultNewsShouldNotBeFound("shortText.equals=" + UPDATED_SHORT_TEXT);
    }

    @Test
    @Transactional
    public void getAllNewsByShortTextIsNotEqualToSomething() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where shortText not equals to DEFAULT_SHORT_TEXT
        defaultNewsShouldNotBeFound("shortText.notEquals=" + DEFAULT_SHORT_TEXT);

        // Get all the newsList where shortText not equals to UPDATED_SHORT_TEXT
        defaultNewsShouldBeFound("shortText.notEquals=" + UPDATED_SHORT_TEXT);
    }

    @Test
    @Transactional
    public void getAllNewsByShortTextIsInShouldWork() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where shortText in DEFAULT_SHORT_TEXT or UPDATED_SHORT_TEXT
        defaultNewsShouldBeFound("shortText.in=" + DEFAULT_SHORT_TEXT + "," + UPDATED_SHORT_TEXT);

        // Get all the newsList where shortText equals to UPDATED_SHORT_TEXT
        defaultNewsShouldNotBeFound("shortText.in=" + UPDATED_SHORT_TEXT);
    }

    @Test
    @Transactional
    public void getAllNewsByShortTextIsNullOrNotNull() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where shortText is not null
        defaultNewsShouldBeFound("shortText.specified=true");

        // Get all the newsList where shortText is null
        defaultNewsShouldNotBeFound("shortText.specified=false");
    }
                @Test
    @Transactional
    public void getAllNewsByShortTextContainsSomething() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where shortText contains DEFAULT_SHORT_TEXT
        defaultNewsShouldBeFound("shortText.contains=" + DEFAULT_SHORT_TEXT);

        // Get all the newsList where shortText contains UPDATED_SHORT_TEXT
        defaultNewsShouldNotBeFound("shortText.contains=" + UPDATED_SHORT_TEXT);
    }

    @Test
    @Transactional
    public void getAllNewsByShortTextNotContainsSomething() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where shortText does not contain DEFAULT_SHORT_TEXT
        defaultNewsShouldNotBeFound("shortText.doesNotContain=" + DEFAULT_SHORT_TEXT);

        // Get all the newsList where shortText does not contain UPDATED_SHORT_TEXT
        defaultNewsShouldBeFound("shortText.doesNotContain=" + UPDATED_SHORT_TEXT);
    }


    @Test
    @Transactional
    public void getAllNewsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where createdDate equals to DEFAULT_CREATED_DATE
        defaultNewsShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the newsList where createdDate equals to UPDATED_CREATED_DATE
        defaultNewsShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNewsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultNewsShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the newsList where createdDate not equals to UPDATED_CREATED_DATE
        defaultNewsShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNewsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultNewsShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the newsList where createdDate equals to UPDATED_CREATED_DATE
        defaultNewsShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNewsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where createdDate is not null
        defaultNewsShouldBeFound("createdDate.specified=true");

        // Get all the newsList where createdDate is null
        defaultNewsShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllNewsByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultNewsShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the newsList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultNewsShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNewsByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultNewsShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the newsList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultNewsShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNewsByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where createdDate is less than DEFAULT_CREATED_DATE
        defaultNewsShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the newsList where createdDate is less than UPDATED_CREATED_DATE
        defaultNewsShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNewsByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultNewsShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the newsList where createdDate is greater than SMALLER_CREATED_DATE
        defaultNewsShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }


    @Test
    @Transactional
    public void getAllNewsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where active equals to DEFAULT_ACTIVE
        defaultNewsShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the newsList where active equals to UPDATED_ACTIVE
        defaultNewsShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllNewsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where active not equals to DEFAULT_ACTIVE
        defaultNewsShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the newsList where active not equals to UPDATED_ACTIVE
        defaultNewsShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllNewsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultNewsShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the newsList where active equals to UPDATED_ACTIVE
        defaultNewsShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllNewsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where active is not null
        defaultNewsShouldBeFound("active.specified=true");

        // Get all the newsList where active is null
        defaultNewsShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllNewsByImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where imageUrl equals to DEFAULT_IMAGE_URL
        defaultNewsShouldBeFound("imageUrl.equals=" + DEFAULT_IMAGE_URL);

        // Get all the newsList where imageUrl equals to UPDATED_IMAGE_URL
        defaultNewsShouldNotBeFound("imageUrl.equals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllNewsByImageUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where imageUrl not equals to DEFAULT_IMAGE_URL
        defaultNewsShouldNotBeFound("imageUrl.notEquals=" + DEFAULT_IMAGE_URL);

        // Get all the newsList where imageUrl not equals to UPDATED_IMAGE_URL
        defaultNewsShouldBeFound("imageUrl.notEquals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllNewsByImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where imageUrl in DEFAULT_IMAGE_URL or UPDATED_IMAGE_URL
        defaultNewsShouldBeFound("imageUrl.in=" + DEFAULT_IMAGE_URL + "," + UPDATED_IMAGE_URL);

        // Get all the newsList where imageUrl equals to UPDATED_IMAGE_URL
        defaultNewsShouldNotBeFound("imageUrl.in=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllNewsByImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where imageUrl is not null
        defaultNewsShouldBeFound("imageUrl.specified=true");

        // Get all the newsList where imageUrl is null
        defaultNewsShouldNotBeFound("imageUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllNewsByImageUrlContainsSomething() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where imageUrl contains DEFAULT_IMAGE_URL
        defaultNewsShouldBeFound("imageUrl.contains=" + DEFAULT_IMAGE_URL);

        // Get all the newsList where imageUrl contains UPDATED_IMAGE_URL
        defaultNewsShouldNotBeFound("imageUrl.contains=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllNewsByImageUrlNotContainsSomething() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where imageUrl does not contain DEFAULT_IMAGE_URL
        defaultNewsShouldNotBeFound("imageUrl.doesNotContain=" + DEFAULT_IMAGE_URL);

        // Get all the newsList where imageUrl does not contain UPDATED_IMAGE_URL
        defaultNewsShouldBeFound("imageUrl.doesNotContain=" + UPDATED_IMAGE_URL);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNewsShouldBeFound(String filter) throws Exception {
        restNewsMockMvc.perform(get("/api/news?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(news.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].shortText").value(hasItem(DEFAULT_SHORT_TEXT)))
            .andExpect(jsonPath("$.[*].fullText").value(hasItem(DEFAULT_FULL_TEXT.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)));

        // Check, that the count call also returns 1
        restNewsMockMvc.perform(get("/api/news/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNewsShouldNotBeFound(String filter) throws Exception {
        restNewsMockMvc.perform(get("/api/news?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNewsMockMvc.perform(get("/api/news/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingNews() throws Exception {
        // Get the news
        restNewsMockMvc.perform(get("/api/news/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNews() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        int databaseSizeBeforeUpdate = newsRepository.findAll().size();

        // Update the news
        News updatedNews = newsRepository.findById(news.getId()).get();
        // Disconnect from session so that the updates on updatedNews are not directly saved in db
        em.detach(updatedNews);
        updatedNews
            .title(UPDATED_TITLE)
            .shortText(UPDATED_SHORT_TEXT)
            .fullText(UPDATED_FULL_TEXT)
            .createdDate(UPDATED_CREATED_DATE)
            .active(UPDATED_ACTIVE)
            .imageUrl(UPDATED_IMAGE_URL);
        NewsDTO newsDTO = newsMapper.toDto(updatedNews);

        restNewsMockMvc.perform(put("/api/news")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(newsDTO)))
            .andExpect(status().isOk());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeUpdate);
        News testNews = newsList.get(newsList.size() - 1);
        assertThat(testNews.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testNews.getShortText()).isEqualTo(UPDATED_SHORT_TEXT);
        assertThat(testNews.getFullText()).isEqualTo(UPDATED_FULL_TEXT);
        assertThat(testNews.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testNews.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testNews.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingNews() throws Exception {
        int databaseSizeBeforeUpdate = newsRepository.findAll().size();

        // Create the News
        NewsDTO newsDTO = newsMapper.toDto(news);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNewsMockMvc.perform(put("/api/news")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(newsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNews() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        int databaseSizeBeforeDelete = newsRepository.findAll().size();

        // Delete the news
        restNewsMockMvc.perform(delete("/api/news/{id}", news.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
