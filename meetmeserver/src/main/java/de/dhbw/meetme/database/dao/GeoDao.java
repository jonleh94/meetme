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


@ApplicationScoped
public class GeoDao extends JpaDao<UuidId, GeoData> {

    public GeoDao() {
        super(GeoData.class);
    }


    public Collection<GeoData> listGeo() {
        Query q = entityManager.createQuery("SELECT g FROM " + entityClass.getName() + " g");
        return q.getResultList();
    }

    public GeoData findByUserName(String username) {
        Query query = entityManager.createQuery("SELECT g FROM GeoData g WHERE g.username = :username ORDER BY g.date DESC");
        query.setParameter("username", username);
        return (GeoData) query.getResultList().get(0);
    }


}
