package de.dhbw.meetme.rest;

import de.dhbw.meetme.database.Transaction;
import de.dhbw.meetme.database.dao.GeoDao;
import de.dhbw.meetme.database.dao.ScoreDao;
import de.dhbw.meetme.database.dao.UserDao;
import de.dhbw.meetme.domain.GeoData;
import de.dhbw.meetme.domain.ScoreBoard;
import de.dhbw.meetme.domain.User;
import de.dhbw.meetme.logic.GeoLogic;
import groovy.lang.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;



@Path("/api/meetme")
@Produces({"application/json"}) // mime type
@Singleton
public class MeetmeService {

    private static final Logger log = LoggerFactory.getLogger(MeetmeService.class);

    @Inject
    UserDao userDao;
    @Inject
    GeoDao geoDao;
    @Inject
    ScoreDao scoreDao;
    @Inject
    Transaction transaction;


    @Path("/{ownusername}/{meetmecode}/{foreignusername}")
    @POST
    public String postMeetme(@PathParam("ownusername") String ownusername, @PathParam("meetmecode") int meetmecode, @PathParam("foreignusername") String foreignusername) {

        transaction.begin();
        log.debug("Meetme Process for User " + ownusername);

        GeoData owngeoData = geoDao.findByUserName(ownusername);
        GeoData foreigngeoData = geoDao.findByUserName(foreignusername);
        User ownuser = userDao.findByUserName(ownusername);
        User foreignuser = userDao.findByUserName(foreignusername);
        ScoreBoard ownuserScoreBoard = scoreDao.findByUserName(ownusername);


        double checkdist = GeoLogic.getDistance(owngeoData.getLatitude(), owngeoData.getLongitude(), foreigngeoData.getLatitude(), foreigngeoData.getLongitude());

        if (checkdist < 0.1) {
            log.debug("Unter 100 Meter!");
            //Check if the entered code is equal to the Code in the database AND if the team is equal for both players
            if ((meetmecode == foreignuser.getMeetmecode()) && (ownuser.getTeam().equals(foreignuser.getTeam()))) {
                ownuserScoreBoard.setScore(ownuserScoreBoard.getScore() + 1);
                scoreDao.persist(ownuserScoreBoard);
                transaction.commit();
                return "Operation successful, updated SCORE and RANK for User: " + ownusername;
            } else {
                return "WRONG CODE or WRONG TEAM, please try again";
            }
        } else {
            log.debug("Über 100 Meter");
            return "SERVER: DISTANCE > 100m   ------------>  CAN'T START MEETME PROCESS";
        }

    }

    @Path("/{ownusername}")
    @GET
    public int getMeetmecode(@PathParam("ownusername") String ownusername) {

        transaction.begin();
        log.debug("MeetMe Pin for User " + ownusername);

        User ownuser = userDao.findByUserName(ownusername);
        return ownuser.getMeetmecode();

    }


}
