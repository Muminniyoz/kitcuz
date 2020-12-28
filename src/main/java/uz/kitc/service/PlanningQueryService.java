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

import uz.kitc.domain.Planning;
import uz.kitc.domain.*; // for static metamodels
import uz.kitc.repository.PlanningRepository;
import uz.kitc.service.dto.PlanningCriteria;
import uz.kitc.service.dto.PlanningDTO;
import uz.kitc.service.mapper.PlanningMapper;

/**
 * Service for executing complex queries for {@link Planning} entities in the database.
 * The main input is a {@link PlanningCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PlanningDTO} or a {@link Page} of {@link PlanningDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PlanningQueryService extends QueryService<Planning> {

    private final Logger log = LoggerFactory.getLogger(PlanningQueryService.class);

    private final PlanningRepository planningRepository;

    private final PlanningMapper planningMapper;

    public PlanningQueryService(PlanningRepository planningRepository, PlanningMapper planningMapper) {
        this.planningRepository = planningRepository;
        this.planningMapper = planningMapper;
    }

    /**
     * Return a {@link List} of {@link PlanningDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PlanningDTO> findByCriteria(PlanningCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Planning> specification = createSpecification(criteria);
        return planningMapper.toDto(planningRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PlanningDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PlanningDTO> findByCriteria(PlanningCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Planning> specification = createSpecification(criteria);
        return planningRepository.findAll(specification, page)
            .map(planningMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PlanningCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Planning> specification = createSpecification(criteria);
        return planningRepository.count(specification);
    }

    /**
     * Function to convert {@link PlanningCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Planning> createSpecification(PlanningCriteria criteria) {
        Specification<Planning> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Planning_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Planning_.name));
            }
            if (criteria.getAbout() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAbout(), Planning_.about));
            }
            if (criteria.getDuration() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDuration(), Planning_.duration));
            }
            if (criteria.getFileUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFileUrl(), Planning_.fileUrl));
            }
            if (criteria.getCourseId() != null) {
                specification = specification.and(buildSpecification(criteria.getCourseId(),
                    root -> root.join(Planning_.course, JoinType.LEFT).get(Courses_.id)));
            }
            if (criteria.getTeacherId() != null) {
                specification = specification.and(buildSpecification(criteria.getTeacherId(),
                    root -> root.join(Planning_.teacher, JoinType.LEFT).get(Teacher_.id)));
            }
        }
        return specification;
    }
}
