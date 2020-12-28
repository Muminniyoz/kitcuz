package uz.kitc.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import uz.kitc.web.rest.TestUtil;

public class CourseRequestsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourseRequests.class);
        CourseRequests courseRequests1 = new CourseRequests();
        courseRequests1.setId(1L);
        CourseRequests courseRequests2 = new CourseRequests();
        courseRequests2.setId(courseRequests1.getId());
        assertThat(courseRequests1).isEqualTo(courseRequests2);
        courseRequests2.setId(2L);
        assertThat(courseRequests1).isNotEqualTo(courseRequests2);
        courseRequests1.setId(null);
        assertThat(courseRequests1).isNotEqualTo(courseRequests2);
    }
}
