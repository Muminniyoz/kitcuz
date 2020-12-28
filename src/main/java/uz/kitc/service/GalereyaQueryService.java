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

import uz.kitc.domain.Galereya;
import uz.kitc.domain.*; // for static metamodels
import uz.kitc.repository.GalereyaRepository;
import uz.kitc.service.dto.GalereyaCriteria;
import uz.kitc.service.dto.GalereyaDTO;
import uz.kitc.service.mapper.GalereyaMapper;

/**
 * Service for executing complex queries for {@link Galereya} entities in the database.
 * The main input is a {@link GalereyaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link GalereyaDTO} or a {@link Page} of {@link GalereyaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GalereyaQueryService extends QueryService<Galereya> {

    private final Logger log = LoggerFactory.getLogger(GalereyaQueryService.class);

    private final GalereyaRepository galereyaRepository;

    private final GalereyaMapper galereyaMapper;

    public GalereyaQueryService(GalereyaRepository galereyaRepository, GalereyaMapper galereyaMapper) {
        this.galereyaRepository = galereyaRepository;
        this.galereyaMapper = galereyaMapper;
    }

    /**
     * Return a {@link List} of {@link GalereyaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<GalereyaDTO> findByCriteria(GalereyaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Galereya> specification = createSpecification(criteria);
        return galereyaMapper.toDto(galereyaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link GalereyaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<GalereyaDTO> findByCriteria(GalereyaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Galereya> specification = createSpecification(criteria);
        return galereyaRepository.findAll(specification, page)
            .map(galereyaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(GalereyaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Galereya> specification = createSpecification(criteria);
        return galereyaRepository.count(specification);
    }

    /**
     * Function to convert {@link GalereyaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Galereya> createSpecification(GalereyaCriteria criteria) {
        Specification<Galereya> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Galereya_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Galereya_.title));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Galereya_.createdDate));
            }
        }
        return specification;
    }
}
