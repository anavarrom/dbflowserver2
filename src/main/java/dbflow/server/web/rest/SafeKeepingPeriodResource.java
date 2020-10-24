package dbflow.server.web.rest;

import dbflow.server.service.SafeKeepingPeriodService;
import dbflow.server.web.rest.errors.BadRequestAlertException;
import dbflow.server.service.dto.SafeKeepingPeriodDTO;

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
 * REST controller for managing {@link dbflow.server.domain.SafeKeepingPeriod}.
 */
@RestController
@RequestMapping("/api")
public class SafeKeepingPeriodResource {

    private final Logger log = LoggerFactory.getLogger(SafeKeepingPeriodResource.class);

    private static final String ENTITY_NAME = "dbflowserver2SafeKeepingPeriod";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SafeKeepingPeriodService safeKeepingPeriodService;

    public SafeKeepingPeriodResource(SafeKeepingPeriodService safeKeepingPeriodService) {
        this.safeKeepingPeriodService = safeKeepingPeriodService;
    }

    /**
     * {@code POST  /safe-keeping-periods} : Create a new safeKeepingPeriod.
     *
     * @param safeKeepingPeriodDTO the safeKeepingPeriodDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new safeKeepingPeriodDTO, or with status {@code 400 (Bad Request)} if the safeKeepingPeriod has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/safe-keeping-periods")
    public ResponseEntity<SafeKeepingPeriodDTO> createSafeKeepingPeriod(@RequestBody SafeKeepingPeriodDTO safeKeepingPeriodDTO) throws URISyntaxException {
        log.debug("REST request to save SafeKeepingPeriod : {}", safeKeepingPeriodDTO);
        if (safeKeepingPeriodDTO.getId() != null) {
            throw new BadRequestAlertException("A new safeKeepingPeriod cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SafeKeepingPeriodDTO result = safeKeepingPeriodService.save(safeKeepingPeriodDTO);
        return ResponseEntity.created(new URI("/api/safe-keeping-periods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /safe-keeping-periods} : Updates an existing safeKeepingPeriod.
     *
     * @param safeKeepingPeriodDTO the safeKeepingPeriodDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated safeKeepingPeriodDTO,
     * or with status {@code 400 (Bad Request)} if the safeKeepingPeriodDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the safeKeepingPeriodDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/safe-keeping-periods")
    public ResponseEntity<SafeKeepingPeriodDTO> updateSafeKeepingPeriod(@RequestBody SafeKeepingPeriodDTO safeKeepingPeriodDTO) throws URISyntaxException {
        log.debug("REST request to update SafeKeepingPeriod : {}", safeKeepingPeriodDTO);
        if (safeKeepingPeriodDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SafeKeepingPeriodDTO result = safeKeepingPeriodService.save(safeKeepingPeriodDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, safeKeepingPeriodDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /safe-keeping-periods} : get all the safeKeepingPeriods.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of safeKeepingPeriods in body.
     */
    @GetMapping("/safe-keeping-periods")
    public ResponseEntity<List<SafeKeepingPeriodDTO>> getAllSafeKeepingPeriods(Pageable pageable) {
        log.debug("REST request to get a page of SafeKeepingPeriods");
        Page<SafeKeepingPeriodDTO> page = safeKeepingPeriodService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /safe-keeping-periods/:id} : get the "id" safeKeepingPeriod.
     *
     * @param id the id of the safeKeepingPeriodDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the safeKeepingPeriodDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/safe-keeping-periods/{id}")
    public ResponseEntity<SafeKeepingPeriodDTO> getSafeKeepingPeriod(@PathVariable Long id) {
        log.debug("REST request to get SafeKeepingPeriod : {}", id);
        Optional<SafeKeepingPeriodDTO> safeKeepingPeriodDTO = safeKeepingPeriodService.findOne(id);
        return ResponseUtil.wrapOrNotFound(safeKeepingPeriodDTO);
    }

    /**
     * {@code DELETE  /safe-keeping-periods/:id} : delete the "id" safeKeepingPeriod.
     *
     * @param id the id of the safeKeepingPeriodDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/safe-keeping-periods/{id}")
    public ResponseEntity<Void> deleteSafeKeepingPeriod(@PathVariable Long id) {
        log.debug("REST request to delete SafeKeepingPeriod : {}", id);
        safeKeepingPeriodService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
