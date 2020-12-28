package uz.kitc.service;

import uz.kitc.service.dto.StudentGroupDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link uz.kitc.domain.StudentGroup}.
 */
public interface StudentGroupService {

    /**
     * Save a studentGroup.
     *
     * @param studentGroupDTO the entity to save.
     * @return the persisted entity.
     */
    StudentGroupDTO save(StudentGroupDTO studentGroupDTO);

    /**
     * Get all the studentGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StudentGroupDTO> findAll(Pageable pageable);


    /**
     * Get the "id" studentGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StudentGroupDTO> findOne(Long id);

    /**
     * Delete the "id" studentGroup.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
