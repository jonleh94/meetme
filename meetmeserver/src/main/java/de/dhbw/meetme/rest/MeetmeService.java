package de.dhbw.meetme.rest;

import de.dhbw.meetme.database.Transaction;
import de.dhbw.meetme.database.dao.ScoreDao;
import de.dhbw.meetme.database.dao.UserDao;
import de.dhbw.meetme.domain.ScoreBoard;
import de.dhbw.meetme.domain.User;
import groovy.lang.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * Created by schabi on 12.10.2015.
 *
 *
 */


@Path("/api/meetme")
@Produces({"application/json"}) // mime type
@Singleton
public class MeetmeService {

    private static final Logger log = LoggerFactory.getLogger(MeetmeService.class);

    @Inject
    UserDao userDao;
    @Inject
    Transaction transaction;
    @Inject
    ScoreDao scoreDao;

    @Path("/{ownusername}/{meetmecode}/{foreignusername}")
    @POST
    public String meetme(@PathParam("ownusername") String ownusername, @PathParam("meetmecode") String meetmecode, @PathParam("foreignusername") String foreignusername) {

        transaction.begin();
        log.debug("Meetme Process for User " + ownusername);

        User ownuser = userDao.findByUserName(ownusername);
        User foreignuser = userDao.findByUserName(foreignusername);

        //Check if the entered code is equal to the Code in the database AND if the team is equal for both players
        if ((meetmecode.equals(foreignuser.getMeetmecode())) && (ownuser.getTeam().equals(foreignuser.getTeam()))) {

            ScoreBoard ownuserScoreBoard = ownuser.getScoreBoard();
            ownuserScoreBoard.setScore(ownuserScoreBoard.getScore() + 1);
            scoreDao.persist(ownuserScoreBoard);

            ownuser.setScoreBoard(ownuserScoreBoard);
            userDao.persist(ownuser);
            transaction.commit();
            return "Operation successful, updated SCORE and RANK for User: " + ownusername;
        }

        else {
            return "WRONG CODE or WRONG TEAM, please try again";
        }

    }



}
