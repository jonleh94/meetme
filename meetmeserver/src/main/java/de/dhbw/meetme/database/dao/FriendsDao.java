package de.dhbw.meetme.database.dao;

import de.dhbw.meetme.domain.Friends;
import de.dhbw.meetme.domain.GeoData;
import de.dhbw.meetme.domain.UuidId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NoResultException;
import javax.persistence.Query;
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


    public Collection<Friends> listFriendsList(String ownusername) {
        Query query = entityManager.createQuery("SELECT f FROM Friends f WHERE f.ownusername = :ownusername");
        query.setParameter("ownusername", ownusername);
        return query.getResultList();
    }


    public boolean checkFriends(String ownusername, String userfriend) {
        Friends newFriend = new Friends();
        Query query = entityManager.createQuery("SELECT f FROM Friends f WHERE f.ownusername = :ownusername AND f.userfriend = :userfriend");
        query.setParameter("ownusername", ownusername);
        query.setParameter("userfriend", userfriend);
        try {
            newFriend = (Friends) query.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
