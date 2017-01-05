package brunoc.csc.ist.pt.csc.controller;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.ArrayList;

import brunoc.csc.ist.pt.csc.locationManager.CloudantController;
import brunoc.csc.ist.pt.csc.model.Encriptor;
import brunoc.csc.ist.pt.csc.model.MyLocation;
import brunoc.csc.ist.pt.csc.model.User;

/**
 * Created by Bruno on 30/12/2016.
 */

public class Controller {
    private final String DOCUMENT_ID = "bb8f07d320e7fb3fb596c71624820bae";
    private final String DATABASE = "locations";
    private final String USER = "cscbruno";
    private final String USER_PASS = "csc_senha123";
    private final Encriptor encriptor;

    private Context context;
    private CloudantController cloudantController;


    public Controller(Context context){
        this.context = context;
        cloudantController = new CloudantController(context);
        cloudantController.doLogin(USER, USER_PASS);
        this.encriptor = new Encriptor(1024);
    }
    public interface onDataReceiver{
        void onReceive(ArrayList<User> users, String rev);
    }
    public void getUsers(final onDataReceiver listener ){
        cloudantController.doLogin(USER, USER_PASS);
        cloudantController.readDoc(DATABASE, DOCUMENT_ID, "READ DOCUMENT", new CloudantController.ResponseListener() {
            @Override
            public void onResponse(String tag, String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    String rev = json.get("_rev").toString();
                    int size = Integer.parseInt(json.get("size").toString());
                    ArrayList<User> users = new ArrayList<User>();
                    JSONArray aux;
                    JSONObject jsonObj;

                    for(int i = 0; i<size; ++i){
                        aux = json.getJSONArray("users");
                        jsonObj = aux.getJSONObject(i);
                        String name = jsonObj.toString().split("\"")[1];
                        aux = jsonObj.getJSONArray(name);
                        jsonObj = (JSONObject)aux.get(0);
                        String lat = encriptor.decrypt(jsonObj.get("latitude").toString());
                        String longi = encriptor.decrypt(jsonObj.get("longitude").toString());
                        users.add(i, new User(name, new MyLocation(lat, longi)));
                    }
                    listener.onReceive(users, rev);

                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
            @Override
            public void onLoginSuccess() {
            }
        });
    }
    boolean inserted;
    public void update(final String username, final MyLocation location){
            cloudantController.doLogin(USER, USER_PASS);
        try{
            location.latitude = encriptor.encrypt(location.latitude);
            location.longitude = encriptor.encrypt(location.longitude);
        } catch(Exception e){e.printStackTrace();}

            inserted = false;
            getUsers(new onDataReceiver() {
                @Override
                public void onReceive(ArrayList<User> users, String rev) {
                    ArrayList<User> aux = new ArrayList<User>();
                    for(User u: users){
                        if(u.getName().equals(username)){
                            u.setLocation(location);
                            inserted = true;
                            aux.add(u);

                        }
                        else {
                            u.getLocation().latitude = encriptor.encrypt(u.getLocation().latitude);
                            u.getLocation().longitude = encriptor.encrypt(u.getLocation().longitude);
                            aux.add(u);
                        }
                        if(inserted){
                            try {

                                cloudantController.updateDoc(DATABASE, usersString(aux)+", \"size\":\""+ aux.size() + "\"}", DOCUMENT_ID, rev, "UPDATE");
                                return;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if(!inserted){
                        aux.add(aux.size(), new User(username, location));
                        try {
                            cloudantController.updateDoc(DATABASE, usersString(aux)+", \"size\":\""+ aux.size() + "\"}", DOCUMENT_ID, rev, "UPDATE");
                            return;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
    }
    public String usersString(ArrayList<User> users){
        String out = "{\"users\":[";
        User u = null;
        for(int i = 0; i<users.size(); i++){
            u = users.get(i);
            out+="{\""+u.getName()+"\":[{\"latitude\":\""+ u.getLocation().latitude+"\",\"longitude\":\""+u.getLocation().longitude+"\"}]}";
            if (i<users.size()-1)
                out+=",";


        }
        out+="]";
        return out;
    }

}
/////////////////////
