package de.dhbw.meetme.rest;

import de.dhbw.meetme.database.Transaction;
import de.dhbw.meetme.database.dao.FriendsDao;
import de.dhbw.meetme.database.dao.GeoDao;
import de.dhbw.meetme.database.dao.ScoreDao;
import de.dhbw.meetme.database.dao.UserDao;
import de.dhbw.meetme.domain.Friends;
import de.dhbw.meetme.domain.GeoData;
import de.dhbw.meetme.domain.ScoreBoard;
import de.dhbw.meetme.domain.User;
import de.dhbw.meetme.logic.FriendsLogic;
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

    //TODO: seperate the service and the logic into the logic activity

    private static final Logger log = LoggerFactory.getLogger(MeetmeService.class);

    @Inject
    UserDao userDao;
    @Inject
    GeoDao geoDao;
    @Inject
    ScoreDao scoreDao;
    @Inject
    Transaction transaction;
    @Inject
    FriendsLogic friendsLogic;
    @Inject
    FriendsDao friendsDao;
    @Inject
    GeoLogic geoLogic;


    @Path("/{ownusername}/{meetmecode}/{foreignusername}")
    @POST
    public String postMeetme(@PathParam("ownusername") String ownusername, @PathParam("meetmecode") int meetmecode, @PathParam("foreignusername") String foreignusername) {
        String result;
        transaction.begin();
        log.debug("Meetme Process for User " + ownusername);

        GeoData owngeoData = geoDao.findByUserName(ownusername);
        GeoData foreigngeoData = geoDao.findByUserName(foreignusername);
        User ownuser = userDao.findByUserName(ownusername);
        User foreignuser = userDao.findByUserName(foreignusername);
        ScoreBoard ownuserScoreBoard = scoreDao.findByUserName(ownusername);


        double checkdist = GeoLogic.getDistance(owngeoData.getLatitude(), owngeoData.getLongitude(), foreigngeoData.getLatitude(), foreigngeoData.getLongitude());

        if (!(friendsDao.checkFriends(ownusername, foreignusername))) { //checks if the user has already met the other user
            if (checkdist < 0.1) {
                log.debug("SERVER: Unter 100 Meter!");
                //Check if the entered code is equal to the Code in the database AND if the team is equal for both players
                if ((meetmecode == foreignuser.getMeetmecode())) {
                    if (ownuser.getTeam().equals(foreignuser.getTeam())) {
                        ownuserScoreBoard.setScore(ownuserScoreBoard.getScore() + 1);
                        friendsLogic.setNewFriend(ownusername, foreignusername); //Adds new entry to the Friends Table :)
                        scoreDao.persist(ownuserScoreBoard);
                        log.debug("Operation successful, updated SCORE and RANK for User: " + ownusername);
                        result = "true";
                    } else {
                        log.debug("SERVER: WRONG TEAM");
                        result = "wrongteam";
                    }
                } else {
                    log.debug("SERVER: WRONG CODE");
                    result = "wrongcode";
                }
            } else {
                log.debug("SERVER: DISTANCE > 100m");
                result = "toofar";
            }
        } else {
            log.debug("SERVER: You have already met " + foreignusername + "! move on buddy life goes on");
            result = "friends";
        }

        transaction.commit();
        return result;
    }


    @Path("/{ownusername}")
    @GET
    public int getMeetmecode(@PathParam("ownusername") String ownusername) {
        int meetmecode;
        transaction.begin();
        log.debug("MeetMe Pin for User " + ownusername);

        User ownuser = userDao.findByUserName(ownusername);
        meetmecode = ownuser.getMeetmecode();
        transaction.commit();
        return meetmecode;
    }


}
