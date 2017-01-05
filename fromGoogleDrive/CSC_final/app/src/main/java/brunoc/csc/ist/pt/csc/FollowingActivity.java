package brunoc.csc.ist.pt.csc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import brunoc.csc.ist.pt.csc.controller.Controller;
import brunoc.csc.ist.pt.csc.model.MyLocation;
import brunoc.csc.ist.pt.csc.model.User;
import brunoc.csc.ist.pt.csc.model.UserAdapter;

public class FollowingActivity extends AppCompatActivity {
    EditText username;
    Controller controller;
    SharedPreferences preferences;
    ArrayList<User> users;
    ListView listView;
    UserAdapter adapter;
    Button follow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);


        controller = new Controller(this);
        preferences = getSharedPreferences(this.getResources().getString(R.string.userPreferences), Context.MODE_PRIVATE);
        users = new ArrayList<User>();

        final Context ctx = this;
        username = (EditText)findViewById(R.id.followText);
        follow = (Button)findViewById(R.id.followButton);

        listView = (ListView)findViewById(R.id.usersList);


        controller.getUsers(new Controller.onDataReceiver() {
            @Override
            public void onReceive(ArrayList<User> u, String rev) {
                users = u;


                ArrayList<User> iFollow = new ArrayList<>();
                int following = preferences.getInt("following", 0);
                for(int i = 1; i<=following; ++i){
                    String aux = preferences.getString("user"+i, "");
                    for(int c = 0; c<users.size();++c){
                        if(aux.equals(users.get(c).getName()))
                            iFollow.add(users.get(c));
                    }
                }

                adapter = new UserAdapter(ctx,iFollow);
                listView.setAdapter(adapter);
                listView.invalidate();

            }
        });


        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                if( user.equals("")){
                    Toast.makeText(ctx, "Please Insert a username", Toast.LENGTH_SHORT).show();
                    return;
                }
                for(User u : users){
                    if(u.getName().equals(user)){
                        int i = preferences.getInt("following", 0);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putInt("following", ++i);
                        editor.putString("user"+i, u.getName());
                        editor.commit();
                        Toast.makeText(ctx, "now following "+u.getName(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                Toast.makeText(ctx, "no such user", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
