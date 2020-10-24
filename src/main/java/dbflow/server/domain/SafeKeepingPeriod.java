package dbflow.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A SafeKeepingPeriod.
 */
@Entity
@Table(name = "safe_keeping_period")
public class SafeKeepingPeriod implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "year")
    private String year;

    @Column(name = "owner")
    private String owner;

    @Column(name = "text")
    private String text;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "background_color")
    private String backgroundColor;

    @Column(name = "text_color")
    private String textColor;

    @ManyToOne
    @JsonIgnoreProperties(value = "safeKeepingPeriods", allowSetters = true)
    private SafeKeepingProject safeKeepingProject;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public SafeKeepingPeriod year(String year) {
        this.year = year;
        return this;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getOwner() {
        return owner;
    }

    public SafeKeepingPeriod owner(String owner) {
        this.owner = owner;
        return this;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getText() {
        return text;
    }

    public SafeKeepingPeriod text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDescription() {
        return description;
    }

    public SafeKeepingPeriod description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public SafeKeepingPeriod startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public SafeKeepingPeriod endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public SafeKeepingPeriod backgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getTextColor() {
        return textColor;
    }

    public SafeKeepingPeriod textColor(String textColor) {
        this.textColor = textColor;
        return this;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public SafeKeepingProject getSafeKeepingProject() {
        return safeKeepingProject;
    }

    public SafeKeepingPeriod safeKeepingProject(SafeKeepingProject safeKeepingProject) {
        this.safeKeepingProject = safeKeepingProject;
        return this;
    }

    public void setSafeKeepingProject(SafeKeepingProject safeKeepingProject) {
        this.safeKeepingProject = safeKeepingProject;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SafeKeepingPeriod)) {
            return false;
        }
        return id != null && id.equals(((SafeKeepingPeriod) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SafeKeepingPeriod{" +
            "id=" + getId() +
            ", year='" + getYear() + "'" +
            ", owner='" + getOwner() + "'" +
            ", text='" + getText() + "'" +
            ", description='" + getDescription() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", backgroundColor='" + getBackgroundColor() + "'" +
            ", textColor='" + getTextColor() + "'" +
            "}";
    }
}
