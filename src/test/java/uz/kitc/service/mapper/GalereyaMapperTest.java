package uz.kitc.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GalereyaMapperTest {

    private GalereyaMapper galereyaMapper;

    @BeforeEach
    public void setUp() {
        galereyaMapper = new GalereyaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(galereyaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(galereyaMapper.fromId(null)).isNull();
    }
}
