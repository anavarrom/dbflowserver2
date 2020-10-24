package dbflow.server.repository;

import dbflow.server.domain.SafeKeepingPeriod;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SafeKeepingPeriod entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SafeKeepingPeriodRepository extends JpaRepository<SafeKeepingPeriod, Long> {
}
