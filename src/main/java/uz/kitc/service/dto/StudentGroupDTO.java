package uz.kitc.service.dto;

import java.time.LocalDate;
import java.io.Serializable;

/**
 * A DTO for the {@link uz.kitc.domain.StudentGroup} entity.
 */
public class StudentGroupDTO implements Serializable {
    
    private Long id;

    private LocalDate startingDate;

    private Boolean active;

    private String contractNumber;


    private Long studentId;

    private Long groupId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long courseGroupId) {
        this.groupId = courseGroupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StudentGroupDTO)) {
            return false;
        }

        return id != null && id.equals(((StudentGroupDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StudentGroupDTO{" +
            "id=" + getId() +
            ", startingDate='" + getStartingDate() + "'" +
            ", active='" + isActive() + "'" +
            ", contractNumber='" + getContractNumber() + "'" +
            ", studentId=" + getStudentId() +
            ", groupId=" + getGroupId() +
            "}";
    }
}
