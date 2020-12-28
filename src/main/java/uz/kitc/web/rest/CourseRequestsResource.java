package uz.kitc.web.rest;

import uz.kitc.service.CourseRequestsService;
import uz.kitc.web.rest.errors.BadRequestAlertException;
import uz.kitc.service.dto.CourseRequestsDTO;
import uz.kitc.service.dto.CourseRequestsCriteria;
import uz.kitc.service.CourseRequestsQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link uz.kitc.domain.CourseRequests}.
 */
@RestController
@RequestMapping("/api")
public class CourseRequestsResource {

    private final Logger log = LoggerFactory.getLogger(CourseRequestsResource.class);

    private static final String ENTITY_NAME = "courseRequests";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CourseRequestsService courseRequestsService;

    private final CourseRequestsQueryService courseRequestsQueryService;

    public CourseRequestsResource(CourseRequestsService courseRequestsService, CourseRequestsQueryService courseRequestsQueryService) {
        this.courseRequestsService = courseRequestsService;
        this.courseRequestsQueryService = courseRequestsQueryService;
    }

    /**
     * {@code POST  /course-requests} : Create a new courseRequests.
     *
     * @param courseRequestsDTO the courseRequestsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new courseRequestsDTO, or with status {@code 400 (Bad Request)} if the courseRequests has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/course-requests")
    public ResponseEntity<CourseRequestsDTO> createCourseRequests(@Valid @RequestBody CourseRequestsDTO courseRequestsDTO) throws URISyntaxException {
        log.debug("REST request to save CourseRequests : {}", courseRequestsDTO);
        if (courseRequestsDTO.getId() != null) {
            throw new BadRequestAlertException("A new courseRequests cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CourseRequestsDTO result = courseRequestsService.save(courseRequestsDTO);
        return ResponseEntity.created(new URI("/api/course-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /course-requests} : Updates an existing courseRequests.
     *
     * @param courseRequestsDTO the courseRequestsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courseRequestsDTO,
     * or with status {@code 400 (Bad Request)} if the courseRequestsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the courseRequestsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/course-requests")
    public ResponseEntity<CourseRequestsDTO> updateCourseRequests(@Valid @RequestBody CourseRequestsDTO courseRequestsDTO) throws URISyntaxException {
        log.debug("REST request to update CourseRequests : {}", courseRequestsDTO);
        if (courseRequestsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CourseRequestsDTO result = courseRequestsService.save(courseRequestsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courseRequestsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /course-requests} : get all the courseRequests.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of courseRequests in body.
     */
    @GetMapping("/course-requests")
    public ResponseEntity<List<CourseRequestsDTO>> getAllCourseRequests(CourseRequestsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CourseRequests by criteria: {}", criteria);
        Page<CourseRequestsDTO> page = courseRequestsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /course-requests/count} : count all the courseRequests.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/course-requests/count")
    public ResponseEntity<Long> countCourseRequests(CourseRequestsCriteria criteria) {
        log.debug("REST request to count CourseRequests by criteria: {}", criteria);
        return ResponseEntity.ok().body(courseRequestsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /course-requests/:id} : get the "id" courseRequests.
     *
     * @param id the id of the courseRequestsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the courseRequestsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/course-requests/{id}")
    public ResponseEntity<CourseRequestsDTO> getCourseRequests(@PathVariable Long id) {
        log.debug("REST request to get CourseRequests : {}", id);
        Optional<CourseRequestsDTO> courseRequestsDTO = courseRequestsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(courseRequestsDTO);
    }

    /**
     * {@code DELETE  /course-requests/:id} : delete the "id" courseRequests.
     *
     * @param id the id of the courseRequestsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/course-requests/{id}")
    public ResponseEntity<Void> deleteCourseRequests(@PathVariable Long id) {
        log.debug("REST request to delete CourseRequests : {}", id);
        courseRequestsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
