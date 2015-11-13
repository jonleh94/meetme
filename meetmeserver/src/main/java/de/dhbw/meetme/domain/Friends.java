package de.dhbw.meetme.domain;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by schabi on 12.11.2015.
 */


@Entity
@XmlRootElement
public class Friends extends PersistentObject {

    private String ownusername;
    private String userfriend;

    public String getOwnusername() {
        return ownusername;
    }

    public void setOwnusername(String ownusername) {
        this.ownusername = ownusername;
    }

    public String getUserfriend() {
        return userfriend;
    }

    public void setUserfriend(String userfriend) {
        this.userfriend = userfriend;
    }

    @Override
    public String toString() {
        return "GeoData{" + "OwnUser" + getOwnusername() + "OtherUser='" + getUserfriend() +
                '}';
    }
}

