package uz.kitc.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import uz.kitc.web.rest.TestUtil;

public class CoursesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CoursesDTO.class);
        CoursesDTO coursesDTO1 = new CoursesDTO();
        coursesDTO1.setId(1L);
        CoursesDTO coursesDTO2 = new CoursesDTO();
        assertThat(coursesDTO1).isNotEqualTo(coursesDTO2);
        coursesDTO2.setId(coursesDTO1.getId());
        assertThat(coursesDTO1).isEqualTo(coursesDTO2);
        coursesDTO2.setId(2L);
        assertThat(coursesDTO1).isNotEqualTo(coursesDTO2);
        coursesDTO1.setId(null);
        assertThat(coursesDTO1).isNotEqualTo(coursesDTO2);
    }
}
