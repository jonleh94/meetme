package de.dhbw.meetme.rest;


import de.dhbw.meetme.database.Transaction;
import de.dhbw.meetme.database.dao.GeoDao;
import de.dhbw.meetme.database.dao.ScoreDao;
import de.dhbw.meetme.database.dao.UserDao;
import de.dhbw.meetme.domain.GeoData;
import de.dhbw.meetme.domain.ScoreBoard;
import de.dhbw.meetme.domain.User;
import de.dhbw.meetme.servlet.UserServlet;
import groovy.lang.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.io.IOException;

@Path("/api/score")
@Produces({"application/json"}) // mime type
@Singleton

public class ScoreService {

    private static final Logger log = LoggerFactory.getLogger(ScoreService.class);

    @Inject
    UserDao userDao;
    @Inject
    Transaction transaction;
    @Inject
    ScoreDao scoreDao;


    @Path("/{username}/{password}/{score}")
    @POST
    public String post(@PathParam("username") String username, @PathParam("password") String password, @PathParam("score") int score) {

        boolean check = false;
        transaction.begin();
        log.debug("Update ScoreBoard for user " + username);

        User thisuser = userDao.findByUserName(username);

        try {
            if (thisuser.getPassword().equals(UserServlet.getMD5(password))) {
                check = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (check) {
            ScoreBoard scoreBoard = scoreDao.findByUserName(username); //Pull out the requested UserName
            scoreBoard.setScore(score);
            scoreDao.persist(scoreBoard);

        } else {
            log.debug("Could not update score data: WRONG PASSWORD, please try again");
            return "SERVER: WRONG PASSWORD, please try again";
        }

        transaction.commit(); //Commit changes to the database
        return "SERVER: Operation Successful!";
    }

}
