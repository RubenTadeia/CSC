package brunoc.csc.ist.pt.csc.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


import brunoc.csc.ist.pt.csc.R;



/**
 * Created by Bruno on 30/12/2016.
 */

public class UserAdapter extends ArrayAdapter<User> {
    private Context context;
    private ArrayList<User> users;
    public UserAdapter(Context context, ArrayList<User> users) {
        super(context, R.layout.user_row);
        this.context = context;
        this.users = users;
    }
    public void setUsers(ArrayList<User> users){
        this.users = users;
    }
    @Override
    public int getCount(){
        return users.size();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.user_row, parent, false);

        TextView name = (TextView)view.findViewById(R.id.usernameTV);
        name.setText("User "+ users.get(position).getName());
        TextView lat = (TextView)view.findViewById(R.id.latitude);
        lat.setText("Latitude: "+users.get(position).getLocation().latitude);
        TextView longi = (TextView)view.findViewById(R.id.longitude);
        longi.setText("Longitude: "+users.get(position).getLocation().longitude);
        return view;
    }
}
