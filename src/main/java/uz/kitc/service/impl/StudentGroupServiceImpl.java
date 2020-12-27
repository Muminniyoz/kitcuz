package uz.kitc.service.impl;

import uz.kitc.service.StudentGroupService;
import uz.kitc.domain.StudentGroup;
import uz.kitc.repository.StudentGroupRepository;
import uz.kitc.service.dto.StudentGroupDTO;
import uz.kitc.service.mapper.StudentGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link StudentGroup}.
 */
@Service
@Transactional
public class StudentGroupServiceImpl implements StudentGroupService {

    private final Logger log = LoggerFactory.getLogger(StudentGroupServiceImpl.class);

    private final StudentGroupRepository studentGroupRepository;

    private final StudentGroupMapper studentGroupMapper;

    public StudentGroupServiceImpl(StudentGroupRepository studentGroupRepository, StudentGroupMapper studentGroupMapper) {
        this.studentGroupRepository = studentGroupRepository;
        this.studentGroupMapper = studentGroupMapper;
    }

    @Override
    public StudentGroupDTO save(StudentGroupDTO studentGroupDTO) {
        log.debug("Request to save StudentGroup : {}", studentGroupDTO);
        StudentGroup studentGroup = studentGroupMapper.toEntity(studentGroupDTO);
        studentGroup = studentGroupRepository.save(studentGroup);
        return studentGroupMapper.toDto(studentGroup);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StudentGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StudentGroups");
        return studentGroupRepository.findAll(pageable)
            .map(studentGroupMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<StudentGroupDTO> findOne(Long id) {
        log.debug("Request to get StudentGroup : {}", id);
        return studentGroupRepository.findById(id)
            .map(studentGroupMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StudentGroup : {}", id);
        studentGroupRepository.deleteById(id);
    }
}
