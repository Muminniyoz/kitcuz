package uz.kitc.service;

import uz.kitc.service.dto.ThemeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link uz.kitc.domain.Theme}.
 */
public interface ThemeService {

    /**
     * Save a theme.
     *
     * @param themeDTO the entity to save.
     * @return the persisted entity.
     */
    ThemeDTO save(ThemeDTO themeDTO);

    /**
     * Get all the themes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ThemeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" theme.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ThemeDTO> findOne(Long id);

    /**
     * Delete the "id" theme.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
