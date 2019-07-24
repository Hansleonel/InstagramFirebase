package com.develop.instagramtest.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.develop.instagramtest.Adapter.EventAdapter;
import com.develop.instagramtest.Model.Event;
import com.develop.instagramtest.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProfileUserFragment extends Fragment {

    ImageView image_profile;
    TextView txtV_username, txtV_userDescription, txtV_userLocation, txtV_userEvents, txtV_userPetitions, txtV_userEstadistics;
    RecyclerView rV_events;
    List<Event> eventArrayList;
    EventAdapter eventAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_user, container, false);
        image_profile = view.findViewById(R.id.image_profile_user);
        txtV_username = view.findViewById(R.id.username_profile);
        txtV_userDescription = view.findViewById(R.id.description_profile_user);
        txtV_userLocation = view.findViewById(R.id.location_profile_user);
        txtV_userEvents = view.findViewById(R.id.events_profile_user);
        txtV_userPetitions = view.findViewById(R.id.petitions_profile_user);
        txtV_userEstadistics = view.findViewById(R.id.estadistics_profile_user);

        rV_events = view.findViewById(R.id.rv_Eventos);
        rV_events.setHasFixedSize(true);
        LinearLayoutManager mLinearLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rV_events.setLayoutManager(mLinearLayout);
        eventArrayList = new ArrayList<>();
        eventAdapter = new EventAdapter(getContext(), eventArrayList);
        rV_events.setAdapter(eventAdapter);

        myEvents();


        return view;
    }

    private void myEvents() {
        eventArrayList.add(new Event("01", "https://images.unsplash.com/photo-1535013113415-b315146c67d5?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=750&q=80", "Description", "userPublisher"));
        eventArrayList.add(new Event("02", "https://images.unsplash.com/photo-1461696114087-397271a7aedc?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=750&q=80", "Description", "userPublisher"));
        eventArrayList.add(new Event("03", "https://images.unsplash.com/photo-1530103043960-ef38714abb15?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=750&q=80", "Description", "userPublisher"));
        eventArrayList.add(new Event("04", "https://images.unsplash.com/photo-1548192746-dd526f154ed9?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=750&q=80", "Description", "userPublisher"));
        eventArrayList.add(new Event("05", "https://images.unsplash.com/photo-1541490178313-cedc90f7e08c?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=749&q=80", "Description", "userPublisher"));
        eventArrayList.add(new Event("06", "https://images.unsplash.com/photo-1536537977604-6aa93c7fa77f?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=750&q=80", "Description", "userPublisher"));
        Collections.reverse(eventArrayList);
        eventAdapter.notifyDataSetChanged();

    }
}
