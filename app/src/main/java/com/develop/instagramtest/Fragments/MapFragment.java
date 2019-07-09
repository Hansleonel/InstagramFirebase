package com.develop.instagramtest.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.develop.instagramtest.MainActivity;
import com.develop.instagramtest.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.develop.instagramtest.Model.Constants.URL_BASE;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    View view;
    MapView mapView;
    GoogleMap MgoogleMap;

    Context mcontext;

    private String TokenUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_map, container, false);
        // TODO creando el mcontext
        mcontext = getActivity().getApplicationContext();
        // TODO sharedPreference para recuperar el TOKEN del usuario
        // TODO con cierta modificacion (getContext) por estar dentro de un Fragment
        SharedPreferences SP = getContext().getSharedPreferences("TOKENSHAREFILE", Context.MODE_PRIVATE);
        TokenUser = SP.getString("TOKENSTRING", "TokenDefaultValue");

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mapView = view.findViewById(R.id.map);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MgoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        CameraUpdate camupd = CameraUpdateFactory.newLatLngZoom(new LatLng(-12.0431800, -77.0282400), 12);
        MgoogleMap.moveCamera(camupd);
        eventsUser();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onStart() {
        super.onStart();
        // eventsUser();
    }

    private void eventsUser() {
        MgoogleMap.clear();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, URL_BASE + "/eventoByFuncionario", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jrJsonObject = response.getJSONObject(i);
                                String idEvento = jrJsonObject.getString("idEvento");
                                String latitud = jrJsonObject.getString("latitud");
                                String longitud = jrJsonObject.getString("longitud");
                                String prioridad = jrJsonObject.getString("prioridad");
                                String Descripicion = jrJsonObject.getString("descripcion");
                                int criticidad = jrJsonObject.getInt("nivelorden");
                                Double lat = Double.parseDouble(latitud);
                                Double longit = Double.parseDouble(longitud);
                                if (criticidad == 1 || criticidad == 2) {
                                    MgoogleMap.addMarker(new MarkerOptions().position(new LatLng(lat, longit)).title(Descripicion).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker_green))).setTag(idEvento);
                                } else if (criticidad == 3) {
                                    MgoogleMap.addMarker(new MarkerOptions().position(new LatLng(lat, longit)).title(Descripicion).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker_yellow))).setTag(idEvento);
                                } else if (criticidad == 4) {
                                    MgoogleMap.addMarker(new MarkerOptions().position(new LatLng(lat, longit)).title(Descripicion).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker_red))).setTag(idEvento);
                                }
                                Log.d("onEventsUser", "onResponse: " + idEvento);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // VolleyLog.d("eRRor Response", "Error: " + error.toString());
                VolleyLog.d("onEventsUser ", "Error: " + error.toString());
                Toast.makeText(getContext(), "ON RESPONSE" + error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "Bearer " + TokenUser);
                return headers;
            }
        };

        // Adding request to request queue
        Volley.newRequestQueue(mcontext).add(jsonArrayRequest);
    }
}
