package uz.kitc.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import uz.kitc.domain.enumeration.FileGroup;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link uz.kitc.domain.SystemFiles} entity. This class is used
 * in {@link uz.kitc.web.rest.SystemFilesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /system-files?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SystemFilesCriteria implements Serializable, Criteria {
    /**
     * Class for filtering FileGroup
     */
    public static class FileGroupFilter extends Filter<FileGroup> {

        public FileGroupFilter() {
        }

        public FileGroupFilter(FileGroupFilter filter) {
            super(filter);
        }

        @Override
        public FileGroupFilter copy() {
            return new FileGroupFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter hashName;

    private StringFilter type;

    private InstantFilter time;

    private FileGroupFilter fileGroup;

    public SystemFilesCriteria() {
    }

    public SystemFilesCriteria(SystemFilesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.hashName = other.hashName == null ? null : other.hashName.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.time = other.time == null ? null : other.time.copy();
        this.fileGroup = other.fileGroup == null ? null : other.fileGroup.copy();
    }

    @Override
    public SystemFilesCriteria copy() {
        return new SystemFilesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getHashName() {
        return hashName;
    }

    public void setHashName(StringFilter hashName) {
        this.hashName = hashName;
    }

    public StringFilter getType() {
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public InstantFilter getTime() {
        return time;
    }

    public void setTime(InstantFilter time) {
        this.time = time;
    }

    public FileGroupFilter getFileGroup() {
        return fileGroup;
    }

    public void setFileGroup(FileGroupFilter fileGroup) {
        this.fileGroup = fileGroup;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SystemFilesCriteria that = (SystemFilesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(hashName, that.hashName) &&
            Objects.equals(type, that.type) &&
            Objects.equals(time, that.time) &&
            Objects.equals(fileGroup, that.fileGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        hashName,
        type,
        time,
        fileGroup
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SystemFilesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (hashName != null ? "hashName=" + hashName + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (time != null ? "time=" + time + ", " : "") +
                (fileGroup != null ? "fileGroup=" + fileGroup + ", " : "") +
            "}";
    }

}
