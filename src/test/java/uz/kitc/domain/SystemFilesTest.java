package uz.kitc.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import uz.kitc.web.rest.TestUtil;

public class SystemFilesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SystemFiles.class);
        SystemFiles systemFiles1 = new SystemFiles();
        systemFiles1.setId(1L);
        SystemFiles systemFiles2 = new SystemFiles();
        systemFiles2.setId(systemFiles1.getId());
        assertThat(systemFiles1).isEqualTo(systemFiles2);
        systemFiles2.setId(2L);
        assertThat(systemFiles1).isNotEqualTo(systemFiles2);
        systemFiles1.setId(null);
        assertThat(systemFiles1).isNotEqualTo(systemFiles2);
    }
}
