package brunoc.csc.ist.pt.csc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    SharedPreferences preferences;
    EditText un;
    EditText pw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        un = (EditText)findViewById(R.id.userText);
        pw = (EditText)findViewById(R.id.pwText);
        final Context ctx = this;
        Button register = (Button)findViewById(R.id.register);
        preferences = getSharedPreferences(this.getResources().getString(R.string.userPreferences), Context.MODE_PRIVATE);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = un.getText().toString();
                String password = pw.getText().toString();
                String savedUser = preferences.getString("username", "");
                if(username == savedUser){
                    Toast.makeText(ctx, "Username already taken", Toast.LENGTH_SHORT).show();
                    return;
                }
                SharedPreferences.Editor editor = preferences.edit();
                if(password == "" || password == null){
                    Toast.makeText(ctx, "Please Insert Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                editor.putString("username", username );
                editor.putString("password", password );
                editor.putBoolean("logged", true);
                editor.commit();
                Toast.makeText(ctx, "User "+username+" registered and logged", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ctx, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
