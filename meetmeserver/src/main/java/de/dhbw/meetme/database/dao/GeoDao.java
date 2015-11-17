package de.dhbw.meetme.database.dao;


import de.dhbw.meetme.domain.GeoData;
import de.dhbw.meetme.domain.UuidId;
import de.dhbw.meetme.logic.GeoLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Collection;


@ApplicationScoped
public class GeoDao extends JpaDao<UuidId, GeoData> {
    private static final Logger log = LoggerFactory.getLogger(GeoDao.class);

    @Inject
    GeoLogic geoLogic;

    public GeoDao() {
        super(GeoData.class);
    }

//not needed anymore
    /*public Collection<GeoData> listGeo() {
        Query q = entityManager.createQuery("SELECT g FROM " + entityClass.getName() + " g");
        return q.getResultList();
    }*/

    public GeoData findByUserName(String username) {
        Query query = entityManager.createQuery("SELECT g FROM GeoData g WHERE g.username = :username ORDER BY g.date DESC");
        query.setParameter("username", username);
        return (GeoData) query.getResultList().get(0);
    }

    //list all geo objects that are "only"
    public Collection<GeoData> listGeoOnlyActive() {
        Query qfirst = entityManager.createQuery("SELECT g.username FROM GeoData g WHERE g.active = true GROUP BY g.username");
        Collection<String> thisList = (Collection<String>) qfirst.getResultList();
        log.debug(thisList.toString());
        return (geoLogic.iterateAll(thisList));
    }

    public void logOut(String username){
        Query q = entityManager.createQuery("UPDATE GeoData g SET g.active = false WHERE g.username = :username");
        q.setParameter("username", username);
        q.executeUpdate();
    }
}