package uz.kitc.web.rest;

import uz.kitc.service.PlanningService;
import uz.kitc.web.rest.errors.BadRequestAlertException;
import uz.kitc.service.dto.PlanningDTO;
import uz.kitc.service.dto.PlanningCriteria;
import uz.kitc.service.PlanningQueryService;

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
 * REST controller for managing {@link uz.kitc.domain.Planning}.
 */
@RestController
@RequestMapping("/api")
public class PlanningResource {

    private final Logger log = LoggerFactory.getLogger(PlanningResource.class);

    private static final String ENTITY_NAME = "planning";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlanningService planningService;

    private final PlanningQueryService planningQueryService;

    public PlanningResource(PlanningService planningService, PlanningQueryService planningQueryService) {
        this.planningService = planningService;
        this.planningQueryService = planningQueryService;
    }

    /**
     * {@code POST  /plannings} : Create a new planning.
     *
     * @param planningDTO the planningDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new planningDTO, or with status {@code 400 (Bad Request)} if the planning has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plannings")
    public ResponseEntity<PlanningDTO> createPlanning(@RequestBody PlanningDTO planningDTO) throws URISyntaxException {
        log.debug("REST request to save Planning : {}", planningDTO);
        if (planningDTO.getId() != null) {
            throw new BadRequestAlertException("A new planning cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlanningDTO result = planningService.save(planningDTO);
        return ResponseEntity.created(new URI("/api/plannings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plannings} : Updates an existing planning.
     *
     * @param planningDTO the planningDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planningDTO,
     * or with status {@code 400 (Bad Request)} if the planningDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the planningDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plannings")
    public ResponseEntity<PlanningDTO> updatePlanning(@RequestBody PlanningDTO planningDTO) throws URISyntaxException {
        log.debug("REST request to update Planning : {}", planningDTO);
        if (planningDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlanningDTO result = planningService.save(planningDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planningDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /plannings} : get all the plannings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plannings in body.
     */
    @GetMapping("/plannings")
    public ResponseEntity<List<PlanningDTO>> getAllPlannings(PlanningCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Plannings by criteria: {}", criteria);
        Page<PlanningDTO> page = planningQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /plannings/count} : count all the plannings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/plannings/count")
    public ResponseEntity<Long> countPlannings(PlanningCriteria criteria) {
        log.debug("REST request to count Plannings by criteria: {}", criteria);
        return ResponseEntity.ok().body(planningQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /plannings/:id} : get the "id" planning.
     *
     * @param id the id of the planningDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the planningDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plannings/{id}")
    public ResponseEntity<PlanningDTO> getPlanning(@PathVariable Long id) {
        log.debug("REST request to get Planning : {}", id);
        Optional<PlanningDTO> planningDTO = planningService.findOne(id);
        return ResponseUtil.wrapOrNotFound(planningDTO);
    }

    /**
     * {@code DELETE  /plannings/:id} : delete the "id" planning.
     *
     * @param id the id of the planningDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plannings/{id}")
    public ResponseEntity<Void> deletePlanning(@PathVariable Long id) {
        log.debug("REST request to delete Planning : {}", id);
        planningService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
