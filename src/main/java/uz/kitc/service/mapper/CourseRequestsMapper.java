package uz.kitc.service.mapper;


import uz.kitc.domain.*;
import uz.kitc.service.dto.CourseRequestsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CourseRequests} and its DTO {@link CourseRequestsDTO}.
 */
@Mapper(componentModel = "spring", uses = {CoursesMapper.class, CourseGroupMapper.class})
public interface CourseRequestsMapper extends EntityMapper<CourseRequestsDTO, CourseRequests> {

    @Mapping(source = "courses.id", target = "coursesId")
    @Mapping(source = "coursesGroup.id", target = "coursesGroupId")
    CourseRequestsDTO toDto(CourseRequests courseRequests);

    @Mapping(source = "coursesId", target = "courses")
    @Mapping(source = "coursesGroupId", target = "coursesGroup")
    CourseRequests toEntity(CourseRequestsDTO courseRequestsDTO);

    default CourseRequests fromId(Long id) {
        if (id == null) {
            return null;
        }
        CourseRequests courseRequests = new CourseRequests();
        courseRequests.setId(id);
        return courseRequests;
    }
}
