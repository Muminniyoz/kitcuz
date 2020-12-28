package uz.kitc.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import uz.kitc.web.rest.TestUtil;

public class GalereyaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GalereyaDTO.class);
        GalereyaDTO galereyaDTO1 = new GalereyaDTO();
        galereyaDTO1.setId(1L);
        GalereyaDTO galereyaDTO2 = new GalereyaDTO();
        assertThat(galereyaDTO1).isNotEqualTo(galereyaDTO2);
        galereyaDTO2.setId(galereyaDTO1.getId());
        assertThat(galereyaDTO1).isEqualTo(galereyaDTO2);
        galereyaDTO2.setId(2L);
        assertThat(galereyaDTO1).isNotEqualTo(galereyaDTO2);
        galereyaDTO1.setId(null);
        assertThat(galereyaDTO1).isNotEqualTo(galereyaDTO2);
    }
}
