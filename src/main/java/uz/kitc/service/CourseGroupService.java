package uz.kitc.service;

import uz.kitc.service.dto.CourseGroupDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link uz.kitc.domain.CourseGroup}.
 */
public interface CourseGroupService {

    /**
     * Save a courseGroup.
     *
     * @param courseGroupDTO the entity to save.
     * @return the persisted entity.
     */
    CourseGroupDTO save(CourseGroupDTO courseGroupDTO);

    /**
     * Get all the courseGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CourseGroupDTO> findAll(Pageable pageable);


    /**
     * Get the "id" courseGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CourseGroupDTO> findOne(Long id);

    /**
     * Delete the "id" courseGroup.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
