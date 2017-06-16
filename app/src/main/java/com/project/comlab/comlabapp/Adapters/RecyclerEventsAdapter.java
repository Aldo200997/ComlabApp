package com.project.comlab.comlabapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codesgood.views.JustifiedTextView;
import com.project.comlab.comlabapp.Activities.EventDetailActivity;
import com.project.comlab.comlabapp.POJO.EventsModel;
import com.project.comlab.comlabapp.R;
import com.project.comlab.comlabapp.SearchV.CustomFilterEvents;
import com.project.comlab.comlabapp.SearchV.ItemClickListener;

import java.io.Serializable;
import java.util.List;

/**
 * Created by aldodev20 on 01/05/17.
 */

public class RecyclerEventsAdapter extends RecyclerView.Adapter<RecyclerEventsAdapter.ViewHolder> implements Filterable {

    public List<EventsModel> eventsList, filterEventsList;
    Activity activity;
    Context context;
    CustomFilterEvents filterEvents;

    int[] colors = {R.color.black, R.color.purple, R.color.indigo, R.color.blue, R.color.cyan,
            R.color.green, R.color.yellow, R.color.orange, R.color.brown};

    // Numero random del 0 a 9
    int randomNum = (int)(Math.floor(Math.random() * 10 ));

    public RecyclerEventsAdapter(Activity activity, Context context, List<EventsModel> eventsList){
        this.eventsList = eventsList;
        this.filterEventsList = eventsList;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_events, parent, false);

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.title.setText(eventsList.get(position).getTitle());
        holder.place.setText(eventsList.get(position).getAdress());
        holder.description.setText(eventsList.get(position).getDescription());
        holder.date.setText(eventsList.get(position).getDate());
        holder.time.setText(eventsList.get(position).getTime());
        holder.tag.setText(eventsList.get(position).getTag());
        holder.image.setBackgroundColor(context.getResources().getColor(colors[(position + randomNum) % 9]));
        holder.rating.setNumStars(5);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, EventDetailActivity.class);
                intent.putExtra("key", eventsList.get(position).getKey());
                intent.putExtra("title", eventsList.get(position).getTitle());
                intent.putExtra("place", eventsList.get(position).getAdress());
                intent.putExtra("place_two", eventsList.get(position).getAdrees_two());
                intent.putExtra("place_three", eventsList.get(position).getAdress_three());
                intent.putExtra("description", eventsList.get(position).getDescription());
                intent.putExtra("image", eventsList.get(position).getImage());
                intent.putExtra("date", eventsList.get(position).getDate());
                intent.putExtra("date_two", eventsList.get(position).getDate_two());
                intent.putExtra("date_three", eventsList.get(position).getDate_three());
                intent.putExtra("time", eventsList.get(position).getTime());
                intent.putExtra("time_two", eventsList.get(position).getTime_two());
                intent.putExtra("time_three", eventsList.get(position).getTime_three());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    @Override
    public Filter getFilter() {
        if(filterEvents == null){
            filterEvents = new CustomFilterEvents(filterEventsList, this);
        }

        return filterEvents;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView place;
        TextView description;
        TextView date;
        TextView time;
        TextView tag;
        ImageView image;
        RatingBar rating;
        CardView cardView;


        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.item_events_title);
            place = (TextView) itemView.findViewById(R.id.item_events_place);
            description = (TextView) itemView.findViewById(R.id.item_events_description);
            date = (TextView) itemView.findViewById(R.id.item_events_date);
            time = (TextView) itemView.findViewById(R.id.item_events_time);
            tag = (TextView) itemView.findViewById(R.id.item_events_tag);
            image = (ImageView) itemView.findViewById(R.id.item_events_image);
            rating = (RatingBar) itemView.findViewById(R.id.item_events_rb);
            cardView = (CardView) itemView.findViewById(R.id.item_events_card);

        }
    }
}
