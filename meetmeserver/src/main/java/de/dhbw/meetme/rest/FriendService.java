package de.dhbw.meetme.rest;

import de.dhbw.meetme.database.Transaction;
import de.dhbw.meetme.database.dao.FriendsDao;
import de.dhbw.meetme.domain.Friends;
import de.dhbw.meetme.logic.FriendsLogic;
import groovy.lang.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Path("/api/friend")
@Produces({"application/json"}) // mime type
@Singleton
public class FriendService {
    private static final Logger log = LoggerFactory.getLogger(MeetmeService.class);
    @Inject
    Transaction transaction;
    @Inject
    FriendsLogic friendsLogic;
    @Inject
    FriendsDao friendsDao;

    //Create new friend for user(ownuser)
    @Path("/{ownusername}/{userfriend}")
    @POST
    public String postToFriends(@PathParam("ownusername") String ownusername, @PathParam("userfriend") String userfriend) {

        transaction.begin();
        log.debug("Update Friendlist for user " + ownusername);

        friendsLogic.setNewFriend(ownusername, userfriend);
        transaction.commit(); //Commit changes to the database
        return "Succesfully added: " + userfriend + " to your friendlist!";
    }

    //List all the user's(ownuser) friends
    @Path("/list/{ownusername}")
    @GET
    public String getFriendList(@PathParam("ownusername") String ownusername) {

        transaction.begin();
        log.debug("GET Friendslist");
        List<String> friendList = friendsDao.listFriendsList(ownusername);
        log.debug(friendList.toString());
        transaction.commit();
        return friendList.toString();
    }


    //Check if two players have already met
    @Path("/checkfriend/{ownusername}/{userfriend}")
    @GET
    public boolean checkFriends(@PathParam("ownusername") String ownusername, @PathParam("userfriend") String userfriend) {
        boolean check;
        transaction.begin();
        log.debug("CHECK Friendship of " + ownusername + " and " + userfriend);

        check = friendsDao.checkFriends(ownusername, userfriend);
        transaction.commit();
        return check; //return false if the users are not friends and returns true if the users have already met
    }

}
