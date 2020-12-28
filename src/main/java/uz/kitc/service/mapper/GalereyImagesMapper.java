package uz.kitc.service.mapper;


import uz.kitc.domain.*;
import uz.kitc.service.dto.GalereyImagesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link GalereyImages} and its DTO {@link GalereyImagesDTO}.
 */
@Mapper(componentModel = "spring", uses = {GalereyaMapper.class})
public interface GalereyImagesMapper extends EntityMapper<GalereyImagesDTO, GalereyImages> {

    @Mapping(source = "galerey.id", target = "galereyId")
    GalereyImagesDTO toDto(GalereyImages galereyImages);

    @Mapping(source = "galereyId", target = "galerey")
    GalereyImages toEntity(GalereyImagesDTO galereyImagesDTO);

    default GalereyImages fromId(Long id) {
        if (id == null) {
            return null;
        }
        GalereyImages galereyImages = new GalereyImages();
        galereyImages.setId(id);
        return galereyImages;
    }
}
