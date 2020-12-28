package uz.kitc.web.rest;

import uz.kitc.service.SystemFilesService;
import uz.kitc.web.rest.errors.BadRequestAlertException;
import uz.kitc.service.dto.SystemFilesDTO;
import uz.kitc.service.dto.SystemFilesCriteria;
import uz.kitc.service.SystemFilesQueryService;

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
 * REST controller for managing {@link uz.kitc.domain.SystemFiles}.
 */
@RestController
@RequestMapping("/api")
public class SystemFilesResource {

    private final Logger log = LoggerFactory.getLogger(SystemFilesResource.class);

    private static final String ENTITY_NAME = "systemFiles";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SystemFilesService systemFilesService;

    private final SystemFilesQueryService systemFilesQueryService;

    public SystemFilesResource(SystemFilesService systemFilesService, SystemFilesQueryService systemFilesQueryService) {
        this.systemFilesService = systemFilesService;
        this.systemFilesQueryService = systemFilesQueryService;
    }

    /**
     * {@code POST  /system-files} : Create a new systemFiles.
     *
     * @param systemFilesDTO the systemFilesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new systemFilesDTO, or with status {@code 400 (Bad Request)} if the systemFiles has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/system-files")
    public ResponseEntity<SystemFilesDTO> createSystemFiles(@Valid @RequestBody SystemFilesDTO systemFilesDTO) throws URISyntaxException {
        log.debug("REST request to save SystemFiles : {}", systemFilesDTO);
        if (systemFilesDTO.getId() != null) {
            throw new BadRequestAlertException("A new systemFiles cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SystemFilesDTO result = systemFilesService.save(systemFilesDTO);
        return ResponseEntity.created(new URI("/api/system-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /system-files} : Updates an existing systemFiles.
     *
     * @param systemFilesDTO the systemFilesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated systemFilesDTO,
     * or with status {@code 400 (Bad Request)} if the systemFilesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the systemFilesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/system-files")
    public ResponseEntity<SystemFilesDTO> updateSystemFiles(@Valid @RequestBody SystemFilesDTO systemFilesDTO) throws URISyntaxException {
        log.debug("REST request to update SystemFiles : {}", systemFilesDTO);
        if (systemFilesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SystemFilesDTO result = systemFilesService.save(systemFilesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, systemFilesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /system-files} : get all the systemFiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of systemFiles in body.
     */
    @GetMapping("/system-files")
    public ResponseEntity<List<SystemFilesDTO>> getAllSystemFiles(SystemFilesCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SystemFiles by criteria: {}", criteria);
        Page<SystemFilesDTO> page = systemFilesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /system-files/count} : count all the systemFiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/system-files/count")
    public ResponseEntity<Long> countSystemFiles(SystemFilesCriteria criteria) {
        log.debug("REST request to count SystemFiles by criteria: {}", criteria);
        return ResponseEntity.ok().body(systemFilesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /system-files/:id} : get the "id" systemFiles.
     *
     * @param id the id of the systemFilesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the systemFilesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/system-files/{id}")
    public ResponseEntity<SystemFilesDTO> getSystemFiles(@PathVariable Long id) {
        log.debug("REST request to get SystemFiles : {}", id);
        Optional<SystemFilesDTO> systemFilesDTO = systemFilesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(systemFilesDTO);
    }

    /**
     * {@code DELETE  /system-files/:id} : delete the "id" systemFiles.
     *
     * @param id the id of the systemFilesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/system-files/{id}")
    public ResponseEntity<Void> deleteSystemFiles(@PathVariable Long id) {
        log.debug("REST request to delete SystemFiles : {}", id);
        systemFilesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
