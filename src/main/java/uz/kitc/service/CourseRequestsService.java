package uz.kitc.service;

import uz.kitc.service.dto.CourseRequestsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link uz.kitc.domain.CourseRequests}.
 */
public interface CourseRequestsService {

    /**
     * Save a courseRequests.
     *
     * @param courseRequestsDTO the entity to save.
     * @return the persisted entity.
     */
    CourseRequestsDTO save(CourseRequestsDTO courseRequestsDTO);

    /**
     * Get all the courseRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CourseRequestsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" courseRequests.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CourseRequestsDTO> findOne(Long id);

    /**
     * Delete the "id" courseRequests.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
