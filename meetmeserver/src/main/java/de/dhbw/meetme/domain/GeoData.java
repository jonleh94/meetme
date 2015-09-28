package de.dhbw.meetme.domain;

import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * This class represents the GeoDao Table in the database
 *
 * Created by schabi on 26.09.2015.
 */

@Entity
public class GeoData extends PersistentObject {

    @EmbeddedId protected UuidId id; //primary k3y for this table
    private String geolength;        // Längengrad
    private String geowidth;         // Breitengrad

    public GeoData() {id = new UuidId();}
    public UuidId getId() {return id;}
    public void setId(UuidId id) {this.id = id;}

    public String getGeolength() {return geolength;}

    public void setGeolength(String geolength) {
        this.geolength = geolength;
    }

    public String getGeowidth() {
        return geowidth;
    }

    public void setGeowidth(String geowidth) {
        this.geowidth = geowidth;
    }

    @Override
    public String toString() {
        return "GeoData{" +
                "id='" + id + '\'' + ", Längengrad='" + getGeolength() + '\'' +", Breitengrad='" + getGeowidth() +
                '}';
    }
}
