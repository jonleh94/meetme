package de.dhbw.meetme;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.hello.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.widget.Toast;
import com.google.android.gms.maps.*;

//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapFragment;
//import com.google.android.gms.maps.UiSettings;


/**
 * Created by kraftna on 29.09.2015.
 */
public class HelloActivity extends Activity implements LocationListener, View.OnClickListener {
    private static final String HOSTNAME = "10.0.2.2";
    private static final int PORT = 8087;
    private static final String TAG = "HelloActivity";
    private LocationManager locationManager;
    private TextView anzeigeLaenge;
    private TextView anzeigeBreite;
    private EditText anzeigeUserlist;

    private Button userlistButton;
    private Button clearButton;
    private Button mapButton;
    private List<Location> positionen;
    private double lat;
    private double lng;
    private boolean geodaten;
    public boolean maperstellt;
    private GoogleMap googleMap;
    private Marker marker;
    //private String provider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout2);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        //Das Widget mit dem Steuerelement verbinden
        anzeigeBreite = (TextView) this.findViewById(R.id.textViewBreite);
        anzeigeLaenge = (TextView) this.findViewById(R.id.textViewLaenge);
        anzeigeUserlist = (EditText) this.findViewById(R.id.textViewUserlist);


        // Testen, ob GPS verfügbar, wenn nicht soll eine Meldung ausgegeben werden und die App beendet
        if (!isProviderEnabled()) {
            warnungUndBeenden();
        }

        //Userlist-Button
        userlistButton = (Button) this.findViewById(R.id.button4);
        userlistButton.setOnClickListener(this);
        //Clear-Button
        clearButton = (Button) this.findViewById(R.id.button5);
        clearButton.setOnClickListener(this);
        clearButton.setEnabled(false);
        //clearButton.setOnClickListener(this);
        //Map-Button (Layout-Wechsel)
        mapButton = (Button) this.findViewById(R.id.button6);
        mapButton.setOnClickListener(this);

