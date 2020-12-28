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
 * Criteria class for the {@link uz.kitc.domain.Faq} entity. This class is used
 * in {@link uz.kitc.web.rest.FaqResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /faqs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FaqCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter orderNumber;

    private StringFilter question;

    private StringFilter answer;

    private BooleanFilter active;

    public FaqCriteria() {
    }

    public FaqCriteria(FaqCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.orderNumber = other.orderNumber == null ? null : other.orderNumber.copy();
        this.question = other.question == null ? null : other.question.copy();
        this.answer = other.answer == null ? null : other.answer.copy();
        this.active = other.active == null ? null : other.active.copy();
    }

    @Override
    public FaqCriteria copy() {
        return new FaqCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(IntegerFilter orderNumber) {
        this.orderNumber = orderNumber;
    }

    public StringFilter getQuestion() {
        return question;
    }

    public void setQuestion(StringFilter question) {
        this.question = question;
    }

    public StringFilter getAnswer() {
        return answer;
    }

    public void setAnswer(StringFilter answer) {
        this.answer = answer;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FaqCriteria that = (FaqCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(orderNumber, that.orderNumber) &&
            Objects.equals(question, that.question) &&
            Objects.equals(answer, that.answer) &&
            Objects.equals(active, that.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        orderNumber,
        question,
        answer,
        active
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FaqCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (orderNumber != null ? "orderNumber=" + orderNumber + ", " : "") +
                (question != null ? "question=" + question + ", " : "") +
                (answer != null ? "answer=" + answer + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
            "}";
    }

}
