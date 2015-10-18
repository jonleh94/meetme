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
@XmlRootElement
public class GeoData extends PersistentObject {

    private String longitude;        // Längengrad
    private String latitude;         // Breitengrad

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }


    @Override
    public String toString() {
        return "GeoData{" + "Längengrad='" + getLongitude() + '\'' + ", Breitengrad='" + getLatitude() +
                '}';
    }
}
