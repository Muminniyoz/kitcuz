package uz.kitc.service;

import uz.kitc.service.dto.ProjectsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link uz.kitc.domain.Projects}.
 */
public interface ProjectsService {

    /**
     * Save a projects.
     *
     * @param projectsDTO the entity to save.
     * @return the persisted entity.
     */
    ProjectsDTO save(ProjectsDTO projectsDTO);

    /**
     * Get all the projects.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProjectsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" projects.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProjectsDTO> findOne(Long id);

    /**
     * Delete the "id" projects.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
