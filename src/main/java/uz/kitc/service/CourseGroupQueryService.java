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

import uz.kitc.domain.CourseGroup;
import uz.kitc.domain.*; // for static metamodels
import uz.kitc.repository.CourseGroupRepository;
import uz.kitc.service.dto.CourseGroupCriteria;
import uz.kitc.service.dto.CourseGroupDTO;
import uz.kitc.service.mapper.CourseGroupMapper;

/**
 * Service for executing complex queries for {@link CourseGroup} entities in the database.
 * The main input is a {@link CourseGroupCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CourseGroupDTO} or a {@link Page} of {@link CourseGroupDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CourseGroupQueryService extends QueryService<CourseGroup> {

    private final Logger log = LoggerFactory.getLogger(CourseGroupQueryService.class);

    private final CourseGroupRepository courseGroupRepository;

    private final CourseGroupMapper courseGroupMapper;

    public CourseGroupQueryService(CourseGroupRepository courseGroupRepository, CourseGroupMapper courseGroupMapper) {
        this.courseGroupRepository = courseGroupRepository;
        this.courseGroupMapper = courseGroupMapper;
    }

    /**
     * Return a {@link List} of {@link CourseGroupDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CourseGroupDTO> findByCriteria(CourseGroupCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CourseGroup> specification = createSpecification(criteria);
        return courseGroupMapper.toDto(courseGroupRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CourseGroupDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CourseGroupDTO> findByCriteria(CourseGroupCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CourseGroup> specification = createSpecification(criteria);
        return courseGroupRepository.findAll(specification, page)
            .map(courseGroupMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CourseGroupCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CourseGroup> specification = createSpecification(criteria);
        return courseGroupRepository.count(specification);
    }

    /**
     * Function to convert {@link CourseGroupCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CourseGroup> createSpecification(CourseGroupCriteria criteria) {
        Specification<CourseGroup> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CourseGroup_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CourseGroup_.name));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), CourseGroup_.startDate));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), CourseGroup_.status));
            }
            if (criteria.getTeacherId() != null) {
                specification = specification.and(buildSpecification(criteria.getTeacherId(),
                    root -> root.join(CourseGroup_.teacher, JoinType.LEFT).get(Teacher_.id)));
            }
            if (criteria.getPlanningId() != null) {
                specification = specification.and(buildSpecification(criteria.getPlanningId(),
                    root -> root.join(CourseGroup_.planning, JoinType.LEFT).get(Planning_.id)));
            }
        }
        return specification;
    }
}
