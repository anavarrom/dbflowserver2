package dbflow.server.service.dto;

import java.time.LocalDate;
import java.io.Serializable;

/**
 * A DTO for the {@link dbflow.server.domain.SafeKeepingProject} entity.
 */
public class SafeKeepingProjectDTO implements Serializable {
    
    private Long id;

    private String name;

    private String description;

    private String parent1;

    private String parent2;

    private String mediator;

    private LocalDate start;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParent1() {
        return parent1;
    }

    public void setParent1(String parent1) {
        this.parent1 = parent1;
    }

    public String getParent2() {
        return parent2;
    }

    public void setParent2(String parent2) {
        this.parent2 = parent2;
    }

    public String getMediator() {
        return mediator;
    }

    public void setMediator(String mediator) {
        this.mediator = mediator;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SafeKeepingProjectDTO)) {
            return false;
        }

        return id != null && id.equals(((SafeKeepingProjectDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SafeKeepingProjectDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", parent1='" + getParent1() + "'" +
            ", parent2='" + getParent2() + "'" +
            ", mediator='" + getMediator() + "'" +
            ", start='" + getStart() + "'" +
            "}";
    }
}
