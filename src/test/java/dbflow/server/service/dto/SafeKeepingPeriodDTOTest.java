package dbflow.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import dbflow.server.web.rest.TestUtil;

public class SafeKeepingPeriodDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SafeKeepingPeriodDTO.class);
        SafeKeepingPeriodDTO safeKeepingPeriodDTO1 = new SafeKeepingPeriodDTO();
        safeKeepingPeriodDTO1.setId(1L);
        SafeKeepingPeriodDTO safeKeepingPeriodDTO2 = new SafeKeepingPeriodDTO();
        assertThat(safeKeepingPeriodDTO1).isNotEqualTo(safeKeepingPeriodDTO2);
        safeKeepingPeriodDTO2.setId(safeKeepingPeriodDTO1.getId());
        assertThat(safeKeepingPeriodDTO1).isEqualTo(safeKeepingPeriodDTO2);
        safeKeepingPeriodDTO2.setId(2L);
        assertThat(safeKeepingPeriodDTO1).isNotEqualTo(safeKeepingPeriodDTO2);
        safeKeepingPeriodDTO1.setId(null);
        assertThat(safeKeepingPeriodDTO1).isNotEqualTo(safeKeepingPeriodDTO2);
    }
}
