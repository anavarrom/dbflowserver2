package dbflow.server.web.rest;

import dbflow.server.security.SecurityUtils;
import dbflow.server.service.SafeKeepingProjectService;
import dbflow.server.web.rest.errors.BadRequestAlertException;
import dbflow.server.service.dto.SafeKeepingProjectDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link dbflow.server.domain.SafeKeepingProject}.
 */
@RestController
@RequestMapping("/api")
public class SafeKeepingProjectResource {

    private final Logger log = LoggerFactory.getLogger(SafeKeepingProjectResource.class);

    private static final String ENTITY_NAME = "dbflowserver2SafeKeepingProject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SafeKeepingProjectService safeKeepingProjectService;

    public SafeKeepingProjectResource(SafeKeepingProjectService safeKeepingProjectService) {
        this.safeKeepingProjectService = safeKeepingProjectService;
    }

    /**
     * {@code POST  /safe-keeping-projects} : Create a new safeKeepingProject.
     *
     * @param safeKeepingProjectDTO the safeKeepingProjectDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new safeKeepingProjectDTO, or with status {@code 400 (Bad Request)} if the safeKeepingProject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project")
    public ResponseEntity<SafeKeepingProjectDTO> createSafeKeepingProject(@RequestBody SafeKeepingProjectDTO safeKeepingProjectDTO) throws URISyntaxException {
        log.debug("REST request to save SafeKeepingProject : {}", safeKeepingProjectDTO);
        if (safeKeepingProjectDTO.getId() != null) {
            throw new BadRequestAlertException("A new safeKeepingProject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SafeKeepingProjectDTO result = safeKeepingProjectService.save(safeKeepingProjectDTO);
        return ResponseEntity.created(new URI("/api/safe-keeping-projects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /safe-keeping-projects} : Updates an existing safeKeepingProject.
     *
     * @param safeKeepingProjectDTO the safeKeepingProjectDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated safeKeepingProjectDTO,
     * or with status {@code 400 (Bad Request)} if the safeKeepingProjectDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the safeKeepingProjectDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project")
    public ResponseEntity<SafeKeepingProjectDTO> updateSafeKeepingProject(@RequestBody SafeKeepingProjectDTO safeKeepingProjectDTO) throws URISyntaxException {
        log.debug("REST request to update SafeKeepingProject : {}", safeKeepingProjectDTO);
        if (safeKeepingProjectDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SafeKeepingProjectDTO result = safeKeepingProjectService.save(safeKeepingProjectDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, safeKeepingProjectDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /safe-keeping-projects} : get all the safeKeepingProjects.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of safeKeepingProjects in body.
     */
    @GetMapping("/project")
    public ResponseEntity<List<SafeKeepingProjectDTO>> getAllSafeKeepingProjects(Pageable pageable) {
        log.debug("REST request to get a page of SafeKeepingProjects");
        Page<SafeKeepingProjectDTO> page = safeKeepingProjectService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /chats} : get all the user appointments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appointments in body.
     */
    @GetMapping("/project/findByUser")
    public ResponseEntity<List<SafeKeepingProjectDTO>> getAllByUser(Pageable pageable) { 
       boolean isConnected = SecurityUtils.isAuthenticated();
       if (!isConnected) {
    	   log.debug("Not Connected ");
    	   return null;
       } 
       Optional<String> username = SecurityUtils.getCurrentUserLogin();
       log.debug("Connected " + username);              
       String real_username = username.get();
       
       Page<SafeKeepingProjectDTO> page = safeKeepingProjectService.findAllByUser(real_username, pageable);
       HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
       return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    /**
     * {@code GET  /safe-keeping-projects/:id} : get the "id" safeKeepingProject.
     *
     * @param id the id of the safeKeepingProjectDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the safeKeepingProjectDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project/{id}")
    public ResponseEntity<SafeKeepingProjectDTO> getSafeKeepingProject(@PathVariable Long id) {
        log.debug("REST request to get SafeKeepingProject : {}", id);
        Optional<SafeKeepingProjectDTO> safeKeepingProjectDTO = safeKeepingProjectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(safeKeepingProjectDTO);
    }

    /**
     * {@code DELETE  /safe-keeping-projects/:id} : delete the "id" safeKeepingProject.
     *
     * @param id the id of the safeKeepingProjectDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project/{id}")
    public ResponseEntity<Void> deleteSafeKeepingProject(@PathVariable Long id) {
        log.debug("REST request to delete SafeKeepingProject : {}", id);
        safeKeepingProjectService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
