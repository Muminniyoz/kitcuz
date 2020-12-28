package uz.kitc.service.mapper;


import uz.kitc.domain.*;
import uz.kitc.service.dto.PlanningDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Planning} and its DTO {@link PlanningDTO}.
 */
@Mapper(componentModel = "spring", uses = {CoursesMapper.class, TeacherMapper.class})
public interface PlanningMapper extends EntityMapper<PlanningDTO, Planning> {

    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "teacher.id", target = "teacherId")
    PlanningDTO toDto(Planning planning);

    @Mapping(source = "courseId", target = "course")
    @Mapping(source = "teacherId", target = "teacher")
    Planning toEntity(PlanningDTO planningDTO);

    default Planning fromId(Long id) {
        if (id == null) {
            return null;
        }
        Planning planning = new Planning();
        planning.setId(id);
        return planning;
    }
}
