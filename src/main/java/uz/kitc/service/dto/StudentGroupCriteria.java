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
 * Criteria class for the {@link uz.kitc.domain.StudentGroup} entity. This class is used
 * in {@link uz.kitc.web.rest.StudentGroupResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /student-groups?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StudentGroupCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter startingDate;

    private BooleanFilter active;

    private StringFilter contractNumber;

    private LongFilter studentId;

    private LongFilter groupId;

    public StudentGroupCriteria() {
    }

    public StudentGroupCriteria(StudentGroupCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.startingDate = other.startingDate == null ? null : other.startingDate.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.contractNumber = other.contractNumber == null ? null : other.contractNumber.copy();
        this.studentId = other.studentId == null ? null : other.studentId.copy();
        this.groupId = other.groupId == null ? null : other.groupId.copy();
    }

    @Override
    public StudentGroupCriteria copy() {
        return new StudentGroupCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDateFilter startingDate) {
        this.startingDate = startingDate;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public StringFilter getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(StringFilter contractNumber) {
        this.contractNumber = contractNumber;
    }

    public LongFilter getStudentId() {
        return studentId;
    }

    public void setStudentId(LongFilter studentId) {
        this.studentId = studentId;
    }

    public LongFilter getGroupId() {
        return groupId;
    }

    public void setGroupId(LongFilter groupId) {
        this.groupId = groupId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StudentGroupCriteria that = (StudentGroupCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(startingDate, that.startingDate) &&
            Objects.equals(active, that.active) &&
            Objects.equals(contractNumber, that.contractNumber) &&
            Objects.equals(studentId, that.studentId) &&
            Objects.equals(groupId, that.groupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        startingDate,
        active,
        contractNumber,
        studentId,
        groupId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StudentGroupCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (startingDate != null ? "startingDate=" + startingDate + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (contractNumber != null ? "contractNumber=" + contractNumber + ", " : "") +
                (studentId != null ? "studentId=" + studentId + ", " : "") +
                (groupId != null ? "groupId=" + groupId + ", " : "") +
            "}";
    }

}
