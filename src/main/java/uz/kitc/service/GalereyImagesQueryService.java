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

import uz.kitc.domain.GalereyImages;
import uz.kitc.domain.*; // for static metamodels
import uz.kitc.repository.GalereyImagesRepository;
import uz.kitc.service.dto.GalereyImagesCriteria;
import uz.kitc.service.dto.GalereyImagesDTO;
import uz.kitc.service.mapper.GalereyImagesMapper;

/**
 * Service for executing complex queries for {@link GalereyImages} entities in the database.
 * The main input is a {@link GalereyImagesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link GalereyImagesDTO} or a {@link Page} of {@link GalereyImagesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GalereyImagesQueryService extends QueryService<GalereyImages> {

    private final Logger log = LoggerFactory.getLogger(GalereyImagesQueryService.class);

    private final GalereyImagesRepository galereyImagesRepository;

    private final GalereyImagesMapper galereyImagesMapper;

    public GalereyImagesQueryService(GalereyImagesRepository galereyImagesRepository, GalereyImagesMapper galereyImagesMapper) {
        this.galereyImagesRepository = galereyImagesRepository;
        this.galereyImagesMapper = galereyImagesMapper;
    }

    /**
     * Return a {@link List} of {@link GalereyImagesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<GalereyImagesDTO> findByCriteria(GalereyImagesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<GalereyImages> specification = createSpecification(criteria);
        return galereyImagesMapper.toDto(galereyImagesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link GalereyImagesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<GalereyImagesDTO> findByCriteria(GalereyImagesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<GalereyImages> specification = createSpecification(criteria);
        return galereyImagesRepository.findAll(specification, page)
            .map(galereyImagesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(GalereyImagesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<GalereyImages> specification = createSpecification(criteria);
        return galereyImagesRepository.count(specification);
    }

    /**
     * Function to convert {@link GalereyImagesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<GalereyImages> createSpecification(GalereyImagesCriteria criteria) {
        Specification<GalereyImages> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), GalereyImages_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), GalereyImages_.title));
            }
            if (criteria.getImageUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImageUrl(), GalereyImages_.imageUrl));
            }
            if (criteria.getNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumber(), GalereyImages_.number));
            }
            if (criteria.getGalereyId() != null) {
                specification = specification.and(buildSpecification(criteria.getGalereyId(),
                    root -> root.join(GalereyImages_.galerey, JoinType.LEFT).get(Galereya_.id)));
            }
        }
        return specification;
    }
}
