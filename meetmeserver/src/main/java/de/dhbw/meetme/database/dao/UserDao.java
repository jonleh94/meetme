package de.dhbw.meetme.database.dao;

import de.dhbw.meetme.domain.GeoData;
import de.dhbw.meetme.domain.User;
import de.dhbw.meetme.domain.UuidId;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import java.util.Collection;

/**
 * Data Access Object for User
 * <p>
 * This class is responsible to do the CRUD (create, read, update, delete) operations
 * for user objects in the database.
 * <p>
 * There is an alternative implementation UserClassicDao using the traditional way.
 * <p>
 * Decide yourself which one you want to use.
 * You may even mix both approaches.
 */
@ApplicationScoped
public class UserDao extends JpaDao<UuidId, User> {

    public UserDao() {
        super(User.class);
    }

    //@SuppressWarnings("unchecked")
    public Collection<User> findByName(String username) {
        Query query = entityManager.createQuery("SELECT u from User u where u.username = :username");
        query.setParameter("username", username);
        return (Collection<User>) query.getResultList();
    }

    /**
     * This method represents the same method as above, but returns just ONE user object instead of a collection
     */
    public User findByUserName(String username) {
        Query query = entityManager.createQuery("SELECT u from User u where u.username = :username");
        query.setParameter("username", username);
        return (User) query.getResultList().get(0);
    }

    public String findUserColor(String username){
        Query query = entityManager.createQuery("SELECT u.team FROM User u WHERE u.username = :username");
        query.setParameter("username", username);
        return (String) query.getSingleResult();
    }
}