package uz.kitc.web.rest;

import uz.kitc.service.GalereyImagesService;
import uz.kitc.web.rest.errors.BadRequestAlertException;
import uz.kitc.service.dto.GalereyImagesDTO;
import uz.kitc.service.dto.GalereyImagesCriteria;
import uz.kitc.service.GalereyImagesQueryService;

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
 * REST controller for managing {@link uz.kitc.domain.GalereyImages}.
 */
@RestController
@RequestMapping("/api")
public class GalereyImagesResource {

    private final Logger log = LoggerFactory.getLogger(GalereyImagesResource.class);

    private static final String ENTITY_NAME = "galereyImages";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GalereyImagesService galereyImagesService;

    private final GalereyImagesQueryService galereyImagesQueryService;

    public GalereyImagesResource(GalereyImagesService galereyImagesService, GalereyImagesQueryService galereyImagesQueryService) {
        this.galereyImagesService = galereyImagesService;
        this.galereyImagesQueryService = galereyImagesQueryService;
    }

    /**
     * {@code POST  /galerey-images} : Create a new galereyImages.
     *
     * @param galereyImagesDTO the galereyImagesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new galereyImagesDTO, or with status {@code 400 (Bad Request)} if the galereyImages has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/galerey-images")
    public ResponseEntity<GalereyImagesDTO> createGalereyImages(@RequestBody GalereyImagesDTO galereyImagesDTO) throws URISyntaxException {
        log.debug("REST request to save GalereyImages : {}", galereyImagesDTO);
        if (galereyImagesDTO.getId() != null) {
            throw new BadRequestAlertException("A new galereyImages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GalereyImagesDTO result = galereyImagesService.save(galereyImagesDTO);
        return ResponseEntity.created(new URI("/api/galerey-images/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /galerey-images} : Updates an existing galereyImages.
     *
     * @param galereyImagesDTO the galereyImagesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated galereyImagesDTO,
     * or with status {@code 400 (Bad Request)} if the galereyImagesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the galereyImagesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/galerey-images")
    public ResponseEntity<GalereyImagesDTO> updateGalereyImages(@RequestBody GalereyImagesDTO galereyImagesDTO) throws URISyntaxException {
        log.debug("REST request to update GalereyImages : {}", galereyImagesDTO);
        if (galereyImagesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GalereyImagesDTO result = galereyImagesService.save(galereyImagesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, galereyImagesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /galerey-images} : get all the galereyImages.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of galereyImages in body.
     */
    @GetMapping("/galerey-images")
    public ResponseEntity<List<GalereyImagesDTO>> getAllGalereyImages(GalereyImagesCriteria criteria, Pageable pageable) {
        log.debug("REST request to get GalereyImages by criteria: {}", criteria);
        Page<GalereyImagesDTO> page = galereyImagesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /galerey-images/count} : count all the galereyImages.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/galerey-images/count")
    public ResponseEntity<Long> countGalereyImages(GalereyImagesCriteria criteria) {
        log.debug("REST request to count GalereyImages by criteria: {}", criteria);
        return ResponseEntity.ok().body(galereyImagesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /galerey-images/:id} : get the "id" galereyImages.
     *
     * @param id the id of the galereyImagesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the galereyImagesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/galerey-images/{id}")
    public ResponseEntity<GalereyImagesDTO> getGalereyImages(@PathVariable Long id) {
        log.debug("REST request to get GalereyImages : {}", id);
        Optional<GalereyImagesDTO> galereyImagesDTO = galereyImagesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(galereyImagesDTO);
    }

    /**
     * {@code DELETE  /galerey-images/:id} : delete the "id" galereyImages.
     *
     * @param id the id of the galereyImagesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/galerey-images/{id}")
    public ResponseEntity<Void> deleteGalereyImages(@PathVariable Long id) {
        log.debug("REST request to delete GalereyImages : {}", id);
        galereyImagesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
