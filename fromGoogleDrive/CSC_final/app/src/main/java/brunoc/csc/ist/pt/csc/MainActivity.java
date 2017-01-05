package brunoc.csc.ist.pt.csc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;

import brunoc.csc.ist.pt.csc.controller.Controller;
import brunoc.csc.ist.pt.csc.services.Locator;

public class MainActivity extends Activity {

    Controller controller;
    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

        setContentView(R.layout.activity_main);
        Button preferencesButton = (Button)findViewById(R.id.preferencesButton);
        final Locator locator = new Locator(this, 12);
        final Intent intent = new Intent(this, PreferencesActivity.class);
        preferencesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        controller = new Controller(this);
        final SharedPreferences preferences = getSharedPreferences(this.getResources().getString(R.string.userPreferences), Context.MODE_PRIVATE);



        final Context ctx = this;
        Button shareLoc = (Button)findViewById(R.id.shareLocButton);
        shareLoc.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                locator.getLastKnownLocation();
                /**
                 * DEBUG ONLY
                 */
                //locator.updateWithFakeLocation();
            }
        });
        Button login = (Button)findViewById(R.id.loginButton);
        login.setEnabled(preferences.getString("username", "") != "" && !preferences.getBoolean("logged", false));
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, LoginActivity.class);
                startActivity(intent);
            }
        });
        Button loggout = (Button)findViewById(R.id.loggoutButton);
        loggout.setEnabled(preferences.getBoolean("logged", false));
        loggout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor edit = preferences.edit();
                edit.putBoolean("logged", false);
                edit.commit();
                Intent i = new Intent(ctx, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        Button register = (Button) findViewById(R.id.registerButton);
        register.setEnabled(preferences.getString("username", "") == "");
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, RegisterActivity.class);
                startActivity(intent);
            }
        });

        Button following = (Button) findViewById(R.id.followingButton);
        following.setEnabled( preferences.getBoolean("logged", false));
        following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, FollowingActivity.class);
                startActivity(intent);
            }
        });
    }


}
