package uz.kitc.service;

import uz.kitc.service.dto.AbilityStudentDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link uz.kitc.domain.AbilityStudent}.
 */
public interface AbilityStudentService {

    /**
     * Save a abilityStudent.
     *
     * @param abilityStudentDTO the entity to save.
     * @return the persisted entity.
     */
    AbilityStudentDTO save(AbilityStudentDTO abilityStudentDTO);

    /**
     * Get all the abilityStudents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AbilityStudentDTO> findAll(Pageable pageable);


    /**
     * Get the "id" abilityStudent.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AbilityStudentDTO> findOne(Long id);

    /**
     * Delete the "id" abilityStudent.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
