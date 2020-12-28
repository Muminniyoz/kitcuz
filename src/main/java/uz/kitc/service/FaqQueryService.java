package uz.kitc.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import uz.kitc.domain.Faq;
import uz.kitc.domain.*; // for static metamodels
import uz.kitc.repository.FaqRepository;
import uz.kitc.service.dto.FaqCriteria;
import uz.kitc.service.dto.FaqDTO;
import uz.kitc.service.mapper.FaqMapper;

/**
 * Service for executing complex queries for {@link Faq} entities in the database.
 * The main input is a {@link FaqCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FaqDTO} or a {@link Page} of {@link FaqDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FaqQueryService extends QueryService<Faq> {

    private final Logger log = LoggerFactory.getLogger(FaqQueryService.class);

    private final FaqRepository faqRepository;

    private final FaqMapper faqMapper;

    public FaqQueryService(FaqRepository faqRepository, FaqMapper faqMapper) {
        this.faqRepository = faqRepository;
        this.faqMapper = faqMapper;
    }

    /**
     * Return a {@link List} of {@link FaqDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FaqDTO> findByCriteria(FaqCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Faq> specification = createSpecification(criteria);
        return faqMapper.toDto(faqRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FaqDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FaqDTO> findByCriteria(FaqCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Faq> specification = createSpecification(criteria);
        return faqRepository.findAll(specification, page)
            .map(faqMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FaqCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Faq> specification = createSpecification(criteria);
        return faqRepository.count(specification);
    }

    /**
     * Function to convert {@link FaqCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Faq> createSpecification(FaqCriteria criteria) {
        Specification<Faq> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Faq_.id));
            }
            if (criteria.getOrderNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrderNumber(), Faq_.orderNumber));
            }
            if (criteria.getQuestion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuestion(), Faq_.question));
            }
            if (criteria.getAnswer() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAnswer(), Faq_.answer));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), Faq_.active));
            }
        }
        return specification;
    }
}
