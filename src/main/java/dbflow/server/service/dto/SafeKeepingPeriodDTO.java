package dbflow.server.service.dto;

import java.time.LocalDate;
import java.io.Serializable;

/**
 * A DTO for the {@link dbflow.server.domain.SafeKeepingPeriod} entity.
 */
public class SafeKeepingPeriodDTO implements Serializable {
    
    private Long id;

    private String year;

    private String owner;

    private String text;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private String backgroundColor;

    private String textColor;


    private Long safeKeepingProjectId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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

    public Long getSafeKeepingProjectId() {
        return safeKeepingProjectId;
    }

    public void setSafeKeepingProjectId(Long safeKeepingProjectId) {
        this.safeKeepingProjectId = safeKeepingProjectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SafeKeepingPeriodDTO)) {
            return false;
        }

        return id != null && id.equals(((SafeKeepingPeriodDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SafeKeepingPeriodDTO{" +
            "id=" + getId() +
            ", year='" + getYear() + "'" +
            ", owner='" + getOwner() + "'" +
            ", text='" + getText() + "'" +
            ", description='" + getDescription() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", backgroundColor='" + getBackgroundColor() + "'" +
            ", textColor='" + getTextColor() + "'" +
            ", safeKeepingProjectId=" + getSafeKeepingProjectId() +
            "}";
    }
}
