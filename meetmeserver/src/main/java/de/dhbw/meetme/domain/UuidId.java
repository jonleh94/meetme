package de.dhbw.meetme.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

/**
 * structure makes sense..
 * Dont get why the UUID objects are converted to Strings.
 * <p>
 * Is the SetId just existing for changing ID's later after user is created with an ID?
 */
@Embeddable
public class UuidId implements Serializable {
    @Column(length = 512)
    private String id;

    private UuidId(String id) {
        this.id = id;
    } //First contructor with parameterized String "id"

    public UuidId() {
        id = UUID.randomUUID().toString();
    } //Second constructor with no paramter -> generates UUID and converts into string

    //Method does not make any sense to me, fromString is pre defined by class UUID, why is this (Override??!!) necessary?
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static UuidId fromString(String id) {
        UUID.fromString(id); // validate
        return new UuidId(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = UUID.fromString(id).toString();
    } //Sets ID by taking ID as paramter and generates new UUID and comnverts it into string

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UuidId id1 = (UuidId) o;

        return id.equals(id1.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "UuidId{" +
                "id=" + id +
                '}';
    }

    public String asString() {
        return id;
    }
}