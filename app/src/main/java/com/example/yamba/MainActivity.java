package com.example.yamba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.action_purge:
                return true;
            case R.id.action_tweet:
                startActivity(new Intent(this, StatusActivity.class));
                return true;
            default:
                return false;
        }
    }

    /*
    PARTE CLAVES TWITTER ELIMINADA
    ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setOAuthConsumerKey("OxvoeCK1YDgO4u7nxlHrnoAQD")
                .setOAuthConsumerSecret("SF0tpacg1SzS54BdzyPPwD3Fq6XTalRgp1HiQcGt6oTQLszTnw")
                .setOAuthAccessToken("1126862183974014976-m3cYCZ2VRUO6AvGE64XL0hD8wDIVrp")
                .setOAuthAccessTokenSecret("mliCoo0HHBOv7E060EdIe0APBh3tvYcBIRYMveebEcgjR");
        TwitterFactory factory = new TwitterFactory(builder.build());
        twitter = factory.getInstance();
     */
}
