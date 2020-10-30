package dbflow.server.repository;

import dbflow.server.domain.SafeKeepingPeriod;
import dbflow.server.domain.SafeKeepingProject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SafeKeepingPeriod entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SafeKeepingPeriodRepository extends JpaRepository<SafeKeepingPeriod, Long> {
	Page<SafeKeepingPeriod> findAllBySafeKeepingProjectId(Long projectId, Pageable pageable);
	Page<SafeKeepingPeriod> findAllBySafeKeepingProjectIdAndYear(Long projectId, String year, Pageable pageable);
}
