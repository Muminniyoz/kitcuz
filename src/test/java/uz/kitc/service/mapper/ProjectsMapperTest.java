package uz.kitc.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProjectsMapperTest {

    private ProjectsMapper projectsMapper;

    @BeforeEach
    public void setUp() {
        projectsMapper = new ProjectsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(projectsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(projectsMapper.fromId(null)).isNull();
    }
}
