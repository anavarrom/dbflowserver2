package dbflow.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import dbflow.server.web.rest.TestUtil;

public class SafeKeepingProjectTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SafeKeepingProject.class);
        SafeKeepingProject safeKeepingProject1 = new SafeKeepingProject();
        safeKeepingProject1.setId(1L);
        SafeKeepingProject safeKeepingProject2 = new SafeKeepingProject();
        safeKeepingProject2.setId(safeKeepingProject1.getId());
        assertThat(safeKeepingProject1).isEqualTo(safeKeepingProject2);
        safeKeepingProject2.setId(2L);
        assertThat(safeKeepingProject1).isNotEqualTo(safeKeepingProject2);
        safeKeepingProject1.setId(null);
        assertThat(safeKeepingProject1).isNotEqualTo(safeKeepingProject2);
    }
}
