package uz.kitc.service.impl;

import uz.kitc.service.CourseGroupService;
import uz.kitc.domain.CourseGroup;
import uz.kitc.repository.CourseGroupRepository;
import uz.kitc.service.dto.CourseGroupDTO;
import uz.kitc.service.mapper.CourseGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CourseGroup}.
 */
@Service
@Transactional
public class CourseGroupServiceImpl implements CourseGroupService {

    private final Logger log = LoggerFactory.getLogger(CourseGroupServiceImpl.class);

    private final CourseGroupRepository courseGroupRepository;

    private final CourseGroupMapper courseGroupMapper;

    public CourseGroupServiceImpl(CourseGroupRepository courseGroupRepository, CourseGroupMapper courseGroupMapper) {
        this.courseGroupRepository = courseGroupRepository;
        this.courseGroupMapper = courseGroupMapper;
    }

    @Override
    public CourseGroupDTO save(CourseGroupDTO courseGroupDTO) {
        log.debug("Request to save CourseGroup : {}", courseGroupDTO);
        CourseGroup courseGroup = courseGroupMapper.toEntity(courseGroupDTO);
        courseGroup = courseGroupRepository.save(courseGroup);
        return courseGroupMapper.toDto(courseGroup);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourseGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CourseGroups");
        return courseGroupRepository.findAll(pageable)
            .map(courseGroupMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CourseGroupDTO> findOne(Long id) {
        log.debug("Request to get CourseGroup : {}", id);
        return courseGroupRepository.findById(id)
            .map(courseGroupMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CourseGroup : {}", id);
        courseGroupRepository.deleteById(id);
    }
}
