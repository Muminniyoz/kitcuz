package uz.kitc.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import uz.kitc.web.rest.TestUtil;

public class CourseRequestsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourseRequestsDTO.class);
        CourseRequestsDTO courseRequestsDTO1 = new CourseRequestsDTO();
        courseRequestsDTO1.setId(1L);
        CourseRequestsDTO courseRequestsDTO2 = new CourseRequestsDTO();
        assertThat(courseRequestsDTO1).isNotEqualTo(courseRequestsDTO2);
        courseRequestsDTO2.setId(courseRequestsDTO1.getId());
        assertThat(courseRequestsDTO1).isEqualTo(courseRequestsDTO2);
        courseRequestsDTO2.setId(2L);
        assertThat(courseRequestsDTO1).isNotEqualTo(courseRequestsDTO2);
        courseRequestsDTO1.setId(null);
        assertThat(courseRequestsDTO1).isNotEqualTo(courseRequestsDTO2);
    }
}
