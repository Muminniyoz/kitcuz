package uz.kitc.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link uz.kitc.domain.Faq} entity.
 */
public class FaqDTO implements Serializable {
    
    private Long id;

    private Integer orderNumber;

    private String question;

    private String answer;

    private Boolean active;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FaqDTO)) {
            return false;
        }

        return id != null && id.equals(((FaqDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FaqDTO{" +
            "id=" + getId() +
            ", orderNumber=" + getOrderNumber() +
            ", question='" + getQuestion() + "'" +
            ", answer='" + getAnswer() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
