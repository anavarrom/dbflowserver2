package dbflow.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SafeKeepingProjectMapperTest {

    private SafeKeepingProjectMapper safeKeepingProjectMapper;

    @BeforeEach
    public void setUp() {
        safeKeepingProjectMapper = new SafeKeepingProjectMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(safeKeepingProjectMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(safeKeepingProjectMapper.fromId(null)).isNull();
    }
}
