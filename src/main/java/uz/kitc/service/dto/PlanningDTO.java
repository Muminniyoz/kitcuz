package uz.kitc.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link uz.kitc.domain.Planning} entity.
 */
public class PlanningDTO implements Serializable {
    
    private Long id;

    private String name;

    private String about;

    private String duration;

    private String fileUrl;


    private Long courseId;

    private Long teacherId;
    
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

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long coursesId) {
        this.courseId = coursesId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlanningDTO)) {
            return false;
        }

        return id != null && id.equals(((PlanningDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlanningDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", about='" + getAbout() + "'" +
            ", duration='" + getDuration() + "'" +
            ", fileUrl='" + getFileUrl() + "'" +
            ", courseId=" + getCourseId() +
            ", teacherId=" + getTeacherId() +
            "}";
    }
}
