package uz.kitc.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import uz.kitc.web.rest.TestUtil;

public class CourseGroupTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourseGroup.class);
        CourseGroup courseGroup1 = new CourseGroup();
        courseGroup1.setId(1L);
        CourseGroup courseGroup2 = new CourseGroup();
        courseGroup2.setId(courseGroup1.getId());
        assertThat(courseGroup1).isEqualTo(courseGroup2);
        courseGroup2.setId(2L);
        assertThat(courseGroup1).isNotEqualTo(courseGroup2);
        courseGroup1.setId(null);
        assertThat(courseGroup1).isNotEqualTo(courseGroup2);
    }
}
