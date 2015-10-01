package de.dhbw.meetme.rest;

import de.dhbw.meetme.database.Transaction;
import de.dhbw.meetme.database.dao.UserDao;
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
        return userDao.list();
    }

    @Path("/get/{id}")
    @GET
    public User get(@PathParam("id") String id) {
        log.debug("Get user " + id);
        return userDao.get(UuidId.fromString(id));
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
        userDao.persist(user);
        log.debug("Save user " + user);
    }

}
