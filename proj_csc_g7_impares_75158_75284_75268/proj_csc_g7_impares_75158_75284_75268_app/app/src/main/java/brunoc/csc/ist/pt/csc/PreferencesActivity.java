package brunoc.csc.ist.pt.csc;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import brunoc.csc.ist.pt.csc.R;

public class PreferencesActivity extends AppCompatActivity {
    static int state;
    static Boolean noti;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_preferences);

        SharedPreferences preferences = this.getSharedPreferences("userPreferences" , Context.MODE_PRIVATE);

        final SharedPreferences.Editor editor = preferences.edit();

        Switch notifications = (Switch)findViewById(R.id.notificationSwitch);

        notifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               noti = isChecked;
            }
        });
        final Context ctx = this;
        SeekBar rate = (SeekBar)findViewById(R.id.timeBar);

        rate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                state = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(ctx, "Rate set to "+state, Toast.LENGTH_SHORT).show();
            }
        });
        Button save = (Button)findViewById(R.id.button3);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean("notifications", noti);
                editor.putInt("locatorRate", state);
                editor.commit();
                Toast.makeText(ctx, "saved", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
