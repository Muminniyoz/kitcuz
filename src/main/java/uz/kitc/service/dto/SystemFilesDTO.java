package uz.kitc.service.dto;

import io.swagger.annotations.ApiModel;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;
import uz.kitc.domain.enumeration.FileGroup;

/**
 * A DTO for the {@link uz.kitc.domain.SystemFiles} entity.
 */
@ApiModel(description = "Entities START")
public class SystemFilesDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    
    private String hashName;

    @Size(max = 5)
    private String type;

    @Lob
    private byte[] file;

    private String fileContentType;
    private Instant time;

    private FileGroup fileGroup;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHashName() {
        return hashName;
    }

    public void setHashName(String hashName) {
        this.hashName = hashName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public FileGroup getFileGroup() {
        return fileGroup;
    }

    public void setFileGroup(FileGroup fileGroup) {
        this.fileGroup = fileGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SystemFilesDTO)) {
            return false;
        }

        return id != null && id.equals(((SystemFilesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SystemFilesDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", hashName='" + getHashName() + "'" +
            ", type='" + getType() + "'" +
            ", file='" + getFile() + "'" +
            ", time='" + getTime() + "'" +
            ", fileGroup='" + getFileGroup() + "'" +
            "}";
    }
}
