package dbflow.server.web.rest;

import dbflow.server.Dbflowserver2App;
import dbflow.server.config.TestSecurityConfiguration;
import dbflow.server.domain.SafeKeepingProject;
import dbflow.server.repository.SafeKeepingProjectRepository;
import dbflow.server.service.SafeKeepingProjectService;
import dbflow.server.service.dto.SafeKeepingProjectDTO;
import dbflow.server.service.mapper.SafeKeepingProjectMapper;

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
 * Integration tests for the {@link SafeKeepingProjectResource} REST controller.
 */
@SpringBootTest(classes = { Dbflowserver2App.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class SafeKeepingProjectResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PARENT_1 = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_1 = "BBBBBBBBBB";

    private static final String DEFAULT_PARENT_2 = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_2 = "BBBBBBBBBB";

    private static final String DEFAULT_MEDIATOR = "AAAAAAAAAA";
    private static final String UPDATED_MEDIATOR = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SafeKeepingProjectRepository safeKeepingProjectRepository;

    @Autowired
    private SafeKeepingProjectMapper safeKeepingProjectMapper;

    @Autowired
    private SafeKeepingProjectService safeKeepingProjectService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSafeKeepingProjectMockMvc;

    private SafeKeepingProject safeKeepingProject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SafeKeepingProject createEntity(EntityManager em) {
        SafeKeepingProject safeKeepingProject = new SafeKeepingProject()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .parent1(DEFAULT_PARENT_1)
            .parent2(DEFAULT_PARENT_2)
            .mediator(DEFAULT_MEDIATOR)
            .start(DEFAULT_START);
        return safeKeepingProject;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SafeKeepingProject createUpdatedEntity(EntityManager em) {
        SafeKeepingProject safeKeepingProject = new SafeKeepingProject()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .parent1(UPDATED_PARENT_1)
            .parent2(UPDATED_PARENT_2)
            .mediator(UPDATED_MEDIATOR)
            .start(UPDATED_START);
        return safeKeepingProject;
    }

    @BeforeEach
    public void initTest() {
        safeKeepingProject = createEntity(em);
    }

    @Test
    @Transactional
    public void createSafeKeepingProject() throws Exception {
        int databaseSizeBeforeCreate = safeKeepingProjectRepository.findAll().size();
        // Create the SafeKeepingProject
        SafeKeepingProjectDTO safeKeepingProjectDTO = safeKeepingProjectMapper.toDto(safeKeepingProject);
        restSafeKeepingProjectMockMvc.perform(post("/api/safe-keeping-projects").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(safeKeepingProjectDTO)))
            .andExpect(status().isCreated());

        // Validate the SafeKeepingProject in the database
        List<SafeKeepingProject> safeKeepingProjectList = safeKeepingProjectRepository.findAll();
        assertThat(safeKeepingProjectList).hasSize(databaseSizeBeforeCreate + 1);
        SafeKeepingProject testSafeKeepingProject = safeKeepingProjectList.get(safeKeepingProjectList.size() - 1);
        assertThat(testSafeKeepingProject.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSafeKeepingProject.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSafeKeepingProject.getParent1()).isEqualTo(DEFAULT_PARENT_1);
        assertThat(testSafeKeepingProject.getParent2()).isEqualTo(DEFAULT_PARENT_2);
        assertThat(testSafeKeepingProject.getMediator()).isEqualTo(DEFAULT_MEDIATOR);
        assertThat(testSafeKeepingProject.getStart()).isEqualTo(DEFAULT_START);
    }

    @Test
    @Transactional
    public void createSafeKeepingProjectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = safeKeepingProjectRepository.findAll().size();

        // Create the SafeKeepingProject with an existing ID
        safeKeepingProject.setId(1L);
        SafeKeepingProjectDTO safeKeepingProjectDTO = safeKeepingProjectMapper.toDto(safeKeepingProject);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSafeKeepingProjectMockMvc.perform(post("/api/safe-keeping-projects").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(safeKeepingProjectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SafeKeepingProject in the database
        List<SafeKeepingProject> safeKeepingProjectList = safeKeepingProjectRepository.findAll();
        assertThat(safeKeepingProjectList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSafeKeepingProjects() throws Exception {
        // Initialize the database
        safeKeepingProjectRepository.saveAndFlush(safeKeepingProject);

        // Get all the safeKeepingProjectList
        restSafeKeepingProjectMockMvc.perform(get("/api/safe-keeping-projects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(safeKeepingProject.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].parent1").value(hasItem(DEFAULT_PARENT_1)))
            .andExpect(jsonPath("$.[*].parent2").value(hasItem(DEFAULT_PARENT_2)))
            .andExpect(jsonPath("$.[*].mediator").value(hasItem(DEFAULT_MEDIATOR)))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())));
    }
    
    @Test
    @Transactional
    public void getSafeKeepingProject() throws Exception {
        // Initialize the database
        safeKeepingProjectRepository.saveAndFlush(safeKeepingProject);

        // Get the safeKeepingProject
        restSafeKeepingProjectMockMvc.perform(get("/api/safe-keeping-projects/{id}", safeKeepingProject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(safeKeepingProject.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.parent1").value(DEFAULT_PARENT_1))
            .andExpect(jsonPath("$.parent2").value(DEFAULT_PARENT_2))
            .andExpect(jsonPath("$.mediator").value(DEFAULT_MEDIATOR))
            .andExpect(jsonPath("$.start").value(DEFAULT_START.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingSafeKeepingProject() throws Exception {
        // Get the safeKeepingProject
        restSafeKeepingProjectMockMvc.perform(get("/api/safe-keeping-projects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSafeKeepingProject() throws Exception {
        // Initialize the database
        safeKeepingProjectRepository.saveAndFlush(safeKeepingProject);

        int databaseSizeBeforeUpdate = safeKeepingProjectRepository.findAll().size();

        // Update the safeKeepingProject
        SafeKeepingProject updatedSafeKeepingProject = safeKeepingProjectRepository.findById(safeKeepingProject.getId()).get();
        // Disconnect from session so that the updates on updatedSafeKeepingProject are not directly saved in db
        em.detach(updatedSafeKeepingProject);
        updatedSafeKeepingProject
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .parent1(UPDATED_PARENT_1)
            .parent2(UPDATED_PARENT_2)
            .mediator(UPDATED_MEDIATOR)
            .start(UPDATED_START);
        SafeKeepingProjectDTO safeKeepingProjectDTO = safeKeepingProjectMapper.toDto(updatedSafeKeepingProject);

        restSafeKeepingProjectMockMvc.perform(put("/api/safe-keeping-projects").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(safeKeepingProjectDTO)))
            .andExpect(status().isOk());

        // Validate the SafeKeepingProject in the database
        List<SafeKeepingProject> safeKeepingProjectList = safeKeepingProjectRepository.findAll();
        assertThat(safeKeepingProjectList).hasSize(databaseSizeBeforeUpdate);
        SafeKeepingProject testSafeKeepingProject = safeKeepingProjectList.get(safeKeepingProjectList.size() - 1);
        assertThat(testSafeKeepingProject.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSafeKeepingProject.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSafeKeepingProject.getParent1()).isEqualTo(UPDATED_PARENT_1);
        assertThat(testSafeKeepingProject.getParent2()).isEqualTo(UPDATED_PARENT_2);
        assertThat(testSafeKeepingProject.getMediator()).isEqualTo(UPDATED_MEDIATOR);
        assertThat(testSafeKeepingProject.getStart()).isEqualTo(UPDATED_START);
    }

    @Test
    @Transactional
    public void updateNonExistingSafeKeepingProject() throws Exception {
        int databaseSizeBeforeUpdate = safeKeepingProjectRepository.findAll().size();

        // Create the SafeKeepingProject
        SafeKeepingProjectDTO safeKeepingProjectDTO = safeKeepingProjectMapper.toDto(safeKeepingProject);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSafeKeepingProjectMockMvc.perform(put("/api/safe-keeping-projects").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(safeKeepingProjectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SafeKeepingProject in the database
        List<SafeKeepingProject> safeKeepingProjectList = safeKeepingProjectRepository.findAll();
        assertThat(safeKeepingProjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSafeKeepingProject() throws Exception {
        // Initialize the database
        safeKeepingProjectRepository.saveAndFlush(safeKeepingProject);

        int databaseSizeBeforeDelete = safeKeepingProjectRepository.findAll().size();

        // Delete the safeKeepingProject
        restSafeKeepingProjectMockMvc.perform(delete("/api/safe-keeping-projects/{id}", safeKeepingProject.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SafeKeepingProject> safeKeepingProjectList = safeKeepingProjectRepository.findAll();
        assertThat(safeKeepingProjectList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
