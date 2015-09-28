package de.dhbw.meetme.database.dao;


import de.dhbw.meetme.domain.GeoData;
import de.dhbw.meetme.domain.UuidId;

import javax.enterprise.context.ApplicationScoped;
import java.util.UUID;


/**
 * Created by schabi on 27.09.2015.
 */

@ApplicationScoped
public class GeoDao extends JpaDao{

    public GeoDao() {
        super(GeoData.class);
    }

}
