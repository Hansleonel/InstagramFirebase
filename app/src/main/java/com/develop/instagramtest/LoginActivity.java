package com.develop.instagramtest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.develop.instagramtest.Model.Constants.URL_BASE;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button login, btn_signup;
    TextView txt_signup;

    FirebaseAuth auth;
    String TOKEN = " ";
    // ProgressDialog pd = new ProgressDialog(LoginActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        txt_signup = findViewById(R.id.txt_signup);
        btn_signup = findViewById(R.id.btn_signup);

        //auth = FirebaseAuth.getInstance();

        txt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
                pd.setMessage("Porfavor Espere...");
                pd.show();

                String str_email = email.getText().toString();
                String str_password = password.getText().toString();

                if (TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password)) {
                    pd.dismiss();
                    Toast.makeText(LoginActivity.this, "Todos los campos son requeridos!", Toast.LENGTH_SHORT).show();
                } else {
                    // loginImagination();
                    // loginFirebase();
                    loginRealDataBase();
                }
            }
        });
    }

    private void loginImagination() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void loginFirebase() {
        auth = FirebaseAuth.getInstance();
        final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
        String str_email = email.getText().toString();
        String str_password = password.getText().toString();
        auth.signInWithEmailAndPassword(str_email, str_password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users")
                                    .child(auth.getCurrentUser().getUid());

                            reference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    pd.dismiss();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    pd.dismiss();
                                }
                            });
                        } else {
                            pd.dismiss();
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void loginRealDataBase() {
        final String user = email.getText().toString();
        final String pass = password.getText().toString();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("password", pass);
            jsonObject.put("remeberMe", "true");
            jsonObject.put("username", user);
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

                            SharedPreferences preferences = getSharedPreferences("USERSHAREFILE", MODE_PRIVATE);
                            SharedPreferences.Editor editor1 = preferences.edit();

                            editor1.putString("USERLOGIN", user);
                            editor1.putString("PASSWORDLOGIN", pass);
                            editor1.commit();


                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("onLoginRealDataBase", "Error: " + error.toString());
                Toast.makeText(getApplicationContext(), "ERROR " + error.toString(), Toast.LENGTH_LONG).show();
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

}