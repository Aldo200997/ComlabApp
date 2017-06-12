package com.project.comlab.comlabapp.SearchV;

import android.widget.Filter;

import com.project.comlab.comlabapp.Adapters.RecyclerEventsAdapter;
import com.project.comlab.comlabapp.POJO.EventsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aldodev20 on 08/06/17.
 */

public class CustomFilterEvents extends Filter {

    List<EventsModel> eventsList;
    RecyclerEventsAdapter adapter;

    public CustomFilterEvents(List<EventsModel> eventsList, RecyclerEventsAdapter adapter){
        this.eventsList = eventsList;
        this.adapter = adapter;
    }


    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();

        if(constraint != null && constraint.length() > 0){
            constraint = constraint.toString().toUpperCase();
            List<EventsModel> eventsFilterList = new ArrayList<>();

            for (int i = 0; i < eventsList.size(); i++){
                if(eventsList.get(i).getTitle().toUpperCase().contains(constraint) || eventsList.get(i).getTag().toUpperCase().contains(constraint)){
                    eventsFilterList.add(eventsList.get(i));
                }
            }

            results.count = eventsFilterList.size();
            results.values = eventsFilterList;
        }else{
            results.count = eventsList.size();
            results.values = eventsList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.eventsList = (List<EventsModel>) results.values;
        adapter.notifyDataSetChanged();
    }
}
