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
 * Criteria class for the {@link uz.kitc.domain.News} entity. This class is used
 * in {@link uz.kitc.web.rest.NewsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /news?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NewsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private StringFilter shortText;

    private LocalDateFilter createdDate;

    private BooleanFilter active;

    private StringFilter imageUrl;

    public NewsCriteria() {
    }

    public NewsCriteria(NewsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.shortText = other.shortText == null ? null : other.shortText.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.imageUrl = other.imageUrl == null ? null : other.imageUrl.copy();
    }

    @Override
    public NewsCriteria copy() {
        return new NewsCriteria(this);
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

    public StringFilter getShortText() {
        return shortText;
    }

    public void setShortText(StringFilter shortText) {
        this.shortText = shortText;
    }

    public LocalDateFilter getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateFilter createdDate) {
        this.createdDate = createdDate;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public StringFilter getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(StringFilter imageUrl) {
        this.imageUrl = imageUrl;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final NewsCriteria that = (NewsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(shortText, that.shortText) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(active, that.active) &&
            Objects.equals(imageUrl, that.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        title,
        shortText,
        createdDate,
        active,
        imageUrl
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NewsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (shortText != null ? "shortText=" + shortText + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (imageUrl != null ? "imageUrl=" + imageUrl + ", " : "") +
            "}";
    }

}
