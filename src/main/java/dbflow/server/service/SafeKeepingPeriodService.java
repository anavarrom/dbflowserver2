package dbflow.server.service;

import dbflow.server.service.dto.SafeKeepingPeriodDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link dbflow.server.domain.SafeKeepingPeriod}.
 */
public interface SafeKeepingPeriodService {

    /**
     * Save a safeKeepingPeriod.
     *
     * @param safeKeepingPeriodDTO the entity to save.
     * @return the persisted entity.
     */
    SafeKeepingPeriodDTO save(SafeKeepingPeriodDTO safeKeepingPeriodDTO);

    /**
     * Get all the safeKeepingPeriods.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SafeKeepingPeriodDTO> findAll(Pageable pageable);


    /**
     * Get the "id" safeKeepingPeriod.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SafeKeepingPeriodDTO> findOne(Long id);

    /**
     * Delete the "id" safeKeepingPeriod.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
