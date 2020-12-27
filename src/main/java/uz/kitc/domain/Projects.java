package uz.kitc.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Projects.
 */
@Entity
@Table(name = "projects")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Projects implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "about")
    private String about;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "is_showing")
    private Boolean isShowing;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Projects title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbout() {
        return about;
    }

    public Projects about(String about) {
        this.about = about;
        return this;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public Projects fileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
        return this;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public Projects createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean isIsShowing() {
        return isShowing;
    }

    public Projects isShowing(Boolean isShowing) {
        this.isShowing = isShowing;
        return this;
    }

    public void setIsShowing(Boolean isShowing) {
        this.isShowing = isShowing;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Projects)) {
            return false;
        }
        return id != null && id.equals(((Projects) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Projects{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", about='" + getAbout() + "'" +
            ", fileUrl='" + getFileUrl() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", isShowing='" + isIsShowing() + "'" +
            "}";
    }
}
