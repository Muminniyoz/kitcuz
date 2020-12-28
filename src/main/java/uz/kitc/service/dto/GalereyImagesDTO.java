package uz.kitc.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link uz.kitc.domain.GalereyImages} entity.
 */
public class GalereyImagesDTO implements Serializable {
    
    private Long id;

    private String title;

    private String imageUrl;

    private String number;


    private Long galereyId;
    
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getGalereyId() {
        return galereyId;
    }

    public void setGalereyId(Long galereyaId) {
        this.galereyId = galereyaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GalereyImagesDTO)) {
            return false;
        }

        return id != null && id.equals(((GalereyImagesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GalereyImagesDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", number='" + getNumber() + "'" +
            ", galereyId=" + getGalereyId() +
            "}";
    }
}
