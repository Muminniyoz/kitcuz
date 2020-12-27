package uz.kitc.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import uz.kitc.web.rest.TestUtil;

public class AbilityStudentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AbilityStudent.class);
        AbilityStudent abilityStudent1 = new AbilityStudent();
        abilityStudent1.setId(1L);
        AbilityStudent abilityStudent2 = new AbilityStudent();
        abilityStudent2.setId(abilityStudent1.getId());
        assertThat(abilityStudent1).isEqualTo(abilityStudent2);
        abilityStudent2.setId(2L);
        assertThat(abilityStudent1).isNotEqualTo(abilityStudent2);
        abilityStudent1.setId(null);
        assertThat(abilityStudent1).isNotEqualTo(abilityStudent2);
    }
}
