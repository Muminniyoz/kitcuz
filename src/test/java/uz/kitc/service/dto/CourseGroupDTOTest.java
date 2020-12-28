package uz.kitc.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import uz.kitc.web.rest.TestUtil;

public class CourseGroupDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourseGroupDTO.class);
        CourseGroupDTO courseGroupDTO1 = new CourseGroupDTO();
        courseGroupDTO1.setId(1L);
        CourseGroupDTO courseGroupDTO2 = new CourseGroupDTO();
        assertThat(courseGroupDTO1).isNotEqualTo(courseGroupDTO2);
        courseGroupDTO2.setId(courseGroupDTO1.getId());
        assertThat(courseGroupDTO1).isEqualTo(courseGroupDTO2);
        courseGroupDTO2.setId(2L);
        assertThat(courseGroupDTO1).isNotEqualTo(courseGroupDTO2);
        courseGroupDTO1.setId(null);
        assertThat(courseGroupDTO1).isNotEqualTo(courseGroupDTO2);
    }
}
