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
            case R.id.itemServiceStart:
                Intent refreshIntent = new Intent(this, RefreshService.class);
                refreshIntent.setAction(RefreshService.ACTION_FETCH_TIMELINE);
                startService(refreshIntent);
                return true;
            case R.id.itemServiceStop:
                stopService(new Intent(this, RefreshService.class));
                return true;
            default:
                return false;
        }
    }
}
