package com.example.yamba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class StatusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        // Comprobar si la actividad ya ha sido creada con anterioridad
        if (savedInstanceState == null) {
            // Crear un fragment
            StatusFragment fragment = new StatusFragment();
            // Crear una instacia del FragmentManager y realizar la transacción
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .add(android.R.id.content, fragment, fragment.getClass().getSimpleName())
                    .commit();

        }
    }
}