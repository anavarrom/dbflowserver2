package dbflow.server.web.rest;

import dbflow.server.Dbflowserver2App;
import dbflow.server.config.TestSecurityConfiguration;
import dbflow.server.domain.SafeKeepingPeriod;
import dbflow.server.repository.SafeKeepingPeriodRepository;
import dbflow.server.service.SafeKeepingPeriodService;
import dbflow.server.service.dto.SafeKeepingPeriodDTO;
import dbflow.server.service.mapper.SafeKeepingPeriodMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SafeKeepingPeriodResource} REST controller.
 */
@SpringBootTest(classes = { Dbflowserver2App.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class SafeKeepingPeriodResourceIT {

    private static final String DEFAULT_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_YEAR = "BBBBBBBBBB";

    private static final String DEFAULT_OWNER = "AAAAAAAAAA";
    private static final String UPDATED_OWNER = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_BACKGROUND_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_BACKGROUND_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_TEXT_COLOR = "BBBBBBBBBB";

    @Autowired
    private SafeKeepingPeriodRepository safeKeepingPeriodRepository;

    @Autowired
    private SafeKeepingPeriodMapper safeKeepingPeriodMapper;

    @Autowired
    private SafeKeepingPeriodService safeKeepingPeriodService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSafeKeepingPeriodMockMvc;

    private SafeKeepingPeriod safeKeepingPeriod;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SafeKeepingPeriod createEntity(EntityManager em) {
        SafeKeepingPeriod safeKeepingPeriod = new SafeKeepingPeriod()
            .year(DEFAULT_YEAR)
            .owner(DEFAULT_OWNER)
            .text(DEFAULT_TEXT)
            .description(DEFAULT_DESCRIPTION)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .backgroundColor(DEFAULT_BACKGROUND_COLOR)
            .textColor(DEFAULT_TEXT_COLOR);
        return safeKeepingPeriod;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SafeKeepingPeriod createUpdatedEntity(EntityManager em) {
        SafeKeepingPeriod safeKeepingPeriod = new SafeKeepingPeriod()
            .year(UPDATED_YEAR)
            .owner(UPDATED_OWNER)
            .text(UPDATED_TEXT)
            .description(UPDATED_DESCRIPTION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .backgroundColor(UPDATED_BACKGROUND_COLOR)
            .textColor(UPDATED_TEXT_COLOR);
        return safeKeepingPeriod;
    }

    @BeforeEach
    public void initTest() {
        safeKeepingPeriod = createEntity(em);
    }

    @Test
    @Transactional
    public void createSafeKeepingPeriod() throws Exception {
        int databaseSizeBeforeCreate = safeKeepingPeriodRepository.findAll().size();
        // Create the SafeKeepingPeriod
        SafeKeepingPeriodDTO safeKeepingPeriodDTO = safeKeepingPeriodMapper.toDto(safeKeepingPeriod);
        restSafeKeepingPeriodMockMvc.perform(post("/api/safe-keeping-periods").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(safeKeepingPeriodDTO)))
            .andExpect(status().isCreated());

        // Validate the SafeKeepingPeriod in the database
        List<SafeKeepingPeriod> safeKeepingPeriodList = safeKeepingPeriodRepository.findAll();
        assertThat(safeKeepingPeriodList).hasSize(databaseSizeBeforeCreate + 1);
        SafeKeepingPeriod testSafeKeepingPeriod = safeKeepingPeriodList.get(safeKeepingPeriodList.size() - 1);
        assertThat(testSafeKeepingPeriod.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testSafeKeepingPeriod.getOwner()).isEqualTo(DEFAULT_OWNER);
        assertThat(testSafeKeepingPeriod.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testSafeKeepingPeriod.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSafeKeepingPeriod.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testSafeKeepingPeriod.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testSafeKeepingPeriod.getBackgroundColor()).isEqualTo(DEFAULT_BACKGROUND_COLOR);
        assertThat(testSafeKeepingPeriod.getTextColor()).isEqualTo(DEFAULT_TEXT_COLOR);
    }

    @Test
    @Transactional
    public void createSafeKeepingPeriodWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = safeKeepingPeriodRepository.findAll().size();

        // Create the SafeKeepingPeriod with an existing ID
        safeKeepingPeriod.setId(1L);
        SafeKeepingPeriodDTO safeKeepingPeriodDTO = safeKeepingPeriodMapper.toDto(safeKeepingPeriod);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSafeKeepingPeriodMockMvc.perform(post("/api/safe-keeping-periods").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(safeKeepingPeriodDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SafeKeepingPeriod in the database
        List<SafeKeepingPeriod> safeKeepingPeriodList = safeKeepingPeriodRepository.findAll();
        assertThat(safeKeepingPeriodList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSafeKeepingPeriods() throws Exception {
        // Initialize the database
        safeKeepingPeriodRepository.saveAndFlush(safeKeepingPeriod);

        // Get all the safeKeepingPeriodList
        restSafeKeepingPeriodMockMvc.perform(get("/api/safe-keeping-periods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(safeKeepingPeriod.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].owner").value(hasItem(DEFAULT_OWNER)))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].backgroundColor").value(hasItem(DEFAULT_BACKGROUND_COLOR)))
            .andExpect(jsonPath("$.[*].textColor").value(hasItem(DEFAULT_TEXT_COLOR)));
    }
    
    @Test
    @Transactional
    public void getSafeKeepingPeriod() throws Exception {
        // Initialize the database
        safeKeepingPeriodRepository.saveAndFlush(safeKeepingPeriod);

        // Get the safeKeepingPeriod
        restSafeKeepingPeriodMockMvc.perform(get("/api/safe-keeping-periods/{id}", safeKeepingPeriod.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(safeKeepingPeriod.getId().intValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.owner").value(DEFAULT_OWNER))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.backgroundColor").value(DEFAULT_BACKGROUND_COLOR))
            .andExpect(jsonPath("$.textColor").value(DEFAULT_TEXT_COLOR));
    }
    @Test
    @Transactional
    public void getNonExistingSafeKeepingPeriod() throws Exception {
        // Get the safeKeepingPeriod
        restSafeKeepingPeriodMockMvc.perform(get("/api/safe-keeping-periods/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSafeKeepingPeriod() throws Exception {
        // Initialize the database
        safeKeepingPeriodRepository.saveAndFlush(safeKeepingPeriod);

        int databaseSizeBeforeUpdate = safeKeepingPeriodRepository.findAll().size();

        // Update the safeKeepingPeriod
        SafeKeepingPeriod updatedSafeKeepingPeriod = safeKeepingPeriodRepository.findById(safeKeepingPeriod.getId()).get();
        // Disconnect from session so that the updates on updatedSafeKeepingPeriod are not directly saved in db
        em.detach(updatedSafeKeepingPeriod);
        updatedSafeKeepingPeriod
            .year(UPDATED_YEAR)
            .owner(UPDATED_OWNER)
            .text(UPDATED_TEXT)
            .description(UPDATED_DESCRIPTION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .backgroundColor(UPDATED_BACKGROUND_COLOR)
            .textColor(UPDATED_TEXT_COLOR);
        SafeKeepingPeriodDTO safeKeepingPeriodDTO = safeKeepingPeriodMapper.toDto(updatedSafeKeepingPeriod);

        restSafeKeepingPeriodMockMvc.perform(put("/api/safe-keeping-periods").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(safeKeepingPeriodDTO)))
            .andExpect(status().isOk());

        // Validate the SafeKeepingPeriod in the database
        List<SafeKeepingPeriod> safeKeepingPeriodList = safeKeepingPeriodRepository.findAll();
        assertThat(safeKeepingPeriodList).hasSize(databaseSizeBeforeUpdate);
        SafeKeepingPeriod testSafeKeepingPeriod = safeKeepingPeriodList.get(safeKeepingPeriodList.size() - 1);
        assertThat(testSafeKeepingPeriod.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testSafeKeepingPeriod.getOwner()).isEqualTo(UPDATED_OWNER);
        assertThat(testSafeKeepingPeriod.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testSafeKeepingPeriod.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSafeKeepingPeriod.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testSafeKeepingPeriod.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testSafeKeepingPeriod.getBackgroundColor()).isEqualTo(UPDATED_BACKGROUND_COLOR);
        assertThat(testSafeKeepingPeriod.getTextColor()).isEqualTo(UPDATED_TEXT_COLOR);
    }

    @Test
    @Transactional
    public void updateNonExistingSafeKeepingPeriod() throws Exception {
        int databaseSizeBeforeUpdate = safeKeepingPeriodRepository.findAll().size();

        // Create the SafeKeepingPeriod
        SafeKeepingPeriodDTO safeKeepingPeriodDTO = safeKeepingPeriodMapper.toDto(safeKeepingPeriod);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSafeKeepingPeriodMockMvc.perform(put("/api/safe-keeping-periods").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(safeKeepingPeriodDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SafeKeepingPeriod in the database
        List<SafeKeepingPeriod> safeKeepingPeriodList = safeKeepingPeriodRepository.findAll();
        assertThat(safeKeepingPeriodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSafeKeepingPeriod() throws Exception {
        // Initialize the database
        safeKeepingPeriodRepository.saveAndFlush(safeKeepingPeriod);

        int databaseSizeBeforeDelete = safeKeepingPeriodRepository.findAll().size();

        // Delete the safeKeepingPeriod
        restSafeKeepingPeriodMockMvc.perform(delete("/api/safe-keeping-periods/{id}", safeKeepingPeriod.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SafeKeepingPeriod> safeKeepingPeriodList = safeKeepingPeriodRepository.findAll();
        assertThat(safeKeepingPeriodList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
