package brunoc.csc.ist.pt.csc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Context ctx = this;

        username = (EditText)findViewById(R.id.usernameEditText);
        password = (EditText)findViewById(R.id.passwordEditText);

        Button login = (Button)findViewById(R.id.login);
        preferences = getSharedPreferences(this.getResources().getString(R.string.userPreferences), Context.MODE_PRIVATE);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user, pass;
                user = username.getText().toString();
                pass = password.getText().toString();
                if(user == null || pass == null || user == "" || pass == ""){
                    Toast.makeText(ctx, "Please insert credentials", Toast.LENGTH_SHORT).show();
                    return;
                }
                String name = preferences.getString("username", "");
                if(!name.equals(user)){
                    Toast.makeText(ctx, "Invalid username", Toast.LENGTH_SHORT).show();
                    return;
                }
                String passw = preferences.getString("password", "");
                if(!pass.equals(passw)){
                    Toast.makeText(ctx, "Incorrect Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                SharedPreferences.Editor edit = preferences.edit();
                edit.putBoolean("logged", true);
                edit.commit();
                Toast.makeText(ctx, name+ " successfully logged", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ctx,MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }
}
