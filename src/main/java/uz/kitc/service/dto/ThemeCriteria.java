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
 * Criteria class for the {@link uz.kitc.domain.Theme} entity. This class is used
 * in {@link uz.kitc.web.rest.ThemeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /themes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ThemeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private IntegerFilter number;

    private BooleanFilter isSection;

    private StringFilter about;

    private StringFilter homeWorkAbouts;

    private StringFilter fileUrl;

    private LongFilter planningId;

    public ThemeCriteria() {
    }

    public ThemeCriteria(ThemeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.number = other.number == null ? null : other.number.copy();
        this.isSection = other.isSection == null ? null : other.isSection.copy();
        this.about = other.about == null ? null : other.about.copy();
        this.homeWorkAbouts = other.homeWorkAbouts == null ? null : other.homeWorkAbouts.copy();
        this.fileUrl = other.fileUrl == null ? null : other.fileUrl.copy();
        this.planningId = other.planningId == null ? null : other.planningId.copy();
    }

    @Override
    public ThemeCriteria copy() {
        return new ThemeCriteria(this);
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

    public IntegerFilter getNumber() {
        return number;
    }

    public void setNumber(IntegerFilter number) {
        this.number = number;
    }

    public BooleanFilter getIsSection() {
        return isSection;
    }

    public void setIsSection(BooleanFilter isSection) {
        this.isSection = isSection;
    }

    public StringFilter getAbout() {
        return about;
    }

    public void setAbout(StringFilter about) {
        this.about = about;
    }

    public StringFilter getHomeWorkAbouts() {
        return homeWorkAbouts;
    }

    public void setHomeWorkAbouts(StringFilter homeWorkAbouts) {
        this.homeWorkAbouts = homeWorkAbouts;
    }

    public StringFilter getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(StringFilter fileUrl) {
        this.fileUrl = fileUrl;
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
        final ThemeCriteria that = (ThemeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(number, that.number) &&
            Objects.equals(isSection, that.isSection) &&
            Objects.equals(about, that.about) &&
            Objects.equals(homeWorkAbouts, that.homeWorkAbouts) &&
            Objects.equals(fileUrl, that.fileUrl) &&
            Objects.equals(planningId, that.planningId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        number,
        isSection,
        about,
        homeWorkAbouts,
        fileUrl,
        planningId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ThemeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (number != null ? "number=" + number + ", " : "") +
                (isSection != null ? "isSection=" + isSection + ", " : "") +
                (about != null ? "about=" + about + ", " : "") +
                (homeWorkAbouts != null ? "homeWorkAbouts=" + homeWorkAbouts + ", " : "") +
                (fileUrl != null ? "fileUrl=" + fileUrl + ", " : "") +
                (planningId != null ? "planningId=" + planningId + ", " : "") +
            "}";
    }

}
