package uz.kitc.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class StudentGroupMapperTest {

    private StudentGroupMapper studentGroupMapper;

    @BeforeEach
    public void setUp() {
        studentGroupMapper = new StudentGroupMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(studentGroupMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(studentGroupMapper.fromId(null)).isNull();
    }
}
