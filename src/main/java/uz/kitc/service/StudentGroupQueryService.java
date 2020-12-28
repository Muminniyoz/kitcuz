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

import uz.kitc.domain.StudentGroup;
import uz.kitc.domain.*; // for static metamodels
import uz.kitc.repository.StudentGroupRepository;
import uz.kitc.service.dto.StudentGroupCriteria;
import uz.kitc.service.dto.StudentGroupDTO;
import uz.kitc.service.mapper.StudentGroupMapper;

/**
 * Service for executing complex queries for {@link StudentGroup} entities in the database.
 * The main input is a {@link StudentGroupCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StudentGroupDTO} or a {@link Page} of {@link StudentGroupDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StudentGroupQueryService extends QueryService<StudentGroup> {

    private final Logger log = LoggerFactory.getLogger(StudentGroupQueryService.class);

    private final StudentGroupRepository studentGroupRepository;

    private final StudentGroupMapper studentGroupMapper;

    public StudentGroupQueryService(StudentGroupRepository studentGroupRepository, StudentGroupMapper studentGroupMapper) {
        this.studentGroupRepository = studentGroupRepository;
        this.studentGroupMapper = studentGroupMapper;
    }

    /**
     * Return a {@link List} of {@link StudentGroupDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StudentGroupDTO> findByCriteria(StudentGroupCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StudentGroup> specification = createSpecification(criteria);
        return studentGroupMapper.toDto(studentGroupRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link StudentGroupDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StudentGroupDTO> findByCriteria(StudentGroupCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StudentGroup> specification = createSpecification(criteria);
        return studentGroupRepository.findAll(specification, page)
            .map(studentGroupMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StudentGroupCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StudentGroup> specification = createSpecification(criteria);
        return studentGroupRepository.count(specification);
    }

    /**
     * Function to convert {@link StudentGroupCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<StudentGroup> createSpecification(StudentGroupCriteria criteria) {
        Specification<StudentGroup> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), StudentGroup_.id));
            }
            if (criteria.getStartingDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartingDate(), StudentGroup_.startingDate));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), StudentGroup_.active));
            }
            if (criteria.getContractNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContractNumber(), StudentGroup_.contractNumber));
            }
            if (criteria.getStudentId() != null) {
                specification = specification.and(buildSpecification(criteria.getStudentId(),
                    root -> root.join(StudentGroup_.student, JoinType.LEFT).get(Student_.id)));
            }
            if (criteria.getGroupId() != null) {
                specification = specification.and(buildSpecification(criteria.getGroupId(),
                    root -> root.join(StudentGroup_.group, JoinType.LEFT).get(CourseGroup_.id)));
            }
        }
        return specification;
    }
}
