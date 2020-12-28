package uz.kitc.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FaqMapperTest {

    private FaqMapper faqMapper;

    @BeforeEach
    public void setUp() {
        faqMapper = new FaqMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(faqMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(faqMapper.fromId(null)).isNull();
    }
}
