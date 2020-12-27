package uz.kitc.service;

import uz.kitc.service.dto.CoursesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link uz.kitc.domain.Courses}.
 */
public interface CoursesService {

    /**
     * Save a courses.
     *
     * @param coursesDTO the entity to save.
     * @return the persisted entity.
     */
    CoursesDTO save(CoursesDTO coursesDTO);

    /**
     * Get all the courses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CoursesDTO> findAll(Pageable pageable);

    /**
     * Get all the courses with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<CoursesDTO> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" courses.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CoursesDTO> findOne(Long id);

    /**
     * Delete the "id" courses.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
