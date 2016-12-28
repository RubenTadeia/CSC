package brunoc.csc.ist.pt.csc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import brunoc.csc.ist.pt.csc.services.Locator;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

        setContentView(R.layout.activity_main);
        Button preferencesButton = (Button)findViewById(R.id.preferencesButton);
        Locator locator = new Locator(this, 12);
        final Intent intent = new Intent(this, PreferencesActivity.class);
        preferencesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        final Context ctx = this;
        Button shareLoc = (Button)findViewById(R.id.shareLocButton);
        shareLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ctx, "Locating", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
