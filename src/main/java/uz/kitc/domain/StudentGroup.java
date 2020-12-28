package uz.kitc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A StudentGroup.
 */
@Entity
@Table(name = "student_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StudentGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "starting_date")
    private LocalDate startingDate;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "contract_number")
    private String contractNumber;

    @ManyToOne
    @JsonIgnoreProperties(value = "studentGroups", allowSetters = true)
    private Student student;

    @ManyToOne
    @JsonIgnoreProperties(value = "studentGroups", allowSetters = true)
    private CourseGroup group;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public StudentGroup startingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
        return this;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    public Boolean isActive() {
        return active;
    }

    public StudentGroup active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public StudentGroup contractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
        return this;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public Student getStudent() {
        return student;
    }

    public StudentGroup student(Student student) {
        this.student = student;
        return this;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public CourseGroup getGroup() {
        return group;
    }

    public StudentGroup group(CourseGroup courseGroup) {
        this.group = courseGroup;
        return this;
    }

    public void setGroup(CourseGroup courseGroup) {
        this.group = courseGroup;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StudentGroup)) {
            return false;
        }
        return id != null && id.equals(((StudentGroup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StudentGroup{" +
            "id=" + getId() +
            ", startingDate='" + getStartingDate() + "'" +
            ", active='" + isActive() + "'" +
            ", contractNumber='" + getContractNumber() + "'" +
            "}";
    }
}
