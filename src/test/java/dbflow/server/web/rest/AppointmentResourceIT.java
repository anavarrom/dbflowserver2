package dbflow.server.web.rest;

import dbflow.server.Dbflowserver2App;
import dbflow.server.config.TestSecurityConfiguration;
import dbflow.server.domain.Appointment;
import dbflow.server.repository.AppointmentRepository;
import dbflow.server.service.AppointmentService;
import dbflow.server.service.dto.AppointmentDTO;
import dbflow.server.service.mapper.AppointmentMapper;

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
 * Integration tests for the {@link AppointmentResource} REST controller.
 */
@SpringBootTest(classes = { Dbflowserver2App.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class AppointmentResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_EMITTED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EMITTED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_START = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_ALL_DAY = false;
    private static final Boolean UPDATED_ALL_DAY = true;

    private static final Boolean DEFAULT_EDITABLE = false;
    private static final Boolean UPDATED_EDITABLE = true;

    private static final String DEFAULT_BACKGROUND_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_BACKGROUND_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_TEXT_COLOR = "BBBBBBBBBB";

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppointmentMockMvc;

    private Appointment appointment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Appointment createEntity(EntityManager em) {
        Appointment appointment = new Appointment()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .emittedDate(DEFAULT_EMITTED_DATE)
            .start(DEFAULT_START)
            .end(DEFAULT_END)
            .allDay(DEFAULT_ALL_DAY)
            .editable(DEFAULT_EDITABLE)
            .backgroundColor(DEFAULT_BACKGROUND_COLOR)
            .textColor(DEFAULT_TEXT_COLOR);
        return appointment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Appointment createUpdatedEntity(EntityManager em) {
        Appointment appointment = new Appointment()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .emittedDate(UPDATED_EMITTED_DATE)
            .start(UPDATED_START)
            .end(UPDATED_END)
            .allDay(UPDATED_ALL_DAY)
            .editable(UPDATED_EDITABLE)
            .backgroundColor(UPDATED_BACKGROUND_COLOR)
            .textColor(UPDATED_TEXT_COLOR);
        return appointment;
    }

    @BeforeEach
    public void initTest() {
        appointment = createEntity(em);
    }

    @Test
    @Transactional
    public void createAppointment() throws Exception {
        int databaseSizeBeforeCreate = appointmentRepository.findAll().size();
        // Create the Appointment
        AppointmentDTO appointmentDTO = appointmentMapper.toDto(appointment);
        restAppointmentMockMvc.perform(post("/api/appointments").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appointmentDTO)))
            .andExpect(status().isCreated());

        // Validate the Appointment in the database
        List<Appointment> appointmentList = appointmentRepository.findAll();
        assertThat(appointmentList).hasSize(databaseSizeBeforeCreate + 1);
        Appointment testAppointment = appointmentList.get(appointmentList.size() - 1);
        assertThat(testAppointment.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testAppointment.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAppointment.getEmittedDate()).isEqualTo(DEFAULT_EMITTED_DATE);
        assertThat(testAppointment.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testAppointment.getEnd()).isEqualTo(DEFAULT_END);
        assertThat(testAppointment.isAllDay()).isEqualTo(DEFAULT_ALL_DAY);
        assertThat(testAppointment.isEditable()).isEqualTo(DEFAULT_EDITABLE);
        assertThat(testAppointment.getBackgroundColor()).isEqualTo(DEFAULT_BACKGROUND_COLOR);
        assertThat(testAppointment.getTextColor()).isEqualTo(DEFAULT_TEXT_COLOR);
    }

    @Test
    @Transactional
    public void createAppointmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appointmentRepository.findAll().size();

        // Create the Appointment with an existing ID
        appointment.setId(1L);
        AppointmentDTO appointmentDTO = appointmentMapper.toDto(appointment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppointmentMockMvc.perform(post("/api/appointments").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appointmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Appointment in the database
        List<Appointment> appointmentList = appointmentRepository.findAll();
        assertThat(appointmentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAppointments() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList
        restAppointmentMockMvc.perform(get("/api/appointments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appointment.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].emittedDate").value(hasItem(DEFAULT_EMITTED_DATE.toString())))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.toString())))
            .andExpect(jsonPath("$.[*].allDay").value(hasItem(DEFAULT_ALL_DAY.booleanValue())))
            .andExpect(jsonPath("$.[*].editable").value(hasItem(DEFAULT_EDITABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].backgroundColor").value(hasItem(DEFAULT_BACKGROUND_COLOR)))
            .andExpect(jsonPath("$.[*].textColor").value(hasItem(DEFAULT_TEXT_COLOR)));
    }
    
    @Test
    @Transactional
    public void getAppointment() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get the appointment
        restAppointmentMockMvc.perform(get("/api/appointments/{id}", appointment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appointment.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.emittedDate").value(DEFAULT_EMITTED_DATE.toString()))
            .andExpect(jsonPath("$.start").value(DEFAULT_START.toString()))
            .andExpect(jsonPath("$.end").value(DEFAULT_END.toString()))
            .andExpect(jsonPath("$.allDay").value(DEFAULT_ALL_DAY.booleanValue()))
            .andExpect(jsonPath("$.editable").value(DEFAULT_EDITABLE.booleanValue()))
            .andExpect(jsonPath("$.backgroundColor").value(DEFAULT_BACKGROUND_COLOR))
            .andExpect(jsonPath("$.textColor").value(DEFAULT_TEXT_COLOR));
    }
    @Test
    @Transactional
    public void getNonExistingAppointment() throws Exception {
        // Get the appointment
        restAppointmentMockMvc.perform(get("/api/appointments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppointment() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        int databaseSizeBeforeUpdate = appointmentRepository.findAll().size();

        // Update the appointment
        Appointment updatedAppointment = appointmentRepository.findById(appointment.getId()).get();
        // Disconnect from session so that the updates on updatedAppointment are not directly saved in db
        em.detach(updatedAppointment);
        updatedAppointment
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .emittedDate(UPDATED_EMITTED_DATE)
            .start(UPDATED_START)
            .end(UPDATED_END)
            .allDay(UPDATED_ALL_DAY)
            .editable(UPDATED_EDITABLE)
            .backgroundColor(UPDATED_BACKGROUND_COLOR)
            .textColor(UPDATED_TEXT_COLOR);
        AppointmentDTO appointmentDTO = appointmentMapper.toDto(updatedAppointment);

        restAppointmentMockMvc.perform(put("/api/appointments").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appointmentDTO)))
            .andExpect(status().isOk());

        // Validate the Appointment in the database
        List<Appointment> appointmentList = appointmentRepository.findAll();
        assertThat(appointmentList).hasSize(databaseSizeBeforeUpdate);
        Appointment testAppointment = appointmentList.get(appointmentList.size() - 1);
        assertThat(testAppointment.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testAppointment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAppointment.getEmittedDate()).isEqualTo(UPDATED_EMITTED_DATE);
        assertThat(testAppointment.getStart()).isEqualTo(UPDATED_START);
        assertThat(testAppointment.getEnd()).isEqualTo(UPDATED_END);
        assertThat(testAppointment.isAllDay()).isEqualTo(UPDATED_ALL_DAY);
        assertThat(testAppointment.isEditable()).isEqualTo(UPDATED_EDITABLE);
        assertThat(testAppointment.getBackgroundColor()).isEqualTo(UPDATED_BACKGROUND_COLOR);
        assertThat(testAppointment.getTextColor()).isEqualTo(UPDATED_TEXT_COLOR);
    }

    @Test
    @Transactional
    public void updateNonExistingAppointment() throws Exception {
        int databaseSizeBeforeUpdate = appointmentRepository.findAll().size();

        // Create the Appointment
        AppointmentDTO appointmentDTO = appointmentMapper.toDto(appointment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppointmentMockMvc.perform(put("/api/appointments").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appointmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Appointment in the database
        List<Appointment> appointmentList = appointmentRepository.findAll();
        assertThat(appointmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAppointment() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        int databaseSizeBeforeDelete = appointmentRepository.findAll().size();

        // Delete the appointment
        restAppointmentMockMvc.perform(delete("/api/appointments/{id}", appointment.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Appointment> appointmentList = appointmentRepository.findAll();
        assertThat(appointmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
