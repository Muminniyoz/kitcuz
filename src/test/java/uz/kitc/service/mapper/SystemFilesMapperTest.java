package uz.kitc.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SystemFilesMapperTest {

    private SystemFilesMapper systemFilesMapper;

    @BeforeEach
    public void setUp() {
        systemFilesMapper = new SystemFilesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(systemFilesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(systemFilesMapper.fromId(null)).isNull();
    }
}
