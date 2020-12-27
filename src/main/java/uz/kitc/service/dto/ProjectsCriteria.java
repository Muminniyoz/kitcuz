package uz.kitc.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link uz.kitc.domain.Projects} entity. This class is used
 * in {@link uz.kitc.web.rest.ProjectsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /projects?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProjectsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private StringFilter fileUrl;

    private LocalDateFilter createdDate;

    private BooleanFilter isShowing;

    public ProjectsCriteria() {
    }

    public ProjectsCriteria(ProjectsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.fileUrl = other.fileUrl == null ? null : other.fileUrl.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.isShowing = other.isShowing == null ? null : other.isShowing.copy();
    }

    @Override
    public ProjectsCriteria copy() {
        return new ProjectsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(StringFilter fileUrl) {
        this.fileUrl = fileUrl;
    }

    public LocalDateFilter getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateFilter createdDate) {
        this.createdDate = createdDate;
    }

    public BooleanFilter getIsShowing() {
        return isShowing;
    }

    public void setIsShowing(BooleanFilter isShowing) {
        this.isShowing = isShowing;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProjectsCriteria that = (ProjectsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(fileUrl, that.fileUrl) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(isShowing, that.isShowing);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        title,
        fileUrl,
        createdDate,
        isShowing
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (fileUrl != null ? "fileUrl=" + fileUrl + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (isShowing != null ? "isShowing=" + isShowing + ", " : "") +
            "}";
    }

}
