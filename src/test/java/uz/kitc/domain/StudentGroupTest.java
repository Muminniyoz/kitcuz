package uz.kitc.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import uz.kitc.web.rest.TestUtil;

public class StudentGroupTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentGroup.class);
        StudentGroup studentGroup1 = new StudentGroup();
        studentGroup1.setId(1L);
        StudentGroup studentGroup2 = new StudentGroup();
        studentGroup2.setId(studentGroup1.getId());
        assertThat(studentGroup1).isEqualTo(studentGroup2);
        studentGroup2.setId(2L);
        assertThat(studentGroup1).isNotEqualTo(studentGroup2);
        studentGroup1.setId(null);
        assertThat(studentGroup1).isNotEqualTo(studentGroup2);
    }
}
