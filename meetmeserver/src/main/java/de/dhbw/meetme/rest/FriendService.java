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
import java.util.Collection;


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

    @Path("/{ownusername}/{userfriend}")
    @POST
    public String postToFriends(@PathParam("ownusername") String ownusername, @PathParam("userfriend") String userfriend) {

        transaction.begin();
        log.debug("Update Friendlist for user " + ownusername);

        friendsLogic.setNewFriend(ownusername, userfriend);
        transaction.commit(); //Commit changes to the database
        return "Succesfully added: " + userfriend + " to your friendlist!";
    }

    @Path("/list/{ownusername}/{userfriend}")
    @GET
    public Collection<Friends> getFriendList(@PathParam("ownusername") String ownusername, @PathParam("userfriend") String userfriend) {

        transaction.begin();
        log.debug("GET Friendslist");

        Collection <Friends> friendList = friendsDao.listFriendsList(ownusername);
        transaction.commit();
        return friendList;
    }


    }
