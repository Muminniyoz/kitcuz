package uz.kitc.service;

import uz.kitc.service.dto.GalereyaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link uz.kitc.domain.Galereya}.
 */
public interface GalereyaService {

    /**
     * Save a galereya.
     *
     * @param galereyaDTO the entity to save.
     * @return the persisted entity.
     */
    GalereyaDTO save(GalereyaDTO galereyaDTO);

    /**
     * Get all the galereyas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GalereyaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" galereya.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GalereyaDTO> findOne(Long id);

    /**
     * Delete the "id" galereya.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
