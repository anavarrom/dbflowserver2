package dbflow.server.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import dbflow.server.domain.enumeration.NotificationStatus;

/**
 * A DTO for the {@link dbflow.server.domain.Notification} entity.
 */
public class NotificationDTO implements Serializable {
    
    private Long id;

    private String subject;

    private String body;

    private String from;

    private String to;

    private LocalDate emittedDate;

    private LocalDate readDate;

    private LocalDate dueDate;

    private NotificationStatus status;

    private Long fromId;

    private Long toId;

    private Long chatId;


    private Long appointmentId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public LocalDate getEmittedDate() {
        return emittedDate;
    }

    public void setEmittedDate(LocalDate emittedDate) {
        this.emittedDate = emittedDate;
    }

    public LocalDate getReadDate() {
        return readDate;
    }

    public void setReadDate(LocalDate readDate) {
        this.readDate = readDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public Long getToId() {
        return toId;
    }

    public void setToId(Long toId) {
        this.toId = toId;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NotificationDTO)) {
            return false;
        }

        return id != null && id.equals(((NotificationDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotificationDTO{" +
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
            ", appointmentId=" + getAppointmentId() +
            "}";
    }
}
