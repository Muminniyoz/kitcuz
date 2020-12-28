package uz.kitc.service.impl;

import uz.kitc.service.GalereyaService;
import uz.kitc.domain.Galereya;
import uz.kitc.repository.GalereyaRepository;
import uz.kitc.service.dto.GalereyaDTO;
import uz.kitc.service.mapper.GalereyaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Galereya}.
 */
@Service
@Transactional
public class GalereyaServiceImpl implements GalereyaService {

    private final Logger log = LoggerFactory.getLogger(GalereyaServiceImpl.class);

    private final GalereyaRepository galereyaRepository;

    private final GalereyaMapper galereyaMapper;

    public GalereyaServiceImpl(GalereyaRepository galereyaRepository, GalereyaMapper galereyaMapper) {
        this.galereyaRepository = galereyaRepository;
        this.galereyaMapper = galereyaMapper;
    }

    @Override
    public GalereyaDTO save(GalereyaDTO galereyaDTO) {
        log.debug("Request to save Galereya : {}", galereyaDTO);
        Galereya galereya = galereyaMapper.toEntity(galereyaDTO);
        galereya = galereyaRepository.save(galereya);
        return galereyaMapper.toDto(galereya);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GalereyaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Galereyas");
        return galereyaRepository.findAll(pageable)
            .map(galereyaMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<GalereyaDTO> findOne(Long id) {
        log.debug("Request to get Galereya : {}", id);
        return galereyaRepository.findById(id)
            .map(galereyaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Galereya : {}", id);
        galereyaRepository.deleteById(id);
    }
}
