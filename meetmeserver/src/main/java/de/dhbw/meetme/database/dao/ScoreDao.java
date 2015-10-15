package de.dhbw.meetme.database.dao;

import de.dhbw.meetme.domain.GeoData;
import de.dhbw.meetme.domain.ScoreBoard;
import de.dhbw.meetme.domain.UuidId;

import javax.enterprise.context.ApplicationScoped;

/**
 * Created by schabi on 03.10.2015.
 */


@ApplicationScoped
public class ScoreDao extends JpaDao <UuidId, ScoreBoard>{

    public ScoreDao() {
        super(ScoreBoard.class);
    }

}
