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

import uz.kitc.domain.Projects;
import uz.kitc.domain.*; // for static metamodels
import uz.kitc.repository.ProjectsRepository;
import uz.kitc.service.dto.ProjectsCriteria;
import uz.kitc.service.dto.ProjectsDTO;
import uz.kitc.service.mapper.ProjectsMapper;

/**
 * Service for executing complex queries for {@link Projects} entities in the database.
 * The main input is a {@link ProjectsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectsDTO} or a {@link Page} of {@link ProjectsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectsQueryService extends QueryService<Projects> {

    private final Logger log = LoggerFactory.getLogger(ProjectsQueryService.class);

    private final ProjectsRepository projectsRepository;

    private final ProjectsMapper projectsMapper;

    public ProjectsQueryService(ProjectsRepository projectsRepository, ProjectsMapper projectsMapper) {
        this.projectsRepository = projectsRepository;
        this.projectsMapper = projectsMapper;
    }

    /**
     * Return a {@link List} of {@link ProjectsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectsDTO> findByCriteria(ProjectsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Projects> specification = createSpecification(criteria);
        return projectsMapper.toDto(projectsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProjectsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectsDTO> findByCriteria(ProjectsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Projects> specification = createSpecification(criteria);
        return projectsRepository.findAll(specification, page)
            .map(projectsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Projects> specification = createSpecification(criteria);
        return projectsRepository.count(specification);
    }

    /**
     * Function to convert {@link ProjectsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Projects> createSpecification(ProjectsCriteria criteria) {
        Specification<Projects> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Projects_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Projects_.title));
            }
            if (criteria.getFileUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFileUrl(), Projects_.fileUrl));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Projects_.createdDate));
            }
            if (criteria.getIsShowing() != null) {
                specification = specification.and(buildSpecification(criteria.getIsShowing(), Projects_.isShowing));
            }
        }
        return specification;
    }
}
