package de.dhbw.meetme.database.dao;

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
 *
 */
@ApplicationScoped
public class UserDao extends JpaDao<UuidId, User> {
    public UserDao() {
        super(User.class);
    }

    @SuppressWarnings("unchecked")
    Collection<User> findByName(String name) {
        Query query = entityManager.createQuery("from User u where u.name = :name");
        query.setParameter("name", name);
        return (Collection<User>) query.getResultList();
    }


}
