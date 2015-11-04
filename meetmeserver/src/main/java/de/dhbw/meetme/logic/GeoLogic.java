package de.dhbw.meetme.logic;

import de.dhbw.meetme.database.Transaction;
import de.dhbw.meetme.database.dao.GeoDao;
import de.dhbw.meetme.domain.GeoData;
import groovy.lang.Singleton;

import javax.inject.Inject;


/**
 * Created by schabi on 04.11.2015.
 */


public class GeoLogic {

    public static double getDistance(double lat1, double long1, double lat2, double long2) {

        double earthRadius = 6371.0; // km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLong = Math.toRadians(long2 - long1);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLong / 2);
        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = earthRadius * c;

        return dist;
    }

}
