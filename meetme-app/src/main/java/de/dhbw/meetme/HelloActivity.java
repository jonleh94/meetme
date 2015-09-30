package de.dhbw.meetme;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.hello.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.widget.Toast;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 * Created by kraftna on 29.09.2015.
 */
public class HelloActivity extends Activity implements LocationListener, View.OnClickListener {
    private LocationManager locationManager;
    private TextView anzeigeLaenge;
    private TextView anzeigeBreite;
    private TextView anzeigeHoehe;

    private SimpleDateFormat gpxZeitFormat;

    private Button startButton;
    private Button stoppButton;
    private Button speichernButton;
   // private Button userlistButton;
    private boolean datenSammeln;
    private List<Location> positionen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout2);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        //Das Widget mit dem Steuerelement verbinden
        anzeigeBreite = (TextView) this.findViewById(R.id.textViewBreite);
        anzeigeLaenge = (TextView) this.findViewById(R.id.textViewLaenge);
        anzeigeHoehe = (TextView) this.findViewById(R.id.textViewHoehe);

        // Testen, ob GPS verfügbar, wenn nicht soll eine Meldung ausgegeben werden und die App beendet
        if (!isProviderEnabled()) {
            warnungUndBeenden();
        }

        // StartButton
        startButton = (Button) this.findViewById(R.id.button1);
        startButton.setOnClickListener(this);
        // Delete-Button
        stoppButton = (Button) this.findViewById(R.id.button2);
        stoppButton.setOnClickListener(this);
        stoppButton.setEnabled(false);
        // Speichern-Button
        speichernButton = (Button) this.findViewById(R.id.button3);
        speichernButton.setOnClickListener(this);
        speichernButton.setEnabled(false);
        //Userlist-Button
        /* userlistButton = (Button) this.findViewById(R.id.button4);
        userlistButton.setOnClickListener(this);
        userlistButton.setEnabled(false);*/

        datenSammeln = false;
        positionen = new ArrayList<Location>();
        gpxZeitFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    }

    //Überprüfung ob GPS angeschaltet ist (&Internet)
    public boolean isProviderEnabled() {
        boolean gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // boolean network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        return (gps_enabled);  //return (gps_enabled&&network_enabled);
    }

    //Start/Stopp/Speichern untereinander agieren können
    public void onClick(View v) {
        if (v == startButton) {
            startButton.setEnabled(false);
            stoppButton.setEnabled(true);
            speichernButton.setEnabled(false);
           // userlistButton.setEnabled(false);
            datenSammeln = true;}
        else if (v == stoppButton) {
            startButton.setEnabled(true);
            stoppButton.setEnabled(false);
            speichernButton.setEnabled(true);
           // userlistButton.setEnabled(true);
            datenSammeln = false;}
        else if (v == speichernButton) {
            startButton.setEnabled(true);
            stoppButton.setEnabled(false);
            speichernButton.setEnabled(false);
           // userlistButton.setEnabled(true);
            datenSammeln = false;
            datenSpeichern();}
      /* else if(v == userlistButton){
            userlistButton.setEnabled(false);
            startButton.setEnabled(true);
            stoppButton.setEnabled(false);
            speichernButton.setEnabled(false);
            datenSammeln = false;
            //getDaten();
            }  */


    }

    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, this); //überprüft alle 500s den Standort wenn ein Mindesabstand von m entstanden ist
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    //Ermittlung der Breiten und Längengrade
    public void onLocationChanged(Location loc) {

        Toast.makeText(this, "Standort wird ermittelt", Toast.LENGTH_SHORT).show();

        double laenge = loc.getLongitude();
        double breite = loc.getLatitude();

        anzeigeBreite.setText(Location.convert(breite, Location.FORMAT_DEGREES));
        anzeigeLaenge.setText(Location.convert(laenge, Location.FORMAT_DEGREES));

        if (loc.hasAltitude()) {
            anzeigeHoehe.setText(String.valueOf(loc.getAltitude()));
        }

        if (datenSammeln) {
            positionen.add(loc);        //je nach dem speichert er die daten bzw. fügt sie der Liste hinzu welche später als Datei gespeichert werden kann
        }
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

    // Dialog für das Speichern anzeigen und Daten anschließend speichern falls gewünscht
    private void datenSpeichern() {
        try {
            final Dialog dialog = new Dialog(this);
            dialog.setOwnerActivity(this);
            dialog.setContentView(R.layout.save);

            // Dialogelemente initialisieren
            Resources res = getResources();
            dialog.setTitle(res.getText(R.string.speichernDialogTitel));

            final EditText dateiname = (EditText) dialog.findViewById(R.id.editTextDateiname);
            dateiname.setText("meinStandort.gpx");

            Button speichern = (Button) dialog.findViewById(R.id.speichernOK);
            speichern.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Editable ed = dateiname.getText();

                    try {
                        positionenSchreiben(ed.toString());
                        positionen.clear();
                    } catch (Exception ex) {
                        Log.d("carpelibrum", ex.getMessage());
                    }
                    dialog.dismiss();
                }
            });

            dialog.show();

        } catch (Exception ex) {
            Log.d("carpelibrum", ex.getMessage());
        }
    }

    // Schreibt die Daten auf die SD-Karte oder internen Speicher im GPX Format , @throws Exception
    private void positionenSchreiben(String dateiName) throws Exception {
        File sdKarte = Environment.getExternalStorageDirectory();
        boolean sdKarteVorhanden = (sdKarte.exists() && sdKarte.canWrite());
        File datei;

        if (sdKarteVorhanden) {
            datei = new File(sdKarte.getAbsolutePath() + File.separator + dateiName);
        } else {
            datei = new File(getFilesDir() + File.separator + dateiName);
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(datei));

        // Dateikopf erstellen
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
        writer.newLine();
        writer.write("<gpx xmlns=\"http://www.topografix.com/GPX/1/1\" version=\"1.1\" ");
        writer.write("creator=\"carpelibrum\" xmlns:xsi= \"http://www.w3.org/2001/XMLSchema-instance\" ");
        writer.write("xsi:schemaLocation=\"http://www.topografix.com/GPX/1/1/gpx.xsd\"");
        writer.newLine();

        // Daten schreiben
        for (Location loc : positionen) {
            locationSpeichern(loc, writer);
        }

        // Dateiende schreiben
        writer.write("</gpx>");
        writer.close();
    }

    // Übergebene Location in den gegebenen Writer schreiben @param loc @param writer
    private void locationSpeichern(Location loc, BufferedWriter writer) throws IOException {
        writer.write("<wpt lat=\"" + loc.getLatitude() + "\" lon=\"" + loc.getLongitude() + "\">");
        writer.newLine();
        writer.write("<ele>" + loc.getAltitude() + "</ele>");
        writer.newLine();

        String zeit = gpxZeitFormat.format(new Date(loc.getTime()));
        writer.write("<time>" + zeit + "</time>");
        writer.newLine();
        writer.write("</wpt>");
        writer.newLine();
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
                finish(); // Activity wird beendet
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /*public class GetDaten {

        private void callClient() {
           // setContentView(R.layout.ulisten);
            private TextView anzeigeuserlist;
            //anzeigeuserlist = (TextView) this.findViewById(R.id.userlist);
            Client c = ClientBuilder.newClient();
            WebTarget target = c.target(http://localhost:8087/meetmeserver/api");
            String responseMsg = target.path("user/list").request().get(String.class);
            //Toast.makeText(this, responseMsg, Toast.LENGTH_LONG).show();
            anzeigeuserlist.setText(responseMsg);

        }
            Button zurueck = (Button) this.findViewById(R.id.listeschließen);
            zurueck.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    setContentView(R.layout.layout2);
                }


            //in textview get text
        }
    }*/

}

