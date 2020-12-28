package uz.kitc.service.mapper;


import uz.kitc.domain.*;
import uz.kitc.service.dto.AbilityStudentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AbilityStudent} and its DTO {@link AbilityStudentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AbilityStudentMapper extends EntityMapper<AbilityStudentDTO, AbilityStudent> {



    default AbilityStudent fromId(Long id) {
        if (id == null) {
            return null;
        }
        AbilityStudent abilityStudent = new AbilityStudent();
        abilityStudent.setId(id);
        return abilityStudent;
    }
}
