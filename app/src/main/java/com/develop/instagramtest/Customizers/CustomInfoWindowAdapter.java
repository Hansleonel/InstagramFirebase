package com.develop.instagramtest.Customizers;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.develop.instagramtest.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter, View.OnClickListener {

    private static final String TAG = "CustomInfoWindowAdapter";
    private LayoutInflater inflater;
    Button btn_marker;
    TextView txtV_ciudad;
    CircleImageView circleImageView_marker;
    private View.OnClickListener listener;

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public CustomInfoWindowAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View v = inflater.inflate(R.layout.marker_item, null);

        btn_marker = (Button) v.findViewById(R.id.button_marker_custom);
        txtV_ciudad = (TextView) v.findViewById(R.id.textV_marker_city);
        circleImageView_marker = (CircleImageView) v.findViewById(R.id.marker_image);
        String[] info = marker.getTitle().split("&");
        String url = marker.getSnippet();
        String ciudad = marker.getTitle();
        if (marker.getTitle().equals("MGP")) {
            circleImageView_marker.setImageResource(R.mipmap.ic_marker_navy_logo);
        } else if (marker.getTitle().equals("FAP")) {
            circleImageView_marker.setImageResource(R.mipmap.ic_marker_air_logo);
        } else if (marker.getTitle().equals("EP")) {
            circleImageView_marker.setImageResource(R.mipmap.ic_marker_army_logo);
        }
        txtV_ciudad.setText(ciudad);
        return v;

    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }
}
