package uz.kitc.service.mapper;


import uz.kitc.domain.*;
import uz.kitc.service.dto.StudentGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link StudentGroup} and its DTO {@link StudentGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {StudentMapper.class, CourseGroupMapper.class})
public interface StudentGroupMapper extends EntityMapper<StudentGroupDTO, StudentGroup> {

    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "group.id", target = "groupId")
    StudentGroupDTO toDto(StudentGroup studentGroup);

    @Mapping(source = "studentId", target = "student")
    @Mapping(source = "groupId", target = "group")
    StudentGroup toEntity(StudentGroupDTO studentGroupDTO);

    default StudentGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        StudentGroup studentGroup = new StudentGroup();
        studentGroup.setId(id);
        return studentGroup;
    }
}