        positionen = new ArrayList<Location>();
    }

    //Überprüfung ob GPS angeschaltet ist (&Internet)
    public boolean isProviderEnabled() {
        boolean gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        return (gps_enabled && network_enabled);
    }

    //Start/Stopp/Speichern untereinander agieren können
    public void onClick(View v) {

        if (v == userlistButton) {
            userlistButton.setEnabled(false);
            String test = getDaten();
            anzeigeUserlist.setText(test);
            clearButton.setEnabled(true);
        } else if (v == clearButton) {
            clearButton.setEnabled(false);
            userlistButton.setEnabled(true);
            anzeigeUserlist.setText("");
        } else if (v == mapButton && geodaten) {
            //Erstellen der Map
            LatLng User = new LatLng(lat, lng);
            try {
                if (googleMap == null) {
                    googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
                    GoogleMapOptions options = new GoogleMapOptions();
                    options.mapType(GoogleMap.MAP_TYPE_NORMAL);
                    options.compassEnabled(false);
                    googleMap.setMyLocationEnabled(true);
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(User, 15));
                    if (googleMap == null) {
                        Toast.makeText(getApplicationContext(), "Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            maperstellt = true;
            //Layout-Wechsel
            /*
            Intent nextScreen = new Intent( getApplicationContext(), Map.class );
            nextScreen.putExtra("lat", lat);
            nextScreen.putExtra("lng",lng);
            startActivity(nextScreen);
            */
        }
    }

    //Userliste bekommen
    protected String getDaten() {

        Log.e(TAG, "run client");
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            // specify the host, protocol, and port
            HttpHost target = new HttpHost(HOSTNAME, PORT, "http");
            // specify the get request
            HttpGet getRequest = new HttpGet("/meetmeserver/api/user/list");
            HttpResponse httpResponse = httpclient.execute(target, getRequest);
            HttpEntity entity = httpResponse.getEntity();
            //Log.e(TAG, EntityUtils.toString(entity));
            return EntityUtils.toString(entity);
            //Toast.makeText(this, "Userlist wird abgerufen, Verbindung steht", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Log.e(TAG, "Error: " + e);
            e.printStackTrace(System.out);
            return e.toString();
            //Toast.makeText(this, "keine Verbindung zum Browser", Toast.LENGTH_SHORT).show();
        }
    }

    protected String getMeetMeCode(String username) {

        Log.e(TAG, "??");
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            // specify the host, protocol, and port
            HttpHost target = new HttpHost(HOSTNAME, PORT, "http");
            // specify the get request
            HttpGet getRequest = new HttpGet("/meetmeserver/api/meetme/" + username);
            HttpResponse httpResponse = httpclient.execute(target, getRequest);
            HttpEntity entity = httpResponse.getEntity();
            return EntityUtils.toString(entity);
        } catch (Exception e) {
            Log.e(TAG, "Error: " + e);
            e.printStackTrace(System.out);
            return e.toString();
        }
    }

    protected void sendMeetMeData() {

        // Example Data
        // TBD as soon as UI ready
        String ownUsername = null;
        String foreignUsername = "moja";
        int foreignCode = 4491;


        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            // specify the host, protocol, and port
            HttpHost target = new HttpHost(HOSTNAME, PORT, "http");
            // specify the put request
            HttpPost postRequest = new HttpPost("/meetmeserver/api/meetme/" + ownUsername + "/" + foreignCode + "/" + foreignUsername);
            HttpResponse httpResponse = httpclient.execute(target, postRequest);
            httpResponse.toString();
        } catch (Exception e) {
            Log.e(TAG, "Error: " + e);
            e.printStackTrace(System.out);
        }
    }

    protected void sendGPSData() { //(String laenge, String breite){

        Log.e(TAG, "run client");
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            // specify the host, protocol, and port
            HttpHost target = new HttpHost(HOSTNAME, PORT, "http");
            // specify the put request
            HttpPost postRequest = new HttpPost("/meetmeserver/api/login/josie/josie/55/55"); // + laenge + "/" + breite);
            HttpResponse httpResponse = httpclient.execute(target, postRequest);
            httpResponse.toString();

        } catch (Exception e) {
            Log.e(TAG, "Error: " + e);
            e.printStackTrace(System.out);
            //Toast.makeText(this, "keine Verbindung zum Browser", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 0, this); //überprüft alle 500s den Standort wenn ein Mindesabstand von m entstanden ist
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    //Ermittlung der Breiten und Längengrade
    public void onLocationChanged(Location loc) {

        //Toast.makeText(this, "Standort wird ermittelt", Toast.LENGTH_SHORT).show();

        lng = loc.getLongitude();
        lat = loc.getLatitude();

        anzeigeBreite.setText(Location.convert(lat, Location.FORMAT_DEGREES));
        anzeigeLaenge.setText(Location.convert(lng, Location.FORMAT_DEGREES));

        geodaten = true;
        sendGPSData();

        //Hier wird der eigene Marker erstellt
        if (maperstellt) {
            //einlesen der daten aus jason format und speichern als
            LatLng User = new LatLng(lat, lng);
            String username = "User1";
            if (marker != null) {
                marker.remove();      //löscht immer nur einen marker
            }
            marker = googleMap.addMarker(new MarkerOptions().position(User).title(username).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).draggable(true)); //später sollten hier die anderen User hin
        }

        // @ Jonas: Hier müssten die Geodaten + Username der anderen User ausgegeben werden und in den Marker gesetzt werden ähnlich wie oben, dadurch dass in in onlocationchanged wird es bereits immer wieder neu durchlaufen und aktuellen Daten abgefragt, deshalb wichtig nicht nur marker setzten sondern veraltete Standorte auch zu löschen
    }

    //Wenn GPS ausgeschaltet wird soll Meldung erscheinen
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
        Toast.makeText(this, "GPS wurde ausgeschaltet, ermitteln des Standortes nicht möglich ",
                Toast.LENGTH_SHORT).show();
    }

    //Wenn GPS angeschaltet wird soll Meldung erscheinen
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
        Toast.makeText(this, "GPS wurde angeschaltet, der Standort kann nun ermittlet werden ",
                Toast.LENGTH_SHORT).show();
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }

    // Benutzer auffordern GPS zu aktivieren, beenden der Activity
    private void warnungUndBeenden() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Resources res = getResources();
        String text = res.getString(R.string.keinGPS);
        builder.setMessage(text);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

