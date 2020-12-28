package uz.kitc.service.mapper;


import uz.kitc.domain.*;
import uz.kitc.service.dto.SystemFilesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SystemFiles} and its DTO {@link SystemFilesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SystemFilesMapper extends EntityMapper<SystemFilesDTO, SystemFiles> {



    default SystemFiles fromId(Long id) {
        if (id == null) {
            return null;
        }
        SystemFiles systemFiles = new SystemFiles();
        systemFiles.setId(id);
        return systemFiles;
    }
}
