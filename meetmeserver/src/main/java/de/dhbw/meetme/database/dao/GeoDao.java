package de.dhbw.meetme.database.dao;


import de.dhbw.meetme.domain.GeoData;
import de.dhbw.meetme.domain.ScoreBoard;
import de.dhbw.meetme.domain.User;
import de.dhbw.meetme.domain.UuidId;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


/**
 * Created by schabi on 27.09.2015.
 */

@ApplicationScoped
public class GeoDao extends JpaDao<UuidId, GeoData> {

    public GeoDao() {
        super(GeoData.class);
    }


    public Collection<GeoData> listGeo() {
        Query q = entityManager.createQuery("SELECT g FROM " + entityClass.getName() + " g");
        return q.getResultList();
    }

}
