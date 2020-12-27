package uz.kitc.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import uz.kitc.web.rest.TestUtil;

public class SystemFilesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SystemFilesDTO.class);
        SystemFilesDTO systemFilesDTO1 = new SystemFilesDTO();
        systemFilesDTO1.setId(1L);
        SystemFilesDTO systemFilesDTO2 = new SystemFilesDTO();
        assertThat(systemFilesDTO1).isNotEqualTo(systemFilesDTO2);
        systemFilesDTO2.setId(systemFilesDTO1.getId());
        assertThat(systemFilesDTO1).isEqualTo(systemFilesDTO2);
        systemFilesDTO2.setId(2L);
        assertThat(systemFilesDTO1).isNotEqualTo(systemFilesDTO2);
        systemFilesDTO1.setId(null);
        assertThat(systemFilesDTO1).isNotEqualTo(systemFilesDTO2);
    }
}
