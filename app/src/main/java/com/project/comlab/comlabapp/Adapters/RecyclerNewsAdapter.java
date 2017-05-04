package com.project.comlab.comlabapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.comlab.comlabapp.Activities.NewDetailActivity;
import com.project.comlab.comlabapp.POJO.NewsModel;
import com.project.comlab.comlabapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by aldodev20 on 24/04/17.
 */

public class RecyclerNewsAdapter extends RecyclerView.Adapter<RecyclerNewsAdapter.ViewHolder> {

    List<NewsModel> newsList;
    Activity activity;
    Context context;

    public RecyclerNewsAdapter(Activity activity, Context context, List<NewsModel> newsList){
        this.newsList = newsList;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);

        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.title.setText(newsList.get(position).getTitle());
        holder.description.setText(newsList.get(position).getDescription());
        Picasso.with(context).load(newsList.get(position).getImage()).into(holder.image);

        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, NewDetailActivity.class);
                intent.putExtra("title", newsList.get(position).getTitle());
                intent.putExtra("description", newsList.get(position).getDescription());
                intent.putExtra("owner", newsList.get(position).getOwner());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView description;
        ImageView image;
        CardView cardview;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.item_news_title);
            description = (TextView) itemView.findViewById(R.id.item_news_description);
            cardview = (CardView) itemView.findViewById(R.id.item_news_card);
            image = (ImageView) itemView.findViewById(R.id.item_news_image);
        }
    }
}
