package uz.kitc.service.mapper;


import uz.kitc.domain.*;
import uz.kitc.service.dto.CoursesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Courses} and its DTO {@link CoursesDTO}.
 */
@Mapper(componentModel = "spring", uses = {SkillMapper.class})
public interface CoursesMapper extends EntityMapper<CoursesDTO, Courses> {


    @Mapping(target = "removeSkills", ignore = true)

    default Courses fromId(Long id) {
        if (id == null) {
            return null;
        }
        Courses courses = new Courses();
        courses.setId(id);
        return courses;
    }
}
