package uz.kitc.web.rest;

import uz.kitc.service.AbilityStudentService;
import uz.kitc.web.rest.errors.BadRequestAlertException;
import uz.kitc.service.dto.AbilityStudentDTO;
import uz.kitc.service.dto.AbilityStudentCriteria;
import uz.kitc.service.AbilityStudentQueryService;

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
 * REST controller for managing {@link uz.kitc.domain.AbilityStudent}.
 */
@RestController
@RequestMapping("/api")
public class AbilityStudentResource {

    private final Logger log = LoggerFactory.getLogger(AbilityStudentResource.class);

    private static final String ENTITY_NAME = "abilityStudent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AbilityStudentService abilityStudentService;

    private final AbilityStudentQueryService abilityStudentQueryService;

    public AbilityStudentResource(AbilityStudentService abilityStudentService, AbilityStudentQueryService abilityStudentQueryService) {
        this.abilityStudentService = abilityStudentService;
        this.abilityStudentQueryService = abilityStudentQueryService;
    }

    /**
     * {@code POST  /ability-students} : Create a new abilityStudent.
     *
     * @param abilityStudentDTO the abilityStudentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new abilityStudentDTO, or with status {@code 400 (Bad Request)} if the abilityStudent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ability-students")
    public ResponseEntity<AbilityStudentDTO> createAbilityStudent(@Valid @RequestBody AbilityStudentDTO abilityStudentDTO) throws URISyntaxException {
        log.debug("REST request to save AbilityStudent : {}", abilityStudentDTO);
        if (abilityStudentDTO.getId() != null) {
            throw new BadRequestAlertException("A new abilityStudent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AbilityStudentDTO result = abilityStudentService.save(abilityStudentDTO);
        return ResponseEntity.created(new URI("/api/ability-students/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ability-students} : Updates an existing abilityStudent.
     *
     * @param abilityStudentDTO the abilityStudentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated abilityStudentDTO,
     * or with status {@code 400 (Bad Request)} if the abilityStudentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the abilityStudentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ability-students")
    public ResponseEntity<AbilityStudentDTO> updateAbilityStudent(@Valid @RequestBody AbilityStudentDTO abilityStudentDTO) throws URISyntaxException {
        log.debug("REST request to update AbilityStudent : {}", abilityStudentDTO);
        if (abilityStudentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AbilityStudentDTO result = abilityStudentService.save(abilityStudentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, abilityStudentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ability-students} : get all the abilityStudents.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of abilityStudents in body.
     */
    @GetMapping("/ability-students")
    public ResponseEntity<List<AbilityStudentDTO>> getAllAbilityStudents(AbilityStudentCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AbilityStudents by criteria: {}", criteria);
        Page<AbilityStudentDTO> page = abilityStudentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ability-students/count} : count all the abilityStudents.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ability-students/count")
    public ResponseEntity<Long> countAbilityStudents(AbilityStudentCriteria criteria) {
        log.debug("REST request to count AbilityStudents by criteria: {}", criteria);
        return ResponseEntity.ok().body(abilityStudentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ability-students/:id} : get the "id" abilityStudent.
     *
     * @param id the id of the abilityStudentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the abilityStudentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ability-students/{id}")
    public ResponseEntity<AbilityStudentDTO> getAbilityStudent(@PathVariable Long id) {
        log.debug("REST request to get AbilityStudent : {}", id);
        Optional<AbilityStudentDTO> abilityStudentDTO = abilityStudentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(abilityStudentDTO);
    }

    /**
     * {@code DELETE  /ability-students/:id} : delete the "id" abilityStudent.
     *
     * @param id the id of the abilityStudentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ability-students/{id}")
    public ResponseEntity<Void> deleteAbilityStudent(@PathVariable Long id) {
        log.debug("REST request to delete AbilityStudent : {}", id);
        abilityStudentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
