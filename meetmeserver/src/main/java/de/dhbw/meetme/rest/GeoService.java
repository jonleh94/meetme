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


/**
 * Webservice to handle the HTTP request ..../api/username/password/longitude/latitude
 * updates a User with GeoData :)
 * works properly
 * <p>
 * <p>
 * TESTED:
 * works when a User has no GEODATA and can update a Users GeoData when he has one allready
 * no GeoData works with Exception e handling which is not optimal
 * <p>
 * TODO:
 * FIX the Exception e Handling and insert a more specific error handling
 */


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

    @Path("/get/distance/{lat1}/{long1}/{lat2}/{long2}")
    @GET
    public static double getDistance(@PathParam("lat1") double lat1, @PathParam("long1") double long1, @PathParam("lat2") double lat2, @PathParam("long2") double long2) {

        return GeoLogic.getDistance(lat1, long1, lat2, long2);
    }

    @Path("/{username}/{password}/{longitude}/{latitude}")
    @POST
    public String postToGeo(@PathParam("username") String username, @PathParam("password") String password, @PathParam("longitude") double longitude, @PathParam("latitude") double latitude) {

        boolean check = false;

        transaction.begin();
        log.debug("Update GeoData for user " + username);

        GeoData thisgeoData = new GeoData();
        User thisuser = userDao.findByUserName(username);

        try {
            if (thisuser.getPassword().equals(UserServlet.getMD5(password))) {
                check = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (check) {
            thisgeoData.setLatitude(latitude);   // Set Latitude from the URL command
            thisgeoData.setLongitude(longitude); //Set Longitude from the URL command
            thisgeoData.setUsername(username);
            thisgeoData.setDate();
            thisgeoData.setTeam(userDao.findByUserName(username).getTeam());
            geoDao.persist(thisgeoData);
        } else {
            log.debug("COULD NOT UPDATE GEODATA: WRONG PASSWORD, please try again");
            return "SERVER: WRONG PASSWORD, please try again";
        }

        transaction.commit(); //Commit changes to the database
        return "SERVER: Operation successful, updated GeoData for User: " + username;

    }


    @Path("/list")
    @GET
    public Collection<GeoData> getGeoList() {
        transaction.begin();

        log.debug("GET GeoData LIST");

        Collection<GeoData> geoDatas = geoDao.listGeo();
        transaction.commit();
        return geoDatas;
    }

}


