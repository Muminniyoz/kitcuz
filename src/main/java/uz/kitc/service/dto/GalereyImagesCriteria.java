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
 * Criteria class for the {@link uz.kitc.domain.GalereyImages} entity. This class is used
 * in {@link uz.kitc.web.rest.GalereyImagesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /galerey-images?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class GalereyImagesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private StringFilter imageUrl;

    private StringFilter number;

    private LongFilter galereyId;

    public GalereyImagesCriteria() {
    }

    public GalereyImagesCriteria(GalereyImagesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.imageUrl = other.imageUrl == null ? null : other.imageUrl.copy();
        this.number = other.number == null ? null : other.number.copy();
        this.galereyId = other.galereyId == null ? null : other.galereyId.copy();
    }

    @Override
    public GalereyImagesCriteria copy() {
        return new GalereyImagesCriteria(this);
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

    public StringFilter getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(StringFilter imageUrl) {
        this.imageUrl = imageUrl;
    }

    public StringFilter getNumber() {
        return number;
    }

    public void setNumber(StringFilter number) {
        this.number = number;
    }

    public LongFilter getGalereyId() {
        return galereyId;
    }

    public void setGalereyId(LongFilter galereyId) {
        this.galereyId = galereyId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final GalereyImagesCriteria that = (GalereyImagesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(imageUrl, that.imageUrl) &&
            Objects.equals(number, that.number) &&
            Objects.equals(galereyId, that.galereyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        title,
        imageUrl,
        number,
        galereyId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GalereyImagesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (imageUrl != null ? "imageUrl=" + imageUrl + ", " : "") +
                (number != null ? "number=" + number + ", " : "") +
                (galereyId != null ? "galereyId=" + galereyId + ", " : "") +
            "}";
    }

}
