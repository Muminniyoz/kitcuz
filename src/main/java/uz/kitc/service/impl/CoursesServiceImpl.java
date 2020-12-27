package uz.kitc.service.impl;

import uz.kitc.service.CoursesService;
import uz.kitc.domain.Courses;
import uz.kitc.repository.CoursesRepository;
import uz.kitc.service.dto.CoursesDTO;
import uz.kitc.service.mapper.CoursesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Courses}.
 */
@Service
@Transactional
public class CoursesServiceImpl implements CoursesService {

    private final Logger log = LoggerFactory.getLogger(CoursesServiceImpl.class);

    private final CoursesRepository coursesRepository;

    private final CoursesMapper coursesMapper;

    public CoursesServiceImpl(CoursesRepository coursesRepository, CoursesMapper coursesMapper) {
        this.coursesRepository = coursesRepository;
        this.coursesMapper = coursesMapper;
    }

    @Override
    public CoursesDTO save(CoursesDTO coursesDTO) {
        log.debug("Request to save Courses : {}", coursesDTO);
        Courses courses = coursesMapper.toEntity(coursesDTO);
        courses = coursesRepository.save(courses);
        return coursesMapper.toDto(courses);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CoursesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Courses");
        return coursesRepository.findAll(pageable)
            .map(coursesMapper::toDto);
    }


    public Page<CoursesDTO> findAllWithEagerRelationships(Pageable pageable) {
        return coursesRepository.findAllWithEagerRelationships(pageable).map(coursesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CoursesDTO> findOne(Long id) {
        log.debug("Request to get Courses : {}", id);
        return coursesRepository.findOneWithEagerRelationships(id)
            .map(coursesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Courses : {}", id);
        coursesRepository.deleteById(id);
    }
}
