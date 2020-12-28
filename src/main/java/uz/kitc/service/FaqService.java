package uz.kitc.service;

import uz.kitc.service.dto.FaqDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link uz.kitc.domain.Faq}.
 */
public interface FaqService {

    /**
     * Save a faq.
     *
     * @param faqDTO the entity to save.
     * @return the persisted entity.
     */
    FaqDTO save(FaqDTO faqDTO);

    /**
     * Get all the faqs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FaqDTO> findAll(Pageable pageable);


    /**
     * Get the "id" faq.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FaqDTO> findOne(Long id);

    /**
     * Delete the "id" faq.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
