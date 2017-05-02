package com.project.comlab.comlabapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codesgood.views.JustifiedTextView;
import com.project.comlab.comlabapp.POJO.EventsModel;
import com.project.comlab.comlabapp.R;

import java.util.List;

/**
 * Created by aldodev20 on 01/05/17.
 */

public class RecyclerEventsAdapter extends RecyclerView.Adapter<RecyclerEventsAdapter.ViewHolder> {

    List<EventsModel> eventsList;

    public RecyclerEventsAdapter(List<EventsModel> eventsList){
        this.eventsList = eventsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_events, parent, false);

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(eventsList.get(position).getTitle());
        holder.place.setText(eventsList.get(position).getAdress());
        holder.description.setText(eventsList.get(position).getDescription());
        holder.date.setText(eventsList.get(position).getDate());
        holder.time.setText(eventsList.get(position).getTime());
        holder.rating.setNumStars(5);
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView place;
        JustifiedTextView description;
        TextView date;
        TextView time;
        RatingBar rating;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.item_events_title);
            place = (TextView) itemView.findViewById(R.id.item_events_place);
            description = (JustifiedTextView) itemView.findViewById(R.id.item_events_description);
            date = (TextView) itemView.findViewById(R.id.item_events_date);
            time = (TextView) itemView.findViewById(R.id.item_events_time);
            rating = (RatingBar) itemView.findViewById(R.id.item_events_rb);

        }
    }
}
