package uz.kitc.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import uz.kitc.web.rest.TestUtil;

public class FaqTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Faq.class);
        Faq faq1 = new Faq();
        faq1.setId(1L);
        Faq faq2 = new Faq();
        faq2.setId(faq1.getId());
        assertThat(faq1).isEqualTo(faq2);
        faq2.setId(2L);
        assertThat(faq1).isNotEqualTo(faq2);
        faq1.setId(null);
        assertThat(faq1).isNotEqualTo(faq2);
    }
}
