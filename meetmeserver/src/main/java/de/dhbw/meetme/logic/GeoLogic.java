package de.dhbw.meetme.logic;


import de.dhbw.meetme.database.dao.GeoDao;
import de.dhbw.meetme.database.dao.UserDao;
import de.dhbw.meetme.domain.GeoData;
import de.dhbw.meetme.domain.User;
import de.dhbw.meetme.servlet.UserServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;

public class GeoLogic {

    private static final Logger log = LoggerFactory.getLogger(GeoLogic.class);

    @Inject
    UserDao userDao;
    @Inject
    GeoDao geoDao;


    public static double getDistance(double lat1, double long1, double lat2, double long2) {

        double earthRadius = 6371.0; // km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLong = Math.toRadians(long2 - long1);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLong / 2);
        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c;
    }


    public String postToGeo(String username, String password, double longitude, double latitude) {

        boolean check = false;

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
        return "SERVER: Operation successful, updated GeoData for User: " + username;
    }


}
