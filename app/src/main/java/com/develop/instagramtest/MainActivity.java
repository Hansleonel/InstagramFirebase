package com.develop.instagramtest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.develop.instagramtest.Fragments.MapFragment;
import com.develop.instagramtest.Fragments.ProfileUserFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.develop.instagramtest.Fragments.HomeFragment;
import com.develop.instagramtest.Fragments.NotificationFragment;
import com.develop.instagramtest.Fragments.ProfileFragment;
import com.develop.instagramtest.Fragments.SearchFragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottom_navigation;
    Fragment selectedfragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        // TODO Selected item default
        // TODO ademas de la visualizacion de la seleccion del item en el bottomNavigation
        // TODO se debe de confirmar la seleccion del Fragment
        // TODO en este caso el FragmentMap
        bottom_navigation.setSelectedItemId(R.id.nav_heart);


        // TODO en caso SI encuentre un extra cuando se llegue a esta Activity nos dirigiremos al fragment
        // TODO Profile
        Bundle intent = getIntent().getExtras();
        if (intent != null) {
            String publisher = intent.getString("publisherid");

            SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
            editor.putString("profileid", publisher);
            editor.apply();

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ProfileFragment()).commit();
        }// TODO en caso no se encuentre un extra cuando se llegue a esta Activity nos dirigiremos al fragment
        // TODO Home
        else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MapFragment()).commit();
        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedfragment = new HomeFragment();
                            break;
                        case R.id.nav_search:
                            selectedfragment = new SearchFragment();
                            break;
                        case R.id.nav_add:
                            // TODO el item no se mostrara ningun fragmento
                            // TODO es por eso que se redirecciona a otra activity
                            selectedfragment = null;
                            startActivity(new Intent(MainActivity.this, PostActivity.class));
                            break;
                        case R.id.nav_heart:
                            selectedfragment = new MapFragment();
                            break;
                        case R.id.nav_profile:
                            // TODO como vemos cuando seleccionamos el item nav_profile realizamos una consulta a Firebase
                            // TODO dicha consulta nos devuelve el usuario lorgeado en ese instante
                            // TODO luego con el editor sharedPreference almacenamos de forma persistente el profileid
                            // TODO dicha variable se usara en el ProfileFragment
                            // SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                            // editor.putString("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                            // editor.apply();
                            selectedfragment = new ProfileUserFragment();
                            break;
                    }
                    if (selectedfragment != null) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                selectedfragment).commit();
                    }

                    return true;
                }
            };
}
