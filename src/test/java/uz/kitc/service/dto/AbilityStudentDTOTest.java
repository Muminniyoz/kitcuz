package uz.kitc.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import uz.kitc.web.rest.TestUtil;

public class AbilityStudentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AbilityStudentDTO.class);
        AbilityStudentDTO abilityStudentDTO1 = new AbilityStudentDTO();
        abilityStudentDTO1.setId(1L);
        AbilityStudentDTO abilityStudentDTO2 = new AbilityStudentDTO();
        assertThat(abilityStudentDTO1).isNotEqualTo(abilityStudentDTO2);
        abilityStudentDTO2.setId(abilityStudentDTO1.getId());
        assertThat(abilityStudentDTO1).isEqualTo(abilityStudentDTO2);
        abilityStudentDTO2.setId(2L);
        assertThat(abilityStudentDTO1).isNotEqualTo(abilityStudentDTO2);
        abilityStudentDTO1.setId(null);
        assertThat(abilityStudentDTO1).isNotEqualTo(abilityStudentDTO2);
    }
}
