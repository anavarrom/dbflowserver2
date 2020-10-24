package dbflow.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import dbflow.server.web.rest.TestUtil;

public class SafeKeepingProjectDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SafeKeepingProjectDTO.class);
        SafeKeepingProjectDTO safeKeepingProjectDTO1 = new SafeKeepingProjectDTO();
        safeKeepingProjectDTO1.setId(1L);
        SafeKeepingProjectDTO safeKeepingProjectDTO2 = new SafeKeepingProjectDTO();
        assertThat(safeKeepingProjectDTO1).isNotEqualTo(safeKeepingProjectDTO2);
        safeKeepingProjectDTO2.setId(safeKeepingProjectDTO1.getId());
        assertThat(safeKeepingProjectDTO1).isEqualTo(safeKeepingProjectDTO2);
        safeKeepingProjectDTO2.setId(2L);
        assertThat(safeKeepingProjectDTO1).isNotEqualTo(safeKeepingProjectDTO2);
        safeKeepingProjectDTO1.setId(null);
        assertThat(safeKeepingProjectDTO1).isNotEqualTo(safeKeepingProjectDTO2);
    }
}
