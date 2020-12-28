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

import uz.kitc.domain.CourseRequests;
import uz.kitc.domain.*; // for static metamodels
import uz.kitc.repository.CourseRequestsRepository;
import uz.kitc.service.dto.CourseRequestsCriteria;
import uz.kitc.service.dto.CourseRequestsDTO;
import uz.kitc.service.mapper.CourseRequestsMapper;

/**
 * Service for executing complex queries for {@link CourseRequests} entities in the database.
 * The main input is a {@link CourseRequestsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CourseRequestsDTO} or a {@link Page} of {@link CourseRequestsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CourseRequestsQueryService extends QueryService<CourseRequests> {

    private final Logger log = LoggerFactory.getLogger(CourseRequestsQueryService.class);

    private final CourseRequestsRepository courseRequestsRepository;

    private final CourseRequestsMapper courseRequestsMapper;

    public CourseRequestsQueryService(CourseRequestsRepository courseRequestsRepository, CourseRequestsMapper courseRequestsMapper) {
        this.courseRequestsRepository = courseRequestsRepository;
        this.courseRequestsMapper = courseRequestsMapper;
    }

    /**
     * Return a {@link List} of {@link CourseRequestsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CourseRequestsDTO> findByCriteria(CourseRequestsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CourseRequests> specification = createSpecification(criteria);
        return courseRequestsMapper.toDto(courseRequestsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CourseRequestsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CourseRequestsDTO> findByCriteria(CourseRequestsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CourseRequests> specification = createSpecification(criteria);
        return courseRequestsRepository.findAll(specification, page)
            .map(courseRequestsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CourseRequestsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CourseRequests> specification = createSpecification(criteria);
        return courseRequestsRepository.count(specification);
    }

    /**
     * Function to convert {@link CourseRequestsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CourseRequests> createSpecification(CourseRequestsCriteria criteria) {
        Specification<CourseRequests> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CourseRequests_.id));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), CourseRequests_.firstName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), CourseRequests_.lastName));
            }
            if (criteria.getMiddleName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMiddleName(), CourseRequests_.middleName));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), CourseRequests_.email));
            }
            if (criteria.getDateOfBirth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateOfBirth(), CourseRequests_.dateOfBirth));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildSpecification(criteria.getGender(), CourseRequests_.gender));
            }
            if (criteria.getRegisterationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRegisterationDate(), CourseRequests_.registerationDate));
            }
            if (criteria.getTelephone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelephone(), CourseRequests_.telephone));
            }
            if (criteria.getMobile() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMobile(), CourseRequests_.mobile));
            }
            if (criteria.getCoursesId() != null) {
                specification = specification.and(buildSpecification(criteria.getCoursesId(),
                    root -> root.join(CourseRequests_.courses, JoinType.LEFT).get(Courses_.id)));
            }
            if (criteria.getCoursesId() != null) {
                specification = specification.and(buildSpecification(criteria.getCoursesId(),
                    root -> root.join(CourseRequests_.courses, JoinType.LEFT).get(CourseGroup_.id)));
            }
        }
        return specification;
    }
}
