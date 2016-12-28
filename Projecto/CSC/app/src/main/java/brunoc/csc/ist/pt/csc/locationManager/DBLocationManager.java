package brunoc.csc.ist.pt.csc.locationManager;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;

/**
 * Created by Bruno on 28-12-2016.
 */

public class DBLocationManager {
    private final Context context;
    private RequestQueue queue;
    public DBLocationManager(Context contex){
        this.context = contex;
        queue = Volley.newRequestQueue(context);
    }

    public boolean updateLocation(Location location) {
        final String body ="{[ \"latitude\":\""+location.getLatitude()+"\"],[\"longitude\":\""+location.getLongitude()+"\"]}";
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, "http://localhost:5984/locationdb/location", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("VOLLEY", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                 return body.getBytes();
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                    // can get more details such as response.headers
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        return false;
    }
}
