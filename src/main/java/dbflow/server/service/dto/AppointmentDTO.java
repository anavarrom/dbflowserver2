package dbflow.server.service.dto;

import java.time.LocalDate;
import java.io.Serializable;

/**
 * A DTO for the {@link dbflow.server.domain.Appointment} entity.
 */
public class AppointmentDTO implements Serializable {
    
    private Long id;

    private String title;

    private String description;

    private LocalDate emittedDate;

    private LocalDate start;

    private LocalDate end;

    private Boolean allDay;

    private Boolean editable;

    private String backgroundColor;

    private String textColor;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getEmittedDate() {
        return emittedDate;
    }

    public void setEmittedDate(LocalDate emittedDate) {
        this.emittedDate = emittedDate;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public Boolean isAllDay() {
        return allDay;
    }

    public void setAllDay(Boolean allDay) {
        this.allDay = allDay;
    }

    public Boolean isEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppointmentDTO)) {
            return false;
        }

        return id != null && id.equals(((AppointmentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppointmentDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", emittedDate='" + getEmittedDate() + "'" +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            ", allDay='" + isAllDay() + "'" +
            ", editable='" + isEditable() + "'" +
            ", backgroundColor='" + getBackgroundColor() + "'" +
            ", textColor='" + getTextColor() + "'" +
            "}";
    }
}
