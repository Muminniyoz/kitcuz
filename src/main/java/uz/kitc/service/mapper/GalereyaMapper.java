package uz.kitc.service.mapper;


import uz.kitc.domain.*;
import uz.kitc.service.dto.GalereyaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Galereya} and its DTO {@link GalereyaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GalereyaMapper extends EntityMapper<GalereyaDTO, Galereya> {



    default Galereya fromId(Long id) {
        if (id == null) {
            return null;
        }
        Galereya galereya = new Galereya();
        galereya.setId(id);
        return galereya;
    }
}
