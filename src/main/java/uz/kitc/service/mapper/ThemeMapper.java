package uz.kitc.service.mapper;


import uz.kitc.domain.*;
import uz.kitc.service.dto.ThemeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Theme} and its DTO {@link ThemeDTO}.
 */
@Mapper(componentModel = "spring", uses = {PlanningMapper.class})
public interface ThemeMapper extends EntityMapper<ThemeDTO, Theme> {

    @Mapping(source = "planning.id", target = "planningId")
    ThemeDTO toDto(Theme theme);

    @Mapping(source = "planningId", target = "planning")
    Theme toEntity(ThemeDTO themeDTO);

    default Theme fromId(Long id) {
        if (id == null) {
            return null;
        }
        Theme theme = new Theme();
        theme.setId(id);
        return theme;
    }
}
