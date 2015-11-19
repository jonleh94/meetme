package de.dhbw.meetme.database.dao;

import de.dhbw.meetme.domain.Friends;
import de.dhbw.meetme.domain.GeoData;
import de.dhbw.meetme.domain.UuidId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@ApplicationScoped
public class FriendsDao extends JpaDao<UuidId, Friends> {
    private static final Logger log = LoggerFactory.getLogger(Friends.class);


    public FriendsDao() {
        super(Friends.class);
    }


    public List<String> listFriendsList(String ownusername) {
        TypedQuery<String> tquery = entityManager.createQuery("SELECT f.userfriend FROM Friends f WHERE f.ownusername = :ownusername", String.class);
        tquery.setParameter("ownusername", ownusername);
        return tquery.getResultList();
    }


    public boolean checkFriends(String ownusername, String userfriend) {
        Query query = entityManager.createQuery("SELECT f FROM Friends f WHERE f.ownusername = :ownusername AND f.userfriend = :userfriend");
        query.setParameter("ownusername", ownusername);
        query.setParameter("userfriend", userfriend);

        List newFriendList = query.getResultList();
        if (newFriendList.isEmpty()) {
            log.debug("User: " + ownusername +" has an empty list for User: " + userfriend);
            return false;
        } else {
            return true;
        }
    }
}
