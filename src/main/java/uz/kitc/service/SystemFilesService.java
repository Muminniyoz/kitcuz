package uz.kitc.service;

import uz.kitc.service.dto.SystemFilesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link uz.kitc.domain.SystemFiles}.
 */
public interface SystemFilesService {

    /**
     * Save a systemFiles.
     *
     * @param systemFilesDTO the entity to save.
     * @return the persisted entity.
     */
    SystemFilesDTO save(SystemFilesDTO systemFilesDTO);

    /**
     * Get all the systemFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SystemFilesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" systemFiles.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SystemFilesDTO> findOne(Long id);

    /**
     * Delete the "id" systemFiles.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
