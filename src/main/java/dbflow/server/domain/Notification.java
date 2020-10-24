package dbflow.server.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

import dbflow.server.domain.enumeration.NotificationStatus;

/**
 * A Notification.
 */
@Entity
@Table(name = "notification")
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "subject")
    private String subject;

    @Column(name = "body")
    private String body;

    @Column(name = "jhi_from")
    private String from;

    @Column(name = "jhi_to")
    private String to;

    @Column(name = "emitted_date")
    private LocalDate emittedDate;

    @Column(name = "read_date")
    private LocalDate readDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private NotificationStatus status;

    @Column(name = "from_id")
    private Long fromId;

    @Column(name = "to_id")
    private Long toId;

    @Column(name = "chat_id")
    private Long chatId;

    @OneToOne
    @JoinColumn(unique = true)
    private Appointment appointment;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public Notification subject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public Notification body(String body) {
        this.body = body;
        return this;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getFrom() {
        return from;
    }

    public Notification from(String from) {
        this.from = from;
        return this;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public Notification to(String to) {
        this.to = to;
        return this;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public LocalDate getEmittedDate() {
        return emittedDate;
    }

    public Notification emittedDate(LocalDate emittedDate) {
        this.emittedDate = emittedDate;
        return this;
    }

    public void setEmittedDate(LocalDate emittedDate) {
        this.emittedDate = emittedDate;
    }

    public LocalDate getReadDate() {
        return readDate;
    }

    public Notification readDate(LocalDate readDate) {
        this.readDate = readDate;
        return this;
    }

    public void setReadDate(LocalDate readDate) {
        this.readDate = readDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Notification dueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public Notification status(NotificationStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public Long getFromId() {
        return fromId;
    }

    public Notification fromId(Long fromId) {
        this.fromId = fromId;
        return this;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public Long getToId() {
        return toId;
    }

    public Notification toId(Long toId) {
        this.toId = toId;
        return this;
    }

    public void setToId(Long toId) {
        this.toId = toId;
    }

    public Long getChatId() {
        return chatId;
    }

    public Notification chatId(Long chatId) {
        this.chatId = chatId;
        return this;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public Notification appointment(Appointment appointment) {
        this.appointment = appointment;
        return this;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Notification)) {
            return false;
        }
        return id != null && id.equals(((Notification) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Notification{" +
            "id=" + getId() +
            ", subject='" + getSubject() + "'" +
            ", body='" + getBody() + "'" +
            ", from='" + getFrom() + "'" +
            ", to='" + getTo() + "'" +
            ", emittedDate='" + getEmittedDate() + "'" +
            ", readDate='" + getReadDate() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", fromId=" + getFromId() +
            ", toId=" + getToId() +
            ", chatId=" + getChatId() +
            "}";
    }
}
