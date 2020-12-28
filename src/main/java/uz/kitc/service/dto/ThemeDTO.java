package uz.kitc.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link uz.kitc.domain.Theme} entity.
 */
public class ThemeDTO implements Serializable {
    
    private Long id;

    private String name;

    private Integer number;

    private Boolean isSection;

    private String about;

    private String homeWorkAbouts;

    private String fileUrl;


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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Boolean isIsSection() {
        return isSection;
    }

    public void setIsSection(Boolean isSection) {
        this.isSection = isSection;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getHomeWorkAbouts() {
        return homeWorkAbouts;
    }

    public void setHomeWorkAbouts(String homeWorkAbouts) {
        this.homeWorkAbouts = homeWorkAbouts;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
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
        if (!(o instanceof ThemeDTO)) {
            return false;
        }

        return id != null && id.equals(((ThemeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ThemeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", number=" + getNumber() +
            ", isSection='" + isIsSection() + "'" +
            ", about='" + getAbout() + "'" +
            ", homeWorkAbouts='" + getHomeWorkAbouts() + "'" +
            ", fileUrl='" + getFileUrl() + "'" +
            ", planningId=" + getPlanningId() +
            "}";
    }
}
