package uz.kitc.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PlanningMapperTest {

    private PlanningMapper planningMapper;

    @BeforeEach
    public void setUp() {
        planningMapper = new PlanningMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(planningMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(planningMapper.fromId(null)).isNull();
    }
}
