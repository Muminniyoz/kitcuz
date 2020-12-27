package uz.kitc.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link uz.kitc.domain.Projects} entity.
 */
public class ProjectsDTO implements Serializable {
    
    private Long id;

    private String title;

    @Lob
    private String about;

    private String fileUrl;

    private LocalDate createdDate;

    private Boolean isShowing;

    
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

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean isIsShowing() {
        return isShowing;
    }

    public void setIsShowing(Boolean isShowing) {
        this.isShowing = isShowing;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectsDTO)) {
            return false;
        }

        return id != null && id.equals(((ProjectsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectsDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", about='" + getAbout() + "'" +
            ", fileUrl='" + getFileUrl() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", isShowing='" + isIsShowing() + "'" +
            "}";
    }
}
