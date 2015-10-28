package de.dhbw.meetme.rest;


import de.dhbw.meetme.database.Transaction;
import de.dhbw.meetme.database.dao.GeoDao;
import de.dhbw.meetme.database.dao.UserDao;
import de.dhbw.meetme.domain.GeoData;
import de.dhbw.meetme.domain.User;
import groovy.lang.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.Collection;
import java.util.List;

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


    @Path("/{username}/{password}/{longitude}/{latitude}")
    @POST
    public String postToGeo(@PathParam("username") String username, @PathParam("password") String password, @PathParam("longitude") String longitude, @PathParam("latitude") String latitude) {
        transaction.begin();
        log.debug("Update GeoData for user " + username);


        GeoData thisgeoData = new GeoData();
        //User thisuser = userDao.findByUserName(username);

        thisgeoData.setLatitude(latitude);   // Set Latitude from the URL command
        thisgeoData.setLongitude(longitude); //Set Longitude from the URL command
        thisgeoData.setUsername(username);
        thisgeoData.setDate();
        geoDao.persist(thisgeoData);

        //if ((thisuser.getPassword()).equals(password.hashCode())) { //Password check, needs to be changed to the new password method

        transaction.commit(); //Commit changes to the database
            return "SERVER: Operation successful, updated GeoData for User: " + username;

        } /**else {
         log.debug("COULD NOT UPDATE GEODATA: WRONG PASSWORD, please try again");
         return "SERVER: WRONG PASSWORD, please try again";
         }*/



    @Path("/list")
    @GET
    public Collection<GeoData> getGeoList(){
        transaction.begin();

        log.debug("GET GeoData LIST");

        Collection<GeoData> geoDatas = geoDao.listGeo();
        transaction.commit();
        return geoDatas;
    }



}














/**
 *
 *
 *                      ----- OLD VERSION WITH CONNECTED TABLES WORKING -----
 *
 *
 *                       public String postToGeo(@PathParam("username") String username, @PathParam("password") String password, @PathParam("longitude") String longitude, @PathParam("latitude") String latitude) {
transaction.begin();
log.debug("Update GeoData for user " + username);

/** find the User Object by findByUserName(username) and set it as a new User Object in the GeoData table
User thisuser = userDao.findByUserName(username); //Pull out the requested UserName

try {
        GeoData thisgeoData = thisuser.getGeoData();

        thisgeoData.setLatitude(latitude);   // Set Latitude from the URL command
        thisgeoData.setLongitude(longitude); //Set Longitude from the URL command
        geoDao.persist(thisgeoData);

        //   if ((thisuser.getPassword()).equals(password.hashCode())) { //Password check, generates hashcode

        thisuser.setGeoData(thisgeoData);
        userDao.persist(thisuser);
        transaction.commit(); //Commit changes to the database
        return "SERVER: Operation successful, updated GeoData for User: " + username;

        } /**else {
 log.debug("COULD NOT UPDATE GEODATA: WRONG PASSWORD, please try again");
 return "SERVER: WRONG PASSWORD, please try again";
 } catch (Exception e) {
        GeoData thisgeoData = new GeoData(); // create new GeoData object entity
        thisgeoData.setLatitude(latitude);   // Set Latitude from the URL command
        thisgeoData.setLongitude(longitude); //Set Longitude from the URL command
        geoDao.persist(thisgeoData);

        // if ((thisuser.getPassword()).equals(password.hashCode())) { //Password check, generates hashcode

        thisuser.setGeoData(thisgeoData);
        userDao.persist(thisuser);
        transaction.commit(); //Commit changes to the database
        return "SERVER: Operation successful, updated GeoData for User: " + username;
        }
        /**else {
         log.debug("COULD NOT UPDATE GEODATA: WRONG PASSWORD, please try again");
         return "SERVER: WRONG PASSWORD, please try again";
         }

        }
 * ----------------------------------------
 */


