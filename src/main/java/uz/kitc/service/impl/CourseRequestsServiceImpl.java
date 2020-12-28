package uz.kitc.service.impl;

import uz.kitc.service.CourseRequestsService;
import uz.kitc.domain.CourseRequests;
import uz.kitc.repository.CourseRequestsRepository;
import uz.kitc.service.dto.CourseRequestsDTO;
import uz.kitc.service.mapper.CourseRequestsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CourseRequests}.
 */
@Service
@Transactional
public class CourseRequestsServiceImpl implements CourseRequestsService {

    private final Logger log = LoggerFactory.getLogger(CourseRequestsServiceImpl.class);

    private final CourseRequestsRepository courseRequestsRepository;

    private final CourseRequestsMapper courseRequestsMapper;

    public CourseRequestsServiceImpl(CourseRequestsRepository courseRequestsRepository, CourseRequestsMapper courseRequestsMapper) {
        this.courseRequestsRepository = courseRequestsRepository;
        this.courseRequestsMapper = courseRequestsMapper;
    }

    @Override
    public CourseRequestsDTO save(CourseRequestsDTO courseRequestsDTO) {
        log.debug("Request to save CourseRequests : {}", courseRequestsDTO);
        CourseRequests courseRequests = courseRequestsMapper.toEntity(courseRequestsDTO);
        courseRequests = courseRequestsRepository.save(courseRequests);
        return courseRequestsMapper.toDto(courseRequests);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourseRequestsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CourseRequests");
        return courseRequestsRepository.findAll(pageable)
            .map(courseRequestsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CourseRequestsDTO> findOne(Long id) {
        log.debug("Request to get CourseRequests : {}", id);
        return courseRequestsRepository.findById(id)
            .map(courseRequestsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CourseRequests : {}", id);
        courseRequestsRepository.deleteById(id);
    }
}
