package uz.kitc.service.dto;

import java.time.LocalDate;
import java.io.Serializable;

/**
 * A DTO for the {@link uz.kitc.domain.Galereya} entity.
 */
public class GalereyaDTO implements Serializable {
    
    private Long id;

    private String title;

    private LocalDate createdDate;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GalereyaDTO)) {
            return false;
        }

        return id != null && id.equals(((GalereyaDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GalereyaDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
