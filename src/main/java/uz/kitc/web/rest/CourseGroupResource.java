package uz.kitc.web.rest;

import uz.kitc.service.CourseGroupService;
import uz.kitc.web.rest.errors.BadRequestAlertException;
import uz.kitc.service.dto.CourseGroupDTO;
import uz.kitc.service.dto.CourseGroupCriteria;
import uz.kitc.service.CourseGroupQueryService;

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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link uz.kitc.domain.CourseGroup}.
 */
@RestController
@RequestMapping("/api")
public class CourseGroupResource {

    private final Logger log = LoggerFactory.getLogger(CourseGroupResource.class);

    private static final String ENTITY_NAME = "courseGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CourseGroupService courseGroupService;

    private final CourseGroupQueryService courseGroupQueryService;

    public CourseGroupResource(CourseGroupService courseGroupService, CourseGroupQueryService courseGroupQueryService) {
        this.courseGroupService = courseGroupService;
        this.courseGroupQueryService = courseGroupQueryService;
    }

    /**
     * {@code POST  /course-groups} : Create a new courseGroup.
     *
     * @param courseGroupDTO the courseGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new courseGroupDTO, or with status {@code 400 (Bad Request)} if the courseGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/course-groups")
    public ResponseEntity<CourseGroupDTO> createCourseGroup(@RequestBody CourseGroupDTO courseGroupDTO) throws URISyntaxException {
        log.debug("REST request to save CourseGroup : {}", courseGroupDTO);
        if (courseGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new courseGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CourseGroupDTO result = courseGroupService.save(courseGroupDTO);
        return ResponseEntity.created(new URI("/api/course-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /course-groups} : Updates an existing courseGroup.
     *
     * @param courseGroupDTO the courseGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courseGroupDTO,
     * or with status {@code 400 (Bad Request)} if the courseGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the courseGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/course-groups")
    public ResponseEntity<CourseGroupDTO> updateCourseGroup(@RequestBody CourseGroupDTO courseGroupDTO) throws URISyntaxException {
        log.debug("REST request to update CourseGroup : {}", courseGroupDTO);
        if (courseGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CourseGroupDTO result = courseGroupService.save(courseGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courseGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /course-groups} : get all the courseGroups.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of courseGroups in body.
     */
    @GetMapping("/course-groups")
    public ResponseEntity<List<CourseGroupDTO>> getAllCourseGroups(CourseGroupCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CourseGroups by criteria: {}", criteria);
        Page<CourseGroupDTO> page = courseGroupQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /course-groups/count} : count all the courseGroups.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/course-groups/count")
    public ResponseEntity<Long> countCourseGroups(CourseGroupCriteria criteria) {
        log.debug("REST request to count CourseGroups by criteria: {}", criteria);
        return ResponseEntity.ok().body(courseGroupQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /course-groups/:id} : get the "id" courseGroup.
     *
     * @param id the id of the courseGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the courseGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/course-groups/{id}")
    public ResponseEntity<CourseGroupDTO> getCourseGroup(@PathVariable Long id) {
        log.debug("REST request to get CourseGroup : {}", id);
        Optional<CourseGroupDTO> courseGroupDTO = courseGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(courseGroupDTO);
    }

    /**
     * {@code DELETE  /course-groups/:id} : delete the "id" courseGroup.
     *
     * @param id the id of the courseGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/course-groups/{id}")
    public ResponseEntity<Void> deleteCourseGroup(@PathVariable Long id) {
        log.debug("REST request to delete CourseGroup : {}", id);
        courseGroupService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
