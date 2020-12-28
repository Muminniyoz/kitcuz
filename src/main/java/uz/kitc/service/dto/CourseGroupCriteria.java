package uz.kitc.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import uz.kitc.domain.enumeration.GroupStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link uz.kitc.domain.CourseGroup} entity. This class is used
 * in {@link uz.kitc.web.rest.CourseGroupResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /course-groups?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CourseGroupCriteria implements Serializable, Criteria {
    /**
     * Class for filtering GroupStatus
     */
    public static class GroupStatusFilter extends Filter<GroupStatus> {

        public GroupStatusFilter() {
        }

        public GroupStatusFilter(GroupStatusFilter filter) {
            super(filter);
        }

        @Override
        public GroupStatusFilter copy() {
            return new GroupStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LocalDateFilter startDate;

    private GroupStatusFilter status;

    private LongFilter teacherId;

    private LongFilter planningId;

    public CourseGroupCriteria() {
    }

    public CourseGroupCriteria(CourseGroupCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.teacherId = other.teacherId == null ? null : other.teacherId.copy();
        this.planningId = other.planningId == null ? null : other.planningId.copy();
    }

    @Override
    public CourseGroupCriteria copy() {
        return new CourseGroupCriteria(this);
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

    public LocalDateFilter getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateFilter startDate) {
        this.startDate = startDate;
    }

    public GroupStatusFilter getStatus() {
        return status;
    }

    public void setStatus(GroupStatusFilter status) {
        this.status = status;
    }

    public LongFilter getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(LongFilter teacherId) {
        this.teacherId = teacherId;
    }

    public LongFilter getPlanningId() {
        return planningId;
    }

    public void setPlanningId(LongFilter planningId) {
        this.planningId = planningId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CourseGroupCriteria that = (CourseGroupCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(status, that.status) &&
            Objects.equals(teacherId, that.teacherId) &&
            Objects.equals(planningId, that.planningId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        startDate,
        status,
        teacherId,
        planningId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseGroupCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (teacherId != null ? "teacherId=" + teacherId + ", " : "") +
                (planningId != null ? "planningId=" + planningId + ", " : "") +
            "}";
    }

}
