package uz.kitc.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import uz.kitc.domain.enumeration.Gender;

/**
 * A DTO for the {@link uz.kitc.domain.CourseRequests} entity.
 */
public class CourseRequestsDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private String middleName;

    private String email;

    private LocalDate dateOfBirth;

    private Gender gender;

    private LocalDate registerationDate;

    private String telephone;

    private String mobile;


    private Long coursesId;

    private Long coursesId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getRegisterationDate() {
        return registerationDate;
    }

    public void setRegisterationDate(LocalDate registerationDate) {
        this.registerationDate = registerationDate;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getCoursesId() {
        return coursesId;
    }

    public void setCoursesId(Long coursesId) {
        this.coursesId = coursesId;
    }

    public Long getCoursesId() {
        return coursesId;
    }

    public void setCoursesId(Long courseGroupId) {
        this.coursesId = courseGroupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourseRequestsDTO)) {
            return false;
        }

        return id != null && id.equals(((CourseRequestsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseRequestsDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", email='" + getEmail() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", gender='" + getGender() + "'" +
            ", registerationDate='" + getRegisterationDate() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", coursesId=" + getCoursesId() +
            ", coursesId=" + getCoursesId() +
            "}";
    }
}
