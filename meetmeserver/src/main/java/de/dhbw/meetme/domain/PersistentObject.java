package de.dhbw.meetme.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;

/**
 *
 */
@MappedSuperclass
abstract public class PersistentObject {
    @EmbeddedId
    protected UuidId id;

    public PersistentObject() {
        id = new UuidId();
    }

    public UuidId getId() {
        return id;
    }

    public void setId(UuidId id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersistentObject)) return false;

        PersistentObject that = (PersistentObject) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "PersistentObject{" +
                "id=" + id +
                '}';
    }
}
