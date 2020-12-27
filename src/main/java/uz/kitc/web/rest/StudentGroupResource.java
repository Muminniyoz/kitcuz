package uz.kitc.web.rest;

import uz.kitc.service.StudentGroupService;
import uz.kitc.web.rest.errors.BadRequestAlertException;
import uz.kitc.service.dto.StudentGroupDTO;
import uz.kitc.service.dto.StudentGroupCriteria;
import uz.kitc.service.StudentGroupQueryService;

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
 * REST controller for managing {@link uz.kitc.domain.StudentGroup}.
 */
@RestController
@RequestMapping("/api")
public class StudentGroupResource {

    private final Logger log = LoggerFactory.getLogger(StudentGroupResource.class);

    private static final String ENTITY_NAME = "studentGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StudentGroupService studentGroupService;

    private final StudentGroupQueryService studentGroupQueryService;

    public StudentGroupResource(StudentGroupService studentGroupService, StudentGroupQueryService studentGroupQueryService) {
        this.studentGroupService = studentGroupService;
        this.studentGroupQueryService = studentGroupQueryService;
    }

    /**
     * {@code POST  /student-groups} : Create a new studentGroup.
     *
     * @param studentGroupDTO the studentGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new studentGroupDTO, or with status {@code 400 (Bad Request)} if the studentGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/student-groups")
    public ResponseEntity<StudentGroupDTO> createStudentGroup(@RequestBody StudentGroupDTO studentGroupDTO) throws URISyntaxException {
        log.debug("REST request to save StudentGroup : {}", studentGroupDTO);
        if (studentGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new studentGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StudentGroupDTO result = studentGroupService.save(studentGroupDTO);
        return ResponseEntity.created(new URI("/api/student-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /student-groups} : Updates an existing studentGroup.
     *
     * @param studentGroupDTO the studentGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated studentGroupDTO,
     * or with status {@code 400 (Bad Request)} if the studentGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the studentGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/student-groups")
    public ResponseEntity<StudentGroupDTO> updateStudentGroup(@RequestBody StudentGroupDTO studentGroupDTO) throws URISyntaxException {
        log.debug("REST request to update StudentGroup : {}", studentGroupDTO);
        if (studentGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StudentGroupDTO result = studentGroupService.save(studentGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, studentGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /student-groups} : get all the studentGroups.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of studentGroups in body.
     */
    @GetMapping("/student-groups")
    public ResponseEntity<List<StudentGroupDTO>> getAllStudentGroups(StudentGroupCriteria criteria, Pageable pageable) {
        log.debug("REST request to get StudentGroups by criteria: {}", criteria);
        Page<StudentGroupDTO> page = studentGroupQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /student-groups/count} : count all the studentGroups.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/student-groups/count")
    public ResponseEntity<Long> countStudentGroups(StudentGroupCriteria criteria) {
        log.debug("REST request to count StudentGroups by criteria: {}", criteria);
        return ResponseEntity.ok().body(studentGroupQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /student-groups/:id} : get the "id" studentGroup.
     *
     * @param id the id of the studentGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the studentGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/student-groups/{id}")
    public ResponseEntity<StudentGroupDTO> getStudentGroup(@PathVariable Long id) {
        log.debug("REST request to get StudentGroup : {}", id);
        Optional<StudentGroupDTO> studentGroupDTO = studentGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(studentGroupDTO);
    }

    /**
     * {@code DELETE  /student-groups/:id} : delete the "id" studentGroup.
     *
     * @param id the id of the studentGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/student-groups/{id}")
    public ResponseEntity<Void> deleteStudentGroup(@PathVariable Long id) {
        log.debug("REST request to delete StudentGroup : {}", id);
        studentGroupService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
