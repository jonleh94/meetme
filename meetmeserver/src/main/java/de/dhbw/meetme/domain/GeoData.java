package de.dhbw.meetme.domain;

import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * This class represents the GeoDao Table in the database
 * <p>
 * Created by schabi on 26.09.2015.
 */

@Entity
@XmlRootElement
public class GeoData extends PersistentObject {

    private String username;
    private String longitude;        // Längengrad
    private String latitude;         // Breitengrad
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate() {
        this.date = new Date();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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
        return "GeoData{" + "username" + getUsername() + "Längengrad='" + getLongitude() + '\'' + ", Breitengrad='" + getLatitude() +
                '}';
    }
}
