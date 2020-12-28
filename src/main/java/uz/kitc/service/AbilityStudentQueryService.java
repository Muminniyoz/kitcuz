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

import uz.kitc.domain.AbilityStudent;
import uz.kitc.domain.*; // for static metamodels
import uz.kitc.repository.AbilityStudentRepository;
import uz.kitc.service.dto.AbilityStudentCriteria;
import uz.kitc.service.dto.AbilityStudentDTO;
import uz.kitc.service.mapper.AbilityStudentMapper;

/**
 * Service for executing complex queries for {@link AbilityStudent} entities in the database.
 * The main input is a {@link AbilityStudentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AbilityStudentDTO} or a {@link Page} of {@link AbilityStudentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AbilityStudentQueryService extends QueryService<AbilityStudent> {

    private final Logger log = LoggerFactory.getLogger(AbilityStudentQueryService.class);

    private final AbilityStudentRepository abilityStudentRepository;

    private final AbilityStudentMapper abilityStudentMapper;

    public AbilityStudentQueryService(AbilityStudentRepository abilityStudentRepository, AbilityStudentMapper abilityStudentMapper) {
        this.abilityStudentRepository = abilityStudentRepository;
        this.abilityStudentMapper = abilityStudentMapper;
    }

    /**
     * Return a {@link List} of {@link AbilityStudentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AbilityStudentDTO> findByCriteria(AbilityStudentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AbilityStudent> specification = createSpecification(criteria);
        return abilityStudentMapper.toDto(abilityStudentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AbilityStudentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AbilityStudentDTO> findByCriteria(AbilityStudentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AbilityStudent> specification = createSpecification(criteria);
        return abilityStudentRepository.findAll(specification, page)
            .map(abilityStudentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AbilityStudentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AbilityStudent> specification = createSpecification(criteria);
        return abilityStudentRepository.count(specification);
    }

    /**
     * Function to convert {@link AbilityStudentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AbilityStudent> createSpecification(AbilityStudentCriteria criteria) {
        Specification<AbilityStudent> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AbilityStudent_.id));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), AbilityStudent_.firstName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), AbilityStudent_.lastName));
            }
            if (criteria.getMiddleName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMiddleName(), AbilityStudent_.middleName));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), AbilityStudent_.email));
            }
            if (criteria.getDateOfBirth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateOfBirth(), AbilityStudent_.dateOfBirth));
            }
            if (criteria.getRegisterationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRegisterationDate(), AbilityStudent_.registerationDate));
            }
            if (criteria.getTelephone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelephone(), AbilityStudent_.telephone));
            }
            if (criteria.getMobile() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMobile(), AbilityStudent_.mobile));
            }
            if (criteria.getThumbnailPhotoUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getThumbnailPhotoUrl(), AbilityStudent_.thumbnailPhotoUrl));
            }
            if (criteria.getFullPhotoUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFullPhotoUrl(), AbilityStudent_.fullPhotoUrl));
            }
            if (criteria.getIsShowing() != null) {
                specification = specification.and(buildSpecification(criteria.getIsShowing(), AbilityStudent_.isShowing));
            }
        }
        return specification;
    }
}
