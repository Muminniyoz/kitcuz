package uz.kitc.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import uz.kitc.web.rest.TestUtil;

public class FaqDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FaqDTO.class);
        FaqDTO faqDTO1 = new FaqDTO();
        faqDTO1.setId(1L);
        FaqDTO faqDTO2 = new FaqDTO();
        assertThat(faqDTO1).isNotEqualTo(faqDTO2);
        faqDTO2.setId(faqDTO1.getId());
        assertThat(faqDTO1).isEqualTo(faqDTO2);
        faqDTO2.setId(2L);
        assertThat(faqDTO1).isNotEqualTo(faqDTO2);
        faqDTO1.setId(null);
        assertThat(faqDTO1).isNotEqualTo(faqDTO2);
    }
}
