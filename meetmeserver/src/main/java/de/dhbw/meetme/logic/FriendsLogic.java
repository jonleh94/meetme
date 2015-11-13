package de.dhbw.meetme.logic;

import de.dhbw.meetme.database.dao.FriendsDao;
import de.dhbw.meetme.database.dao.GeoDao;
import de.dhbw.meetme.database.dao.UserDao;
import de.dhbw.meetme.domain.Friends;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * Created by schabi on 12.11.2015.
 */
public class FriendsLogic {


    private static final Logger log = LoggerFactory.getLogger(GeoLogic.class);

    @Inject
    UserDao userDao;
    @Inject
    FriendsDao friendsDao;

    public void setNewFriend(String ownusername, String userfriend) {

        Friends newFriend = new Friends();
        newFriend.setOwnusername(ownusername);
        newFriend.setUserfriend(userfriend);
        friendsDao.persist(newFriend);

    }
}
