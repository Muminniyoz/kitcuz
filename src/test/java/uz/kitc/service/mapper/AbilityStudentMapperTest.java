package uz.kitc.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AbilityStudentMapperTest {

    private AbilityStudentMapper abilityStudentMapper;

    @BeforeEach
    public void setUp() {
        abilityStudentMapper = new AbilityStudentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(abilityStudentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(abilityStudentMapper.fromId(null)).isNull();
    }
}
