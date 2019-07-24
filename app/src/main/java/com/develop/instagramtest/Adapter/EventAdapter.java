package com.develop.instagramtest.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.develop.instagramtest.Model.Event;
import com.develop.instagramtest.R;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ImageViewHolder> {

    Context mContext;
    List<Event> mEvents;

    public EventAdapter(Context mContext, List<Event> mEvents) {
        this.mContext = mContext;
        this.mEvents = mEvents;
    }

    @NonNull
    @Override
    public EventAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.event_item, parent, false);
        return new EventAdapter.ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.ImageViewHolder imageViewHolder, int position) {
        Event event = mEvents.get(position);

        Glide.with(mContext).load(event.getEventImage()).into(imageViewHolder.event_image);

        imageViewHolder.event_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("onEventAdapter", "onClick: se realizo un click");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public ImageView event_image;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            event_image = itemView.findViewById(R.id.image_item_event);
        }
    }
}
