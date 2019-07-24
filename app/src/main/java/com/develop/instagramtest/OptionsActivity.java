package com.develop.instagramtest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class OptionsActivity extends AppCompatActivity {

    TextView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Options");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO cuando se usó firebase para el login
                /* FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(OptionsActivity.this, StartActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));*/
                // TODO cuando se uso un login normal limpiamos todo el sharedPreference
                // TODO ademas limpiamos de memoria los activities previos
                SharedPreferences sharedPreferences = getSharedPreferences("USERSHAREFILE", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Intent i = new Intent(OptionsActivity.this, SplashActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }
}
