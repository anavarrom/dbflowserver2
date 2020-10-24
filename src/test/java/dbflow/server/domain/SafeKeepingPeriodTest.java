package dbflow.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import dbflow.server.web.rest.TestUtil;

public class SafeKeepingPeriodTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SafeKeepingPeriod.class);
        SafeKeepingPeriod safeKeepingPeriod1 = new SafeKeepingPeriod();
        safeKeepingPeriod1.setId(1L);
        SafeKeepingPeriod safeKeepingPeriod2 = new SafeKeepingPeriod();
        safeKeepingPeriod2.setId(safeKeepingPeriod1.getId());
        assertThat(safeKeepingPeriod1).isEqualTo(safeKeepingPeriod2);
        safeKeepingPeriod2.setId(2L);
        assertThat(safeKeepingPeriod1).isNotEqualTo(safeKeepingPeriod2);
        safeKeepingPeriod1.setId(null);
        assertThat(safeKeepingPeriod1).isNotEqualTo(safeKeepingPeriod2);
    }
}
