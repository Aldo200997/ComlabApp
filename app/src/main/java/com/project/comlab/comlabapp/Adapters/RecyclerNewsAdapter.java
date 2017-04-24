package com.project.comlab.comlabapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.comlab.comlabapp.POJO.NewsModel;
import com.project.comlab.comlabapp.R;

import java.util.List;

/**
 * Created by aldodev20 on 24/04/17.
 */

public class RecyclerNewsAdapter extends RecyclerView.Adapter<RecyclerNewsAdapter.ViewHolder> {

    List<NewsModel> newsList;

    public RecyclerNewsAdapter(List<NewsModel> newsList){
        this.newsList = newsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);

        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(newsList.get(position).getName());
        holder.description.setText(newsList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView description;

        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.item_news_title);
            description = (TextView) itemView.findViewById(R.id.item_news_description);
        }
    }
}
