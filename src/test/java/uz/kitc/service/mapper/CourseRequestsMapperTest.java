package uz.kitc.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CourseRequestsMapperTest {

    private CourseRequestsMapper courseRequestsMapper;

    @BeforeEach
    public void setUp() {
        courseRequestsMapper = new CourseRequestsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(courseRequestsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(courseRequestsMapper.fromId(null)).isNull();
    }
}
