package dbflow.server.web.rest;

import dbflow.server.Dbflowserver2App;
import dbflow.server.config.TestSecurityConfiguration;
import dbflow.server.domain.Notification;
import dbflow.server.repository.NotificationRepository;
import dbflow.server.service.NotificationService;
import dbflow.server.service.dto.NotificationDTO;
import dbflow.server.service.mapper.NotificationMapper;

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

import dbflow.server.domain.enumeration.NotificationStatus;
/**
 * Integration tests for the {@link NotificationResource} REST controller.
 */
@SpringBootTest(classes = { Dbflowserver2App.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class NotificationResourceIT {

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_BODY = "AAAAAAAAAA";
    private static final String UPDATED_BODY = "BBBBBBBBBB";

    private static final String DEFAULT_FROM = "AAAAAAAAAA";
    private static final String UPDATED_FROM = "BBBBBBBBBB";

    private static final String DEFAULT_TO = "AAAAAAAAAA";
    private static final String UPDATED_TO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_EMITTED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EMITTED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_READ_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_READ_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DUE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final NotificationStatus DEFAULT_STATUS = NotificationStatus.EMITTED;
    private static final NotificationStatus UPDATED_STATUS = NotificationStatus.RECEIVED;

    private static final Long DEFAULT_FROM_ID = 1L;
    private static final Long UPDATED_FROM_ID = 2L;

    private static final Long DEFAULT_TO_ID = 1L;
    private static final Long UPDATED_TO_ID = 2L;

    private static final Long DEFAULT_CHAT_ID = 1L;
    private static final Long UPDATED_CHAT_ID = 2L;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNotificationMockMvc;

    private Notification notification;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notification createEntity(EntityManager em) {
        Notification notification = new Notification()
            .subject(DEFAULT_SUBJECT)
            .body(DEFAULT_BODY)
            .from(DEFAULT_FROM)
            .to(DEFAULT_TO)
            .emittedDate(DEFAULT_EMITTED_DATE)
            .readDate(DEFAULT_READ_DATE)
            .dueDate(DEFAULT_DUE_DATE)
            .status(DEFAULT_STATUS)
            .fromId(DEFAULT_FROM_ID)
            .toId(DEFAULT_TO_ID)
            .chatId(DEFAULT_CHAT_ID);
        return notification;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notification createUpdatedEntity(EntityManager em) {
        Notification notification = new Notification()
            .subject(UPDATED_SUBJECT)
            .body(UPDATED_BODY)
            .from(UPDATED_FROM)
            .to(UPDATED_TO)
            .emittedDate(UPDATED_EMITTED_DATE)
            .readDate(UPDATED_READ_DATE)
            .dueDate(UPDATED_DUE_DATE)
            .status(UPDATED_STATUS)
            .fromId(UPDATED_FROM_ID)
            .toId(UPDATED_TO_ID)
            .chatId(UPDATED_CHAT_ID);
        return notification;
    }

    @BeforeEach
    public void initTest() {
        notification = createEntity(em);
    }

    @Test
    @Transactional
    public void createNotification() throws Exception {
        int databaseSizeBeforeCreate = notificationRepository.findAll().size();
        // Create the Notification
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);
        restNotificationMockMvc.perform(post("/api/notifications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificationDTO)))
            .andExpect(status().isCreated());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeCreate + 1);
        Notification testNotification = notificationList.get(notificationList.size() - 1);
        assertThat(testNotification.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testNotification.getBody()).isEqualTo(DEFAULT_BODY);
        assertThat(testNotification.getFrom()).isEqualTo(DEFAULT_FROM);
        assertThat(testNotification.getTo()).isEqualTo(DEFAULT_TO);
        assertThat(testNotification.getEmittedDate()).isEqualTo(DEFAULT_EMITTED_DATE);
        assertThat(testNotification.getReadDate()).isEqualTo(DEFAULT_READ_DATE);
        assertThat(testNotification.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testNotification.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testNotification.getFromId()).isEqualTo(DEFAULT_FROM_ID);
        assertThat(testNotification.getToId()).isEqualTo(DEFAULT_TO_ID);
        assertThat(testNotification.getChatId()).isEqualTo(DEFAULT_CHAT_ID);
    }

    @Test
    @Transactional
    public void createNotificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = notificationRepository.findAll().size();

        // Create the Notification with an existing ID
        notification.setId(1L);
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotificationMockMvc.perform(post("/api/notifications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNotifications() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList
        restNotificationMockMvc.perform(get("/api/notifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notification.getId().intValue())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].body").value(hasItem(DEFAULT_BODY)))
            .andExpect(jsonPath("$.[*].from").value(hasItem(DEFAULT_FROM)))
            .andExpect(jsonPath("$.[*].to").value(hasItem(DEFAULT_TO)))
            .andExpect(jsonPath("$.[*].emittedDate").value(hasItem(DEFAULT_EMITTED_DATE.toString())))
            .andExpect(jsonPath("$.[*].readDate").value(hasItem(DEFAULT_READ_DATE.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].fromId").value(hasItem(DEFAULT_FROM_ID.intValue())))
            .andExpect(jsonPath("$.[*].toId").value(hasItem(DEFAULT_TO_ID.intValue())))
            .andExpect(jsonPath("$.[*].chatId").value(hasItem(DEFAULT_CHAT_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getNotification() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get the notification
        restNotificationMockMvc.perform(get("/api/notifications/{id}", notification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(notification.getId().intValue()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT))
            .andExpect(jsonPath("$.body").value(DEFAULT_BODY))
            .andExpect(jsonPath("$.from").value(DEFAULT_FROM))
            .andExpect(jsonPath("$.to").value(DEFAULT_TO))
            .andExpect(jsonPath("$.emittedDate").value(DEFAULT_EMITTED_DATE.toString()))
            .andExpect(jsonPath("$.readDate").value(DEFAULT_READ_DATE.toString()))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.fromId").value(DEFAULT_FROM_ID.intValue()))
            .andExpect(jsonPath("$.toId").value(DEFAULT_TO_ID.intValue()))
            .andExpect(jsonPath("$.chatId").value(DEFAULT_CHAT_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingNotification() throws Exception {
        // Get the notification
        restNotificationMockMvc.perform(get("/api/notifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNotification() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        int databaseSizeBeforeUpdate = notificationRepository.findAll().size();

        // Update the notification
        Notification updatedNotification = notificationRepository.findById(notification.getId()).get();
        // Disconnect from session so that the updates on updatedNotification are not directly saved in db
        em.detach(updatedNotification);
        updatedNotification
            .subject(UPDATED_SUBJECT)
            .body(UPDATED_BODY)
            .from(UPDATED_FROM)
            .to(UPDATED_TO)
            .emittedDate(UPDATED_EMITTED_DATE)
            .readDate(UPDATED_READ_DATE)
            .dueDate(UPDATED_DUE_DATE)
            .status(UPDATED_STATUS)
            .fromId(UPDATED_FROM_ID)
            .toId(UPDATED_TO_ID)
            .chatId(UPDATED_CHAT_ID);
        NotificationDTO notificationDTO = notificationMapper.toDto(updatedNotification);

        restNotificationMockMvc.perform(put("/api/notifications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificationDTO)))
            .andExpect(status().isOk());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeUpdate);
        Notification testNotification = notificationList.get(notificationList.size() - 1);
        assertThat(testNotification.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testNotification.getBody()).isEqualTo(UPDATED_BODY);
        assertThat(testNotification.getFrom()).isEqualTo(UPDATED_FROM);
        assertThat(testNotification.getTo()).isEqualTo(UPDATED_TO);
        assertThat(testNotification.getEmittedDate()).isEqualTo(UPDATED_EMITTED_DATE);
        assertThat(testNotification.getReadDate()).isEqualTo(UPDATED_READ_DATE);
        assertThat(testNotification.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testNotification.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testNotification.getFromId()).isEqualTo(UPDATED_FROM_ID);
        assertThat(testNotification.getToId()).isEqualTo(UPDATED_TO_ID);
        assertThat(testNotification.getChatId()).isEqualTo(UPDATED_CHAT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingNotification() throws Exception {
        int databaseSizeBeforeUpdate = notificationRepository.findAll().size();

        // Create the Notification
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotificationMockMvc.perform(put("/api/notifications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNotification() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        int databaseSizeBeforeDelete = notificationRepository.findAll().size();

        // Delete the notification
        restNotificationMockMvc.perform(delete("/api/notifications/{id}", notification.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
