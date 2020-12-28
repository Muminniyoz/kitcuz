package uz.kitc.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import uz.kitc.web.rest.TestUtil;

public class GalereyImagesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GalereyImagesDTO.class);
        GalereyImagesDTO galereyImagesDTO1 = new GalereyImagesDTO();
        galereyImagesDTO1.setId(1L);
        GalereyImagesDTO galereyImagesDTO2 = new GalereyImagesDTO();
        assertThat(galereyImagesDTO1).isNotEqualTo(galereyImagesDTO2);
        galereyImagesDTO2.setId(galereyImagesDTO1.getId());
        assertThat(galereyImagesDTO1).isEqualTo(galereyImagesDTO2);
        galereyImagesDTO2.setId(2L);
        assertThat(galereyImagesDTO1).isNotEqualTo(galereyImagesDTO2);
        galereyImagesDTO1.setId(null);
        assertThat(galereyImagesDTO1).isNotEqualTo(galereyImagesDTO2);
    }
}
