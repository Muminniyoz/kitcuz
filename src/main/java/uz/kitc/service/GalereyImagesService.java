package uz.kitc.service;

import uz.kitc.service.dto.GalereyImagesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link uz.kitc.domain.GalereyImages}.
 */
public interface GalereyImagesService {

    /**
     * Save a galereyImages.
     *
     * @param galereyImagesDTO the entity to save.
     * @return the persisted entity.
     */
    GalereyImagesDTO save(GalereyImagesDTO galereyImagesDTO);

    /**
     * Get all the galereyImages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GalereyImagesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" galereyImages.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GalereyImagesDTO> findOne(Long id);

    /**
     * Delete the "id" galereyImages.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
