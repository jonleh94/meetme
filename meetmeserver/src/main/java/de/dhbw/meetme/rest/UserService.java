package de.dhbw.meetme.rest;

import de.dhbw.meetme.database.Transaction;
import de.dhbw.meetme.database.dao.GeoDao;
import de.dhbw.meetme.database.dao.UserDao;
import de.dhbw.meetme.domain.GeoData;
import de.dhbw.meetme.domain.User;
import de.dhbw.meetme.domain.UuidId;
import groovy.lang.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.Arrays;
import java.util.Collection;

/**
 *
 */
@Path("/api/user")
@Produces({"application/json"}) // mime type
@Singleton
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Inject
    UserDao userDao;
    @Inject
    Transaction transaction;


    @Path("/list")
    @GET
    public Collection<User> list() {
        log.debug("List users");
        transaction.begin();
        Collection<User> users = userDao.list();
        transaction.commit();
        return users;
    }

    @Path("/get/{id}")
    @GET
    public User get(@PathParam("id") String id) {
        log.debug("Get user " + id);
        transaction.begin();
        User user = userDao.get(UuidId.fromString(id));
        transaction.commit();
        return user;
    }


    @Path("/delete/{id}")
    @DELETE
    public void delete(@PathParam("id") String id) {
        transaction.begin();
        log.debug("Delete user " + id);
        userDao.delete(UuidId.fromString(id));
        transaction.commit();
    }

    @Path("/save")
    @PUT
    public void save(@PathParam("user") User user) {
        log.debug("Save user " + user);
        transaction.begin();
        userDao.persist(user);
        transaction.commit();
    }
}
