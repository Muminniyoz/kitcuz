package uz.kitc.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import uz.kitc.domain.enumeration.GroupStatus;

/**
 * A DTO for the {@link uz.kitc.domain.CourseGroup} entity.
 */
public class CourseGroupDTO implements Serializable {
    
    private Long id;

    private String name;

    private LocalDate startDate;

    private GroupStatus status;


    private Long teacherId;

    private Long planningId;
    
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public GroupStatus getStatus() {
        return status;
    }

    public void setStatus(GroupStatus status) {
        this.status = status;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Long getPlanningId() {
        return planningId;
    }

    public void setPlanningId(Long planningId) {
        this.planningId = planningId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourseGroupDTO)) {
            return false;
        }

        return id != null && id.equals(((CourseGroupDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseGroupDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", teacherId=" + getTeacherId() +
            ", planningId=" + getPlanningId() +
            "}";
    }
}
