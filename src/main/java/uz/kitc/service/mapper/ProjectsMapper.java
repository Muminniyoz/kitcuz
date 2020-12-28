package uz.kitc.service.mapper;


import uz.kitc.domain.*;
import uz.kitc.service.dto.ProjectsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Projects} and its DTO {@link ProjectsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProjectsMapper extends EntityMapper<ProjectsDTO, Projects> {



    default Projects fromId(Long id) {
        if (id == null) {
            return null;
        }
        Projects projects = new Projects();
        projects.setId(id);
        return projects;
    }
}
