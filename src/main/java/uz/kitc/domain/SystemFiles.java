package uz.kitc.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import uz.kitc.domain.enumeration.FileGroup;

/**
 * Entities START
 */
@Entity
@Table(name = "system_files")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SystemFiles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    
    @Column(name = "hash_name", unique = true)
    private String hashName;

    @Size(max = 5)
    @Column(name = "type", length = 5)
    private String type;

    @Lob
    @Column(name = "file")
    private byte[] file;

    @Column(name = "file_content_type")
    private String fileContentType;

    @Column(name = "time")
    private Instant time;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_group")
    private FileGroup fileGroup;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public SystemFiles name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHashName() {
        return hashName;
    }

    public SystemFiles hashName(String hashName) {
        this.hashName = hashName;
        return this;
    }

    public void setHashName(String hashName) {
        this.hashName = hashName;
    }

    public String getType() {
        return type;
    }

    public SystemFiles type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getFile() {
        return file;
    }

    public SystemFiles file(byte[] file) {
        this.file = file;
        return this;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public SystemFiles fileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
        return this;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public Instant getTime() {
        return time;
    }

    public SystemFiles time(Instant time) {
        this.time = time;
        return this;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public FileGroup getFileGroup() {
        return fileGroup;
    }

    public SystemFiles fileGroup(FileGroup fileGroup) {
        this.fileGroup = fileGroup;
        return this;
    }

    public void setFileGroup(FileGroup fileGroup) {
        this.fileGroup = fileGroup;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SystemFiles)) {
            return false;
        }
        return id != null && id.equals(((SystemFiles) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SystemFiles{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", hashName='" + getHashName() + "'" +
            ", type='" + getType() + "'" +
            ", file='" + getFile() + "'" +
            ", fileContentType='" + getFileContentType() + "'" +
            ", time='" + getTime() + "'" +
            ", fileGroup='" + getFileGroup() + "'" +
            "}";
    }
}
