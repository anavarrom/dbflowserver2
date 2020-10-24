package dbflow.server.service.impl;

import dbflow.server.service.SafeKeepingProjectService;
import dbflow.server.domain.SafeKeepingProject;
import dbflow.server.repository.SafeKeepingProjectRepository;
import dbflow.server.service.dto.SafeKeepingProjectDTO;
import dbflow.server.service.mapper.SafeKeepingProjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SafeKeepingProject}.
 */
@Service
@Transactional
public class SafeKeepingProjectServiceImpl implements SafeKeepingProjectService {

    private final Logger log = LoggerFactory.getLogger(SafeKeepingProjectServiceImpl.class);

    private final SafeKeepingProjectRepository safeKeepingProjectRepository;

    private final SafeKeepingProjectMapper safeKeepingProjectMapper;

    public SafeKeepingProjectServiceImpl(SafeKeepingProjectRepository safeKeepingProjectRepository, SafeKeepingProjectMapper safeKeepingProjectMapper) {
        this.safeKeepingProjectRepository = safeKeepingProjectRepository;
        this.safeKeepingProjectMapper = safeKeepingProjectMapper;
    }

    @Override
    public SafeKeepingProjectDTO save(SafeKeepingProjectDTO safeKeepingProjectDTO) {
        log.debug("Request to save SafeKeepingProject : {}", safeKeepingProjectDTO);
        SafeKeepingProject safeKeepingProject = safeKeepingProjectMapper.toEntity(safeKeepingProjectDTO);
        safeKeepingProject = safeKeepingProjectRepository.save(safeKeepingProject);
        return safeKeepingProjectMapper.toDto(safeKeepingProject);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SafeKeepingProjectDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SafeKeepingProjects");
        return safeKeepingProjectRepository.findAll(pageable)
            .map(safeKeepingProjectMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SafeKeepingProjectDTO> findOne(Long id) {
        log.debug("Request to get SafeKeepingProject : {}", id);
        return safeKeepingProjectRepository.findById(id)
            .map(safeKeepingProjectMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SafeKeepingProject : {}", id);
        safeKeepingProjectRepository.deleteById(id);
    }

	@Override
	public Page<SafeKeepingProjectDTO> findAllByUser(String username, Pageable pageable) {
		// TODO Auto-generated method stub
        return safeKeepingProjectRepository.findAllByParent1OrParent2(username, username, pageable).map(safeKeepingProjectMapper::toDto);
	}
}
