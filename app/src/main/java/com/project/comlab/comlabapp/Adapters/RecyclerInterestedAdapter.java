package com.project.comlab.comlabapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.comlab.comlabapp.POJO.InterestedModel;
import com.project.comlab.comlabapp.R;

import java.util.List;

/**
 * Created by aldodev20 on 19/04/17.
 */

public class RecyclerInterestedAdapter extends RecyclerView.Adapter<RecyclerInterestedAdapter.ViewHolder> {
    List<InterestedModel> interestedList;

    public RecyclerInterestedAdapter(List<InterestedModel> interestedList){
        this.interestedList = interestedList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_interested, parent, false);

        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(interestedList.get(position).title);
        holder.image.setImageResource(interestedList.get(position).photo);
    }

    @Override
    public int getItemCount() {
        return interestedList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.item_interested_title);
            image = (ImageView) itemView.findViewById(R.id.item_interested_header);
        }
    }
}
