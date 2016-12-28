package brunoc.csc.ist.pt.csc.services;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import brunoc.csc.ist.pt.csc.locationManager.DBLocationManager;

/**
 * Created by Bruno on 19-12-2016.
 */

public class Locator {
    private Context context;
    private LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    private DBLocationManager dataManager = new DBLocationManager(context);
    private int id;
    public Locator(Context context, int id) {
        this.id = id;
        this.context = context;
        LocationListener locationListener = new MyLocationListener();

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
        }

    }

    private class MyLocationListener implements LocationListener {


        @Override
        public void onLocationChanged(Location location) {
            dataManager.updateLocation(location);
            Toast.makeText(context, "location updated", Toast.LENGTH_SHORT).show();
            //queue.add(new StringRequest(Request.Method.PUT, path,))
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
