package dbflow.server.service.impl;

import dbflow.server.service.SafeKeepingPeriodService;
import dbflow.server.domain.SafeKeepingPeriod;
import dbflow.server.repository.SafeKeepingPeriodRepository;
import dbflow.server.service.dto.SafeKeepingPeriodDTO;
import dbflow.server.service.mapper.SafeKeepingPeriodMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SafeKeepingPeriod}.
 */
@Service
@Transactional
public class SafeKeepingPeriodServiceImpl implements SafeKeepingPeriodService {

    private final Logger log = LoggerFactory.getLogger(SafeKeepingPeriodServiceImpl.class);

    private final SafeKeepingPeriodRepository safeKeepingPeriodRepository;

    private final SafeKeepingPeriodMapper safeKeepingPeriodMapper;

    public SafeKeepingPeriodServiceImpl(SafeKeepingPeriodRepository safeKeepingPeriodRepository, SafeKeepingPeriodMapper safeKeepingPeriodMapper) {
        this.safeKeepingPeriodRepository = safeKeepingPeriodRepository;
        this.safeKeepingPeriodMapper = safeKeepingPeriodMapper;
    }

    @Override
    public SafeKeepingPeriodDTO save(SafeKeepingPeriodDTO safeKeepingPeriodDTO) {
        log.debug("Request to save SafeKeepingPeriod : {}", safeKeepingPeriodDTO);
        SafeKeepingPeriod safeKeepingPeriod = safeKeepingPeriodMapper.toEntity(safeKeepingPeriodDTO);
        safeKeepingPeriod = safeKeepingPeriodRepository.save(safeKeepingPeriod);
        return safeKeepingPeriodMapper.toDto(safeKeepingPeriod);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SafeKeepingPeriodDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SafeKeepingPeriods");
        return safeKeepingPeriodRepository.findAll(pageable)
            .map(safeKeepingPeriodMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SafeKeepingPeriodDTO> findOne(Long id) {
        log.debug("Request to get SafeKeepingPeriod : {}", id);
        return safeKeepingPeriodRepository.findById(id)
            .map(safeKeepingPeriodMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SafeKeepingPeriod : {}", id);
        safeKeepingPeriodRepository.deleteById(id);
    }

	@Override
	public Page<SafeKeepingPeriodDTO> findAllByProject(Long projectId, Pageable pageable) {
		// TODO Auto-generated method stub
        return safeKeepingPeriodRepository.findAllBySafeKeepingProjectId(projectId, pageable).map(safeKeepingPeriodMapper::toDto);
	}

	@Override
	public Page<SafeKeepingPeriodDTO> findAllByProjectAndYear(Long projectId, String year, Pageable pageable) {
		// TODO Auto-generated method stub
        return safeKeepingPeriodRepository.findAllBySafeKeepingProjectIdAndYear(projectId, year, pageable).map(safeKeepingPeriodMapper::toDto);
	}
}
