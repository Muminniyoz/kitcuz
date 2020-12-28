package uz.kitc.service.impl;

import uz.kitc.service.ProjectsService;
import uz.kitc.domain.Projects;
import uz.kitc.repository.ProjectsRepository;
import uz.kitc.service.dto.ProjectsDTO;
import uz.kitc.service.mapper.ProjectsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Projects}.
 */
@Service
@Transactional
public class ProjectsServiceImpl implements ProjectsService {

    private final Logger log = LoggerFactory.getLogger(ProjectsServiceImpl.class);

    private final ProjectsRepository projectsRepository;

    private final ProjectsMapper projectsMapper;

    public ProjectsServiceImpl(ProjectsRepository projectsRepository, ProjectsMapper projectsMapper) {
        this.projectsRepository = projectsRepository;
        this.projectsMapper = projectsMapper;
    }

    @Override
    public ProjectsDTO save(ProjectsDTO projectsDTO) {
        log.debug("Request to save Projects : {}", projectsDTO);
        Projects projects = projectsMapper.toEntity(projectsDTO);
        projects = projectsRepository.save(projects);
        return projectsMapper.toDto(projects);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProjectsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Projects");
        return projectsRepository.findAll(pageable)
            .map(projectsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectsDTO> findOne(Long id) {
        log.debug("Request to get Projects : {}", id);
        return projectsRepository.findById(id)
            .map(projectsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Projects : {}", id);
        projectsRepository.deleteById(id);
    }
}
