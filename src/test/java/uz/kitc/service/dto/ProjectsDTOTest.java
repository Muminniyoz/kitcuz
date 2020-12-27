package uz.kitc.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import uz.kitc.web.rest.TestUtil;

public class ProjectsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectsDTO.class);
        ProjectsDTO projectsDTO1 = new ProjectsDTO();
        projectsDTO1.setId(1L);
        ProjectsDTO projectsDTO2 = new ProjectsDTO();
        assertThat(projectsDTO1).isNotEqualTo(projectsDTO2);
        projectsDTO2.setId(projectsDTO1.getId());
        assertThat(projectsDTO1).isEqualTo(projectsDTO2);
        projectsDTO2.setId(2L);
        assertThat(projectsDTO1).isNotEqualTo(projectsDTO2);
        projectsDTO1.setId(null);
        assertThat(projectsDTO1).isNotEqualTo(projectsDTO2);
    }
}
