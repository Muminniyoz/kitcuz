package uz.kitc.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CoursesMapperTest {

    private CoursesMapper coursesMapper;

    @BeforeEach
    public void setUp() {
        coursesMapper = new CoursesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(coursesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(coursesMapper.fromId(null)).isNull();
    }
}
