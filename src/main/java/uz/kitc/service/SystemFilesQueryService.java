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

import uz.kitc.domain.SystemFiles;
import uz.kitc.domain.*; // for static metamodels
import uz.kitc.repository.SystemFilesRepository;
import uz.kitc.service.dto.SystemFilesCriteria;
import uz.kitc.service.dto.SystemFilesDTO;
import uz.kitc.service.mapper.SystemFilesMapper;

/**
 * Service for executing complex queries for {@link SystemFiles} entities in the database.
 * The main input is a {@link SystemFilesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SystemFilesDTO} or a {@link Page} of {@link SystemFilesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SystemFilesQueryService extends QueryService<SystemFiles> {

    private final Logger log = LoggerFactory.getLogger(SystemFilesQueryService.class);

    private final SystemFilesRepository systemFilesRepository;

    private final SystemFilesMapper systemFilesMapper;

    public SystemFilesQueryService(SystemFilesRepository systemFilesRepository, SystemFilesMapper systemFilesMapper) {
        this.systemFilesRepository = systemFilesRepository;
        this.systemFilesMapper = systemFilesMapper;
    }

    /**
     * Return a {@link List} of {@link SystemFilesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SystemFilesDTO> findByCriteria(SystemFilesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SystemFiles> specification = createSpecification(criteria);
        return systemFilesMapper.toDto(systemFilesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SystemFilesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SystemFilesDTO> findByCriteria(SystemFilesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SystemFiles> specification = createSpecification(criteria);
        return systemFilesRepository.findAll(specification, page)
            .map(systemFilesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SystemFilesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SystemFiles> specification = createSpecification(criteria);
        return systemFilesRepository.count(specification);
    }

    /**
     * Function to convert {@link SystemFilesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SystemFiles> createSpecification(SystemFilesCriteria criteria) {
        Specification<SystemFiles> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SystemFiles_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), SystemFiles_.name));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), SystemFiles_.type));
            }
            if (criteria.getTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTime(), SystemFiles_.time));
            }
        }
        return specification;
    }
}
