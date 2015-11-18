package de.dhbw.meetme.rest;


import de.dhbw.meetme.database.Transaction;
import de.dhbw.meetme.database.dao.GeoDao;
import de.dhbw.meetme.database.dao.UserDao;
import de.dhbw.meetme.domain.GeoData;
import de.dhbw.meetme.domain.User;
import de.dhbw.meetme.logic.GeoLogic;
import de.dhbw.meetme.servlet.UserServlet;
import groovy.lang.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.io.IOException;
import java.util.Collection;


@Path("/api/geo")
@Produces({"application/json"}) // mime type
@Singleton

public class GeoService {
    private static final Logger log = LoggerFactory.getLogger(GeoService.class);

    @Inject
    Transaction transaction;
    @Inject
    GeoDao geoDao;
    @Inject
    UserDao userDao;
    @Inject
    GeoLogic geoLogic;

    @Path("/get/distance/{lat1}/{long1}/{lat2}/{long2}")
    @GET
    public double getDistance(@PathParam("lat1") double lat1, @PathParam("long1") double long1, @PathParam("lat2") double lat2, @PathParam("long2") double long2) {
        double dist;
        transaction.begin();
        dist =  GeoLogic.getDistance(lat1, long1, lat2, long2);
        transaction.commit();
        return dist;
    }

    @Path("/{username}/{password}/{longitude}/{latitude}")
    @POST
    public String postToGeo(@PathParam("username") String username, @PathParam("password") String password, @PathParam("longitude") double longitude, @PathParam("latitude") double latitude) {

        String returnvalue;
        transaction.begin();
        log.debug("Update GeoData for user " + username);

        returnvalue = geoLogic.postToGeo(username, password, longitude, latitude);

        transaction.commit(); //Commit changes to the database
        return returnvalue;
    }


    @Path("/list")
    @GET
    public Collection<GeoData> getGeoList() {
        transaction.begin();

        log.debug("GET GeoData LIST");

        Collection<GeoData> geoDatas = geoDao.listGeoOnlyActive();

        transaction.commit();
        return geoDatas;
    }

}


