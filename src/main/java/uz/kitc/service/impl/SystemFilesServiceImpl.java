package uz.kitc.service.impl;

import uz.kitc.service.SystemFilesService;
import uz.kitc.domain.SystemFiles;
import uz.kitc.repository.SystemFilesRepository;
import uz.kitc.service.dto.SystemFilesDTO;
import uz.kitc.service.mapper.SystemFilesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SystemFiles}.
 */
@Service
@Transactional
public class SystemFilesServiceImpl implements SystemFilesService {

    private final Logger log = LoggerFactory.getLogger(SystemFilesServiceImpl.class);

    private final SystemFilesRepository systemFilesRepository;

    private final SystemFilesMapper systemFilesMapper;

    public SystemFilesServiceImpl(SystemFilesRepository systemFilesRepository, SystemFilesMapper systemFilesMapper) {
        this.systemFilesRepository = systemFilesRepository;
        this.systemFilesMapper = systemFilesMapper;
    }

    @Override
    public SystemFilesDTO save(SystemFilesDTO systemFilesDTO) {
        log.debug("Request to save SystemFiles : {}", systemFilesDTO);
        SystemFiles systemFiles = systemFilesMapper.toEntity(systemFilesDTO);
        systemFiles = systemFilesRepository.save(systemFiles);
        return systemFilesMapper.toDto(systemFiles);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SystemFilesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SystemFiles");
        return systemFilesRepository.findAll(pageable)
            .map(systemFilesMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SystemFilesDTO> findOne(Long id) {
        log.debug("Request to get SystemFiles : {}", id);
        return systemFilesRepository.findById(id)
            .map(systemFilesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SystemFiles : {}", id);
        systemFilesRepository.deleteById(id);
    }
}
