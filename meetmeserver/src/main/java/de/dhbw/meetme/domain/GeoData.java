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
    private double longitude;        // Längengrad
    private double latitude;         // Breitengrad
    private Date date;
    private String team;
    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


    @Override
    public String toString() {
        return "GeoData{" + "username" + getUsername() + "Längengrad='" + getLongitude() + '\'' + ", Breitengrad='" + getLatitude() +
                '}';
    }
}
