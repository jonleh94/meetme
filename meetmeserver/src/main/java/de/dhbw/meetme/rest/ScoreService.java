package de.dhbw.meetme.rest;


import de.dhbw.meetme.database.Transaction;
import de.dhbw.meetme.database.dao.GeoDao;
import de.dhbw.meetme.database.dao.ScoreDao;
import de.dhbw.meetme.database.dao.UserDao;
import de.dhbw.meetme.domain.GeoData;
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
    public void post(@PathParam("username") String username, @PathParam("password") String password, @PathParam("score") int score) {

        transaction.begin();

        log.debug("Update ScoreBoard for user " + username);

            ScoreBoard scoreBoard = scoreDao.findByUserName(username); //Pull out the requested UserName
            scoreBoard.setScore(score);
            scoreDao.persist(scoreBoard);
            transaction.commit(); //Commit changes to the database

        } /**catch (Exception e) {
            ScoreBoard scoreBoard = new ScoreBoard();
            scoreBoard.setUsername(username);
            scoreBoard.setScore(score);
            scoreDao.persist(scoreBoard);
            transaction.commit(); //Commit changes to the database
        }*/

    }
