package uz.kitc.service.mapper;


import uz.kitc.domain.*;
import uz.kitc.service.dto.CourseGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CourseGroup} and its DTO {@link CourseGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {TeacherMapper.class, PlanningMapper.class})
public interface CourseGroupMapper extends EntityMapper<CourseGroupDTO, CourseGroup> {

    @Mapping(source = "teacher.id", target = "teacherId")
    @Mapping(source = "planning.id", target = "planningId")
    CourseGroupDTO toDto(CourseGroup courseGroup);

    @Mapping(source = "teacherId", target = "teacher")
    @Mapping(source = "planningId", target = "planning")
    CourseGroup toEntity(CourseGroupDTO courseGroupDTO);

    default CourseGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        CourseGroup courseGroup = new CourseGroup();
        courseGroup.setId(id);
        return courseGroup;
    }
}
