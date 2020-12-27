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

/**
 * Criteria class for the {@link uz.kitc.domain.Planning} entity. This class is used
 * in {@link uz.kitc.web.rest.PlanningResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /plannings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PlanningCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter about;

    private StringFilter duration;

    private StringFilter fileUrl;

    private LongFilter courseId;

    private LongFilter teacherId;

    public PlanningCriteria() {
    }

    public PlanningCriteria(PlanningCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.about = other.about == null ? null : other.about.copy();
        this.duration = other.duration == null ? null : other.duration.copy();
        this.fileUrl = other.fileUrl == null ? null : other.fileUrl.copy();
        this.courseId = other.courseId == null ? null : other.courseId.copy();
        this.teacherId = other.teacherId == null ? null : other.teacherId.copy();
    }

    @Override
    public PlanningCriteria copy() {
        return new PlanningCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getAbout() {
        return about;
    }

    public void setAbout(StringFilter about) {
        this.about = about;
    }

    public StringFilter getDuration() {
        return duration;
    }

    public void setDuration(StringFilter duration) {
        this.duration = duration;
    }

    public StringFilter getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(StringFilter fileUrl) {
        this.fileUrl = fileUrl;
    }

    public LongFilter getCourseId() {
        return courseId;
    }

    public void setCourseId(LongFilter courseId) {
        this.courseId = courseId;
    }

    public LongFilter getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(LongFilter teacherId) {
        this.teacherId = teacherId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PlanningCriteria that = (PlanningCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(about, that.about) &&
            Objects.equals(duration, that.duration) &&
            Objects.equals(fileUrl, that.fileUrl) &&
            Objects.equals(courseId, that.courseId) &&
            Objects.equals(teacherId, that.teacherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        about,
        duration,
        fileUrl,
        courseId,
        teacherId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlanningCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (about != null ? "about=" + about + ", " : "") +
                (duration != null ? "duration=" + duration + ", " : "") +
                (fileUrl != null ? "fileUrl=" + fileUrl + ", " : "") +
                (courseId != null ? "courseId=" + courseId + ", " : "") +
                (teacherId != null ? "teacherId=" + teacherId + ", " : "") +
            "}";
    }

}
