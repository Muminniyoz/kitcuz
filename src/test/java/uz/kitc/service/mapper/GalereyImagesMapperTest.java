package uz.kitc.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GalereyImagesMapperTest {

    private GalereyImagesMapper galereyImagesMapper;

    @BeforeEach
    public void setUp() {
        galereyImagesMapper = new GalereyImagesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(galereyImagesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(galereyImagesMapper.fromId(null)).isNull();
    }
}
