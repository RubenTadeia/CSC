package brunoc.csc.ist.pt.csc;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import brunoc.csc.ist.pt.csc.brunoc.csc.ist.pt.csc.utils.OnFragmentInteractionListener;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FragmentManager fm = getSupportFragmentManager();

        BlankFragment bf = new BlankFragment();
        fm.beginTransaction().add(R.id.content_main, bf).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    public void rastrear_onClick(View view) {
        Toast.makeText(this, "Rastrear", Toast.LENGTH_LONG).show();
    }

    public void alerta_onClick(View view) {
        Toast.makeText(this, "Alerta", Toast.LENGTH_SHORT).show();

        FragmentManager fm = getSupportFragmentManager();
        TestFragment tf = new TestFragment();
        fm.beginTransaction()
                .replace(R.id.content_main, tf)
                .addToBackStack(null)
                .commit();
    }

    public void sair_onClick(View view){
        finish();
        System.exit(0);
    }
}
