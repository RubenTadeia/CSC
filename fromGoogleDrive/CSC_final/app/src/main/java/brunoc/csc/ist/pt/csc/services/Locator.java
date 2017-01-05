package brunoc.csc.ist.pt.csc.services;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import brunoc.csc.ist.pt.csc.R;
import brunoc.csc.ist.pt.csc.controller.Controller;
import brunoc.csc.ist.pt.csc.locationManager.CloudantController;
import brunoc.csc.ist.pt.csc.model.Encriptor;
import brunoc.csc.ist.pt.csc.model.MyLocation;

/**
 * Created by Bruno on 19-12-2016.
 */

public class Locator {
    private Context context;
    private LocationManager locationManager;
    private Controller dbController;
    private int id;
    private SharedPreferences preferences;

    public Locator(Context context, int id) {
        this.id = id;
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new MyLocationListener();
        dbController = new Controller(context);
        preferences = context.getSharedPreferences(context.getResources().getString(R.string.userPreferences), Context.MODE_PRIVATE);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, preferences.getInt("locatorRate",30)*1000, 0 , locationListener);

        }

    }

    private static int counter = 0;
    /**
     * DEBUG PURPOSE ONLY
     */
    public void updateWithFakeLocation(){
        dbController.update(preferences.getString("username",""), new MyLocation(Integer.toString(counter++), Integer.toString(counter++)));
    }

    public void getLastKnownLocation() {
        Location l = null;
        boolean b1 = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        boolean b2 = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        if ( b1 && b2) {
            Toast.makeText(context, "Premission granted", Toast.LENGTH_SHORT).show();
            try {
                Location locationNet = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                dbController.update(preferences.getString("username",""), new MyLocation(Double.toString(locationNet.getLatitude()), Double.toString(locationNet.getLongitude())));
                return;
            }catch (Exception e){
                e.printStackTrace();
            }

        }

            dbController.update(preferences.getString("username",""), new MyLocation("my", "location"));
            Toast.makeText(context, "Locating", Toast.LENGTH_SHORT).show();
        //}
    }


    private class MyLocationListener implements LocationListener {


        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onLocationChanged(Location location) {
            try {
                dbController.update(preferences.getString("username", ""),
                        new MyLocation(Double.toString(location.getLatitude()), Double.toString(location.getLongitude())));
                Toast.makeText(context, "Location has changed",Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onProviderDisabled(String provider) {

        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    }
}
