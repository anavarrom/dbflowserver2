package dbflow.server.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A SafeKeepingProject.
 */
@Entity
@Table(name = "safe_keeping_project")
public class SafeKeepingProject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "parent_1")
    private String parent1;

    @Column(name = "parent_2")
    private String parent2;

    @Column(name = "mediator")
    private String mediator;

    @Column(name = "start")
    private LocalDate start;

    @OneToMany(mappedBy = "safeKeepingProject")
    private Set<SafeKeepingPeriod> safeKeepingPeriods = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public SafeKeepingProject name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public SafeKeepingProject description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParent1() {
        return parent1;
    }

    public SafeKeepingProject parent1(String parent1) {
        this.parent1 = parent1;
        return this;
    }

    public void setParent1(String parent1) {
        this.parent1 = parent1;
    }

    public String getParent2() {
        return parent2;
    }

    public SafeKeepingProject parent2(String parent2) {
        this.parent2 = parent2;
        return this;
    }

    public void setParent2(String parent2) {
        this.parent2 = parent2;
    }

    public String getMediator() {
        return mediator;
    }

    public SafeKeepingProject mediator(String mediator) {
        this.mediator = mediator;
        return this;
    }

    public void setMediator(String mediator) {
        this.mediator = mediator;
    }

    public LocalDate getStart() {
        return start;
    }

    public SafeKeepingProject start(LocalDate start) {
        this.start = start;
        return this;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public Set<SafeKeepingPeriod> getSafeKeepingPeriods() {
        return safeKeepingPeriods;
    }

    public SafeKeepingProject safeKeepingPeriods(Set<SafeKeepingPeriod> safeKeepingPeriods) {
        this.safeKeepingPeriods = safeKeepingPeriods;
        return this;
    }

    public SafeKeepingProject addSafeKeepingPeriod(SafeKeepingPeriod safeKeepingPeriod) {
        this.safeKeepingPeriods.add(safeKeepingPeriod);
        safeKeepingPeriod.setSafeKeepingProject(this);
        return this;
    }

    public SafeKeepingProject removeSafeKeepingPeriod(SafeKeepingPeriod safeKeepingPeriod) {
        this.safeKeepingPeriods.remove(safeKeepingPeriod);
        safeKeepingPeriod.setSafeKeepingProject(null);
        return this;
    }

    public void setSafeKeepingPeriods(Set<SafeKeepingPeriod> safeKeepingPeriods) {
        this.safeKeepingPeriods = safeKeepingPeriods;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SafeKeepingProject)) {
            return false;
        }
        return id != null && id.equals(((SafeKeepingProject) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SafeKeepingProject{" +
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
