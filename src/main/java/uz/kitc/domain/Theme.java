package uz.kitc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Theme.
 */
@Entity
@Table(name = "theme")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Theme implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "number")
    private Integer number;

    @Column(name = "is_section")
    private Boolean isSection;

    @Column(name = "about")
    private String about;

    @Column(name = "home_work_abouts")
    private String homeWorkAbouts;

    @Column(name = "file_url")
    private String fileUrl;

    @ManyToOne
    @JsonIgnoreProperties(value = "themes", allowSetters = true)
    private Planning planning;

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

    public Theme name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public Theme number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Boolean isIsSection() {
        return isSection;
    }

    public Theme isSection(Boolean isSection) {
        this.isSection = isSection;
        return this;
    }

    public void setIsSection(Boolean isSection) {
        this.isSection = isSection;
    }

    public String getAbout() {
        return about;
    }

    public Theme about(String about) {
        this.about = about;
        return this;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getHomeWorkAbouts() {
        return homeWorkAbouts;
    }

    public Theme homeWorkAbouts(String homeWorkAbouts) {
        this.homeWorkAbouts = homeWorkAbouts;
        return this;
    }

    public void setHomeWorkAbouts(String homeWorkAbouts) {
        this.homeWorkAbouts = homeWorkAbouts;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public Theme fileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
        return this;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Planning getPlanning() {
        return planning;
    }

    public Theme planning(Planning planning) {
        this.planning = planning;
        return this;
    }

    public void setPlanning(Planning planning) {
        this.planning = planning;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Theme)) {
            return false;
        }
        return id != null && id.equals(((Theme) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Theme{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", number=" + getNumber() +
            ", isSection='" + isIsSection() + "'" +
            ", about='" + getAbout() + "'" +
            ", homeWorkAbouts='" + getHomeWorkAbouts() + "'" +
            ", fileUrl='" + getFileUrl() + "'" +
            "}";
    }
}
