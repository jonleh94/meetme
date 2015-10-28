package de.dhbw.meetme.rest;

import de.dhbw.meetme.database.Transaction;
import de.dhbw.meetme.database.dao.ScoreDao;
import de.dhbw.meetme.domain.ScoreBoard;
import groovy.lang.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Collection;

/**
 * Created by schabi on 27.10.2015.
 */


@Path("/api/leaderboard")
@Produces({"application/json"}) // mime type
@Singleton
public class LeaderboardService {

    private static final Logger log = LoggerFactory.getLogger(GeoService.class);

    @Inject
    Transaction transaction;
    @Inject
    ScoreDao scoreDao;

    @Path("/list")
    @GET
    public Collection<ScoreBoard> getLeaderboard() {
        transaction.begin();
        log.debug("Get Leaderboard");

        Collection<ScoreBoard> scoreBoards = scoreDao.listScore();
        transaction.commit();
        return scoreBoards;
    }


}
