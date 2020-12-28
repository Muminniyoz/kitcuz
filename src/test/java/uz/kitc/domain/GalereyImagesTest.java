package uz.kitc.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import uz.kitc.web.rest.TestUtil;

public class GalereyImagesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GalereyImages.class);
        GalereyImages galereyImages1 = new GalereyImages();
        galereyImages1.setId(1L);
        GalereyImages galereyImages2 = new GalereyImages();
        galereyImages2.setId(galereyImages1.getId());
        assertThat(galereyImages1).isEqualTo(galereyImages2);
        galereyImages2.setId(2L);
        assertThat(galereyImages1).isNotEqualTo(galereyImages2);
        galereyImages1.setId(null);
        assertThat(galereyImages1).isNotEqualTo(galereyImages2);
    }
}
