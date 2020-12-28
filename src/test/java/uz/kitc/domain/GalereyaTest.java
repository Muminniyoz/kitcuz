package uz.kitc.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import uz.kitc.web.rest.TestUtil;

public class GalereyaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Galereya.class);
        Galereya galereya1 = new Galereya();
        galereya1.setId(1L);
        Galereya galereya2 = new Galereya();
        galereya2.setId(galereya1.getId());
        assertThat(galereya1).isEqualTo(galereya2);
        galereya2.setId(2L);
        assertThat(galereya1).isNotEqualTo(galereya2);
        galereya1.setId(null);
        assertThat(galereya1).isNotEqualTo(galereya2);
    }
}
