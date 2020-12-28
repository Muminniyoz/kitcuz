package uz.kitc.service.impl;

import uz.kitc.service.AbilityStudentService;
import uz.kitc.domain.AbilityStudent;
import uz.kitc.repository.AbilityStudentRepository;
import uz.kitc.service.dto.AbilityStudentDTO;
import uz.kitc.service.mapper.AbilityStudentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AbilityStudent}.
 */
@Service
@Transactional
public class AbilityStudentServiceImpl implements AbilityStudentService {

    private final Logger log = LoggerFactory.getLogger(AbilityStudentServiceImpl.class);

    private final AbilityStudentRepository abilityStudentRepository;

    private final AbilityStudentMapper abilityStudentMapper;

    public AbilityStudentServiceImpl(AbilityStudentRepository abilityStudentRepository, AbilityStudentMapper abilityStudentMapper) {
        this.abilityStudentRepository = abilityStudentRepository;
        this.abilityStudentMapper = abilityStudentMapper;
    }

    @Override
    public AbilityStudentDTO save(AbilityStudentDTO abilityStudentDTO) {
        log.debug("Request to save AbilityStudent : {}", abilityStudentDTO);
        AbilityStudent abilityStudent = abilityStudentMapper.toEntity(abilityStudentDTO);
        abilityStudent = abilityStudentRepository.save(abilityStudent);
        return abilityStudentMapper.toDto(abilityStudent);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AbilityStudentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AbilityStudents");
        return abilityStudentRepository.findAll(pageable)
            .map(abilityStudentMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AbilityStudentDTO> findOne(Long id) {
        log.debug("Request to get AbilityStudent : {}", id);
        return abilityStudentRepository.findById(id)
            .map(abilityStudentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AbilityStudent : {}", id);
        abilityStudentRepository.deleteById(id);
    }
}
