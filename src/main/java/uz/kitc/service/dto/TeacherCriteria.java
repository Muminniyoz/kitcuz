package uz.kitc.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import uz.kitc.domain.enumeration.Gender;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link uz.kitc.domain.Teacher} entity. This class is used
 * in {@link uz.kitc.web.rest.TeacherResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /teachers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TeacherCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Gender
     */
    public static class GenderFilter extends Filter<Gender> {

        public GenderFilter() {
        }

        public GenderFilter(GenderFilter filter) {
            super(filter);
        }

        @Override
        public GenderFilter copy() {
            return new GenderFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter firstName;

    private StringFilter lastName;

    private StringFilter middleName;

    private StringFilter email;

    private LocalDateFilter dateOfBirth;

    private GenderFilter gender;

    private LocalDateFilter registerationDate;

    private ZonedDateTimeFilter lastAccess;

    private StringFilter telephone;

    private StringFilter mobile;

    private StringFilter thumbnailPhotoUrl;

    private StringFilter fullPhotoUrl;

    private BooleanFilter active;

    private StringFilter key;

    private StringFilter about;

    private LocalDateFilter leaveDate;

    private BooleanFilter isShowingHome;

    private StringFilter imageUrl;

    private LongFilter skillsId;

    public TeacherCriteria() {
    }

    public TeacherCriteria(TeacherCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.middleName = other.middleName == null ? null : other.middleName.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.dateOfBirth = other.dateOfBirth == null ? null : other.dateOfBirth.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.registerationDate = other.registerationDate == null ? null : other.registerationDate.copy();
        this.lastAccess = other.lastAccess == null ? null : other.lastAccess.copy();
        this.telephone = other.telephone == null ? null : other.telephone.copy();
        this.mobile = other.mobile == null ? null : other.mobile.copy();
        this.thumbnailPhotoUrl = other.thumbnailPhotoUrl == null ? null : other.thumbnailPhotoUrl.copy();
        this.fullPhotoUrl = other.fullPhotoUrl == null ? null : other.fullPhotoUrl.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.key = other.key == null ? null : other.key.copy();
        this.about = other.about == null ? null : other.about.copy();
        this.leaveDate = other.leaveDate == null ? null : other.leaveDate.copy();
        this.isShowingHome = other.isShowingHome == null ? null : other.isShowingHome.copy();
        this.imageUrl = other.imageUrl == null ? null : other.imageUrl.copy();
        this.skillsId = other.skillsId == null ? null : other.skillsId.copy();
    }

    @Override
    public TeacherCriteria copy() {
        return new TeacherCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFirstName() {
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getMiddleName() {
        return middleName;
    }

    public void setMiddleName(StringFilter middleName) {
        this.middleName = middleName;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public LocalDateFilter getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateFilter dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public GenderFilter getGender() {
        return gender;
    }

    public void setGender(GenderFilter gender) {
        this.gender = gender;
    }

    public LocalDateFilter getRegisterationDate() {
        return registerationDate;
    }

    public void setRegisterationDate(LocalDateFilter registerationDate) {
        this.registerationDate = registerationDate;
    }

    public ZonedDateTimeFilter getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(ZonedDateTimeFilter lastAccess) {
        this.lastAccess = lastAccess;
    }

    public StringFilter getTelephone() {
        return telephone;
    }

    public void setTelephone(StringFilter telephone) {
        this.telephone = telephone;
    }

    public StringFilter getMobile() {
        return mobile;
    }

    public void setMobile(StringFilter mobile) {
        this.mobile = mobile;
    }

    public StringFilter getThumbnailPhotoUrl() {
        return thumbnailPhotoUrl;
    }

    public void setThumbnailPhotoUrl(StringFilter thumbnailPhotoUrl) {
        this.thumbnailPhotoUrl = thumbnailPhotoUrl;
    }

    public StringFilter getFullPhotoUrl() {
        return fullPhotoUrl;
    }

    public void setFullPhotoUrl(StringFilter fullPhotoUrl) {
        this.fullPhotoUrl = fullPhotoUrl;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public StringFilter getKey() {
        return key;
    }

    public void setKey(StringFilter key) {
        this.key = key;
    }

    public StringFilter getAbout() {
        return about;
    }

    public void setAbout(StringFilter about) {
        this.about = about;
    }

    public LocalDateFilter getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(LocalDateFilter leaveDate) {
        this.leaveDate = leaveDate;
    }

    public BooleanFilter getIsShowingHome() {
        return isShowingHome;
    }

    public void setIsShowingHome(BooleanFilter isShowingHome) {
        this.isShowingHome = isShowingHome;
    }

    public StringFilter getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(StringFilter imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LongFilter getSkillsId() {
        return skillsId;
    }

    public void setSkillsId(LongFilter skillsId) {
        this.skillsId = skillsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TeacherCriteria that = (TeacherCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(middleName, that.middleName) &&
            Objects.equals(email, that.email) &&
            Objects.equals(dateOfBirth, that.dateOfBirth) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(registerationDate, that.registerationDate) &&
            Objects.equals(lastAccess, that.lastAccess) &&
            Objects.equals(telephone, that.telephone) &&
            Objects.equals(mobile, that.mobile) &&
            Objects.equals(thumbnailPhotoUrl, that.thumbnailPhotoUrl) &&
            Objects.equals(fullPhotoUrl, that.fullPhotoUrl) &&
            Objects.equals(active, that.active) &&
            Objects.equals(key, that.key) &&
            Objects.equals(about, that.about) &&
            Objects.equals(leaveDate, that.leaveDate) &&
            Objects.equals(isShowingHome, that.isShowingHome) &&
            Objects.equals(imageUrl, that.imageUrl) &&
            Objects.equals(skillsId, that.skillsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        firstName,
        lastName,
        middleName,
        email,
        dateOfBirth,
        gender,
        registerationDate,
        lastAccess,
        telephone,
        mobile,
        thumbnailPhotoUrl,
        fullPhotoUrl,
        active,
        key,
        about,
        leaveDate,
        isShowingHome,
        imageUrl,
        skillsId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TeacherCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (firstName != null ? "firstName=" + firstName + ", " : "") +
                (lastName != null ? "lastName=" + lastName + ", " : "") +
                (middleName != null ? "middleName=" + middleName + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (dateOfBirth != null ? "dateOfBirth=" + dateOfBirth + ", " : "") +
                (gender != null ? "gender=" + gender + ", " : "") +
                (registerationDate != null ? "registerationDate=" + registerationDate + ", " : "") +
                (lastAccess != null ? "lastAccess=" + lastAccess + ", " : "") +
                (telephone != null ? "telephone=" + telephone + ", " : "") +
                (mobile != null ? "mobile=" + mobile + ", " : "") +
                (thumbnailPhotoUrl != null ? "thumbnailPhotoUrl=" + thumbnailPhotoUrl + ", " : "") +
                (fullPhotoUrl != null ? "fullPhotoUrl=" + fullPhotoUrl + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (key != null ? "key=" + key + ", " : "") +
                (about != null ? "about=" + about + ", " : "") +
                (leaveDate != null ? "leaveDate=" + leaveDate + ", " : "") +
                (isShowingHome != null ? "isShowingHome=" + isShowingHome + ", " : "") +
                (imageUrl != null ? "imageUrl=" + imageUrl + ", " : "") +
                (skillsId != null ? "skillsId=" + skillsId + ", " : "") +
            "}";
    }

}
