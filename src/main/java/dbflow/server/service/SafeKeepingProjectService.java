package dbflow.server.service;

import dbflow.server.service.dto.SafeKeepingProjectDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link dbflow.server.domain.SafeKeepingProject}.
 */
public interface SafeKeepingProjectService {

    /**
     * Save a safeKeepingProject.
     *
     * @param safeKeepingProjectDTO the entity to save.
     * @return the persisted entity.
     */
    SafeKeepingProjectDTO save(SafeKeepingProjectDTO safeKeepingProjectDTO);

    /**
     * Get all the safeKeepingProjects.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SafeKeepingProjectDTO> findAll(Pageable pageable);


    /**
     * Get all the user projects
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SafeKeepingProjectDTO> findAllByUser(String username, Pageable pageable);

    /**
     * Get the "id" safeKeepingProject.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SafeKeepingProjectDTO> findOne(Long id);

    /**
     * Delete the "id" safeKeepingProject.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
