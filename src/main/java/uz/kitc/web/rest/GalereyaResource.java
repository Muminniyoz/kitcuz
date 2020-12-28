package uz.kitc.web.rest;

import uz.kitc.service.GalereyaService;
import uz.kitc.web.rest.errors.BadRequestAlertException;
import uz.kitc.service.dto.GalereyaDTO;
import uz.kitc.service.dto.GalereyaCriteria;
import uz.kitc.service.GalereyaQueryService;

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
 * REST controller for managing {@link uz.kitc.domain.Galereya}.
 */
@RestController
@RequestMapping("/api")
public class GalereyaResource {

    private final Logger log = LoggerFactory.getLogger(GalereyaResource.class);

    private static final String ENTITY_NAME = "galereya";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GalereyaService galereyaService;

    private final GalereyaQueryService galereyaQueryService;

    public GalereyaResource(GalereyaService galereyaService, GalereyaQueryService galereyaQueryService) {
        this.galereyaService = galereyaService;
        this.galereyaQueryService = galereyaQueryService;
    }

    /**
     * {@code POST  /galereyas} : Create a new galereya.
     *
     * @param galereyaDTO the galereyaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new galereyaDTO, or with status {@code 400 (Bad Request)} if the galereya has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/galereyas")
    public ResponseEntity<GalereyaDTO> createGalereya(@RequestBody GalereyaDTO galereyaDTO) throws URISyntaxException {
        log.debug("REST request to save Galereya : {}", galereyaDTO);
        if (galereyaDTO.getId() != null) {
            throw new BadRequestAlertException("A new galereya cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GalereyaDTO result = galereyaService.save(galereyaDTO);
        return ResponseEntity.created(new URI("/api/galereyas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /galereyas} : Updates an existing galereya.
     *
     * @param galereyaDTO the galereyaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated galereyaDTO,
     * or with status {@code 400 (Bad Request)} if the galereyaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the galereyaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/galereyas")
    public ResponseEntity<GalereyaDTO> updateGalereya(@RequestBody GalereyaDTO galereyaDTO) throws URISyntaxException {
        log.debug("REST request to update Galereya : {}", galereyaDTO);
        if (galereyaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GalereyaDTO result = galereyaService.save(galereyaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, galereyaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /galereyas} : get all the galereyas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of galereyas in body.
     */
    @GetMapping("/galereyas")
    public ResponseEntity<List<GalereyaDTO>> getAllGalereyas(GalereyaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Galereyas by criteria: {}", criteria);
        Page<GalereyaDTO> page = galereyaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /galereyas/count} : count all the galereyas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/galereyas/count")
    public ResponseEntity<Long> countGalereyas(GalereyaCriteria criteria) {
        log.debug("REST request to count Galereyas by criteria: {}", criteria);
        return ResponseEntity.ok().body(galereyaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /galereyas/:id} : get the "id" galereya.
     *
     * @param id the id of the galereyaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the galereyaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/galereyas/{id}")
    public ResponseEntity<GalereyaDTO> getGalereya(@PathVariable Long id) {
        log.debug("REST request to get Galereya : {}", id);
        Optional<GalereyaDTO> galereyaDTO = galereyaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(galereyaDTO);
    }

    /**
     * {@code DELETE  /galereyas/:id} : delete the "id" galereya.
     *
     * @param id the id of the galereyaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/galereyas/{id}")
    public ResponseEntity<Void> deleteGalereya(@PathVariable Long id) {
        log.debug("REST request to delete Galereya : {}", id);
        galereyaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
