package de.dhbw.meetme.database.dao;

import de.dhbw.meetme.domain.GeoData;
import de.dhbw.meetme.domain.ScoreBoard;
import de.dhbw.meetme.domain.UuidId;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;

/**
 * Created by schabi on 03.10.2015.
 */


@ApplicationScoped
public class ScoreDao extends JpaDao <UuidId, ScoreBoard>{

    public ScoreDao() {
        super(ScoreBoard.class);
    }

    public ScoreBoard findByUserName(String username) {
        Query query = entityManager.createQuery("SELECT s from ScoreBoard s where s.username = :username");
        query.setParameter("username", username);
        return (ScoreBoard) query.getResultList().get(0);
    }

    public List<String> listScore() {
        Query q = entityManager.createQuery("SELECT s.username from " + entityClass.getName() + " s"); //ORDER BY s.score DESC
        return  q.getResultList();
    }

}
