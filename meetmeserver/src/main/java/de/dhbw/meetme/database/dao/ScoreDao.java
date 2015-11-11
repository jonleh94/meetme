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
public class ScoreDao extends JpaDao<UuidId, ScoreBoard> {

    public ScoreDao() {
        super(ScoreBoard.class);
    }

    public ScoreBoard findByUserName(String username) {
        Query query = entityManager.createQuery("SELECT s from ScoreBoard s where s.username = :username");
        query.setParameter("username", username);
        return (ScoreBoard) query.getResultList().get(0);
    }

    public Collection<ScoreBoard> list() {
        Query q = entityManager.createQuery("SELECT s from " + entityClass.getName() + " s ORDER BY s.score DESC");
        return (Collection<ScoreBoard>) q.getResultList();
    }

    public long getTeamScore(String team) {
        Query query = entityManager.createQuery("SELECT SUM(s.score) FROM ScoreBoard s WHERE s.team = :team");
        query.setParameter("team", team);
        return (Long) query.getSingleResult();
    }




}
