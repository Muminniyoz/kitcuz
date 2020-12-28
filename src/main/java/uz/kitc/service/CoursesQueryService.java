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

import uz.kitc.domain.Courses;
import uz.kitc.domain.*; // for static metamodels
import uz.kitc.repository.CoursesRepository;
import uz.kitc.service.dto.CoursesCriteria;
import uz.kitc.service.dto.CoursesDTO;
import uz.kitc.service.mapper.CoursesMapper;

/**
 * Service for executing complex queries for {@link Courses} entities in the database.
 * The main input is a {@link CoursesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CoursesDTO} or a {@link Page} of {@link CoursesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CoursesQueryService extends QueryService<Courses> {

    private final Logger log = LoggerFactory.getLogger(CoursesQueryService.class);

    private final CoursesRepository coursesRepository;

    private final CoursesMapper coursesMapper;

    public CoursesQueryService(CoursesRepository coursesRepository, CoursesMapper coursesMapper) {
        this.coursesRepository = coursesRepository;
        this.coursesMapper = coursesMapper;
    }

    /**
     * Return a {@link List} of {@link CoursesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CoursesDTO> findByCriteria(CoursesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Courses> specification = createSpecification(criteria);
        return coursesMapper.toDto(coursesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CoursesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CoursesDTO> findByCriteria(CoursesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Courses> specification = createSpecification(criteria);
        return coursesRepository.findAll(specification, page)
            .map(coursesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CoursesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Courses> specification = createSpecification(criteria);
        return coursesRepository.count(specification);
    }

    /**
     * Function to convert {@link CoursesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Courses> createSpecification(CoursesCriteria criteria) {
        Specification<Courses> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Courses_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Courses_.title));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), Courses_.price));
            }
            if (criteria.getImageUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImageUrl(), Courses_.imageUrl));
            }
            if (criteria.getSkillsId() != null) {
                specification = specification.and(buildSpecification(criteria.getSkillsId(),
                    root -> root.join(Courses_.skills, JoinType.LEFT).get(Skill_.id)));
            }
        }
        return specification;
    }
}
