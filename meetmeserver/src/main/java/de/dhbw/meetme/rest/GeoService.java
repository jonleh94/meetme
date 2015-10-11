package de.dhbw.meetme.rest;


import de.dhbw.meetme.database.Transaction;
import de.dhbw.meetme.database.dao.UserDao;
import de.dhbw.meetme.domain.User;
import groovy.lang.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;

/**
 * Webservice to handle the HTTP request ..../api/username/password/longitude/latitude
 * updates a User with GeoData :)
 * works properly
 */


@Path("/api/login")
@Produces({"application/json"}) // mime type
@Singleton

public class GeoService {
    private static final Logger log = LoggerFactory.getLogger(GeoService.class);

    @Inject
    UserDao userDao;
    @Inject
    Transaction transaction;

    @Path("/{username}/{password}/{longitude}/{latitude}")
    @PUT
    public String put(@PathParam("username") String username, @PathParam("password") String password, @PathParam("longitude") String longitude, @PathParam("latitude") String latitude) {
        transaction.begin();
        log.debug("Get user " + username);
        User thisuser = userDao.findByUserName(username); //find the username and return the User Object from the database and cast the collection to User
        if ((thisuser.getPassword()).equals(password.hashCode())) {

            thisuser.setGeoWidth(latitude);
            thisuser.setGeoLength(longitude);
            transaction.commit();
            return "Operation successful, updated GeoData for User: " + username;
        } else {
            return "WRONG PASSWORD";
        }
    }
}

