package dbflow.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SafeKeepingPeriodMapperTest {

    private SafeKeepingPeriodMapper safeKeepingPeriodMapper;

    @BeforeEach
    public void setUp() {
        safeKeepingPeriodMapper = new SafeKeepingPeriodMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(safeKeepingPeriodMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(safeKeepingPeriodMapper.fromId(null)).isNull();
    }
}
