package uz.kitc.service.impl;

import uz.kitc.service.GalereyImagesService;
import uz.kitc.domain.GalereyImages;
import uz.kitc.repository.GalereyImagesRepository;
import uz.kitc.service.dto.GalereyImagesDTO;
import uz.kitc.service.mapper.GalereyImagesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GalereyImages}.
 */
@Service
@Transactional
public class GalereyImagesServiceImpl implements GalereyImagesService {

    private final Logger log = LoggerFactory.getLogger(GalereyImagesServiceImpl.class);

    private final GalereyImagesRepository galereyImagesRepository;

    private final GalereyImagesMapper galereyImagesMapper;

    public GalereyImagesServiceImpl(GalereyImagesRepository galereyImagesRepository, GalereyImagesMapper galereyImagesMapper) {
        this.galereyImagesRepository = galereyImagesRepository;
        this.galereyImagesMapper = galereyImagesMapper;
    }

    @Override
    public GalereyImagesDTO save(GalereyImagesDTO galereyImagesDTO) {
        log.debug("Request to save GalereyImages : {}", galereyImagesDTO);
        GalereyImages galereyImages = galereyImagesMapper.toEntity(galereyImagesDTO);
        galereyImages = galereyImagesRepository.save(galereyImages);
        return galereyImagesMapper.toDto(galereyImages);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GalereyImagesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GalereyImages");
        return galereyImagesRepository.findAll(pageable)
            .map(galereyImagesMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<GalereyImagesDTO> findOne(Long id) {
        log.debug("Request to get GalereyImages : {}", id);
        return galereyImagesRepository.findById(id)
            .map(galereyImagesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GalereyImages : {}", id);
        galereyImagesRepository.deleteById(id);
    }
}
