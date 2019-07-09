package com.develop.instagramtest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.develop.instagramtest.Model.Constants.URL_BASE;

public class SplashActivity extends AppCompatActivity {

    FirebaseUser firebaseUser;

    private String realLoginUser;
    private String realLoginPass;
    private String TOKEN;

    @Override
    protected void onStart() {
        super.onStart();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // checkLoginFirebase();
                checkLoginRealDataBase();
            }
        }, 2000);
    }

    private void checkLoginFirebase() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        //check if user is null
        if (firebaseUser != null) {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent i = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }
    }

    private void checkLoginRealDataBase() {
        SharedPreferences SP = getApplicationContext().getSharedPreferences("USERSHAREFILE", Context.MODE_PRIVATE);
        realLoginUser = SP.getString("USERLOGIN", "UserDefaultValue");
        realLoginPass = SP.getString("PASSWORDLOGIN", "PassDefaultValue");
        Log.d("onStartActivity", "checkLoginRealDataBase: usuario " + realLoginUser);
        Log.d("onStartActivity", "checkLoginRealDataBase: password " + realLoginPass);
        // TODO si existe un valor almacenado dentro y que no sea el valor por Default
        // TODO dentro del sharedPreference realizar un login
        if (!realLoginUser.equals("UserDefaultValue")) {
            // TODO metodo usado si se tiene los datos del usuario dentro del sharedPreference
            // TODO pero no se garantiza la conexion a internet
            // ingresoOffLine();
            // TODO metodo usado cuando se garantiza que siempre se tendra conexion a internet
            // TODO ademas de tener los datos de usuario dentro de el sharedPreference
            // TODO para realizar el login
            loginRealDataBase();
        } else {
            // TODO metodo usado en caso sea el primer ingreso a aplicacion
            // TODO es decir se necesita realizar un login
            toLoginActivity();
        }
    }

    private void loginRealDataBase() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("password", realLoginPass);
            jsonObject.put("remeberMe", "true");
            jsonObject.put("username", realLoginUser);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Make request for JSONObject
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, URL_BASE + "/authenticate", jsonObject,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("onLoginRealDatabase", response.get("id_token") + " i am queen");
                            TOKEN = response.get("id_token").toString();

                            SharedPreferences sharedPreferences = getSharedPreferences("TOKENSHAREFILE", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString("TOKENSTRING", TOKEN);
                            editor.commit();

                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO en caso exista un error
                // TODO se hara un clear al sharedPreference
                SharedPreferences sharedPreferences = getSharedPreferences("USERSHAREFILE", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                VolleyLog.d("onLoginRealDataBase", "Error: " + error.toString());
                Toast.makeText(getApplicationContext(), "ERROR " + error.toString(), Toast.LENGTH_LONG).show();
                finish();
            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        // Adding request to request queue
        Volley.newRequestQueue(this).add(jsonObjReq);
    }

    private void toLoginActivity() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }
}
